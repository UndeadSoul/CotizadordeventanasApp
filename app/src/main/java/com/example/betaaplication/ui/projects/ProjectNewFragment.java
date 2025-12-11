package com.example.betaaplication.ui.projects;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.betaaplication.Client;
import com.example.betaaplication.Project;
import com.example.betaaplication.ProjectRepository;
import com.example.betaaplication.R;

import java.util.ArrayList;
import java.util.List;

public class ProjectNewFragment extends Fragment {

    private ProjectViewModel projectViewModel;
    private Spinner clientSpinner;
    // private Spinner projectStatusSpinner, paymentStatusSpinner;
    private EditText deliveryAddressEditText;
    // private EditText startDateEditText, depositEditText, otherWindowsValueEditText, otherWindowsEditText;
    private List<Client> clientList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_project_new, container, false);

        // Initialize Views
        clientSpinner = root.findViewById(R.id.spinner_client);
        ImageButton addClientButton = root.findViewById(R.id.button_add_client);
        deliveryAddressEditText = root.findViewById(R.id.edit_text_delivery_address);
        // startDateEditText = root.findViewById(R.id.edit_text_start_date);
        // projectStatusSpinner = root.findViewById(R.id.spinner_project_status);
        // paymentStatusSpinner = root.findViewById(R.id.spinner_payment_status);
        // depositEditText = root.findViewById(R.id.edit_text_deposit);
        // otherWindowsValueEditText = root.findViewById(R.id.edit_text_other_windows_value);
        // otherWindowsEditText = root.findViewById(R.id.edit_text_other_windows);
        Button saveButton = root.findViewById(R.id.button_save_project);

        // Initialize ViewModel
        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);

        // Setup UI components
        setupClientSpinner();
        // setupStatusSpinners();
        // setDefaultDate();

        // Button Listeners
        saveButton.setOnClickListener(v -> saveProject());
        addClientButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_project_new_to_client_new);
        });

        return root;
    }

    private void setupClientSpinner() {
        ArrayAdapter<String> clientAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new ArrayList<>());
        clientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clientSpinner.setAdapter(clientAdapter);

        projectViewModel.getAllClients().observe(getViewLifecycleOwner(), clients -> {
            clientList = clients;
            List<String> clientNames = new ArrayList<>();
            for (Client client : clients) {
                clientNames.add(client.getName());
            }
            clientAdapter.clear();
            clientAdapter.addAll(clientNames);
            clientAdapter.notifyDataSetChanged();
        });
    }

    /*
    private void setupStatusSpinners() {
        // Project Status Spinner
        String[] projectStatusOptions = {"En espera de confirmación", "En fabricación", "Entregado"};
        ArrayAdapter<String> projectStatusAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, projectStatusOptions);
        projectStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projectStatusSpinner.setAdapter(projectStatusAdapter);

        // Payment Status Spinner
        String[] paymentStatusOptions = {"Sin pagar", "Abonado", "Pagado"};
        ArrayAdapter<String> paymentStatusAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, paymentStatusOptions);
        paymentStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentStatusSpinner.setAdapter(paymentStatusAdapter);
    }
    */

    /*
    private void setDefaultDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        startDateEditText.setText(sdf.format(new Date()));
    }
    */

    private void saveProject() {
        // Client Validation
        int selectedClientPosition = clientSpinner.getSelectedItemPosition();
        if (clientList.isEmpty() || selectedClientPosition < 0) {
            Toast.makeText(getContext(), "Por favor, seleccione un cliente", Toast.LENGTH_SHORT).show();
            return;
        }
        Client selectedClient = clientList.get(selectedClientPosition);

        // Get data from fields
        String deliveryAddress = deliveryAddressEditText.getText().toString().trim();

        // Field Validation
        if (TextUtils.isEmpty(deliveryAddress)) {
            Toast.makeText(getContext(), "La dirección no puede estar vacía", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create and insert project
        Project newProject = new Project(selectedClient.getId(), deliveryAddress);

        projectViewModel.insert(newProject, projectId -> {
            if (getActivity() != null) {
                requireActivity().runOnUiThread(() -> {
                    // Navigate to the new edit screen
                    Bundle bundle = new Bundle();
                    bundle.putLong("projectId", projectId);
                    Navigation.findNavController(requireView()).navigate(R.id.action_project_new_to_project_new_edit, bundle);
                });
            }
        });
    }
}

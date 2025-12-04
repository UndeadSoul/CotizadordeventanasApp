package com.example.betaaplication.ui.projects;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.betaaplication.Client;
import com.example.betaaplication.Project;
import com.example.betaaplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProjectNewFragment extends Fragment {

    private ProjectViewModel projectViewModel;
    private Spinner clientSpinner, statusSpinner;
    private EditText startDateEditText, deliveryAddressEditText, notesEditText, addedValueEditText;
    private List<Client> clientList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_project_new, container, false);

        // Initialize Views
        clientSpinner = root.findViewById(R.id.spinner_client);
        statusSpinner = root.findViewById(R.id.spinner_status);
        startDateEditText = root.findViewById(R.id.edit_text_start_date);
        deliveryAddressEditText = root.findViewById(R.id.edit_text_delivery_address);
        notesEditText = root.findViewById(R.id.edit_text_notes);
        addedValueEditText = root.findViewById(R.id.edit_text_added_value);
        Button saveButton = root.findViewById(R.id.button_save_project);

        // Initialize ViewModel
        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);

        // Setup Client Spinner
        setupClientSpinner();

        // Setup Status Spinner
        setupStatusSpinner();

        // Save Button Logic
        saveButton.setOnClickListener(v -> saveProject());

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

    private void setupStatusSpinner() {
        String[] statusOptions = {"En espera", "Pagado", "Finalizado"};
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, statusOptions);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);
    }

    private void saveProject() {
        // Get selected client
        int selectedClientPosition = clientSpinner.getSelectedItemPosition();
        if (clientList.isEmpty() || selectedClientPosition < 0) {
            Toast.makeText(getContext(), "Por favor, seleccione un cliente", Toast.LENGTH_SHORT).show();
            return;
        }
        Client selectedClient = clientList.get(selectedClientPosition);
        int clientId = selectedClient.getId();

        // Get other fields
        String startDate = startDateEditText.getText().toString().trim();
        String status = statusSpinner.getSelectedItem().toString();
        String deliveryAddress = deliveryAddressEditText.getText().toString().trim();
        String notes = notesEditText.getText().toString().trim();
        String addedValue = addedValueEditText.getText().toString().trim();

        // Validation
        if (TextUtils.isEmpty(startDate) || TextUtils.isEmpty(deliveryAddress)) {
            Toast.makeText(getContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidDate(startDate)) {
            Toast.makeText(getContext(), "Formato de fecha inválido. Use dd/MM/yyyy", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create and insert project
        Project newProject = new Project(clientId, startDate, status, deliveryAddress, notes, addedValue);
        projectViewModel.insert(newProject);

        // Navigate back
        getParentFragmentManager().popBackStack();
    }

    private boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        sdf.setLenient(false); // This is important to reject invalid dates like 32/01/2023
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}

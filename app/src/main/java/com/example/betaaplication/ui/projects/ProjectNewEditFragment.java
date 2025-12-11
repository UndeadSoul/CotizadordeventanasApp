package com.example.betaaplication.ui.projects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.betaaplication.Project;
import com.example.betaaplication.ProjectDetails;
import com.example.betaaplication.R;
import com.example.betaaplication.Ventana;
import com.example.betaaplication.ui.windows.VentanaViewModel;
import com.example.betaaplication.ui.windows.VentanasAdapter;

public class ProjectNewEditFragment extends Fragment {

    private ProjectViewModel projectViewModel;
    private VentanaViewModel ventanaViewModel;
    private long projectId;
    private Project currentProject;

    private TextView clientNameTextView;
    private EditText deliveryAddressEditText, startDateEditText, depositEditText, otherWindowsValueEditText, otherWindowsEditText;
    private Spinner projectStatusSpinner, paymentStatusSpinner;
    private RecyclerView windowsRecyclerView;
    private Button saveButton;
    private ImageButton addWindowButton;
    private ArrayAdapter<String> projectStatusAdapter;
    private ArrayAdapter<String> paymentStatusAdapter;
    private VentanasAdapter ventanasAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_project_new_edit, container, false);

        initializeViews(root);

        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);
        ventanaViewModel = new ViewModelProvider(requireActivity()).get(VentanaViewModel.class);

        setupSpinners();
        setupRecyclerView();

        if (getArguments() != null) {
            projectId = getArguments().getLong("projectId");
            loadProjectDetails();
            ventanaViewModel.setProjectId(projectId);
        }

        ventanaViewModel.getWindowsForProject().observe(getViewLifecycleOwner(), ventanas -> {
            ventanasAdapter.submitList(ventanas);
        });

        saveButton.setOnClickListener(v -> saveProject());
        addWindowButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong("projectId", projectId);
            Navigation.findNavController(v).navigate(R.id.action_project_new_edit_to_quote, bundle);
        });

        return root;
    }

    private void initializeViews(View root) {
        clientNameTextView = root.findViewById(R.id.text_client_name);
        deliveryAddressEditText = root.findViewById(R.id.edit_text_delivery_address);
        startDateEditText = root.findViewById(R.id.edit_text_start_date);
        projectStatusSpinner = root.findViewById(R.id.spinner_project_status);
        paymentStatusSpinner = root.findViewById(R.id.spinner_payment_status);
        depositEditText = root.findViewById(R.id.edit_text_deposit);
        windowsRecyclerView = root.findViewById(R.id.recycler_view_windows);
        otherWindowsValueEditText = root.findViewById(R.id.edit_text_other_windows_value);
        otherWindowsEditText = root.findViewById(R.id.edit_text_other_windows);
        saveButton = root.findViewById(R.id.button_save_project);
        addWindowButton = root.findViewById(R.id.button_add_window);
    }

    private void setupRecyclerView() {
        windowsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        windowsRecyclerView.setHasFixedSize(false); // Allow it to wrap content
        ventanasAdapter = new VentanasAdapter();
        windowsRecyclerView.setAdapter(ventanasAdapter);

        ventanasAdapter.setOnItemClickListener(ventana -> {
            Toast.makeText(getContext(), "Ventana seleccionada: " + ventana.getId(), Toast.LENGTH_SHORT).show();
        });
    }

    private void setupSpinners() {
        String[] projectStatusOptions = {"En espera de confirmación", "En fabricación", "Entregado"};
        projectStatusAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, projectStatusOptions);
        projectStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projectStatusSpinner.setAdapter(projectStatusAdapter);

        String[] paymentStatusOptions = {"Sin pagar", "Abonado", "Pagado"};
        paymentStatusAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, paymentStatusOptions);
        paymentStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentStatusSpinner.setAdapter(paymentStatusAdapter);

        // Add listener to control the deposit field
        paymentStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStatus = parent.getItemAtPosition(position).toString();
                if ("Abonado".equals(selectedStatus) || "Pagado".equals(selectedStatus)) {
                    depositEditText.setEnabled(true);
                } else {
                    depositEditText.setEnabled(false);
                    depositEditText.setText("0"); // Reset to 0 when not applicable
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                depositEditText.setEnabled(false);
            }
        });
    }

    private void loadProjectDetails() {
        projectViewModel.getProjectDetailsById(projectId).observe(getViewLifecycleOwner(), projectDetails -> {
            if (projectDetails != null) {
                clientNameTextView.setText(projectDetails.getClientName());
                deliveryAddressEditText.setText(projectDetails.getDeliveryAddress());
                startDateEditText.setText(projectDetails.getStartDate());
                depositEditText.setText(projectDetails.getDeposit());
                otherWindowsValueEditText.setText(projectDetails.getOtherWindowsValue());
                otherWindowsEditText.setText(projectDetails.getOtherWindows());

                int projectStatusPosition = projectStatusAdapter.getPosition(projectDetails.getProjectStatus());
                projectStatusSpinner.setSelection(projectStatusPosition);

                int paymentStatusPosition = paymentStatusAdapter.getPosition(projectDetails.getPaymentStatus());
                paymentStatusSpinner.setSelection(paymentStatusPosition);

                currentProject = new Project(projectDetails.getClientId(), projectDetails.getDeliveryAddress(), projectDetails.getStartDate(), projectDetails.getProjectStatus(), projectDetails.getPaymentStatus(), projectDetails.getDeposit(), projectDetails.getOtherWindows(), projectDetails.getOtherWindowsValue());
                currentProject.setId(projectDetails.getId());
            }
        });
    }

    private void saveProject() {
        if (currentProject == null) {
            Toast.makeText(getContext(), "Error: No se pueden guardar los datos", Toast.LENGTH_SHORT).show();
            return;
        }

        currentProject.setDeliveryAddress(deliveryAddressEditText.getText().toString());
        currentProject.setStartDate(startDateEditText.getText().toString());
        currentProject.setProjectStatus(projectStatusSpinner.getSelectedItem().toString());
        currentProject.setPaymentStatus(paymentStatusSpinner.getSelectedItem().toString());
        currentProject.setDeposit(depositEditText.getText().toString());
        currentProject.setOtherWindows(otherWindowsEditText.getText().toString());
        currentProject.setOtherWindowsValue(otherWindowsValueEditText.getText().toString());

        projectViewModel.update(currentProject);

        Toast.makeText(getContext(), "Proyecto guardado", Toast.LENGTH_SHORT).show();
        
        // Navigate to the project data screen
        Bundle bundle = new Bundle();
        bundle.putLong("projectId", projectId);
        Navigation.findNavController(requireView()).navigate(R.id.action_project_new_edit_to_project_data, bundle);
    }
}

package com.example.betaaplication.ui.projects;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.betaaplication.FormatUtils;
import com.example.betaaplication.Project;
import com.example.betaaplication.ProjectDetails;
import com.example.betaaplication.R;
import com.example.betaaplication.Ventana;
import com.example.betaaplication.ui.windows.VentanaViewModel;
import com.example.betaaplication.ui.windows.VentanasAdapter;

import java.util.List;

public class ProjectNewEditFragment extends Fragment {

    private ProjectViewModel projectViewModel;
    private VentanaViewModel ventanaViewModel;
    private long projectId;
    private Project currentProject;
    private List<Ventana> currentWindows;

    private TextView clientNameTextView, totalProjectTextView, balanceTextView;
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
        addTextWatchers();
        setupFocusListeners();

        if (getArguments() != null) {
            projectId = getArguments().getLong("projectId");
            loadProjectDetails();
            ventanaViewModel.setProjectId(projectId);
        }

        ventanaViewModel.getWindowsForProject().observe(getViewLifecycleOwner(), ventanas -> {
            currentWindows = ventanas;
            ventanasAdapter.submitList(ventanas);
            updateTotals();
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
        totalProjectTextView = root.findViewById(R.id.value_total_project);
        balanceTextView = root.findViewById(R.id.value_balance);
    }

    private void setupRecyclerView() {
        windowsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        windowsRecyclerView.setHasFixedSize(false);
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

        paymentStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private String previousState = "";

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStatus = parent.getItemAtPosition(position).toString();

                // Prevent changing to "Sin pagar" if there is a deposit
                if ("Sin pagar".equals(selectedStatus)) {
                    double depositAmount = 0;
                    try {
                        depositAmount = Double.parseDouble(depositEditText.getText().toString());
                    } catch (NumberFormatException e) { /* ignore */ }
                    
                    if (depositAmount > 0) {
                        Toast.makeText(getContext(), "No se puede seleccionar 'Sin pagar' si ya existe un abono.", Toast.LENGTH_SHORT).show();
                        int lastValidPosition = paymentStatusAdapter.getPosition(previousState);
                        paymentStatusSpinner.setSelection(lastValidPosition > -1 ? lastValidPosition : 0);
                        return;
                    }
                }

                // Apply logic based on the new selection
                if ("Pagado".equals(selectedStatus)) {
                    double total = calculateTotal();
                    depositEditText.setText(String.valueOf(Math.round(total)));
                    depositEditText.setEnabled(false);
                } else if ("Abonado".equals(selectedStatus)) {
                    depositEditText.setEnabled(true);
                } else { // Sin pagar
                    depositEditText.setEnabled(false);
                    depositEditText.setText("0");
                }
                previousState = selectedStatus;
                updateTotals();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                depositEditText.setEnabled(false);
            }
        });
    }

    private void addTextWatchers() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                updateTotals();
            }
        };
        otherWindowsValueEditText.addTextChangedListener(textWatcher);
        depositEditText.addTextChangedListener(textWatcher);
    }

    private void setupFocusListeners() {
        View.OnFocusChangeListener zeroingListener = (v, hasFocus) -> {
            EditText editText = (EditText) v;
            if (hasFocus) {
                if ("0".equals(editText.getText().toString())) {
                    editText.setText("");
                }
            } else {
                if (editText.getText().toString().isEmpty()) {
                    editText.setText("0");
                }
            }
        };

        depositEditText.setOnFocusChangeListener(zeroingListener);
        otherWindowsValueEditText.setOnFocusChangeListener(zeroingListener);
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
                
                updateTotals();
            }
        });
    }

    private double calculateTotal() {
        double windowsTotal = 0;
        if (currentWindows != null) {
            for (Ventana v : currentWindows) {
                try {
                    windowsTotal += Double.parseDouble(v.getPrice());
                } catch (NumberFormatException e) { /* ignore */ }
            }
        }

        double otherValue = 0;
        if (otherWindowsValueEditText != null && !otherWindowsValueEditText.getText().toString().isEmpty()) {
            try {
                otherValue = Double.parseDouble(otherWindowsValueEditText.getText().toString());
            } catch (NumberFormatException e) { /* ignore */ }
        }

        return windowsTotal + otherValue;
    }

    private void updateTotals() {
        double total = calculateTotal();
        totalProjectTextView.setText(FormatUtils.formatCurrency(String.valueOf(total)));

        double depositAmount = 0;
        if (depositEditText != null && !depositEditText.getText().toString().isEmpty()) {
            try {
                depositAmount = Double.parseDouble(depositEditText.getText().toString());
            } catch (NumberFormatException e) { /* ignore */ }
        }

        double balanceAmount = total - depositAmount;
        balanceTextView.setText(FormatUtils.formatCurrency(String.valueOf(balanceAmount)));
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
        
        Bundle bundle = new Bundle();
        bundle.putLong("projectId", projectId);
        Navigation.findNavController(requireView()).navigate(R.id.action_project_new_edit_to_project_data, bundle);
    }
}

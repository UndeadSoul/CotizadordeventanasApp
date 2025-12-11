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
import com.example.betaaplication.ui.windows.VentanasAdapter;
import com.example.betaaplication.ui.windows.VentanaViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProjectDataFragment extends Fragment implements VentanasAdapter.OnItemClickListener {

    private long currentProjectId = -1;
    private Project currentProject;
    private List<Ventana> currentWindows = new ArrayList<>();
    private ProjectViewModel projectViewModel;
    private VentanaViewModel ventanaViewModel;

    private EditText otherWindowsValueEditText;
    private TextView totalProjectTextView, balanceTextView;
    private TextView deliveryAddressTextView, startDateTextView;
    private Spinner paymentStatusSpinner;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_project_data, container, false);

        // Initialize Views
        TextView clientName = root.findViewById(R.id.value_project_client_name);
        deliveryAddressTextView = root.findViewById(R.id.value_delivery_address);
        startDateTextView = root.findViewById(R.id.value_start_date);
        Spinner projectStatusSpinner = root.findViewById(R.id.spinner_edit_project_status);
        paymentStatusSpinner = root.findViewById(R.id.spinner_edit_payment_status);
        otherWindowsValueEditText = root.findViewById(R.id.edit_text_other_windows_value);
        EditText otherWindows = root.findViewById(R.id.edit_text_other_windows);
        totalProjectTextView = root.findViewById(R.id.value_total_project);
        balanceTextView = root.findViewById(R.id.value_balance);
        TextView depositTextView = root.findViewById(R.id.value_deposit);
        Button saveButton = root.findViewById(R.id.button_save_project_changes);
        ImageButton addWindowButton = root.findViewById(R.id.button_add_window);

        // Setup Adapters
        VentanasAdapter ventanaAdapter = new VentanasAdapter();
        RecyclerView windowsRecyclerView = root.findViewById(R.id.recycler_view_project_windows);
        windowsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        windowsRecyclerView.setAdapter(ventanaAdapter);
        ventanaAdapter.setOnItemClickListener(this);

        setupSpinners(projectStatusSpinner, paymentStatusSpinner);
        setupFocusListeners();

        // Initialize ViewModels
        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);
        ventanaViewModel = new ViewModelProvider(this).get(VentanaViewModel.class);

        // Get project ID from arguments
        if (getArguments() != null) {
            currentProjectId = getArguments().getLong("projectId");
        }

        if (currentProjectId != -1) {
            // Observe Project Details
            projectViewModel.getProjectDetailsById(currentProjectId).observe(getViewLifecycleOwner(), projectDetails -> {
                if (projectDetails != null) {
                    clientName.setText(projectDetails.getClientName());
                    deliveryAddressTextView.setText(projectDetails.getDeliveryAddress());
                    startDateTextView.setText(projectDetails.getStartDate());
                    setSpinnerToValue(projectStatusSpinner, projectDetails.getProjectStatus());
                    setSpinnerToValue(paymentStatusSpinner, projectDetails.getPaymentStatus());
                    otherWindowsValueEditText.setText(projectDetails.getOtherWindowsValue());
                    otherWindows.setText(projectDetails.getOtherWindows());
                    depositTextView.setText(FormatUtils.formatCurrency(projectDetails.getDeposit()));

                    currentProject = new Project(projectDetails.getClientId(), projectDetails.getDeliveryAddress(), projectDetails.getStartDate(), projectDetails.getProjectStatus(), projectDetails.getPaymentStatus(), projectDetails.getDeposit(), projectDetails.getOtherWindows(), projectDetails.getOtherWindowsValue());
                    currentProject.setId(projectDetails.getId());
                    updateTotals(); // Update totals when project details are loaded
                }
            });

            // Set the project ID in the VentanaViewModel
            ventanaViewModel.setProjectId(currentProjectId);
            
            // Observe Windows to calculate totals
            ventanaViewModel.getWindowsForProject().observe(getViewLifecycleOwner(), ventanas -> {
                currentWindows = ventanas;
                ventanaAdapter.submitList(ventanas);
                updateTotals(); // Update totals when window list changes
            });
        }

        // Listeners
        addWindowButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong("projectId", currentProjectId);
            Navigation.findNavController(v).navigate(R.id.action_project_data_to_quote, bundle);
        });

        saveButton.setOnClickListener(v -> {
            if (currentProject != null) {
                currentProject.setProjectStatus(projectStatusSpinner.getSelectedItem().toString());
                currentProject.setPaymentStatus(paymentStatusSpinner.getSelectedItem().toString());
                currentProject.setOtherWindowsValue(otherWindowsValueEditText.getText().toString());
                currentProject.setOtherWindows(otherWindows.getText().toString());

                projectViewModel.update(currentProject);
                Toast.makeText(getContext(), "Cambios guardados", Toast.LENGTH_SHORT).show();
            }
        });

        // Add listener to recalculate total when otherWindowsValue changes
        otherWindowsValueEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                updateTotals();
            }
        });

        return root;
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

        otherWindowsValueEditText.setOnFocusChangeListener(zeroingListener);
    }

    private void updateTotals(){
        double windowsTotal = 0;
        for (Ventana v : currentWindows) {
            try {
                windowsTotal += Double.parseDouble(v.getPrice());
            } catch (NumberFormatException e) { /* ignore */ }
        }

        double otherValue = 0;
        if (otherWindowsValueEditText != null && !otherWindowsValueEditText.getText().toString().isEmpty()) {
            try {
                otherValue = Double.parseDouble(otherWindowsValueEditText.getText().toString());
            } catch (NumberFormatException e) { /* ignore */ }
        }

        double total = windowsTotal + otherValue;
        totalProjectTextView.setText(FormatUtils.formatCurrency(String.valueOf(total)));

        double depositAmount = 0;
        if (currentProject != null && currentProject.getDeposit() != null && !currentProject.getDeposit().isEmpty()) {
            try {
                depositAmount = Double.parseDouble(currentProject.getDeposit());
            } catch (NumberFormatException e) { /* ignore */ }
        }

        double balanceAmount;
        String paymentStatus = paymentStatusSpinner.getSelectedItem().toString();
        if ("Pagado".equals(paymentStatus)) {
            balanceAmount = 0;
        } else {
            balanceAmount = total - depositAmount;
        }
        
        balanceTextView.setText(FormatUtils.formatCurrency(String.valueOf(balanceAmount)));
    }

    private void setupSpinners(Spinner projectStatusSpinner, Spinner paymentStatusSpinner) {
        if(getContext() == null) return;
        ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new String[]{"En espera de confirmación", "En fabricación", "Entregado"});
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projectStatusSpinner.setAdapter(projectAdapter);

        final ArrayAdapter<String> paymentAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new String[]{"Sin pagar", "Abonado", "Pagado"});
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentStatusSpinner.setAdapter(paymentAdapter);

        paymentStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private String previousState = "";

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStatus = parent.getItemAtPosition(position).toString();
                
                if ("Sin pagar".equals(selectedStatus)) {
                    double depositAmount = 0;
                    if (currentProject != null && currentProject.getDeposit() != null && !currentProject.getDeposit().isEmpty()) {
                        try {
                            depositAmount = Double.parseDouble(currentProject.getDeposit());
                        } catch (NumberFormatException e) { /* ignore */ }
                    }

                    if (depositAmount > 0) {
                        Toast.makeText(getContext(), "No se puede seleccionar 'Sin pagar' si ya existe un abono.", Toast.LENGTH_SHORT).show();
                        int lastValidPosition = paymentAdapter.getPosition(previousState);
                        paymentStatusSpinner.setSelection(lastValidPosition > -1 ? lastValidPosition : 0);
                        return;
                    }
                }
                previousState = selectedStatus;
                updateTotals();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setSpinnerToValue(Spinner spinner, String value) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).toString().equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void onItemClick(Ventana ventana) {
        Bundle bundle = new Bundle();
        bundle.putInt("windowId", ventana.getId());
        Navigation.findNavController(requireView()).navigate(R.id.action_project_data_to_window_data, bundle);
    }
}

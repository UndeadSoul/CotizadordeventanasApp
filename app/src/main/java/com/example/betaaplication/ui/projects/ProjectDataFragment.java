package com.example.betaaplication.ui.projects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.betaaplication.R;

public class ProjectDataFragment extends Fragment {

    private ProjectViewModel projectViewModel;
    private TextView clientNameTextView, startDateTextView, statusTextView, addressTextView, addedValueTextView, notesTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_project_data, container, false);

        // Initialize Views
        clientNameTextView = root.findViewById(R.id.text_project_client_name);
        startDateTextView = root.findViewById(R.id.text_project_start_date);
        statusTextView = root.findViewById(R.id.text_project_status);
        addressTextView = root.findViewById(R.id.text_project_address);
        addedValueTextView = root.findViewById(R.id.text_project_added_value);
        notesTextView = root.findViewById(R.id.text_project_notes);

        // Initialize ViewModel
        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);

        // Get project ID from arguments and observe data
        if (getArguments() != null) {
            int projectId = getArguments().getInt("projectId");
            projectViewModel.getProjectDetailsById(projectId).observe(getViewLifecycleOwner(), projectDetails -> {
                if (projectDetails != null) {
                    clientNameTextView.setText(projectDetails.clientName);
                    startDateTextView.setText(projectDetails.startDate);
                    statusTextView.setText(projectDetails.status);
                    addressTextView.setText(projectDetails.deliveryAddress);
                    addedValueTextView.setText(projectDetails.addedValue);
                    notesTextView.setText(projectDetails.notes);
                }
            });
        }

        return root;
    }
}

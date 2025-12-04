package com.example.betaaplication.ui.clients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.betaaplication.R;
import com.example.betaaplication.ui.projects.ProjectViewModel;

public class ClientDataFragment extends Fragment implements ClientProjectsAdapter.OnProjectClickListener {

    private ClientViewModel clientViewModel;
    private ProjectViewModel projectViewModel;
    private TextView nameTextView, phoneTextView, addressTextView;
    private ClientProjectsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_client_data, container, false);

        // Initialize Views
        nameTextView = root.findViewById(R.id.text_client_name);
        phoneTextView = root.findViewById(R.id.text_client_phone);
        addressTextView = root.findViewById(R.id.text_client_address);

        // Setup RecyclerView for projects
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view_client_projects);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ClientProjectsAdapter(this);
        recyclerView.setAdapter(adapter);

        // Initialize ViewModels
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);

        // Get client ID from arguments and observe data
        if (getArguments() != null) {
            int clientId = getArguments().getInt("clientId");

            // Observe client details
            clientViewModel.getClientById(clientId).observe(getViewLifecycleOwner(), client -> {
                if (client != null) {
                    nameTextView.setText(client.getName());
                    phoneTextView.setText(client.getPhone());
                    addressTextView.setText(client.getAddress());
                }
            });

            // Observe projects for this client
            projectViewModel.getProjectsForClient(clientId).observe(getViewLifecycleOwner(), projects -> {
                adapter.setProjects(projects);
            });
        }

        return root;
    }

    @Override
    public void onProjectClick(int projectId) {
        Bundle bundle = new Bundle();
        bundle.putInt("projectId", projectId);
        Navigation.findNavController(requireView()).navigate(R.id.action_client_data_to_project_data, bundle);
    }
}

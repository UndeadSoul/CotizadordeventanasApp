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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_client_data, container, false);

        // Initialize Views
        TextView nameTextView = root.findViewById(R.id.value_client_name);
        TextView phoneTextView = root.findViewById(R.id.value_client_phone);
        TextView addressTextView = root.findViewById(R.id.value_client_address);
        TextView rutTextView = root.findViewById(R.id.value_client_rut);

        // Setup RecyclerView for projects
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view_client_projects);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ClientProjectsAdapter adapter = new ClientProjectsAdapter(this);
        recyclerView.setAdapter(adapter);

        // Initialize ViewModels
        ClientViewModel clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        ProjectViewModel projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);

        // Get client ID from arguments and observe data
        if (getArguments() != null) {
            int clientId = getArguments().getInt("clientId");

            // Observe client details
            clientViewModel.getClientById(clientId).observe(getViewLifecycleOwner(), client -> {
                if (client != null) {
                    nameTextView.setText(client.getName());
                    phoneTextView.setText(client.getPhone());
                    addressTextView.setText(client.getAddress());
                    rutTextView.setText(client.getRut());
                }
            });

            // Observe projects for this client using the NEW method
            projectViewModel.getClientProjectListItems(clientId).observe(getViewLifecycleOwner(), clientProjectListItems -> {
                adapter.setProjects(clientProjectListItems);
            });
        }

        return root;
    }

    @Override
    public void onProjectClick(long projectId) { // Changed to long
        Bundle bundle = new Bundle();
        bundle.putLong("projectId", projectId); // Changed to putLong
        Navigation.findNavController(requireView()).navigate(R.id.action_client_data_to_project_data, bundle);
    }
}

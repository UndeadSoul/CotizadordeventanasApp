package com.example.betaaplication.ui.clients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.betaaplication.Client;
import com.example.betaaplication.R;

public class ClientsFragment extends Fragment implements ClientsAdapter.OnClientClickListener {

    private ClientViewModel clientViewModel;
    private ClientsAdapter clientsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_clients, container, false);

        // Setup RecyclerView
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view_clients);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        clientsAdapter = new ClientsAdapter(this);
        recyclerView.setAdapter(clientsAdapter);

        // Setup ViewModel and observe LiveData
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        clientViewModel.getAllClients().observe(getViewLifecycleOwner(), clients -> {
            // Update the cached copy of the clients in the adapter.
            clientsAdapter.setClients(clients);
        });

        // Handle the add button click to navigate to the new client screen
        root.findViewById(R.id.bt_clientnew).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_clients_to_new_client);
        });

        return root;
    }

    @Override
    public void onClientClick(Client client) {
        Bundle bundle = new Bundle();
        bundle.putInt("clientId", client.getId());
        Navigation.findNavController(requireView()).navigate(R.id.action_clients_to_client_data, bundle);
    }
}

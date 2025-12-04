package com.example.betaaplication.ui.clients;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.betaaplication.Client;
import com.example.betaaplication.R;

public class ClientNewFragment extends Fragment {

    private ClientViewModel clientViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_client_new, container, false);

        // Initialize ViewModel
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);

        EditText nameEditText = root.findViewById(R.id.edit_text_name);
        EditText phoneEditText = root.findViewById(R.id.edit_text_phone);
        EditText addressEditText = root.findViewById(R.id.edit_text_address);
        Button saveButton = root.findViewById(R.id.button_save);

        saveButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();
            String address = addressEditText.getText().toString().trim();

            // Validate that all fields are filled
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)) {
                Toast.makeText(getContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                // Create a new client and insert it into the database
                Client newClient = new Client(name, phone, address);
                clientViewModel.insert(newClient);

                // After saving, navigate back to the client list
                getParentFragmentManager().popBackStack();
            }
        });

        return root;
    }
}

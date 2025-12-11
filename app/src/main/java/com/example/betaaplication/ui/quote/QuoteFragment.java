package com.example.betaaplication.ui.quote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.betaaplication.R;
import com.example.betaaplication.databinding.FragmentQuoteBinding;

public class QuoteFragment extends Fragment {

    private FragmentQuoteBinding binding;
    private long projectId = -1L; // Default to -1 (not in project mode)

    // Constants for business logic
    private static final float ALTURA_LIMITE_L20_CM = 150.0f;
    private static final float DIMENSION_MINIMA_CM = 30.0f;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Correctly get the projectId as a long
            projectId = getArguments().getLong("projectId", -1L);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configurar el boton
        binding.btContinue.setOnClickListener(v -> {
            String inputValueH = binding.editTextHeight.getText().toString();
            String inputValueW = binding.editTextWidth.getText().toString();

            if (!inputValueW.isEmpty() && !inputValueH.isEmpty()){
                float valueH = Float.parseFloat(inputValueH);
                float valueW = Float.parseFloat(inputValueW);

                if (valueH <= DIMENSION_MINIMA_CM || valueW <= DIMENSION_MINIMA_CM){
                    String message = String.format("Ingrese valores mayores a %.0f cm", DIMENSION_MINIMA_CM);
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("valueH", inputValueH);
                    bundle.putString("valueW", inputValueW);
                    // Correctly pass the projectId as a long
                    bundle.putLong("projectId", projectId); 

                    if (valueH < ALTURA_LIMITE_L20_CM){
                        NavHostFragment.findNavController(this).navigate(R.id.action_quoteFragment_to_newFragment20, bundle);
                    } else if (projectId != -1L) {
                        // Block navigation to L25 if in 'add to project' mode
                        Toast.makeText(getContext(), "El máximo de altura para agregar ventanas a un proyecto es 150cm.", Toast.LENGTH_LONG).show();
                    } else {
                        // Allow navigation to L25 only in normal quote mode
                        NavHostFragment.findNavController(this).navigate(R.id.action_quoteFragment_to_newFragment25, bundle);
                    }
                }
            }else{
                Toast.makeText(getContext(), "Por favor, ingrese ancho y alto", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

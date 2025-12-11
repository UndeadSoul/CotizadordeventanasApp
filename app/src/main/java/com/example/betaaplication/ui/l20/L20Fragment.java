package com.example.betaaplication.ui.l20;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.betaaplication.Aluminium;
import com.example.betaaplication.AppDatabase;
import com.example.betaaplication.Crystal;
import com.example.betaaplication.R;
import com.example.betaaplication.Ventana;
import com.example.betaaplication.WinAdds;
import com.example.betaaplication.databinding.FragmentL20Binding;
import com.example.betaaplication.ui.windows.VentanaViewModel;

import java.util.concurrent.Future;

public class L20Fragment extends Fragment {

    private FragmentL20Binding binding;
    private long projectId = -1L; // Use long for projectId
    private VentanaViewModel ventanaViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Correctly get projectId as a long
            projectId = getArguments().getLong("projectId", -1L);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentL20Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ventanaViewModel = new ViewModelProvider(this).get(VentanaViewModel.class);

        // Set default values from arguments
        String valueW = getArguments().getString("valueW", "0");
        String valueH = getArguments().getString("valueH", "0");
        binding.tvWidth.setText(valueW + " cm");
        binding.tvHeight.setText(valueH + " cm");

        // Change button text if in project mode
        if (projectId != -1L) {
            binding.btCalc.setText("Guardar Ventana");
        }

        binding.btCalc.setOnClickListener(v -> {
            handleCalculationAndSave();
        });

        return root;
    }

    private void handleCalculationAndSave() {
        final Context context = getContext();
        if (context == null) return;

        // Get values from UI
        float width = Float.parseFloat(getArguments().getString("valueW", "0"));
        float height = Float.parseFloat(getArguments().getString("valueH", "0"));
        String alumColor = binding.spinner.getSelectedItem().toString();
        String crysType = binding.spinner2.getSelectedItem().toString();
        boolean install = binding.cbInstall.isChecked();
        boolean freight = binding.cbFreight.isChecked();
        float freightAmount = 0;
        if (freight && !binding.etFreightAmount.getText().toString().isEmpty()) {
            freightAmount = Float.parseFloat(binding.etFreightAmount.getText().toString());
        }

        final float finalFreightAmount = freightAmount;

        new Thread(() -> {
            Aluminium aluminium = new Aluminium();
            Crystal crystal = new Crystal();
            WinAdds winadds = new WinAdds();
            AppDatabase db = AppDatabase.getInstance(context.getApplicationContext());

            try {
                Future<Float> aluminiumCostF = aluminium.getAlumCost(width, height, alumColor, "20", context);
                Future<Float> crystalCostF = crystal.getCrystalCost(width, height, crysType, context);
                Future<Float> addsCostF = winadds.getAddsCost(width, height, context);

                float costekmflete = Float.parseFloat(db.daoData().getOneDataValue("costokmflete").get(0).toString());
                float other = (install ? 5000 : 0) + (freight ? finalFreightAmount * costekmflete : 0);

                float totalCost = ((aluminiumCostF.get() + crystalCostF.get() + addsCostF.get()) * 2) + other;
                int finalPrice = Math.round(totalCost);

                // Calculate market cost in background thread BEFORE posting to UI thread
                float preciomercadom2 = Float.parseFloat(db.daoData().getOneDataValue("preciomercadom2").get(0).toString());
                float costoMercado = (preciomercadom2 * (width / 100) * (height / 100)) + other;
                int finalCostoMercado = Math.round(costoMercado);

                new Handler(Looper.getMainLooper()).post(() -> {
                    if (projectId != -1L) {
                        // --- SAVE MODE ---
                        Ventana newVentana = new Ventana(projectId, String.valueOf(height), String.valueOf(width), "Linea 20", alumColor, crysType, String.valueOf(finalPrice));
                        ventanaViewModel.insert(newVentana);
                        Toast.makeText(context, "Ventana guardada en el proyecto", Toast.LENGTH_SHORT).show();
                        
                        // Navigate back twice to return to the project screen
                        NavController navController = NavHostFragment.findNavController(L20Fragment.this);
                        navController.popBackStack(); // Pops L20Fragment
                        navController.popBackStack(); // Pops QuoteFragment, returning to the project screen
                    } else {
                        // --- QUOTE MODE ---
                        binding.tvResult.setText("Resultado: " + finalPrice + "\nCosto Mercado: " + finalCostoMercado);
                    }
                });

            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    binding.tvResult.setText(e.toString());
                });
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

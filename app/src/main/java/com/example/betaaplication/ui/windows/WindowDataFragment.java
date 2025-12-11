package com.example.betaaplication.ui.windows;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.betaaplication.FormatUtils;
import com.example.betaaplication.R;
import com.example.betaaplication.Ventana;

public class WindowDataFragment extends Fragment {

    private VentanaViewModel ventanaViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_window_data, container, false);

        // Initialize Views
        TextView dimensions = root.findViewById(R.id.value_window_dimensions);
        TextView type = root.findViewById(R.id.value_window_type);
        TextView aluminumColor = root.findViewById(R.id.value_aluminum_color);
        TextView crystalType = root.findViewById(R.id.value_crystal_type);
        TextView price = root.findViewById(R.id.value_window_price);
        CheckBox materialCut = root.findViewById(R.id.checkbox_material_cut);
        CheckBox glassCut = root.findViewById(R.id.checkbox_glass_cut);
        TextView cuttingSheet = root.findViewById(R.id.value_cutting_sheet_details);
        TextView glassSize = root.findViewById(R.id.value_glass_size_details);

        // Initialize ViewModel
        ventanaViewModel = new ViewModelProvider(this).get(VentanaViewModel.class);

        // Get window ID and observe data
        if (getArguments() != null) {
            int windowId = getArguments().getInt("windowId", -1);
            if (windowId != -1) {
                ventanaViewModel.getWindowById(windowId).observe(getViewLifecycleOwner(), ventana -> {
                    if (ventana != null) {
                        // Populate basic data
                        String dims = ventana.getWidth() + "cm x " + ventana.getHeight() + "cm";
                        dimensions.setText(dims);

                        String windowType = "Corredera " + ventana.getLine();
                        type.setText(windowType);

                        aluminumColor.setText(ventana.getColor());
                        crystalType.setText(ventana.getCrystal());
                        price.setText(FormatUtils.formatCurrency(ventana.getPrice()));

                        // Set checkbox status and add listeners
                        materialCut.setChecked(ventana.isMaterialCut());
                        glassCut.setChecked(ventana.isGlassCut());

                        materialCut.setOnClickListener(v -> {
                            ventana.setMaterialCut(materialCut.isChecked());
                            ventanaViewModel.update(ventana);
                        });

                        glassCut.setOnClickListener(v -> {
                            ventana.setGlassCut(glassCut.isChecked());
                            ventanaViewModel.update(ventana);
                        });

                        // Calculate and display cutting sheet and glass size
                        cuttingSheet.setText(calculateCuttingSheet(ventana));
                        glassSize.setText(calculateGlassSize(ventana));
                    }
                });
            }
        }

        return root;
    }

    private String calculateCuttingSheet(Ventana ventana) {
        try {
            float width = Float.parseFloat(ventana.getWidth());
            float height = Float.parseFloat(ventana.getHeight());
            String line = ventana.getLine();

            if ("Linea 20".equals(line)) {
                float rielSup = width;
                float rielInf = width;
                float zocalo = (width / 2) + 1.5f;
                float cabezal = (width / 2) + 1.5f;
                float batiente = height - 3.4f;
                float traslapo = height - 3.4f;
                float jamba = height;

                return String.format("%-15s %s cm (1 unidad)\n", "Riel Superior:", rielSup) +
                       String.format("%-15s %s cm (1 unidad)\n", "Riel Inferior:", rielInf) +
                       String.format("%-15s %s cm (2 unidades)\n", "Zócalo:", zocalo) +
                       String.format("%-15s %s cm (2 unidades)\n", "Cabezal:", cabezal) +
                       String.format("%-15s %s cm (2 unidades)\n", "Batiente:", batiente) +
                       String.format("%-15s %s cm (2 unidades)\n", "Traslapo:", traslapo) +
                       String.format("%-15s %s cm (2 unidades)", "Jamba:", jamba);

            } else {
                return "Hoja de corte para esta línea no implementada.";
            }
        } catch (NumberFormatException e) {
            return "Error: Dimensiones de la ventana no válidas.";
        }
    }

    private String calculateGlassSize(Ventana ventana) {
        try {
            float width = Float.parseFloat(ventana.getWidth());
            float height = Float.parseFloat(ventana.getHeight());
            String line = ventana.getLine();

            if ("Linea 20".equals(line)) {
                float glassWidth = (width / 2) - 0.5f;
                float glassHeight = height - 4.5f;
                
                return String.format("%.1fcm x %.1fcm (2 unidades)", glassWidth, glassHeight);
            } else {
                return "Cálculo de vidrio para esta línea no implementado.";
            }
        } catch (NumberFormatException e) {
            return "Error: Dimensiones de la ventana no válidas.";
        }
    }
}

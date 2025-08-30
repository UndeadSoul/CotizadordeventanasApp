package com.example.betaaplication.ui.l20;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.betaaplication.Aluminium;
import com.example.betaaplication.AppDatabase;
import com.example.betaaplication.Crystal;
import com.example.betaaplication.WinAdds;
import com.example.betaaplication.databinding.FragmentL20Binding;

import java.util.concurrent.Future;

public class L20Fragment extends Fragment {

    private FragmentL20Binding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentL20Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtén el valor pasado como argumento y mostrarlo
        if (getArguments() != null) {
            String valueW = getArguments().getString("valueW", "Valor por defecto");
            String valueH = getArguments().getString("valueH", "Valor por defecto");
            String valueWcm=valueW+" cm";
            String valueHcm=valueH+" cm";
            binding.tvWidth.setText(valueWcm);
            binding.tvHeight.setText(valueHcm);
        }
        // Accion al precionar el boton
        binding.btCalc.setOnClickListener(v -> {
            //obtener valores del hilo principal
            float width = Float.parseFloat(getArguments().getString("valueW", "100"));
            float height = Float.parseFloat(getArguments().getString("valueH", "100"));
            String varAlumColor = binding.spinner.getSelectedItem().toString();
            String valCrysType = binding.spinner2.getSelectedItem().toString();
            Boolean valInstallBool = binding.cbInstall.isChecked();
            Boolean valFreightBool = binding.cbFreight.isChecked();
            float[] valFreightAmount = new float[1];
            if (valFreightBool && !binding.etFreightAmount.getText().toString().isEmpty()){
                valFreightAmount[0] = Float.parseFloat(binding.etFreightAmount.getText().toString());
                //Toast.makeText(requireContext(), "Ambos seleccionados"+valFreightBool+valFreightAmount, Toast.LENGTH_SHORT).show();
            }else {
                valFreightAmount[0]= 0;
            }
            String line = "20"; // Ejemplo de valor fijo para la línea
            //Verificar los valores ingresados Toast.makeText(requireContext(), "Seleccionado: "+width+height+varAlumColor+valCrysType+valInstallBool+valFreightBool+valFreightAmount[0], Toast.LENGTH_SHORT).show();

            new Thread(()->{
                Aluminium aluminium = new Aluminium();
                Crystal crystal = new Crystal();
                WinAdds winadds = new WinAdds();
                AppDatabase db = AppDatabase.getInstance(getContext());

                try {

                    //usar los valores de los arreglos para llamar a los métodos
                    Future<Float> aluminiumCost= aluminium.getAlumCost(width,height,varAlumColor,line,requireContext());
                    Future<Float> crystalCost= crystal.getCrystalCost(width,height,valCrysType,requireContext());
                    Future<Float> addsCost= winadds.getAddsCost(width,height,requireContext());
                    // Obtener el coste de los agregados
                    float costekmflete = Float.parseFloat(db.daoData().getOneDataValue("costokmflete").get(0).toString());
                    float other = 0;
                    if (valInstallBool){
                        other = other + 5000;
                    }else {
                        other = other + 0;
                    }
                    if (valFreightBool){
                        other = other + valFreightAmount[0]*costekmflete;
                    }else{
                        other = other + 0;
                    }
                    // obtener el valor del precio de mercado
                    float preciomercadom2 = Float.parseFloat(db.daoData().getOneDataValue("preciomercadom2").get(0).toString());
                    float costoMercado = (preciomercadom2*(width/100)*(height/100))+other;

                    float totalCost= ((aluminiumCost.get()+crystalCost.get()+addsCost.get())*2+other);
                    //Actualizar la ui en el hilo principal
                    new Handler(Looper.getMainLooper()).post(()->{
                        //Asignar resultado al ui
                        binding.tvResult.setText("Resultado: "+Math.round(totalCost)+"\nCosto Mercado: "+Math.round(costoMercado));
                    });

                } catch (Exception e){
                    new Handler(Looper.getMainLooper()).post(()->{
                        binding.tvResult.setText(e.toString());

                    });
                    e.printStackTrace();
                }
            }).start();
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
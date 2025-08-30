package com.example.betaaplication.ui.settings;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.betaaplication.AppDatabase;
import com.example.betaaplication.Data;
import com.example.betaaplication.R;
import com.example.betaaplication.databinding.FragmentHomeBinding;
import com.example.betaaplication.databinding.FragmentSettingsBinding;

import java.util.List;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //asignar los valores desde la base de datos
        assignFromDb(binding);
        //configurar boton
        binding.btSave.setOnClickListener(v -> {
            //5
            String valuepcbl20 = binding.colorEtBlancoPc.getText().toString();
            String valuepcbr20 = binding.colorEtBroncePc.getText().toString();
            String valuepcmad20 = binding.colorEtMaderaPc.getText().toString();
            String valuepcmat20 = binding.colorEtMatePc.getText().toString();
            String valuepct20 = binding.colorEtTitaneoPc.getText().toString();
            //7
            String valuekgmrs20 = binding.rsEtKgm.getText().toString();
            String valuekgmri20 = binding.riEtKgm.getText().toString();
            String valuekgmzo20 = binding.jmEtKgm.getText().toString();
            String valuekgmca20 = binding.caEtKgm.getText().toString();
            String valuekgmba20 = binding.zoEtKgm.getText().toString();
            String valuekgmtr20 = binding.tpEtKgm.getText().toString();
            String valuekgmja20 = binding.bteEtKgm.getText().toString();
            //7
            String valuem2plancha = binding.vidrioEtM2plancha.getText().toString();
            String valuem2ppbr4 = binding.vidrioEtBronce4Precioplancha.getText().toString();
            String valuem2ppbr5 = binding.vidrioEtBronce5Precioplancha.getText().toString();
            String valuem2ppinc4 = binding.vidrioEtInc4Precioplancha.getText().toString();
            String valuem2ppinc5 = binding.vidrioEtInc5Precioplancha.getText().toString();
            String valuem2ppsol4 = binding.vidrioEtSolar4Precioplancha.getText().toString();
            String valuem2ppsol5 = binding.vidrioEtSolar5Precioplancha.getText().toString();
            //6
            String valuemrf55 = binding.otrosEtFelpa5x5Mrollo.getText().toString();
            String valueprf55 = binding.otrosEtFelpa5x5Preciorollo.getText().toString();
            String valuemrb302 = binding.otrosEtBurlete302Mrollo.getText().toString();
            String valueprb302 = binding.otrosEtBurlete302Preciorollo.getText().toString();
            String valuemrb506 = binding.otrosEtBurlete506Mrollo.getText().toString();
            String valueprb506 = binding.otrosEtBurlete506Preciorollo.getText().toString();
            //11
            String valuecppp20 = binding.otrosEtPestillos20Cantpack.getText().toString();
            String valueppp20 = binding.otrosEtPestillos20Preciopack.getText().toString();
            String valuecppc20 = binding.otrosEtCaracol20Cantpack.getText().toString();
            String valueppc20 = binding.otrosEtCaracol20Preciopack.getText().toString();
            String valuecppr20 = binding.otrosEtRod20Cantpack.getText().toString();
            String valueppr20 = binding.otrosEtRod20Preciopack.getText().toString();
            String valuecpptor = binding.otrosEtTornillosCantpack.getText().toString();
            String valuepptor = binding.otrosEtTornillosPreciopack.getText().toString();
            String valuecppsil = binding.otrosEtSiliconaCantpack.getText().toString();
            String valueppsil = binding.otrosEtSiliconaPreciopack.getText().toString();
            String valueckmflete = binding.otrosEtFleteCostokm.getText().toString();
            String valuepreciomercado = binding.otrosEtPreciomercado.getText().toString();

            if (valuepreciomercado.isEmpty() || valuepcbl20.isEmpty() || valuepcbr20.isEmpty() || valuepcmad20.isEmpty() || valuepcmat20.isEmpty() || valuepct20.isEmpty() || valuekgmrs20.isEmpty() || valuekgmri20.isEmpty() || valuekgmzo20.isEmpty() || valuekgmca20.isEmpty() || valuekgmba20.isEmpty() || valuekgmtr20.isEmpty() || valuekgmja20.isEmpty() || valuem2plancha.isEmpty() || valuem2ppbr4.isEmpty() || valuem2ppbr5.isEmpty() || valuem2ppinc4.isEmpty() || valuem2ppinc5.isEmpty() || valuem2ppsol4.isEmpty() || valuem2ppsol5.isEmpty() || valuemrf55.isEmpty() || valueprf55.isEmpty() || valuemrb302.isEmpty() || valueprb302.isEmpty() || valuemrb506.isEmpty() || valueprb506.isEmpty() || valuecppp20.isEmpty() || valueppp20.isEmpty() || valuecppc20.isEmpty() || valueppc20.isEmpty() || valuecppr20.isEmpty() || valueppr20.isEmpty() || valuecpptor.isEmpty() || valuepptor.isEmpty() || valuecppsil.isEmpty() || valueppsil.isEmpty() || valueckmflete.isEmpty()) {
                Toast.makeText(requireContext(), "Ninguno de los campos puede estar vacío", Toast.LENGTH_SHORT).show();
                return;
            }
            new Thread(()->{
                AppDatabase db = AppDatabase.getInstance(requireContext());
                db.daoData().updateData("preciolinea20blanco", valuepcbl20);
                db.daoData().updateData("preciolinea20bronce", valuepcbr20);
                db.daoData().updateData("preciolinea20madera", valuepcmad20);
                db.daoData().updateData("preciolinea20mate", valuepcmat20);
                db.daoData().updateData("preciolinea20titaneo", valuepct20);
                db.daoData().updateData("kgmetrorielsup20", valuekgmrs20);
                db.daoData().updateData("kgmetrorielinf20", valuekgmri20);
                db.daoData().updateData("kgmetrozocalo20", valuekgmzo20);
                db.daoData().updateData("kgmetrocabezal20", valuekgmca20);
                db.daoData().updateData("kgmetrobatiente20", valuekgmba20);
                db.daoData().updateData("kgmetrotraslapo20", valuekgmtr20);
                db.daoData().updateData("kgmetrojamba20", valuekgmja20);
                db.daoData().updateData("m2plancha", valuem2plancha);
                db.daoData().updateData("precioplanchabronce4", valuem2ppbr4);
                db.daoData().updateData("precioplanchabronce5", valuem2ppbr5);
                db.daoData().updateData("precioplanchainc4", valuem2ppinc4);
                db.daoData().updateData("precioplanchainc5", valuem2ppinc5);
                db.daoData().updateData("precioplanchasolar4", valuem2ppsol4);
                db.daoData().updateData("precioplanchasolar5", valuem2ppsol5);
                db.daoData().updateData("mrollofelpa55", valuemrf55);
                db.daoData().updateData("preciorollofelpa55", valueprf55);
                db.daoData().updateData("mrolloburlete302", valuemrb302);
                db.daoData().updateData("preciorolloburlete302", valueprb302);
                db.daoData().updateData("mrolloburlete506", valuemrb506);
                db.daoData().updateData("preciorolloburlete506", valueprb506);
                db.daoData().updateData("preciopackpestillo20", valueppp20);
                db.daoData().updateData("cantporpackpestillo20", valuecppp20);
                db.daoData().updateData("preciopackcaracol20", valueppc20);
                db.daoData().updateData("cantporpackcaracol20", valuecppc20);
                db.daoData().updateData("preciopackrod20", valueppr20);
                db.daoData().updateData("cantporpackrod20", valuecppr20);
                db.daoData().updateData("preciopacktornillo", valuepptor);
                db.daoData().updateData("cantporpacktornillo", valuecpptor);
                db.daoData().updateData("preciopacksilicona", valueppsil);
                db.daoData().updateData("cantporpacksilicona", valuecppsil);
                db.daoData().updateData("costokmflete", valueckmflete);
                db.daoData().updateData("preciomercadom2", valuepreciomercado);
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Guardado correctamente", Toast.LENGTH_SHORT).show()
                );
            }).start();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void assignFromDb(FragmentSettingsBinding binding){
        if (getContext() == null) return;
        AppDatabase db = AppDatabase.getInstance(requireContext());
        new Thread(() -> {
            List<Data> dataList = db.daoData().getDataValue();
            String valuepcbl20 = dataList.get(0).value;
            String valuepcbr20 = dataList.get(1).value;
            String valuepcmad20 = dataList.get(2).value;
            String valuepcmat20 = dataList.get(3).value;
            String valuepct20 = dataList.get(4).value;
            //
            String valuekgmrs20 = dataList.get(5).value;
            String valuekgmri20 = dataList.get(6).value;
            String valuekgmzo20 = dataList.get(7).value;
            String valuekgmca20 = dataList.get(8).value;
            String valuekgmba20 = dataList.get(9).value;
            String valuekgmtr20 = dataList.get(10).value;
            String valuekgmja20 = dataList.get(11).value;
            //
            String valuem2plancha = dataList.get(12).value;
            //
            String valuem2ppbr4 = dataList.get(13).value;
            String valuem2ppbr5 = dataList.get(14).value;
            String valuem2ppinc4 = dataList.get(15).value;
            String valuem2ppinc5 = dataList.get(16).value;
            String valuem2ppsol4 = dataList.get(17).value;
            String valuem2ppsol5 = dataList.get(18).value;
            //
            String valuemrf55 = dataList.get(19).value;
            String valueprf55 = dataList.get(20).value;
            String valuemrb302 = dataList.get(21).value;
            String valueprb302 = dataList.get(22).value;
            String valuemrb506 = dataList.get(23).value;
            String valueprb506 = dataList.get(24).value;
            //
            String valueppp20 = dataList.get(25).value;
            String valuecppp20 = dataList.get(26).value;
            String valueppc20 = dataList.get(27).value;
            String valuecppc20 = dataList.get(28).value;
            String valueppr20 = dataList.get(29).value;
            String valuecppr20 = dataList.get(30).value;
            String valuepptor = dataList.get(31).value;
            String valuecpptor = dataList.get(32).value;
            String valueppsil = dataList.get(33).value;
            String valuecppsil = dataList.get(34).value;
            String valueckmflete = dataList.get(35).value;
            String valuepreciomercado = dataList.get(36).value;

            requireActivity().runOnUiThread(()->{
                //5
                binding.colorEtBlancoPc.setText(valuepcbl20);
                binding.colorEtBroncePc.setText(valuepcbr20);
                binding.colorEtMaderaPc.setText(valuepcmad20);
                binding.colorEtMatePc.setText(valuepcmat20);
                binding.colorEtTitaneoPc.setText(valuepct20);
                //7
                binding.rsEtKgm.setText(valuekgmrs20);
                binding.riEtKgm.setText(valuekgmri20);
                binding.jmEtKgm.setText(valuekgmzo20);
                binding.caEtKgm.setText(valuekgmca20);
                binding.zoEtKgm.setText(valuekgmba20);
                binding.tpEtKgm.setText(valuekgmtr20);
                binding.bteEtKgm.setText(valuekgmja20);
                //7
                binding.vidrioEtM2plancha.setText(valuem2plancha);
                binding.vidrioEtBronce4Precioplancha.setText(valuem2ppbr4);
                binding.vidrioEtBronce5Precioplancha.setText(valuem2ppbr5);
                binding.vidrioEtInc4Precioplancha.setText(valuem2ppinc4);
                binding.vidrioEtInc5Precioplancha.setText(valuem2ppinc5);
                binding.vidrioEtSolar4Precioplancha.setText(valuem2ppsol4);
                binding.vidrioEtSolar5Precioplancha.setText(valuem2ppsol5);
                //6
                binding.otrosEtFelpa5x5Mrollo.setText(valuemrf55);
                binding.otrosEtFelpa5x5Preciorollo.setText(valueprf55);
                binding.otrosEtBurlete302Mrollo.setText(valuemrb302);
                binding.otrosEtBurlete302Preciorollo.setText(valueprb302);
                binding.otrosEtBurlete506Mrollo.setText(valuemrb506);
                binding.otrosEtBurlete506Preciorollo.setText(valueprb506);
                //11
                binding.otrosEtPestillos20Cantpack.setText(valuecppp20);
                binding.otrosEtPestillos20Preciopack.setText(valueppp20);
                binding.otrosEtCaracol20Cantpack.setText(valuecppc20);
                binding.otrosEtCaracol20Preciopack.setText(valueppc20);
                binding.otrosEtRod20Cantpack.setText(valuecppr20);
                binding.otrosEtRod20Preciopack.setText(valueppr20);
                binding.otrosEtTornillosCantpack.setText(valuecpptor);
                binding.otrosEtTornillosPreciopack.setText(valuepptor);
                binding.otrosEtSiliconaCantpack.setText(valuecppsil);
                binding.otrosEtSiliconaPreciopack.setText(valueppsil);
                binding.otrosEtFleteCostokm.setText(valueckmflete);
                binding.otrosEtPreciomercado.setText(valuepreciomercado);

            });
        }).start();

    }
}
package com.example.betaaplication.ui.quote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.betaaplication.R;
import com.example.betaaplication.databinding.FragmentQuoteBinding;

public class QuoteFragment extends Fragment {

    private FragmentQuoteBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        float alturaLimiteCm=Float.parseFloat("150");
        float alturaMinimaCm=Float.parseFloat("30");
        float anchoMinimoCm=Float.parseFloat("30");
        // Configurar el boton
        binding.btContinue.setOnClickListener(v -> {
            //obtener el valor del texto de alto y ancho
            String inputValueH = binding.editTextHeight.getText().toString();
            String inputValueW = binding.editTextWidth.getText().toString();
            //Verificar que no estén vacíos
            if (!inputValueW.isEmpty() && !inputValueH.isEmpty()){
                //verificar si la altura es de linea 20 o 25
                float valueH=Float.parseFloat(inputValueH);
                float valueW=Float.parseFloat(inputValueW);
                // Usa Navigation Component para navegar al nuevo fragmento
                Bundle bundle = new Bundle();
                bundle.putString("valueH", inputValueH);
                bundle.putString("valueW", inputValueW);
                if (valueH<=alturaMinimaCm || valueW<=anchoMinimoCm){
                    Toast.makeText(getContext(), "Ingrese valores mayores a 30cm", Toast.LENGTH_SHORT).show();
                }else{
                    if (valueH<alturaLimiteCm){
                        //redirigir a linea 20 y pasar valores
                        NavHostFragment.findNavController(this).navigate(R.id.action_quoteFragment_to_newFragment20, bundle);
                    }else{
                        //redirigir a linea 25 y pasar valores
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
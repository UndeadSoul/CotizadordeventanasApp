package com.example.betaaplication.ui.windows;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.betaaplication.R;
import com.example.betaaplication.Ventana;

import java.util.ArrayList;
import java.util.List;

public class VentanaAdapter extends RecyclerView.Adapter<VentanaAdapter.VentanaViewHolder> {

    private List<Ventana> ventanaList = new ArrayList<>();
    // Listener for future use (e.g., editing a window)
    private OnVentanaClickListener listener;

    public interface OnVentanaClickListener {
        void onVentanaClick(Ventana ventana);
    }

    public VentanaAdapter(OnVentanaClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public VentanaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.window_list_item, parent, false);
        return new VentanaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VentanaViewHolder holder, int position) {
        Ventana currentVentana = ventanaList.get(position);
        String details = String.format("Ventana %sx%s - %s", currentVentana.getHeight(), currentVentana.getWidth(), currentVentana.getLine());
        String price = String.format("$ %s", currentVentana.getPrice());
        holder.detailsTextView.setText(details);
        holder.priceTextView.setText(price);
    }

    @Override
    public int getItemCount() {
        return ventanaList.size();
    }

    public void setVentanas(List<Ventana> ventanas) {
        this.ventanaList = ventanas;
        notifyDataSetChanged();
    }

    class VentanaViewHolder extends RecyclerView.ViewHolder {
        private final TextView detailsTextView;
        private final TextView priceTextView;

        public VentanaViewHolder(@NonNull View itemView) {
            super(itemView);
            detailsTextView = itemView.findViewById(R.id.text_view_window_details);
            priceTextView = itemView.findViewById(R.id.text_view_window_price);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onVentanaClick(ventanaList.get(position));
                }
            });
        }
    }
}

package com.example.betaaplication.ui.windows;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.betaaplication.R;
import com.example.betaaplication.Ventana;

public class VentanasAdapter extends ListAdapter<Ventana, VentanasAdapter.VentanaViewHolder> {

    private OnItemClickListener listener;

    public VentanasAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Ventana> DIFF_CALLBACK = new DiffUtil.ItemCallback<Ventana>() {
        @Override
        public boolean areItemsTheSame(@NonNull Ventana oldItem, @NonNull Ventana newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Ventana oldItem, @NonNull Ventana newItem) {
            return oldItem.getHeight().equals(newItem.getHeight()) &&
                   oldItem.getWidth().equals(newItem.getWidth()) &&
                   oldItem.getPrice().equals(newItem.getPrice());
        }
    };

    @NonNull
    @Override
    public VentanaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.window_list_item, parent, false);
        return new VentanaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VentanaViewHolder holder, int position) {
        Ventana currentVentana = getItem(position);
        String details = "Ventana " + currentVentana.getHeight() + "x" + currentVentana.getWidth() + " - " + currentVentana.getLine();
        holder.detailsTextView.setText(details);
        holder.priceTextView.setText("$ " + currentVentana.getPrice());
    }

    public Ventana getVentanaAt(int position) {
        return getItem(position);
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
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Ventana ventana);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

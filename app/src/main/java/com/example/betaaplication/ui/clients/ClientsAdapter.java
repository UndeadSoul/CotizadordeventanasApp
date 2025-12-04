package com.example.betaaplication.ui.clients;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.betaaplication.Client;
import com.example.betaaplication.R;
import java.util.ArrayList;
import java.util.List;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.ClientViewHolder> {

    private List<Client> clientList = new ArrayList<>();
    private OnClientClickListener onClientClickListener;

    public interface OnClientClickListener {
        void onClientClick(Client client);
    }

    public ClientsAdapter(OnClientClickListener onClientClickListener) {
        this.onClientClickListener = onClientClickListener;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_list_item, parent, false);
        return new ClientViewHolder(view, onClientClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        Client currentClient = clientList.get(position);
        holder.clientNameTextView.setText(currentClient.getName());
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public void setClients(List<Client> clients) {
        this.clientList = clients;
        notifyDataSetChanged();
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView clientNameTextView;
        OnClientClickListener onClientClickListener;

        public ClientViewHolder(@NonNull View itemView, OnClientClickListener onClientClickListener) {
            super(itemView);
            clientNameTextView = itemView.findViewById(R.id.client_name);
            this.onClientClickListener = onClientClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onClientClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                onClientClickListener.onClientClick(clientList.get(getAdapterPosition()));
            }
        }
    }
}

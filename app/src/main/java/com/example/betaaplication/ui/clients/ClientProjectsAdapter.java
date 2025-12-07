package com.example.betaaplication.ui.clients;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.betaaplication.ClientProjectListItem;
import com.example.betaaplication.FormatUtils;
import com.example.betaaplication.R;
import java.util.ArrayList;
import java.util.List;

public class ClientProjectsAdapter extends RecyclerView.Adapter<ClientProjectsAdapter.ProjectViewHolder> {

    private List<ClientProjectListItem> projectList = new ArrayList<>();
    private OnProjectClickListener onProjectClickListener;

    public interface OnProjectClickListener {
        void onProjectClick(int projectId);
    }

    public ClientProjectsAdapter(OnProjectClickListener onProjectClickListener) {
        this.onProjectClickListener = onProjectClickListener;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_project_list_item, parent, false);
        return new ProjectViewHolder(view, onProjectClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        ClientProjectListItem currentProject = projectList.get(position);
        holder.dateTextView.setText(currentProject.startDate);
        holder.statusTextView.setText(currentProject.projectStatus);
        holder.totalPriceTextView.setText(FormatUtils.formatCurrency(currentProject.totalPrice));
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public void setProjects(List<ClientProjectListItem> projects) {
        this.projectList = projects;
        notifyDataSetChanged();
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView dateTextView;
        public TextView statusTextView;
        public TextView totalPriceTextView;
        OnProjectClickListener onProjectClickListener;

        public ProjectViewHolder(@NonNull View itemView, OnProjectClickListener onProjectClickListener) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.text_view_project_date);
            statusTextView = itemView.findViewById(R.id.text_view_project_status);
            totalPriceTextView = itemView.findViewById(R.id.text_view_total_price);
            this.onProjectClickListener = onProjectClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onProjectClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                onProjectClickListener.onProjectClick(projectList.get(getAdapterPosition()).projectId);
            }
        }
    }
}

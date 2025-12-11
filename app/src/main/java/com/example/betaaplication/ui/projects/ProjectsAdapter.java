package com.example.betaaplication.ui.projects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.betaaplication.FormatUtils;
import com.example.betaaplication.ProjectListItem;
import com.example.betaaplication.R;
import java.util.ArrayList;
import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectViewHolder> {

    private List<ProjectListItem> projectList = new ArrayList<>();
    private OnProjectClickListener onProjectClickListener;

    public interface OnProjectClickListener {
        void onProjectClick(long projectId); // Changed to long
    }

    public ProjectsAdapter(OnProjectClickListener onProjectClickListener) {
        this.onProjectClickListener = onProjectClickListener;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_list_item, parent, false);
        return new ProjectViewHolder(view, onProjectClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        ProjectListItem currentProject = projectList.get(position);
        holder.clientNameTextView.setText(currentProject.clientName);
        holder.statusTextView.setText(currentProject.projectStatus);
        holder.startDateTextView.setText(currentProject.startDate);
        holder.totalPriceTextView.setText(FormatUtils.formatCurrency(currentProject.totalPrice));
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public void setProjects(List<ProjectListItem> projects) {
        this.projectList = projects;
        notifyDataSetChanged();
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView clientNameTextView;
        public TextView statusTextView;
        public TextView totalPriceTextView;
        public TextView startDateTextView;
        OnProjectClickListener onProjectClickListener;

        public ProjectViewHolder(@NonNull View itemView, OnProjectClickListener onProjectClickListener) {
            super(itemView);
            clientNameTextView = itemView.findViewById(R.id.text_view_client_name);
            statusTextView = itemView.findViewById(R.id.text_view_project_status);
            totalPriceTextView = itemView.findViewById(R.id.text_view_total_price);
            startDateTextView = itemView.findViewById(R.id.text_view_start_date);
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

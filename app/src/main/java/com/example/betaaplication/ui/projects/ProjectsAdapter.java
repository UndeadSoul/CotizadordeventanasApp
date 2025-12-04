package com.example.betaaplication.ui.projects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.betaaplication.ProjectWithClientName;
import com.example.betaaplication.R;
import java.util.ArrayList;
import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectViewHolder> {

    private List<ProjectWithClientName> projectList = new ArrayList<>();
    private OnProjectClickListener onProjectClickListener;

    public interface OnProjectClickListener {
        void onProjectClick(int projectId);
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
        ProjectWithClientName currentProject = projectList.get(position);
        holder.clientNameTextView.setText(currentProject.clientName);
        holder.startDateTextView.setText(currentProject.startDate);
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public void setProjects(List<ProjectWithClientName> projects) {
        this.projectList = projects;
        notifyDataSetChanged();
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView clientNameTextView;
        public TextView startDateTextView;
        OnProjectClickListener onProjectClickListener;

        public ProjectViewHolder(@NonNull View itemView, OnProjectClickListener onProjectClickListener) {
            super(itemView);
            clientNameTextView = itemView.findViewById(R.id.text_view_client_name);
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

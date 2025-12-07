package com.example.betaaplication.ui.projects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.betaaplication.R;

public class ProjectsFragment extends Fragment implements ProjectsAdapter.OnProjectClickListener {

    private ProjectViewModel projectViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_projects, container, false);

        // Setup RecyclerView
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view_projects);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ProjectsAdapter projectsAdapter = new ProjectsAdapter(this);
        recyclerView.setAdapter(projectsAdapter);

        // Setup ViewModel and observe LiveData
        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);
        projectViewModel.getProjectListItems().observe(getViewLifecycleOwner(), projectListItems -> {
            // Update the cached copy of the projects in the adapter.
            projectsAdapter.setProjects(projectListItems);
        });

        // Handle the add button click to navigate to the new project screen
        root.findViewById(R.id.bt_projectnew).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_projects_to_new_project);
        });

        return root;
    }

    @Override
    public void onProjectClick(int projectId) {
        Bundle bundle = new Bundle();
        bundle.putInt("projectId", projectId);
        Navigation.findNavController(requireView()).navigate(R.id.action_projects_to_project_data, bundle);
    }
}

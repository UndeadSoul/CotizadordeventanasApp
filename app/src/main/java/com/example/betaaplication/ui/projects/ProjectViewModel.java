package com.example.betaaplication.ui.projects;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.betaaplication.Client;
import com.example.betaaplication.ClientRepository;
import com.example.betaaplication.Project;
import com.example.betaaplication.ProjectRepository;
import com.example.betaaplication.ProjectDetails;
import com.example.betaaplication.ProjectWithClientName;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel {

    private ProjectRepository projectRepository;
    private ClientRepository clientRepository;
    private LiveData<List<Project>> allProjects;
    private LiveData<List<Client>> allClients;
    private LiveData<List<ProjectWithClientName>> allProjectsWithClientNames;

    public ProjectViewModel(@NonNull Application application) {
        super(application);
        projectRepository = new ProjectRepository(application);
        clientRepository = new ClientRepository(application);
        allProjects = projectRepository.getAllProjects();
        allClients = clientRepository.getAllClients();
        allProjectsWithClientNames = projectRepository.getAllProjectsWithClientName();
    }

    public LiveData<List<ProjectWithClientName>> getAllProjectsWithClientNames() {
        return allProjectsWithClientNames;
    }

    public LiveData<ProjectDetails> getProjectDetailsById(int projectId) {
        return projectRepository.getProjectDetailsById(projectId);
    }

    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }

    public LiveData<List<Client>> getAllClients() {
        return allClients;
    }

    public void insert(Project project) {
        projectRepository.insert(project);
    }

    public LiveData<List<Project>> getProjectsForClient(int clientId) {
        return projectRepository.getProjectsForClient(clientId);
    }
}

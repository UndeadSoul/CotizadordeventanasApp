package com.example.betaaplication.ui.projects;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.betaaplication.Client;
import com.example.betaaplication.ClientProjectListItem;
import com.example.betaaplication.ClientRepository;
import com.example.betaaplication.Project;
import com.example.betaaplication.ProjectListItem;
import com.example.betaaplication.ProjectRepository;
import com.example.betaaplication.ProjectDetails;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel {

    private ProjectRepository projectRepository;
    private ClientRepository clientRepository;
    private LiveData<List<Project>> allProjects;
    private LiveData<List<Client>> allClients;
    private LiveData<List<ProjectListItem>> projectListItems;

    public ProjectViewModel(@NonNull Application application) {
        super(application);
        projectRepository = new ProjectRepository(application);
        clientRepository = new ClientRepository(application);
        allProjects = projectRepository.getAllProjects();
        allClients = clientRepository.getAllClients();
        projectListItems = projectRepository.getProjectListItems();
    }

    public LiveData<List<ProjectListItem>> getProjectListItems() {
        return projectListItems;
    }

    public LiveData<List<ClientProjectListItem>> getClientProjectListItems(int clientId) {
        return projectRepository.getClientProjectListItems(clientId);
    }

    public LiveData<ProjectDetails> getProjectDetailsById(long projectId) {
        return projectRepository.getProjectDetailsById(projectId);
    }

    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }

    public LiveData<List<Client>> getAllClients() {
        return allClients;
    }

    public void insert(Project project, ProjectRepository.InsertProjectCallback callback) {
        projectRepository.insert(project, callback);
    }

    public void update(Project project) {
        projectRepository.update(project);
    }
}

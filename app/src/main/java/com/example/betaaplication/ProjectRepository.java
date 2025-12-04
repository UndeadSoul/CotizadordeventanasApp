package com.example.betaaplication;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class ProjectRepository {

    private ProjectDao projectDao;
    private LiveData<List<Project>> allProjects;
    private LiveData<List<ProjectWithClientName>> allProjectsWithClientName;

    public ProjectRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        projectDao = db.projectDao();
        allProjects = projectDao.getAllProjects();
        allProjectsWithClientName = projectDao.getAllProjectsWithClientName();
    }

    public LiveData<List<ProjectWithClientName>> getAllProjectsWithClientName() {
        return allProjectsWithClientName;
    }

    public LiveData<ProjectDetails> getProjectDetailsById(int projectId) {
        return projectDao.getProjectDetailsById(projectId);
    }

    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }

    public LiveData<List<Project>> getProjectsForClient(int clientId) {
        return projectDao.getProjectsForClient(clientId);
    }

    public void insert(Project project) {
        new Thread(() -> {
            projectDao.insert(project);
        }).start();
    }
}

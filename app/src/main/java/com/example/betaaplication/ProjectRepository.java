package com.example.betaaplication;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class ProjectRepository {

    private ProjectDao projectDao;
    private LiveData<List<Project>> allProjects;
    private LiveData<List<ProjectListItem>> projectListItems;

    public interface InsertProjectCallback {
        void onProjectInserted(long projectId);
    }

    public ProjectRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        projectDao = db.projectDao();
        allProjects = projectDao.getAllProjects();
        projectListItems = projectDao.getProjectListItems();
    }

    public LiveData<List<ProjectListItem>> getProjectListItems() {
        return projectListItems;
    }

    public LiveData<List<ClientProjectListItem>> getClientProjectListItems(int clientId) {
        return projectDao.getClientProjectListItems(clientId);
    }

    public LiveData<ProjectDetails> getProjectDetailsById(long projectId) {
        return projectDao.getProjectDetailsById(projectId);
    }

    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }

    public LiveData<List<Project>> getProjectsForClient(int clientId) {
        return projectDao.getProjectsForClient(clientId);
    }

    public void insert(Project project, InsertProjectCallback callback) {
        new Thread(() -> {
            long projectId = projectDao.insert(project);
            callback.onProjectInserted(projectId);
        }).start();
    }

    public void update(Project project) {
        new Thread(() -> {
            projectDao.update(project);
        }).start();
    }
}

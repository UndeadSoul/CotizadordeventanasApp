package com.example.betaaplication;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class VentanaRepository {

    private VentanaDao ventanaDao;

    public VentanaRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        ventanaDao = db.ventanaDao();
    }

    public LiveData<List<Ventana>> getWindowsForProject(int projectId) {
        return ventanaDao.getWindowsForProject(projectId);
    }

    public LiveData<Ventana> getWindowById(int windowId) {
        return ventanaDao.getWindowById(windowId);
    }

    public void insert(Ventana ventana) {
        new Thread(() -> {
            ventanaDao.insert(ventana);
        }).start();
    }

    public void update(Ventana ventana) {
        new Thread(() -> {
            ventanaDao.update(ventana);
        }).start();
    }
}

package com.example.betaaplication.ui.windows;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.betaaplication.Ventana;
import com.example.betaaplication.VentanaRepository;
import java.util.List;

public class VentanaViewModel extends AndroidViewModel {

    private VentanaRepository repository;

    public VentanaViewModel(@NonNull Application application) {
        super(application);
        repository = new VentanaRepository(application);
    }

    public LiveData<List<Ventana>> getWindowsForProject(int projectId) {
        return repository.getWindowsForProject(projectId);
    }

    public LiveData<Ventana> getWindowById(int windowId) {
        return repository.getWindowById(windowId);
    }

    public void insert(Ventana ventana) {
        repository.insert(ventana);
    }

    public void update(Ventana ventana) {
        repository.update(ventana);
    }
}

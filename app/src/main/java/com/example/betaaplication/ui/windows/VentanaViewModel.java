package com.example.betaaplication.ui.windows;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.example.betaaplication.Ventana;
import com.example.betaaplication.VentanaRepository;
import java.util.List;

public class VentanaViewModel extends AndroidViewModel {

    private VentanaRepository repository;
    private final MutableLiveData<Long> projectId = new MutableLiveData<>();
    private final LiveData<List<Ventana>> windowsForProject;

    public VentanaViewModel(@NonNull Application application) {
        super(application);
        repository = new VentanaRepository(application);
        // When the projectId changes, switchMap re-queries the repository for the new list of windows.
        windowsForProject = Transformations.switchMap(projectId, id -> repository.getWindowsForProject(id));
    }

    /**
     * Sets the project ID to observe. This will trigger the LiveData to be updated
     * with the windows for the given project.
     * @param id The ID of the project.
     */
    public void setProjectId(long id) {
        projectId.setValue(id);
    }

    /**
     * Returns the LiveData list of windows for the current project ID.
     * The fragment will observe this to update the UI.
     */
    public LiveData<List<Ventana>> getWindowsForProject() {
        return windowsForProject;
    }

    /**
     * Returns a LiveData object of a single window, identified by its ID.
     * @param windowId The ID of the window to retrieve.
     */
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

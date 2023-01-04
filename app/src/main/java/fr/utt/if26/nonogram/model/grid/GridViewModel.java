package fr.utt.if26.nonogram.model.grid;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class GridViewModel extends AndroidViewModel {
    private final GridRepository repository;

    private final LiveData<List<Grid>> grids;

    public GridViewModel(@NonNull Application application) {
        super(application);
        repository = new GridRepository(application);
        grids = repository.getAllGrids();
    }

    public LiveData<List<Grid>> getAllGrids() {
        return grids;
    }

    public long insert(Grid grid) {
        return repository.insert(grid);
    }
}

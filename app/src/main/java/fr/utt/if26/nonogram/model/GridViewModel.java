package fr.utt.if26.nonogram.model;

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

    LiveData<List<Grid>> getAllGrids() {
        return grids;
    }

    public void insert(Grid grid) {
        repository.insert(grid);
    }
}

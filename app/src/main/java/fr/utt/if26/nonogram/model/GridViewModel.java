package fr.utt.if26.nonogram.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Random;

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

    public void insert(Grid grid) {
        repository.insert(grid);
    }

    public Grid getRandomGrid() {
        List<Grid> gridList = grids.getValue();
        return gridList != null ? gridList.get(new Random().nextInt(gridList.size())) : null;
    }
}

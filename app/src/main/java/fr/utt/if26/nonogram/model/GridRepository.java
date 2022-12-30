package fr.utt.if26.nonogram.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class GridRepository {
    private final GridDAO gridDAO;
    private final LiveData<List<Grid>> grids;

    GridRepository(Application application) {
        GridDatabase database = GridDatabase.getDatabase(application);
        gridDAO = database.gridDAO();
        grids = gridDAO.getAllGrids();
    }

    LiveData<List<Grid>> getAllGrids() {
        return grids;
    }

    public void insert(Grid grid) {
        GridDatabase.databaseWriteExecutor.execute(() -> gridDAO.insert(grid));
    }
}

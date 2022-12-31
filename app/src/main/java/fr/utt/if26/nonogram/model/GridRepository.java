package fr.utt.if26.nonogram.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

    public long insert(Grid grid) {
        Callable<Long> insertCallable = () -> gridDAO.insert(grid);
        long rowId = 0;

        Future<Long> future = GridDatabase.databaseWriteExecutor.submit(insertCallable);
        try {
            rowId = future.get();
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
        return rowId;
    }
}

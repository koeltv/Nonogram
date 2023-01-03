package fr.utt.if26.nonogram.model.grid;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import fr.utt.if26.nonogram.model.Database;

public class GridRepository {
    private final GridDAO gridDAO;
    private final LiveData<List<Grid>> grids;

    GridRepository(Application application) {
        Database database = Database.getDatabase(application);
        gridDAO = database.gridDAO();
        grids = gridDAO.getAllGrids();
    }

    LiveData<List<Grid>> getAllGrids() {
        return grids;
    }

    public long insert(Grid grid) {
        Callable<Long> insertCallable = () -> gridDAO.insert(grid);
        long rowId = 0;

        Future<Long> future = Database.databaseWriteExecutor.submit(insertCallable);
        try {
            rowId = future.get();
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
        return rowId;
    }
}

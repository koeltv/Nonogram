package fr.utt.if26.nonogram.model.grid;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GridDAO {
    @Insert
    long insert(Grid grid);

    @Delete
    void delete(Grid grid);

    @Update
    void update(Grid grid);

    @Query("SELECT * FROM grid")
    LiveData<List<Grid>> getAllGrids();

    @Query("DELETE FROM grid")
    void deleteAllGrids();
}

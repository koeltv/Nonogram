package fr.utt.if26.nonogram.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Grid.class}, version = 1)
public abstract class GridDatabase extends RoomDatabase {

    public abstract GridDAO gridDAO();

    private static volatile GridDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static GridDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GridDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            GridDatabase.class,
                            "grid_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}

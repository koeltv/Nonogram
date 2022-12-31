package fr.utt.if26.nonogram.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
                    ).addCallback(populateOnCreate).build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback populateOnCreate = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                GridDAO gridDAO = INSTANCE.gridDAO();
                gridDAO.deleteAllGrids();

                gridDAO.insert(new Grid(0, new Boolean[][]{
                        new Boolean[] {false, false, false, true, false},
                        new Boolean[] {false, true, false, false, true},
                        new Boolean[] {false, false, true, false, false},
                        new Boolean[] {true, false, false, false, false},
                        new Boolean[] {false, false, true, false, false}
                }));
            });
        }
    };
}

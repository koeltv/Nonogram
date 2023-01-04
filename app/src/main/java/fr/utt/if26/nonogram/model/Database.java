package fr.utt.if26.nonogram.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.utt.if26.nonogram.model.account.Account;
import fr.utt.if26.nonogram.model.account.AccountDAO;
import fr.utt.if26.nonogram.model.accountGrids.AccountGridCrossRef;
import fr.utt.if26.nonogram.model.grid.Grid;
import fr.utt.if26.nonogram.model.grid.GridDAO;

@androidx.room.Database(entities = {Grid.class, Account.class, AccountGridCrossRef.class}, version = 1)
public abstract class Database extends RoomDatabase {

    public abstract GridDAO gridDAO();

    public abstract AccountDAO accountDAO();

    private static volatile Database INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            Database.class,
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
                        new Boolean[] {false, false, true, true, false},
                        new Boolean[] {false, true, false, false, true},
                        new Boolean[] {false, false, true, true, false},
                        new Boolean[] {true, true, false, true, false},
                        new Boolean[] {true, true, true, true, true}
                }));

                AccountDAO accountDAO = INSTANCE.accountDAO();
                accountDAO.deleteAllAccounts();

                accountDAO.insert(new Account(0, "koeltv", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8"));
            });
        }
    };
}

package fr.utt.if26.nonogram.model.account;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.utt.if26.nonogram.model.grid.Grid;

@Database(entities = {Account.class}, version = 1)
public abstract class AccountDatabase extends RoomDatabase {

    public abstract AccountDAO accountDAO();

    private static volatile AccountDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AccountDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AccountDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AccountDatabase.class,
                            "account_database"
                    ).addCallback(populateOnCreate).build();
                }
            }
        }
        return INSTANCE;
    }

    private static final Callback populateOnCreate = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                AccountDAO accountDAO = INSTANCE.accountDAO();
                accountDAO.deleteAllAccounts();

                accountDAO.insert(new Account(0, "koeltv", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8"));
            });
        }
    };
}

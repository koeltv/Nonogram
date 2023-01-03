package fr.utt.if26.nonogram.model.account;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import fr.utt.if26.nonogram.model.Database;

public class AccountRepository {
    private final AccountDAO accountDAO;
    private final LiveData<List<Account>> accounts;

    AccountRepository(Application application) {
        Database database = Database.getDatabase(application);
        accountDAO = database.accountDAO();
        accounts = accountDAO.getAllAccounts();
    }

    public LiveData<List<Account>> getAllAccounts() {
        return accounts;
    }

    public LiveData<Account> getAccountWithId(long id) {
        return accountDAO.getAccountWithId(id);
    }

    public long insert(Account account) {
        Callable<Long> insertCallable = () -> accountDAO.insert(account);
        long rowId = 0;

        Future<Long> future = Database.databaseWriteExecutor.submit(insertCallable);
        try {
            rowId = future.get();
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
        return rowId;
    }

    public LiveData<Account> getAccountWith(String username, String hash) {
        return accountDAO.getAccountWith(username, hash);
    }
}

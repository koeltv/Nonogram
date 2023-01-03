package fr.utt.if26.nonogram.model.account;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import fr.utt.if26.nonogram.model.grid.Grid;

public class AccountRepository {
    private final AccountDAO accountDAO;
    private final LiveData<List<Account>> accounts;

    AccountRepository(Application application) {
        AccountDatabase database = AccountDatabase.getDatabase(application);
        accountDAO = database.accountDAO();
        accounts = accountDAO.getAllAccounts();
    }

    LiveData<List<Account>> getAllAccounts() {
        return accounts;
    }

    public void insert(Account account) {
        AccountDatabase.databaseWriteExecutor.execute(() -> accountDAO.insert(account));
    }
}

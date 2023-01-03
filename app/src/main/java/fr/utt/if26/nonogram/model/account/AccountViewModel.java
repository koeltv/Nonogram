package fr.utt.if26.nonogram.model.account;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {
    private final AccountRepository repository;

    private final LiveData<List<Account>> accounts;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        repository = new AccountRepository(application);
        accounts = repository.getAllAccounts();
    }

    public LiveData<List<Account>> getAllAccounts() {
        return accounts;
    }

    public LiveData<Account> getAccountWithId(long id) {
        return repository.getAccountWithId(id);
    }

    public long insert(Account account) {
        return repository.insert(account);
    }

    public LiveData<Account> getAccountWith(String username, String hash) {
        return repository.getAccountWith(username, hash);
    }
}

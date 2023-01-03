package fr.utt.if26.nonogram.model.account;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AccountDAO {
    @Insert
    long insert(Account account);

    @Delete
    void delete(Account account);

    @Update
    void update(Account account);

    @Query("SELECT * FROM account")
    LiveData<List<Account>> getAllAccounts();

    @Query("DELETE FROM account")
    void deleteAllAccounts();
}

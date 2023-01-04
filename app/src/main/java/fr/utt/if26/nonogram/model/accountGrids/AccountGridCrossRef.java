package fr.utt.if26.nonogram.model.accountGrids;

import androidx.room.Entity;

@Entity(tableName = "account_grid", primaryKeys = {"id", "gridId"})
public class AccountGridCrossRef {
    public long id;
    public long gridId;

    public AccountGridCrossRef(long id, long gridId) {
        this.id = id;
        this.gridId = gridId;
    }
}


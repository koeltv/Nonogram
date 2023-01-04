package fr.utt.if26.nonogram.model.accountGrids;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import fr.utt.if26.nonogram.model.account.Account;
import fr.utt.if26.nonogram.model.grid.Grid;

public class AccountWithGrids {
    @Embedded
    public Account account;
    @Relation(
            parentColumn = "id",
            entityColumn = "gridId",
            associateBy = @Junction(AccountGridCrossRef.class)
    )
    public List<Grid> grids;
}

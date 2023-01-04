package fr.utt.if26.nonogram.ui.grid;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.utt.if26.nonogram.MainActivity;
import fr.utt.if26.nonogram.databinding.ActivityGridRecyclerBinding;
import fr.utt.if26.nonogram.model.account.AccountViewModel;
import fr.utt.if26.nonogram.model.grid.Grid;
import fr.utt.if26.nonogram.model.grid.GridViewModel;

public class GridListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityGridRecyclerBinding binding = ActivityGridRecyclerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = binding.gridItemRecycler;
        final AdapterRecyclerGrid adapter = new AdapterRecyclerGrid(new AdapterRecyclerGrid.ModuleDiff());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        AccountViewModel accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.getGridsOfAccountWithId(MainActivity.currentAccount.getId()).observe(this, accountWithGrids -> {
            List<Grid> completedGrids = accountWithGrids.grids;

            GridViewModel gridViewModel = new ViewModelProvider(this).get(GridViewModel.class);
            gridViewModel.getAllGrids().observe(this, grids -> {
                grids.removeAll(completedGrids);

                List<GridCompletion> completionList = new ArrayList<>();
                for (Grid grid : grids) {
                    completionList.add(new GridCompletion(grid, false));
                }
                for (Grid grid : completedGrids) {
                    completionList.add(new GridCompletion(grid, true));
                }

                adapter.submitList(completionList);
            });
        });
    }
}
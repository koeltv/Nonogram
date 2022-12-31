package fr.utt.if26.nonogram.ui.grid;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import fr.utt.if26.nonogram.databinding.ActivityGridBinding;
import fr.utt.if26.nonogram.model.Grid;
import fr.utt.if26.nonogram.model.GridViewModel;

public class GridActivity extends AppCompatActivity {

    private ActivityGridBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGridBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GridViewModel gridViewModel = new ViewModelProvider(this).get(GridViewModel.class);

        gridViewModel.getAllGrids().observe(this, grids -> {
            System.out.println("This is good: " + grids.size());

            Grid currentGrid = grids.get(0);

            GridView gridView = binding.grid;
            gridView.setNumColumns(currentGrid.getWidth());

            GridAdapter adapter = new GridAdapter(this, gridViewModel);
            gridView.setAdapter(adapter);

//            GridViewModel gridViewModel = new ViewModelProvider(this).get(GridViewModel.class);
//            gridViewModel.getAllGrids().observe(this, adapter::submitList);
        });
    }
}
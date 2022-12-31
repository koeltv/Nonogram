package fr.utt.if26.nonogram.ui.grid;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

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

            LinearLayout leftRow = binding.leftRow;
            for (List<Integer> line : currentGrid.getLineNumbers()) {
                LinearLayout lineLayout = new LinearLayout(this);
                for (Integer value : line) {
                    TextView textView = new TextView(this);
                    textView.setText(value.toString());
                    lineLayout.addView(textView);
                }
                leftRow.addView(lineLayout);
            }

            LinearLayout topRow = binding.topRow;
            for (List<Integer> line : currentGrid.getColumnNumbers()) {
                LinearLayout lineLayout = new LinearLayout(this);
                lineLayout.setOrientation(LinearLayout.VERTICAL);
                for (Integer value : line) {
                    TextView textView = new TextView(this);
                    textView.setText(value.toString());
                    lineLayout.addView(textView);
                }
                topRow.addView(lineLayout);
            }

//            GridViewModel gridViewModel = new ViewModelProvider(this).get(GridViewModel.class);
//            gridViewModel.getAllGrids().observe(this, adapter::submitList);
        });
    }
}
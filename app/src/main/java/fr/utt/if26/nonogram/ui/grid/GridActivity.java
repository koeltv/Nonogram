package fr.utt.if26.nonogram.ui.grid;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.Random;

import fr.utt.if26.nonogram.MainActivity;
import fr.utt.if26.nonogram.R;
import fr.utt.if26.nonogram.databinding.ActivityGridBinding;
import fr.utt.if26.nonogram.model.account.AccountViewModel;
import fr.utt.if26.nonogram.model.accountGrids.AccountGridCrossRef;
import fr.utt.if26.nonogram.model.grid.Grid;
import fr.utt.if26.nonogram.model.grid.GridViewModel;

public class GridActivity extends AppCompatActivity {
    private static final int CASE_SIZE = 85;
    private static final int SPACING = 3;

    private ActivityGridBinding binding;

    private Grid currentGrid;

    private final int emptyHeartId = R.drawable.heart_empty;

    private int livesLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        livesLeft = 3;

        binding = ActivityGridBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GridViewModel gridViewModel = new ViewModelProvider(this).get(GridViewModel.class);

        gridViewModel.getAllGrids().observe(this, grids -> {
            //Grid selection
            int id = getIntent().getIntExtra("id", -10);
            if (id < 1) id = new Random().nextInt(grids.size()) + 1;
            currentGrid = grids.get(id - 1);

            GridView gridView = binding.grid;
            gridView.setNumColumns(currentGrid.getWidth());

            ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
            layoutParams.width = currentGrid.getWidth() * (CASE_SIZE + SPACING) - SPACING;
            gridView.setLayoutParams(layoutParams);

            gridView.setHorizontalSpacing(CASE_SIZE + SPACING);
            gridView.setVerticalSpacing(SPACING);

            View.OnClickListener listener = view -> {
                Button button = (Button) view;
                //The button was clicked, trigger action depending on whether it was correct or not

                int color = ((ColorDrawable) button.getBackground()).getColor();
                if (color == Color.WHITE) { //Incorrect
                    livesLeft--;
                    switch (livesLeft) {
                        case 2:
                            binding.imageView3.setImageResource(emptyHeartId);
                            break;
                        case 1:
                            binding.imageView2.setImageResource(emptyHeartId);
                            break;
                        case 0:
                            binding.imageView1.setImageResource(emptyHeartId);
                            showAndBlock("You Lose !");
                            break;
                    }
                }

                if (gridIsComplete()) {
                    showAndBlock("You Win !");

                    AccountViewModel accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
                    accountViewModel.insertAccountGridReference(new AccountGridCrossRef(MainActivity.currentAccount.getId(), currentGrid.getGridId()));
                }
            };

            InteractiveGridAdapter adapter = new InteractiveGridAdapter(this, currentGrid, listener);
            gridView.setAdapter(adapter);

            populateLeftRow();
            populateTopRow();
        });
    }

    private void showAndBlock(String s) {
        binding.textView.setText(s);
        binding.grid.setAdapter(new GridAdapter(this, currentGrid));
    }

    private boolean gridIsComplete() {
        for (int i = 0; i < currentGrid.getWidth() * currentGrid.getHeight(); i++) {
            Button currentButton = ((Button) binding.grid.getItemAtPosition(i));
            if (currentButton == null) return false;
            int backgroundColor = ((ColorDrawable) currentButton.getBackground()).getColor();
            if (currentGrid.getGrid()[i % currentGrid.getWidth()][i / currentGrid.getWidth()] && backgroundColor != Color.BLUE)
                return false;
        }
        return true;
    }

    private void populateTopRow() {
        LinearLayout topRow = binding.topRow;
        for (List<Integer> line : currentGrid.getColumnNumbers()) {
            LinearLayout lineLayout = new LinearLayout(this);
            lineLayout.setOrientation(LinearLayout.VERTICAL);
            for (Integer value : line) {
                TextView textView = new TextView(this);
                textView.setPadding(32, 0, 32, 0);
                textView.setText(String.valueOf(value));
                lineLayout.addView(textView);
            }
            topRow.addView(lineLayout);
        }
    }

    private void populateLeftRow() {
        LinearLayout leftRow = binding.leftRow;
        for (List<Integer> line : currentGrid.getLineNumbers()) {
            LinearLayout lineLayout = new LinearLayout(this);
            for (Integer value : line) {
                TextView textView = new TextView(this);
                textView.setPadding(0, 18, 0, 18);
                textView.setText(String.valueOf(value));
                lineLayout.addView(textView);
            }
            leftRow.addView(lineLayout);
        }
    }
}
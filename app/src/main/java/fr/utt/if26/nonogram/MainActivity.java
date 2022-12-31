package fr.utt.if26.nonogram;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import fr.utt.if26.nonogram.databinding.ActivityMainBinding;
import fr.utt.if26.nonogram.databinding.DialogueNewGridBinding;
import fr.utt.if26.nonogram.model.Grid;
import fr.utt.if26.nonogram.model.GridViewModel;
import fr.utt.if26.nonogram.ui.grid.GridActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //TODO Select a level from the database (with size displayed)
        binding.levelSelect.setOnClickListener(view -> {

        });

        //Play a random level from the database
        binding.randomLevel.setOnClickListener(view -> playGridWithId(-1));

        //TODO Create a new level with given difficulty and play it
        binding.customLevel.setOnClickListener(view -> createNewGridAndPlay());

        //Configurable settings, may be removed or exchanged with player selection
        binding.preferences.setOnClickListener(view -> {

        });
    }

    public void createNewGridAndPlay() {
        DialogueNewGridBinding newGridBinding = DialogueNewGridBinding.inflate(getLayoutInflater());
        newGridBinding.colsPicker.setMinValue(2);
        newGridBinding.colsPicker.setMaxValue(15);
        newGridBinding.colsPicker.setValue(5);

        newGridBinding.rowsPicker.setMinValue(2);
        newGridBinding.rowsPicker.setMaxValue(15);
        newGridBinding.rowsPicker.setValue(5);

        newGridBinding.difficultyPicker.setMinValue(1);
        newGridBinding.difficultyPicker.setMaxValue(10);
        newGridBinding.difficultyPicker.setValue(5);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Grid config");
        builder.setView(newGridBinding.getRoot());
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            Grid grid = new Grid(
                    newGridBinding.rowsPicker.getValue(),
                    newGridBinding.colsPicker.getValue(),
                    newGridBinding.difficultyPicker.getValue()
            );

            GridViewModel gridViewModel = new ViewModelProvider(this).get(GridViewModel.class);
            long id = gridViewModel.insert(grid);

            playGridWithId((int) id);
        });
        builder.create().show();
    }

    public <T extends AppCompatActivity> void playGridWithId(int id) {
        Intent intent = new Intent(MainActivity.this, GridActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

}
package fr.utt.if26.nonogram;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import fr.utt.if26.nonogram.databinding.ActivityMainBinding;
import fr.utt.if26.nonogram.ui.grid.GridActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Button button = binding.randomLevel;
        button.setOnClickListener(view -> switchTo(GridActivity.class));
    }

    public <T extends AppCompatActivity> void switchTo(Class<T> activityClass) {
        Intent intent = new Intent(MainActivity.this, activityClass);
        startActivity(intent);
    }

}
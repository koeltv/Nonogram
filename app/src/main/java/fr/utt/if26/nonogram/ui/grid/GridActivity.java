package fr.utt.if26.nonogram.ui.grid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import fr.utt.if26.nonogram.databinding.ActivityGridBinding;

public class GridActivity extends AppCompatActivity {

    private ActivityGridBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGridBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
package fr.utt.if26.nonogram;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import fr.utt.if26.nonogram.databinding.ActivityMainBinding;
import fr.utt.if26.nonogram.databinding.DialogueAccountBinding;
import fr.utt.if26.nonogram.databinding.DialogueNewGridBinding;
import fr.utt.if26.nonogram.model.account.Account;
import fr.utt.if26.nonogram.model.account.AccountViewModel;
import fr.utt.if26.nonogram.model.grid.Grid;
import fr.utt.if26.nonogram.model.grid.GridViewModel;
import fr.utt.if26.nonogram.ui.grid.GridActivity;
import fr.utt.if26.nonogram.ui.grid.GridListActivity;

public class MainActivity extends AppCompatActivity {

    public static Account currentAccount;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        long currentAccountId = sharedPref.getLong(getString(R.string.saved_account_id), -1);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (currentAccountId != -1) {
            AccountViewModel accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
            accountViewModel.getAccountWithId(currentAccountId).observe(this, account -> {
                if (account == null) selectAccount();
                else currentAccount = account;

                binding.playerGreeting.setText(getString(R.string.greeting, currentAccount.getUsername()));
            });
        }

        //Select a level from the database (with size and difficulty displayed)
        binding.levelSelect.setOnClickListener(view -> selectGridAndPlay());

        //Play a random level from the database
        binding.randomLevel.setOnClickListener(view -> playGridWithId(-1));

        //Create a new grid with given settings and play
        binding.customLevel.setOnClickListener(view -> createNewGridAndPlay());

        //Select an account or create a new one
        binding.selectAccount.setOnClickListener(view -> selectAccount());
    }

    private void selectGridAndPlay() {
        Intent intent = new Intent(MainActivity.this, GridListActivity.class);
        startActivity(intent);
    }

    public void createNewGridAndPlay() {
        DialogueNewGridBinding newGridBinding = DialogueNewGridBinding.inflate(getLayoutInflater());
        newGridBinding.colsPicker.setMinValue(2);
        newGridBinding.colsPicker.setMaxValue(10);
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
                    newGridBinding.colsPicker.getValue(),
                    newGridBinding.rowsPicker.getValue(),
                    newGridBinding.difficultyPicker.getValue()
            );

            GridViewModel gridViewModel = new ViewModelProvider(this).get(GridViewModel.class);
            long id = gridViewModel.insert(grid);

            playGridWithId((int) id);
        });
        builder.create().show();
    }

    public void selectAccount() {
        DialogueAccountBinding newAccountBinding = DialogueAccountBinding.inflate(getLayoutInflater());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select account");
        builder.setView(newAccountBinding.getRoot());
        builder.setNegativeButton("Create account", (dialogInterface, i) -> {
            Account account = Account.withPassword(
                    0,
                    newAccountBinding.editUsername.getText().toString(),
                    newAccountBinding.editPassword.getText().toString()
            );

            AccountViewModel accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
            long accountId = accountViewModel.insert(account);

            setCurrentAccount(accountId);
        });
        builder.setPositiveButton("Log In", (dialogInterface, i) -> {
            AccountViewModel accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
            accountViewModel.getAccountWith(
                    newAccountBinding.editUsername.getText().toString(),
                    Account.getSHA256(newAccountBinding.editPassword.getText().toString())
            ).observe(this, account -> {
                if (account != null) {
                    setCurrentAccount(account.getId());
                } else {
                    Toast.makeText(this, newAccountBinding.editUsername.getText().toString() + " incorrect, check the password", Toast.LENGTH_SHORT).show();
                }
            });
        });
        builder.create().show();
    }

    private void setCurrentAccount(long accountId) {
        AccountViewModel accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.getAccountWithId(accountId).observe(this, account -> {
            currentAccount = account;
            binding.playerGreeting.setText(getString(R.string.greeting, currentAccount.getUsername()));
        });

        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
        editor.putLong(getString(R.string.saved_account_id), accountId);
        editor.apply();
    }

    public void playGridWithId(int id) {
        Intent intent = new Intent(MainActivity.this, GridActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
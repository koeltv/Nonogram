package fr.utt.if26.nonogram.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

@Entity(tableName = "grid")
@TypeConverters({GridArrayConverter.class})
public class Grid implements Serializable {
    @Ignore
    private static int nextId = 0;

    @Ignore
    private static final Random random = new Random();

    @PrimaryKey
    private final int id;

    private final Boolean[][] grid;

    Grid(int size) {
        id = nextId++;
        grid = new Boolean[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                grid[x][y] = random.nextBoolean();
            }
        }
    }

    public Grid(int id, Boolean[][] grid) {
        this.id = id;
        this.grid = grid;
    }

    public int getId() {
        return id;
    }

    public Boolean[][] getGrid() {
        return grid;
    }

    public ArrayList<ArrayList<Integer>> getColumnNumbers() {
        ArrayList<ArrayList<Integer>> columnCounts = new ArrayList<>();
        for (int x = 0; x < grid.length; x++) {
            ArrayList<Integer> currentSuite = new ArrayList<>();
            currentSuite.add(0);
            for (int y = 0; y < grid.length; y++) {
                if (grid[x][y]) {
                    currentSuite.set(currentSuite.size()-1, currentSuite.get(currentSuite.size()-1) + 1);
                } else {
                    currentSuite.add(0);
                }
            }
            columnCounts.add(currentSuite);
        }
        return columnCounts;
    }

    public ArrayList<ArrayList<Integer>> getLineNumbers() {
        ArrayList<ArrayList<Integer>> lineCounts = new ArrayList<>();
        for (int y = 0; y < grid.length; y++) {
            ArrayList<Integer> currentSuite = new ArrayList<>();
            currentSuite.add(0);
            for (int x = 0; x < grid.length; x++) {
                if (grid[x][y]) {
                    currentSuite.set(currentSuite.size()-1, currentSuite.get(currentSuite.size()-1) + 1);
                } else {
                    currentSuite.add(0);
                }
            }
            lineCounts.add(currentSuite);
        }
        return lineCounts;
    }

    public int getWidth() {
        return grid.length;
    }

    public int getHeight() {
        return grid[0].length;
    }
}

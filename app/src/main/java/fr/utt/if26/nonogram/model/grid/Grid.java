package fr.utt.if26.nonogram.model.grid;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Entity(tableName = "grid")
@TypeConverters({GridArrayConverter.class})
public class Grid implements Serializable {
    @Ignore
    private static final Random random = new Random();

    @PrimaryKey(autoGenerate = true)
    private final int gridId;

    private final int difficulty;

    private final Boolean[][] grid;

    public Grid(int width, int height, int difficulty) {
        gridId = 0;
        this.difficulty = difficulty;
        grid = new Boolean[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = (random.nextInt(difficulty) + 1) < difficulty;
            }
        }
    }

    Grid(int size, int difficulty) {
        gridId = 0;
        this.difficulty = difficulty;
        grid = new Boolean[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                grid[x][y] = random.nextBoolean();
            }
        }
    }

    public Grid(int gridId, int difficulty, Boolean[][] grid) {
        this.gridId = gridId;
        this.difficulty = difficulty;
        this.grid = grid;
    }

    public int getGridId() {
        return gridId;
    }

    public Boolean[][] getGrid() {
        return grid;
    }

    public List<List<Integer>> getColumnNumbers() {
        List<List<Integer>> columnCounts = new ArrayList<>();
        for (int x = 0; x < grid.length; x++) {
            LinkedList<Integer> currentSuite = new LinkedList<>();
            currentSuite.add(0);
            boolean emptyCaseEncountered = false;
            for (int y = 0; y < grid.length; y++) {
                if (grid[x][y]) {
                    if (emptyCaseEncountered) {
                        currentSuite.add(1);
                        emptyCaseEncountered = false;
                    } else {
                        currentSuite.addLast(currentSuite.pollLast() + 1);
                    }
                } else if (currentSuite.getLast() != 0){
                    emptyCaseEncountered = true;
                }
            }
            columnCounts.add(currentSuite);
        }
        return columnCounts;
    }

    public List<List<Integer>> getLineNumbers() {
        List<List<Integer>> lineCounts = new ArrayList<>();
        for (int y = 0; y < grid.length; y++) {
            LinkedList<Integer> currentSuite = new LinkedList<>();
            currentSuite.add(0);
            boolean emptyCaseEncountered = false;
            for (int x = 0; x < grid.length; x++) {
                if (grid[x][y]) {
                    if (emptyCaseEncountered) {
                        currentSuite.add(1);
                        emptyCaseEncountered = false;
                    } else {
                        currentSuite.addLast(currentSuite.pollLast() + 1);
                    }
                } else if (currentSuite.getLast() != 0){
                    emptyCaseEncountered = true;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grid grid1 = (Grid) o;
        return gridId == grid1.gridId && Arrays.deepEquals(grid, grid1.grid);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(gridId);
        result = 31 * result + Arrays.deepHashCode(grid);
        return result;
    }

    public int getDifficulty() {
        return difficulty;
    }
}

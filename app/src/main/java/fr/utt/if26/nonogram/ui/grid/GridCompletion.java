package fr.utt.if26.nonogram.ui.grid;

import java.util.Objects;

import fr.utt.if26.nonogram.model.grid.Grid;

public class GridCompletion {
    private final long gridId;
    private final int width;
    private final int height;
    private final int difficulty;
    private final boolean completed;

    public GridCompletion(long gridId, int width, int height, int difficulty, boolean completed) {
        this.gridId = gridId;
        this.width = width;
        this.height = height;
        this.difficulty = difficulty;
        this.completed = completed;
    }

    public GridCompletion(Grid grid, boolean completed) {
        this.gridId = grid.getGridId();
        this.width = grid.getWidth();
        this.height = grid.getHeight();
        this.difficulty = grid.getDifficulty();
        this.completed = completed;
    }

    public long getGridId() {
        return gridId;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridCompletion that = (GridCompletion) o;
        return gridId == that.gridId && width == that.width && height == that.height && difficulty == that.difficulty && completed == that.completed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gridId, width, height, difficulty, completed);
    }
}

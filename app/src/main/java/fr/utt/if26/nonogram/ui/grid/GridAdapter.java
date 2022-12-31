package fr.utt.if26.nonogram.ui.grid;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import fr.utt.if26.nonogram.model.GridViewModel;

public class GridAdapter extends BaseAdapter {
    private final Context context;
    private final Boolean[][] gridContent; //TODO Correct with live data or ???

    public GridAdapter(Context context, GridViewModel gridViewModel) {
        this.context = context;
        this.gridContent = gridViewModel.getRandomGrid().getGrid();
    }

    @Override
    public int getCount() {
        return gridContent.length * gridContent.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        Button button;

        if (view == null) {
            button = new Button(context);
            button.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
//            button.setPadding(0, 0, 0, 0);
        } else {
            button = (Button) view;
        }

        boolean caseFilled = gridContent[index % gridContent.length][index / gridContent.length];
        button.setBackgroundColor(caseFilled ? Color.BLUE : Color.WHITE);
        return button;
    }
}

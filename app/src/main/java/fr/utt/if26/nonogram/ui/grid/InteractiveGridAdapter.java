package fr.utt.if26.nonogram.ui.grid;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import fr.utt.if26.nonogram.model.grid.Grid;

public class InteractiveGridAdapter extends BaseAdapter {
    private final Context context;
    private final Boolean[][] gridContent;
    private final View.OnClickListener listener;
    private final List<Button> buttonList = new ArrayList<>();

    public InteractiveGridAdapter(Context context, Grid grid, View.OnClickListener listener) {
        this.context = context;
        this.gridContent = grid.getGrid();
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return gridContent.length * gridContent.length;
    }

    @Override
    public Object getItem(int i) {
        return buttonList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        Button button;

        boolean caseFilled = gridContent[index % gridContent.length][index / gridContent.length];

        if (view == null) {
            button = new Button(context);
            buttonList.add(button);
            button.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
            button.setOnClickListener(buttonView -> {
                if (caseFilled) {
                    button.setBackgroundColor(Color.BLUE);
                }
                listener.onClick(buttonView);
            });
        } else {
            button = (Button) view;
        }

        button.setBackgroundColor(Color.WHITE);
        return button;
    }
}

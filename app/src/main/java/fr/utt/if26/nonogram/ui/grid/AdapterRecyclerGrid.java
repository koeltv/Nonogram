package fr.utt.if26.nonogram.ui.grid;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import fr.utt.if26.nonogram.R;
import fr.utt.if26.nonogram.model.grid.Grid;

public class AdapterRecyclerGrid extends ListAdapter<Grid, AdapterRecyclerGrid.ModuleViewHolder> {
    public AdapterRecyclerGrid(@NonNull DiffUtil.ItemCallback<Grid> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ModuleViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        Grid current = getItem(position);
        holder.bind(current);
    }

    static class ModuleViewHolder extends RecyclerView.ViewHolder {
        private final TextView gridId;
        private final TextView gridDimensions;
        private final TextView gridDifficulty;
        private final TextView gridCompleted;
        private final LinearLayout layout;

        private ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout = itemView.findViewById(R.id.gridItemLayout);
            this.gridId = itemView.findViewById(R.id.gridId);
            this.gridDimensions = itemView.findViewById(R.id.gridDimensions);
            this.gridDifficulty = itemView.findViewById(R.id.gridDifficulty);
            this.gridCompleted = itemView.findViewById(R.id.gridCompleted);
        }

        void bind(Grid grid) {
            layout.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), GridActivity.class);
                intent.putExtra("id", grid.getGridId());
                itemView.getContext().startActivity(intent);
            });
            gridId.setText(String.valueOf(grid.getGridId()));
            gridDimensions.setText(grid.getWidth() + "x" + grid.getHeight());
            gridDifficulty.setText(String.valueOf(grid.getDifficulty()));
            gridCompleted.setText("Yes");
        }

        static ModuleViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_item, parent, false);
            return new ModuleViewHolder(view);
        }
    }

    static class ModuleDiff extends DiffUtil.ItemCallback<Grid> {
        @Override
        public boolean areItemsTheSame(@NonNull Grid oldItem, @NonNull Grid newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Grid oldItem, @NonNull Grid newItem) {
            return oldItem.equals(newItem);
        }
    }
}

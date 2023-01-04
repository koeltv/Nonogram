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

public class AdapterRecyclerGrid extends ListAdapter<GridCompletion, AdapterRecyclerGrid.ModuleViewHolder> {
    public AdapterRecyclerGrid(@NonNull DiffUtil.ItemCallback<GridCompletion> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ModuleViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        GridCompletion current = getItem(position);
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

        void bind(GridCompletion gridCompletion) {
            layout.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), GridActivity.class);
                intent.putExtra("id", (int) gridCompletion.getGridId());
                itemView.getContext().startActivity(intent);
            });
            gridId.setText(String.valueOf(gridCompletion.getGridId()));
            gridDimensions.setText(itemView.getContext().getString(R.string.written_dimensions, gridCompletion.getWidth(), gridCompletion.getHeight()));
            gridDifficulty.setText(String.valueOf(gridCompletion.getDifficulty()));
            gridCompleted.setText(itemView.getContext().getString(gridCompletion.isCompleted() ? R.string.yes : R.string.no));
        }

        static ModuleViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_item, parent, false);

            return new ModuleViewHolder(view);
        }
    }

    static class ModuleDiff extends DiffUtil.ItemCallback<GridCompletion> {
        @Override
        public boolean areItemsTheSame(@NonNull GridCompletion oldItem, @NonNull GridCompletion newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull GridCompletion oldItem, @NonNull GridCompletion newItem) {
            return oldItem.equals(newItem);
        }
    }
}

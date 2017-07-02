package com.example.profbola.bakingtime.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.models.Ingredient;

/**
 * Created by prof.BOLA on 7/2/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private Context mContext;
    private Ingredient[] mIngredients;

    public IngredientAdapter(Context context, Ingredient[] ingredients) {
        mContext = context;
        mIngredients = ingredients;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.ingredient, parent, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        Ingredient ingredient = mIngredients[position];
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        if (null == mIngredients) return 0;
        return mIngredients.length;
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        public final TextView name;
        public final TextView quantity;
        public final TextView measure;

        public IngredientViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.ingredient_name);
            quantity = (TextView) itemView.findViewById(R.id.ingredient_quantity);
            measure = (TextView) itemView.findViewById(R.id.ingredient_measure);
        }

        void bind(Ingredient ingredient) {
            name.setText(ingredient.ingredient);
            quantity.setText(String.valueOf(ingredient.quantity));
            measure.setText(ingredient.measure);
        }
    }

    public void swapIngredients(Ingredient[] ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }
}
package com.example.profbola.bakingtime.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.models.Ingredient;
import com.example.profbola.bakingtime.ui.IngredientsFragment;

import java.util.ArrayList;

/**
 * Created by prof.BOLA on 7/2/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private Context mContext;
    private ArrayList<Ingredient> mIngredients;
    private IngredientsFragment.OnIngredientSelected mCallback;

    public IngredientAdapter(Context context, IngredientsFragment.OnIngredientSelected callback) {
        mContext = context;
        mCallback = callback;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.ingredient, parent, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (null == mIngredients) return 0;
        return mIngredients.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final TextView name;
        private final TextView quantity;
        private final TextView measure;

        private Ingredient mIngredient;

        public IngredientViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.ingredient_name);
            quantity = (TextView) itemView.findViewById(R.id.ingredient_quantity);
            measure = (TextView) itemView.findViewById(R.id.ingredient_measure);

            itemView.setOnClickListener(this);
        }

        void bind(int position) {
            mIngredient = mIngredients.get(position);
            name.setText(mIngredient.ingredient);
            quantity.setText(String.valueOf(mIngredient.quantity));
            measure.setText(mIngredient.measure);
        }

        @Override
        public void onClick(View v) {
            mCallback.ingredientClicked(mIngredient);
        }
    }

    public void swapIngredients(ArrayList<Ingredient> ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }
}
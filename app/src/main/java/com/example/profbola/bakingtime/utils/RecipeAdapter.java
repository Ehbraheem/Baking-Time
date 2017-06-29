package com.example.profbola.bakingtime.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.models.Recipe;

import java.util.List;


/**
 * Created by prof.BOLA on 6/29/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final Context mContext;
    private List<Recipe> mRecipes;

    public RecipeAdapter(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.recipe_card, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public void swapRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener  {

        private final TextView mTitleView;
        private final TextView mServingsView;
        private final ImageView mRecipeImage;

        public RecipeViewHolder(View view) {
            super(view);

            mTitleView = (TextView) view.findViewById(R.id.recipe_title);
            mRecipeImage = (ImageView) view.findViewById(R.id.recipe_image);
            mServingsView = (TextView) view.findViewById(R.id.recipe_servings);

        }

        @Override
        public void onClick(View v) {
            Snackbar.make(v, "Congrats!!! You Just Clicked a cake!!!! Stay tuned for how to bake!!!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        void bind(Recipe recipe) {
            mTitleView.setText(recipe.name);
            mServingsView.setText(recipe.servings);
            int id = mContext.getResources().getIdentifier(recipe.image, "drawable", mContext.getPackageName());
            mRecipeImage.setImageResource(id);
        }
    }
}

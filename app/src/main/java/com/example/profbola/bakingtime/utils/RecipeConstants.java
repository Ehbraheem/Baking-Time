package com.example.profbola.bakingtime.utils;

import com.example.profbola.bakingtime.services.RecipeService;

/**
 * Created by prof.BOLA on 7/10/2017.
 */

public final class RecipeConstants {

    /**
     * This is used to mirror an outer static class
     */
    private RecipeConstants() {
    }

    public static final String RECIPE = "recipe";

    public static final int RECIPE_LOADER_ID = 456;

    public static final class RecipeDetailsConstants {

        public static final String INGREDIENTS_KEY = "ingredient";
        public static final String STEP_KEY = "step";

        public static final int STEP_LOADER_ID = 223;
        public static final int INGREDIENT_LOADER_ID = 445;

        public static final String INGREDIENT_TAB = "ingredients";
        public static final String STEP_TAB = "steps";

    }

    public static final class RecipesConstants {

        public static final String SERVINGS_KEY = "servings";
        public static final String NAME_KEY = "name";
        public static final String IMAGE_KEY = "image";
        public static final String INGREDIENTS_KEY = "ingredients";
        public static final String STEPS_KEY = "steps";
        public static final String ID_KEY = "id";

    }

    public static final class StepFragmentContants {
        public static final String STEPS_KEY = "steps_key";
    }

    public static final class RecipeServiceConstants {

        private static final String BASE_ACTION
                = "com.example.profbola.bakingtime.services.action.";

        public static final String ACTION_SYC_RECIPES
                = BASE_ACTION + "sync_recipes";

        public static final String ACTION_GET_ASSOCIATED_DATA
                = BASE_ACTION + "get_associated_data";

        public static final String UPDATE_RECIPE_WIDGET
                = BASE_ACTION + "update_recipe_widget";

        public static final String LAST_VIEWED_RECIPE_INGREDIENTS
                = BASE_ACTION + "last_viewd_recipe_ingredients";
    }

    public static final class IngredientFragmentConstants {
        public static final String INGREDIENT_KEY = "ingredients_key";
    }

    public static final class RecipesWidgetProviderConstants {

        public static final String RECIPE_ID = "recipeId";

    }

    public static final class IngredientsConstants {

        public static final String QUANTITY_KEY = "quantity";

        public static final String MEASURE_KEY = "measure";

        public static final String INGREDIENT_KEY = "ingredient";

    }

    public static final class StepsConstants {

        public static final String ID_KEY = "id";

        public static final String SHORT_DESCRIPTION_KEY = "shortDescription";

        public static final String DESCRIPTION_KEY = "description";

        public static final String VIDEO_URL_KEY = "videoURL";

        public static final String THUMBNAIL_URL_KEY = "thumbnailURL";

    }

    public static final class DbHelperConstants {

        public static final String DATABASE_NAME = "bakingtime.db";

        public static final int DATABASE_VERSION = 1;

    }

    public static final class RecipeProviderConstants {

        public static final int CODE_RECIPES = 100;

        public static final int CODE_SINGLE_RECIPE = 101;

        public static final int CODE_SINGLE_RECIPE_INGREDIENTS = 101100;

        public static final int CODE_SINGLE_RECIPE_STEPS = 101200;

        public static final int CODE_SINGLE_RECIPE_SINGLE_STEP = 1012001;

        public static final int CODE_SINGLE_RECIPE_SINGLE_INGREDIENT = 101101;

    }

    public static final class RecipeNetworkUtilConstants {

        public static final String API_URL = "https://go.udacity.com/android-baking-app-json";

    }

    public static final class StepDbHelperConstants {

        public static final String STEP_RECIPE_ID_IDX = "step_recipe_id_idx";

    }

    public static final class IngredientDbHelperConstants {

        public static final String INGREDIENT_RECIPE_ID_IDX = "ingredient_recipe_id_idx";

    }

    public static final class FullDetailsConstants {

        public static final int PLAYBACK_DELTA = 3000;

    }

    public static final class RecipeActivityTestConstants {

        public static final String CAKE_NAME = "Cheesecake";

        public static final String INGREDIENT_NAME = "vanilla";

        public static final String STEP_TITLE = "Add heavy cream and vanilla.";
        public static final String FAST_FORWARD = "fast forward";
        public static final String REWIND = "rewind";
        public static final String FF_DESCRIPTION = "Fast forward";
        public static final String RWD_DESCRIPTION = "Rewind";

        public static final int RECIPE_POSITION = 3;

        public static final int STEP_POSITION = 6;

        public static final int VIDEO_DELTA = 03;
    }

}

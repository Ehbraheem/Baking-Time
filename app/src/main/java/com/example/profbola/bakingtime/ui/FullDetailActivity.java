package com.example.profbola.bakingtime.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.models.Step;

import static com.example.profbola.bakingtime.utils.RecipeConstants.FullDetailsConstants.NEXT_BUTTON_REQUEST;
import static com.example.profbola.bakingtime.utils.RecipeConstants.RecipeDetailsConstants.STEP_KEY;

public class FullDetailActivity extends AppCompatActivity
        implements FullDetailsFragment.NextStepButtonClicked {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_detail);

        if (null == savedInstanceState) {

            // FIXME: 7/13/2017 Add NULL Guard
            Intent intent = getIntent();
            if (intent != null) {
                Bundle bundle = getIntent().getExtras();

                FullDetailsFragment fullDetailsFragment = new FullDetailsFragment();
                fullDetailsFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.full_details, fullDetailsFragment)
                        .commit();
            }
        }
    }

    private void respondToNextButton(Step step) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(STEP_KEY, step);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void openNextStep(Step step) {
        respondToNextButton(step);
    }
}

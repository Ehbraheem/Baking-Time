package com.example.profbola.bakingtime.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.profbola.bakingtime.R;

public class FullDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_detail);

        if (null == savedInstanceState) {

            Bundle bundle = getIntent().getExtras();

            FullDetailsFragment fullDetailsFragment = new FullDetailsFragment();
            fullDetailsFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.full_details, fullDetailsFragment)
                    .commit();
        }
    }
}

package com.example.profbola.bakingtime.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.services.RecipeService;
import com.example.profbola.bakingtime.utils.RecipeConstants;
import com.example.profbola.bakingtime.utils.RecipeNetworkUtil;

import static com.example.profbola.bakingtime.provider.RecipeContract.RecipeEntry.CONTENT_URI;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {


    private ProgressBar mLoadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null ) {
            startSync(findViewById(R.id.fab));
            mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSync(view);
            }
        });

    }

    private void startSync(View view) {
        boolean isConnected = RecipeNetworkUtil.networkCheck(this);
        if (isConnected) {
            RecipeService.startActionSyncRecipes(this);
        } else {
            Snackbar.make(view, getString(R.string.no_connection), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (mLoadingIndicator != null) mLoadingIndicator.setVisibility(View.GONE);
        switch (loader.getId()) {

            case RecipeConstants.RECIPE_LOADER_ID:

                MainActivityFragment fragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
                fragment.changeData(data);

                break;

            default:
                return;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.put(RECIPE, mCursor);
//    }


    @Override
    protected void onResume(){
        super.onResume();
        android.content.Loader<Cursor> loader = getLoaderManager().getLoader(RecipeConstants.RECIPE_LOADER_ID);
        if (loader==null) {
            getSupportLoaderManager().initLoader(RecipeConstants.RECIPE_LOADER_ID, null, this);
        }else {
            getSupportLoaderManager().restartLoader(RecipeConstants.RECIPE_LOADER_ID, null, this);
        }
    }
}

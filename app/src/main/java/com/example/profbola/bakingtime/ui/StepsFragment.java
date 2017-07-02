package com.example.profbola.bakingtime.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.models.Step;
import com.example.profbola.bakingtime.utils.StepAdapter;

/**
 * Created by prof.BOLA on 7/1/2017.
 */

public class StepsFragment extends Fragment {

    private RecyclerView mStepsListing;
    private StepAdapter mAdapter;
    private Step[] mSteps;

    public StepsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps_listing, container, false);

        mStepsListing = (RecyclerView) view.findViewById(R.id.steps_listing);
        setUpIngredientView();

        return view;
    }

    private void setUpIngredientView() {
        mAdapter = new StepAdapter(getContext());
        mStepsListing.setAdapter(mAdapter);
        mAdapter.swapSteps(mSteps);
        mStepsListing.setLayoutManager(new LinearLayoutManager(getContext()));
        mStepsListing.setHasFixedSize(true);
    }

    public void addData(Step[] steps) {
       mSteps = steps;
    }
}

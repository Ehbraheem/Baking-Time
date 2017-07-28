package com.example.profbola.bakingtime.ui;

import android.content.Context;
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

import java.util.ArrayList;

import static com.example.profbola.bakingtime.utils.RecipeConstants.StepFragmentContants.STEPS_KEY;

/**
 * Created by prof.BOLA on 7/1/2017.
 */

public class StepsFragment extends Fragment {

    private RecyclerView mStepsListing;
    private StepAdapter mAdapter;
    private ArrayList<Step> mSteps;

    OnVideoPlayerSelected mCallback;

    public interface OnVideoPlayerSelected {
        void videoClicked(int position);
    }

    public StepsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mSteps = savedInstanceState.getParcelableArrayList(STEPS_KEY);
        }
        View view = inflater.inflate(R.layout.fragment_steps_listing, container, false);

        mStepsListing = (RecyclerView) view.findViewById(R.id.steps_listing);
        setUpStepView();

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STEPS_KEY, mSteps);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnVideoPlayerSelected) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
            + " must implement OnVideoPlayerSelected");
        }
    }

    private void setUpStepView() {
        mAdapter = new StepAdapter(getContext(), (OnVideoPlayerSelected) getActivity());
        mStepsListing.setAdapter(mAdapter);
        mAdapter.swapSteps(mSteps);
        mStepsListing.setLayoutManager(new LinearLayoutManager(getContext()));
        mStepsListing.setHasFixedSize(true);
    }

    public void addData(ArrayList<Step> steps) {
        mSteps = steps;
        mAdapter.swapSteps(mSteps);
    }
}

package com.example.profbola.bakingtime.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.models.Step;
import com.example.profbola.bakingtime.ui.StepsFragment;

import java.util.List;

/**
 * Created by prof.BOLA on 7/2/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private Context mContext;
    private List<Step> mSteps;
    private StepsFragment.OnVideoPlayerSelected mCallback;

    public StepAdapter(Context context, StepsFragment.OnVideoPlayerSelected playerSelected) {
        mContext = context;
        mCallback = playerSelected;
    }

    @Override
    public StepAdapter.StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.step, parent, false);

        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepAdapter.StepViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (null == mSteps) return 0;
        return mSteps.size();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final TextView shortDescription;

        private Step mStep;

        private int position;

        public StepViewHolder(View itemView) {
            super(itemView);

            shortDescription = (TextView) itemView.findViewById(R.id.step_short_description);

            itemView.setOnClickListener(this);
        }

        void bind(int position) {
            mStep = mSteps.get(position);
            this.position = position;

            shortDescription.setText(mStep.shortDescription);
        }

        @Override
        public void onClick(View v) {
            mCallback.videoClicked(position);
        }
    }

    public void swapSteps(List<Step> steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }
}

package com.example.profbola.bakingtime.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.models.Step;

/**
 * Created by prof.BOLA on 7/2/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private Context mContext;
    private Step[] mSteps;

    public StepAdapter(Context context) {
        mContext = context;
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
        return mSteps.length;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder {

        private final TextView id;
        private final TextView description;

        public StepViewHolder(View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.step_id);
            description = (TextView) itemView.findViewById(R.id.step_description);
        }

        void bind(int position) {
            Step step = mSteps[position];
            id.setText(String.valueOf(step.id));
            description.setText(step.description);
        }
    }

    public void swapSteps(Step[] steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }
}

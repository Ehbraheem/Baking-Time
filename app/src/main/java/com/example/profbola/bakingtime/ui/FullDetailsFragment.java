package com.example.profbola.bakingtime.ui;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.models.Ingredient;
import com.example.profbola.bakingtime.models.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by prof.BOLA on 7/2/2017.
 */

public class FullDetailsFragment extends Fragment implements ExoPlayer.EventListener {

    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;

    private ImageView mThumbnailView;

    private static MediaSessionCompat mMediaSession;

    private PlaybackStateCompat.Builder mStateBuilder;

    private NotificationManager mNotificationManager;

    private Context mContext;

    private Step mStep;
    private Ingredient mIngredient;

    public static final String TAG = FullDetailsFragment.class.getName();

    public FullDetailsFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_full_detail, container, false);
        Bundle bundle = getArguments();
        mContext = getContext();

        if (bundle != null && bundle.containsKey(RecipeDetailActivity.INGREDIENTS_KEY)) {
            mIngredient = bundle.getParcelable(RecipeDetailActivity.INGREDIENTS_KEY);
            setUpIngredient(view);
        } else if (bundle != null && bundle.containsKey(RecipeDetailActivity.STEP_KEY)) {
            mStep = bundle.getParcelable(RecipeDetailActivity.STEP_KEY);
            setUpStep(view);
        }

        Toast.makeText(mContext, "It Worked!!!", Toast.LENGTH_SHORT).show();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) releasePlayer();
    }

    private void setUpIngredient(View view) {
        Toast.makeText(getContext(), "It Worked!!!", Toast.LENGTH_SHORT).show();

        view.findViewById(R.id.player_view).setVisibility(View.GONE);
        view.findViewById(R.id.ingredient_view).setVisibility(View.VISIBLE);

        TextView quantity = (TextView) view.findViewById(R.id.quantity);
        TextView measure = (TextView) view.findViewById(R.id.measure);
        TextView name = (TextView) view.findViewById(R.id.ingredient);

        quantity.getText();
        quantity.setText(String.valueOf(mIngredient.quantity));
        measure.setText(mIngredient.measure);
        name.setText(mIngredient.ingredient);
    }

    private void setUpStep(View view) {
        Toast.makeText(mContext, "It Worked!!!", Toast.LENGTH_SHORT).show();

        view.findViewById(R.id.player_view).setVisibility(View.VISIBLE);
        view.findViewById(R.id.ingredient_view).setVisibility(View.GONE);

        if (!mStep.videoURL.isEmpty()) {
            mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.playerView);
            setUpMediaSession();
            initializePlayer(Uri.parse(mStep.videoURL));
//        mPlayerView.setDefaultArtwork();
        }
        if (!mStep.thumbnailURL.isEmpty()) {
            mThumbnailView = (ImageView) view.findViewById(R.id.stepThumbnail);
            Picasso.with(mContext).load(mStep.thumbnailURL).into(mThumbnailView);
        }
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext,
                                            trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(mContext, "Baking Time");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    mContext, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        mNotificationManager.cancelAll();
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady ) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }

        mMediaSession.setPlaybackState(mStateBuilder.build());
        showVideoNotification(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    private void setUpMediaSession() {
        mMediaSession = new MediaSessionCompat(mContext, TAG);

        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        );

        mMediaSession.setMediaButtonReceiver(null);

        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE |
                                PlaybackStateCompat.ACTION_FAST_FORWARD |
                                PlaybackStateCompat.ACTION_REWIND
                );

        mMediaSession.setPlaybackState(mStateBuilder.build());

        mMediaSession.setCallback(new MySessionCallback());

        mMediaSession.setActive(true);
    }

    private void showVideoNotification(PlaybackStateCompat state) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);

        int icon;
        String play_pause;
        if (state.getState() == PlaybackStateCompat.STATE_PLAYING) {
            icon = R.drawable.exo_controls_pause;
            play_pause = getString(R.string.pause);
        } else {
            icon = R.drawable.exo_controls_play;
            play_pause = getString(R.string.play);
        }

        NotificationCompat.Action playPauseAction = new NotificationCompat.Action(
                icon, play_pause,
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                        mContext, PlaybackStateCompat.ACTION_PLAY_PAUSE));

        NotificationCompat.Action fowardAction = new NotificationCompat.Action(
                R.drawable.exo_controls_fastforward, getString(R.string.ff),
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                        mContext, PlaybackStateCompat.ACTION_FAST_FORWARD));

        NotificationCompat.Action rewindAction = new NotificationCompat.Action(
                R.drawable.exo_controls_rewind, getString(R.string.rewind),
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                        mContext, PlaybackStateCompat.ACTION_REWIND));

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (mContext, 0, new Intent(mContext, getActivity().getClass()), 0);

        builder.setContentTitle(mStep.shortDescription)
                .setContentText(getString(R.string.notification_text))
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.brownies)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(rewindAction)
                .addAction(playPauseAction)
                .addAction(fowardAction)
                .setStyle(new NotificationCompat.MediaStyle()
                            .setMediaSession(mMediaSession.getSessionToken())
                            .setShowActionsInCompactView(0,1));

        mNotificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, builder.build());
    }

    private class MySessionCallback extends MediaSessionCompat.Callback {

        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onRewind() {
            long currentPostion = mExoPlayer.getCurrentPosition();
            mExoPlayer.seekTo(currentPostion - 5);
        }

        @Override
        public void onFastForward() {
            long currentPostion = mExoPlayer.getCurrentPosition();
            mExoPlayer.seekTo(currentPostion + 5);
        }
    }

    public static class MediaReceiver extends BroadcastReceiver {

        public MediaReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession, intent);
        }

    }
}

package com.example.wojtekkurylo.miwoklearnapp;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED;

public class ColorsActivity extends AppCompatActivity {

    private static final int COLOR_COLORS = Color.parseColor("#8800A0");
    private MediaPlayer mMediaPlayerWojtek;
    private AudioManager mAudioManagerWojtek;
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangerWojtek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManagerWojtek = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        mAudioFocusChangerWojtek = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                    mMediaPlayerWojtek.stop();
                    releaseMediaPlayer();
                } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    mMediaPlayerWojtek.pause();
                    mMediaPlayerWojtek.seekTo(0); // zacznie od 0 millisecond slowa, na ktorym przerwalo

                } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                    mMediaPlayerWojtek.start();
                }

            }

        };

        final ArrayList<Word> color = new ArrayList<Word>();

        Word colorOne = new Word("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red);
        color.add(colorOne);
        color.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        color.add(new Word("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        color.add(new Word("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        color.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        color.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        color.add(new Word("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        color.add(new Word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));


        WordAdapter colorAdapter = new WordAdapter(ColorsActivity.this, color, COLOR_COLORS);

        ListView colorList = (ListView) findViewById(R.id.list);
        colorList.setAdapter(colorAdapter);

        colorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                releaseMediaPlayer();
//                     Zachowuje z Variable typu Word Obiekt jaki zostal klikniety i wolam na nim metode przypisana do Word Class
                Word clickedWordObjectInArray = color.get(position); // get position of View clicked in ListView

                int audioManagerWojtekResult = mAudioManagerWojtek.requestAudioFocus(mAudioFocusChangerWojtek, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (audioManagerWojtekResult == AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayerWojtek = MediaPlayer.create(ColorsActivity.this, clickedWordObjectInArray.getAudioResourceId());

                    mMediaPlayerWojtek.start();

                    mMediaPlayerWojtek.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });


    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayerWojtek != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayerWojtek.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayerWojtek = null;
            mAudioManagerWojtek.abandonAudioFocus(mAudioFocusChangerWojtek);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("Colors Activity", "onStop");
        releaseMediaPlayer();
    }
}

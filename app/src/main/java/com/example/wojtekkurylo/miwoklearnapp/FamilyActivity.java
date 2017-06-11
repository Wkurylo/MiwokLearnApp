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

public class FamilyActivity extends AppCompatActivity {

    private static final int COLOR_FAMILY = Color.parseColor("#379237");
    private MediaPlayer mMediaPlayerWojtek;
    private AudioManager mAudioManagerWojtek;
    private AudioManager.OnAudioFocusChangeListener mAudioManagerChangerWojtek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManagerWojtek = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        mAudioManagerChangerWojtek = new AudioManager.OnAudioFocusChangeListener() {
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

        final ArrayList<Word> family = new ArrayList<Word>();

        family.add(new Word("father","әpә", R.drawable.family_father,R.raw.family_father));
        family.add(new Word("mother","әṭa", R.drawable.family_mother,R.raw.family_mother));
        family.add(new Word("son","angsi",R.drawable.family_son,R.raw.family_son));
        family.add(new Word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        family.add(new Word("older brother","taachi", R.drawable.family_older_brother,R.raw.family_older_brother));
        family.add(new Word("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        family.add(new Word("older sister","teṭe", R.drawable.family_older_sister,R.raw.family_older_sister));
        family.add(new Word("younger sister","kolliti", R.drawable.family_younger_sister,R.raw.family_younger_sister));
        family.add(new Word("grandmother","ama", R.drawable.family_grandmother,R.raw.family_grandmother));
        family.add(new Word("grandfather","paapa", R.drawable.family_grandfather,R.raw.family_grandfather));

        WordAdapter familyAdapter = new WordAdapter(FamilyActivity.this,family,COLOR_FAMILY);

        ListView familyList = (ListView) findViewById(R.id.list);
        familyList.setAdapter(familyAdapter);

        familyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                releaseMediaPlayer();

                Word clickedWordObjectInArray = family.get(position);

                int audioManagerResultsWojtek = mAudioManagerWojtek.requestAudioFocus(mAudioManagerChangerWojtek,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(audioManagerResultsWojtek == AUDIOFOCUS_REQUEST_GRANTED)
                {
                    mMediaPlayerWojtek = MediaPlayer.create(FamilyActivity.this,clickedWordObjectInArray.getAudioResourceId());

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
            mAudioManagerWojtek.abandonAudioFocus(mAudioManagerChangerWojtek);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("Family Activity","onStop");
        releaseMediaPlayer();
    }
}

package com.example.wojtekkurylo.miwoklearnapp;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    private static final int COLOR_PHRASES = Color.parseColor("#16AFCA");
    private MediaPlayer mMediaPlayerWojtek;
    private AudioManager mAudioManagerWojtek;
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangerWojtek;

    // Zeby za kazdym razem nie tworzyc nowego Anonimowego Obiektu,
    // Tworze Global variable przechowującą adres do w/w Obiektu z zaimplementowana metoda
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

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

        final ArrayList<Word> phrasesArrayList = new ArrayList<Word>();

        phrasesArrayList.add(new Word("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        phrasesArrayList.add(new Word("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        phrasesArrayList.add(new Word("My name is...","oyaaset...",R.raw.phrase_my_name_is));
        phrasesArrayList.add(new Word("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        phrasesArrayList.add(new Word("I’m feelilng good.","kuchi achit",R.raw.phrase_im_feeling_good));
        phrasesArrayList.add(new Word("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        phrasesArrayList.add(new Word("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        phrasesArrayList.add(new Word("I’m coming.","әәnәm",R.raw.phrase_im_coming));
        phrasesArrayList.add(new Word("Let’s go.","yoowutis",R.raw.phrase_lets_go));
        phrasesArrayList.add(new Word("Come here.","әnni'nem",R.raw.phrase_come_here));

        WordAdapter phrasesAdapter = new WordAdapter(PhrasesActivity.this,phrasesArrayList,COLOR_PHRASES);

        ListView phrasesList = (ListView) findViewById(R.id.list);
        //phrasesList.setBackgroundColor(COLOR_PHRASES);
        phrasesList.setAdapter(phrasesAdapter);

        phrasesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                releaseMediaPlayer();

                Word clickedWordObjectInArray = phrasesArrayList.get(position);

                int audioManagerResultsWojtek = mAudioManagerWojtek.requestAudioFocus(mAudioFocusChangerWojtek,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(audioManagerResultsWojtek == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    mMediaPlayerWojtek = MediaPlayer.create(PhrasesActivity.this,clickedWordObjectInArray.getAudioResourceId());
                    mMediaPlayerWojtek.start();

                    mMediaPlayerWojtek.setOnCompletionListener(mCompletionListener);
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
        Log.v("Phrases Activity","onStop");
        releaseMediaPlayer();
    }
}

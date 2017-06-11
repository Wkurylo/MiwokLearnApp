package com.example.wojtekkurylo.miwoklearnapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_GAIN;
import static android.media.AudioManager.AUDIOFOCUS_LOSS;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;
import static android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
import static com.example.wojtekkurylo.miwoklearnapp.R.id.numbers;


public class NumbersActivity extends AppCompatActivity {

    private static final int COLOR_NUMBERS = Color.parseColor("#FD8E09");
    private MediaPlayer mMediaPlayerWojtek;
    private AudioManager mAudioManagerWojtek;
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangerWojek;

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

        /**
         * This listener gets triggered whenever the audio focus changes
         * (i.e., we gain or lose audio focus because of another app or device).
         */
        mAudioFocusChangerWojek = new AudioManager.OnAudioFocusChangeListener()
        {
            @Override
            public void onAudioFocusChange(int focusChange) {
                if(focusChange ==  AudioManager.AUDIOFOCUS_LOSS)
                {
                    mMediaPlayerWojtek.stop();
                    releaseMediaPlayer();

                }else if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT){

                    // Pause playback and reset player to the start of the file. That way, we can
                    // play the word from the beginning when we resume playback.
                    mMediaPlayerWojtek.pause();
                    mMediaPlayerWojtek.seekTo(0); // zacznie od 0 millisecond slowa, na ktorym przerwalo

                }else if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                    mMediaPlayerWojtek.pause();
                    mMediaPlayerWojtek.seekTo(0); // zacznie od poczatku cale slowo, na ktorym przerwalo

                }else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                    mMediaPlayerWojtek.start();
                }

            }
        };

        // Creating Object ArrayList of type Word Object
        // Object has Instance Variable "word" which keeps address of new String Object

        // Do ArrayList o data type Word możemy jedynie dodać Obiekty typu Word w związku z tym
        // musimy zmienić poniższa liste slow

        final ArrayList<Word> word = new ArrayList<Word>();

        //word.add(0,"one");
        //Word w = new Word("one","lutti")
        //word.add(w);

        // czyli do ArrayList mozemy dodac slowa o takim samym Data Type = Word
        word.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        word.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        word.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        word.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        word.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        word.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        word.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        word.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        word.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        word.add(new Word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));

//        1. I am creating new Object ArrayAdapter with data type Word (it is Generic data type so can be any Object data type);
//        2. While creating this Object i am calling Constructor with 3 inputs (current class, predefined layout , created ArrayList)
//        3. I am anchoring/connecting the Java with layout where I want to display my ArrayList
//        4. I am adding to listView object, created Obiekt type Word which contains our ArrayList
//        
        // Tworzymy Obiekt ArrayAdapter typu String z Variable "itemsAdapter" - (Data Type tej Variable to WordAdapter)
        // Tworzymy w oparciu o konstruktora przyjmującego 3 parametry (context, ID of layout zawierające TextView, list fo words to display)
        // Adapter wie jak stworzyć layout dla każdego elementu/Obiektu z Listy Obiektow Word, używa do tego informacje z
        // activity_numbers_instructions_to_create_text_view.

        final WordAdapter numbersAdapter = new WordAdapter(NumbersActivity.this, word, COLOR_NUMBERS);

        // Tworzymy Variable ktora przechowuje adres do "list" zdefiniowanego w activity_numbers.xml

        final ListView numbersView = (ListView) findViewById(R.id.list);

        // setAdapter to metoda typu void , ListView Class i parametrem wejsciowym jest Obiektem ListView Class
        // Powodujemy, że ListView używa ArrayAdapter stworzone powyżej.
        //
        // setAdapter metod nalezy do ListView i tak wyglada: setAdapter(ListAdapter adapter);
        // czyli parametrem wejsciowym jest adapter o Data Type ListAdapter. Nasz itemAdapter jest Data Type ArrayAdapter;
        // Jedank jak na samej gorze Class ArrayAdapter klikniemy BaseAdapter (co z tego sie ArrayAdapter wywodzi)
        // to widzimy, ze BaseAdapter implements ListAdapter , czyli implementuje/zawiera metody wszystckie co ListAdapter
        // wiec można użyc ArrayAdapter zamiast ListAdapter

        numbersView.setAdapter(numbersAdapter);



        numbersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                //  Zachowuje z Variable typu Word Obiekt jaki zostal klikniety i wolam na nim metode przypisana do Word Class
                Word clickedWordObjectInArray = word.get(position); // get position of View clicked in ListView

                /**
                 * AudioManager implementation
                 */

                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int amResult = mAudioManagerWojtek.requestAudioFocus(mAudioFocusChangerWojek,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(amResult == AUDIOFOCUS_REQUEST_GRANTED)
                {
                    // We have audio focus now.

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mMediaPlayerWojtek = MediaPlayer.create(NumbersActivity.this, clickedWordObjectInArray.getAudioResourceId());

                    mMediaPlayerWojtek.start();

                    /**
                     * Setup a listener on the media player Class to stop and release memory usage
                     * while song has finished playing.
                     */
                    mMediaPlayerWojtek.setOnCompletionListener(mCompletionListener);
                }

            }
        });
        // Verifies If the String Object is at expected position

        int wordArraySize = word.size();
        int counter = 0;
        while (counter < wordArraySize) {
            Log.v("NumbersActivity", "Word at index\n " + counter + " is: " + word.get(counter));
            counter++;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("NumbersActivity", "onStop");
        releaseMediaPlayer();
    }
    /**
     * Release the media player if it currently exists because we are about to play another song.
     * Uzyteczny jak uztkownik naciska po kolei kazda piosenke
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

            // abandon AudioFocus when playback completed
            mAudioManagerWojtek.abandonAudioFocus(mAudioFocusChangerWojek);
        }
    }
}

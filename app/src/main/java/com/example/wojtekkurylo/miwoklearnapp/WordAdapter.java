package com.example.wojtekkurylo.miwoklearnapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
* {@link WordAdapter} is an Custom Class of  {@link ArrayAdapter} that can provide the layout for each list
* based on a data source, which is a list of {@link ArrayAdapter} objects with custom object type <Word>.
* */

public class WordAdapter extends ArrayAdapter<Word> {

    /**
     * Resource ID for the background color of word container (with TextView)
     */

    private int mColorTextViewId;
//    private MediaPlayer mMediaPlayerWojtek;

    /**
     * Clean up the media player by releasing its resources.
     */
//    private void releaseMediaPlayer() {
//        // If the media player is not null, then it may be currently playing a sound.
//        if (mMediaPlayerWojtek != null) {
//            // Regardless of the current state of the media player, release its resources
//            // because we no longer need it.
//            mMediaPlayerWojtek.release();
//
//            // Set the media player back to null. For our code, we've decided that
//            // setting the media player to null is an easy way to tell that the media player
//            // is not configured to play an audio file at the moment.
//            mMediaPlayerWojtek = null;
//        }
//    }

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context.
     * @param listToDisplay A List of words objects to display in a list
     */
    public WordAdapter(Context context, ArrayList<Word> listToDisplay, int colorTextViewId) {

        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        // Ponieważ int resource polega na getView method a my ją Override wiec nie chcemy korzytac z rozwiazania od ArrayAdapter,
        // tylko z własnej getView method

        super(context, 0, listToDisplay);
        mColorTextViewId = colorTextViewId;
    }
    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation/tworzenie nowych Obiektow.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Ponieważ to ListView za pomoca metody getView wysyła zapytanie o dalsze dane do wyswietlania
        // to w metodzie getView wysyła konkretne wartosci i my w naszej klasie musimy je odczytac i
        // zrobic update tego co ma wyswietlic


        // Sprawdz jaki Obiekt jest na pozycji jakiej ListView potrzebuje i zapisz jego wartosc w naszej zmiennej // Get the {@link Word} object located at this position in the list
        // metoda getItem(position) zwraca Obiekt - czyli nasz obiekt Word z dwoma slowami (Miwok + Eng)
        final Word currentWord = getItem(position);

        // Sprawdz czy mozna użyć już nieużywane wcześniej stworzony Obiekt, jak nie to stwórz nowy Obiekt
        View anotherListItemView = convertView;

        if (anotherListItemView == null)
        {
            anotherListItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_numbers_instructions_to_create_text_view, parent, false);

        }


        // Zakotwiczamy TextView Obiektu Word w którym jest slowo po Ang // Find the TextView in the english_text_view.xml layout with the ID version_name
        // Interesuje nas slowo po ang zgodne z zapytaniem wysłanym przez ListView wiec
        // anotherListItemView.findViewById...
        TextView defaultTextView = (TextView) anotherListItemView.findViewById(R.id.english_text_view);
        // Wykorzystujemy metody Word Obiektu i bierzemy slowo po Ang
        defaultTextView.setText(currentWord.getDefaultTranslation());

        TextView miwokTextView = (TextView) anotherListItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currentWord.getMiwokTranslation());

        ImageView imageView = (ImageView) anotherListItemView.findViewById(R.id.icon);

        // Sprawdzamy czy w danym Obiekcie Word zostało wykorzystane zdjęcie
        // Oznacza, ze jezeli true to niech zrobi to co nizej
        if(currentWord.checkIfImageRequired())
        {
            imageView.setImageResource(currentWord.getImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        }
        else
        {
            imageView.setVisibility(View.GONE);
        }


        /**
         * Setting color of view with text in English and Miwok
         */

        View textViewContainer = anotherListItemView.findViewById(R.id.textViewContainer);
        textViewContainer.setBackgroundColor(mColorTextViewId);

//        mMediaPlayerWojtek = MediaPlayer.create(getContext(),currentWord.getAudioResourceId());
//        anotherListItemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Word currentWord = getItem(position);
//
//                releaseMediaPlayer();
//
//                mMediaPlayerWojtek = MediaPlayer.create(getContext(),currentWord.getAudioResourceId());
//
//                mMediaPlayerWojtek.start();
//                mMediaPlayerWojtek.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        releaseMediaPlayer();
//                    }
//                });
//            }
//        });

        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        // anotherListItemView staje sie Child View of Adapter View
        return anotherListItemView;
    }
}

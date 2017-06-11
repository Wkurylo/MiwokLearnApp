package com.example.wojtekkurylo.miwoklearnapp;

import android.graphics.Picture;
import android.media.Image;

/**
 * {@link Word} represent the word that the user want to learn
 * It contains a default and Miwok translation
 */

public class Word {

    // instance variables / state
    /** Default translation of word */
    private String mDefaultTranslation;

    /** Miwok translation of word , m because it is with private Access Modifier*/
    private String mMiwokTranslation;

    /** Drawable resource to image assiociated with word*/
    private int mImage = NO_IMAGE;

    /** Static - associated with Class, final - value cannoch change. Constant Variable*/
    private static final int NO_IMAGE = -1;

    /** Raw resource to audio file assiociated with word*/
    private int mSongId;

    /**
     * Constructor for objects of class Word
     *
     * Create a nwe Word Object
     *
     * @param defaultTranslation is the word in a language that user is familiar with
     * @param miwokTranslation is the word in Miwok Language
     * @param songId is the raw resource ID for the audio file associated with current word

     */
    public Word(String defaultTranslation, String miwokTranslation, int songId)
    {
        this.mDefaultTranslation = defaultTranslation;
        this.mMiwokTranslation = miwokTranslation;
        this.mSongId = songId;
    }

    /**
     * Constructor for objects of class Word
     *
     * Create a nwe Word Object
     * We are assigning some value to mImage that i why will return false in checkIfImageRequired method in Class
     *
     * @param defaultTranslation is the word in a language that user is familiar with
     * @param miwokTranslation is the word in Miwok Language
     * @param image is the drawable resource ID for the image associated with current word
     * @param songId is the raw resource ID for the audio file associated with current word
     */
    public Word(String defaultTranslation, String miwokTranslation, int image, int songId)
    {
        this.mDefaultTranslation = defaultTranslation;
        this.mMiwokTranslation = miwokTranslation;
        this.mImage = image;
        this.mSongId = songId;
    }

    /**
     *
     * Method return String Object data type, but us not taking any input parameter
     *
     */
    public String getMiwokTranslation()
    {
        return mMiwokTranslation;
    }
    public String getDefaultTranslation()
    {
        return mDefaultTranslation;
    }
    public int getImageResourceId()
    {
        return mImage;
    }
    public int getAudioResourceId()
    {
        return mSongId;
    }

    /**
     * Method to check if in Class Word has been assigned value to mImage variable
     * This method is used in WordAdapter Class
     *
     * @return true If value of mImage is NOT equal to constant variable NO_IMAGE = -1;
     */
    public boolean checkIfImageRequired()
    {
        return mImage != NO_IMAGE;
    }
}

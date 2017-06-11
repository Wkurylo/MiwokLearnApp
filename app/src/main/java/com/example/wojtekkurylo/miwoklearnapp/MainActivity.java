/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.wojtekkurylo.miwoklearnapp;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import static com.example.wojtekkurylo.miwoklearnapp.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(activity_main);


        // Find the View that shows the numbers category
        final TextView numbers = (TextView) findViewById(R.id.numbers);
        // Set a click listener on that View
        numbers.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {

                // Clickable effect
                int[] attrs = new int[]{R.attr.selectableItemBackground};
                TypedArray typedArray = MainActivity.this.obtainStyledAttributes(attrs);
                int backgroundResource = typedArray.getResourceId(0, 0);
                numbers.setBackgroundResource(backgroundResource);

                // Fire up next activity
                Intent numbersIntent = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(numbersIntent);
            }
        });

        // Find the View that shows the numbers category
        TextView phrases = (TextView) findViewById(R.id.phrases);

        // Set a click listener on that View
        phrases.setOnClickListener(new View.OnClickListener() {

            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent phrasesIntent = new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(phrasesIntent);
            }
        });

        // Find the View that shows the numbers category
        TextView family = (TextView) findViewById(R.id.family);

        // Set a click listener on that View
        family.setOnClickListener(new View.OnClickListener() {

            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent familyIntent = new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(familyIntent);
            }
        });

        // Find the View that shows the numbers category
        TextView colors = (TextView) findViewById(R.id.colors);

        // Set a click listener on that View
        colors.setOnClickListener(new View.OnClickListener() {

            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent colorsIntent = new Intent(MainActivity.this, ColorsActivity.class);
                startActivity(colorsIntent);
            }
        });

//        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /* declaration of method to open activity_numbers.xml layout
    * onCLick method is type: void // with input parameter type View
    *
    * (view or v )= Called when view has been clicked
    *
    * source: https://developer.android.com/reference/android/view/View.OnClickListener.html
    * source: http://www.vogella.com/tutorials/AndroidIntent/article.html#usingintents_call
    *
    * Celem stworzenia objektu Intent z variable która przechowuje adres,
    * musimy użyć Constructora
    *
    * Constructor public Intent() - Class Intent z Constructoremzostal zdefiniowany przez Android team wiec nie musimy go tworzyc
    * Tak on wyglada: public Intent(Context packageContext,Class<?> cls)
    *
    * source: https://developer.android.com/reference/android/content/Intent.html#Intent(android.content.Context,%20java.lang.Class<?>)
    *
    * JEZELI wywolujemy metode z xml to zawsze metoda ma parametr wejsciowy typu View (View view)
    * dopiero z tej metody wolamy kolejne metody.
    */
    public void openNumbersList(View view)
    {
        Intent nextActivity = new Intent(MainActivity.this, NumbersActivity.class);
        startActivity(nextActivity);
    }

}

package com.petmate.fcfm.petmate;

import android.app.Activity;
import android.os.Bundle;

import com.petmate.fcfm.petmate.fragmentos.CardFrontFragment;

/**
 * Created by alfonso on 07/05/15.
 */
public class CardFlipActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_card_flip);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new CardFrontFragment())
                    .commit();
        }
    }
}

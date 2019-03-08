package com.mithrilmania.blocktopograph;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.mithrilmania.blocktopograph.worldlist.WorldItemListActivity;

import java.io.IOException;
import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    // Splash screen timer, in milliseconds
    private static final int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //1. Show the already loaded small logo first, while loading the sharper bigger one
        //2. Replace with the sharper one as soon as it is loaded
        //3. Keep the splash-screen open till the SPLASH_TIME_OUT (started at #1) is over
        //4. Done! Some quick brand-awareness.

        // Hey proto where did you copy these shit from...
        // Why did you went through 3rd party outdated Android tutorial
        // instead the official one.........

        //Comment: yes, splash-screens are against Material-design android guide-lines,
        // but brand-awareness is important. And it is a very quick splash (just 1 sec!)
        ImageView brandLogoView = this.findViewById(R.id.brandLogo);

        /*
         * Showing splash screen with a timer. This will be useful when you
         * want to show case your app logo / company
         */
        new Handler().postDelayed(() -> {
            // This method will be executed once the timer is over
            // Start your app main activity
            Intent i = new Intent(SplashScreen.this, WorldItemListActivity.class);
            SplashScreen.this.startActivity(i);

            //user can not go back to splashscreen
            SplashScreen.this.finish();

        }, SPLASH_TIME_OUT);


        // #2: load sharp brand image
        try {
            // get input stream
            InputStream ims = getAssets().open("icon.png");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            brandLogoView.setImageDrawable(d);
        } catch (IOException ex) {
            //fail, but xml has app icon as backup logo (lower resolution, from #1)
        }
    }

}

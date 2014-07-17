package com.wrapp.example.floatlabelededittext;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.wrapp.floatlabelededittext.FloatLabeledEditText;


public class MainActivity extends ActionBarActivity {
    FloatLabeledEditText typefacedEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        typefacedEditText = (FloatLabeledEditText) findViewById(R.id.typefacedEditText);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "RobotoCondensed-LightItalic.ttf");
        typefacedEditText.setHintTypeFace(typeface);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

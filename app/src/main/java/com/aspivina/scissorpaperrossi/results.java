package com.aspivina.scissorpaperrossi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class results extends AppCompatActivity {
    private TextView m_text_results;
    private String mScores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        m_text_results = (TextView) findViewById(R.id.tv_total_result);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mScores = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                m_text_results.setText(mScores);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.menu_scores, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_back) {
            //VOLVER ATRAS?!

            return true;
        }

        // TODO (2) Launch the map when the map menu item is clicked

        return super.onOptionsItemSelected(item);
    }

}

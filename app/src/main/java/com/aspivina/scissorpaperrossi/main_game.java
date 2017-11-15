package com.aspivina.scissorpaperrossi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class main_game extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener{
    private Integer[] results={0,0,0};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);



        // Register MainActivity as a OnSharedPreferenceChangedListener in onCreate
        /*
         * Register MainActivity as an OnPreferenceChangedListener to receive a callback when a
         * SharedPreference has changed. Please note that we must unregister MainActivity as an
         * OnSharedPreferenceChanged listener in onDestroy to avoid any memory leaks.
         */
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }



    protected void onStart(){
        super.onStart();

        TextView tv_username = (TextView) findViewById(R.id.name_box);


        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        String keyUserName = getString(R.string.pref_namebox_key);
        String userNameDefault = prefs.getString(getString(R.string.pref_namebox_key),
                getString(R.string.pref_namebox_default));

        tv_username.setText(userNameDefault);

        String showCheckboxKey = getString(R.string.pref_checkbox_key);
        Boolean showCheckboxDefault = prefs.getBoolean(getString(R.string.pref_checkbox_key),
                getResources().getBoolean(R.bool.pref_checkbox_default));
        if(showCheckboxDefault){
            tv_username.setVisibility(View.VISIBLE);

        }else{
            tv_username.setVisibility(View.INVISIBLE);

        }
        String beingCool = prefs.getString(getString(R.string.pref_list_key),
                getString(R.string.pref_list_value_1));
        String cool = getString(R.string.pref_list_value_1);
        if (cool.equals(beingCool)){
            tv_username.setText(getString(R.string.pref_namebox_cool));
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    public int my_game(int user_option){
        Random rand = new Random();
        int computer_play = rand.nextInt(3)+1;
        String C_Says="";
        int results = 3; // 1 player, 2 computer, 3 it's a tie
        TextView computer = (TextView) findViewById(R.id.tv_computer_move);
        TextView tv_computer = (TextView) findViewById(R.id.tv_result);
        tv_computer.setText("Computer says..");
        switch (computer_play){
            case 1 : {
                C_Says=" Paper";
                break;
            }
            case 2 : {
                C_Says=" Rock";
                break;
            }
            case 3 : {
                C_Says=" Scissor";
                break;
            }
        }
        computer.setGravity(Gravity.CENTER|Gravity.RIGHT);
        computer.setText(C_Says);
        switch (user_option+computer_play){
            case 3: {
                if ((user_option - computer_play) == (-1)) {  // Paper-Rock
                    results = 1;
                } else {
                    results = 2;
                }
                break;
            }
            case 5: {if( (user_option-computer_play) == (-1)){  // Rock-Scissor
                results=1;
            } else{ results=2;}
                break;}
            case 4: {
                if (user_option != computer_play) {
                    if ((user_option - computer_play) == (2)) {
                        results = 1;
                        break;
                    } else {
                        results = 2;
                        break;
                    }
                }
            }

        }
        return results;
    } // my_game
    public boolean onOptionsItemSelected(MenuItem item){
        RadioGroup My_group = (RadioGroup) findViewById(R.id.radio_game);
        int id_option = My_group.getCheckedRadioButtonId();
        int user_option = 0;
        int winner = 0;
        String S_winner;
        TextView Msg_win= (TextView)findViewById(R.id.tv_winner);
        TextView Temp_results = (TextView) findViewById(R.id.tv_temp_result);
        int my_menu_option = item.getItemId();
        if (my_menu_option == R.id.action_play) { // If the option_menu selected is the one we looking for
            Context context = main_game.this; // main activity object
            // here we should call the function for the game per se
            // but for now we only will show some stactic text
            String msg ="here!";
            String msg1 = "your choice: ";
            switch (id_option){
                case R.id.radio_paper : {
                    msg1+=" Paper";
                    user_option = 1;
                    break;
                }
                case R.id.radio_rock : {
                    msg1+=" Rock";
                    user_option = 2;
                    break;
                }
                case R.id.radio_scissor : {
                    msg1+=" Scissor";
                    user_option = 3;
                    break;
                }
            }
            if (user_option>0){
                //Toast.makeText(context,msg1,Toast.LENGTH_LONG).show();
                winner = my_game(user_option);
                switch (winner){
                    case 1 : {
                        Msg_win.setTextColor(Color.rgb(0,204,0));
                        S_winner="You are the winner!!!";

                        results[0]+=1;
                        results[1]+=1;
                        user_option = 1;
                        break;
                    }
                    case 2 : {
                        Msg_win.setTextColor(Color.RED);
                        S_winner="Computer wins!!!";
                        results[0]+=1;
                        results[2]+=1;
                        user_option = 2;
                        break;
                    }
                    case 3 : {
                        Msg_win.setTextColor(Color.YELLOW);
                        //Msg_win.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        results[0]+=1;
                        S_winner="Its a TYLER!!";
                        break;
                    }
                    default: {
                        Msg_win.setTextColor(Color.MAGENTA);
                        S_winner="Stuff happened";
                        break;
                    }
                }
                Msg_win.setGravity(Gravity.CENTER|Gravity.CENTER);
                Msg_win.setText(S_winner);
                Temp_results.setText(results[0].toString()+" - "+results[1].toString()+" - "+results[2].toString());
            }
            return true;
        }
        if(my_menu_option == R.id.action_scores){
            Context context = main_game.this;
            Class destinationClass = results.class;
            Intent intentToStartDetailActivity = new Intent(context, destinationClass);
            intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, results[0].toString()+" - "+results[1].toString()+" - "+results[2].toString() );
            startActivity(intentToStartDetailActivity);
            return true;
        }
        if (my_menu_option == R.id.action_settings){
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onSharedPreferenceChanged (SharedPreferences sharedPreferences, String s){


    }

}

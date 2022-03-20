package com.example.lenselink_final;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    //private SharedPreferences savedValues;
    private TextView textview_turnplayer;
    private TextView textviewScorePlayerX;
    private TextView textviewScorePlayerO;
    private String PlayerXName;
    private String PlayerOName;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button_newGame;
    private Button button_reset;

    private SharedPreferences prefs;
    private String turnPlayer;
    private String turnPLayerName;
    private int playerXPoints;
    private int playerOPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
            // set the default values for the preferences in onCreate
        PreferenceManager.setDefaultValues(this,R.xml.preferences,false);

    // get default SharedPreferences object in onCreate
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        textview_turnplayer = findViewById(R.id.textview_turnplayer);
        textviewScorePlayerX = findViewById(R.id.textviewScorePlayerX);
        textviewScorePlayerO = findViewById(R.id.textviewScorePlayerO);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        button_newGame = findViewById(R.id.button_newGame);
        button_reset = findViewById(R.id.button_reset);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button_newGame.setOnClickListener(this);
        button_reset.setOnClickListener(this);

        ResetPoints();
        turnPlayer = "X";
        PlayerXName = prefs.getString("pref_name_one", "X");
        PlayerOName = prefs.getString("pref_name_two", "O");
        turnPLayerName = PlayerXName;
        playerXPoints = 0;
        playerOPoints = 0;
        System.out.println(String.valueOf(playerXPoints));
        System.out.println(String.valueOf(playerOPoints));
        ResetGame();
        UpdateScores();
    }

    @Override
    protected void onPause() {
        // save the instance variables
        SharedPreferences.Editor editor = prefs.edit();
        editor = prefs.edit();
        String playerX = prefs.getString("pref_name_one", "X");
        String playerO = prefs.getString("pref_name_two", "O");
        editor.putString("player_x", playerX);
        editor.putString("player_o", playerO);
        editor.putInt("player_x_score", playerXPoints);
        editor.putInt("player_o_score", playerOPoints);
        editor.apply();
        editor.commit();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // get name from preferences and display it
        PlayerXName = "X";
        PlayerOName = "O";
        turnPlayer = prefs.getString("turn_player", "");
        PlayerXName = prefs.getString("player_x", "");
        PlayerOName = prefs.getString("player_o", "");
        playerXPoints = prefs.getInt("player_x_score", 0);
        playerOPoints = prefs.getInt("player_o_score", 0);
        textview_turnplayer.setText(String.valueOf(turnPlayer));
        textviewScorePlayerX.setText(PlayerXName);
        textviewScorePlayerO.setText(PlayerOName);
        UpdateScores();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings){
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ActivitySettings.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case(R.id.button_newGame):
                ResetGame();
                UpdateScores();
                break;
            case(R.id.button_reset):
                ResetPoints();
                ResetGame();
                UpdateScores();
                break;
            case(R.id.button1):
                button1.setText(turnPlayer);
                break;
            case(R.id.button2):
                button2.setText(turnPlayer);
                break;
            case(R.id.button3):
                button3.setText(turnPlayer);
                break;
            case(R.id.button4):
                button4.setText(turnPlayer);
                break;
            case(R.id.button5):
                button5.setText(turnPlayer);
                break;
            case(R.id.button6):
                button6.setText(turnPlayer);
                break;
            case(R.id.button7):
                button7.setText(turnPlayer);
                break;
            case(R.id.button8):
                button8.setText(turnPlayer);
                break;
            case(R.id.button9):
                button9.setText(turnPlayer);
                break;
            default:
                break;
        }
        String winner = checkForWinner();
        if(winner != null && winner == "X")
        {
            playerXPoints++;
            UpdateScores();
            textview_turnplayer.setText(turnPLayerName + " wins!");
        }
        else if(winner != null && winner == "O")
        {
            playerOPoints++;
            UpdateScores();
            textview_turnplayer.setText(turnPLayerName + " wins!");
        }
        else if(winner == "Tie")
        {
            UpdateScores();
            textview_turnplayer.setText("Tie Game");
        }
        else
        {
            toggleTurnPlayer();
            UpdateScores();
        }
    }

    public void toggleTurnPlayer()
    {
        if(turnPlayer.toUpperCase() == "X")
        {
            turnPLayerName = PlayerOName;
            turnPlayer = "O";
        }
        else if(turnPlayer.toUpperCase() == "O")
        {
            turnPLayerName = PlayerXName;
            turnPlayer = "X";
        }
        else
        {
            turnPLayerName = PlayerXName;
            turnPlayer = "X";
        }
    }

    public String checkForWinner()
    {
        String a1 = button1.getText().toString();
        String a2 = button2.getText().toString();
        String a3 = button3.getText().toString();
        String a4 = button4.getText().toString();
        String a5 = button5.getText().toString();
        String a6 = button6.getText().toString();
        String a7 = button7.getText().toString();
        String a8 = button8.getText().toString();
        String a9 = button9.getText().toString();
        String winner = "none";

        if(a1 != "" && a1 == a2 && a2 == a3)
        {
            button1.setBackgroundColor(Color.GREEN);
            button2.setBackgroundColor(Color.GREEN);
            button3.setBackgroundColor(Color.GREEN);
            winner = a1;
            disableButtons();
        }
        if(a1 != "" && a1 == a5 && a5 == a9)
        {
            button1.setBackgroundColor(Color.GREEN);
            button5.setBackgroundColor(Color.GREEN);
            button9.setBackgroundColor(Color.GREEN);
            winner = a1;
            disableButtons();
        }
        if(a1 != "" && a1 == a4 && a4 == a7)
        {
            button1.setBackgroundColor(Color.GREEN);
            button4.setBackgroundColor(Color.GREEN);
            button7.setBackgroundColor(Color.GREEN);
            winner = a1;
            disableButtons();
        }
        if(a2 != "" && a2 == a5 && a5 == a8)
        {
            button2.setBackgroundColor(Color.GREEN);
            button5.setBackgroundColor(Color.GREEN);
            button8.setBackgroundColor(Color.GREEN);
            winner = a2;
            disableButtons();
        }
        if(a3 != "" && a3 == a6 && a6 == a9)
        {
            button3.setBackgroundColor(Color.GREEN);
            button6.setBackgroundColor(Color.GREEN);
            button9.setBackgroundColor(Color.GREEN);
            winner = a3;
            disableButtons();
        }
        if(a4 != "" && a4 == a5 && a5 == a6)
        {
            button4.setBackgroundColor(Color.GREEN);
            button5.setBackgroundColor(Color.GREEN);
            button6.setBackgroundColor(Color.GREEN);
            winner = a4;
            disableButtons();
        }
        if(a7 != "" && a7 == a5 && a5 == a3)
        {
            button7.setBackgroundColor(Color.GREEN);
            button5.setBackgroundColor(Color.GREEN);
            button3.setBackgroundColor(Color.GREEN);
            winner = a7;
            disableButtons();
        }
        if(a7 != "" && a7 == a8 && a8 == a9)
        {
            button7.setBackgroundColor(Color.GREEN);
            button8.setBackgroundColor(Color.GREEN);
            button9.setBackgroundColor(Color.GREEN);
            winner = a7;
            disableButtons();
        }
        if(a1 != "" && a2 != "" && a3 != "" &&
                a4 != "" && a5 != "" && a6 != "" &&
                a7 != "" && a8 != "" && a9 != "" && winner == "none")
        {winner = "Tie";}

        return winner;
    }

    @SuppressLint("SetTextI18n")
    public void UpdateScores()
    {
        textview_turnplayer.setText(turnPLayerName + "'s turn");
        textviewScorePlayerX.setText(PlayerXName + " score:" + String.valueOf(playerXPoints));
        textviewScorePlayerO.setText(PlayerOName + " score:" + String.valueOf(playerOPoints));
    }

    public void ResetGame()
    {
        turnPlayer = "X";
        turnPLayerName = PlayerXName;
        button1.setText("");
        button2.setText("");
        button3.setText("");
        button4.setText("");
        button5.setText("");
        button6.setText("");
        button7.setText("");
        button8.setText("");
        button9.setText("");
        button1.setBackgroundColor(Color.GRAY);
        button2.setBackgroundColor(Color.GRAY);
        button3.setBackgroundColor(Color.GRAY);
        button4.setBackgroundColor(Color.GRAY);
        button5.setBackgroundColor(Color.GRAY);
        button6.setBackgroundColor(Color.GRAY);
        button7.setBackgroundColor(Color.GRAY);
        button8.setBackgroundColor(Color.GRAY);
        button9.setBackgroundColor(Color.GRAY);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);
        button6.setEnabled(true);
        button7.setEnabled(true);
        button8.setEnabled(true);
        button9.setEnabled(true);
    }

    public void disableButtons()
    {
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
        button7.setEnabled(false);
        button8.setEnabled(false);
        button9.setEnabled(false);
    }

    public void ResetPoints()
    {
        playerXPoints = 0;
        playerOPoints = 0;
    }
}
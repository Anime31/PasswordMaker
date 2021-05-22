package com.example.passwordgenerator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.content.ClipboardManager;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String password = "";
    private String characters = "";
    final String upperCase = "QWERTYUIOPASDFGHJKLZXCVBNM";
    final String numbers = "0123456789";
    final String specialCharacters = "!@#$%^&*";

    private Button btnMain;
    private Button btnClipboard;
    private TextView txtMain;
    private TextView txtPassLength;
    private SeekBar seek;
    private Switch switchUpr;
    private Switch switchNum;
    private Switch switchSpc;
    private Switch switchPin;

    int progressChange = 4;     //Used in Seekbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMain = (Button) findViewById(R.id.btnMain);
        btnClipboard = (Button) findViewById(R.id.btnClipboard);
        txtMain = (TextView) findViewById(R.id.txtMain);
        txtPassLength = (TextView) findViewById(R.id.txtPassLength);
        switchUpr = (Switch) findViewById(R.id.switchUpr);
        switchNum = (Switch) findViewById(R.id.switchNum);
        switchSpc = (Switch) findViewById(R.id.switchSpc);
        switchPin = (Switch) findViewById(R.id.switchPin);
        seek = (SeekBar) findViewById(R.id.seek);

        switchUpr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    switchPin.setChecked(false);
                }
            }
        });
        switchNum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    switchPin.setChecked(false);
                }
            }
        });
        switchSpc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    switchPin.setChecked(false);
                }
            }
        });
        switchPin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    switchUpr.setChecked(false);
                    switchNum.setChecked(false);
                    switchSpc.setChecked(false);
                }
            }
        });

        btnMain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuilder characterbuild = new StringBuilder();
                characterbuild.append("qwertyuiopasdfghjklzxcvbnm");
                if(switchUpr.isChecked()) {
                    characterbuild.append(upperCase);
                }
                if(switchNum.isChecked()) {
                    characterbuild.append(numbers);
                }
                if(switchSpc.isChecked()) {
                    characterbuild.append(specialCharacters);
                }
                if(switchPin.isChecked()) {
                    characterbuild.setLength(0);
                    characterbuild.append(numbers);
                }

                characters = characterbuild.toString();
                password = getRandomString(progressChange);
                txtMain.setText(password);
            }
        });
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress += 4;                                                                       //Seekbar starts from 4
                progressChange = progress;

                txtPassLength.setText(String.valueOf(progressChange));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnClipboard.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("PASSWORD", password);
                clipboard.setPrimaryClip(clip);

                Toast toast = Toast.makeText(getApplicationContext(), "Copied!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0,0);
                toast.show();
            }
        });

    }
    public String getRandomString(int i) {

        StringBuilder result = new StringBuilder();
        while(i>0) {
            Random rand = new Random();
            result.append(characters.charAt(rand.nextInt(characters.length())));
            i --;
        }
        return result.toString();
    }
}
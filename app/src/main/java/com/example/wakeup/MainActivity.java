package com.example.wakeup;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.content.Context;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements MessageListener {

    //Permission asking
    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 0;

    AudioManager audioManager;
    MediaPlayer mediaPlayer;
    Button button;
    ImageView imageView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.about:
                Intent intent = new Intent(getApplicationContext(),About.class);
                startActivity(intent);
            return true;
            case R.id.settings:
                Intent intent2 = new Intent(getApplicationContext(),settings.class);
                startActivity(intent2);
                return true;
                default:
                    return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyReceiver.bindListener(this);
        mediaPlayer = MediaPlayer.create(this, R.raw.spring);
        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        button = findViewById(R.id.button2);
        imageView = findViewById(R.id.imageView);

        //check permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            //If USer denied the permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {
                //User has denied
            } else {
                // a pop appear
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
            }
        }

        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(intent);
        }



    }

    //Result Of Permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_RECEIVE_SMS: {
                //check length and grant of permission
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Now broadcast reciever will work
                    Toast.makeText(this, "Thank You !! For Permitting", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "You didn't grant I cant do anything", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void messageReceived(String msg, String phoneNo) {
        // && phoneNo.equals("8295480960")
        if (msg.equals("WAKE UP")) {
            Toast.makeText(this, "Wake UP  PLease!!", Toast.LENGTH_LONG).show();
            Log.i("CONTACT",phoneNo);
            if (button.getText().equals("STOP")){
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0);
                Log.i("STATUS","I am in");
                start();
            }
    }
        }

        public void clicked(View view)
        {
            if(button.getText().equals("START"))
            {
                button.setText("STOP");
                imageView.setImageResource(R.drawable.sleep);

            }
            else
            {
                button.setText("START");
                imageView.setImageResource(R.drawable.wake);
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer = MediaPlayer.create(this, R.raw.spring);
            }
        }




    public void start() {

    if(audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL){
    mediaPlayer.start();
     }
    }
}

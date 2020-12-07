package com.example.textmessageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver;
    String messageReceived = "";
    String numberReceived = "";
    int stateNumber = 0;
    String[][] states = new String[][]{
            //state 1: greeting
            {"Hey! let's go on a date!", "Hi! We should go out", "Hello! let's go out somewhere"},

            //State 2: question why
            {"I just want to hang out with you", "To spend time with you", "I am hungry and want to eat with you"},

            //State 3: question time
            {"Are you free around 8:00", "I booked us at 8:00", "Let's go at 8:00"},

            //State 4: question place
            {"Chipotle", "Taco Bell", "Buffalo Wild Wings", "Pizza Hut"},

            //State 5: Goodbye
            {"Alright see you there", "Okay bye! I will see you there", "Alright sounds like a plan"}
    };

    String[] stateText = new String[]{"Greeting State", "Asking why state", "Asking time state",
            "Ask place state", "Acknowledge/Goodbye state"};

    TextView textView;

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 0);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 0);
        }

        textView = findViewById(R.id.textView);
        textView.setText(stateText[stateNumber]);

        //RECEIVE
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Bundle bundle = intent.getExtras();

                if(bundle != null){
                Object[] pdus = (Object[])bundle.get("pdus");
                SmsMessage[] smsMessages = new SmsMessage[pdus.length];
                for(int i=0;i<pdus.length;i++) {
                    byte[] b = (byte[]) pdus[i];
                    smsMessages[i] = SmsMessage.createFromPdu(b, bundle.getString("format"));
                    messageReceived = smsMessages[i].getMessageBody();
                    numberReceived = smsMessages[i].getOriginatingAddress();

                    evaluate(messageReceived);
                }
                }

            }
        };

        registerReceiver(broadcastReceiver, (new IntentFilter("android.provider.Telephony.SMS_RECEIVED")));


        //AI



    }
    public void send(final String message){
        //SEND
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SmsManager manager = SmsManager.getDefault();
                manager.sendTextMessage(numberReceived, null, message, null, null);
            }
        }, 3000);
    }

    public void evaluate(final String messageReceived){
        if(stateNumber == 0 && (messageReceived.toLowerCase().contains("hi") ||
                messageReceived.toLowerCase().contains("hello") || messageReceived.toLowerCase().contains("hey"))){
            int rand = (int)(Math.random()*3);
            String messageSend = states[stateNumber][rand];
            send(messageSend);
            stateNumber++;
            textView.setText(stateText[stateNumber]);
        }
        else if(stateNumber == 1 && (messageReceived.toLowerCase().contains("why") ||
                messageReceived.toLowerCase().contains("reason"))){
            int rand = (int)(Math.random()*3);
            String messageSend = states[stateNumber][rand];
            send(messageSend);
            stateNumber++;
            textView.setText(stateText[stateNumber]);
        }
        else if(stateNumber == 2 && (messageReceived.toLowerCase().contains("when") ||
                messageReceived.toLowerCase().contains("time"))){
            int rand = (int)(Math.random()*3);
            String messageSend = states[stateNumber][rand];
            send(messageSend);
            stateNumber++;
            textView.setText(stateText[stateNumber]);
        }
        else if(stateNumber == 3 && (messageReceived.toLowerCase().contains("where") ||
                messageReceived.toLowerCase().contains("place"))){
            int rand = (int)(Math.random()*4);
            String messageSend = states[stateNumber][rand];
            send(messageSend);
            stateNumber++;
            textView.setText(stateText[stateNumber]);
        }
        else if(stateNumber == 4 && (messageReceived.toLowerCase().contains("yes") ||
                messageReceived.toLowerCase().contains("yeah") || messageReceived.toLowerCase().contains("sure") ||
                messageReceived.toLowerCase().contains("down"))){
            int rand = (int)(Math.random()*4);
            String messageSend = states[stateNumber][rand];
            send(messageSend);
            stateNumber++;
            textView.setText("Date Bot is done");
        }
        else{
            if(stateNumber != 5) {
                send("Sorry. I dont understand what you are trying to say. Please make your message more clear");
                textView.setText("Unknown response : State of confusion");
            }
        }
    }
}

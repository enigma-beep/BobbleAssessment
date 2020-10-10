package com.abhigyan.bobbleassessment;


import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;


public class QueryChat extends AppCompatActivity {
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2;
    TextView tv;
    SeekBar tSeekBar;
    int p = 20;

    private Handler repeatUpdateHandler = new Handler();
    private boolean mAutoIncrement = false;

    private long firstTime=0,secondTime=0;
    public int mValue= 20;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_chat);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/



        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout)findViewById(R.id.layout2);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        tv=findViewById(R.id.mytext);
        tSeekBar=findViewById(R.id.textSeekBar);



        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://mychatapp-eaf03.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://mychatapp-eaf03.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);

        tv.setText(UserDetails.chatWith);

        /*sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });*/
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tSeekBar.setProgress(tSeekBar.getProgress() + 1);
//                final String messageText = messageArea.getText().toString();
//                if(!messageText.equals("")){
//                    Map<String, String> map = new HashMap<String, String>();
//                    map.put("message", messageText);
//                    map.put("user", UserDetails.username);
//                    reference1.push().setValue(map);
//                    reference2.push().setValue(map);
//                    messageArea.setText("");
//                }
//            }
//        });

        /*sendButton.setOnTouchListener(new View.OnTouchListener() {

            @Override

            public boolean onTouch(View view, MotionEvent motionEvent) {
                String messageText = messageArea.getText().toString();
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        firstTime = System.currentTimeMillis();

                    case MotionEvent.ACTION_UP:
                        secondTime = System.currentTimeMillis();
                        if(!messageText.equals("")){
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("message", messageText);
                            map.put("user", UserDetails.username);
                            reference1.push().setValue(map);
                            reference2.push().setValue(map);
                            messageArea.setText("");
                        }
                      Toast.makeText(QueryChat.this, "pressed time"+(secondTime-firstTime), Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });*/

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();
                if(!messageText.equals("")) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });
        sendButton.setOnLongClickListener(
                new View.OnLongClickListener(){
                    public boolean onLongClick(View arg0) {
                        mAutoIncrement = true;
                        repeatUpdateHandler.post( new RptUpdater() );
                        return false;
                    }
                }
        );

        sendButton.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if( (event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL)
                        && mAutoIncrement ){
                    mAutoIncrement = false;
                    tSeekBar.setProgress(mValue);
                    p=mValue;
                    String messageText = messageArea.getText().toString();
                    if(!messageText.equals("")) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("message", messageText);
                        map.put("user", UserDetails.username);
                        reference1.push().setValue(map);
                        reference2.push().setValue(map);
                        messageArea.setText("");
                    }
                    messageArea.setTextSize(20);
                    mValue=20;
                }
                return false;
            }
        });
        tSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                p = progress;
                //messageArea.setTextSize(p);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if(userName.equals(UserDetails.username)){
                    addMessageBox(message, 1);
                }
                else{
                    addMessageBox(message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(QueryChat.this);
        textView.setTextSize(p+1);
        textView.setText(message);
        tSeekBar.setProgress(20);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 1) {
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.box1);
            textView.setTypeface(Typeface.SANS_SERIF);
        }
        else{
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.box2);
            textView.setTypeface(Typeface.SANS_SERIF);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    class RptUpdater implements Runnable {
        public void run() {
            if( mAutoIncrement ){
                increment();
                repeatUpdateHandler.postDelayed( new RptUpdater(), 50);
            }
        }
    }
    public void increment(){
        mValue++;
        messageArea.setTextSize(mValue);
    }
}

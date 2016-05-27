package com.example.iii_user.mingstopwatch;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView clock;
    private boolean run;
    private Button right ,left;
    private int counter;
    private Timer timer;
    private UIhandler handler;
    private counterTask task;
    private ListView lv;
    private LinkedList<HashMap<String,String>> is;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clock = (TextView)findViewById(R.id.tv);
        right = (Button)findViewById(R.id.right);
        left = (Button)findViewById(R.id.left);

        timer  = new Timer();
        handler = new UIhandler();
    }
    //rest
    public void bt_left(View view){
        if(run){
            dolap();
        }else{
            doreset();
        }
    }
    //start
    public void bt_right(View view){
        run = !run;
        right.setText(run ? "Stop" : "Start");
        left.setText(run ? "Lap" : "Reset");

        if(run){
                doStart();
        }else{
                doStop();
        }
    }
    private void doStart(){
        Log.i("ming","doStart");
        task = new counterTask();

        timer.schedule(task, 10, 10);
    }
    private void doStop(){
        Log.i("ming", "doStop");
        if(task!=null){
            timer.cancel();
        }
    }
    private void dolap(){

    }
    private void processlap(){
        SimpleAdapter adapter = new SimpleAdapter(this,data,R.layout.layout_lap,from,to);
        lv.setAdapter(adapter);
    }
    private void doreset(){
        counter = 0;
        handler.sendEmptyMessage(counter);

    }

    @Override
    public void finish() {
        if(timer!=null){
            timer.purge();
            timer.cancel();
            timer = null;
        }
        super.finish();
    }

    private class counterTask extends TimerTask{
        @Override
        public void run() {
            counter++;
            handler.sendEmptyMessage(counter);
        }
    }
    private class UIhandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clock.setText(""+msg.what);
        }
    }
}

package com.cs.android190701;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView display;
    Button btn;

    // Index 변수
    int value;

    // 대화상자 변수
    ProgressDialog pd;
    // 대화상자 출력 여부를 나타낼 변수
    boolean isQuit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // View 찾아오기
        display = (TextView) findViewById(R.id.display);
        btn = (Button) findViewById(R.id.btn);

        // Button의 Click Event 처리

        /*
        btn.setOnClickListener(view -> {
            try{
                // Thread를 사용하지 않고 UI 변경 Code를 만들면, 모아서 한꺼번에 처리 합니다.
                for(int i=0 ; i<100 ; i=i+1){
                    value = value + 1;
                    Log.e("value", value+"");
                    display.setText(Integer.toString(value));
                    Thread.sleep(100);
                }
            }catch (Exception e){}
        });
        */

        /*
        // Thread Class를 상속받는 방식
        btn.setOnClickListener(view -> {
            Thread th = new Thread() {
                @Override
                public void run() {
                    try {
                        // Thread를 사용하지 않고 UI 변경 Code를 만들면, 모아서 한꺼번에 처리 합니다.
                        for (int i = 0; i < 100; i = i + 1) {
                            value = value + 1;
                            Log.e("value", value + "");
                            display.setText(Integer.toString(value));
                            Thread.sleep(100);
                        }
                    }catch(Exception e){}
                }
            };
            th.start();
        });
        */

        // Main Thread에게 작업을 요청하는 Class의 객체 생성
        Handler handler = new Handler(){
            // Message가 전송되면 호출되는 Method
            @Override
            public void handleMessage(Message msg){
                // 넘겨받은 Data를 읽어서 정수로 변환하여 v에 저장
                int v = (Integer)msg.obj;
                display.setText(Integer.toString(v));
                if(v%100!=0 && isQuit == false){
                    pd.setProgress(v%100);
                }else {
                    pd.dismiss();
                    isQuit = true;
                }
            }
        };

        btn.setOnClickListener(view -> {

            pd = new ProgressDialog(MainActivity.this);
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setTitle("Update Install");
            pd.setMessage("Waiting...");

            // Back Button을 눌렀을 때 대화상자가 닫히지 않도록 하는 설정
            pd.setCancelable(false);
            pd.show();
            isQuit = false;

           Thread th = new Thread(){
               @Override
               public void run(){

                   try{
                       for(int i=0 ; i<100 ; i=i+1){
                           value = value + 1;
                           // Handler에게 전송할 Message 생성
                           Message msg = new Message();
                           msg.obj = value;
                           // Handler에게 Message 전송
                           handler.sendMessage(msg);
                           Thread.sleep(100);
                       }
                   }catch (Exception e){}
               }
           };
           th.start();
        });
    }
}
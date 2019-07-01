package com.cs.android190701;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.time.temporal.Temporal;

public class SocketActivity extends AppCompatActivity {

    EditText msg;
    Button send;
    TextView disp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        msg = (EditText)findViewById(R.id.msg);
        send = (Button)findViewById(R.id.send);
        disp = (TextView)findViewById(R.id.disp);

        send.setOnClickListener(view -> {

            // Network 작업은 Thread를 이용해야 합니다.
            Thread th = new Thread(){
                public void run(){
                    try{

                        /*
                        // 연결할 주소 생성
                        InetAddress ia = InetAddress.getByName("192.168.0.105");

                        // Socket 만들기
                        Socket socket = new Socket(ia, 9999);

                        // 입력한 문자열 전송
                        PrintWriter pw = new PrintWriter(socket.getOutputStream());
                        pw.println(msg.getText().toString());
                        pw.flush();

                        // 전송받은 문자열 읽기
                        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String content = br.readLine();
                        //disp.setText(content);
                        pw.close();
                        br.close();
                        socket.close();
                        */

                        // Data를 전송하는 Data Gram Socket 생성
                        DatagramSocket ds = new DatagramSocket();

                        // 보낼 주소 생성
                        InetAddress ia = InetAddress.getByName("192.168.0.107");

                        // 보낼 Data 생성
                        String data = msg.getText().toString();
                        DatagramPacket dp = new DatagramPacket(data.getBytes(), data.getBytes().length, ia, 8888);
                        ds.send(dp);

                        disp.setText("Send");

                        ds.close();

                    }catch (Exception e){
                        Log.e("Error", e.getMessage());
                    }
                }
            };
            th.start();
        });

    }
}

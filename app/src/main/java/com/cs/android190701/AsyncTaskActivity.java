package com.cs.android190701;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AsyncTaskActivity extends AppCompatActivity {

    TextView asyncDisplay;
    ProgressBar progress;

    Button start, end;

    // 진행율을 표시하기 위한 변수
    int value;

    BackgrounTask task = new BackgrounTask();

    class BackgrounTask extends AsyncTask<Integer, Integer, Integer> {
        @Override
        // Task가 시작하면 호출되는 Method - 선택적
        public void onPreExecute(){

            // Progress Bar 초기화
            value = 0;
            progress.setProgress(value);
        }

        @Override
        // Background Thread로 동작하는 Method - 필수적
        // Return Type의 자료형이 Class 생성 시 설정하는 3번째 매개변수 자료형
        // 매개변수는 Class 생성 시 설정하는 2번째 매개변수 자료형
        // ...은 Parameter가 몇개가 오던지 관계 없습니다.
        public Integer doInBackground(Integer ... values){
            while (isCancelled() == false && value < 100){
                value = value + 1;

                // UI 갱신을 요청
                publishProgress(value);
                // 잠시 대기
                try {
                    Thread.sleep(1000);
                }catch (Exception e){}
            }
            return value;
        }

        @Override
        // doInBackground에서 publishProgress 를 호출하면 실행되는 Method - 선택적
        // 이 Method의 매개변수는 Class 생성 시 첫번째 자료형
        // 이 Method에서 주기적으로 UI를 갱신 합니다.
        public void onProgressUpdate(Integer ... values){
            progress.setProgress(values[0]);
            asyncDisplay.setText("Value : " + values[0]);
        }

        @Override
        // doInBackground가 작업을 종료하면 호출되는 Method - 선택적
        // 매개변수가 doInBackground의 Return값
        public void  onPostExecute(Integer result){
            asyncDisplay.setText("Code AG.");
        }

        @Override
        // Thread가 중지 되었을 때 호출되는 Method
        public void onCancelled(){
            asyncDisplay.setText("Code ER.");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        asyncDisplay = (TextView)findViewById(R.id.asyncdisplay);
        progress = (ProgressBar)findViewById(R.id.progress);
        start = (Button)findViewById(R.id.start);
        end = (Button)findViewById(R.id.end);

        start.setOnClickListener(view -> {
            // AsyncTask Instance를 생성하여 실행
            task = new BackgrounTask();
            task.execute(100);
        });

        end.setOnClickListener(view -> {
            task.cancel(true);
        });
    }
}

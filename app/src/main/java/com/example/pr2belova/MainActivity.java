package com.example.pr2belova;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.ObjectInputStream;

public class MainActivity extends AppCompatActivity {

    private TextView weather;
    private Button btn;

    private View.OnClickListener myOnClick = new View.OnClickListener() {

        public void onClick(View v) {

            MyTask mt = new MyTask();
            mt.execute();

        }

    };

    class MyTask extends AsyncTask<Void, Void, Void> {

       String title;

        @Override
        protected Void doInBackground(Void... params) {

            try {

                Document doc = Jsoup.connect("https://www.gismeteo.ru/weather-nizhny-novgorod-4355/").get();
                Elements ele = doc.select("span [class=weather]");

                title = ele.last().text();

            } catch (IOException e) {
                //e.printStackTrace();
                title = "error";
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            weather.setText("Сейчас: " + title);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        weather = (TextView) findViewById(R.id.weather);
        btn = (Button) findViewById( R.id.btn);
        btn.setOnClickListener(myOnClick);

    }
}






package com.example.restapi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    public TextView info;
    public Button nextInfo;
    public String var;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextInfo = findViewById(R.id.buttonReset);
        nextInfo.setOnClickListener(v -> {
            finish();
            startActivity(getIntent());
        });
        getCatInfo();
    }

    public void getCatInfo() {
        info = findViewById(R.id.factText);
        CatInfo catInfo = new CatInfo();

        try {
            var = catInfo.execute("https://catfact.ninja/fact").get();
            Log.i("varriableData", var);
            JSONObject jsonObject = new JSONObject(var);
            String fact = jsonObject.getString("fact");
            String factText = "Random info about cats: \n" + fact;
            info.setText(factText);
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static class CatInfo extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... address) {
            try {
                URL url = new URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputstream = connection.getInputStream();
                InputStreamReader inputstreamreader = new InputStreamReader(inputstream);

                StringBuilder var = new StringBuilder();
                int data = inputstreamreader.read();
                char mark;
                while (data != -1) {
                    mark = (char) data;
                    var.append(mark);
                    data = inputstreamreader.read();
                }
                return var.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
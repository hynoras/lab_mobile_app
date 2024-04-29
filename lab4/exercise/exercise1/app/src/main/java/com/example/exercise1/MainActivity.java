package com.example.exercise1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editdata = (EditText) findViewById(R.id.editdata);

        final Button btn=(Button) findViewById(R.id.btnreaddata);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String data;
                InputStream in = getResources().openRawResource(R.raw.dummytext);
                InputStreamReader inreader = new InputStreamReader(in);
                BufferedReader bufreader = new BufferedReader(inreader);
                StringBuilder builder = new StringBuilder();

                try {
                    while ((data = bufreader.readLine()) != null) {
                        builder.append(data);
                        builder.append("\n");
                    }
                    in.close();
                    editdata.setText(builder.toString());
                } catch (IOException ex) {
                    Log.e("ERROR", ex.getMessage());
                }
            }
        });
    }
}
package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText wordEditText;
    Button searchButton;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordEditText = findViewById(R.id.wordEditText);
        searchButton = findViewById(R.id.searchButton);
        resultTextView = findViewById(R.id.resultTextView);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = wordEditText.getText().toString();
                if (word.trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a word", Toast.LENGTH_SHORT).show();
                } else {
                    new FetchDefinitionTask().execute(word);
                }
            }
        });
    }

    private class FetchDefinitionTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String word = params[0];
            String jsonResponse = null;

            try {
                URL url = new URL("https://api.dictionaryapi.dev/api/v2/entries/en/" + word);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                jsonResponse = buffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            super.onPostExecute(jsonResponse);
            if (jsonResponse != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonResponse);
                    StringBuilder resultBuilder = new StringBuilder();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String word = jsonObject.optString("word");
                        JSONArray meanings = jsonObject.getJSONArray("meanings");

                        resultBuilder.append("Word: ").append(word).append("\n");

                        for (int j = 0; j < meanings.length(); j++) {
                            JSONObject meaningObject = meanings.getJSONObject(j);
                            String partOfSpeech = meaningObject.optString("partOfSpeech");
                            JSONArray definitions = meaningObject.getJSONArray("definitions");
                            if(partOfSpeech.equals("noun")) {
                                resultBuilder.append(partOfSpeech).append(" (n)").append("\n");
                            }
                            if(partOfSpeech.equals("adverb")) {
                                resultBuilder.append(partOfSpeech).append(" (adv)").append("\n");
                            }
                            if(partOfSpeech.equals("verb")) {
                                resultBuilder.append(partOfSpeech).append(" (v)").append("\n");
                            }

                            for (int k = 0; k < definitions.length(); k++) {
                                JSONObject definitionObject = definitions.getJSONObject(k);
                                String definition = definitionObject.optString("definition");
                                resultBuilder.append("Definition ").append(k + 1).append(": ").append(definition).append("\n");
                            }
                        }

                        resultBuilder.append("\n");
                    }

                    resultTextView.setText(resultBuilder.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                    resultTextView.setText("Error parsing JSON");
                }
            } else {
                resultTextView.setText("No response from server");
            }
        }

    }
}

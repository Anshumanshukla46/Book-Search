package com.example.space;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class FetchBook extends AsyncTask<String, Void, String> {

    private TextView titleText;
    private TextView authorText;

    public FetchBook(TextView titleText, TextView authorText) {
        this.titleText = titleText;
        this.authorText = authorText;
    }

    @Override
    protected String doInBackground(String... strings) {


        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject book = itemsArray.getJSONObject(i);

                String title = null;
                String authors = null;

                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // If book title and author exist, update textView and return

                if (title != null && authors != null) {
                    titleText.setText(title);
                    authorText.setText(authors);
                    return;
                }
            }
            titleText.setText("No results found !");
            authorText.setText("");
        } catch (Exception e) {
            titleText.setText("No results found !");
            authorText.setText("");
            e.printStackTrace();
        }
    }
}

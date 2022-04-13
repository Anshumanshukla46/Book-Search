package com.example.space;

import android.app.LoaderManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String log = MainActivity.class.getName();

    private EditText editText;
    private TextView tvTitle, tvAuthor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.entered_text);
        tvTitle = (TextView) findViewById(R.id.title);
        tvAuthor = (TextView) findViewById(R.id.author);
    }

    public void searchBook(View view) {
        String query = editText.getText().toString();

        // For hiding keyboard when search button is clicked
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        // For Checking network and search field whether empty or not
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && query.length() != 0) {
            Log.i(log, "Search text : " + query);

            tvTitle.setText(R.string.loading); // before : loading ...
            tvAuthor.setText("");

            new FetchBook(tvTitle, tvAuthor).execute(query); // after : title and author shown

        } else {
            if (query.length() == 0)
                Toast.makeText(this, "Enter atleast three letters", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Check network connection", Toast.LENGTH_SHORT).show();
            tvAuthor.setText("");
            tvTitle.setText("");
        }
    }
}
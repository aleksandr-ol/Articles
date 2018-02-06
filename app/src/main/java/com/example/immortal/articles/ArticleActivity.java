package com.example.immortal.articles;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;

public class ArticleActivity extends AppCompatActivity {
    WebView webView;
    TextView titleTetxView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        webView = (WebView) findViewById(R.id.web_view);

        Intent intent = getIntent();
        final String cat_race = intent.getStringExtra("cat_race");
        final int type_article_int = intent.getIntExtra("type_article", 0);
        final int cat_race_index = intent.getIntExtra("cat_race_index", 0);

        String type_article = "";
        titleTetxView = (TextView) findViewById(R.id.title);

        switch (type_article_int) {
            case 0:
                type_article = "main_info";
                titleTetxView.setText(R.string.main_info);
                break;
            case 1:
                type_article = "short_info";
                titleTetxView.setText(R.string.short_info);
                break;
            case 2:
                type_article = "history";
                titleTetxView.setText(R.string.history);
                break;
            case 3:
                type_article = "health";
                titleTetxView.setText(R.string.health);
                break;
            default:
                break;
        }

        String fileName = cat_race + "_" + type_article;
        String[] cat_races = getResources().getStringArray(R.array.cats);
        setTitle(cat_races[cat_race_index]);

        String text = readRawTextFile(this, getResources().getIdentifier(fileName, "raw", "com.example.immortal.articles"));

        webView.loadDataWithBaseURL(null, text, "text/html", "en_US", null);
    }

    //читаем текст из raw-ресурсов
    public static String readRawTextFile(Context context, int resId) {
        InputStream inputStream = context.getResources().openRawResource(resId);

        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader buffReader = new BufferedReader(inputReader);
        String line;
        StringBuilder builder = new StringBuilder();

        try {
            while ((line = buffReader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
        } catch (IOException e) {
            return null;
        }
        return builder.toString();
    }
}

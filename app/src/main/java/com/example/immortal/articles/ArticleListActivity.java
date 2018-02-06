package com.example.immortal.articles;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class ArticleListActivity extends ListActivity {
    String[] articles = {"Основная информация", "Краткое описание", "История", "Здоровье и болезни"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, articles);
        setListAdapter(adapter);

        Intent intent = getIntent();
        final String cat_race = intent.getStringExtra("cat_race");
        final int cat_race_index = intent.getIntExtra("cat_race_index", 0);

        AdapterView.OnItemClickListener ItemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent article = new Intent(ArticleListActivity.this, ArticleActivity.class);
                article.putExtra("cat_race", cat_race);
                article.putExtra("type_article", position);
                article.putExtra("cat_race_index", cat_race_index);
                startActivity(article);
            }
        };
        getListView().setOnItemClickListener(ItemListener);
    }
}

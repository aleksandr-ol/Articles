package com.example.immortal.articles;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.widget.GridView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int NUM_OF_COLUMNS = 3;
    public static final int GRID_PADDING = 8; //dp

    private ArrayList<String> imagePaths = new ArrayList<String>();
    private GridViewImageAdapter adapter;
    private GridView gridView;
    private int columnWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.grid_view);

        InitializeGridLayout();

        try {
            String[] listFiles = getApplicationContext().getAssets().list("awesome_cats");

            for (int i = 0; i < listFiles.length; i++)
                imagePaths.add(listFiles[i]);

            adapter = new GridViewImageAdapter(MainActivity.this, imagePaths, columnWidth);

            gridView.setAdapter(adapter);
        }
        catch (IOException e){
            Log.e("MainActivity", "Error loading assets", e);
        }
    }

    private void InitializeGridLayout() {
        Resources resources = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                GRID_PADDING, resources.getDisplayMetrics());

        columnWidth = (int) ((getScreenWidth() -
                ((NUM_OF_COLUMNS + 1) * padding)) / NUM_OF_COLUMNS);

        gridView.setNumColumns(NUM_OF_COLUMNS);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding, (int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }

    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (NoSuchMethodError ignore) {
            point.x = display.getWidth();
            point.y = display.getHeight();
        }

        columnWidth = point.x;
        return columnWidth;
    }
}

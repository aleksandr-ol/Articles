package com.example.immortal.articles;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

//import com.example.immortal.multiscreen.Fullscreen_View;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GridViewImageAdapter extends BaseAdapter {
    private Activity _activity;
    private ArrayList<String> _filePaths = new ArrayList<String>();
    private int imageWidth;

    public GridViewImageAdapter(Activity activity, ArrayList<String> filePaths, int imageWidth) {
        this._activity = activity;
        this._filePaths = filePaths;
        this.imageWidth = imageWidth;
    }

    @Override
    public int getCount() {
        return this._filePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return this._filePaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(_activity);
        } else {
            imageView = (ImageView) convertView;
        }

        try {
            InputStream image = _activity.getAssets().open("awesome_cats/" + _filePaths.get(position));

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(imageWidth, imageWidth));

            Drawable d = Drawable.createFromStream(image, null);

            imageView.setImageDrawable(d);
//            Log.d("ImageAdapter", "Error loading " + _filePaths.get(position));
        }catch (IOException e){
            Log.e("ImageAdapter", "Error loading " + _filePaths.get(position), e);
        }

        imageView.setOnClickListener(new OnImageClickListener(position));

        return imageView;
    }

    class OnImageClickListener implements View.OnClickListener {
        int _position;

        public OnImageClickListener(int position) {
            this._position = position;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(_activity, ArticleListActivity.class);
            int i = _filePaths.get(_position).lastIndexOf('.');
            String cat_race;
            //if (i != -1) {
                cat_race = _filePaths.get(_position).substring(0, i);
                intent.putExtra("cat_race", cat_race);
                intent.putExtra("cat_race_index", _position);
            //}
            _activity.startActivity(intent);
        }
    }

//    public static Bitmap decodeFile(String filePath, int WIDTH, int HEIGHT) {
//        try {
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(filePath, options);
//
//            final int REQUIRED_WIDTH = WIDTH;
//            final int REQUIRED_HEIGHT = HEIGHT;
//            int scale = 1;
//            while (((options.outWidth / scale / 2) >= REQUIRED_WIDTH) && ((options.outHeight / scale / 2) >= REQUIRED_HEIGHT))
//                scale *= 2;
//
//            BitmapFactory.Options options2 = new BitmapFactory.Options();
//            options2.inSampleSize = scale;
//            options2.inJustDecodeBounds = false;
//            return BitmapFactory.decodeFile(filePath, options2);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
}

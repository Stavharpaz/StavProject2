package com.example.myproj2.Utilities;

import android.content.Context;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.example.myproj2.R;


public class ImageLoader {

    private static ImageLoader instance;

    private static Context appContext;

    private ImageLoader(Context context){
        this.appContext = context;
    }

    public static ImageLoader getInstance() {
        return instance;
    }

    public static ImageLoader initImageLoader (Context context){
        if (instance == null)
            instance = new ImageLoader(context);
        return instance;
    }

    public void loadImage(String imageURL, ImageView imageView){
        Glide
                .with(this.appContext)
                .load(imageURL)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);
    }

    public void loadImage(int imageResource, AppCompatImageView image){
        Glide
                .with(this.appContext)
                .load(imageResource)
                .centerCrop()
                .into(image);
    }
}

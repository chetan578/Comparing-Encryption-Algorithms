package com.example.chetan578.eandd;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context)
    {
        this.context=context;
    }

    public int[] slide_images = {
            R.drawable.des,
            R.drawable.blowfish,
            R.drawable.aes
    };

    public String[] slide_headings = {
            "DATA ENCRYPTION STANDARD",
            "BLOWFISH",
            "ADVANCED ENCRYPTION STANDARD"
    };

    public String[] slide_desc = {
            "The Data Encryption Standard (DES) is a symmetric-key block cipher published by the National Institute of Standards and Technology (NIST)." +
                    "\n" +
                    "DES is an implementation of a Feistel Cipher. It uses 16 round Feistel structure. The block size is 64-bit.",
            "Blowfish is yet another algorithm designed to replace DES.\nThis symmetric cipher splits messages into blocks of 64 bits and encrypts them individually." +
                    "\n" +
                    "Blowfish is known for both its speed and overall effectiveness.",
            "AES is the algorithm trusted as the standard by the U.S. Government and numerous organizations." + "\n" +   "Although it is extremely efficient in 128-bit form, AES also uses keys of 192 and 256 bits for heavy duty encryption purposes."
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout)o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_slider_adapter,container,false);

        ImageView slideImageView = (ImageView)view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView)view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView)view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_desc[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}


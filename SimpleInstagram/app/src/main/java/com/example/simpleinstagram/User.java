package com.example.simpleinstagram;

import android.graphics.Bitmap;

class User {
    private Bitmap photo;
    private String high_photo_url;
    private String text;

    User(Bitmap photo, String text, String high_photo_url) {
        this.photo = photo;
        this.text = text;
        this.high_photo_url = high_photo_url;
    }

    Bitmap getPhoto() {
        return photo;
    }

    String getText() {
        return text;
    }

    String getHigh_photo_url(){return high_photo_url;}
}

/* Filename: Destination.java
 * Programmer: Kate Kowalyshyn
 * Description: Destination class used to initialize and get variables
 */

package com.example.destinationslideshow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Destination {

    //initialize variables
    private String title;
    private String description;

    private String category;

    private Bitmap image;
//    private int imageResource;

    public Destination(String title, String description, String category, byte[] imageData){
        this.title = title;
        this.description = description;
        this.category = category;

        //convert image to bitmap
        if (imageData != null) {
            this.image = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        }
    }

    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }

    public Bitmap getImage() {
        return image;
    }
}

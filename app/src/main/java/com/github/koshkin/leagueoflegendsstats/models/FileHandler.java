package com.github.koshkin.leagueoflegendsstats.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by tehras on 1/13/16.
 */
public class FileHandler {

    private String mImageName;
    private File mFile;

    public FileHandler(String imageName, File file) {
        mImageName = imageName;
        mFile = file;
    }

    public FileHandler(String imageName, File file, Bitmap bitmap) throws IOException {
        mImageName = imageName;
        mFile = file;

        saveBitmapToFile(bitmap);
    }

    public void saveBitmapToFile(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, bos);
        byte[] bitmapData = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = new FileOutputStream(mFile);
        fos.write(bitmapData);
        fos.flush();
        fos.close();
    }

    public Bitmap getBitmapFromFile() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(mFile.getPath(), options);
    }

    public String getImageName() {
        return mImageName;
    }

    public void setImageName(String imageName) {
        mImageName = imageName;
    }

    public File getFile() {
        return mFile;
    }

    public void setFile(File file) {
        mFile = file;
    }
}

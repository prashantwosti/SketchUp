package com.notnotme.sketchup;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class Utils {

    public static File saveImageToExternalStorage(@NonNull Context context, @NonNull String imageName, @NonNull Bitmap image) throws IOException {
        FileOutputStream fos = null;

        File imageFile;
        try {
            File imagePath = new File(context.getFilesDir(), "images");
            if (!imagePath.exists()) {
                if (!imagePath.mkdir()) {
                    throw new IOException("Cannot create images directory");
                }
            }

            imageFile = new File(imagePath, imageName + ".png");
            fos = new FileOutputStream(imageFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            throw e;
        }

        return imageFile;
    }

    public static void deleteFile(@NonNull Context context, @NonNull File file) {
        Uri imageUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.delete(imageUri, null, null);
    }

}


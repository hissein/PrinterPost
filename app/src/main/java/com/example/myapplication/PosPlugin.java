package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import com.imagpay.Settings;
import com.imagpay.mpos.MposHandler;

public class PosPlugin {

    private Settings setting;
    private Context context;

    PosPlugin(Context context) {
        this.context = context;
        this.initSdk(context);
    }


    /**
     * initialise Sdk
     */
    private void initSdk(Context context) {
        MposHandler handler = MposHandler.getInstance(context);
        handler.setShowLog(true);
        setting = Settings.getInstance(handler);
        setting.mPosPowerOn();
        try {

            if (!handler.isConnected()) {
                System.out.println("Connect Res:" + handler.connect());
            } else {
                handler.close();
                System.out.println("ReConnect Res:" + handler.connect());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    /**
     * Resize Bitmap in aspect Ratio of the printer
     *
     * @param image
     * @param maxWidth
     * @param maxHeight
     * @return Bitmap
     */
    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }


    /**
     * print Ticket use base64 of a image
     *
     * @param paths base64 of the image
     * @return Boolean
     */
    public boolean printBitmap( String paths) {


        byte[] bytes = Base64.decode(paths, Base64.NO_CLOSE);
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opt);

        bitmap = resize(bitmap, 342, bitmap.getHeight() + 200);
        try {

            setting.prnBitmap(bitmap);
            setting.prnStr("\n");
            setting.prnStart();

            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * @param buffer String
     */
    public void printText(StringBuffer buffer) {

        try {
            setting.prnStr(buffer.toString());
            setting.prnStr("\n");
            setting.prnStart();
            System.out.println("status: " + setting.prnStatus());
        } catch (Exception e) {
            throw e;
        }


    }
}

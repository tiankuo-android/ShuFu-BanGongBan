package com.wfkj.android.smartoffice.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Tools for handler picture
 *
 * @author Ryan.Tang
 */
public final class ImageTools {


    /**
     * Save image to the SD card
     *
     * @param photoBitmap
     * @param photoName
     * @param path
     */
    public static void savePhotoToSDCard(Bitmap photoBitmap, String path, String photoName) {
        if (checkSDCardAvailable()) {
            AppUtils.makeRootDirectory(path.toString().trim());
            File photoFile = new File(path.toString().trim()+ photoName.toString().trim());




                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(photoFile);
                    if (photoBitmap != null) {
                        if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
                            fileOutputStream.flush();
                        }
                    }
                } catch (Exception e) {
                    photoFile.delete();
                    e.printStackTrace();
                } finally {
                    try {
                        fileOutputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    public static void savePhotoToSDCard(Bitmap photoBitmap, String path) {
        if (checkSDCardAvailable()) {
            File photoFile = new File(path);

            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
                        fileOutputStream.flush();
                    }
                }
            } catch (Exception e) {
                photoFile.delete();
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Check the SD card
     *
     * @return
     */
    public static boolean checkSDCardAvailable() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * bitmap
     *
     * @param path
     * @param w
     * @param h
     * @return
     */
    public static final Bitmap convertToBitmap(String path, int w, int h) {
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();

            opts.inJustDecodeBounds = true;
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888;

            BitmapFactory.decodeFile(path, opts);
            int width = opts.outWidth;
            int height = opts.outHeight;
            float scaleWidth = 0.f, scaleHeight = 0.f;
            if (width > w || height > h) {

                scaleWidth = ((float) width) / w;
                scaleHeight = ((float) height) / h;
            }
            opts.inJustDecodeBounds = false;
            float scale = Math.max(scaleWidth, scaleHeight);
            opts.inSampleSize = (int) scale;
            WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
            Bitmap bMapRotate = Bitmap.createBitmap(weak.get(), 0, 0, weak.get().getWidth(), weak.get().getHeight(), null, true);

            if (bMapRotate != null) {
                return rotateBitmapByDegree(bMapRotate, getBitmapDegree(path));
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    private static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }


    /**
     * 计算图片的比例尺寸
     * @param op
     * @param reqWidth
     * @param reqheight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options op, int reqWidth,
                                            int reqheight) {
        int originalWidth = op.outWidth;
        int originalHeight = op.outHeight;
        int inSampleSize = 1;
        if (originalWidth > reqWidth || originalHeight > reqheight) {
            int halfWidth = originalWidth / 2;
            int halfHeight = originalHeight / 2;
            while ((halfWidth / inSampleSize > reqWidth)
                    &&(halfHeight / inSampleSize > reqheight)) {
                inSampleSize *= 2;

            }
        }
        return inSampleSize;
    }


}
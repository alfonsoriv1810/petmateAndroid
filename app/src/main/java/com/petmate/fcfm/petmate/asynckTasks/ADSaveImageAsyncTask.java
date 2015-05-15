package com.petmate.fcfm.petmate.asynckTasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by alfonso on 10/05/15.
 */
public class ADSaveImageAsyncTask extends AsyncTask<Bitmap, Void, Void> {
    private String _imageUrl;
    private String _directorio;
    private boolean _error;
    private ImageSavedListener _savedListener;
    public ADSaveImageAsyncTask (String imageUri, String directorio, ImageSavedListener listener) {
        _imageUrl = imageUri;
        _directorio = directorio;
        _savedListener = listener;
    }
    @Override
    protected Void doInBackground(Bitmap... arg0) {
        File mypath = new File(_directorio, _imageUrl);
        _error = false;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            arg0[0].compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (Exception e) {
            _error = true;
            e.printStackTrace();
        }
        if(_savedListener != null) {}
        return null;
    }
    @Override
    protected void onPostExecute(Void params) {
        if(_error) {
            Log.e("ERROR guardarse------->", "--------");
        } else {}
        if(_savedListener != null) {
            _savedListener.imageSaved();
        }
    }
    public interface ImageSavedListener {
        public void imageSaved();
    }
}

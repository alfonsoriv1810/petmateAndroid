package com.petmate.fcfm.petmate.asynckTasks;

/**
 * Created by alfonso on 10/05/15.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class ADLoadImageAsyncTask extends AsyncTask<Void, Void, Bitmap> {
    private String _imageUrl;
    private WeakReference<View> _imageViewReference;
    private OnLoadedImage _onLoadedImage;
    private File _myPath;
    public ADLoadImageAsyncTask (String imageUri, View image, OnLoadedImage listener) {
        _imageViewReference = new WeakReference<View>(image);
        _imageUrl = imageUri;
        _onLoadedImage = listener;
    }
    @Override
    protected Bitmap doInBackground(Void... voids) {
        _myPath = new File(_imageUrl);
        Bitmap b = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        try {
            b = BitmapFactory.decodeStream(new FileInputStream(_myPath), null, options);
        } catch (FileNotFoundException e) {
            _onLoadedImage.loadedError();
            e.printStackTrace();
        }
        return b;
    }
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        Log.e("En el onPostExecute-------------", "-------------");
        if (_imageViewReference != null && bitmap != null) {
            View imageView = _imageViewReference.get();
            if (imageView != null) {
                if(imageView instanceof ImageView) {
                    ((ImageView)imageView).setImageBitmap(bitmap);
                } else {
                    imageView.setBackground(new BitmapDrawable(imageView.getContext().getResources(), bitmap));
                }
            }
            _onLoadedImage.loadedImage();
        } else {
            _onLoadedImage.loadedError();
            if(_myPath.exists()) {
                _myPath.delete();
                Log.e("Se borra------>", _imageUrl);
            }
            Log.e("Algo anda mal---------->", _imageUrl);
        }
    }
    public interface OnLoadedImage {
        public void loadedImage();
        public void loadedError();
    }
}

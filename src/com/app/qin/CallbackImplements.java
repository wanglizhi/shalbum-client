package com.app.qin;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class CallbackImplements implements SyncImageLoader.ImageCallback {
    private ImageView imageView;
    public CallbackImplements(ImageView imageView) {
            super();
            this.imageView = imageView;
    }
    @Override
    public void imageLoaded(Drawable imageDrawable) {
            imageView.setImageDrawable(imageDrawable);
    }
	
}

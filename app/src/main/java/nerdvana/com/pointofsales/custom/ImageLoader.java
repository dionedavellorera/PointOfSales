package nerdvana.com.pointofsales.custom;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import nerdvana.com.pointofsales.R;

public class ImageLoader {
    public static void loadImage(String url, ImageView imageView) {
        Picasso.get().load(url)
                .fit()
                .error(R.drawable.no_image)
                .into(imageView);
    }
    public static void loadImage(int drawable, ImageView imageView) {
        Picasso.get()
                .load(drawable)
                .fit()
                .error(R.drawable.no_image)
                .into(imageView);
    }
}

package nerdvana.com.pointofsales.custom;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageLoader {
    public static void loadImage(String url, ImageView imageView) {
        Picasso.get().load(url).into(imageView);
    }
    public static void loadImage(int drawable, ImageView imageView) {
        Picasso.get().load(drawable).into(imageView);
    }
}

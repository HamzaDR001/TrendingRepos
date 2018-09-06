package ga.hamzabenhida.trendingrepos.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import java.io.InputStream;

/**
 * DownloadImageTask
 * extends AsyncTask<String, Void, Bitmap>
 * this class will take a ArrayList<RepoItem> try to download
 * the repository owner avatars and load them into the RepoItem
 * Objects.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    RepoItem repoItem;
    RecyclerView.Adapter adapter;

    public DownloadImageTask(RepoItem repoItem, RecyclerView.Adapter adapter) {
        //this.bmImage = bmImage;
        this.repoItem = repoItem;
        this.adapter = adapter;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            //Log.e("Error", e.getMessage());
            //e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        repoItem.setImage(result);
        adapter.notifyDataSetChanged();
    }
}
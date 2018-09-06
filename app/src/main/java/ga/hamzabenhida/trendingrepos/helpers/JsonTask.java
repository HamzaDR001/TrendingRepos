package ga.hamzabenhida.trendingrepos.helpers;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import ga.hamzabenhida.trendingrepos.TrendingFragment;

/**
 * JsonTask
 * extends AsyncTask<String, String, String>
 * this class will take a ArrayList<RepoItem> and fill it with
 * data from a url given as a parameter to execute.
 */
public class JsonTask extends AsyncTask<String, String, String> {

    private ArrayList<RepoItem> repoItems;
    MListAdapter adapter;

    public JsonTask(ArrayList<RepoItem> repoItems,MListAdapter adapter) {
        this.repoItems = repoItems;
        this.adapter = adapter;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(String... params) {
        TrendingFragment.isLoading = true;
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();


            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

            }

            return buffer.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(repoItems.size()>0){
            repoItems.remove(repoItems.size()-1);
        }
        try {
            JSONArray json = new JSONObject(result).getJSONArray("items");
            for(int i=0; i < json.length(); i++){
                JSONObject jsonItem = json.getJSONObject(i);
                RepoItem item = new RepoItem();
                item.setTitle(jsonItem.getString("name"));
                item.setDesc(jsonItem.getString("description"));
                item.setOwnerName(jsonItem.getJSONObject("owner").getString("login"));
                item.setStars(jsonItem.getString("stargazers_count"));
                item.setOwnerImageURL(jsonItem.getJSONObject("owner").getString("avatar_url"));
                new DownloadImageTask(item, adapter).execute(item.getOwnerImageURL());

                repoItems.add(item);


                adapter.notifyDataSetChanged();
            }
            repoItems.add(RepoItem.getLoadingRepo());
            TrendingFragment.isLoading = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
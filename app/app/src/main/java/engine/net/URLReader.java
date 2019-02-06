package engine.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.*;
import java.io.*;

public abstract class URLReader {

    public static final String URL = "http://h134832.s27.test-hf.su/";

    public static String getData(String strUrl) throws Exception {

        URL url = new URL(strUrl);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()));

        String output = "";
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            output += inputLine;

        in.close();

        return output;
    }

    public static Bitmap getBitmap(String strUrl) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            return null;
        }
    }

    public static void getBitmapAsynh(String strUrl, final ICompleteRunnable<Bitmap> runnable) {
        new AsyncTask<Void, Void, Void>() {
            Bitmap image;
            @Override
            protected Void doInBackground(Void... params) {
                image = getBitmap(strUrl);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                runnable.onComplete(image);
            }
        }.execute();
    }
}
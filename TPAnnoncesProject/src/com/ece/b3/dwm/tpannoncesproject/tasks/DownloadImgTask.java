package com.ece.b3.dwm.tpannoncesproject.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.ece.b3.dwm.tpannoncesproject.constants.AppPrefs;
import com.ece.b3.dwm.tpannoncesproject.constants.Debug;
import com.ece.b3.dwm.tpannoncesproject.constants.Extra;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadImgTask extends AsyncTask<String, Bitmap, Bitmap> {
	
	public DownloadImgTask (String imgName){
	   	super ();
        String host = AppPrefs.getIMGServerAdress();
        String connectionString = new StringBuilder (host).append(imgName).toString();
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        this.executeOnExecutor(SERIAL_EXECUTOR, new String [] {connectionString});
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		InputStream is = null;
		Bitmap bitmap = null;
		
	    try {
	        URL url = new URL(params[0]);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setDoInput(true);
	        conn.setUseCaches(false);
	        conn.setChunkedStreamingMode(0);
			conn.setConnectTimeout(15000);
			conn.setReadTimeout(10000);
	        // Starts the query
	        conn.connect();
	        int rCode = conn.getResponseCode();
	        
	        if (!Extra.isError(rCode)){
	        	is = conn.getInputStream();
	        	bitmap = BitmapFactory.decodeStream(is);
	        }
	        conn.disconnect();
	    } 
	    catch (IOException e) {
	    	Log.e (Debug.DEBUG_TAG, e.getMessage());
	    }
		finally {
	        if (is != null) {
	            try {
					is.close();
				} catch (IOException e) {
					Log.e (Debug.DEBUG_TAG, e.getMessage());
				}
	        } 
	    }
        return bitmap;
	}
}

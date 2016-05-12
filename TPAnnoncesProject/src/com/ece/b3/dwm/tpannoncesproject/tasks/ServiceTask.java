package com.ece.b3.dwm.tpannoncesproject.tasks;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.ece.b3.dwm.tpannoncesproject.constants.AppPrefs;
import com.ece.b3.dwm.tpannoncesproject.constants.Debug;
import com.ece.b3.dwm.tpannoncesproject.constants.Extra;
import com.ece.b3.dwm.tpannoncesproject.handlers.CustomBaseHandler;
import com.ece.b3.dwm.tpannoncesproject.services.Service;

/**
 * 
 * @author Romaaan
 * This is the decompiled version of ServiceTask class
 * Warning ! May cause brain damage
 */

public class ServiceTask extends AsyncTask <String, Void, String> {
	
    private CustomBaseHandler handler;
    private Service service;
    
    public ServiceTask(CustomBaseHandler handler) {
       	super ();
        this.handler = handler;
        this.service = handler.getService();
        String host = AppPrefs.getWSAdress();
        		
        String connectionString = new StringBuilder (host).append(service.adress).toString();
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        executeOnExecutor(SERIAL_EXECUTOR, new String[]{service.method, connectionString});
    }

    private boolean isConnected() {
        NetworkInfo networkInfo = ((ConnectivityManager) this.handler.getSender().getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    private String readData(HttpURLConnection con) throws IOException {
    	if (!Extra.isError(con.getResponseCode())){
	        try {
	            BufferedReader bReader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
	            StringBuilder sBuilder = new StringBuilder();
	            while (true) {
	                String line = bReader.readLine();
	                if (line == null) {
	                    bReader.close();
	                    return sBuilder.toString();
	                }
	                sBuilder.append(new StringBuilder(String.valueOf(line)).append("\n").toString());
	            }
	        } catch (Exception e) {
	            cancel(true);
	            Log.e(Debug.DEBUG_TAG, e.getMessage());
	            return Debug.INTERNAL_ERROR + "";
	        }
    	}
    	return con.getResponseCode() + "";
    }

    private void sendMsgToHandler(String result) {
        Message msg = new Message();
        msg.what = this.service.identifier;
        Bundle bundle = new Bundle();
        bundle.putString(this.service.tag, result);
        msg.setData(bundle);
        msg.setTarget(this.handler);
        msg.sendToTarget();
    }

    protected String doInBackground(String... params) {
    	if (isConnected()) {
    		HttpURLConnection con = null;
    		try {
    			String result;
    			URL url = new URL(params[1]);
    			con = (HttpURLConnection) url.openConnection();
    			con.setRequestProperty("Accept-Encoding", "identity");
    			con.setUseCaches(false);
    			con.setChunkedStreamingMode(0);
    			con.setRequestMethod(params[0]);
    			con.setConnectTimeout(15000);
    			con.setReadTimeout(10000);
    			
    			if (con.getRequestMethod() == "POST" || con.getRequestMethod() == "PUT") { 
        			con.setDoInput(false);
    				con.setDoOutput(true);
    				prepareOutputData (con);
          			con.connect();
          			result = con.getResponseCode() + "";
    			}
    			else {
        			con.setDoInput(true);
    				con.setDoOutput(false);
        			con.connect();
    				result = readData(con);
    			}
    			con.disconnect();
    			return result;
	      	}
          	catch (Exception e) {
          		cancel(true);
          		Log.e(Debug.DEBUG_TAG, e.getMessage());
          		if (con != null) {
          			con.disconnect();
          		}
          		return Debug.INTERNAL_ERROR + "";
          	}
    		finally {
    			if (con != null)
    				con.disconnect();
    		}
      }
      cancel(true);
      return Debug.NO_CONNECTION + "";
    }

    protected void onPostExecute(String result) {
        sendMsgToHandler(result);
    }

    protected void onCancelled(String result) {
        sendMsgToHandler(result);
    }
    
    private String getQuery (ArrayList<Pair<String,String>> params) throws UnsupportedEncodingException{
    	StringBuilder builder = new StringBuilder ();
    	boolean first = true;

		for (Pair<String,String> p : params){
			if (first)
				first = false;
			else
				builder.append("&");

	        builder.append(URLEncoder.encode(p.first, "UTF-8"));
	        builder.append("=");
	        builder.append(URLEncoder.encode(p.second, "UTF-8"));
		}
		
		return builder.toString();
    }
    
    private void prepareOutputData (HttpURLConnection con) throws IOException{
		OutputStream os = con.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		writer.write(getQuery(this.service.queryParams));
		writer.flush();
		writer.close();
		os.close(); 
    }
}

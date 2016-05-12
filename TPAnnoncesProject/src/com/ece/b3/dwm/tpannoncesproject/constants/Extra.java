package com.ece.b3.dwm.tpannoncesproject.constants;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public abstract class Extra {

	public static String DateEnToFr(String dateStr){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
			return new String (sdf.format(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateStr)));
		}
		catch (Exception e){
			Log.i(Debug.DEBUG_TAG,e.getMessage());
			return dateStr;
		}
	}
	
	public static String DateFrToEn(String dateStr){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
			return new String (sdf.format(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(dateStr)));
		}
		catch (Exception e){
			Log.i(Debug.DEBUG_TAG,e.getMessage());
			return dateStr;
		}
	}
	
	public static Point getDisplaySize (Activity sender){
		Display display = ((WindowManager) sender.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	    Point size = new Point();
	    display.getSize(size);
	    return size;
	}
	
	public static String getPreferencesByKey (String key, Activity sender){
		 SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(sender);
		 return prefs.getString(key,"-1");
	}
	
	public static boolean isError (int code){
	    if (code >= 300 || code == -1 || code < 200 || code == 0)
				return true;
	    return false;
	}
	
    public static boolean isParsableToInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            Log.i(Debug.DEBUG_TAG, e.getMessage());
            return false;
        }
    }
    
    public static Bitmap.CompressFormat getImageFormat (File file) {
    	try {
    		if (!file.isFile() || !file.exists()) throw new FileNotFoundException ("File not found");
    		
    		String fileName = file.getName();
    		int pointIndex = fileName.lastIndexOf('.') + 1;
    		int lastCharIndex = fileName.length();
    		
    		String extension = fileName.substring(pointIndex, lastCharIndex).toUpperCase(Locale.getDefault());
    		String enumStr;
    		switch (extension){
    			case "JPEG":
    				enumStr = "JPEG";
    				break;
    			case "JPG":
    				enumStr = "JPEG";
    				break;
    			case "PNG":
    				enumStr = "PNG";
    				break;
    			case "WEBP":
    				enumStr = "WEBP";
    				break;
    			default :
    				throw new UnsupportedEncodingException("Unsupported format");	
    		}
    		return Bitmap.CompressFormat.valueOf(enumStr);
    	}
    	catch (Exception e) {
    		Log.e(Debug.DEBUG_TAG, e.getMessage());
    	   	return null;
    	}
    }
    
    public static Bitmap createIconFromBitmap (Bitmap fullsized, int scale){
    	int width = fullsized.getWidth();
    	int height = fullsized.getHeight();
    	try {
    		if (scale < 1) throw new Exception("Invalid scale value : must be >= 1");
    		
    		return Bitmap.createScaledBitmap(fullsized, width/scale, height/scale, false);
    	}
    	catch (Exception e){
    		Log.e(Debug.DEBUG_TAG, e.getMessage());
    		return null;
    	}
    }
    
    public static boolean saveBitmapToFile (Bitmap bitmap, String fileName, String path){
    	 File dir = new File(path);
    	 if (!dir.exists()){
    		 dir.mkdir();
    	 }
    	 dir = new File (path, fileName);
    	 
    	 try {
    		 FileOutputStream outStream = new FileOutputStream(dir);
    		 bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream); 
    		 outStream.flush();
    		 outStream.close();
    		 return true;
    	 }
    	 catch (Exception e){
    		 Log.e(Debug.DEBUG_TAG, e.getMessage());
    		 return false;
    	 }
    }
    
    public static boolean existsFile (String path){
    	File file = new File (path);
    	if (!file.exists() || !file.isFile())
    		return false;
    	return true;
    }
    
    public static boolean existsFileWithinPath (String path, String fileName){
	    File file = new File (path, fileName);
	    	if (!file.exists() || !file.isFile())
    		return false;
    	return true;
    }
    
    public static boolean isSameBitmapWithinPath (Bitmap bitmap, String path){
    	try {
    		File file = new File (path);
    		if (!file.exists() || !file.isFile()) throw new NotFoundException ("File not found");
    		
    		ByteArrayOutputStream stream = new ByteArrayOutputStream();
    		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
    		
    		byte[] streamBytes = stream.toByteArray();
    		stream.close();
    		
    		//byte[] fileBytes = new byte[ (int) file.length()];
    		int fileBytes = (int) file.length();
    		
    		if (streamBytes.length == fileBytes)
    			return true;
    	}
    	catch (Exception e){
    		Log.e(Debug.DEBUG_TAG, e.getMessage());
    	}
    	return false;
    }
    
}

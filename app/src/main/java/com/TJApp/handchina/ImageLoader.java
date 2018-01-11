package com.TJApp.handchina;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

public class ImageLoader {
	private ImageView imageView;
	private String mUrl;
	private LruCache<String, Bitmap> myCache;
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(imageView.getTag().equals(mUrl)){
				imageView.setImageBitmap((Bitmap) msg.obj);
			}
		}
	};
	public ImageLoader() {
		// TODO Auto-generated constructor stub
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 4;
		myCache = new LruCache<String, Bitmap>(cacheSize){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// TODO Auto-generated method stub
				//在每次存入缓存时调用
				return value.getByteCount();
			}
		};
	}
	
	public void addBitmapToCache(String url, Bitmap bitmap){
		if(getBitmapToCache(url) == null){
			myCache.put(url, bitmap);
		}
	}
	
	public Bitmap getBitmapToCache(String url) {
		return myCache.get(url);
	}
	
	public void showImageByThread(ImageView imageView,final String url){
		this.imageView = imageView;
		mUrl = url; 
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				Bitmap bitmap = getBitmapFromURL(url);
				Message message = new Message();
				message.obj = bitmap;
				mHandler.sendMessage(message);
			}
		}.start();
	}
	public Bitmap getBitmapFromURL(String urlString){
		Bitmap bitmap;
		InputStream is = null;
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			is = new BufferedInputStream(connection.getInputStream());
			bitmap = BitmapFactory.decodeStream(is);
			connection.disconnect();
			return bitmap;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	public void showImageByAsyncTask(ImageView imageView, String url){
		Bitmap bitmap = getBitmapToCache(url);
		if(bitmap == null){
			new ImageLoaderAsyncTask(imageView, url).execute(url);
		}else{
			imageView.setImageBitmap(bitmap);
		}
		//new ImageLoaderAsyncTask(imageView, url).execute(url);
		//Executors.newCachedThreadPool();
	}
	
	class ImageLoaderAsyncTask extends AsyncTask<String, Process, Bitmap>{
		private ImageView mImageView;
		private String mUrl;
		public ImageLoaderAsyncTask(ImageView imageView, String Url) {
			// TODO Auto-generated constructor stub
			mImageView = imageView;
			mUrl = Url;
		}
		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			//从网络获取图片
			Bitmap bitmap = getBitmapFromURL(params[0]);
			if(bitmap != null){
				//将不在缓存的图片加人缓存
				addBitmapToCache(params[0], bitmap);
			}
			return bitmap;
		}
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(mImageView.getTag().equals(mUrl)){
				mImageView.setImageBitmap(result);
			}
		}
		
	}
}

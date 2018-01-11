package com.TJApp.handchina;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.TJApp.handchina.Activity.MyVideoPlay;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class VideoListAsyncTask extends AsyncTask<String, Void, List<VideoInfo>> {
	private List<VideoInfo> mList;
	private Context mContext;
	private ListView mListView;
	public VideoListAsyncTask(Context context, ListView listView) {
		mContext = context;
		mListView = listView;
	
	}
	@Override
	protected List<VideoInfo> doInBackground(String... params) {
		// TODO Auto-generated method stub
		mList = getVideoInfosFromUrl(params[0]);
		return mList;
	}

	private List<VideoInfo> getVideoInfosFromUrl(String mUrl) {
		List<VideoInfo> videoInfos = new ArrayList<VideoInfo>();
		try {
			String jsonString = readSteam(new URL(mUrl).openStream());
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			for(int i=0;i<jsonArray.length();i++){
				VideoInfo info = new VideoInfo();
				info.setTitle(jsonArray.getJSONObject(i).getString("item"));
				info.setImageUrl(jsonArray.getJSONObject(i).getString("img"));
				info.setVideoUrl(jsonArray.getJSONObject(i).getString("url"));
				videoInfos.add(info);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return videoInfos;
	}
	private String readSteam(InputStream is){
		InputStreamReader isr;
		String result = "";
		try {
			String line = "";
			isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			while((line = br.readLine())!=null){
				result += line;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	@Override
	protected void onPostExecute(List<VideoInfo> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		VideoListAdapter listAdapter = new VideoListAdapter(mContext, result);
		mListView.setAdapter(listAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(mContext, MyVideoPlay.class);
				intent.putExtra("VideoUrl", mList.get(position).getVideoUrl());
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);
			}
		});
	}
	

}

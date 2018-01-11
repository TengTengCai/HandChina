package com.TJApp.handchina;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.TJApp.handchina.Activity.WebActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ThreadGetListData extends Thread{
	private String url,str;
	private ListView listView;
	private MyListAdapter adapter;
	private Handler handler;
	private Context context;
	private List<ArticleListItem> data;
	private HttpClient client;
	public ThreadGetListData(String url,ListView listView,Handler handler,Context context) {
		// TODO Auto-generated constructor stub
		this.url = url;
		this.listView = listView;
		this.handler = handler;
		this.context = context;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			client = new DefaultHttpClient();
			HttpGet request = new HttpGet(); 
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				str = EntityUtils.toString(entity);
				Log.i("STRING", str);
			}
			data = parseJson(str);
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				adapter = new MyListAdapter(context);
				adapter.setData(data);
				listView.setAdapter(adapter);
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.putExtra("titleName", data.get(position).getArticleTitle());
						intent.putExtra("textUrl","http://guanruiyun.cc/"+data.get(position).getArticleUrl());
						intent.setClass(context, WebActivity.class);
						context.startActivity(intent);
					}
				});
			}
		});
		super.run();
	}
	private List<ArticleListItem> parseJson(String json) {
		List<ArticleListItem> articleListItems = new ArrayList<ArticleListItem>();
		try {
			JSONObject object = new JSONObject(json);
			String state = object.getString("flag");
			if(state.equals("Success")){
				JSONArray ListData = object.getJSONArray("data");
				for(int i = 0;i<ListData.length();i++){
					ArticleListItem articleitem = new ArticleListItem();
					JSONObject item = ListData.getJSONObject(i);
					articleitem.setArticleImageUrl(item.getString("smeta"));
					articleitem.setArticleTitle(item.getString("post_title"));
					articleitem.setArticleExcerpt(item.getString("post_excerpt"));
					articleitem.setArticleTime(item.getString("post_date"));
					articleitem.setArticleUrl(item.getString("href"));
					articleListItems.add(articleitem);
				}
				return articleListItems;
			}else{
				Log.i("STATE", "数据获取失败！！！");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

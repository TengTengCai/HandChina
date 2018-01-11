package com.TJApp.handchina;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoListAdapter extends BaseAdapter {
	private List<VideoInfo> mList;
	private LayoutInflater mInflater;
	private ImageLoader imageLoader;
	public VideoListAdapter(Context context, List<VideoInfo> List) {
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(context);
		mList = List;
		imageLoader = new ImageLoader();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressWarnings("unused")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if(viewHolder == null){
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.videolist_item, null);
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.videoImage);
			viewHolder.title = (TextView) convertView.findViewById(R.id.videoTitle);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String url = mList.get(position).getImageUrl();
		viewHolder.imageView.setTag(url);
		imageLoader.showImageByAsyncTask(viewHolder.imageView, url);
		viewHolder.title.setText(mList.get(position).getTitle());
		return convertView;
	}
	class ViewHolder{
		public ImageView imageView;
		public TextView title;
	}

}

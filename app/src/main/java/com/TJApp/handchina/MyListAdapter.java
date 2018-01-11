package com.TJApp.handchina;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter{
	private List<ArticleListItem> items;
	private Context context;
	private LayoutInflater inflater;
	private Handler handler = new Handler();
	
	public MyListAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	public void setData(List<ArticleListItem> data) {
		this.items = data;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.listview_item,null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        ArticleListItem articleListItem = items.get(position);
        holder.title.setText(articleListItem.getArticleTitle());
        holder.uptime.setText(articleListItem.getArticleTime());
        holder.excerpt.setText(articleListItem.getArticleExcerpt());
        if(articleListItem.getArticleImageUrl().equals("null")){
        	holder.articleImageUrl.setImageResource(R.drawable.no_image);
        }else{
        	new HttpImage(articleListItem.getArticleImageUrl(),handler,holder.articleImageUrl).start();
        }
        return convertView;
	}
	class Holder{
        /*
        * 初始化控件
        * */
        private TextView title;
        private TextView excerpt;
        private TextView uptime;
        private ImageView articleImageUrl;

        public Holder(View view){
            title = (TextView) view.findViewById(R.id.itemTitle);
            uptime = (TextView) view.findViewById(R.id.itemTime);
            excerpt = (TextView) view.findViewById(R.id.itemExcerpt);
            articleImageUrl = (ImageView) view.findViewById(R.id.itemImage);
        }
    }
}

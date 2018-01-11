package com.TJApp.handchina.Activity;


import com.TJApp.handchina.R;
import com.TJApp.handchina.ThreadGetListData;
import com.TJApp.handchina.R.id;
import com.TJApp.handchina.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewActivity extends Activity {
	private ListView listView;
	private ImageButton imageBack,imageRefresh;
	private TextView titleName;
	private int termId;
	private String title,url;
	private Handler handler = new Handler();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		listView = (ListView) findViewById(R.id.listView);
		imageBack = (ImageButton) findViewById(R.id.back);
		imageRefresh = (ImageButton) findViewById(R.id.refresh);
		titleName = (TextView) findViewById(R.id.title);
		Intent intent = getIntent();
		termId = intent.getIntExtra("termId", 3);
		title = intent.getStringExtra("titleName");
		titleName.setText(title);
		url = "http://guanruiyun.cc/palmchina/index.php?g=service&m=index&a=list_article&tid="+termId;
		new ThreadGetListData(url, listView,handler, this).start();
		imageBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}

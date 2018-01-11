package com.TJApp.handchina.Fragment;


import com.TJApp.handchina.R;
import com.TJApp.handchina.VideoListAsyncTask;
import com.TJApp.handchina.R.id;
import com.TJApp.handchina.R.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class FragmentActivity extends Fragment {
	private ListView mListView;
	private Spinner spinner;
	private String[] VideoName = {"习近平用典 第一季"};
	private String url = "http://guanruiyun.cc/palmchina/index.php?g=service&m=index&a=videoList";
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_activity, container, false);
		mListView = (ListView) view.findViewById(R.id.videoList);
		spinner = (Spinner) view.findViewById(R.id.spinner1);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, VideoName);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(arrayAdapter);
		setDefaultListView();
//		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> parent) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		return view;
	}
	public void setDefaultListView(){
		VideoListAsyncTask listAsyncTask = new VideoListAsyncTask(getActivity().getApplicationContext(), mListView);
		listAsyncTask.execute(url);
	}
}

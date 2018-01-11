package com.TJApp.handchina.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.TJApp.handchina.R;
import com.TJApp.handchina.ThreadGetHomeImages;
import com.TJApp.handchina.Activity.ListViewActivity;
import com.TJApp.handchina.R.drawable;
import com.TJApp.handchina.R.id;
import com.TJApp.handchina.R.layout;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.ViewFlipper;

public class FragmentHome extends Fragment {
	private ViewFlipper flipper;
	private GridView gridView;
	private List<Map<String, Object>> dataList;
	private SimpleAdapter sim_adapter;
	private int[] IconIds={R.drawable.gv_qun,R.drawable.gv_dang,
			R.drawable.gv_lizhi,R.drawable.gv_xue,
			R.drawable.gv_ilove_stydy,R.drawable.gv_honghuang_power};
	private String[] IconName={"政治","经济","文化","纵观历史","各省时事","̽探索未知֪"};
	private String url="http://guanruiyun.cc/palmchina/index.php?g=service&m=index&a=index";
	private Handler handler = new Handler();
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_home,container,false);
		flipper = (ViewFlipper) view.findViewById(R.id.viewFlipper);
		gridView = (GridView) view.findViewById(R.id.gridView);
		//setVeiwFlipperAnim();
		new ThreadGetHomeImages(url, handler, flipper, getActivity()).start();
        dataList = new ArrayList<Map<String,Object>>();
        getData();
        String [] from ={"image","text"};
        int [] to = {R.id.imageItem,R.id.text};
        sim_adapter = new SimpleAdapter(getActivity(), dataList, R.layout.gridview_item, from, to);
		gridView.setAdapter(sim_adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//����position�Ĳ�ͬ���ݵ����ݲ�ͬ
				Intent intent = new Intent();
				switch (position) {
				case 0:
					intent.putExtra("termId", 3);
					intent.putExtra("titleName", "政治");
					break;
				case 1:
					intent.putExtra("termId", 4);
					intent.putExtra("titleName", "经济");
					break;
				case 2:
					intent.putExtra("termId", 5);
					intent.putExtra("titleName", "文化");
					break;
				case 3:
					intent.putExtra("termId", 6);
					intent.putExtra("titleName", "纵观历史");
					break;
				case 4:
					intent.putExtra("termId", 7);
					intent.putExtra("titleName", "各省时事");
					break;
				case 5:
					intent.putExtra("termId", 8);
					intent.putExtra("titleName", "探索未知");
					break;
				default:
					break;
				}
				intent.setClass(getActivity(), ListViewActivity.class);
				startActivity(intent);
			}
		});
        return view;
	}

	private List<Map<String, Object>> getData(){        

        for(int i=0;i<IconIds.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", IconIds[i]);
            map.put("text", IconName[i]);
            dataList.add(map);
        }
            
        return dataList;
    }
}


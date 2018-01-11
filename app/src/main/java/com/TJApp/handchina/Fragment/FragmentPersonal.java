package com.TJApp.handchina.Fragment;

import com.TJApp.handchina.R;
import com.TJApp.handchina.R.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentPersonal extends Fragment {
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_personal, container, false);
		return view;
	}
}

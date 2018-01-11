package com.TJApp.handchina.Activity;

import com.TJApp.handchina.R;
import com.TJApp.handchina.Fragment.FragmentActivity;
import com.TJApp.handchina.Fragment.FragmentHome;
import com.TJApp.handchina.Fragment.FragmentPersonal;
import com.TJApp.handchina.R.id;
import com.TJApp.handchina.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;



public class MainActivity extends Activity {
	private FragmentHome home;
	private FragmentActivity activity;
	private FragmentPersonal personal;
	private RadioGroup radioGroup;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkedId) {
				// TODO Auto-generated method stub
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction transaction = fragmentManager.beginTransaction();
				switch (checkedId) {
				case R.id.radio0:
					if(home == null){
						home = new FragmentHome();
					}
					transaction.replace(R.id.frameLayout, home);
					Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_LONG).show();
					
					break;
				case R.id.radio1:
					if(activity == null){
						activity = new FragmentActivity();
					}
					transaction.replace(R.id.frameLayout, activity);
					Toast.makeText(getApplicationContext(), "activity", Toast.LENGTH_LONG).show();
					break;
				case R.id.radio2:
					if(personal == null){
						personal = new FragmentPersonal();
					}
					transaction.replace(R.id.frameLayout, personal);
					Toast.makeText(getApplicationContext(), "personal", Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
				transaction.commit();
			}
		});
        setDefaultFragment();
        
    }
    private void setDefaultFragment() {
    	FragmentManager fragmentManager = getFragmentManager();
    	FragmentTransaction transaction = fragmentManager.beginTransaction();
    	home = new FragmentHome();
    	transaction.add(R.id.frameLayout, home);
    	transaction.commit();
	}
    
	

}

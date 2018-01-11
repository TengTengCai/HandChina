package com.TJApp.handchina;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import android.widget.ImageView.ScaleType;

public class ThreadGetHomeImages extends Thread {
	private String url,str,imageUrl;
	private ViewFlipper flipper;
	private List<ImageView> imageViews = new ArrayList<ImageView>();
	private List<CarouselImage> data;
	private ImageView temp;
	private Handler handler;
	private Context context;
	private TranslateAnimation rightInAnim,leftOutAnim,rightOutAnim,leftInAnim;
	private float startX;
	private HttpClient client;
	public ThreadGetHomeImages(String url,Handler handler,ViewFlipper flipper,Context context) {
		// TODO Auto-generated constructor stub
		this.url = url;
		this.handler = handler;
		this.flipper = flipper;
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
			//BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			HttpEntity entity = response.getEntity();
			if(entity != null){
				str = EntityUtils.toString(entity);
				Log.i("Json", str);
				data = parseJson(str);
         		ImageFactory factory = new ImageFactory();
         		for(int i = 0; i<data.size();i++){
         			imageUrl = data.get(i).getImageUrl();
         			Log.i("ImageUrl", imageUrl+"");
         			temp = new ImageView(context);
         			temp.setImageBitmap(factory.ratio(getbitmap(imageUrl), 240, 120));
         			//temp.setImageDrawable(loadImageFromUrl(imageUrl));
         			temp.setScaleType(ScaleType.CENTER_CROP);
         			imageViews.add(temp);
         		}
			}
            
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				setVeiwFlipperAnim();
			}
		});
		
		super.run();
	}
	
	private List<CarouselImage> parseJson(String json) {
		List<CarouselImage> items = new ArrayList<CarouselImage>();
		try {
			JSONObject object = new JSONObject(json);
			String state = object.getString("flag");
			if(state.equals("Success")){
				JSONArray ImagesData = object.getJSONArray("data");
				for(int i = 0;i<ImagesData.length();i++){
					CarouselImage image = new CarouselImage();	
					JSONObject item = ImagesData.getJSONObject(i);
					image.setImageName(item.getString("slide_name"));
					image.setImageUrl(item.getString("slide_pic"));
					image.setTextUrl(item.getString("slide_url"));
					Log.i("ImageUrl", item.getString("slide_pic"));
					items.add(image);
				}
				return items;
			}else{
				Log.i("STATE", "数据获取失败!!");
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static Bitmap getbitmap(String imageUri) {
        Log.v("ImageUrl", "getbitmap:" + imageUri);
		// 显示网络上的图片
        Bitmap bitmap = null;
        try {
            URL myFileUrl = new URL(imageUri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();

            Log.v("download finished", "image download finished." + imageUri);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            bitmap = null;
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("getbitmap", "getbitmap bmp fail---");
            bitmap = null;
        }
        return bitmap;
    }
	

	private void setVeiwFlipperAnim() {
		for(int i =0;i<imageViews.size();i++){
			flipper.addView(imageViews.get(i));
		}
		// 图片从右侧滑入
        rightInAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        rightInAnim.setDuration(1000);
		// 图片从左侧滑出
        leftOutAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        leftOutAnim.setDuration(1000);
		// 图片从右侧滑出
        rightOutAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        rightOutAnim.setDuration(1000);

		// 图片从左侧滑入
        leftInAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        leftInAnim.setDuration(1000);

		// 正常情况左侧划出，右侧滑入
        flipper.setOutAnimation(leftOutAnim);
        flipper.setInAnimation(rightInAnim);
		
        flipper.setFlipInterval(5000);
        flipper.startFlipping();
		
        flipper.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				 int action = event.getAction();
			        switch (action) {
			            case MotionEvent.ACTION_DOWN:
			            	startX = (int) event.getX();
							//flipper.stopFlipping();// 当手指按下时，停止图片的循环播放。并记录当前x坐标
			                Log.i("count", flipper.getDisplayedChild()+"");
			                break;
			            case MotionEvent.ACTION_MOVE:
			                break;
			            case MotionEvent.ACTION_UP:
			                if (event.getX() - startX > 100) { // 手指向右滑动，左侧滑入，右侧滑出
			                	flipper.setInAnimation(leftInAnim);
			                	flipper.setOutAnimation(rightOutAnim);
			                	flipper.showPrevious();
			                	flipper.startFlipping();
			                } else if (startX - event.getX() > 100) {// 手指向左滑动，右侧滑入，左侧滑出
			                	flipper.setInAnimation(rightInAnim);
			                	flipper.setOutAnimation(leftOutAnim);
			                	flipper.showNext();
			                	flipper.startFlipping();
			                }else if(startX - event.getX()<10||event.getX()-startX<10){
								//Toast.makeText(context, "当前索引"+flipper.getDisplayedChild(), Toast.LENGTH_SHORT).show();
			                	data.get(flipper.getDisplayedChild());
			                	Intent intent = new Intent();
			                	intent.putExtra("titleName",data.get(flipper.getDisplayedChild()).getImageName());
			                	intent.putExtra("textUrl", data.get(flipper.getDisplayedChild()).getTextUrl());
			                	intent.setClass(context, WebActivity.class);
			                	context.startActivity(intent);
			                }
			                break;
			            
			        }
				//返回false只执行一次监听，返回true会一直进行监听
				return true;
			}
		});	
	}
}

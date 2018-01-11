package com.TJApp.handchina;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Administrator on 2016/2/13.
 */
public class HttpImage extends Thread {
    private ImageView imageView;
    private String url;
    private Handler handler;
    private Bitmap bitmap;
//    private Drawable default_drawable;

    /*
    * 寤虹珛鏋勯�犳柟娉�
    * 浼犻�掑浘鐗囩殑url锛孒andler锛岀浉搴擨mageView鐨勫璞�
    * 鐒跺悗杩涜璧嬪�煎垵濮嬪寲*/
    public HttpImage(String url, Handler handler, ImageView imageView){
        this.url = url;
        this.handler = handler;
        this.imageView = imageView;
        
    }
    /*
    * 閲嶅啓Run鏂规硶锛岃闂浘鐗囩殑url*/
    @Override
    public void run() {
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();//寤虹珛閾炬帴
            connection.setRequestMethod("GET");//浠ET鏂规硶杩涜鑾峰彇
            connection.setReadTimeout(5000);//璁块棶鐢熸晥鏃堕棿5绉�
            InputStream inputStream = connection.getInputStream();//寤虹珛杈撳叆娴�
            bitmap = BitmapFactory.decodeStream(inputStream);//灏嗗浘鐗囦氦缁橞itmapFactory杞寲鎴怋itmap瀵硅薄
            /*
            * 鐢℉andler post灏咺mageView鐨勫浘鍍忔洿鏀逛负Bitmap涓殑鍥剧墖*/
            handler.post(new Runnable(){
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                    imageView.setScaleType(ScaleType.CENTER_CROP);
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

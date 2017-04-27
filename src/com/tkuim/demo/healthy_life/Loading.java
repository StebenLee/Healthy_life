package com.tkuim.demo.healthy_life;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

@SuppressLint("HandlerLeak")
public class Loading extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//不顯示標題與圖示
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//畫面不可轉向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.loading);
        //畫面延遲兩秒
       handler.sendMessageDelayed(new Message(), 2000);
	}
	private Handler handler = new Handler() {
		  @Override
	        public void handleMessage(Message msg) {
	            super.handleMessage(msg);
	            Intent intent = new Intent();
	            intent.setClass(Loading.this, Main_Menu.class);
	            Loading.this.startActivity(intent);
	            Loading.this.finish();
	        }		
	};

}

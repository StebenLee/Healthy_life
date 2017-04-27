package com.tkuim.demo.healthy_life;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
//import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class Main_Menu extends Activity {
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar ab=getActionBar();
		ab.setTitle("主選單");
		setContentView(R.layout.activity_health);
		findViews();

	}
    //匯入介面元件
	private void findViews() {
		
		TabHost tabhost = (TabHost) findViewById(R.id.tabhost);
		tabhost.setup();

		tabhost.addTab(tabhost.newTabSpec("tab1").setIndicator("OK認證")
				.setContent(R.id.tab1));

		tabhost.addTab(tabhost.newTabSpec("tab2").setIndicator("銀髮族服務")
				.setContent(R.id.tab2));
		tabhost.addTab(tabhost.newTabSpec("tab3").setIndicator("關於我")
				.setContent(R.id.tab3));
		
		// 顯示目前分頁
		tabhost.setCurrentTab(0);
		
		// 從resource取得tabwidget並轉成TabWidget物件
		TabWidget tabwidget = (TabWidget) tabhost
				.findViewById(android.R.id.tabs);
		TextView tab = (TextView) tabwidget.getChildTabViewAt(0).findViewById(
				android.R.id.title);
		// 調整TAB內文字大小與顏色
		tab.setTextSize(24);
		tab.setTextColor(Color.BLACK);

		tab = (TextView) tabwidget.getChildTabViewAt(1).findViewById(
				android.R.id.title);
		tab.setTextSize(24);
		tab.setTextColor(Color.BLACK);
		
		tab = (TextView) tabwidget.getChildTabViewAt(2).findViewById(
				android.R.id.title);
		tab.setTextSize(24);
		tab.setTextColor(Color.BLACK);

		// OK認證下拉式選單
		Spinner item = (Spinner) findViewById(R.id.sp1);
		ArrayAdapter<CharSequence> adapCateList = ArrayAdapter
				.createFromResource(this, R.array.category,
						R.drawable.spinner_style);
		item.setAdapter((new NotingSelectedSpinner(
				adapCateList, 
	            R.drawable.spinner_style, 
	            this)));
		//避免spinner自動執行一次
		item.setSelection(0, true);
		item.setOnItemSelectedListener(new item_OnItemSelectedListener());

		// 銀髮族下拉式選單
		Spinner item1 = (Spinner) findViewById(R.id.sp2);
		ArrayAdapter<CharSequence> adapCateList1 = ArrayAdapter
				.createFromResource(this, R.array.oldcategory,
						R.drawable.spinner_style);
		item1.setAdapter((new NotingSelectedSpinner(
				adapCateList1, 
	            R.drawable.spinner_style, 
	            this)));
		item1.setSelection(0, true);
		item1.setOnItemSelectedListener(new item_OnItemSelectedListener1());
		

	}

	// OK選單Json點擊連結
	private class item_OnItemSelectedListener implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
	
	    	
			
			try{
				if(isConnectingToInternet()==true){
			Intent i = new Intent(Main_Menu.this, ItemList.class);
			Bundle b = new Bundle();
			if (position == 1)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/json/N0JFMjA1NEUtNjJCQi00NkIzLThDNTEtMEVDMDBERDE0QkUx");
			else if (position == 2)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/json/NEI5ODUxQzMtRTc5MS00MjJCLTk1QTMtMTkxRUFCQTBBMzY5");
			else if (position == 3)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/json/RjQzRThDNjUtMzU3OS00MTU5LUEwOUEtMUI2NzFDOTE5NDcz");
			else if (position == 4)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/json/NEMwNDE1OTEtRTJDOC00NTM2LUI1QkItMjc3NDJBMDU3MjNE");
			else if (position == 5)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/json/QTBEMTY0RUEtMjgyNi00Q0I1LTkwNzMtMjlDQUM0MkNBOTdD");
			else if (position == 6)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/json/RUMzQTdDQUYtRDQ5NC00QTVBLUJFRkMtMzlEQUMwNTVBN0Yx");
			else if (position == 7)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/json/N0JCNzMwNEYtMkRDQi00ODNFLUIzQjMtN0E0ODM4RTU4NUUz");
			else if (position == 8)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/json/QTdBNEQ5NkQtQkM3MS00QUI2LUJENTctODI0QTM5MkIwMUZE");
			else if (position == 9)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/json/MDE1QzBFRUQtQkE2RC00MjNGLTkwMUEtOUMzMTU3MDYwNkE2");
			else if (position == 10)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/json/NzQzQjFGQjUtNzUxMi00RkUxLUIwQ0UtOUQxNjQ4MkExMDBD");
			else if (position == 11)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/json/QzgwMEFBMjUtMjlCNS00OUZDLUE2MzgtQUIyRDJBRDM5NjJB");
			else if (position == 12)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/json/NEFERTQ0NDUtQjFFMy00NzJGLTlDRUQtQURGMEExQzI2NjNF");
			else if (position == 13)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/json/ODg0QTEyNUEtRDMwQi00RTJCLTgyODAtQzNBMzlFOTk1NUJF");
			else if (position == 14)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/json/NUUwNjg2MEItRTM3NC00NzY0LTg0NjgtQ0NBMjkzOTY1OTE4");
			else if (position == 15)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/json/MDY2RERBMTctQTE4Mi00OEU5LUI2M0YtRTg0NTQ1NUEzM0Mw");
			i.putExtras(b);
			startActivity(i);}
				else{
					Toast toast = Toast.makeText(Main_Menu.this, "需要連線才可使用", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, 520);
					LinearLayout toastLayout = (LinearLayout) toast.getView();
					TextView toastTV = (TextView) toastLayout.getChildAt(0);
					toastTV.setTextSize(22);
					toast.show();
				}
			}catch(Exception e){
				
			}
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

		
	}
	// 銀髮族選單Json點擊連結
	private class item_OnItemSelectedListener1 implements
			OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			try {
				if(isConnectingToInternet()==true){
			Intent i = new Intent(Main_Menu.this, OldItemList.class);
			Bundle b = new Bundle();
			if (position == 1)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/query/MkMzRjY4QzQtNTMzOS00Q0ZBLThBRkMtNDI2QzczM0FDOTQ2?$format=json");
			else if (position == 2)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/query/QTkzODFBOTUtQTE2NC00QjE1LTlCQzgtNTA1RTY2RDRGQTkw?$format=json");
			else if (position == 3)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/query/RkFDRjkyRTctRUI1OS00RUE3LTkzNzEtRTU1NTZBNzE1NkY1?$format=json");
			else if (position == 4)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/query/QUEyMTQ1REMtQjhBQy00NEUxLTgxNTUtRjQ1MDk4RkVENzEw?$format=json");
			else if (position == 5)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/query/NEZCQTJDOEUtNzg4MS00OTgyLUFCQ0MtMDYwQzRDOUZEQTgw?$format=json");
			else if (position == 6)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/query/NUNDNUIyOEItNTNCMS00NjY4LThFRDAtMEU5NTU1MzkyQzNG?$format=json");
			else if (position == 7)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/query/OUU2QkJDRTgtMjkwMS00OTU4LTlDNzMtNjE2ODBCN0I0Qzc2?$format=json");
			else if (position == 8)
				b.putString(
						"url",
						"http://data.taipei.gov.tw/opendata/apply/query/RDUyRjY0ODYtM0U3My00QTAxLTkyNjYtRkQ0QjVEN0YzREFD?$format=json");
			i.putExtras(b);
			startActivity(i);}
			else{
				
				Toast toast = Toast.makeText(Main_Menu.this, "需要連線才可使用", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, 520);
				LinearLayout toastLayout = (LinearLayout) toast.getView();
				TextView toastTV = (TextView) toastLayout.getChildAt(0);
				toastTV.setTextSize(22);
				toast.show();
			}
		    }
			catch(Exception e){
			}
			

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}
	private boolean isConnectingToInternet(){
	     ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	     if (CM != null)
	     {
	       NetworkInfo info = CM.getActiveNetworkInfo();
	       if (info != null)
	          return (info.isConnected());
	     }
	     return false;
	  }
}

package com.tkuim.demo.healthy_life;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;


public class OldItemList extends Activity implements 
SearchView.OnQueryTextListener, SearchView.OnCloseListener{
	String name1,x1,y1,addr;
	ListView l1;
	SearchView search;
	ArrayList<HashMap<String,String>> listdata;
	ArrayList<HashMap<String,String>> newListdata;//存放搜尋後結果
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.item);
		findViews();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search.setIconifiedByDefault(false);
		Bundle extras=getIntent().getExtras();
		//進行載入中(以下是他的執行緒)和解析資料(onCreate後即產生)各有他們的執行緒，不互相衝突
		final ProgressDialog myDialog = ProgressDialog.show(this,"Loading...", "讀取中...");
	       new Thread()
	       { 
	         public void run()
	         { 
	           try
	           { 
	             sleep(3000);
	           }
	           catch (Exception e)
	           {
	             e.printStackTrace();
	           }
	           finally
	           {
	             myDialog.dismiss();    
	           }
	         }
	       }.start(); //開始執行執行緒
		
	    //listdata中資料為HashMap類別產生，排序效能較好
		listdata=new ArrayList<HashMap<String,String>>();
	    //SimpleAdapter能處理多個String，所以用它當作listView跟ArrayList中間的橋樑，透過他將ArrayList資料餵給listView
		l1.setAdapter(new SimpleAdapter(OldItemList.this,listdata, R.drawable.listview_style, 
				new String[]{"org_name","address","phone"}, new int[] { R.id.textView1,R.id.textView2,R.id.textView3}));
		
		if(android.os.Build.VERSION.SDK_INT>=4) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());        
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build()); 
		}
		//可變長度字串類別，較不浪費記憶體空間
		StringBuilder strb = new StringBuilder("");
		try {
			String url=extras.getString("url");
			//開啟串流進行網頁內容讀取
			InputStream stream = new URL(url).openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line = null;
			//檢查每一行內容，若不為空，字串內容將不斷累加
			while( (line = reader.readLine()) != null) {
				strb.append(line);
			}
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			//將資料寫入JSONArray
			JSONArray result = new JSONArray(strb.toString());
			//取出陣列內所有物件
			for(int i = 0;i < result.length(); i++) {
				//取出JSON物件
				JSONObject stock_data = result.getJSONObject(i);
				HashMap<String,String> item=new HashMap<String,String>();//塞入key與value，方便未來做資料查詢
				if(stock_data.getString("org_name").compareTo("null")==0){
					item.put("org_name", "查無此商家");
					item.put("address", "地址 : "+stock_data.getString("address"));
					item.put("phone","電話 : "+stock_data.getString("phone"));
					item.put("X",stock_data.getString("lon"));
					item.put("Y",stock_data.getString("lat"));
				}
				if(stock_data.getString("address").compareTo("null")==0){
					item.put("org_name",stock_data.getString("org_name"));
					item.put("address","查無此地址");
					item.put("phone","電話 : "+stock_data.getString("phone"));
					item.put("X",stock_data.getString("lon"));
					item.put("Y",stock_data.getString("lat"));
				}
				if(stock_data.getString("phone").compareTo("null")==0){
					item.put("org_name",stock_data.getString("org_name"));
					item.put("address", "地址: "+stock_data.getString("address"));
					item.put("phone","查無此電話");
					item.put("X",stock_data.getString("lon"));
					item.put("Y",stock_data.getString("lat"));
				}
				else{
				item.put("org_name",stock_data.getString("org_name"));
				item.put("address", "地址 : "+stock_data.getString("address"));
				item.put("phone","電話 : "+stock_data.getString("phone"));
				item.put("X",stock_data.getString("lon"));
				item.put("Y",stock_data.getString("lat"));
				}
				//item.put("lon",stock_data.getString("lon"));經度
				//item.put("lat",stock_data.getString("lat"));緯度
				
				//資料匯入listdata後就可以依照key或value進行排序
				listdata.add(item);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  }
		search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);
	 }	

	private void findViews(){
		l1=(ListView)findViewById(R.id.l1);
		search = (SearchView) findViewById(R.id.se);
		l1.setOnItemClickListener(new l1OnItemClickListener());
	 }
    private class l1OnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			if(newListdata==null){
				name1=listdata.get(position).get("org_name");
				x1=listdata.get(position).get("X");
			    y1=listdata.get(position).get("Y");
			    addr=listdata.get(position).get("address");
		    }
			else{
				name1=newListdata.get(position).get("org_name");
				x1=newListdata.get(position).get("X");
			    y1=newListdata.get(position).get("Y");
			    addr=newListdata.get(position).get("address");
			}
		    
			AlertDialog.Builder myalert=new AlertDialog.Builder(OldItemList.this);
			myalert.setTitle("取得地圖位置");
			myalert.setMessage("你是否想利用地圖來查看位置");
			myalert.setIcon(R.drawable.a_36);
			myalert.setNegativeButton("是", new ConfirmOnClickListener());
			myalert.setPositiveButton("否", new CancelOnClickListener());
			myalert.setCancelable(true);
			myalert.show();
		   }	
	 }
    private class ConfirmOnClickListener implements OnClickListener{
    	//設定左邊按鈕事件處理
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			Intent i=new Intent(OldItemList.this,Getmap.class);
			Bundle b=new Bundle();
			b.putString("name", name1);
			b.putString("X", x1);
			b.putString("Y", y1);
			b.putString("addr", addr);
			i.putExtras(b);
			startActivity(i);
		}
    	
    }
    
    private class CancelOnClickListener implements OnClickListener{
    	//設定右邊按鈕事件處理
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
    	
    }

    @Override
	public boolean onClose() {
		// TODO Auto-generated method stub
    	newListdata=filterData("");
		return false;
	}

    //在搜尋欄進行查詢，listview內容同時變動的方法
	@Override
	public boolean onQueryTextChange(String query) {
		// TODO Auto-generated method stub
		newListdata=filterData(query);
		l1.setAdapter(new SimpleAdapter(OldItemList.this,newListdata, R.drawable.listview_style, 
				new String[]{"org_name","address","phone"}, new int[] { R.id.textView1,R.id.textView2,R.id.textView3}));
		return false;
	}

	//查詢完畢按下按鈕後，顯示listview內容的方法
	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		newListdata=filterData(query);
		l1.setAdapter(new SimpleAdapter(OldItemList.this,newListdata, R.drawable.listview_style, 
				new String[]{"org_name","address","phone"}, new int[] { R.id.textView1,R.id.textView2,R.id.textView3}));
		return false;
	}
    //過濾資料的方法
	public ArrayList<HashMap<String,String>> filterData(String query){
		  ArrayList<HashMap<String,String>> newListdata = new ArrayList<HashMap<String,String>>(); 
		  query = query.toLowerCase();
		    for(HashMap<String,String> item: listdata){
		     if(item.get("org_name").toLowerCase().contains(query)||item.get("address").toLowerCase().contains(query) ){
		    	 newListdata.add(item);
		     }
		    }return newListdata;
		 }
}
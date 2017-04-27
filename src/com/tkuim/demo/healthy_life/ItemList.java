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
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class ItemList extends Activity implements 
SearchView.OnQueryTextListener, SearchView.OnCloseListener{
	String name1,x1,y1,addr;
	ListView l1;
	SearchView search;
	ArrayList<HashMap<String,String>> listdata;
	ArrayList<HashMap<String,String>> newListdata;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.item);
		findViews();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search.setIconifiedByDefault(false);
		Bundle extras=getIntent().getExtras();
		/*進行載入中(以下是他的執行緒)和解析資料(onCreate後即產生)各有他們的執行緒，不互相衝突*/
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
		l1.setAdapter(new SimpleAdapter(ItemList.this,listdata, R.drawable.listview_style, new String[]{"name","display_addr","tel"}, new int[] { R.id.textView1, R.id.textView2, R.id.textView3 }));
		
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
			//將讀資料寫入JSONArray
			JSONArray result = new JSONArray(strb.toString());
			//取出陣列內所有物件
			for(int i = 0;i < result.length(); i++) {
				//取出JSON物件
				JSONObject stock_data = result.getJSONObject(i);
				HashMap<String,String> item=new HashMap<String,String>();
				if(stock_data.getString("name").compareTo("null") == 0){
					item.put("name","查無此商家");
					item.put("display_addr","地址 : "+stock_data.getString("display_addr"));
					item.put("tel","電話 : "+stock_data.getString("tel"));
					item.put("X",stock_data.getString("X"));
					item.put("Y",stock_data.getString("Y"));
				}
			    if(stock_data.getString("display_addr").compareTo("null") == 0){
					item.put("name",stock_data.getString("name"));
					item.put("display_addr","查無此地址");
				    item.put("tel","電話:"+stock_data.getString("tel"));
				    item.put("X",stock_data.getString("X"));
					item.put("Y",stock_data.getString("Y"));
			    }
			    if(stock_data.getString("tel").compareTo("null") == 0){
					item.put("name",stock_data.getString("name"));
					item.put("display_addr","地址 : "+stock_data.getString("display_addr"));
					item.put("tel","查無此電話");
					item.put("X",stock_data.getString("X"));
					item.put("Y",stock_data.getString("Y"));
				}
				else{		
 				    item.put("name",stock_data.getString("name"));
				    item.put("display_addr", "地址 : "+stock_data.getString("display_addr"));
				    item.put("tel","電話: "+stock_data.getString("tel"));
				    item.put("X",stock_data.getString("X"));//讀取X座標
				    item.put("Y",stock_data.getString("Y"));//讀取Y座標
				}
				listdata.add(item);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  }
		finally{
		search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);
        }
	}	

	private void findViews(){
		l1=(ListView)findViewById(R.id.l1);
		search = (SearchView) findViewById(R.id.se);
		l1.setOnItemClickListener(new l1OnItemClickListener());
	}
	private class l1OnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			if(newListdata==null){
				name1=listdata.get(position).get("name");
				x1=listdata.get(position).get("X");
			    y1=listdata.get(position).get("Y");
			    addr=listdata.get(position).get("display_addr");
		    }
			else{
				name1=newListdata.get(position).get("name");
				x1=newListdata.get(position).get("X");
			    y1=newListdata.get(position).get("Y");
			    addr=newListdata.get(position).get("display_addr");
			}
			AlertDialog.Builder myalert=new AlertDialog.Builder(ItemList.this);
			myalert.setTitle("取得地圖位置");
			//對話框中顯示內容
			myalert.setMessage("你是否想利用地圖來查看位置");
			myalert.setIcon(R.drawable.a_36);
			myalert.setNegativeButton("是", new ConfirmOnClickListener());
			myalert.setPositiveButton("否", new CancelOnClickListener());
			myalert.setCancelable(true);
			myalert.show();
		}
	}	
	private class ConfirmOnClickListener implements OnClickListener{

		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			Intent i=new Intent(ItemList.this,Getmap.class);
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
	
	//在搜尋欄進行查詢方法
	@Override
	public boolean onQueryTextChange(String query) {
		// TODO Auto-generated method stub
		newListdata=filterData(query);
		l1.setAdapter(new SimpleAdapter(ItemList.this,newListdata, R.drawable.listview_style, new String[]{"name","display_addr","tel"}, new int[] { R.id.textView1, R.id.textView2, R.id.textView3 }));
		return false;
	}

	//查詢完畢按下按鈕後的方法
	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		newListdata=filterData(query);
		l1.setAdapter(new SimpleAdapter(ItemList.this,newListdata, R.drawable.listview_style, new String[]{"name","display_addr","tel"}, new int[] { R.id.textView1, R.id.textView2, R.id.textView3 }));
		return false;
	}

	//過濾資料的方法
	public ArrayList<HashMap<String,String>> filterData(String query){
		  ArrayList<HashMap<String,String>> newListdata = new ArrayList<HashMap<String,String>>(); 
		  query = query.toLowerCase();
		    for(HashMap<String,String> item: listdata){
		     if(item.get("name").toLowerCase().contains(query)||item.get("display_addr").toLowerCase().contains(query) ){
		       newListdata.add(item);
		     }
		    }return newListdata;
		 }

}

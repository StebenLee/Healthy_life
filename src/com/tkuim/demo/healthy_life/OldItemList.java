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
	ArrayList<HashMap<String,String>> newListdata;//�s��j�M�ᵲ�G
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.item);
		findViews();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search.setIconifiedByDefault(false);
		Bundle extras=getIntent().getExtras();
		//�i����J��(�H�U�O�L�������)�M�ѪR���(onCreate��Y����)�U���L�̪�������A�����۽Ĭ�
		final ProgressDialog myDialog = ProgressDialog.show(this,"Loading...", "Ū����...");
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
	       }.start(); //�}�l��������
		
	    //listdata����Ƭ�HashMap���O���͡A�ƧǮį���n
		listdata=new ArrayList<HashMap<String,String>>();
	    //SimpleAdapter��B�z�h��String�A�ҥH�Υ���@listView��ArrayList���������١A�z�L�L�NArrayList�������listView
		l1.setAdapter(new SimpleAdapter(OldItemList.this,listdata, R.drawable.listview_style, 
				new String[]{"org_name","address","phone"}, new int[] { R.id.textView1,R.id.textView2,R.id.textView3}));
		
		if(android.os.Build.VERSION.SDK_INT>=4) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());        
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build()); 
		}
		//�i�ܪ��צr�����O�A�������O�O����Ŷ�
		StringBuilder strb = new StringBuilder("");
		try {
			String url=extras.getString("url");
			//�}�Ҧ�y�i��������eŪ��
			InputStream stream = new URL(url).openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line = null;
			//�ˬd�C�@�椺�e�A�Y�����šA�r�ꤺ�e�N���_�֥[
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
			//�N��Ƽg�JJSONArray
			JSONArray result = new JSONArray(strb.toString());
			//���X�}�C���Ҧ�����
			for(int i = 0;i < result.length(); i++) {
				//���XJSON����
				JSONObject stock_data = result.getJSONObject(i);
				HashMap<String,String> item=new HashMap<String,String>();//��Jkey�Pvalue�A��K���Ӱ���Ƭd��
				if(stock_data.getString("org_name").compareTo("null")==0){
					item.put("org_name", "�d�L���Ӯa");
					item.put("address", "�a�} : "+stock_data.getString("address"));
					item.put("phone","�q�� : "+stock_data.getString("phone"));
					item.put("X",stock_data.getString("lon"));
					item.put("Y",stock_data.getString("lat"));
				}
				if(stock_data.getString("address").compareTo("null")==0){
					item.put("org_name",stock_data.getString("org_name"));
					item.put("address","�d�L���a�}");
					item.put("phone","�q�� : "+stock_data.getString("phone"));
					item.put("X",stock_data.getString("lon"));
					item.put("Y",stock_data.getString("lat"));
				}
				if(stock_data.getString("phone").compareTo("null")==0){
					item.put("org_name",stock_data.getString("org_name"));
					item.put("address", "�a�}: "+stock_data.getString("address"));
					item.put("phone","�d�L���q��");
					item.put("X",stock_data.getString("lon"));
					item.put("Y",stock_data.getString("lat"));
				}
				else{
				item.put("org_name",stock_data.getString("org_name"));
				item.put("address", "�a�} : "+stock_data.getString("address"));
				item.put("phone","�q�� : "+stock_data.getString("phone"));
				item.put("X",stock_data.getString("lon"));
				item.put("Y",stock_data.getString("lat"));
				}
				//item.put("lon",stock_data.getString("lon"));�g��
				//item.put("lat",stock_data.getString("lat"));�n��
				
				//��ƶפJlistdata��N�i�H�̷�key��value�i��Ƨ�
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
			myalert.setTitle("���o�a�Ϧ�m");
			myalert.setMessage("�A�O�_�Q�Q�Φa�ϨӬd�ݦ�m");
			myalert.setIcon(R.drawable.a_36);
			myalert.setNegativeButton("�O", new ConfirmOnClickListener());
			myalert.setPositiveButton("�_", new CancelOnClickListener());
			myalert.setCancelable(true);
			myalert.show();
		   }	
	 }
    private class ConfirmOnClickListener implements OnClickListener{
    	//�]�w������s�ƥ�B�z
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
    	//�]�w�k����s�ƥ�B�z
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

    //�b�j�M��i��d�ߡAlistview���e�P���ܰʪ���k
	@Override
	public boolean onQueryTextChange(String query) {
		// TODO Auto-generated method stub
		newListdata=filterData(query);
		l1.setAdapter(new SimpleAdapter(OldItemList.this,newListdata, R.drawable.listview_style, 
				new String[]{"org_name","address","phone"}, new int[] { R.id.textView1,R.id.textView2,R.id.textView3}));
		return false;
	}

	//�d�ߧ������U���s��A���listview���e����k
	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		newListdata=filterData(query);
		l1.setAdapter(new SimpleAdapter(OldItemList.this,newListdata, R.drawable.listview_style, 
				new String[]{"org_name","address","phone"}, new int[] { R.id.textView1,R.id.textView2,R.id.textView3}));
		return false;
	}
    //�L�o��ƪ���k
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
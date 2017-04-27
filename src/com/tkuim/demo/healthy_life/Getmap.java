package com.tkuim.demo.healthy_life;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import android.app.Activity;
import android.os.Bundle;

public class Getmap extends Activity {
	
    private GoogleMap map;
    Bundle extras;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        	   
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();//產生地圖
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);//顯示一般地圖
        map.setMyLocationEnabled(true);//允許定位
        extras= getIntent().getExtras();
        double x=Double.parseDouble(extras.getString("X"));//接收x座標
        double y=Double.parseDouble(extras.getString("Y"));//接收y座標
        MarkerOptions markerOpt = new MarkerOptions();
        markerOpt.position(new LatLng(y, x));
        markerOpt.title(extras.getString("name"));//為地圖標記
        markerOpt.snippet(extras.getString("addr"));
        markerOpt.draggable(false);

        map.addMarker(markerOpt);
        LatLng mark = new LatLng(y,x);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mark, 16));
                      
    }

}
package com.example.universitycatalogue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class CollegeList extends AppCompatActivity implements ICollegeAdapter{

    RecyclerView recyclerView;
    CollegeListAdapter adapter;
    String countryName;
    TextView country;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_list);

        recyclerView = findViewById(R.id.collegeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(CollegeList.this));
        adapter = new CollegeListAdapter(this);
        recyclerView.setAdapter(adapter);
        countryName = getIntent().getExtras().getString("countryName");
        country = findViewById(R.id.Title);
        url = "http://universities.hipolabs.com/search?country="+countryName;
        country.setText(countryName);
        fetch();
    }

    private void fetch(){
        MySingleton.getInstance(CollegeList.this).addToRequestQueue(new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<CollegeInfo> collegeInfoArrayList = new ArrayList<>();
                for(int i=0; i<response.length(); i++){
                    JSONObject collegeJsonObject = null;
                    CollegeInfo ob = null;
                    try{
                        collegeJsonObject = response.getJSONObject(i);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                    try {
                        ob = new CollegeInfo(collegeJsonObject.getString("name"), collegeJsonObject.getJSONArray("web_pages").getString(0), collegeJsonObject.getString("state-province"));
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                    collegeInfoArrayList.add(ob);
                }
                adapter.updateColleges(collegeInfoArrayList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CollegeList.this.recyclerView.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }));
    }

    @Override
    public void onItemClicked(CollegeInfo collegeInfo) {
        new CustomTabsIntent.Builder().build().launchUrl(CollegeList.this, Uri.parse(collegeInfo.websiteUrl));
    }
}
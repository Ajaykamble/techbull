package com.techbull.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techbull.task.adapter.MoviewListAdapter;
import com.techbull.task.models.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout layoutSwipeRefresh;

    EditText editSearch;
    ArrayList<Movie> movieList;

    int currentPage=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutSwipeRefresh=(SwipeRefreshLayout) findViewById(R.id.layout_swipe);

        editSearch=(EditText) findViewById(R.id.edit_search);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                layoutSwipeRefresh.setRefreshing(true);
                currentPage=1;
                LoadMoviewList();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("ASK","AFTER TEXT CHANGE");
            }
        });


        movieList=new ArrayList<>();

        layoutSwipeRefresh.setOnRefreshListener(MainActivity.this);
        layoutSwipeRefresh.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        layoutSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                layoutSwipeRefresh.setRefreshing(true);
                currentPage=1;
                LoadMoviewList();
            }
        });
    }

    @Override
    public void onRefresh() {
        layoutSwipeRefresh.setRefreshing(true);
        currentPage=1;
        LoadMoviewList();
    }
    boolean shouldClear=true;
    public void LoadMoviewList(){

        String text=editSearch.getText().toString();
        String query="";

        if(text.equals(""))
        {
            query="batman";
        }
        else{
            query=text;
        }

        String url="https://www.omdbapi.com/?apikey=c2dffd6c&s="+query+"&page="+(currentPage);
        Log.e("ASK",url);


        MoviewListAdapter adapter = new MoviewListAdapter(movieList, MainActivity.this);
        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.layout_moviewlist);
        my_recycler_view.setHasFixedSize(true);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        my_recycler_view.setAdapter(adapter);


        movieList.clear();
        adapter.notifyDataSetChanged();

        StringRequest str=new StringRequest(
                Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                layoutSwipeRefresh.setRefreshing(false);

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("Search");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject moviewObj = jsonArray.getJSONObject(i);
                        String title=moviewObj.getString("Title");
                        String year=moviewObj.getString("Year");
                        String poster=moviewObj.getString("Poster");

                        movieList.add(new Movie(title,year,poster));
                    }
                    adapter.notifyDataSetChanged();
                }
                catch (Exception ex){
                    Log.e("ASK","Exception "+ex.getMessage());
                    Toast.makeText(MainActivity.this, "No Result Found", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                layoutSwipeRefresh.setRefreshing(false);
                Toast.makeText(MainActivity.this, "Unable to fetch result at this moment", Toast.LENGTH_SHORT).show();
            }
        }
        );
        Volley.newRequestQueue(this).add(str);
    }
    public void prevPage(View view){
        if(currentPage<=1)
        {
            currentPage=1;
        }
        else{
            currentPage--;
        }
        layoutSwipeRefresh.setRefreshing(true);
        LoadMoviewList();

    }
    public void nextPage(View view){
        currentPage++;
        layoutSwipeRefresh.setRefreshing(true);
        LoadMoviewList();
    }
}
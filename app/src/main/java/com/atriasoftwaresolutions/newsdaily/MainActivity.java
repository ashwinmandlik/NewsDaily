package com.atriasoftwaresolutions.newsdaily;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.atriasoftwaresolutions.newsdaily.Models.NewsApiResponse;
import com.atriasoftwaresolutions.newsdaily.Models.Newsheadlines;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener{

    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    Button b1,b2,b3,b4,b5,b6,b7;
    SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        dialog= new ProgressDialog(this);
        dialog.setTitle("fetching news");
        dialog.show();

        searchView=findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Fetching news for "+query);
                dialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsheadlines(listener,"general", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        b1=findViewById(R.id.btn_1);
        b1.setOnClickListener(this);

        b2=findViewById(R.id.btn_2);
        b2.setOnClickListener(this);

        b3=findViewById(R.id.btn_3);
        b3.setOnClickListener(this);

        b4=findViewById(R.id.btn_4);
        b4.setOnClickListener(this);

        b5=findViewById(R.id.btn_5);
        b5.setOnClickListener(this);

        b6=findViewById(R.id.btn_6);
        b6.setOnClickListener(this);

        b7=findViewById(R.id.btn_7);
        b7.setOnClickListener(this);




        RequestManager manager = new RequestManager(this);
        manager.getNewsheadlines(listener,"general", null);
    }

    private final OnfetchDataListener<NewsApiResponse> listener = new OnfetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<Newsheadlines> list, String message) {
        if (list.isEmpty()){
            Toast.makeText(MainActivity.this, "No data found:(", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }else {
            showNews(list);
            dialog.dismiss();
        }

        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, "An error occureed", Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<Newsheadlines> list) {
        recyclerView =findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adapter = new CustomAdapter(this, list,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnNewsClicked(Newsheadlines headlines) {
        startActivity(new Intent(MainActivity.this, DetailsActivity.class)
                .putExtra("data", headlines));

    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String category = button.getText().toString();
        dialog.setTitle("Fetching news for "+category);
        dialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getNewsheadlines(listener,category, null);

    }
}
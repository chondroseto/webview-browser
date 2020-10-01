package com.xela.browser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.xela.browser.adapter.HistoryAdapter;
import com.xela.browser.model.HistoryData;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    ArrayList<HistoryData> data_recycler;
    RecyclerView.Adapter adapter;
    RecyclerView rv;

    ImageButton back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rv = (RecyclerView) findViewById(R.id.datahistory_recview);
        back_btn = (ImageButton) findViewById(R.id.back);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(History.this, MainActivity.class);
                startActivity(intent);
            }
        });

        dataRecycler();
        adapter = new HistoryAdapter(data_recycler);
        rv.setLayoutManager(new LinearLayoutManager(History.this));
        rv.setAdapter(adapter);
    }

    public void dataRecycler(){
        data_recycler = new ArrayList<>();
        data_recycler.add(new HistoryData("1","mangapark.net"));
        data_recycler.add(new HistoryData("2","google.com"));
        data_recycler.add(new HistoryData("3","bing.com"));
    }
}
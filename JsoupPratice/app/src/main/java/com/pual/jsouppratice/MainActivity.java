package com.pual.jsouppratice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        new Thread(runnable).start();
//        setListView();
    }
    void findViews(){
        listView = (ListView)findViewById(R.id.list);
        textView = (TextView)findViewById(R.id.textView);
    }
    Runnable runnable = new Runnable() {

        ArrayList<String> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        Document document = null;

        @Override
        public void run() {
            try {
                document = Jsoup.connect("http://new.cpc.com.tw/Home/").get();
                Elements oilData = document.select("dl#OilPrice2>dd");
                for(Element data : oilData){
                    list.add(data.text());
                    stringBuilder.append(data.text());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this , android.R.layout.simple_list_item_1 , list);
                    listView.setAdapter(arrayAdapter);
                    textView.setText(stringBuilder);
                }
            });
        }
    };
}

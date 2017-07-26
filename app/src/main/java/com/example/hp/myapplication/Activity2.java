package com.example.hp.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by M.Fahad_Butt on 24/07/2017.
 */
public class Activity2 extends AppCompatActivity  implements View.OnClickListener{

    private EditText name;
    private Button btn;

    private ListView lv;
    private ArrayAdapter<String> adp;
    private ArrayList<String> al;
    private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("List View");
        ab.setDisplayHomeAsUpEnabled(true);

        rl =(RelativeLayout) findViewById(R.id.main);

        rl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });
        name = (EditText) findViewById(R.id.name);
        lv = (ListView) findViewById(R.id.listView);
        btn = (Button) findViewById(R.id.insert);
        al = new ArrayList<String>();
        al.add("Asad");

        adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(adp);
        btn.setOnClickListener(this);
    }

    //going back when press back on action bar
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (name.getText().toString().equals("")) {
            Toast.makeText(Activity2.this, "Enter the name", Toast.LENGTH_SHORT).show();
        } else {
            String data = name.getText().toString().trim();
            al.add(data);
            adp.notifyDataSetChanged();
            name.setText("");
            hideKeyboard();
        }

        //list view item click listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hideKeyboard();

            }
        });

        //list view item scroll down listener
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                hideKeyboard();
            }

            //when the scroll completed
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    //method to hide the keyboard
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

    }
}



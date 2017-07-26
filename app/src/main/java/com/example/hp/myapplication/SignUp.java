package com.example.hp.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by M.Fahad_Butt on 24/07/2017.
 */
public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private EditText email;
    private EditText password;
    private Button btn ;
    private SQLiteDatabase db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        email = (EditText) findViewById(R.id.addEmail);
        password = (EditText) findViewById(R.id.addPassword);
        btn = (Button) findViewById(R.id.signUp);

        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        db = openOrCreateDatabase("User_db", MODE_PRIVATE , null);
        db.execSQL("Insert into User Values (NULL , '"+ email.getText().toString()+"' , '"+password.getText().toString()+"'  )");
        Toast.makeText(this, "User Added ",   Toast.LENGTH_SHORT).show();
        finish();
    }
}
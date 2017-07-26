package com.example.hp.myapplication;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.DeviceLoginButton;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signIn;
    private EditText email;
    private EditText password;
    private Button fPass;
    private Button signUp;
    private RelativeLayout rl;

    private LoginButton loginButton;
    CallbackManager callBack ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Sign In");
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signIn = (Button) findViewById(R.id.signIn);
        fPass = (Button) findViewById(R.id.f_pass);
        signUp = (Button) findViewById(R.id.signUp);

        loginButton = (LoginButton) findViewById(R.id.fb_btn);

        callBack = CallbackManager.Factory.create();
        loginButton.registerCallback(callBack, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(MainActivity.this, "Login Successs", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        // touch outside to hide the keyboard
        rl = (RelativeLayout) findViewById(R.id.activity_main);
        rl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });
        signUp.setOnClickListener(this);
        signIn.setOnClickListener(this);
        fPass.setOnClickListener(this);
    }

    //method to hide the keyboard
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

    }

    // function for email validation
    public static boolean isEmailValid(String email) {
        //return !(email == null || TextUtils.isEmpty(email)) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern) && email.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        String data = email.getText().toString();
        switch (v.getId()) {
            case R.id.signIn: {
                if (data.equalsIgnoreCase("")) {
                    Toast.makeText(this, "Enter the email", Toast.LENGTH_LONG).show();
                } else {
                    if (isEmailValid(data)) {
                        Intent i = new Intent(this, Activity2.class);
                        i.putExtra("email", email.getText().toString());
                        startActivity(i);
                    } else {
                        Toast.makeText(MainActivity.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }

            case R.id.f_pass: {
                Toast.makeText(this, "Forgot Password", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.signUp: {
                Toast.makeText(MainActivity.this, "Sign Up", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
        callBack.onActivityResult(requestCode ,resultCode , data);
    }
}
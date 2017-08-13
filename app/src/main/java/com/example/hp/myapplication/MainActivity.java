package com.example.hp.myapplication;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONObject;
import java.net.URL;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button signIn;
    private EditText email;
    private EditText password;
    private Button fPass;
    private Button signUp;
    private RelativeLayout rl;
    ImageView user_picture;
    Bitmap mIcon1;
    String userId = "";

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

        // permission of accessing the data
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends" ));
        callBack = CallbackManager.Factory.create();
        loginButton.registerCallback(callBack, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Paste your Code here on FACEBOOK Login Success
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(final JSONObject object, GraphResponse response) {
                                Log.v("Main", response.toString());
                                try {
                                    String email = object.getString("email");
                                    String birthday = object.getString("birthday");
                                    userId = object.getString("id");
                                    final Dialog dialog = new Dialog(MainActivity.this);
                                    dialog.setContentView(R.layout.profile_data_preview);
                                    dialog.setTitle("Facebook Data");
                                    // find the feilds from the inflated layout
                                    TextView t1 = (TextView)dialog.findViewById(R.id.t1);
                                    t1.setText("Email = "+email.toString());
                                    TextView t2 = (TextView)dialog.findViewById(R.id.t2);
                                    t2.setText("Birthday = " +birthday.toString());
                                    TextView t3 = (TextView)dialog.findViewById(R.id.t3);
                                    t3.setText("Id = " +object.getString("id"));
                                    Button btn = (Button) dialog.findViewById(R.id.btn);
                                    user_picture=(ImageView)dialog.findViewById(R.id.imageView2);

                                  //This can be done in new thread not in the main thread
                                    new AsyncTask<String, Void, Bitmap>() {
                                       @Override
                                        protected Bitmap doInBackground(String... params) {
                                            Bitmap bitmap = null;
                                            try {
                                                String imageURL = "https://graph.facebook.com/" + object.getString("id") +"/picture?width=150&width=150";
                                                URL imageURI = new URL(imageURL);
                                                bitmap = BitmapFactory.decodeStream(imageURI.openConnection().getInputStream());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            mIcon1 = bitmap;
                                            return bitmap;
                                        }

                                        @Override
                                        protected void onPostExecute(Bitmap params) {
                                            user_picture.setImageBitmap(mIcon1);
                                            dialog.show();
                                        }
                                    }.execute();
                                    btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(MainActivity.this , ShowData.class);
                                            startActivity(i);
                                        }
                                    });
                                }catch (Exception ee )
                                {
                                    String msg = ee.getMessage();
                                }
                            }
                        });
                // those parameters that we want to get in the graph api response
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }
            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MainActivity.this, "Valid Email", Toast.LENGTH_SHORT).show();
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
        callBack.onActivityResult(requestCode ,resultCode , data);
    }

}
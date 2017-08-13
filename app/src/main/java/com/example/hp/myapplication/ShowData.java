package com.example.hp.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.hp.myapplication.R.id.listView;

/**
 * Created by M.FAHAD on 8/12/2017.
 */
public class ShowData extends AppCompatActivity {
    private ListView list;
    private ArrayList<facebookData> arrayList;
    private CustomAdapter customAdapter;
    private Context context;
    Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_data);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Facebook Post Data");

        context = getApplicationContext();
        list = (ListView)findViewById(listView);
        arrayList = new ArrayList<facebookData>();
        date = new Date();
        // Request for getting the data related to the Profile
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/feed",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                              /* handle the result */
                        JSONObject obj = null;
                        JSONArray jsonArray = null;
                        try {
                            obj = new JSONObject(response.getRawResponse());
                            jsonArray = obj.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                facebookData showData = new facebookData();
                                // use optString here because it returns the empty string if no field is found againt the string
                                if(jsonObject.optString("message") != "") {
                                    showData.setMessage(jsonObject.optString("message"));
                                }
                                else{
                                    showData.setMessage(" Empty");
                                }
                                String time = jsonObject.getString("created_time");
                                // This change the Created_date format which you get from facebook graph
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                                Date date = dateFormat.parse(time);
                                dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                                String formatedDate = dateFormat.format(date);
                                showData.setCreated_time(formatedDate);

                                if(jsonObject.optString("story") != "") {
                                    showData.setStory(jsonObject.optString("story"));
                                }else{
                                    showData.setStory(" Empty");
                                }
                                arrayList.add(showData);
                            }
                        } catch (Exception e) {
                            e.getMessage();
                        }
                        customAdapter = new CustomAdapter(context, R.layout.fb_data_item , arrayList);
                        list.setAdapter(customAdapter);
                    }

                }
        ).executeAsync();
        }
}
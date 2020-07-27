package com.papahades.bitcoin;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.jar.JarException;

public class MainActivity extends AppCompatActivity {
         private TextView  bitValue;

         private Spinner mSpinner;
// URL for bitaverage.com site not this url will be same for all currency but the part after BTC will include currency name like USD
         private final String URL_APi ="https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bitValue=findViewById(R.id.currencyShow);


        mSpinner=findViewById(R.id.spinner);
// Creating adapter for spinner see android docs for help its just copy paste you have done your self

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country_names, android.R.layout.simple_spinner_item);
        mSpinner.setAdapter(adapter);

// setting on seleceted listner for spinner

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
// what will happen on on select
                // note in this url we are adding country name from our adapter for spinner 
                           final String BaseUrl= URL_APi+adapterView.getItemAtPosition(i);  // new variable for full Url
                           LetsDoSomeNetworking(BaseUrl); //calling network method for the data on the bases of url
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // what will happen if nothing is selected
            }
        });


    }
 // creating network method

   public void  LetsDoSomeNetworking(String url){
       AsyncHttpClient Client =new AsyncHttpClient();  //creating http client named client
    Client.get(url,new JsonHttpResponseHandler(){          //creating handler
        @Override

        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {      // on success method
            super.onSuccess(statusCode, headers, response);
            try {
                String price = response.getString("last");    //storing data from json object in string variable
                 bitValue.setText(price);                 // updateing text view
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        @Override

  // on fail method
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);

        }
    });
    }

}
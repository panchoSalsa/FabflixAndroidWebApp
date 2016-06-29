package edu.uci.ics.fabflixmobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RedActivity extends ActionBarActivity {

    String validFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red);

        // handleling intents coming from blue or green activity
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("last_activity") != null) {
                Toast.makeText(this, "Last activity was " + bundle.get("last_activity") + ".", Toast.LENGTH_LONG).show();
            }
            String msg = bundle.getString("message");
            // do not want to do .equals(msg) before null check because it will throw a null exception
//            if(msg != null && !"".equals(msg)){
//                ((TextView)findViewById(R.id.last_page_msg_container)).setText(msg);
//            }
        }


        // Now lets make sure we can connect with https on tempfabflix
        // create a war file with these servlets and launch it
        // test the auto complete and and pop with a new search link to new servlet
        // oh remmeber the null, fix it.


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_red, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void callLoginServlet(){

        //

        final Map<String, String> params = new HashMap<String, String>();


        // post parameters
        String username = ((EditText)findViewById(R.id.username)).getText().toString();
        String password = ((EditText)findViewById(R.id.password)).getText().toString();

        params.put("username", username);
        params.put("password", password);
        Log.d("franco",username);
        Log.d("franco",password);

        // no user is logged in, so we must connect to the server
        RequestQueue queue = Volley.newRequestQueue(this);

        final Context context = this;
        String url = "http://52.27.151.25:8080/project4/AndroidLoginServlet";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("franco", response);
                        callBack(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("franco", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }
        };


        // Add the request to the RequestQueue.
        queue.add(postRequest);

//        validFlag = "valid";
        return;
    }

    public interface VolleyCallback{
        void onSuccess(String result);
    }

    private void callBack(String response) {
        Log.d("franco", "inside call me " + response );
        validFlag = response;
        loginProcessing(RedActivity.this);
    }
    public void loginProcessing(RedActivity view){
        //String msg = ((EditText)findViewById(R.id.red_2_blue_message)).getText().toString();

        Log.d("franco","insideloginProcess");
        Log.d("franco","validFlag: **" + validFlag + "**");
        if (validFlag.equals("valid")) {
            Intent goToIntent = new Intent(this, BlueActivity.class);
            startActivity(goToIntent);
        } else
            Toast.makeText(this, "Wrong username or password, try again", Toast.LENGTH_LONG).show();
    }

    public void loginRequest(View view){
        //String msg = ((EditText)findViewById(R.id.red_2_blue_message)).getText().toString();

        callLoginServlet();
    }

    public void goToBlue(View view){
        //String msg = ((EditText)findViewById(R.id.red_2_blue_message)).getText().toString();
        String msg = "hello";
        Intent goToIntent = new Intent(this, BlueActivity.class);

        goToIntent.putExtra("last_activity", "red");
        goToIntent.putExtra("message", msg);

        startActivity(goToIntent);
    }
    public void goToGreen(View view){
        //String msg = ((EditText)findViewById(R.id.red_2_green_message)).getText().toString();
        String msg = "hello";
        Intent goToIntent = new Intent(this, GreenActivity.class);

        goToIntent.putExtra("last_activity", "red");
        goToIntent.putExtra("message", msg);

        startActivity(goToIntent);
    }

}

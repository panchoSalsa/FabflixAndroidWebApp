package edu.uci.ics.fabflixmobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlueActivity extends ActionBarActivity {
    String jsonResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue);

//        Bundle bundle = getIntent().getExtras();
//        Toast.makeText(this, "Last activity was " + bundle.get("last_activity") + ".", Toast.LENGTH_LONG).show();

//        String msg = bundle.getString("message");
//        if(msg != null && !"".equals(msg)){
//            ((TextView)findViewById(R.id.last_page_msg_container)).setText(msg);
//        }

    }

    public void searchRequest(){
        final Map<String, String> params = new HashMap<String, String>();


        // post parameters
        String keyword = ((EditText)findViewById(R.id.search)).getText().toString();

        params.put("keyword", keyword);
        Log.d("franco", "keyword" + keyword);

        // no user is logged in, so we must connect to the server
        RequestQueue queue = Volley.newRequestQueue(this);

        final Context context = this;
        String url = "http://52.27.151.25:8080/project4/AndroidSearchServlet";


        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Log.d("franco", response);
                        //((TextView)findViewById(R.id.http_response)).setText(response);
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
        return;
    }

    private void callBack(String response){
        Log.d("franco", "inside BLUE callBack **" + response + "**");
        jsonResponse = response;
        searchProcessing(BlueActivity.this);
    }

    private void searchProcessing(BlueActivity view){
        if (jsonResponse != null && !jsonResponse.equals("")) {
            Log.d("franco", "inside searchProcessing");
            Gson gson = new Gson();
            bean mybean = gson.fromJson(jsonResponse, bean.class);
            List<String> listOfMovies = mybean.getListOfMovies();
            Intent goToIntent = new Intent(this, GreenActivity.class);
            //goToIntent.putExtra("movies", (Parcelable) listOfMovies);
            goToIntent.putStringArrayListExtra("movies",(ArrayList) listOfMovies);
            startActivity(goToIntent);
        }
    }
    public void search(View view) throws JSONException {
        // String msg = ((EditText)findViewById(R.id.blue_2_red_message)).getText().toString();
        searchRequest();
//        String yourstring = "{\"listOfMovies\":[\"Star Wars: Episode III - Revenge of the Sith\", \"Star Wars, Episode 3: Revenge of the Sith\"]}";
////        String yourstring = sharedpreferences.getString("response", null);
//        Intent goToIntent = new Intent(this, GreenActivity.class);
//        goToIntent.putExtra("response", "yourstring");
//        startActivity(goToIntent);
//        Gson gson = new Gson();
//        bean mybean = gson.fromJson(yourstring, bean.class);
////		JSONArray array = jsonObject.getJSONArray("movies");
//
//        List<String> listOfMovies = mybean.getListOfMovies();
//        for (int i = 0; i < listOfMovies.size(); i++) {
//            Log.d("franco","movie titles " + listOfMovies.get(i));
//        }
    }


    public void goToRed(View view){
       // String msg = ((EditText)findViewById(R.id.blue_2_red_message)).getText().toString();
        String msg = "hi";
        Intent goToIntent = new Intent(this, RedActivity.class);

        goToIntent.putExtra("last_activity", "blue");
        goToIntent.putExtra("message", msg);

        startActivity(goToIntent);
    }
    public void goToGreen(View view){
        //String msg = ((EditText)findViewById(R.id.blue_2_green_message)).getText().toString();
        String msg = "hi";
        Intent goToIntent = new Intent(this, GreenActivity.class);

        goToIntent.putExtra("last_activity", "blue");
        goToIntent.putExtra("message", msg);

        startActivity(goToIntent);
    }



    public class bean
    {
        private List<String> listOfMovies;


        public List<String> getListOfMovies() {
            return listOfMovies;
        }

        public void setListOfStates(List<String> listOfMovies) {
            this.listOfMovies = listOfMovies;
        }


    }

}

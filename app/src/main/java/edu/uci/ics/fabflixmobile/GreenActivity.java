package edu.uci.ics.fabflixmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class GreenActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        Log.d("franco", "creating green");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green);
        LinearLayout ll = (LinearLayout) findViewById(R.id.displayMovies);
        Bundle bundle = getIntent().getExtras();

        List<String> movies = bundle.getStringArrayList("movies");
        for (int i = 0; i < movies.size(); i++) {
            TextView tv = new TextView(this);
            tv.setText(movies.get(i));
            ll.addView(tv);
        }
        /*if(response != null && !"".equals(response)){
            //((TextView)findViewById(R.id.last_page_msg_container)).setText();


            // clashes throw json exception error !!!
//            JSONObject reader = new JSONObject(response);
//            JSONObject movies  = reader.getJSONObject("movies");
//            JSONArray getArray = movies.getJSONArray("title");
//            for (int i = 0; i < getArray.length(); i++) {
//                    Log.d("franco", "array value is " + getArray.getJSONObject(i).getString("title") );
//            }

        }*/
    }


    public void goToRed(View view){
        //String msg = ((EditText)findViewById(R.id.green_2_red_message)).getText().toString();
        String msg = "hi";

        Intent goToIntent = new Intent(this, RedActivity.class);

        goToIntent.putExtra("last_activity", "green");
        goToIntent.putExtra("message", msg);

        startActivity(goToIntent);
    }
    public void goToBlue(View view){
        //String msg = ((EditText)findViewById(R.id.green_2_blue_message)).getText().toString();
        String msg = "hi";
        Intent goToIntent = new Intent(this, BlueActivity.class);

        goToIntent.putExtra("last_activity", "green");
        goToIntent.putExtra("message", msg);

        startActivity(goToIntent);
    }

}

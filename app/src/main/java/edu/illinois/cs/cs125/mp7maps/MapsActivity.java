package edu.illinois.cs.cs125.mp7maps;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public Button getDirections;
    public EditText start;
    public EditText end;
    public String jsonString;

    String YOUR_API_KEY = "AIzaSyBOpGPVyJWQCtDhTXVpFhBF1ZZNIfR6r6s";

    private GoogleMap mMap;

    public MapsActivity() throws UnsupportedEncodingException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getDirections = (Button) findViewById(R.id.getDirections);
        start = (EditText) findViewById(R.id.start);
        end = (EditText) findViewById(R.id.end);

        getDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startAPICall();
                } catch (Exception e) {
                    Toast.makeText(MapsActivity.this, "\"Invalid input, please re-enter locations in the following format: Gregory Hall or Siebel Center\"", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    String START;
    String END;

    public void startAPICall() throws UnsupportedEncodingException {
        String START = start.getText().toString();
        String END = end.getText().toString();
        if (START.isEmpty()) {
            Toast.makeText(MapsActivity.this, "\"Invalid input, please re-enter locations in the following format: Gregory Hall or Siebel Center\"", Toast.LENGTH_LONG).show();
        }
        if (END.isEmpty()) {
            Toast.makeText(MapsActivity.this, "\"Invalid input, please re-enter locations in the following format: Gregory Hall or Siebel Center\"", Toast.LENGTH_LONG).show();
        }
        try {
            JsonObjectRequest jsonObjectRequest;
            jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    URL,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                response.toString();
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //UIUC Marker

        LatLng UIUC = new LatLng(40.1020, 88.2272);
        mMap.addMarker(new MarkerOptions().position(UIUC).title("University of Illinois Schedule Helper"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(UIUC));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

        String urlSTART = URLEncoder.encode(START, "utf-8");
        String urlEND = URLEncoder.encode(END, "utf-8");
        String URL = "https://maps.googleapis.com/maps/api/directions/json?origin=" + urlSTART + "&destination=" + urlEND + "&key=" + YOUR_API_KEY;
}

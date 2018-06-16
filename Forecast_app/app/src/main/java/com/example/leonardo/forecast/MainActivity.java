package com.example.leonardo.forecast;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends AppCompatActivity {

    private static MainActivity sInstance = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tvCid = (TextView) findViewById(R.id.tvCid);
        final EditText etCid = (EditText) findViewById(R.id.etCid);
        final TextView tvGraus = (TextView) findViewById(R.id.tvGraus);
        final TextView tvTemp = (TextView) findViewById(R.id.tvTemp);
        Button btnBuscar = (Button) findViewById(R.id.btnBusca);

        sInstance = this;

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cidade = etCid.getText().toString();
                String url = "https://api.apixu.com/v1/forecast.json?key=003af5b227e34c5aaa200336181406&q="+cidade+"&lang=pt";

                JsonObjectRequest reqJson = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {

                                    tvCid.setText(response.getJSONObject("location").getString("name"));

                                    tvTemp.setText(response.getJSONObject("current").getJSONObject("condition").getString("text"));

                                    tvGraus.setText(response.getJSONObject("current").getString("temp_c").concat("ºC / ").concat(response.getJSONObject("current").getString("temp_f").concat("ºF")));

                                } catch (JSONException e) {
                                    //Log.e("volley", "Erro");
                                    Toast.makeText(getApplicationContext(),"Cidade não encontrada",Toast.LENGTH_SHORT);
                                }
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),"Cidade não encontrada",Toast.LENGTH_SHORT);
                            }
                        });


                MySingleton.getInstance(sInstance).addToRequestQueue(reqJson);

            }
        });


    }
}

package com.example.zohai.healthapp;
import android.util.Log;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UbidotsClient {
    private UbiListener listener;

    public UbiListener getListener() {
        return listener;
    }

    public void setListener(UbiListener listener) {
        this.listener = listener;
    }

    public void handleUbidots(String Datasource ,String varId, String apiKey, final UbiListener listener) {

        final List<Value> results = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().addHeader("X-Auth-Token", apiKey)
                .url("http://things.ubidots.com/api/v1.6/devices/" + Datasource + "/" + varId + "/values/?page_size=100")
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("Chart", "Network error");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String body = response.body().string();
                Log.d("Chart", body);

                try {
                    JSONObject jObj = new JSONObject(body);
                    JSONArray jRes = jObj.getJSONArray("results");
                    for (int i=0; i < jRes.length(); i++) {
                        JSONObject obj = jRes.getJSONObject(i);
                        Value val = new Value();
                        val.timestamp = obj.getLong("timestamp");
                        val.value  = (float) obj.getDouble("value");
                        results.add(val);
                    }

                    listener.onDataReady(results);

                }
                catch(JSONException jse) {
                    jse.printStackTrace();
                }

            }
        });

    }


    public static class Value {
        public float value;
       public long timestamp;

    }

    public interface  UbiListener {
        public void onDataReady( List<Value> result);
    }
}


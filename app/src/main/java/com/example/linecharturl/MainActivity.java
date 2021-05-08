package com.example.linecharturl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private LineChart mChart;
    public String TAG = "YOUR CLASS NAME";
    final DateFormat dFormatHHMM = new SimpleDateFormat("HH:mm");
    final Map<Float, String> map = new HashMap<>();
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);

        mChart = (LineChart) findViewById(R.id.chart);
        mChart.setDrawGridBackground(false);
        //mChart.setDescription("");
        mChart.getDescription().setText("Description of my chart");
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(true);
        //mChart.setMarkerView(mv);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener()
        {
            @Override
            public void onValueSelected(Entry e, Highlight h)
            {
                dialog.dismiss();
                float x = e.getX();
                float y = e.getY();
                String date = map.get(x);
                dialog.setMessage("Temperatura : " + y + " " + date);
                dialog.show();
            }

            @Override
            public void onNothingSelected()
            {

            }
        });
//        mChart.setOnChartGestureListener(new OnChartGestureListener() {
//            @Override
//            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
//
//            }
//
//            @Override
//            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
//
//            }
//
//            @Override
//            public void onChartLongPressed(MotionEvent me) {
//
//            }
//
//            @Override
//            public void onChartDoubleTapped(MotionEvent me) {
//                Toast.makeText(MainActivity.this, "Double", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onChartSingleTapped(MotionEvent me) {
//                Toast.makeText(MainActivity.this, "Single", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
//
//            }
//
//            @Override
//            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
//
//            }
//
//            @Override
//            public void onChartTranslate(MotionEvent me, float dX, float dY) {
//
//            }
//        });

        XAxis xl = mChart.getXAxis();
        xl.setAvoidFirstLastClipping(true);

//        xl.setLabelCount(6, true);
//        xl.setAxisMinimum(1);
//        xl.setAxisMaximum(100);
        xl.setValueFormatter(new ValueFormatter() {

            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                Log.d(TAG, "value: " + value +
                "axis: " + axis.mEntries[0] +
                "axis: " + axis.mEntries[1]);
                String time = "";
                if ( map.containsKey(value) ) {
                    try {
                        Log.d(TAG, "map.get(value): " + map.get(value) );

                        DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(dFormat.parse(map.get(value)));
                        TimeZone tzInAmerica1 = TimeZone.getTimeZone("America/Santiago");
                        dFormat.setTimeZone(tzInAmerica1);
                        Log.d(TAG, "Santiago: " + dFormat.format(cal.getTime()) );
                        Log.d(TAG, "TimeZone : " + tzInAmerica1.getID() + " - " + tzInAmerica1.getDisplayName());
                        Log.d(TAG, "TimeZone : " + tzInAmerica1);

                        // To TimeZone America/New_York
//                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//
//                        String dateInString = "2021-04-24T10:43:20Z";
//                        Date date = formatter.parse(dateInString);
//                        TimeZone tz = TimeZone.getTimeZone("GMT");

                        // From TimeZone Asia/Singapore
//                        System.out.println("TimeZone : " + tz.getID() + " - " + tz.getDisplayName());
//                        System.out.println("TimeZone : " + tz);
//                        System.out.println("Date (Singapore) : " + formatter.format(date));
//
//                        SimpleDateFormat sdfAmerica = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//                        TimeZone tzInAmerica = TimeZone.getTimeZone("America/Santiago");
//                        sdfAmerica.setTimeZone(tzInAmerica);
//
//                        String sDateInAmerica = sdfAmerica.format(formatter.parse(dateInString)); // Convert to String first
//                        Date dateInAmerica = formatter.parse(sDateInAmerica); // Create a new Date object
//
//                        System.out.println("Date (New York) (String) : " + sDateInAmerica);
//                        System.out.println("Date (New York) (Object) : " + formatter.format(dateInAmerica));
//                        time = dFormatHHMM.format(dateInAmerica);

                        time = dFormatHHMM.format(cal.getTime());
                        Log.d(TAG, "time: " + time );
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return time;
            }
        });

        YAxis leftAxis = mChart.getAxisLeft();
        //leftAxis.setInverted(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);

        drawChart();
    }

    private void drawChart() {

        String url = "https://api.thingspeak.com/channels/1336629/feeds.json?api_key=DOKY6QEU0IR040NF";

        StringRequest strReq = new StringRequest(url,
            new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    Log.d(TAG, "Response: " + response);

                    try {
                        ArrayList<Entry> y = parseJsonData(response);
                        LineDataSet dataset = new LineDataSet(y, "NAV Data Value");
                        //dataset.setLineWidth(1.5f);
                        //dataset.setColor(Color.RED);
//                        dataset.setCircleRadius(4f);
//                        dataset.setCircleColor(Color.RED);
//                        dataset.setDrawCircleHole(false);
                        //dataset.setValueTextSize(9f);
                        //dataset.setDrawFilled(true);
                        //dataset.setFormLineWidth(1f);
                        //dataset.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
                        //dataset.setHighlightLineWidth(50f);
                        //dataset.setDrawValues(true);
                        LineData data = new LineData(dataset);
                        mChart.setData(data);
                        mChart.invalidate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error: " + error.getMessage());
                }
        });
        strReq.setRetryPolicy(new RetryPolicy() {

            @Override
            public void retry(VolleyError arg0) throws VolleyError {}

            @Override
            public int getCurrentTimeout() {
                return 0;
            }

            @Override
            public int getCurrentRetryCount() {
                return 0;
            }
        });
        strReq.setShouldCache(false);

        RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
        rQueue.add(strReq);
    }

    private ArrayList<Entry> parseJsonData(String jsonString) {
        ArrayList<Entry> entries = new ArrayList<Entry>();
        try {
            JSONObject object = new JSONObject(jsonString.toString());
            JSONArray jsonArray = object.getJSONArray("feeds");

            for(int i = 0; i < jsonArray.length(); ++i) {
                JSONObject explrObject = jsonArray.getJSONObject(i);
                float value = Float.parseFloat((String)explrObject.get("field1"));
                String date = (String)explrObject.get("created_at");
                entries.add(new Entry(i, value, date));
                map.put((float)i, date);
            }
            Log.d(TAG, "map: " + map);
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
            e.printStackTrace();
        }
        Log.i("parse", "finish");

        return entries;
    }

    private ArrayList<Entry> parseJsonData() {
        StringBuffer json = new StringBuffer()
                .append("{\"feeds\": [                                            ")
                .append("{                                                        ")
                .append("    \"created_at\": \"2021-04-20T17:15:53Z\",            ")
                .append("        \"entry_id\": 2928,                              ")
                .append("        \"field1\": \"17.70000\",                        ")
                .append("        \"field2\": \"97.80000\"                         ")
                .append("},                                                       ")
                .append("{                                                        ")
                .append("    \"created_at\": \"2021-04-20T17:16:22Z\",            ")
                .append("        \"entry_id\": 2929,                              ")
                .append("        \"field1\": \"17.70000\",                        ")
                .append("        \"field2\": \"97.80000\"                         ")
                .append("},                                                       ")
                .append("{                                                        ")
                .append("    \"created_at\": \"2021-04-20T17:16:52Z\",            ")
                .append("        \"entry_id\": 2930,                              ")
                .append("        \"field1\": \"17.70000\",                        ")
                .append("        \"field2\": \"97.80000\"                         ")
                .append("},                                                       ")
                .append("{                                                        ")
                .append("    \"created_at\": \"2021-04-20T17:17:22Z\",            ")
                .append("        \"entry_id\": 2931,                              ")
                .append("        \"field1\": \"17.70000\",                        ")
                .append("        \"field2\": \"97.80000\"                         ")
                .append("}                                                        ")
                .append("]}                                                       ");

        ArrayList<Entry> entries = new ArrayList<Entry>();
        try {
            JSONObject object = new JSONObject(json.toString());
            JSONArray jsonArray = object.getJSONArray("feeds");

            for(int i = 0; i < jsonArray.length(); ++i) {
                JSONObject explrObject = jsonArray.getJSONObject(i);
                float value = Float.parseFloat((String)explrObject.get("field1"));
                String date = (String)explrObject.get("created_at");
                entries.add(new Entry((float)i, value));
                map.put((float)i, date);
            }
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
            e.printStackTrace();
        }
        Log.i("parse", "finish");

        return entries;
    }
}
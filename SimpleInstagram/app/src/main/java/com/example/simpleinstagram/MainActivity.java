package com.example.simpleinstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.simpleinstagram.Utils.generatedURL;
import static com.example.simpleinstagram.Utils.getResponseFromURL;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public static ArrayList<User> users = new ArrayList<>();
    public ProgressBar progressBar;
    public ImageView imageView;

    static class VKQueryTask extends AsyncTask<URL, Void, String> {
        private WeakReference<MainActivity> weakActivity;

        VKQueryTask (MainActivity activity){
            weakActivity = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                response = getResponseFromURL(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String text;
            String high;
            try {
                assert response != null;
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                JSONArray jsonArray = jsonObject1.getJSONArray("items");
                for (int i = 0; i < Math.min(15, jsonArray.length()); i++) {
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    text = jsonObject2.getString("text");
                    high = jsonObject2.getString("photo_604");
                    URL url = new URL(jsonObject2.getString("photo_130"));
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    users.add(new User(BitmapFactory.decodeStream(connection.getInputStream()), text, high));
                    connection.disconnect();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPreExecute() {
            MainActivity activity = weakActivity.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected void onPostExecute(String response) {
            MainActivity activity = weakActivity.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.progressBar.setVisibility(ProgressBar.INVISIBLE);
            UserAdapter userAdapter = new UserAdapter(users);
            activity.recyclerView.setAdapter(userAdapter);
        }
    }

    static class getHighPhoto extends AsyncTask<String, Void, Bitmap> {

        private WeakReference<MainActivity> weakActivity;

        getHighPhoto(MainActivity activity){
            weakActivity = new WeakReference<>(activity);
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                connection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPreExecute() {
            MainActivity activity = weakActivity.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            MainActivity activity = weakActivity.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.progressBar.setVisibility(ProgressBar.INVISIBLE);
            activity.imageView.setVisibility(ImageView.VISIBLE);
            activity.imageView.setImageBitmap(bitmap);
            activity.recyclerView.setVisibility(RecyclerView.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.my_recycler_view);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.image);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        URL generatedURL = generatedURL();
        new VKQueryTask(this).execute(generatedURL);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        new getHighPhoto(MainActivity.this).execute(users.get(position).getHigh_photo_url());
                    }
                })
        );
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setVisibility(ImageView.INVISIBLE);
                recyclerView.setVisibility(RecyclerView.VISIBLE);
            }
        });
    }

    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        RecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
                return true;
            }
            return false;
        }

        @Override public void onTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent motionEvent) { }

        @Override
        public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}
    }

}



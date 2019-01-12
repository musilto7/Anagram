package com.example.anagram;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import mistoCinu.MistoCinu;

public class MainActivity extends Activity {

    static ArrayAdapter<String> arrayAdapter;
    static Handler handler;
    private static MistoCinu misto;
    ListView listView;
    Thread downloadThread;

    public static File getInternalDir() {
        return getInternalDir();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                findViewById(R.id.button).setEnabled(true);
            }
        };


        downloadThread = (Thread) getLastNonConfigurationInstance();
        if (downloadThread == null) {
            findViewById(R.id.button).setEnabled(false);
            downloadThread = new ThreadForLoadingData(getAssets());
            downloadThread.start();
        } else {
            if (downloadThread.isAlive())
                findViewById(R.id.button).setEnabled(false);
        }
        listView = (ListView) findViewById(R.id.listView);
        if (arrayAdapter == null)
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new LinkedList<String>());
        listView.setAdapter(arrayAdapter);

    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        return downloadThread;
    }

    public void onClick(View view) {
        arrayAdapter.clear();
        arrayAdapter.addAll(misto.getAnagrams(((EditText) findViewById(R.id.edittext)).getText().toString()));

    }

    @Override
    public void finish() {
        super.finish();
        System.gc();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public class ThreadForLoadingData extends Thread {
        AssetManager asset;

        public ThreadForLoadingData(AssetManager asset) {
            this.asset = asset;
        }

        @Override
        public void run() {
            try {
                misto = new MistoCinu(asset);
                handler.sendEmptyMessage(0);
            } catch (IOException e) {
                Log.e("anagramLoading", e.getStackTrace().toString());
                System.exit(1);

            } catch (ClassNotFoundException e) {
                Log.e("anagramLoading", e.getStackTrace().toString());
                System.exit(1);
            }

        }
    }


}

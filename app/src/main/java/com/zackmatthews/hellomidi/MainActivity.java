package com.zackmatthews.hellomidi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MidiHelper.MidiHelperEventListener{
    private TextView statusTextView;
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MidiHelper.instance(MainActivity.this).presentDevices();
            }
        });

        MidiHelper.instance(MainActivity.this).registerMidiHelperEventListener(this);

        statusTextView = (TextView)findViewById(R.id.statusText);
        scrollView = (ScrollView)findViewById(R.id.scrollview);
    }

    @Override
    public void onMidiHelperStatusEvent(final String statusText) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                statusTextView.append("\n" + statusText + "\n");
                scrollView.fullScroll(View.FOCUS_DOWN);

                if(statusText.contains(EventTriggerHelper.LAUNCH_APP_EVENT_TRIGGER)){
                    EventTriggerHelper.instance().openApp(MainActivity.this, "com.google.android.talk");
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

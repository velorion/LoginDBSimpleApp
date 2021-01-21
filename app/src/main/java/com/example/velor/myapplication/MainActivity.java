package com.example.velor.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.DialogFragment;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        populateListView();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new AddDialogFragment();
                dialog.show(getSupportFragmentManager(), "add");
            }
        });

        FloatingActionButton fab1 = findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new UpdateDialogFragment();
                dialog.show(getSupportFragmentManager(), "update");
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new DeleteDialogFragment();
                dialog.show(getSupportFragmentManager(), "delete");
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        populateListView();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void populateListView() {
        SimpleDatabaseHelper sql = new SimpleDatabaseHelper(getBaseContext());
        Cursor cursor = sql.getAll();
        String[] fromFieldNames = new String[] {"_id", "name", "discipline", "grade"};
        int[] toViewIDs = new int[] {R.id.textView, R.id.textView1, R.id.textView2, R.id.textView3};
        SimpleCursorAdapter sca = new SimpleCursorAdapter(getBaseContext(), R.layout.item_layout, cursor,  fromFieldNames, toViewIDs,0);
        ListView li = findViewById(R.id.listViewGrades);
        li.setAdapter(sca);
    }
}



package com.barracudapff.hoobes.flatter.activities.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.adapters.SettingsAdapter;

public class SettingsActivity extends AppCompatActivity {
    RecyclerView settingsRecyclerView;
    SettingsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.action_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new SettingsAdapter(this);

        settingsRecyclerView = findViewById(R.id.settings_list);
        settingsRecyclerView.setHasFixedSize(true);
        settingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        settingsRecyclerView.setAdapter(adapter);
        settingsRecyclerView.addItemDecoration(
                new DividerItemDecoration(settingsRecyclerView.getContext(),DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}

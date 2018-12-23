package com.barracudapff.hoobes.flatter.fragments.settings;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.barracudapff.hoobes.flatter.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.activities.settings.ProfileSettingsActivity;
import com.barracudapff.hoobes.flatter.database.models.User;

public class SettingsNamesFragment extends SettingsBaseFragment {
    EditText editTextFName;
    EditText editTextSName;

    public SettingsNamesFragment() {
        // Required empty public constructor
    }

    public static SettingsNamesFragment newInstance() {
        SettingsNamesFragment fragment = new SettingsNamesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_names, container, false);
        editTextFName = view.findViewById(R.id.settings_f_name);
        editTextSName = view.findViewById(R.id.settings_s_name);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_confirm, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_name:
                if (!validateStringNR(editTextFName) || !validateStringNR(editTextFName)) {
                    return false;
                }
                String fName = editTextFName.getText().toString();
                String sName = editTextSName.getText().toString();
                if (fName.length() > 20 || fName.length() < 3 || sName.length() > 20 || sName.length() < 3) {
                    Toast.makeText(getContext(), "Некоректное имя", Toast.LENGTH_SHORT).show();
                    return false;
                }
                write(User.F_NAME, fName);
                write(User.S_NAME, sName);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update() {
        Toolbar bar = ((ProfileSettingsActivity) getActivity()).getBar();
        if (bar != null) {
            bar.setTitle("Имя и фамилия");
            bar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_close_24dp));
            bar.setNavigationOnClickListener(v -> ((ProfileSettingsActivity) getActivity()).changePage(0));
            setHasOptionsMenu(true);
        }
    }
}

package com.barracudapff.hoobes.flatter.fragments.settings;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.activities.settings.ProfileSettingsActivity;
import com.barracudapff.hoobes.flatter.database.models.User;

public class SettingsAboutFragment extends SettingsBaseFragment {
    EditText editTextAbout;

    public SettingsAboutFragment() {
        // Required empty public constructor
    }

    public static SettingsAboutFragment newInstance() {
        SettingsAboutFragment fragment = new SettingsAboutFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_about, container, false);
        editTextAbout = view.findViewById(R.id.settings_about);
        InputMethodManager im = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showSoftInput(editTextAbout, 0);
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
                if (!validateString(editTextAbout)) {
                    return false;
                }
                String about = editTextAbout.getText().toString();
                write(User.ABOUT, about);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update() {
        Toolbar bar = ((ProfileSettingsActivity) getActivity()).getBar();
        if (bar != null) {
            bar.setTitle("О себе");
            bar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_close_24dp));
            bar.setNavigationOnClickListener(v -> ((ProfileSettingsActivity) getActivity()).changePage(0));
            setHasOptionsMenu(true);
        }
    }
}

package com.barracudapff.hoobes.flatter.fragments.settings;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.barracudapff.hoobes.flatter.R;

import android.widget.EditText;
import android.widget.Toast;

import com.barracudapff.hoobes.flatter.activities.settings.ProfileSettingsActivity;
import com.barracudapff.hoobes.flatter.database.models.User;

public class SettingsAgeFragment extends SettingsBaseFragment {
    EditText editTextAge;

    public SettingsAgeFragment() {
        // Required empty public constructor
    }

    public static SettingsAgeFragment newInstance() {
        SettingsAgeFragment fragment = new SettingsAgeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_age, container, false);
        editTextAge = view.findViewById(R.id.settings_f_name);
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
                if (!validateInt(editTextAge)) {
                    return false;
                }
                int age = Integer.parseInt(editTextAge.getText().toString());
                if (age > 80 || age < 16) {
                    Toast.makeText(getContext(), "Некоректный возраст", Toast.LENGTH_SHORT).show();
                    return false;
                }
                write(User.AGE, age);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update() {
        Toolbar bar = ((ProfileSettingsActivity) getActivity()).getBar();
        if (bar != null) {
            bar.setTitle("Возраст");
            bar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_close_24dp));
            bar.setNavigationOnClickListener(v -> ((ProfileSettingsActivity) getActivity()).changePage(0));
            setHasOptionsMenu(true);
        }
    }
}

package com.barracudapff.hoobes.flatter.fragments.settings;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.activities.settings.ProfileSettingsActivity;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsPasswordFragment extends SettingsBaseFragment {
    EditText editTextOldPas;
    EditText editTextNewPas;
    EditText editTextConfNewPas;

    public SettingsPasswordFragment() {
        // Required empty public constructor
    }

    public static SettingsPasswordFragment newInstance() {
        SettingsPasswordFragment fragment = new SettingsPasswordFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_password, container, false);
        editTextOldPas = view.findViewById(R.id.settings_f_name);
        editTextNewPas = view.findViewById(R.id.settings_s_name);
        editTextConfNewPas = view.findViewById(R.id.settings_new_confirm_pass);
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
                if (!validatePass(editTextOldPas) || !validatePass(editTextNewPas) || !validatePass(editTextConfNewPas)) {
                    return false;
                }
                String oldPass = editTextOldPas.getText().toString();
                String newPass = editTextNewPas.getText().toString();
                String newConfPass = editTextConfNewPas.getText().toString();
                if (!newPass.equals(newConfPass)) {
                    Toast.makeText(getContext(), "Неверно введен новый пароль", Toast.LENGTH_SHORT).show();
                    return false;
                }
                AuthCredential credential = EmailAuthProvider
                        .getCredential(FirebaseAuth.getInstance().getCurrentUser().getEmail()
                                , oldPass);
                FirebaseAuth.getInstance().getCurrentUser()
                        .reauthenticate(credential)
                        .addOnCompleteListener(task -> FirebaseAuth.getInstance().getCurrentUser()
                                .updatePassword(newPass)
                                .addOnCompleteListener(task1 -> hideProgressDialog())
                                .addOnSuccessListener(task1 -> {
                                    Toast.makeText(getContext(), "Пароль изменен", Toast.LENGTH_SHORT).show();
                                    ((ProfileSettingsActivity) getActivity()).changePage(0);
                                })
                                .addOnFailureListener(e -> {
                                    e.printStackTrace();
                                    Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                                }))
                        .addOnFailureListener(e -> Toast.makeText(getContext(), "Неправильный пароль", Toast.LENGTH_SHORT).show());

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update() {
        Toolbar bar = ((ProfileSettingsActivity) getActivity()).getBar();
        if (bar != null) {
            bar.setTitle("Пароль");
            bar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_close_24dp));
            bar.setNavigationOnClickListener(v -> ((ProfileSettingsActivity) getActivity()).changePage(0));
            setHasOptionsMenu(true);
        }
    }
}

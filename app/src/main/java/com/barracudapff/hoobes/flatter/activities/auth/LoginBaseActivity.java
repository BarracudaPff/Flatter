package com.barracudapff.hoobes.flatter.activities.auth;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.database.FirebassDatabaseHelper;
import com.barracudapff.hoobes.flatter.database.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class LoginBaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected DatabaseReference mDatabase;
    protected FirebaseAuth mAuth;

    private ProgressDialog mProgressDialog;

    protected int layoutIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutIndex);

        //Firebase connections
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        initViews();
        initListeners();
    }


    protected abstract void initViews();

    protected abstract void initListeners();

    protected boolean validateForm(TextView... views) {
        boolean result = true;
        for (TextView view : views) {
            result = setTextRequired(view, result);
        }
        return result;
    }

    private boolean setTextRequired(TextView view, boolean result) {
        System.out.println("Result is: " + view.getId());
        if (TextUtils.isEmpty(view.getText().toString())) {
            try {
                view.setError(getString(R.string.required));
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            return false;
        } else {
            try {
                view.setError(null);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    protected void writeNewUser(Task<AuthResult> task, EditText email, EditText firstName, EditText secondName) {
        User user = new User(email.getText().toString().replace(" ", "").toLowerCase()
                , firstName.getText().toString().replace(" ", "")
                , secondName.getText().toString().replace(" ", ""));
        FirebassDatabaseHelper.setCurrentUser(user);
        mDatabase.child("users")
                .child(task.getResult().getUser().getUid())
                .setValue(user);
    }

    protected void notifyUser(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle(getString(R.string.error_login_title))
                .setMessage(getString(id))
                .setPositiveButton("ОК", (dialog, i) -> dialog.cancel())
                .setCancelable(true)
                .create()
                .show();
    }

    protected void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(getString(R.string.loading));
        }

        mProgressDialog.show();
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}

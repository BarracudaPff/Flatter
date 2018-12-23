package com.barracudapff.hoobes.flatter.fragments.settings;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.activities.settings.ProfileSettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class SettingsBaseFragment extends Fragment {
    public abstract void update();

    private ProgressDialog mProgressDialog;

    protected boolean validateString(TextView view) {
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
            return true;
        }
    }

    protected boolean validateInt(TextView view) {
        if (!validateString(view)) {
            return false;
        } else if (!view.getText().toString().matches("[0-9]+")) {
            try {
                view.setError(getString(R.string.request_int));
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
            return true;
        }
    }

    protected boolean validatePass(TextView view) {
        if (!validateString(view)) {
            return false;
        } else if (!validatePass(view.getText().toString())) {
            try {
                view.setError(getString(R.string.pass_min_length));
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
            return true;
        }
    }

    protected boolean validateStringNR(TextView view) {
        if (!isCharac(view.getText().toString())) {
            try {
                view.setError(getString(R.string.request_name));
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
            return true;
        }
    }

    private boolean validatePass(String s) {
        return s.length() >= 6 && !s.contains(" ");
    }

    private boolean isCharac(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    protected void write(String key, Object value) {
        showProgressDialog();
        FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(key)
                .setValue(value)
                .addOnCompleteListener(task -> hideProgressDialog())
                .addOnSuccessListener(task -> {
                    Toast.makeText(getContext(), "Changed", Toast.LENGTH_SHORT).show();
                    ((ProfileSettingsActivity) getActivity()).changePage(0);
                })
                .addOnFailureListener(task -> Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show());
    }

    protected void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
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

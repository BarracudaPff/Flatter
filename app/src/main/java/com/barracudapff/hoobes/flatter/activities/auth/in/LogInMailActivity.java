package com.barracudapff.hoobes.flatter.activities.auth.in;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.activities.auth.LoginBaseActivity;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LogInMailActivity extends LoginBaseActivity {
    public static final String TAG = "LogInMailActivity";

    Animation animation, animFadeIn, animFadeInWithListener, animationCard;
    ImageView imageView;
    CardView cardView;

    private Intent result;

    private EditText mEmailField;
    private EditText mPasswordField;
    private FloatingActionButton fab;
    private TextView mForgotText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        result = new Intent();
        layoutIndex = R.layout.activity_log_in_mail;
        super.onCreate(savedInstanceState);
        initAnimations();
    }

    @Override
    protected void initViews() {
        fab = findViewById(R.id.fab_log_in);
        cardView = findViewById(R.id.card_log_container);
        imageView = findViewById(R.id.iv_log_in_logo);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        animFadeInWithListener = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animationCard = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

        mEmailField = findViewById(R.id.sign_up_s_name);
        mPasswordField = findViewById(R.id.sign_up_pass);
        mForgotText = findViewById(R.id.forgot_pass);
    }

    @Override
    protected void initListeners() {
        fab.setOnClickListener(this::onClick);

        mForgotText.setOnClickListener(v -> {
            Toast.makeText(this,"TODO", Toast.LENGTH_SHORT).show();
        });
    }

    private void initAnimations() {
        animFadeInWithListener.setAnimationListener(new Animation.AnimationListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);

                fab.startAnimation(LogInMailActivity.this.animation);
                cardView.startAnimation(LogInMailActivity.this.animationCard);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        imageView.setAnimation(animFadeIn);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            imageView.startAnimation(animFadeInWithListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        fab.setOnClickListener(null);
        animFadeInWithListener.setAnimationListener(null);
        fab = null;
    }

    @Override
    public void onClick(View v) {
        signIn();
    }

    private void signIn() {
        Log.d(TAG, "signIn");
        if (!validateForm(mEmailField, mPasswordField)) {
            return;
        }

        showProgressDialog();
        String email = mEmailField.getText().toString().replace(" ", "").toLowerCase();
        String password = mPasswordField.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    hideProgressDialog();

                    if (task.isSuccessful()) {
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                })
                .addOnFailureListener(e -> {
                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        notifyUser(R.string.error_login_password_login);
                    } else if (e instanceof FirebaseAuthInvalidUserException) {
                        String errorCode = ((FirebaseAuthInvalidUserException) e).getErrorCode();

                        if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                            notifyUser(R.string.error_login_password_login);
                        } else if (errorCode.equals("ERROR_USER_DISABLED")) {
                            notifyUser(R.string.error_login_disable);
                        } else {
                            notifyUser(R.string.error_login_extra);
                        }
                    }
                });
        // TODO: 12.09.2018 Handle every error
        //    ("ERROR_INVALID_CUSTOM_TOKEN", "The custom token format is incorrect. Please check the documentation."));
        //    ("ERROR_CUSTOM_TOKEN_MISMATCH", "The custom token corresponds to a different audience."));
        //    ("ERROR_INVALID_CREDENTIAL", "The supplied auth credential is malformed or has expired."));
        //    ("ERROR_INVALID_EMAIL", "The email address is badly formatted."));
        //    ("ERROR_WRONG_PASSWORD", "The password is invalid or the user does not have a password."));
        //    ("ERROR_USER_MISMATCH", "The supplied credentials do not correspond to the previously signed in user."));
        //    ("ERROR_REQUIRES_RECENT_LOGIN", "This operation is sensitive and requires recent authentication. Log in again before retrying this request."));
        //    ("ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL", "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address."));
        //    ("ERROR_EMAIL_ALREADY_IN_USE", "The email address is already in use by another account."));
        //    ("ERROR_CREDENTIAL_ALREADY_IN_USE", "This credential is already associated with a different user account."));
        //    ("ERROR_USER_DISABLED", "The user account has been disabled by an administrator."));
        //    ("ERROR_USER_TOKEN_EXPIRED", "The user\'s credential is no longer valid. The user must sign in again."));
        //    ("ERROR_USER_NOT_FOUND", "There is no user record corresponding to this identifier. The user may have been deleted."));
        //    ("ERROR_INVALID_USER_TOKEN", "The user\'s credential is no longer valid. The user must sign in again."));
        //    ("ERROR_OPERATION_NOT_ALLOWED", "This operation is not allowed. You must enable this service in the console."));
        //    ("ERROR_WEAK_PASSWORD", "The given password is invalid."));

        //case "ERROR_INVALID_CUSTOM_TOKEN":
        //                            Toast.makeText(MainActivity.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
        //                            break;
        //
        //                        case "ERROR_CUSTOM_TOKEN_MISMATCH":
        //                            Toast.makeText(MainActivity.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
        //                            break;
        //
        //                        case "ERROR_INVALID_CREDENTIAL":
        //                            Toast.makeText(MainActivity.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
        //                            break;
        //
        //                        case "ERROR_INVALID_EMAIL":
        //                            Toast.makeText(MainActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
        //                            etEmail.setError("The email address is badly formatted.");
        //                            etEmail.requestFocus();
        //                            break;
        //
        //                        case "ERROR_WRONG_PASSWORD":
        //                            Toast.makeText(MainActivity.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
        //                            etPassword.setError("password is incorrect ");
        //                            etPassword.requestFocus();
        //                            etPassword.setText("");
        //                            break;
        //
        //                        case "ERROR_USER_MISMATCH":
        //                            Toast.makeText(MainActivity.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
        //                            break;
        //
        //                        case "ERROR_REQUIRES_RECENT_LOGIN":
        //                            Toast.makeText(MainActivity.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
        //                            break;
        //
        //                        case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
        //                            Toast.makeText(MainActivity.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
        //                            break;
        //
        //                        case "ERROR_EMAIL_ALREADY_IN_USE":
        //                            Toast.makeText(MainActivity.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
        //                            etEmail.setError("The email address is already in use by another account.");
        //                            etEmail.requestFocus();
        //                            break;
        //
        //                        case "ERROR_CREDENTIAL_ALREADY_IN_USE":
        //                            Toast.makeText(MainActivity.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
        //                            break;
        //
        //                        case "ERROR_USER_DISABLED":
        //                            Toast.makeText(MainActivity.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
        //                            break;
        //
        //                        case "ERROR_USER_TOKEN_EXPIRED":
        //                            Toast.makeText(MainActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
        //                            break;
        //
        //                        case "ERROR_USER_NOT_FOUND":
        //                            Toast.makeText(MainActivity.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
        //                            break;
        //
        //                        case "ERROR_INVALID_USER_TOKEN":
        //                            Toast.makeText(MainActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
        //                            break;
        //
        //                        case "ERROR_OPERATION_NOT_ALLOWED":
        //                            Toast.makeText(MainActivity.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
        //                            break;
        //
        //                        case "ERROR_WEAK_PASSWORD":
        //                            Toast.makeText(MainActivity.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
        //                            etPassword.setError("The password is invalid it must 6 characters at least");
        //                            etPassword.requestFocus();
        //                            break;
    }
}
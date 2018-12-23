package com.barracudapff.hoobes.flatter.fragments.auth;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.barracudapff.hoobes.flatter.MainActivity;
import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.activities.auth.up.LogUpMailActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;
import java.util.concurrent.Executor;

public class AuthSignUpFragment extends AuthSignBaseFragment implements GoogleApiClient.OnConnectionFailedListener {
    private OnAuthSignUpFragmentInteractionListener mListener;

    private GoogleApiClient mGoogleApiClient;

    public AuthSignUpFragment() {
        super();
    }

    public static AuthSignUpFragment newInstance() {
        return new AuthSignUpFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setUp(context);
        if (context instanceof OnAuthSignUpFragmentInteractionListener) {
            mListener = (OnAuthSignUpFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(String.valueOf(GoogleSignInOptions.DEFAULT_SIGN_IN))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this::onConnectionFailed)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_sign_up, container, false);
        setUpCard(view.findViewById(R.id.cardViewGoogle), v -> {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, 9001);
        });
        setUpCard(view.findViewById(R.id.cardViewInstagram), v ->
                Toast.makeText(getActivity(), "TODO: not available now", Toast.LENGTH_SHORT).show());
        setUpCard(view.findViewById(R.id.cardViewVk), v ->
                Toast.makeText(getActivity(), "TODO: not available now", Toast.LENGTH_SHORT).show());
        setUpCard(view.findViewById(R.id.cardViewMail), v ->
                MainActivity.basicStart(getActivity(), LogUpMailActivity.class, 102));
        setUpCard(view.findViewById(R.id.sign_up_button), v ->
                mListener.OnAuthSignUpFragmentInteraction());

        addAnimationStart(view.findViewById(R.id.in_logo));

        addAnimationStep(view.findViewById(R.id.in_tag));
        addAnimationStep(view.findViewById(R.id.cardViewGoogle));
        addAnimationStep(view.findViewById(R.id.cardViewInstagram));
        addAnimationStep(view.findViewById(R.id.cardViewVk));
        addAnimationStep(view.findViewById(R.id.cardViewMail));
        addAnimationStep(view.findViewById(R.id.sign_up_button));

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        views = new ArrayList<>();
        animations = new ArrayList<>();
        delay = 100;
    }

    private void setUpCard(View view, View.OnClickListener listener) {
        view.setOnClickListener(listener);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        System.out.println("C A N C E L");
    }

    public interface OnAuthSignUpFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnAuthSignUpFragmentInteraction();
    }

}

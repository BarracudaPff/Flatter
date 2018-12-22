package com.barracudapff.hoobes.flatter.fragments.auth;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.barracudapff.hoobes.flatter.MainActivity;
import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.activities.auth.in.SignInMainActivity;
import com.barracudapff.hoobes.flatter.activities.auth.up.LogUpMailActivity;
import com.barracudapff.hoobes.flatter.support.StepAnimation;

import java.util.ArrayList;

public class AuthSignUpFragment extends AuthSignBaseFragment {
    private OnAuthSignUpFragmentInteractionListener mListener;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_sign_up, container, false);
        setUpCard(view.findViewById(R.id.cardViewGoogle), v ->
                Toast.makeText(getActivity(), "TODO: not available now", Toast.LENGTH_SHORT).show());
        setUpCard(view.findViewById(R.id.cardViewInstagram), v ->
                Toast.makeText(getActivity(), "TODO: not available now", Toast.LENGTH_SHORT).show());
        setUpCard(view.findViewById(R.id.cardViewVk), v ->
                Toast.makeText(getActivity(), "TODO: not available now", Toast.LENGTH_SHORT).show());
        setUpCard(view.findViewById(R.id.cardViewMail), v ->
                MainActivity.basicStart(getActivity(), LogUpMailActivity.class, 102));
        setUpCard(view.findViewById(R.id.sign_up_button), v ->
                mListener.OnAuthSignUpFragmentInteraction());

        System.out.println("Hey");
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.err.println("DEst");
        System.out.println("dasda");
        animations = new ArrayList<>();
        delay = 100;
    }

    private void setUpCard(View view, View.OnClickListener listener) {
        view.setOnClickListener(listener);
    }

    public interface OnAuthSignUpFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnAuthSignUpFragmentInteraction();
    }

}

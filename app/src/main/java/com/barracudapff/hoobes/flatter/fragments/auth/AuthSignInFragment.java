package com.barracudapff.hoobes.flatter.fragments.auth;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.barracudapff.hoobes.flatter.MainActivity;
import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.activities.auth.in.LogInMailActivity;
import com.barracudapff.hoobes.flatter.activities.auth.up.SignUpMainActivity;

public class AuthSignInFragment extends AuthSignBaseFragment {
    private OnAuthSignInFragmentInteractionListener mListener;

    public AuthSignInFragment() {
        // Required empty public constructor
    }

    public static AuthSignInFragment newInstance() {
        return new AuthSignInFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setUp(context);
        if (context instanceof OnAuthSignInFragmentInteractionListener) {
            mListener = (OnAuthSignInFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_sign_in, container, false);

        setUpCard(view.findViewById(R.id.cardViewGoogle), v ->
                Toast.makeText(getActivity(), "TODO: not available now", Toast.LENGTH_SHORT).show());
        setUpCard(view.findViewById(R.id.cardViewInstagram), v ->
                Toast.makeText(getActivity(), "TODO: not available now", Toast.LENGTH_SHORT).show());
        setUpCard(view.findViewById(R.id.cardViewVk), v ->
                Toast.makeText(getActivity(), "TODO: not available now", Toast.LENGTH_SHORT).show());
        setUpCard(view.findViewById(R.id.cardViewMail), v ->
                MainActivity.basicStart(getActivity(), LogInMailActivity.class, 102));
        setUpCard(view.findViewById(R.id.sign_up_button), v ->
                mListener.OnAuthSignInInteraction());

        addAnimationStart(view.findViewById(R.id.in_logo));

        addAnimationStep(view.findViewById(R.id.in_tag));
        addAnimationStep(view.findViewById(R.id.cardViewGoogle));
        addAnimationStep(view.findViewById(R.id.cardViewInstagram));
        addAnimationStep(view.findViewById(R.id.cardViewVk));
        addAnimationStep(view.findViewById(R.id.cardViewMail));
        addAnimationStep(view.findViewById(R.id.sign_up_button));

        return view;
    }

    private void setUpCard(View view, View.OnClickListener listener) {
        view.setOnClickListener(listener);
    }

    public interface OnAuthSignInFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnAuthSignInInteraction();
    }
}

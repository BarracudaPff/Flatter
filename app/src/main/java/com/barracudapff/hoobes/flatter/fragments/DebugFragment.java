package com.barracudapff.hoobes.flatter.fragments;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

public class DebugFragment extends Fragment {

    String debugTag = "DEBUG - ";
    Fragment rootFragment;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        System.out.println(debugTag + "onHiddenChanged");
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        System.out.println(debugTag + "onInfate");
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        System.out.println(debugTag + "onAttachFragment");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println(debugTag + "onAttach");
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        System.out.println(debugTag + "onCreateAnimation");
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        System.out.println(debugTag + "onCreateAnimator");
        return super.onCreateAnimator(transit, enter, nextAnim);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(debugTag + "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println(debugTag + "onCraeteView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println(debugTag + "onViewCreated");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println(debugTag + "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println(debugTag + "onStop");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.out.println(debugTag + "onLowMemory");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println(debugTag + "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println(debugTag + "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println(debugTag + "onDetach");
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println(debugTag + "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println(debugTag + "onResume");
    }
}

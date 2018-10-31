package com.barracudapff.hoobes.flatter.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;

public class DebugActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        System.out.println("onCreate Bundle savedInstanceState");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        System.out.println("onConfigurationChanged");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        System.out.println("onPostResume");
    }

    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();

        System.out.println("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        System.out.println("onDestroy");
    }

    @Override
    public void onSupportActionModeStarted(@NonNull ActionMode mode) {
        super.onSupportActionModeStarted(mode);

        System.out.println("onSupportActionModeStarted");
    }

    @Override
    public void onSupportActionModeFinished(@NonNull ActionMode mode) {
        super.onSupportActionModeFinished(mode);

        System.out.println("onSupportActionModeFinished");
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(@NonNull ActionMode.Callback callback) {
        System.out.println("onWindowStartingSupportActionMode");
        return super.onWindowStartingSupportActionMode(callback);

    }

    @Override
    public void onCreateSupportNavigateUpTaskStack(@NonNull TaskStackBuilder builder) {
        super.onCreateSupportNavigateUpTaskStack(builder);

        System.out.println("onCreateSupportNavigateUpTaskStack");
    }

    @Override
    public void onPrepareSupportNavigateUpTaskStack(@NonNull TaskStackBuilder builder) {
        super.onPrepareSupportNavigateUpTaskStack(builder);

        System.out.println("onPrepareSupportNavigateUpTaskStack");
    }

    @Override
    public boolean onSupportNavigateUp() {
        System.out.println("onSupportNavigateUp");
        return super.onSupportNavigateUp();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        System.out.println("onContentChanged");
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        System.out.println("onMenuOpened");
        return super.onMenuOpened(featureId, menu);

    }

    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        super.onPanelClosed(featureId, menu);

        System.out.println("onPanelClosed");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        System.out.println("onSaveInstanceState");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        System.out.println("onBackPressed");
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        System.out.println("onCreatePanelMenu");
        return super.onCreatePanelMenu(featureId, menu);

    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        System.out.println("onCreateView: View parent, String name, Context context, AttributeSet attrs");
        return super.onCreateView(parent, name, context, attrs);

    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        System.out.println("onCreateView: String name, Context context, AttributeSet attrs");
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        System.out.println("onLowMemory");
    }

    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("onPause");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        System.out.println("onNewIntent");
    }

    @Override
    public void onStateNotSaved() {
        super.onStateNotSaved();

        System.out.println("onStateNotSaved");
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("onResume");
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();

        System.out.println("onResumeFragments");
    }

    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        System.out.println("onPreparePanel");
        return super.onPreparePanel(featureId, view, menu);

    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        System.out.println("onRetainCustomNonConfigurationInstance");
        return super.onRetainCustomNonConfigurationInstance();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        System.out.println("onAttachFragment");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        System.out.println("onRequestPermissionsResult");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        System.out.println("onCreate: @Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        System.out.println("onRestoreInstanceState");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);

        System.out.println("onRestoreInstanceState");
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

        System.out.println("onPostCreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        System.out.println("onRestart");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        System.out.println("onSaveInstanceState");
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

        System.out.println("onUserLeaveHint");
    }

    @Nullable
    @Override
    public View onCreatePanelView(int featureId) {
        System.out.println("onCreatePanelView");
        return super.onCreatePanelView(featureId);

    }

    @Override
    public boolean onNavigateUp() {
        System.out.println("onNavigateUp");
        return super.onNavigateUp();
    }
}

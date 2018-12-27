package com.barracudapff.hoobes.flatter.activities.party;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.database.models.Party;
import com.barracudapff.hoobes.flatter.database.models.User;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class PartyActivity extends AppCompatActivity {

    private ImageView previewView;
    private TextView nameView;
    private TextView aboutView;
    private TextView dateView;
    private TextView timeView;
    private TextView membersView;
    private TextView authorView;
    private TextView placeView;
    private Button photosView;
    private Button joinView;

    private Party party;
    private User user;

    private int fl;
    private String request;
    private String requestUndo;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        party = Party.getFromIntent(getIntent());
        System.out.println(party);
        user = User.getCurrent(this);

        setContentView(R.layout.activity_party);

        init();
        bindOnView(party);

        joinView.setOnClickListener(v -> {
            System.out.println(fl);
            if (fl == -2) {
                showDeleteWarning();
                return;
            }
            doAction();
            Snackbar
                    .make(findViewById(R.id.party_layout), request, Snackbar.LENGTH_LONG)
                    .setAction("ОТМЕНА", view -> {
                        doUndoAction();
                        Snackbar snackbar1 = Snackbar.make(findViewById(R.id.party_layout), requestUndo, Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }).setActionTextColor(getResources().getColor(R.color.red)).show();
            System.out.println(request);
            System.out.println(requestUndo);
        });
    }

    private void showDeleteWarning() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setCancelable(true)
                .setTitle("Вы уверены что хотите удалить вечеринку")
                .setNegativeButton(R.string.cancel,
                        (dialog, which) -> {
                        })
                .setPositiveButton(R.string.delete,
                        (dialog, which) -> {
                            dialog.dismiss();
                            showProgressDialog();

                            FirebaseDatabase.getInstance().getReference()
                                    .child("parties")
                                    .child(party.uid)
                                    .removeValue();
                            FirebaseDatabase.getInstance().getReference()
                                    .child("images")
                                    .child("parties")
                                    .child(party.uid)
                                    .removeValue();
                            FirebaseDatabase.getInstance().getReference()
                                    .child("user-parties")
                                    .child(party.author_uid)
                                    .child(party.uid)
                                    .removeValue();

                            Intent intent = new Intent();
                            Party.putInIntent(intent, party);
                            setResult(RESULT_OK, intent);
                            hideProgressDialog();
                            finish();
                        })
                .create().show();
    }

    private void doAction() {
        switch (fl) {
            case -1:
                int index = party.members.indexOf(user.uid);
                FirebaseDatabase.getInstance().getReference()
                        .child("parties")
                        .child(party.uid)
                        .child(Party.MEMBERS)
                        .child(String.valueOf(index))
                        .removeValue();
                party.members.remove(index);
                break;
            case 0:
                FirebaseDatabase.getInstance().getReference()
                        .child("parties")
                        .child(party.uid)
                        .child(Party.MEMBERS)
                        .setValue(party.members.size(), user.uid);
                party.members.add(user.uid);
                break;
            case 1:
                break;
        }
    }

    private void doUndoAction() {
        switch (fl) {
            case -1:
                FirebaseDatabase.getInstance().getReference()
                        .child("parties")
                        .child(party.uid)
                        .child(Party.MEMBERS)
                        .setValue(party.members.size(), user.uid);
                party.members.add(user.uid);
                break;
            case 0:
                int index = party.members.indexOf(user.uid);
                FirebaseDatabase.getInstance().getReference()
                        .child("parties")
                        .child(party.uid)
                        .child(Party.MEMBERS)
                        .child(String.valueOf(index))
                        .removeValue();
                party.members.remove(index);
                break;
            case 1:
                break;
        }
    }

    private void init() {
        previewView = findViewById(R.id.party_preview);
        nameView = findViewById(R.id.party_name);
        aboutView = findViewById(R.id.party_about);
        dateView = findViewById(R.id.party_date);
        timeView = findViewById(R.id.party_time);
        membersView = findViewById(R.id.party_members);
        authorView = findViewById(R.id.party_author);
        placeView = findViewById(R.id.party_place);
        photosView = findViewById(R.id.party_photos);
        joinView = findViewById(R.id.party_join);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void bindOnView(Party party) {
        nameView.setText(party.name);
        aboutView.setText(party.about);
        dateView.setText(new SimpleDateFormat(
                "dd MMMM yyyy", getResources().getConfiguration().locale)
                .format(String.valueOf(party.dateTime)));
        timeView.setText(new SimpleDateFormat(
                "dd MMMM yyyy", getResources().getConfiguration().locale)
                .format(String.valueOf(party.dateTime)));
        String members;
        if (party.members.size() == 1)
            members = "На вечеринке пока только организатор";
        else
            members = "На вечеринке уже " + party.members.size() + " участников!";
        membersView.setText(members);
        authorView.setText(party.author);
        placeView.setText(party.address);

        if (party.author_uid.equals(user.uid)) {
            setRequest(-2);
        } else if (party.members.contains(user.uid)) {
            setRequest(-1);
        } else if (party.isFree) {
            setRequest(0);
        } else {
            setRequest(1);
        }

        if (!party.profile_image)
            previewView.setVisibility(View.GONE);
        else
            FirebaseStorage.getInstance().getReference()
                    //.child("images")
                    .child("parties")
                    .child(party.uid)
                    //.child(Party.PROFILE_IMAGE)
                    .child(Party.PROFILE_IMAGE)
                    .getDownloadUrl()
                    .addOnCompleteListener(task -> {
                        Picasso.get().load(task.getResult())
                                //.placeholder(R.color.lightGray)
                                .error(R.color.lightGray)
                                .into(previewView);
                    });
    }

    /**
     * @param fl -2 is delete, -1 is exit, 0 is join, 1 is request for join
     */
    private void setRequest(int fl) {
        if (fl < -2 || fl > 1)
            throw new IllegalArgumentException("fl must be -2, -1, 0, or 1");
        switch (fl) {
            case -2:
                this.fl = -2;
                request = "Удалить";
                requestUndo = "Отмена удаления";
                joinView.setText("Удалить");
                break;
            case -1:
                this.fl = -1;
                request = "Вы вышил из вечеринки";
                requestUndo = "Выход отменен";
                joinView.setText("Выйти");
                break;
            case 0:
                this.fl = 0;
                request = "Вы присоеденились к вечеринки";
                requestUndo = "Вы вышли из вечеринки";
                joinView.setText("Присоедениться");
                break;
            case 1:
                this.fl = 1;
                request = "Запрос отправлен";
                requestUndo = "Запрос отменен";
                joinView.setText("Запросить");
                break;

        }
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

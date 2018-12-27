package com.barracudapff.hoobes.flatter.activities.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.adapters.NewSearchFirebaseRecyclerAdapter;
import com.barracudapff.hoobes.flatter.database.models.User;
import com.barracudapff.hoobes.flatter.database.viewHolders.UserViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class NewChatActivity extends AppCompatActivity {

    private EditText mSearchField;
    private ImageButton mSearchBtn;

    private RecyclerView mRResultList;
    private DatabaseReference mUserDatabase;
    private FirebaseRecyclerAdapter<User, UserViewHolder> firebaseRecyclerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        //Toolbar settings
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Firebase setup
        mUserDatabase = FirebaseDatabase.getInstance().getReference();

        //View setup
        mSearchField = findViewById(R.id.search_field);
        mSearchBtn = findViewById(R.id.search_button);

        mRResultList = findViewById(R.id.result_list);
        mRResultList.setHasFixedSize(true);
        mRResultList.setLayoutManager(new LinearLayoutManager(this));

        mSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = mSearchField.getText().toString();

                firebaseUserSearch(searchText);
            }
        });

        mSearchBtn.setOnClickListener(view -> {
            String searchText = mSearchField.getText().toString();

            firebaseUserSearch(searchText);
        });
    }

    private void firebaseUserSearch(String searchText) {
        System.out.println("Search");
        Query postsQuery = mUserDatabase.child("users").orderByChild("email").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(postsQuery, User.class)
                .build();

        firebaseRecyclerAdapter = new NewSearchFirebaseRecyclerAdapter(options, this);
        mRResultList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode);
        if (resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.stopListening();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

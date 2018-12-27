package com.barracudapff.hoobes.flatter.activities.chat;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.adapters.NewChatFirebaseRecyclerAdapter;
import com.barracudapff.hoobes.flatter.database.models.LastMessage;
import com.barracudapff.hoobes.flatter.database.models.Message;
import com.barracudapff.hoobes.flatter.database.models.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;

    private DatabaseReference messagesHistory;
    private DatabaseReference lastMessages1;
    private DatabaseReference lastMessages2;
    private NewChatFirebaseRecyclerAdapter firebaseRecyclerAdapter;

    private static User user;
    private String userUID;
    private static String otherUID;

    public static void setUser(User user) {
        ChatActivity.user = user;
    }

    public static void setOtherUID(String otherUID) {
        ChatActivity.otherUID = otherUID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(user.first_name+" "+user.second_name);
        setContentView(R.layout.activity_chat);
        userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Toolbar settings
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sendButton = (ImageView) findViewById(R.id.send_button);
        messageArea = (EditText) findViewById(R.id.message_area);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        lastMessages1 = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("talk")
                .child("last_messages")
                .child(userUID)
                .child(otherUID);

        lastMessages2 = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("talk")
                .child("last_messages")
                .child(otherUID)
                .child(userUID);

        int compare = userUID.compareTo(otherUID);
        if (compare < 0) {
            System.out.println("<0!");
            messagesHistory = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child("talk")
                    .child("messages")
                    .child(userUID + ":" + otherUID);
        } else {
            System.out.println(">0!");
            messagesHistory = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child("talk")
                    .child("messages")
                    .child(otherUID + ":" + userUID);
        }

        sendButton.setOnClickListener(v -> {
            String messageText = messageArea.getText().toString();

            System.out.println(messagesHistory.getParent().getKey());

            if (!messageText.equals("")) {
                Message message = new Message(userUID, otherUID, messageText);
                LastMessage lastMessage = new LastMessage(messageText);
                DatabaseReference reference = messagesHistory;
                reference
                        .child(String.valueOf(new Date().getTime()))
                        .setValue(message);
                messageArea.setText("");
                lastMessages1
                        .setValue(lastMessage);
                lastMessages2
                        .setValue(lastMessage);
            }
        });

        Query postsQuery = messagesHistory;

        FirebaseRecyclerOptions<Message> options = new FirebaseRecyclerOptions.Builder<Message>()
                .setQuery(postsQuery, Message.class)
                .build();

        RecyclerView mMessageRecycler = findViewById(R.id.reyclerview_message_list);
        LinearLayoutManager mManager = new LinearLayoutManager(this);
        //mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mMessageRecycler.setLayoutManager(mManager);

        firebaseRecyclerAdapter = new NewChatFirebaseRecyclerAdapter(options, this, otherUID, user.email);
        mMessageRecycler.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

        /*messagesHistory.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message map = dataSnapshot.getValue(Message.class);
                String message = map.message;

                if (map.sender_uid.equals(userUID)) {
                    addMessageBox("You:-\n" + message, 2);
                } else {
                    addMessageBox(user.first_name + ":-\n" + message, 1);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }

    public void addMessageBox(String message, int type) {
        TextView textView = new TextView(this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if (type == 1) {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.search_outline);
        } else {
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.search_outline);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
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
}

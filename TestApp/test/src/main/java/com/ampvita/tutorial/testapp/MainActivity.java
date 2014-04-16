package com.ampvita.tutorial.testapp;

import android.app.Activity;
import android.app.ListActivity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.view.View;
import android.widget.ListView;

import com.firebase.client.Firebase;

public class MainActivity extends ListActivity {

    private static final String FIREBASE_URL = "https://hacktorial.firebaseio.com/";

    private String username;
    private Firebase fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fb = new Firebase(FIREBASE_URL).child("conversation");

        EditText inputText = (EditText)findViewById(R.id.messageInput);
        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupChat();
    }

    @Override
    protected void onStart() {
        super.onStart();
        final ListView listView = getListView();

        chatListAdapter = new ChatListAdapter(fb.limit(50), this, R.layout.chat_message, username);
        chatListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatListAdapter.getCount() - 1);
            }
        });
    }

    private void setupChat() {

        setTitle("Hey " + username + "!");

    }

    private void sendMessage() {
        EditText inputText = (EditText)findViewById(R.id.messageInput);
        String input = inputText.getText().toString();
        if (!input.equals("")) {
            // Create our 'model', a Chat object
            Chat chat = new Chat(input, username);
            // Create a new, auto-generated child of that chat location, and save our chat data there
            fb.push().setValue(chat);
            inputText.setText("");
        }
    }
}

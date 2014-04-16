package com.ampvita.tutorial.testapp;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
/**
 * Created by dan on 4/16/14.
 */
public class ChatListAdapter extends FirebaseListAdapter<Chat> {
    private String username;

    public ChatListAdapter(Query ref, Activity activity, int layout, String username) {
        super(ref, Chat.class, layout, activity);
        this.username = username;
    }

    @Override
    protected void populateView(View view, Chat chat) {
        Log.e("ListAdapter", "viewPopulated");
        // Map a Chat object to an entry in our listview
        String author = chat.getAuthor();
        TextView authorText = (TextView)view.findViewById(R.id.author);
        authorText.setText(author + ": ");
        // If the message was sent by this user, color it differently
        if (author.equals(username)) {
            authorText.setTextColor(Color.RED);
        } else {
            authorText.setTextColor(Color.BLUE);
        }
        ((TextView)view.findViewById(R.id.message)).setText(chat.getMessage());
    }
}

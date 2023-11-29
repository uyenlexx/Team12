package com.example.team12.components.listener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface OnGetDataListener {
    public void onSuccess(DataSnapshot dataSnapshot);
    public void onStart();
    public void onFailure(DatabaseError databaseError);
}

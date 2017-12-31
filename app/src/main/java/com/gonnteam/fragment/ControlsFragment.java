package com.gonnteam.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.gonnteam.smartlamp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by MrThien on 2017-12-17.
 */

public class ControlsFragment extends Fragment {

    private DatabaseReference mDatabase;
    Switch swLivingroom,swKitchen;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.controls_fragment, container, false);
        swLivingroom=(Switch)rootView.findViewById(R.id.swLivingroom);
        swKitchen=(Switch)rootView.findViewById(R.id.swKitchen);
        //Firebase
        mDatabase=FirebaseDatabase.getInstance().getReference();
        mDatabase.child("thienromtn55/LivingRoom").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()=="1"){
                    swLivingroom.setChecked(true);
                }
                else if(dataSnapshot.getValue()=="0"){
                    swLivingroom.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDatabase.child("thienromtn55/Kitchen").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()=="1"){
                    swKitchen.setChecked(true);
                }
                else if(dataSnapshot.getValue()=="0"){
                    swKitchen.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        swLivingroom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    //turn off
                    mDatabase.child("thienromtn55/LivingRoom").setValue(1);
                }
                else {
                    //turn on
                    mDatabase.child("thienromtn55/LivingRoom").setValue(0);

                }
            }
        });

        swKitchen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    //turn off
                    mDatabase.child("thienromtn55/Kitchen").setValue(1);
                }
                else {
                    //turn on
                    mDatabase.child("thienromtn55/Kitchen").setValue(0);

                }
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Bảng điều khiển");
    }
}

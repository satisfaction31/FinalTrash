package com.example.taquio.trasearch.Samok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.taquio.trasearch.R;

import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity {

    private static final String Tag = "UserProfileActivity";
    private static final int NUM_COLUMNS = 2;

    private ArrayList<String> mItemImage = new ArrayList<>();
    private ArrayList<String> mItemName = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        initItemValues();
        initRecyclerView();
    }

    private void initItemValues(){
        //place images and names here;

//        item 0
//        mItemImage.add();
//        mItemName.add();
//
//        item 1
//        mItemImage.add();
//        mItemName.add();
//
//        item 2
//        mItemImage.add();
//        mItemName.add();
    }

    private void initRecyclerView(){


//        StaggeredRecViewAdapter staggeredRecyclerViewAdapter =
//                new StaggeredRecViewAdapter(this, mItemName, mItemImage);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayout.VERTICAL);
//        recyclerView.setLayoutManager(staggeredGridLayoutManager);
//        recyclerView.setAdapter(staggeredRecyclerViewAdapter);
    }
}

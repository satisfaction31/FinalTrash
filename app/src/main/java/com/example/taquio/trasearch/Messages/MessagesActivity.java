package com.example.taquio.trasearch.Messages;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.taquio.trasearch.Samok.FriendsFragment;
import com.example.taquio.trasearch.R;
import com.example.taquio.trasearch.Samok.SectionsPagerAdapter;
import com.example.taquio.trasearch.Utils.BottomNavigationViewHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by Del Mar on 2/7/2018.
 */

public class MessagesActivity extends AppCompatActivity {
    private static final String TAG = "MessagesActivity";
    private static final int ACTIVITY_NUM = 1;
    private Context mContext = MessagesActivity.this;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        setupBottomNavigationView();
        setupViewPager();

    }

    private void setupViewPager() {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new InboxFragment(), "Inbox");
        adapter.addFragment(new FriendsFragment(), "Friends List");
        ViewPager viewPager = findViewById(R.id.messageContainer);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.messageTabLayout);
        tabLayout.setupWithViewPager(viewPager);

//        tabLayout.getTabAt(0).setText("Inbox");
//        tabLayout.getTabAt(1).setText("Friends List");
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());

        if(mAuth.getCurrentUser()!=null)
        {
            mDatabase.child("online").setValue("online");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        if(mAuth.getCurrentUser()!=null)
        {
            mDatabase.child("online").setValue(ServerValue.TIMESTAMP);
        }
    }
}

package com.example.taquio.trasearch.Samok;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taquio.trasearch.Models.User;
import com.example.taquio.trasearch.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {
    private RecyclerView mFriendList;
    private FirebaseAuth mAuth;
    private DatabaseReference mFriendsDatabase
            ,mUsersDatabase;
    private static final String TAG = "FriendsFragment";

    private String mCurrent_user_id;

    private View mMainView;




    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mMainView = inflater
                .inflate(R.layout.fragment_friendslist,container,false);
        mFriendList = mMainView
                .findViewById(R.id.friendsList);
        mAuth = FirebaseAuth
                .getInstance();

        mCurrent_user_id = mAuth.
                getCurrentUser()
                .getUid();
        mFriendsDatabase = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Friends")
                .child(mCurrent_user_id);
        mUsersDatabase = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Users");
        mFriendsDatabase.keepSynced(true);
        mUsersDatabase.keepSynced(true);

        mFriendList
                .setHasFixedSize(true);
        mFriendList
                .setLayoutManager(new LinearLayoutManager(getContext()));

        return mMainView;
    }


    @Override
    public void onStart() {
        super.onStart();

        final FirebaseRecyclerAdapter<Friends,FriendsViewHolder> friendRecyclerAdapter = new FirebaseRecyclerAdapter<Friends, FriendsViewHolder>(
                Friends.class,
                R.layout.friend,
                FriendsViewHolder.class,
                mFriendsDatabase
        ) {
            @Override
            protected void populateViewHolder(final FriendsViewHolder viewHolder, Friends model, int position) {
//                viewHolder.setDate(model.getDate());

                final String list_user_Id = getRef(position).getKey();
                mUsersDatabase.child(list_user_Id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String name = dataSnapshot.child("Name").getValue().toString();
                        if(dataSnapshot.hasChild("Image_thumb"))
                        {
                            String user_thumb_img = dataSnapshot.child("Image_thumb").getValue().toString();
                            viewHolder.setProfileImage(user_thumb_img,getContext());
                        }

                        if(dataSnapshot.hasChild("online"))
                        {
                            String user_online = dataSnapshot.child("online").getValue().toString();
                            if(user_online.equals("online"))
                            {
                                viewHolder.setuserOnline(true);
                            }
                            else
                            {
                                viewHolder.setuserOnline(false);

                            }
                        }

                        viewHolder.setStatus(dataSnapshot.child("Email").getValue().toString());
                        viewHolder.setName(name);
                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]{"Open Profile","Send Message"};

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle(name+"\nSelect Options");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if(which==0)
                                        {
                                            Log.d(TAG, "onClick: Open Profile");
                                            Query userQuery = mUsersDatabase.orderByChild("userID").equalTo(list_user_Id);
                                            userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                                                        final User user = singleSnapshot.getValue(User.class);
                                                        Log.d(TAG, "onDataChange: "+user);
                                                        startActivity(new Intent(getContext(), MyProfileActivity.class)
                                                                .putExtra("intent_user",user));
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                        if(which==1)
                                        {
                                            startActivity(new Intent(getContext(), MessageActivity.class)
                                                    .putExtra("user_id",list_user_Id)
                                                    .putExtra("user_Name",name));

                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };
        mFriendList.setAdapter(friendRecyclerAdapter);
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder
    {
        View mView;


        public FriendsViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setStatus(String Status)
            {
                TextView userNameView  = mView.findViewById(R.id.allUsersEmail);
                userNameView.setText(Status);
            }
        public void setName (String Name)
        {
            TextView nameView = mView.findViewById(R.id.allUsersName);
            nameView.setText(Name);
        }
        public void setProfileImage (String ImageURL, Context cTHis)
        {
            CircleImageView mImageHolder = mView.findViewById(R.id.allUsersImg);
            Picasso.with(cTHis).load(ImageURL)
                    .into(mImageHolder);
        }
        public void setuserOnline (Boolean online_status)
        {
            CircleImageView userOnlineView = mView.findViewById(R.id.stat_icon);

            if(online_status)
            {
                userOnlineView.setVisibility(View.VISIBLE);
            }
            else
            {
                userOnlineView.setVisibility(View.INVISIBLE);

            }
        }
    }
}

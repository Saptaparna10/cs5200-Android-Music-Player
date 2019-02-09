package project.random.fall2018.cs5200.com.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;

import project.random.fall2018.cs5200.com.myapplication.LandingActivity;
import project.random.fall2018.cs5200.com.myapplication.R;
import project.random.fall2018.cs5200.com.myapplication.Singleton.UserSingleton;
import project.random.fall2018.cs5200.com.myapplication.adapters.FollowerListAdapter;
import project.random.fall2018.cs5200.com.myapplication.entities.RegisteredUser;

public class FollowersFragment extends Fragment{


    private RecyclerView mFollowerListView;
    private FollowerListAdapter mPlaylistAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<RegisteredUser> mPlaylists;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers,
                container, false);
        mFollowerListView = (RecyclerView) view.findViewById(R.id.follower_list);
        mFollowerListView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        mFollowerListView.setLayoutManager(layoutManager);
        ((LandingActivity) getActivity()).fetchArtistFollowers(UserSingleton.getInstance().getArtist().getId());
        return view;
    }


    public void inflateFollowerRegsiteredUsers(List<RegisteredUser> playlists)
    {
        mPlaylists = playlists;
        mPlaylistAdapter = new FollowerListAdapter(playlists);
        mFollowerListView.setAdapter(mPlaylistAdapter);
    }

}

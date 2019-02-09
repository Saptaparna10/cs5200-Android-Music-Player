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

import java.util.List;

import project.random.fall2018.cs5200.com.myapplication.LandingActivity;
import project.random.fall2018.cs5200.com.myapplication.R;
import project.random.fall2018.cs5200.com.myapplication.Singleton.UserSingleton;
import project.random.fall2018.cs5200.com.myapplication.adapters.FollowListAdapter;
import project.random.fall2018.cs5200.com.myapplication.adapters.FollowingListAdapter;
import project.random.fall2018.cs5200.com.myapplication.entities.Artist;

public class FollowFragment extends Fragment{

    private RecyclerView mFollowingListView;
    private RecyclerView mFollowListView;
    private FollowingListAdapter mFollowingListAdapter;
    private FollowListAdapter mFollowListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Artist> artists;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow_artist,
                container, false);
        mFollowingListView = (RecyclerView) view.findViewById(R.id.following_artist_view);
        mFollowListView = (RecyclerView) view.findViewById(R.id.follow_artist_view);
        mFollowingListView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        mFollowingListView.setLayoutManager(layoutManager);

        mFollowListView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        mFollowListView.setLayoutManager(layoutManager);
        inflateFollowingArtists(UserSingleton.getInstance().getRegisteredUser().getFollowing());

        ((LandingActivity) getActivity()).fetchArtists();
        ((LandingActivity) getActivity()).fetchFollowingArtists(UserSingleton.getInstance().getRegisteredUser().getId());

        return view;
    }


    public void inflateAllArtists(List<Artist> artists)
    {
        mFollowListAdapter = new FollowListAdapter(artists,getActivity());
        mFollowListView.setAdapter(mFollowListAdapter);
    }


    public void inflateFollowingArtists(List<Artist> artists)
    {
        mFollowingListAdapter = new FollowingListAdapter(artists);
        mFollowingListView.setAdapter(mFollowingListAdapter);
    }
}

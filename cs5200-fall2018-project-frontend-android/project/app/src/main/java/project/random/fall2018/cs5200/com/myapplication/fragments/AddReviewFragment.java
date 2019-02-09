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

import project.random.fall2018.cs5200.com.myapplication.LandingActivity;
import project.random.fall2018.cs5200.com.myapplication.R;
import project.random.fall2018.cs5200.com.myapplication.adapters.ReviewSongListAdapter;
import project.random.fall2018.cs5200.com.myapplication.vo.SongListValueObject;

public class AddReviewFragment extends Fragment {

    ReviewSongListAdapter mSongListAdapter;
    private RecyclerView mSongListView;
    private RecyclerView.LayoutManager layoutManager;
    SongListValueObject songList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,
                container, false);
        mSongListView = (RecyclerView) view.findViewById(R.id.recview_songs_list) ;
        mSongListView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        mSongListView.setLayoutManager(layoutManager);
        ((LandingActivity)getActivity()).sendLatestTrackRequestForReview();
        return view;
    }


    public void inflateSongListView(SongListValueObject songList){
        mSongListAdapter = new ReviewSongListAdapter(songList.getTracks().getTrack(),getActivity());
        mSongListView.setAdapter(mSongListAdapter);
    }
}

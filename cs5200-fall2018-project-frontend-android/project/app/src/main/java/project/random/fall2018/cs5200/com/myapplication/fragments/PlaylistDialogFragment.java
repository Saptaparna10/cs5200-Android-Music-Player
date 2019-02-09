package project.random.fall2018.cs5200.com.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import project.random.fall2018.cs5200.com.myapplication.LandingActivity;
import project.random.fall2018.cs5200.com.myapplication.R;
import project.random.fall2018.cs5200.com.myapplication.Singleton.PlaylistSingleton;
import project.random.fall2018.cs5200.com.myapplication.adapters.PlayListAdapter;
import project.random.fall2018.cs5200.com.myapplication.entities.Artist;
import project.random.fall2018.cs5200.com.myapplication.entities.Song;
import project.random.fall2018.cs5200.com.myapplication.listeners.PlaylistItemClickListener;
import project.random.fall2018.cs5200.com.myapplication.vo.Track;

public class PlaylistDialogFragment extends DialogFragment implements PlaylistItemClickListener {


    private RecyclerView mPlaylistView;
    private PlayListAdapter mPlaylistAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Track mTrack;

    public PlaylistDialogFragment(){
    }

    public Track getmTrack() {
        return mTrack;
    }

    public void setmTrack(Track mTrack) {
        this.mTrack = mTrack;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_playlist, container);

        mPlaylistView = (RecyclerView) view.findViewById(R.id.playlist_view);
        mPlaylistView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        mPlaylistView.setLayoutManager(layoutManager);

        mPlaylistAdapter = new PlayListAdapter(PlaylistSingleton.getInstance().getmPlaylists(),this);
        mPlaylistView.setAdapter(mPlaylistAdapter);
        return view;
    }


    @Override
    public void onItemClick(View v, int position) {
        PlaylistSingleton.getInstance().setmSelecetedPlayList(position);
        Song song = new Song();
        song.setName(mTrack.getName());
        String playCount = mTrack.getPlaycount();
        if(playCount!=null) song.setPlayCount(Long.parseLong(mTrack.getPlaycount()));
        song.setImageUrl(mTrack.getImage().get(3).getText());
        song.setStreamUrl(mTrack.getUrl());

        Artist artist = new Artist();
        artist.setName(mTrack.getArtist().getName());
        song.getArtists().add(artist);

        try {
            ((LandingActivity) getActivity()).createSong(song);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dismiss();
    }
}

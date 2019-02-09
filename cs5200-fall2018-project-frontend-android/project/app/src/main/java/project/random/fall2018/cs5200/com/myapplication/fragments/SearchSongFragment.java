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
import android.widget.RadioGroup;
import android.widget.Toast;

import project.random.fall2018.cs5200.com.myapplication.LandingActivity;
import project.random.fall2018.cs5200.com.myapplication.R;
import project.random.fall2018.cs5200.com.myapplication.adapters.SearchArtistAdapter;
import project.random.fall2018.cs5200.com.myapplication.adapters.SearchSongAdapter;
import project.random.fall2018.cs5200.com.myapplication.vo.songs.Example;

public class SearchSongFragment extends Fragment {

    private EditText mSearchText;
    private ImageButton mSearchButton;
    private RadioGroup mSearchGroup;

    SearchSongAdapter mSongListAdapter;
    private RecyclerView mSongListView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_song,
                container, false);
        mSearchButton = (ImageButton) view.findViewById(R.id.btn_serch_songs);
        mSearchText = (EditText) view.findViewById(R.id.search_text);
        mSearchGroup = (RadioGroup) view.findViewById(R.id.radio_search);
        mSearchGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.radio_search_artist:
                        ((LandingActivity)getActivity()).searchArtists(mSearchText.getText().toString());
                        Toast.makeText(getActivity(), "Artist", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_search_song:
                        ((LandingActivity)getActivity()).searchSongs(mSearchText.getText().toString());
                        Toast.makeText(getActivity(), "Songs", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LandingActivity)getActivity()).searchSongs(mSearchText.getText().toString());
            }
        });
        mSongListView = (RecyclerView) view.findViewById(R.id.search_songs_list) ;
        mSongListView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        mSongListView.setLayoutManager(layoutManager);

        return view;
    }

    public void inflateSongListView(Example query){
        mSongListAdapter = new SearchSongAdapter(query.getResults().getTrackmatches().getTrack(),getActivity());
        mSongListView.setAdapter(mSongListAdapter);

    }

    public void inflateArtists(project.random.fall2018.cs5200.com.myapplication.vo.artists.Example query)
    {
        SearchArtistAdapter mSongListAdapter = new SearchArtistAdapter(query.getResults().getArtistmatches().getArtist());
        mSongListView.setAdapter(mSongListAdapter);
    }
}

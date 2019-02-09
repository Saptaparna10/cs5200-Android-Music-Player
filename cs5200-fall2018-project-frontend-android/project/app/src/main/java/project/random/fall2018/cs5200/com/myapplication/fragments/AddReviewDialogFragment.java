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
import android.widget.Button;
import android.widget.EditText;

import project.random.fall2018.cs5200.com.myapplication.LandingActivity;
import project.random.fall2018.cs5200.com.myapplication.R;
import project.random.fall2018.cs5200.com.myapplication.entities.Artist;
import project.random.fall2018.cs5200.com.myapplication.entities.Song;
import project.random.fall2018.cs5200.com.myapplication.vo.Track;

public class AddReviewDialogFragment extends DialogFragment{


   private EditText mAddReviewText;
   private Button mSubmitReview;


    private Track mTrack;

    public Track getmTrack() {
        return mTrack;
    }

    public void setmTrack(Track mTrack) {
        this.mTrack = mTrack;
    }

    public AddReviewDialogFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_add_review, container);
        mAddReviewText = (EditText) view.findViewById(R.id.edtv_add_review);
        mSubmitReview = (Button) view.findViewById(R.id.btn_submit_review);

        mSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Song song = new Song();
                song.setName(mTrack.getName());
                song.setPlayCount(mTrack.getPlaycount()!=null ? Long.parseLong(mTrack.getPlaycount()) : null);
                song.setImageUrl(mTrack.getImage().get(3).getText());
                song.setStreamUrl(mTrack.getUrl());

                Artist artist = new Artist();
                artist.setName(mTrack.getArtist().getName());
                song.getArtists().add(artist);

                try {
                    ((LandingActivity) getActivity()).createSongForReview(song,mAddReviewText.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            dismissFragment();
            }
        });
        return view;
    }


    public void dismissFragment(){
        this.dismiss();
    }
}

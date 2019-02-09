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
import project.random.fall2018.cs5200.com.myapplication.adapters.ReviewListaAdapter;
import project.random.fall2018.cs5200.com.myapplication.entities.Review;
import project.random.fall2018.cs5200.com.myapplication.entities.Song;

public class SeeReviewsFragment extends Fragment {

    private RecyclerView mReviewList;
    private RecyclerView.LayoutManager layoutManager;
    private ReviewListaAdapter mReviewListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,
                container, false);
        mReviewList = (RecyclerView) view.findViewById(R.id.recview_songs_list);
        mReviewList.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        mReviewList.setLayoutManager(layoutManager);
        ((LandingActivity) getActivity()).getAllReviews();

        return view;
    }


    public void inflateReviews(List<Review> reviews){
        mReviewListAdapter = new ReviewListaAdapter(reviews,getActivity());
        mReviewList.setAdapter(mReviewListAdapter);
    }


     public void setSongDetails(ReviewListaAdapter.ViewHolder holder, int position, Song song)
    {
        mReviewListAdapter.setSongDetails(holder,position,song);
    }

}

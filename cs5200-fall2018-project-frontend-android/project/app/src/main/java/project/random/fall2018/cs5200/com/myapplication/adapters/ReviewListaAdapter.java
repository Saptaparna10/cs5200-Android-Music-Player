package project.random.fall2018.cs5200.com.myapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import project.random.fall2018.cs5200.com.myapplication.LandingActivity;
import project.random.fall2018.cs5200.com.myapplication.R;
import project.random.fall2018.cs5200.com.myapplication.entities.Review;
import project.random.fall2018.cs5200.com.myapplication.entities.Song;
import project.random.fall2018.cs5200.com.myapplication.utilities.VolleySingleton;

public class ReviewListaAdapter extends RecyclerView.Adapter<ReviewListaAdapter.ViewHolder> {

    private List<Review> values;
    private ImageLoader mImageLoader;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtSongName;
        public TextView txtCriticName;
        public TextView mReview;
        private NetworkImageView songThumbnail;
        public View layout;


        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtSongName = (TextView) v.findViewById(R.id.song_name);
            mReview = (TextView) v.findViewById(R.id.review_text);
            txtCriticName = (TextView) v.findViewById(R.id.critic_name);
            songThumbnail = (NetworkImageView) v.findViewById(R.id.song_image_thumbnail);

        }
    }

    public void add(int position, Review item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ReviewListaAdapter(List<Review> myDataset, Context context) {
        values = myDataset;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.review_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Review review = values.get(position);
        holder.mReview.setText(review.getContent());
        holder.txtCriticName.setText(review.getCritic().getFirstName()+" "+review.getCritic().getLastName());
        ((LandingActivity) context).findSongForReview(holder,position,review.getId());
    }

    public void setSongDetails(ViewHolder holder, int position, Song song)
    {
        holder.txtSongName.setText(song.getName());
        mImageLoader = VolleySingleton.getInstance().getImageLoader();
        holder.songThumbnail.setImageUrl(song.getImageUrl(),mImageLoader);

    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}

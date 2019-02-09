package project.random.fall2018.cs5200.com.myapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import project.random.fall2018.cs5200.com.myapplication.LandingActivity;
import project.random.fall2018.cs5200.com.myapplication.R;
import project.random.fall2018.cs5200.com.myapplication.entities.Artist;

public class FollowListAdapter extends RecyclerView.Adapter<FollowListAdapter.ViewHolder> {

    private List<Artist> values;
    private Context context;


    public class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public TextView txtArtistName;
        public ImageButton btnFollowArtist;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtArtistName = (TextView) v.findViewById(R.id.artist_name);
            btnFollowArtist = (ImageButton) v.findViewById(R.id.bt_follow_artist);

        }

    }

    public void add(int position, Artist item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FollowListAdapter(List<Artist> myDataset,Context context) {
        values = myDataset;
        this.context=context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.follow_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Artist track = values.get(position);
        holder.txtArtistName.setText(track.getName());
        holder.btnFollowArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LandingActivity)context).followArtist(track.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}

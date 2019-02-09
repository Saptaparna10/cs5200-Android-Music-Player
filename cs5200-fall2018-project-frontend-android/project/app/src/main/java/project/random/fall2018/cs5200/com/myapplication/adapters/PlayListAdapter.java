package project.random.fall2018.cs5200.com.myapplication.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import project.random.fall2018.cs5200.com.myapplication.R;
import project.random.fall2018.cs5200.com.myapplication.entities.Playlist;
import project.random.fall2018.cs5200.com.myapplication.listeners.PlaylistItemClickListener;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.ViewHolder> {

    private List<Playlist> values;
    private PlaylistItemClickListener mListener;


    public class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public TextView txtPlaylistName;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtPlaylistName = (TextView) v.findViewById(R.id.txt_playlist_name);

        }

    }

    public void add(int position, Playlist item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PlayListAdapter(List<Playlist> myDataset, PlaylistItemClickListener listener) {
        values = myDataset;
        mListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.playlist_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Playlist track = values.get(position);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(view, position);
            }
        });
        holder.txtPlaylistName.setText(track.getName());
    }

    @Override
    public int getItemCount() {
        return values==null? 0 : values.size();
    }
}

package project.random.fall2018.cs5200.com.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.random.fall2018.cs5200.com.myapplication.Singleton.*;
import project.random.fall2018.cs5200.com.myapplication.adapters.*;
import project.random.fall2018.cs5200.com.myapplication.entities.*;
import project.random.fall2018.cs5200.com.myapplication.fragments.*;
import project.random.fall2018.cs5200.com.myapplication.utilities.VolleySingleton;
import project.random.fall2018.cs5200.com.myapplication.vo.Artist;
import project.random.fall2018.cs5200.com.myapplication.vo.SongListValueObject;
import project.random.fall2018.cs5200.com.myapplication.vo.songs.Example;
import project.random.fall2018.cs5200.com.myapplication.vo.SongListValueObject;

public class LandingActivity extends AppCompatActivity {
    private NavigationView mNavigationView;
    private TextView mDrawerHeader;
    private Fragment currFragment;
    private static final String TAG = LandingActivity.class.getName();
    private Button btnRequest;
    ObjectMapper mapper = new ObjectMapper();
    SongListValueObject songList;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private DrawerLayout mDrawerLayout;
    private String SONG_LIST_URL = "http://ws.audioscrobbler.com/2.0/?method=tag.gettoptracks&api_key=5e49bf9862ed781addd20ec0c09ed577&format=json";
    private Example searchQuery;
    private project.random.fall2018.cs5200.com.myapplication.vo.artists.Example artistSearchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        Toast.makeText(this, UserSingleton.getInstance().getType(),Toast.LENGTH_SHORT).show();
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerHeader = (TextView) navigationView.getHeaderView(0).findViewById(R.id.drawer_header);
        mDrawerHeader.setText(UserSingleton.getInstance().getUser().getFirstName() + " "+ UserSingleton.getInstance().getUser().getLastName());
        manipulateDrawer();
        //mDrawerHeader = (TextView) navigationView.getHeaderView().findViewById(R.id.drawer_header);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        switch (menuItem.getItemId()) {

                            case android.R.id.home:
                                mDrawerLayout.openDrawer(GravityCompat.START);
                                return true;
                            case R.id.Home:
                                currFragment = new HomeFragment();
                                switchFragments(currFragment);
                                return true;
                            case R.id.search_song:
                                currFragment = new SearchSongFragment();
                                switchFragments(currFragment);
                                return true;
                            case R.id.playlist:
                                currFragment = new PlaylistFragment();
                                switchFragments(currFragment);
                                return true;
                            case R.id.followers:
                                currFragment = new FollowersFragment();
                                switchFragments(currFragment);
                                return true;
                            case R.id.following:
                                currFragment = new FollowFragment();
                                switchFragments(currFragment);
                                return true;
                            case R.id.add_reviews:
                                currFragment = new AddReviewFragment();
                                switchFragments(currFragment);
                                return true;
                            case R.id.see_reviews:
                                currFragment = new SeeReviewsFragment();
                                switchFragments(currFragment);
                                return true;
                        }
                    return true;
                    }
                });
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("AudioPlayer");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        currFragment = new HomeFragment();
        switchFragments(currFragment);

    }


    public void switchFragments(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendLatestTrackRequestForReview(){

        //RequestQueue initialized
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, SONG_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    songList = mapper.readValue(response.toString(), SongListValueObject.class);
                    ((AddReviewFragment) currFragment).inflateSongListView(songList);

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }


    public void sendLatestTrackRequest(){

        //RequestQueue initialized
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, SONG_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    songList = mapper.readValue(response.toString(), SongListValueObject.class);
                    ((HomeFragment) currFragment).inflateSongListView(songList);

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
               // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }

    public void searchSongs(String trackName)
    {
        String URL  = "http://ws.audioscrobbler.com/2.0/?method=track.search&track="+trackName +"&api_key=5e49bf9862ed781addd20ec0c09ed577&format=json";
        //RequestQueue initialized
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    searchQuery = mapper.readValue(response.toString(), Example.class);

                    ((SearchSongFragment) currFragment).inflateSongListView(searchQuery);

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }

    public void searchArtists(String artistName)
    {
        String URL  = "http://ws.audioscrobbler.com/2.0/?method=tag.gettoptracks&api_key=5e49bf9862ed781addd20ec0c09ed577&format=json";
        //RequestQueue initialized
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    artistSearchQuery = mapper.readValue(response.toString(), project.random.fall2018.cs5200.com.myapplication.vo.artists.Example.class);

                    ((SearchSongFragment) currFragment).inflateArtists(artistSearchQuery);

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }


    public void findSongForReview(final ReviewListaAdapter.ViewHolder holder,final int position,int reviewId)
    {
        String URL  = "http://cs5200fall2018projectrandom.us-east-2.elasticbeanstalk.com/api/review/"+reviewId+"/song";
        //RequestQueue initialized
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                  Song song = mapper.readValue(response.toString(), Song.class);

                    ((SeeReviewsFragment) currFragment).setSongDetails(holder,position,song);

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }


    public void getAllReviews()
    {
        String URL  = "http://cs5200fall2018projectrandom.us-east-2.elasticbeanstalk.com/api/review";
        //RequestQueue initialized
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    List<Review> reviews = mapper.readValue(response.toString(), new TypeReference<List<Review>>(){});

                    ((SeeReviewsFragment) currFragment).inflateReviews(reviews);

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }

    public void fetchFollowingArtists(int userId)
    {
        String URL  = "http://cs5200fall2018projectrandom.us-east-2.elasticbeanstalk.com/api/registereduser/"+userId+"/following";
        //RequestQueue initialized
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    List<project.random.fall2018.cs5200.com.myapplication.entities.Artist> artists = mapper.readValue(response.toString(), new TypeReference<List<project.random.fall2018.cs5200.com.myapplication.entities.Artist>>(){});

                    ((FollowFragment) currFragment).inflateFollowingArtists(artists);

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }


    public void fetchArtistFollowers(int userId)
    {
        String URL  = "http://cs5200fall2018projectrandom.us-east-2.elasticbeanstalk.com/api/artist/registereduser/follower/"+userId;
        //RequestQueue initialized
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    List<RegisteredUser> artists = mapper.readValue(response.toString(), new TypeReference<List<RegisteredUser>>(){});

                    ((FollowersFragment) currFragment).inflateFollowerRegsiteredUsers(artists);

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }

    public void fetchArtists()
    {
        String URL  = "http://cs5200fall2018projectrandom.us-east-2.elasticbeanstalk.com/api/artist";
        //RequestQueue initialized
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                   List<project.random.fall2018.cs5200.com.myapplication.entities.Artist> artists = mapper.readValue(response.toString(), new TypeReference<List<project.random.fall2018.cs5200.com.myapplication.entities.Artist>>(){});

                    ((FollowFragment) currFragment).inflateAllArtists(artists);

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }


    public void followArtist(int artistId)
    {
        String URL  = "http://cs5200fall2018projectrandom.us-east-2.elasticbeanstalk.com/api/registereduser/follow/"+UserSingleton.getInstance().getUser().getId()+"/"+artistId;
        //RequestQueue initialized
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                fetchFollowingArtists(UserSingleton.getInstance().getRegisteredUser().getId());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }

    public void fetchPlaylists()
    {
        String URL  = "http://cs5200fall2018projectrandom.us-east-2.elasticbeanstalk.com/api/registereduser/"+UserSingleton.getInstance().getUser().getId()+"/playlists";
        //RequestQueue initialized
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    List<Playlist> mPlaylistList = mapper.readValue(response.toString(), new TypeReference<List<Playlist>>(){});

                    ((PlaylistFragment) currFragment).inflatePlaylist(mPlaylistList);
                    PlaylistSingleton.getInstance().setmPlaylists(mPlaylistList);

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }

    public void createPlaylist(String PlaylistName) throws Exception
    {
        Playlist playlist = new Playlist();
        playlist.setName(PlaylistName);
        RegisteredUser user = new RegisteredUser();
        user.setId(UserSingleton.getInstance().getUser().getId());
        playlist.setOwner(user);
        final String mPlaylistString = mapper.writeValueAsString(playlist);

        String URL  = "http://cs5200fall2018projectrandom.us-east-2.elasticbeanstalk.com/api/playlist";
        //RequestQueue initialized
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Playlist mPlaylistList = mapper.readValue(response.toString(), Playlist.class);
                    fetchPlaylists();


                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError
            {
                try {
                    return mPlaylistString.getBytes("utf-8");
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        mRequestQueue.add(mStringRequest);
    }


    public void createSong(Song song) throws Exception
    {

        final String mSongString = mapper.writeValueAsString(song);

        String URL  = "http://cs5200fall2018projectrandom.us-east-2.elasticbeanstalk.com/api/song";
        //RequestQueue initialized
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Song song = mapper.readValue(response.toString(), Song.class);
                    addSongToPlayList(song.getId());


                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError
            {
                try {
                    return mSongString.getBytes("utf-8");
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        mRequestQueue.add(mStringRequest);
    }

    public void addReviewForSong(Song song, String reviewText)throws Exception
    {

        Review review = new Review();
        review.setContent(reviewText);

        final String mSongString = mapper.writeValueAsString(review);

        String URL  = "http://cs5200fall2018projectrandom.us-east-2.elasticbeanstalk.com/api/review/critic/"+UserSingleton.getInstance().getCritic().getId()+"/song/"+song.getId();
        //RequestQueue initialized
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError
            {
                try {
                    return mSongString.getBytes("utf-8");
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        mRequestQueue.add(mStringRequest);
    }

    public void createSongForReview(Song song, final String reviewText) throws Exception
    {

        final String mSongString = mapper.writeValueAsString(song);

        String URL  = "http://cs5200fall2018projectrandom.us-east-2.elasticbeanstalk.com/api/song";
        //RequestQueue initialized
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Song song = mapper.readValue(response.toString(), Song.class);
                    addReviewForSong(song,reviewText);


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError
            {
                try {
                    return mSongString.getBytes("utf-8");
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        mRequestQueue.add(mStringRequest);
    }

    public void addSongToPlayList(int songId)
    {
        int playListId = PlaylistSingleton.getInstance().getmPlaylists().get(PlaylistSingleton.getInstance().getmSelecetedPlayList()).getId();

        String URL  = "http://cs5200fall2018projectrandom.us-east-2.elasticbeanstalk.com/api/playlist/"+playListId+"/song/"+songId;
        //RequestQueue initialized
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                    Toast.makeText(getApplicationContext(),"Song successfully added to playlist",Toast.LENGTH_SHORT).show();

                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);
    }


 public void navigateToPlayListSongs(Playlist playlist)
 {
     currFragment = new SonginPlayListFragment();
     switchFragments(currFragment);
 }



 public void manipulateDrawer(){
        if(UserSingleton.getInstance().getUser().getType().equals("Artist")){
          Menu nav_Menu = mNavigationView.getMenu();
            nav_Menu.findItem(R.id.followers).setVisible(true);
        }

     if(UserSingleton.getInstance().getUser().getType().equals("User")){
         Menu nav_Menu = mNavigationView.getMenu();
         nav_Menu.findItem(R.id.following).setVisible(true);
     }

     if(UserSingleton.getInstance().getUser().getType().equals("Critic")){
         Menu nav_Menu = mNavigationView.getMenu();
         nav_Menu.findItem(R.id.add_reviews).setVisible(true);
     }
 }
}

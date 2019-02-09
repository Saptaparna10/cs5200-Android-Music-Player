package project.random.fall2018.cs5200.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import project.random.fall2018.cs5200.com.myapplication.Singleton.UserSingleton;
import project.random.fall2018.cs5200.com.myapplication.entities.*;
import project.random.fall2018.cs5200.com.myapplication.utilities.VolleySingleton;

public class RegistrationActivity extends AppCompatActivity {

    private Button mSubmit;
    private Button mLogin;
    private EditText mFirstName;
    private EditText mlastName;
    private EditText mUserName;
    private EditText mEmail;
    private EditText mPassword;
    private RadioGroup mUSerGroup;
    private String mUserType="User";
    ObjectMapper mapper = new ObjectMapper();
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private static final String TAG = RegistrationActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mSubmit = (Button) findViewById(R.id.btn_signup);
        mLogin = (Button) findViewById(R.id.btn_link_login);
        mFirstName = (EditText) findViewById(R.id.signup_input_firstName);
        mlastName = (EditText) findViewById(R.id.signup_input_lastName);
        mUserName = (EditText) findViewById(R.id.signup_input_username);
        mEmail = (EditText) findViewById(R.id.signup_input_email);
        mPassword = (EditText) findViewById(R.id.signup_input_password);
        mUSerGroup = (RadioGroup) findViewById(R.id.user_radio_group);

        mUSerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.user_radio_btn:
                        mUserType = "User";
                        break;
                    case R.id.artist_radio_button:
                        mUserType = "Artist";
                        break;
                    case R.id.critic_radio_button:
                        mUserType = "Critic";
                        break;
                }
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (mUserType) {
                    case "User":
                        createUser();
                        break;
                    case "Artist":
                        createArtist();
                        break;
                    case "Critic":
                        createCritic();
                        break;
                    default:
                        createUser();

                }
            }
        });

    }


    public void createUser() {
        RegisteredUser u = new RegisteredUser();
        u.setFirstName(mFirstName.getText().toString());
        u.setLastName(mlastName.getText().toString());
        u.setUsername(mUserName.getText().toString());
        u.setEmail(mEmail.getText().toString());
        u.setPassword(mPassword.getText().toString());
        u.setType(mUserType);
        try {
            final String mUserString = mapper.writeValueAsString(u);
            String URL = "http://cs5200fall2018projectrandom.us-east-2.elasticbeanstalk.com/api/registereduser";
            sendUserCreationRequest(mUserString, URL);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public void createArtist() {
        Artist u = new Artist();
        u.setFirstName(mFirstName.getText().toString());
        u.setLastName(mlastName.getText().toString());
        u.setUsername(mUserName.getText().toString());
        u.setEmail(mEmail.getText().toString());
        u.setPassword(mPassword.getText().toString());
        u.setType(mUserType);

        try {
            final String mUserString = mapper.writeValueAsString(u);
            String URL = "http://cs5200fall2018projectrandom.us-east-2.elasticbeanstalk.com/api/artist";
            sendUserCreationRequest(mUserString, URL);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void createCritic() {
        Critic u = new Critic();
        u.setFirstName(mFirstName.getText().toString());
        u.setLastName(mlastName.getText().toString());
        u.setUsername(mUserName.getText().toString());
        u.setEmail(mEmail.getText().toString());
        u.setPassword(mPassword.getText().toString());
        u.setType(mUserType);
        try {
            final String mUserString = mapper.writeValueAsString(u);
            String URL = "http://cs5200fall2018projectrandom.us-east-2.elasticbeanstalk.com/api/critic";
            sendUserCreationRequest(mUserString, URL);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void navigateToLanding() {
        Intent i = new Intent(RegistrationActivity.this, LandingActivity.class);
        startActivity(i);
    }

    public void saveUserAndNavigate(String response) {
        try {
            UserSingleton.getInstance().setType(mUserType);
            switch (mUserType) {
                case "User":
                    RegisteredUser user = mapper.readValue(response, RegisteredUser.class);
                    UserSingleton.getInstance().setRegisteredUser(user);
                    UserSingleton.getInstance().setUser(user);
                    break;
                case "Artist":
                    Artist artist = mapper.readValue(response, Artist.class);
                    UserSingleton.getInstance().setArtist(artist);
                    UserSingleton.getInstance().setUser(artist);
                    break;
                case "Critic":
                    Critic critic = mapper.readValue(response, Critic.class);
                    UserSingleton.getInstance().setCritic(critic);
                    UserSingleton.getInstance().setUser(critic);
                    break;
                default:
                    RegisteredUser user1 = mapper.readValue(response, RegisteredUser.class);
                    UserSingleton.getInstance().setRegisteredUser(user1);
                    UserSingleton.getInstance().setUser(user1);

            }
            navigateToLanding();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendUserCreationRequest(final String mUserRequestString, String URL) {

        //RequestQueue initialized
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                saveUserAndNavigate(response.toString());

                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG, "Error :" + error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mUserRequestString.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        mRequestQueue.add(mStringRequest);
    }
}

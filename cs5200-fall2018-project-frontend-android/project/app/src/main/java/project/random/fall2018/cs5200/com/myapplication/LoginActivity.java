package project.random.fall2018.cs5200.com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import project.random.fall2018.cs5200.com.myapplication.*;
import project.random.fall2018.cs5200.com.myapplication.*;
import project.random.fall2018.cs5200.com.myapplication.entities.*;
import project.random.fall2018.cs5200.com.myapplication.fragments.PlaylistFragment;
import project.random.fall2018.cs5200.com.myapplication.utilities.VolleySingleton;
import project.random.fall2018.cs5200.com.myapplication.vo.SongListValueObject;
import project.random.fall2018.cs5200.com.myapplication.Singleton.UserSingleton;
import project.random.fall2018.cs5200.com.myapplication.entities.RegisteredUser;
import project.random.fall2018.cs5200.com.myapplication.utilities.VolleySingleton;

public class LoginActivity extends AppCompatActivity {

    private Button mLoginButton;
    private Button mRegistration;
    ObjectMapper mapper = new ObjectMapper();
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private static final String TAG = LoginActivity.class.getName();
    private EditText mUSername;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginButton = (Button) findViewById(R.id.btn_login);
        mRegistration = (Button) findViewById(R.id.btn_link_registration);
        mUSername = (EditText) findViewById(R.id.edt_username);
        mPassword = (EditText) findViewById(R.id.edt_password);
        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(i);
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              login(mUSername.getText().toString(),mPassword.getText().toString());
            }
        });

    }

    public void navigateToLanding() {
        Intent i = new Intent(LoginActivity.this, LandingActivity.class);
        startActivity(i);
    }


    public void login(String username,String password){

        String URL  = "http://cs5200fall2018projectrandom.us-east-2.elasticbeanstalk.com/api/user/credentials/"+ username+"/"+password;
        //RequestQueue initialized
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JsonNode actualObj = mapper.readTree(response.toString());
                    JsonNode jsonNode1 = actualObj.get("type");
                    String userType = jsonNode1.textValue();
                    UserSingleton.getInstance().setType(userType);
                    switch (userType) {
                        case "User":
                            RegisteredUser user = mapper.readValue(response.toString(), RegisteredUser.class);
                            UserSingleton.getInstance().setRegisteredUser(user);
                            UserSingleton.getInstance().setUser(user);
                            break;
                        case "Artist":
                            Artist artist = mapper.readValue(response.toString(), Artist.class);
                            UserSingleton.getInstance().setArtist(artist);
                            UserSingleton.getInstance().setUser(artist);
                            break;
                        case "Critic":
                            Critic critic = mapper.readValue(response.toString(), Critic.class);
                            UserSingleton.getInstance().setCritic(critic);
                            UserSingleton.getInstance().setUser(critic);
                            break;
                        default:
                            Toast.makeText(LoginActivity.this, "Invalid UserName or Password!", Toast.LENGTH_SHORT).show();
                    }

                    navigateToLanding();
                }
                catch (Exception e)
                {
                    Toast.makeText(LoginActivity.this, "Invalid UserName or Password!", Toast.LENGTH_SHORT).show();
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
}

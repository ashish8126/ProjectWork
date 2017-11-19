package series.test.yuvayana.com.yuvayanatestseries.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import series.test.yuvayana.com.yuvayanatestseries.R;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {

    private SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;
    Button login;
    LoginButton fb;
    CallbackManager callbackmanager=null;
    public TextInputLayout inputLayoutName, inputLayoutPass;
    public EditText userName, password;
    // Commit By Ashish Viltoriya

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        fb = (LoginButton) findViewById(R.id.fbbutton);
        login = (Button) findViewById(R.id.loginbutton);
        fb.setReadPermissions("email", "public_profile");
        callbackmanager = CallbackManager.Factory.create();
        fb.registerCallback(callbackmanager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String userid = loginResult.getAccessToken().getUserId();
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        displayUserInfo(object);
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "first_name,last_name,email,id");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this,""+error,Toast.LENGTH_SHORT).show();
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutPass = (TextInputLayout) findViewById(R.id.input_layout_pass);

        signInButton = (SignInButton)findViewById(R.id.signinbutton);
        userName = (EditText) findViewById(R.id.input_userName);
        password = (EditText) findViewById(R.id.input_pass);

        userName.addTextChangedListener(new MyTextWatcher(userName));
        password.addTextChangedListener(new MyTextWatcher(password));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabb);
        fab.setVisibility(View.GONE);

        signInButton.setOnClickListener(this);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();

    }


    public void displayUserInfo(JSONObject object) {
        String first_name,last_name,email,id;
        try {
            first_name = object.getString("first_name");
            last_name = object.getString("last_name");
            email = object.getString("email");
            id = object.getString("id");

            Toast.makeText(LoginActivity.this,first_name+" "+last_name+" "+id+" "+email,Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected boolean validateUserName() {

        if (userName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setErrorEnabled(true);
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(userName);
            return false;
        } else if (userName.getText().toString().length() <= 5) {
            inputLayoutName.setErrorEnabled(true);
            inputLayoutName.setError(getString(R.string.err_valid_name));
            requestFocus(userName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

         return true;
    }

    protected boolean validatePassword() {

        if (password.getText().toString().trim().isEmpty()) {
            try {
                inputLayoutPass.setErrorEnabled(true);
                inputLayoutPass.setError(getString(R.string.err_msg_pass));
                requestFocus(password);
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }  else if (password.getText().toString().length() <= 5) {
            try {
                inputLayoutPass.setErrorEnabled(true);
                inputLayoutPass.setError(getString(R.string.err_valid_pass));
                requestFocus(password);
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            inputLayoutPass.setErrorEnabled(false);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginActivity.this.finish();
                    }
                }).show();
    }

    public void login(View c) {
        if (!validateUserName()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        Toast.makeText(LoginActivity.this, "Thank You!", Toast.LENGTH_SHORT).show();
    }

    protected void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    /**
     * Method added to go to sign up page
     ***/
    public void gotoSignUp(View view) {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.signinbutton:
                signIn();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void signIn(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);

    }

    private void handleResult(GoogleSignInResult result){
        if(result.isSuccess()){
            Toast.makeText(LoginActivity.this,"Google Login Success",Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText(LoginActivity.this,"Google Login UnSuccessfull",Toast.LENGTH_LONG).show();
            // updateUI(false);
        }
    }

    private void updateUI(boolean isLogin){

    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }

    class MyTextWatcher extends LoginActivity implements TextWatcher {

        View myView;

        MyTextWatcher(View v) {
            this.myView = v;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (myView.getId()) {
                case R.id.input_userName:
                    validateUserName();
                    break;
                case R.id.input_pass:
                    validatePassword();
                    break;
            }
        }
    }

}






package series.test.yuvayana.com.yuvayanatestseries.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import series.test.yuvayana.com.yuvayanatestseries.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailId,passwordId,conPasswordId;
    private TextInputLayout emailLayout,passLayout,confPassLayout;
    private Button registerButtonId;
    private String userEmail,userPassword;
    private static String signUpUrl = "http://test.yuvayana.org/wp-json/wp/v2/users/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /***** Get UI Id's *****/
        emailLayout     = (TextInputLayout)findViewById(R.id.input_layout_email);
        passLayout      = (TextInputLayout)findViewById(R.id.input_layout_passs);
        confPassLayout  = (TextInputLayout)findViewById(R.id.input_layout_confPass);

        emailId             = (EditText)findViewById(R.id.input_email);
        passwordId          = (EditText)findViewById(R.id.input_passs);
        conPasswordId       = (EditText)findViewById(R.id.input_confPass);
        registerButtonId    = (Button) findViewById(R.id.registerButton);

        emailId.addTextChangedListener(new MyTextWatcher(emailId));
        passwordId.addTextChangedListener(new MyTextWatcher(passwordId));
        conPasswordId.addTextChangedListener(new MyTextWatcher(conPasswordId));


        /***** Work For Back Button in Toolbar *****/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                Intent i = new Intent(SignUpActivity.this,LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    protected boolean validateEmail() {

        String email = emailId.getText().toString();
        if (email.trim().isEmpty()) {
            emailLayout.setErrorEnabled(true);
            emailLayout.setError(getString(R.string.err_msg_email));
            requestFocus(emailId);
            return false;
        } else if (!email.contains("@") || email.length() <= 5) {
            emailLayout.setErrorEnabled(true);
            emailLayout.setError(getString(R.string.err_valid_email));
            requestFocus(emailId);
            return false;
        } else {
            userEmail = email;
            emailLayout.setErrorEnabled(false);
        }

        return true;
    }

    protected void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private String password = "";

    protected boolean chkPassword() {

        password = passwordId.getText().toString();
        if (password.trim().isEmpty()) {
            //Toast.makeText(SignUpActivity.this,"Glt Chal rha h",Toast.LENGTH_SHORT).show();
            try {
                passLayout.setErrorEnabled(true);
                passLayout.setError(getString(R.string.err_msg_pass));
                requestFocus(passwordId);
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (password.length() <= 5) {
            try {
                passLayout.setErrorEnabled(true);
                passLayout.setError(getString(R.string.err_valid_pass));
                requestFocus(passwordId);
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            passLayout.setErrorEnabled(false);
        }

       // password = passwordId.getText().toString();
        return true;
    }

    protected boolean validateConfPassword() {

        String confPass = conPasswordId.getText().toString();
        if (confPass.trim().isEmpty()) {
            try {
                confPassLayout.setErrorEnabled(true);
                confPassLayout.setError(getString(R.string.err_conf_pass));
                requestFocus(conPasswordId);
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (!confPass.equalsIgnoreCase(password)) {
            try {
                confPassLayout.setErrorEnabled(true);
                confPassLayout.setError(getString(R.string.err_conf_pass_match));
                requestFocus(conPasswordId);
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            userPassword = confPass;
            confPassLayout.setErrorEnabled(false);
        }

        return true;
    }

    public void signUp(View c) {
        if (!validateEmail()) {
            return;
        }

        if (!chkPassword()) {
            return;
        }

        if (!validateConfPassword()) {
            return;
        }

        if (userEmail != "" && userPassword != "") {
            //Toast.makeText(SignUpActivity.this,userEmail + "-----------" + userPassword,Toast.LENGTH_SHORT).show();
            sendSignUpDataToServer();
        }


    }

    /**** Method Add for Sending Data to the Server *****/
    private void sendSignUpDataToServer() {

        JsonArrayRequest signUpReq = new JsonArrayRequest(signUpUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONArray ashish = response;
                        Toast.makeText(SignUpActivity.this,""+ashish,Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUpActivity.this,""+error,Toast.LENGTH_SHORT).show();
            }

        } ) {

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",userEmail);
                params.put("email",userEmail);
                params.put("password",userPassword);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Authorization","Basic eXV2YXlhbmFAeWFob28uY29tOkJpbEBzcHVyIzMy");
                return params;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
        queue.add(signUpReq);


    }

    private class MyTextWatcher implements TextWatcher {

        View myVieww;

        MyTextWatcher(View v) {
            this.myVieww = v;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (myVieww.getId()) {
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_passs:
                    chkPassword();
                    break;
                case R.id.input_confPass:
                    validateConfPassword();
                    break;
            }
        }

    }

}

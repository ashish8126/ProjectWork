package series.test.yuvayana.com.yuvayanatestseries.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import series.test.yuvayana.com.yuvayanatestseries.R;

public class LoginActivity extends AppCompatActivity {


    private TextInputLayout inputLayoutName,inputLayoutPass;
    private EditText userName,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutPass = (TextInputLayout)findViewById(R.id.input_layout_pass);

        userName = (EditText)findViewById(R.id.input_userName);
        password = (EditText)findViewById(R.id.input_pass);

        userName.addTextChangedListener(new MyTextWatcher(userName));
        password.addTextChangedListener(new MyTextWatcher(password));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabb);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        fab.setVisibility(View.GONE);
    }

    protected boolean validateUserName(){

        String x = userName.getText().toString();
        Toast.makeText(LoginActivity.this,"Input String Is -> "+x,Toast.LENGTH_SHORT).show();


        /*if (userName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(userName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }*/

        return true;
    }

    protected void validatePassword() {

    }

    public void login(View c) {
        if (!validateUserName()) {
            return;
        }

        /*if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }*/

        Toast.makeText(LoginActivity.this, "Thank You!", Toast.LENGTH_SHORT).show();
    }

    protected void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    /**
     *
     * Method added to go to sign up page
     *
     ***/
    public void gotoSignUp(View view) {
        startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
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
            case R.id.input_userName :
                validateUserName();
                break;
            case R.id.input_pass :
                validatePassword ();
                break;
        }
    }

}

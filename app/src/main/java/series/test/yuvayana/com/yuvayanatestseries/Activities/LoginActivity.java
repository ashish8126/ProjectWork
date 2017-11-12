package series.test.yuvayana.com.yuvayanatestseries.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
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

    public TextInputLayout inputLayoutName, inputLayoutPass;
    public EditText userName, password;
    // Commit By Abhishek
    // Again


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutPass = (TextInputLayout) findViewById(R.id.input_layout_pass);

        userName = (EditText) findViewById(R.id.input_userName);
        password = (EditText) findViewById(R.id.input_pass);

        userName.addTextChangedListener(new MyTextWatcher(userName));
        password.addTextChangedListener(new MyTextWatcher(password));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabb);
        fab.setVisibility(View.GONE);
    }

    public boolean validateUserName() {

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

    public boolean validatePassword() {

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


    private class MyTextWatcher implements TextWatcher {

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

package com.rateit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rateit.http.HttpClient;

public class AccountActivity extends RateItActivity {

    boolean b;

    private void initializeSignIn()
    {
        final AccountActivity activity = this;

        final EditText loginTxt = (EditText)findViewById(R.id.loginTxt);
        final EditText passwordTxt = (EditText)findViewById(R.id.passwordTxt);

        Button signinBtn = (Button)findViewById(R.id.signinBtn);
        Button forgotBtn = (Button)findViewById(R.id.forgotPasswBtn);
        Button regBtn = (Button)findViewById(R.id.regBtn);

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = loginTxt.getText().toString();
                String password = passwordTxt.getText().toString();

                HttpClient httpClient = activity.getHttpClient();

                httpClient.Autorize(activity, username, password);
            }
        });

        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotPassword();
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegistration();
            }
        });
    }

    private void initializeForgotPassword()
    {
        final Activity activity = this;
        final EditText emailTxt = (EditText)findViewById(R.id.emailTxt);
        Button restoreBtn = (Button)findViewById(R.id.restoreBtn);
        restoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTxt.getText().toString();

                // действия по восстановлению пароля

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(R.string.forgot_layout_dialog_result_message_lb)
                        .setTitle(R.string.forgot_layout_dialog_result_title_lb)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showSignIn();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void showSignIn()
    {
        setContentView(R.layout.signin_layout);
        initializeSignIn();
        b = false;
    }

    private  void showForgotPassword()
    {
        setContentView(R.layout.forgot_layout);
        initializeForgotPassword();
        b = true;
    }

    private void showRegistration()
    {
        setContentView(R.layout.registration_layout);
        b = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showSignIn();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        if (b)
            showSignIn();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

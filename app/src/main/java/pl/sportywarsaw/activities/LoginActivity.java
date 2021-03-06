package pl.sportywarsaw.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import pl.sportywarsaw.MyApplication;
import pl.sportywarsaw.R;
import pl.sportywarsaw.infrastructure.CustomCallback;
import pl.sportywarsaw.models.AccessTokenModel;
import pl.sportywarsaw.services.AccountService;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A login screen that offers login via username and password.
 *
 * @author Marcin Chudy
 */
public class LoginActivity extends AppCompatActivity  {

    private static final String GRANT_TYPE = "password";
    public static final String ACCESS_TOKEN_KEY = "accessToken";
    public static final String USERNAME_KEY = "username";

    private EditText usernameView;
    private EditText passwordView;

    private ProgressDialog progressDialog;

    @Inject AccountService service;
    @Inject SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ((MyApplication) getApplication()).getServicesComponent().inject(this);

        if(!TextUtils.isEmpty(preferences.getString(ACCESS_TOKEN_KEY, null))){
            showMainActivity();
        }

        usernameView = (EditText) findViewById(R.id.username);
        passwordView = (EditText) findViewById(R.id.password);

        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void attemptLogin() {
        String username = usernameView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        TextInputLayout layout = (TextInputLayout) findViewById(R.id.password_layout);
        if (TextUtils.isEmpty(password)) {
            layout.setError(getString(R.string.error_field_required));
            focusView = passwordView;
            cancel = true;
        } else {
            layout.setError(null);
        }

        layout = (TextInputLayout) findViewById(R.id.username_layout);
        if (TextUtils.isEmpty(username)) {
            layout.setError(getString(R.string.error_field_required));
            focusView = usernameView;
            cancel = true;
        } else {
            layout.setError(null);
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            progressDialog = ProgressDialog.show(this, getString(R.string.please_wait),
                    getString(R.string.authenticating), true);
            getToken(username, password);
        }
    }

    private void getToken(final String username, String password) {
        Call<AccessTokenModel> call = service.getToken(username, password, GRANT_TYPE);
        call.enqueue(new CustomCallback<AccessTokenModel>(this) {
            @Override
            public void onSuccess(AccessTokenModel model) {
                String token = model.getAccessToken();
                if(!TextUtils.isEmpty(token)) {
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString(ACCESS_TOKEN_KEY, token);
                    edit.putString(USERNAME_KEY, username);
                    edit.apply();
                    showMainActivity();
                }
            }

            @Override
            public void onResponse(Response<AccessTokenModel> response, Retrofit retrofit) {
                if(!response.isSuccess() && response.code() == 400) {
                    progressDialog.dismiss();
                    showIncorrectCredentialsDialog();
                } else {
                    super.onResponse(response, retrofit);
                }
            }

            @Override
            public void always() {
                progressDialog.dismiss();
            }
        });
    }

    private void showIncorrectCredentialsDialog() {
        new AlertDialog.Builder(LoginActivity.this)
            .setTitle(R.string.incorrect_credentials)
            .setMessage(R.string.incorrect_credentials_message)
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }

    private void showMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}


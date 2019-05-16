package com.eqiba.kizen.client.view.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.eqiba.kizen.client.R;
import com.eqiba.kizen.client.contract.LoginContract;
import com.eqiba.kizen.client.presenter.LoginPresenterImpl;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginContract.LoginView
{

    private EditText usernameText,passwordText;
    private ProgressBar progressBar;
    private LoginContract.LoginPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindView();
        presenter=new LoginPresenterImpl(this);

    }

    private void bindView()
    {
        usernameText=findViewById(R.id.username_login);
        passwordText=findViewById(R.id.password_login);
        findViewById(R.id.login_button).setOnClickListener(this);
        findViewById(R.id.register_button).setOnClickListener(this);
        progressBar=findViewById(R.id.progressBar_login);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.login_button:
            {
                presenter.login();
                break;
            }
            case R.id.register_button:
            {
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            }
        }
    }

    @Override
    public String getUsername() {
        return usernameText.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordText.getText().toString();
    }

    @Override
    public void waitDialog() {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void finishWait() {
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void flushPassword()
    {
        passwordText.setText("");
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toMain() {
        setResult(RESULT_OK);
        finish();
    }
}

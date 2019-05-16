package com.eqiba.kizen.client.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.eqiba.kizen.client.R;
import com.eqiba.kizen.client.contract.RegisterContract;
import com.eqiba.kizen.client.presenter.RegisterPresenterImpl;
import com.eqiba.kizen.client.view.VerificationCode;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.RegisterView, View.OnClickListener {

    private EditText usernameText,passwordText,repeatText,verificationText;
    private VerificationCode verificationCode;
    private RegisterContract.RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bindView();
        presenter = new RegisterPresenterImpl(this);
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
    public boolean verify() {
        return verificationText.getText().toString().equals(verificationCode.getVerificationCode());
    }

    @Override
    public boolean repeat() {
        return passwordText.getText().toString().equals(repeatText.getText().toString());
    }

    @Override
    public void flushContent() {
        passwordText.setText("");
        repeatText.setText("");
        verificationText.setText("");
        verificationCode.flushCode();
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishRegister() {
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.register_button_register:
            {
                presenter.register();
                break;
            }
        }
    }

    private void bindView()
    {
        usernameText=findViewById(R.id.username_text_register);
        passwordText=findViewById(R.id.password_text_register);
        repeatText=findViewById(R.id.repeat_text_register);
        verificationText=findViewById(R.id.verification_text_register);
        verificationCode=findViewById(R.id.verification_register);
        findViewById(R.id.register_button_register).setOnClickListener(this);
    }
}

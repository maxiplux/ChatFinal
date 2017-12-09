package net.juanfrancisco.blog.chatfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements AuthContract, Validator.ValidationListener {

    @NotEmpty
    @Email(message = "Por favor escribir un correo valido")
    @BindView(R.id.edtEmail)
    TextInputEditText edtEmail;


    @NotEmpty
    @Password(min = 6, scheme = Password.Scheme.ALPHA,message = "Por favor escribir catareres alfanumericos , con maximo 6 carateres")
    @BindView(R.id.edtPassword)
    TextInputEditText edtPassword;




    @BindView(R.id.textInputLayout)
    TextInputLayout textInputLayout;
    @BindView(R.id.textInputLayout2)
    TextInputLayout textInputLayout2;


    @BindView(R.id.btnEntrar)
    Button btnEntrar;
    @BindView(R.id.btnRegistrar)
    Button btnRegistrar;

    private FirebaseAuth auth;

    Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        validator = new Validator(this);
        validator.setValidationListener(this);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        this.goToMainActivity();


    }


    @Override
    public void goToMainActivity() {
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }



    }

    @Override
    public void goToRegister() {

        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        finish();

    }

    @Override
    public void goToLogin() {

    }

    @Override
    public void doRegister() {

    }

    @Override
    public void doLogin() {

        String email = this.edtEmail.getText().toString();
        final String password = this.edtPassword.getText().toString();

        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        //progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();

                        } else {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });


    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Yay! we got it right!", Toast.LENGTH_SHORT).show();
        this.doLogin();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick(R.id.btnEntrar)
    public void onBtnEntrarClicked() {
        this.validator.validate();

    }

    @OnClick(R.id.btnRegistrar)
    public void onBtnRegistrarClicked() {
        this.goToRegister();
    }
}

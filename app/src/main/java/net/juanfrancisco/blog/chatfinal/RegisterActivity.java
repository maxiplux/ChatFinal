package net.juanfrancisco.blog.chatfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
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




public class RegisterActivity extends AppCompatActivity  implements AuthContract,Validator.ValidationListener{

    @NotEmpty(message = "Campo requerido")
    @Email(message = "Correo invalido ")
    @BindView(R.id.edtEmail)
    TextInputEditText edtEmail;

    @NotEmpty(message = "Campo requerido")
    @Password(min = 6, scheme = Password.Scheme.ALPHA,message = "Por favor escribir catareres alfanumericos , con maximo 6 carateres")

    @BindView(R.id.edtPassword)
    TextInputEditText edtPassword;
    @BindView(R.id.btnVolver)
    Button btnVolver;
    @BindView(R.id.btnRegistrar)
    Button btnRegistrar;

    Validator validator ;


    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();


        validator = new Validator(this);
        validator.setValidationListener(this);


    }

    @OnClick({R.id.btnVolver, R.id.btnRegistrar})
    public void onViewClicked(View view)
    {
        switch (view.getId()) {
            case R.id.btnVolver:

                goToLogin();
            case R.id.btnRegistrar:
                this.validator.validate();
                doRegister();
        }
    }





    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Yay! we got it right!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void goToMainActivity() {

    }

    @Override
    public void goToRegister() {

    }

    @Override
    public void goToLogin() {

    }

    @Override
    public void doRegister() {

        String email = this.edtEmail.getText().toString();
        final String password = this.edtPassword.getText().toString();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                        if (!task.isSuccessful())
                        {
                            Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });



    }

    @Override
    public void doLogin() {



    }
}

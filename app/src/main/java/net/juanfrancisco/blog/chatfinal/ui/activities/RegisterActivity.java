package net.juanfrancisco.blog.chatfinal.ui.activities;

import android.content.Context;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import net.juanfrancisco.blog.chatfinal.MainActivity;
import net.juanfrancisco.blog.chatfinal.R;
import net.juanfrancisco.blog.chatfinal.core.AuthContract;
import net.juanfrancisco.blog.chatfinal.core.SingletonApplication;
import net.juanfrancisco.blog.chatfinal.models.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class RegisterActivity extends AppCompatActivity implements AuthContract, Validator.ValidationListener {

    @NotEmpty(message = "Campo requerido")
    @Email(message = "Correo invalido ")
    @BindView(R.id.edtEmail)
    TextInputEditText edtEmail;

    @NotEmpty(message = "Campo requerido")
    @Password(min = 6, scheme = Password.Scheme.ALPHA, message = "Por favor escribir catareres alfanumericos , con maximo 6 carateres")

    @BindView(R.id.edtPassword)
    TextInputEditText edtPassword;
    @BindView(R.id.btnVolver)
    Button btnVolver;


    @BindView(R.id.btnRegistrar)
    Button btnRegistrar;

    Validator validator;
    boolean all_ok=false;
    private Context currentContext;



    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        this.currentContext=this;

        mDatabase = SingletonApplication.getFirbaseDatabaseReference();


        validator = new Validator(this);



    }

    @OnClick({R.id.btnVolver, R.id.btnRegistrar})
    public void onViewClicked(View view) {
        switch (view.getId())
        {
            case R.id.btnVolver:
                //this.validator.isValidating();

                goToLogin();

            case R.id.btnRegistrar:

                validator.setValidationListener(this);
                this.validator.validate();
                doRegister();
        }
    }


    @Override
    public void onValidationSucceeded() {
        this.all_ok=true;
        Toast.makeText(this, "Exito Todos los campos estan ok ", Toast.LENGTH_SHORT).show();
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

        startActivity(new Intent(this, LoginActivity.class));
        finish();


    }

    @Override
    public void doRegister() {
        if (all_ok)
        {
            String email = this.edtEmail.getText().toString();
            final String password = this.edtPassword.getText().toString();

            SingletonApplication.getFirbaseUserReference().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful())
                            {

                                new SweetAlertDialog(currentContext, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Error!")
                                        .setContentText("Fallo de autenticaciòn!")
                                        .show();


                            }

                            if(task.getException() instanceof FirebaseAuthUserCollisionException) {

                                new SweetAlertDialog(currentContext, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Error!")
                                        .setContentText("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.")
                                        .show();




                            }
                            else
                            {
                                FirebaseUser firebaseUser = task.getResult().getUser();
                                User user = new User();
                                user.setId(firebaseUser.getUid());
                                user.setEmail(firebaseUser.getEmail());
                                user.setPassword(null);

                                mDatabase.child("users").child(user.getId()).setValue(user);

                                new SweetAlertDialog(currentContext, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Exito!")
                                        .setContentText("Registro Completo!")
                                        .show();
                                // Toast.makeText(RegisterActivity.this, "Registro Completo:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                finish();

                            }









                        }
                    });


        }




    }

    @Override
    public void doLogin() {


    }
}

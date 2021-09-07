package in.rbofficial.myott;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import in.rbofficial.myott.databinding.ActivityLoginBinding;
import in.rbofficial.myott.databinding.ActivityMainBinding;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private String email,password;

    private FirebaseAuth auth;
    private FirebaseUser user;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();

        binding.navSignUp.setOnClickListener(vsup->{
            startActivity(new Intent(getApplicationContext(),Signup.class));
        });



        if(user!=null){
//            Move to main activity
            startActivity(new Intent(this,MainActivity.class));
            finish();

        }


        binding.btnSignIn.setOnClickListener(v -> {
            email = binding.email.getText().toString();
            password = binding.password.getText().toString();
            if(email.isEmpty()){
                View view = findViewById(R.id.loginLayout);
                Snackbar.make(view,"Field Can't be Empty",Snackbar.LENGTH_LONG)
                        .setAction("Retry", ve -> {
                            binding.email.requestFocus();
                        }).show();
                return;
            }

            if(password.isEmpty()){
                View view = findViewById(R.id.loginLayout);
                Snackbar.make(view,"Field Can't be Empty",Snackbar.LENGTH_LONG)
                        .setAction("Retry", vw -> {
                            binding.password.requestFocus();
                        }).show();
                return;
            }

            pd.show();
            pd.setCancelable(false);
            auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){

                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "Welcome Back !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }else {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "Error In Login", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(e ->
                    Toast.makeText(getApplicationContext(), "User No Found!", Toast.LENGTH_SHORT).show());

        });



    }

    private void initialize() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        pd = new ProgressDialog(this);
        pd.setMessage("Checking credentials! Please Wait");

    }
}
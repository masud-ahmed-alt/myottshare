package in.rbofficial.myott;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import in.rbofficial.myott.databinding.ActivitySignupBinding;

public class Signup extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private String name,email,password,password2;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pd = new ProgressDialog(this);
        pd.setMessage("Loading! Please Wait");

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        binding.navSignIn.setOnClickListener(vsup->{
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        });

        binding.btnSignUp.setOnClickListener(v->{
            name = binding.fullName.getText().toString();
            email = binding.email.getText().toString();
            password = binding.password.getText().toString();
            password2 = binding.passwordConf.getText().toString();
            if(name.isEmpty()){
                View view = findViewById(R.id.signUpLayout);
                Snackbar.make(view,"Field Can't be Empty",Snackbar.LENGTH_LONG)
                        .setAction("Retry", vw -> {
                            binding.fullName.requestFocus();
                        }).show();
                return;
            }

            if(email.isEmpty()){
                View view = findViewById(R.id.signUpLayout);
                Snackbar.make(view,"Field Can't be Empty",Snackbar.LENGTH_LONG)
                        .setAction("Retry", vw -> {
                            binding.email.requestFocus();
                        }).show();
                return;
            }

            if(!password.isEmpty() && !password2.isEmpty()){
                if (!password.equals(password2)) {
                    View view = findViewById(R.id.signUpLayout);
                    Snackbar.make(view,"Password and Confirm Password Should be Same",Snackbar.LENGTH_LONG)
                            .setAction("Retry", vw -> {
                                binding.passwordConf.requestFocus();
                            }).show();
                    return;
                }

            }else {
                View view = findViewById(R.id.signUpLayout);
                Snackbar.make(view,"Field Can't be Empty",Snackbar.LENGTH_LONG)
                        .setAction("Retry", vw -> {
                            binding.password.requestFocus();
                        }).show();
                return;
            }

            auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(task -> {

                        if(task.isSuccessful()){
                            pd.show();
                            pd.setCancelable(false);
                            user = auth.getCurrentUser();
                            Map<String,Object> data = new HashMap<>();
                            data.put("name",name);
                            data.put("email",email);
                            data.put("user_id",user.getUid());
                            db.collection("users").add(data)
                                    .addOnCompleteListener(task1 -> {
                                        if(task1.isSuccessful()){
                                            pd.dismiss();
                                            Toast.makeText(getApplicationContext(),
                                                    "Success", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                            finish();
                                        }
                                    }).addOnFailureListener(e -> {
                                        pd.dismiss();
                                Toast.makeText(getApplicationContext(), "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });

                        }
                    }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Error "+e.getMessage(), Toast.LENGTH_SHORT).show());

        });


    }

    private void validate(){
//        Empty


    }
}
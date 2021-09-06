package in.rbofficial.myott.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import in.rbofficial.myott.Login;
import in.rbofficial.myott.Models.UserModel;
import in.rbofficial.myott.R;
import in.rbofficial.myott.databinding.FragmentHomeBinding;
import in.rbofficial.myott.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {
    private FirebaseUser user;
    private FirebaseAuth auth;
    private FragmentProfileBinding binding;
    private FirebaseFirestore db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        if(user==null){
            binding.name.setText("You Are not Logged In");
            binding.signOut.setVisibility(View.GONE);
        }else {
            binding.signOut.setVisibility(View.VISIBLE);
        }
        db.collection("users").whereEqualTo("user_id",user.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        QuerySnapshot data = task.getResult();
                        for (QueryDocumentSnapshot docs : data){
                            binding.name.setText(docs.get("name").toString());
                            binding.email.setText(docs.get("email").toString());
                        }
                    }
                }).addOnFailureListener(e -> Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show())
        ;
        binding.signOut.setOnClickListener(v->{
            auth.signOut();
            startActivity(new Intent(getContext(),Login.class));
            getActivity().finish();
        });




        return view;
    }
}
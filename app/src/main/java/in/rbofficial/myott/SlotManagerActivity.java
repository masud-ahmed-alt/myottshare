package in.rbofficial.myott;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import in.rbofficial.myott.databinding.ActivityNetflixBinding;
import in.rbofficial.myott.ui.mysubscription.MySubsFragment;

import static in.rbofficial.myott.Constant.Constant.CURRENCY;


public class SlotManagerActivity extends AppCompatActivity implements PaymentResultListener {
    ActivityNetflixBinding binding;
    private String email, name, desc, image, subName;
    private int amount;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db;
    private int usersSize = 0;
    private FirebaseFirestore current_ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNetflixBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getIntentData();
        db = FirebaseFirestore.getInstance();
        binding.btnBuy.setOnClickListener(v->{
            if (binding.checkbox.isChecked()){
                Toast.makeText(this, "Complete Payment", Toast.LENGTH_SHORT).show();
//                if(isSlothAvailable()){
//                    Toast.makeText(getApplicationContext(), "Direct Pay", Toast.LENGTH_SHORT).show();
//                    pay();
//                }else {
//                    Toast.makeText(getApplicationContext(), "Create Slot", Toast.LENGTH_SHORT).show();
//                    createAndPay();
//                }
                isSlothAvailable();
            }
            else {
                binding.checkbox.setError("Please accept terms and conditions");
            }
        });
    }

    private void createAndPay() {
        HashMap<String, Object> slot = new HashMap<>();
        List<String> users = new ArrayList<>();
        slot.put("platform",name);
        slot.put("price",name);
        slot.put("users",users);
        db.collection("slots").add(slot)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Slot Refreshed", Toast.LENGTH_SHORT).show();
                        pay();
                        current_ref = task.getResult().getFirestore();
                    }

                }).addOnFailureListener(e -> {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        });

    }


    private void isSlothAvailable() {
        db.collection("slots").get()
                .addOnCompleteListener(task -> {
                    QuerySnapshot snapshots = task.getResult();
                    for (QueryDocumentSnapshot documentSnapshot : snapshots){

                    }
                });
        Toast.makeText(getApplicationContext(), "TOATL : "+String.valueOf(usersSize) , Toast.LENGTH_SHORT).show();
    }

    private void getIntentData(){
        Intent intent  = getIntent();
        if(intent!=null)
        {
            desc = intent.getStringExtra("desc");
            name = intent.getStringExtra("name");
            subName = intent.getStringExtra("subName");
            amount = intent.getIntExtra("amount",1000);
            if(user==null){
                return;
            }
            email = user.getEmail();
        }
    }

    private void pay() {
        Checkout co = new Checkout();
        JSONObject object = new JSONObject();
        try {
            object.put("name", subName);
            object.put("desc","");
            object.put("image",R.drawable.yt);
            object.put("currency",CURRENCY);
            object.put("amount",amount*100);

            JSONObject prefill = new JSONObject();
           // prefill.put("contact","9101743618");
            prefill.put("email",email);
            object.put("prefill",prefill);

            co.open(SlotManagerActivity.this,object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.d("abc","fhg  s : "+s);
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();
        updateDatabase(s);

        try {
            startActivity(new Intent(getApplicationContext(), MySubsFragment.class));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void updateDatabase(String s) {
        db.collection("slots").get().addOnCompleteListener(task -> {
            QuerySnapshot snapshots = task.getResult();
            for (QueryDocumentSnapshot ss : snapshots){
                List<String> users = (List<String>) ss.get("users");
                if(users.size()<4){
                    List<DocumentSnapshot> doc = task.getResult().getDocuments();
                    Log.d(TAG, "updateDatabase: "+doc);
                }
            }
        }).addOnFailureListener(e -> {

        });
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
        try {
            startActivity(new Intent(getApplicationContext(), MySubsFragment.class));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
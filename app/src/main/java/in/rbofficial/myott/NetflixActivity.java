package in.rbofficial.myott;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import in.rbofficial.myott.databinding.ActivityNetflixBinding;


public class NetflixActivity extends AppCompatActivity implements PaymentResultListener {
    ActivityNetflixBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNetflixBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.btnBuy.setOnClickListener(v->{
            if (binding.checkbox.isChecked()){
                Toast.makeText(this, "Complete Payment", Toast.LENGTH_SHORT).show();
                pay();
            }
            else {
                binding.checkbox.setError("Please accept terms and conditions");
            }
        });
    }

    private void pay() {
        Checkout co = new Checkout();
        JSONObject object = new JSONObject();
        try {
            object.put("name","MyOTShare");
            object.put("desc","Buy Netflix Premium");
            object.put("image","data:image/png;base64,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");
            object.put("currency","INR");
            object.put("amount","100");

            JSONObject prefill = new JSONObject();
            prefill.put("contact","9101743618");
            prefill.put("email","ma7332125@gmail.com");
            object.put("prefill",prefill);

            co.open(NetflixActivity.this,object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.d("abc","fhg  s : "+s);
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
    }
}
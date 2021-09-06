package in.rbofficial.myott.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import in.rbofficial.myott.NetflixActivity;
import in.rbofficial.myott.PrimeVideoActivity;
import in.rbofficial.myott.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.netflix.setOnClickListener(v->{
            startActivity(new Intent(getContext(), NetflixActivity.class));
        });
        binding.primeVideo.setOnClickListener(v->{
            startActivity(new Intent(getContext(), PrimeVideoActivity.class));
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}
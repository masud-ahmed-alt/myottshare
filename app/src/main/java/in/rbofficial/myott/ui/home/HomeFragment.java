package in.rbofficial.myott.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.rbofficial.myott.Adapters.MainAdapter;
import in.rbofficial.myott.Models.MainModel;
import in.rbofficial.myott.R;
import in.rbofficial.myott.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private List<MainModel> list;

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        init();

        list.add(new MainModel(
                R.drawable.prime_vdo,
                "Prime Video",
                "100"
        ));
        list.add(new MainModel(
                R.drawable.netflix,
                "Netflix",
                "210"
        ));
        list.add(new MainModel(
                R.drawable.hot,
                "Desney + Hotstar",
                "400"
        ));
        list.add(new MainModel(
                R.drawable.sony,
                "SonyLIV",
                "50"
        ));

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();




        return view;
    }

    private void init() {
        recyclerView = binding.mainView;
        list = new ArrayList<>();
        adapter = new MainAdapter(getContext(),list);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}
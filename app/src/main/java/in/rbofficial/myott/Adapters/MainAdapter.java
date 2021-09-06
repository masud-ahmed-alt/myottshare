package in.rbofficial.myott.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import in.rbofficial.myott.Models.MainModel;
import in.rbofficial.myott.NetflixActivity;
import in.rbofficial.myott.R;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MViewHolder> {
    private Context context;
    private List<MainModel> list;

    public MainAdapter(Context context, List<MainModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_platform,parent,false);
        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MViewHolder holder, int position) {
        MainModel mainModel = list.get(position);
          holder.price.setText(mainModel.getPriceDesc()+ "INR Per Screen");
          holder.brand.setText(mainModel.getBrandName());
          holder.logo.setImageResource(mainModel.getImage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView logo;
        private TextView brand,price;
        public MViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            logo = itemView.findViewById(R.id.brandLogo);
            brand = itemView.findViewById(R.id.brandName);
            price = itemView.findViewById(R.id.priceDesc);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,NetflixActivity.class);
            context.startActivity(intent);
        }
    }
}

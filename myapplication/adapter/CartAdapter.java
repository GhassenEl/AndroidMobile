package tn.esprit.myapplication.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tn.esprit.myapplication.R;
import tn.esprit.myapplication.model.Product;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<Product> cartList;

    public CartAdapter(Context context, List<Product> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price;

        public CartViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageCartProduct);
            name = itemView.findViewById(R.id.textCartName);
            price = itemView.findViewById(R.id.textCartPrice);
        }
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_cart_product, parent, false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        Product p = cartList.get(position);
        holder.image.setImageResource(p.getImageResId());
        holder.name.setText(p.getName());
        holder.price.setText(p.getPrice() + " DT");
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}

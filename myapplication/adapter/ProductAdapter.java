package tn.esprit.myapplication.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tn.esprit.myapplication.R;
import tn.esprit.myapplication.model.PanierManager;
import tn.esprit.myapplication.model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> productList;

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct;
        TextView textName, textPrice;
        Button buttonAdd;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);
            buttonAdd = itemView.findViewById(R.id.buttonAdd);
        }
    }

    public ProductAdapter(Context context, List<Product> list) {
        this.context = context;
        this.productList = list;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_product_card, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.imageProduct.setImageResource(product.getImageResId());
        holder.textName.setText(product.getName());
        holder.textPrice.setText(product.getPrice() + " DT");

        holder.buttonAdd.setOnClickListener(v -> {
            PanierManager.getInstance().ajouterProduit(product);
            Toast.makeText(context, product.getName() + " ajouté au panier", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}


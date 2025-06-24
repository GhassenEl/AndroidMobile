package tn.esprit.myapplication.Controller;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tn.esprit.myapplication.R;
import tn.esprit.myapplication.adapter.ProductAdapter;
import tn.esprit.myapplication.model.Product;

public class SelectProductActivity extends AppCompatActivity {

    private RecyclerView recyclerProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_product);
        Button buttonVoirPanier = findViewById(R.id.buttonVoirPanier);
        buttonVoirPanier.setOnClickListener(v -> {
            Intent intent = new Intent(SelectProductActivity.this, CartActivity.class);
            startActivity(intent);
        });

        recyclerProducts = findViewById(R.id.recyclerProducts);
        recyclerProducts.setLayoutManager(new GridLayoutManager(this, 2));

        List<Product> products = new ArrayList<>();
        products.add(new Product("Pizza Margherita", 12.0, R.drawable.plat1));
        products.add(new Product("Spaghetti Bolo", 14.5, R.drawable.plat2));
        products.add(new Product("Burger Maison", 11.0, R.drawable.plat3));
        products.add(new Product("Salade César", 9.5, R.drawable.plat4));
        products.add(new Product("Tacos Poulet", 10.0, R.drawable.plat5));
        products.add(new Product("Couscous Royal", 15.0, R.drawable.plat6));

        ProductAdapter adapter = new ProductAdapter(this, products);
        recyclerProducts.setAdapter(adapter);
    }
}


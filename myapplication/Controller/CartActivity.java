package tn.esprit.myapplication.Controller;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tn.esprit.myapplication.R;
import tn.esprit.myapplication.adapter.CartAdapter;
import tn.esprit.myapplication.model.PanierManager;
import tn.esprit.myapplication.model.Product;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerCart;
    private TextView textTotal;
    private Button buttonPayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerCart = findViewById(R.id.recyclerCart);
        textTotal = findViewById(R.id.textTotal);
        buttonPayer = findViewById(R.id.buttonPayer);

        List<Product> produitsPanier = PanierManager.getInstance().getProduits();
        recyclerCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerCart.setAdapter(new CartAdapter(this, produitsPanier));

        double total = PanierManager.getInstance().getTotal();
        textTotal.setText("Total: " + String.format("%.2f", total) + " DT");

        buttonPayer.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, StripeWebViewActivity.class);
            startActivity(intent);
        });
    }
}


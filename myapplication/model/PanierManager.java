package tn.esprit.myapplication.model;


import java.util.ArrayList;
import java.util.List;

public class PanierManager {

    private static PanierManager instance;
    private final List<Product> panier;

    private PanierManager() {
        panier = new ArrayList<>();
    }

    public static PanierManager getInstance() {
        if (instance == null) {
            instance = new PanierManager();
        }
        return instance;
    }

    public void ajouterProduit(Product product) {
        panier.add(product);
    }

    public void viderPanier() {
        panier.clear();
    }

    public List<Product> getProduits() {
        return new ArrayList<>(panier); // retourne une copie
    }

    public double getTotal() {
        double total = 0;
        for (Product p : panier) {
            total += p.getPrice();
        }
        return total;
    }

    public int getNombreArticles() {
        return panier.size();
    }
}


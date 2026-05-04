package main.model;

import java.util.List;

@Entity
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer clientId;
    
    private List<Produit> produits;

    private double total;

    public Commande() {
    }

    public Commande(Integer id, Integer clientId, List<Produit> produits, double total) {
        this.id = id;
        this.clientId = clientId;
        this.produits = produits;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public List<Produit> getproduits() {
        return produits;
    }

    public void setproduits(List<Produit> produits) {
        this.produits = produits;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
}

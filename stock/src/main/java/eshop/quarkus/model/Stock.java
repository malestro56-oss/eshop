package eshop.quarkus.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Stock {

    @Id
    public Long produitId;

    public int quantite;

     public Stock(Long produitId, int quantite) {
        this.produitId = produitId;
        this.quantite = quantite;
    }

     public Long getProduitId() {
         return produitId;
     }

     public void setProduitId(Long produitId) {
         this.produitId = produitId;
     }

     public int getQuantite() {
         return quantite;
     }

     public void setQuantite(int quantite) {
         this.quantite = quantite;
     }

    
}

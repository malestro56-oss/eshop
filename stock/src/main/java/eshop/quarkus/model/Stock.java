package eshop.quarkus.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Stock {

    @Id
    public int produitId;

    public int quantite;
}

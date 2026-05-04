package formation.sopra.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = true)
    private String nom;

    @Column(nullable = true)
    private String code;

    @Column(nullable = true)
    private BigDecimal prix;

    public Produit() {
    }

    public Produit(Integer id, String nom, String code, BigDecimal prix) {
        this.id = id;
        this.nom = nom;
        this.code = code;
        this.prix = prix;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }
}

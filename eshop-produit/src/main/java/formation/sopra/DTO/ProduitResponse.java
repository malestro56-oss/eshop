package formation.sopra.DTO;

import formation.sopra.model.Produit;
import java.math.BigDecimal;

public record ProduitResponse(Integer id, String nom, String code, BigDecimal prix) {

    public static ProduitResponse convert(Produit produit) {
        return new ProduitResponse(
                produit.getId(),
                produit.getNom(),
                produit.getCode(),
                produit.getPrix()
        );
    }
}
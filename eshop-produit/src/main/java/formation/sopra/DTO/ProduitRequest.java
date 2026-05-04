package formation.sopra.DTO;

import java.math.BigDecimal;

public record ProduitRequest(String nom, String code, BigDecimal prix) {
}
package fr.formation.api.dto.response;

import java.math.BigDecimal;

public record ProduitResponse (Integer id, String nom, BigDecimal prix) {
    
}

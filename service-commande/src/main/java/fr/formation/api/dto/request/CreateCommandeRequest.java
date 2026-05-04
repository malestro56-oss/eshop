package fr.formation.api.dto.request;

import java.util.List;

public record CreateCommandeRequest (Integer clientId, List<ProduitRequest> produits) {
    
}

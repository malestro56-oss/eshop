package fr.formation.servicecommande.api.dto.request;

import fr.formation.servicecommande.model.Produit;

import java.util.List;

public record CreateCommandeRequest (Integer clientId, List<ProduitRequest> produits) {
    
}

package main.api.dto.request;

public record CreateCommandeRequest (Integer clientId, List<Produit> produits, double total) {
    
}

package fr.formation.api.dto.response;

import fr.formation.model.Produit;

import java.util.List;

public record CommandeResponseDTO(
        Integer id,
        String nomClient,
        List<Produit> produits,
        double total) {
}
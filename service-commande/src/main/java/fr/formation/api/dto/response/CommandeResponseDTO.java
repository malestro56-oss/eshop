package fr.formation.servicecommande.api.dto.response;

import fr.formation.servicecommande.model.Produit;

import java.util.List;

public record CommandeResponseDTO(
                Integer id,
                String nomClient,
                List<Produit> produits,
                double total) {
}
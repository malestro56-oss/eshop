package fr.formation.api.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.formation.api.dto.request.ProduitRequest;
import fr.formation.api.dto.request.StockRequest;
import fr.formation.api.dto.response.CommandeResponseDTO;
import fr.formation.api.dto.response.ProduitResponse;
import fr.formation.model.Commande;
import fr.formation.repo.CommandeRepository;
import fr.formation.rest.produitrest.ProduitRest;
import fr.formation.rest.stockrest.StockRest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/api/commande")
public class CommandeResource {
    private static Logger log = LoggerFactory.getLogger(CommandeResource.class);

    @RestClient
    @Inject
    private ClientRest clientRest;

    @RestClient
    @Inject
    private ProduitRest produitRest;

    @RestClient
    @Inject
    private StockRest stockRest;

    @Inject
    private CommandeRepository repository;

    @GET
    @Path("/is-deletable/{clientId}")
    public boolean isDeletable(@PathParam("clientId") String clientId) {
        List<Integer> commandes = this.repository.findCommandeByClientId(clientId);
        if (commandes.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    @GET
    public List<CommandeResponseDTO> recupererToutesLesCommandes() {
        return repository.listAll().stream()
                .map(commande -> {
                    String nomClient = clientRest.getNomClient(commande.getClientId());

                    double prixTotal = commande.getProduits().stream()
                            .map(produit -> produit.prix().doubleValue())
                            .reduce(0.0, Double::sum);

                    return new CommandeResponseDTO(
                            commande.getId(),
                            nomClient,
                            commande.getProduits(),
                            prixTotal);
                })
                .collect(Collectors.toList());
    }

    @POST
    @Transactional
    public Commande ajouterCommande(fr.formation.servicecommande.api.dto.request.CreateCommandeRequest request) {

        // verifier que le client existe
        String nomClient = clientRest.getNomClient(request.clientId());

        if (nomClient.isBlank()) {
            return null;
        }

        Commande commande = new Commande();

        commande.setClientId(request.clientId());
        commande.setTotal(0);
        commande.setProduits(new ArrayList<>());

        double prixTotal = 0;
        // on fait une boucle pour parcourir la liste de produit et faire des verifs
        for (ProduitRequest produit : request.produits()) {

            // Verif que le produit existe
            ProduitResponse produitBdd = this.produitRest.prixByNom(produit.nom());

            if (produitBdd == null) {
                continue;
            }

            // verifier que le stock est suffisant pour commander

            if (!stockRest.isDisponible(produitBdd.id(), produit.quantite())) {

                continue;
            }

            StockRequest stockRequest = new StockRequest(produitBdd.id(), produit.quantite());

            stockRest.modifierStock(stockRequest);

            // calculer le prix total
            double total = produitBdd.getPrix() * produit.quantite();

            prixTotal += total;
            // creer la commande
        }

        commande.setTotal(prixTotal);
        repository.persist(commande);
        return commande;
    }

}

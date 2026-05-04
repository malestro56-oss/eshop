package fr.formation.servicecommande.api.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.LoggerFactory;

import fr.formation.servicecommande.api.dto.response.CommandeResponseDTO;
import fr.formation.servicecommande.repo.CommandeRepository;
import fr.formation.servicecommande.rest.clientrest.ClientRest;
import fr.formation.servicecommande.rest.produitrest.ProduitRest;
import fr.formation.servicecommande.rest.stockrest.StockRest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import main.api.dto.request.CreateCommandeRequest;
import main.api.dto.request.ProduitRequest;
import main.java.fr.formation.api.dto.response.ProduitResponse;
import main.model.Commande;
import main.model.Produit;

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

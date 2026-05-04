package fr.formation.servicecommande.api.dto;

import fr.formation.servicecommande.repo.CommandeRepository;
import fr.formation.servicecommande.rest.clientrest.ClientRest;
import fr.formation.servicecommande.rest.produitrest.ProduitRest;
import fr.formation.servicecommande.rest.stockrest.StockRest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

}

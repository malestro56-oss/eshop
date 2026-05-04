package fr.formation.servicecommande.api.dto;

import fr.formation.servicecommande.repo.CommandeRepository;
import fr.formation.servicecommande.rest.ClientRest;
import fr.formation.servicecommande.rest.ProduitRest;
import fr.formation.servicecommande.rest.StockRest;

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

}

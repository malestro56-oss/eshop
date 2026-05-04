package fr.formation.servicecommande.api.dto;

import java.util.Map;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.LoggerFactory;

import fr.formation.servicecommande.repo.CommandeRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import main.api.dto.request.CreateCommandeRequest;
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

    @POST 
    @Transactional
    public commande ajouterCommande(CreateCommandeRequest request){
        //Verif que le produit existe
        try{

            boolean exist = this.produitRest.exist(request.produitId());

            if (!exist) {
                return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("produit introuvable"))
                .build();
            }

           //verifier que le client existe
           
           boolean clientExist = clientRest.exist(request.clientId());

            if (!clientExist) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("Client introuvable"))
                    .build();
        }

        //verifier que le stock est suffisant pour commander

        Produit produit = produitService.findById(request.produitId());

        if (produit.getStock() < request.quantite()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Stock insuffisant"))
                    .build();
    }


    //calculer le prix total
    double total = produit.getPrix() * request.quantite();


            catch (WebApplicationException ex) {
            if (ex.getResponse().getStatus() == 404) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
}
            //creer la commande

            Commande commande = new Commande();
            commande.setClientId(request.clientId());
            commande.setproduits();
            commande.setTotal();
            commandeRepository.persist(commande);


            return Response.status(Response.Status.CREATED)
            .entity(commande)
            .build();
            //diminuer le stock en fonction de la commande
}
    }
}

package eshop.quarkus.resource;

import eshop.quarkus.dto.StockRequest;
import eshop.quarkus.dto.StockResponseDispo;
import eshop.quarkus.service.StockService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/api/stock")
public class StockResource {

    @Inject
    StockService stockService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public void  ajouterStock(StockRequest request) {
        
        stockService.ajouterStock(request.produitId, request.quantite);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/remove")
    public void  supprimerStock(StockRequest request) {
        
        stockService.retraitProduit(request.produitId, request.quantite);
    }

    @GET
    @Path("/dispo/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public StockResponseDispo isDisponible(@PathParam("id") Long id,@QueryParam("quantite") int quantite) {

        StockResponseDispo response = new StockResponseDispo();

        response.produitId = id;
        response.quantiteDemandee = quantite;        
        response.disponible= stockService.isDisponible(id, quantite);

        return response;
    }
}

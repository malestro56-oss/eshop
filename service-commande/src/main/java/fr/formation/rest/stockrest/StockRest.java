package fr.formation.servicecommande.rest.stockrest;

@Path("/api/stock")
@RegisterRestClient(configKey="stock-service")
public interface StockRest {
@GET
@Path("/dispo/{id}")
public boolean isDisponible(@PathParam("id") Integer id, @QueryParam("quantite") int quantite);

@POST
@Path("/remove")
public void modifierStock(StockRequest request);
}

package fr.formation.rest.stockrest;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import fr.formation.api.dto.request.StockRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

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

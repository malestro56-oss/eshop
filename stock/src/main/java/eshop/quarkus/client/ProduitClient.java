package eshop.quarkus.client;



import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import eshop.quarkus.dto.ProduitDTO;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@RegisterRestClient(configKey = "produit-api")
@Path("/api/produit")
public interface ProduitClient {

    @GET
    @Path("/{id}")
    ProduitDTO getById(@PathParam("id") Long id);

}

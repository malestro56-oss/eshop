

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@RegisterRestClient(configKey = "produit-api")
@Path("/api/produit")
public class ProduitClient {

    @GET
    @Path("/{id}")
    Produit getById(@PathParam("id") int id);

}

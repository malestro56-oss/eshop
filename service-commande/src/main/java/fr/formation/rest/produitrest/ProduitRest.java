package fr.formation.rest.produitrest;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import fr.formation.api.dto.response.ProduitResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/api/produit")
@RegisterRestClient(configKey="produit-service")
public interface ProduitRest {
@GET
@Path("/prix-nom/{nom}")
public ProduitResponse prixByNom(@PathParam("nom") String nom);
}

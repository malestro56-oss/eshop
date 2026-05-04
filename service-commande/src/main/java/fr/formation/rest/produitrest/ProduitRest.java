package fr.formation.servicecommande.rest.produitrest;


import java.math.BigDecimal;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/produit")
@RegisterRestClient(configKey="produit-service")
public interface ProduitRest {
@GET
@Path("/prix-nom/{nom}")
public ProduitResponse prixByNom(@PathParam("nom") String nom);
}

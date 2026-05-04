package fr.formation.servicecommande.rest.clientrest;

@Path("/client")
@RegisterRestClient(configKey = "client-service")
public interface ClientRest {

    @GET
    @Path("/{id}")
    String getNomClient(@PathParam("id") Integer id);
}

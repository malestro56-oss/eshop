package fr.formation.rest.clientrest;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/client")
@RegisterRestClient(configKey = "client-service")
public interface ClientRest {

    @GET
    @Path("/{id}")
    String getNomClient(@PathParam("id") Integer id);
}

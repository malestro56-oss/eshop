package fr.formation;

import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import fr.formation.dto.request.ClientRequest;
import fr.formation.dto.response.ClientResponse;
import fr.formation.dto.response.ClientWithDetailsResponse;
import fr.formation.model.Client;
import fr.formation.repo.ClientRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@Path("/api/client")
public class ClientResource {

	@RestClient
	@Inject
	private CommandeClientRest commandeClientRest;
	@Inject
	private ClientRepository clientrepository;



	@GET
	public List<ClientResponse> findAll() {
		return this.clientrepository.findAll().stream().map(ClientResponse::convert).toList();
	}

	@GET
	@Path("/details")
	public List<ClientWithDetailsResponse> findAllWithDetails() {
		return this.clientrepository.findAll().stream().map(ClientWithDetailsResponse::convert).toList();
	}

	@GET
	@Path("/{id}")
	public ClientResponse findById (@PathParam("id") String id) {
		return ClientResponse.convert(this.clientrepository.findByIdOptional(id).orElseThrow(NotFoundException::new));
	}

	@GET
	@Path("/{id}/details")
	public ClientWithDetailsResponse findByIdWithDetails (@PathParam("id") String id) {
		return ClientWithDetailsResponse.convert(this.clientrepository.findByIdOptional(id).orElseThrow(NotFoundException::new));
	}

	@Transactional
	@POST
	public Response create (@Valid ClientRequest request) {
		Client client = new Client();

		client.setNom(request.getNom());
		client.setPrenom(request.getPrenom());

		this.clientrepository.persist(client);

		return Response.status(Response.Status.CREATED)
				.entity(client.getId())
				.build()
				;
	}


	@PUT
	@Transactional
	@Path("/{id}")
	public Response update(@PathParam("id") String id, @Valid ClientRequest request) {


		Client client = this.clientrepository.findByIdOptional(id).orElseThrow(NotFoundException::new);

		client.setPrenom(request.getPrenom());
		client.setNom(request.getNom());

		this.clientrepository.persist(client);

		return Response.ok(client.getId()).build();
	}

	@Transactional
	@DELETE
	@Path("/{id}")
	public Response deleteById (@PathParam("id") String id) {
		try {
			boolean isDeletable = this.commandeClientRest.isDeletable(id);

			if (!isDeletable) {

				return Response.status(Response.Status.FORBIDDEN)
						.entity(Map.of("deletable", false))
						.build()
						;
			}

		}
		catch (WebApplicationException ex) {
			if (ex.getResponse().getStatus() == 404) {

				return Response.status(Response.Status.NOT_FOUND).build();
			}

			else {

				return Response.status(Response.Status.BAD_REQUEST)
						.entity(
								Map.of(
										"error", ex.getMessage(),
										"status", ex.getResponse().getStatus()
										)
								)
						.build()
						;
			}
		}
		this.clientrepository.deleteById(id);
		return Response.noContent().build();
	}
}


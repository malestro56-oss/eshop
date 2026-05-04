package formation.sopra.controller;

import formation.sopra.DAO.DAOProduit;
import formation.sopra.DTO.ProduitRequest;
import formation.sopra.DTO.ProduitResponse;
import formation.sopra.model.Produit;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Path("/api/produit")
public class ProduitController {

    private final DAOProduit daoProduit;

    public ProduitController(DAOProduit daoProduit) {
        this.daoProduit = daoProduit;
    }

    @Transactional
    @POST
    public Response createProduit(ProduitRequest request){
        Produit produit = new Produit();
        produit.setCode(request.code());
        produit.setNom(request.nom());
        produit.setPrix(request.prix());
        this.daoProduit.persist(produit);
        return Response
                .status(Response.Status.CREATED)
                .entity(Map.of(
                        "id",produit.getId()
                ))
                .build();
        //return ProduitResponse.convert(produit);
    }

    @GET
    @Path("/{id}")
    public Response getProduitById(@PathParam("id") int id){
        //return this.daoProduit.findByIdOptional(id).map(ProduitResponse::convert).orElseThrow(()->new RuntimeException("Produit not found"));
        ProduitResponse produit = this.daoProduit.findByIdOptional(id).map(ProduitResponse::convert).orElseThrow(()->new NotFoundException("Produit not found"));
        return Response
                .status(Response.Status.OK)
                .entity(produit)
                .build();
    }

    @Path("/prix-nom/{nom}")
    @GET
    public ProduitResponse getProduitByNom(@PathParam("nom") String nom){
        return this.daoProduit.findByNom(nom).map(ProduitResponse::convert).orElse(null);
    }

    @GET
    public Response getAllProduit(){
        //return this.daoProduit.findAll().stream().map(ProduitResponse::convert).toList();
        List<ProduitResponse> produits = this.daoProduit.findAll().stream().map(ProduitResponse::convert).toList();
        return Response
                .status(Response.Status.OK)
                .entity(produits)
                .build();
    }

    @Transactional
    @PUT
    @Path("/{id}")
    public ProduitResponse updateProduit(@PathParam("id") int id, ProduitRequest request){
        Produit produit = this.daoProduit.findByIdOptional(id).orElseThrow(()->new RuntimeException("Produit not found"));
        produit.setPrix(request.prix());
        produit.setNom(request.nom());
        produit.setCode(request.code());
        this.daoProduit.persist(produit);
        return ProduitResponse.convert(produit);
    }

    @Transactional
    @DELETE
    @Path("/{id}")
    public int deleteById(@PathParam("id") int id){
        Produit produit = this.daoProduit.findById(id);
        produit.setNom(null);
        produit.setCode(null);
        produit.setPrix(null);
        this.daoProduit.persist(produit);
        return id;
    }


}

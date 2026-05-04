package formation.sopra.controller;

import formation.sopra.DAO.DAOProduit;
import formation.sopra.DTO.ProduitRequest;
import formation.sopra.DTO.ProduitResponse;
import formation.sopra.model.Produit;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;

import java.util.List;

@Path("/api/produit")
public class ProduitController {

    private final DAOProduit daoProduit;

    public ProduitController(DAOProduit daoProduit) {
        this.daoProduit = daoProduit;
    }

    @Transactional
    @POST
    public ProduitResponse createProduit(ProduitRequest request){
        Produit produit = new Produit();
        produit.setCode(request.code());
        produit.setNom(request.nom());
        produit.setPrix(request.prix());
        this.daoProduit.persist(produit);
        return ProduitResponse.convert(produit);
    }

    @GET
    @Path("/{id}")
    public ProduitResponse getProduitById(@PathParam("id") int id){
        return this.daoProduit.findByIdOptional(id).map(ProduitResponse::convert).orElseThrow(()->new RuntimeException("Produit not found"));
    }

    @GET
    public List<ProduitResponse> getAllProduit(){
        return this.daoProduit.findAll().stream().map(ProduitResponse::convert).toList();
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

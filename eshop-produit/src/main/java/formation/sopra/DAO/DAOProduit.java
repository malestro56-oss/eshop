package formation.sopra.DAO;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import formation.sopra.model.Produit;

import java.util.Optional;

@ApplicationScoped
public class DAOProduit implements PanacheRepositoryBase<Produit, Integer> {
    public Optional<Produit> findByNom(String nom){
        return find("nom", nom).firstResultOptional();
    }
}

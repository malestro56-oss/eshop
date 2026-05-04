package formation.sopra.DAO;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import formation.sopra.model.Produit;

@ApplicationScoped
public class DAOProduit implements PanacheRepositoryBase<Produit, Integer> {
}

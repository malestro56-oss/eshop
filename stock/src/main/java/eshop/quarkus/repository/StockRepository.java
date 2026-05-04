
import java.util.Optional;

import eshop.quarkus.model.Stock;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StockRepository implements PanacheRepository<Stock> {


    public Optional<Stock> findByProduitId(int produitId) {
        return find("produitId", produitId).firstResultOptional();
    }
}

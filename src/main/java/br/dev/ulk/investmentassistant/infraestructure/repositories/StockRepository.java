package br.dev.ulk.investmentassistant.infraestructure.repositories;

import br.dev.ulk.investmentassistant.domain.models.Stock;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {

    Optional<Stock> findByTicker(String ticker);

}
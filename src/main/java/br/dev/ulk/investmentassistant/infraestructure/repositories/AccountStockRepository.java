package br.dev.ulk.investmentassistant.infraestructure.repositories;

import br.dev.ulk.investmentassistant.domain.models.AccountStock;
import br.dev.ulk.investmentassistant.domain.models.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {

}
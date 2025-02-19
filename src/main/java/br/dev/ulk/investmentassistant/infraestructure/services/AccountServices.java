package br.dev.ulk.investmentassistant.infraestructure.services;

import br.dev.ulk.investmentassistant.application.controllers.v1.payloads.AccountStockDTO;
import br.dev.ulk.investmentassistant.application.controllers.v1.payloads.AccountStockResponseDTO;
import br.dev.ulk.investmentassistant.domain.models.Account;
import br.dev.ulk.investmentassistant.domain.models.AccountStock;
import br.dev.ulk.investmentassistant.domain.models.AccountStockId;
import br.dev.ulk.investmentassistant.domain.models.Stock;
import br.dev.ulk.investmentassistant.infraestructure.repositories.AccountRepository;
import br.dev.ulk.investmentassistant.infraestructure.repositories.AccountStockRepository;
import br.dev.ulk.investmentassistant.infraestructure.repositories.StockRepository;
import br.dev.ulk.investmentassistant.utils.brapi.BrapiService;
import br.dev.ulk.investmentassistant.utils.brapi.payloads.BrapiResponseDTO;
import br.dev.ulk.investmentassistant.utils.brapi.payloads.StockDTO;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountServices {

    private AccountRepository accountRepository;
    private StockRepository stockRepository;
    private AccountStockRepository accountStockRepository;
    private BrapiService brapiService;

    @Value("#{environment.TOKEN}")
    private String token;

    public AccountServices(
        AccountRepository accountRepository, StockRepository stockRepository,
        AccountStockRepository accountStockRepository,
        BrapiService brapiService) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
        this.brapiService = brapiService;
    }

    public void associateStock(String accountId, AccountStockDTO accountStockDTO) {
        Account account = accountRepository.findById(UUID.fromString(accountId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Stock stock = stockRepository.findByTicker(accountStockDTO.getTicker())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        AccountStockId id = new AccountStockId(account.getId(), stock.getId());

        AccountStock accountStock = AccountStock.builder()
            .id(id)
            .account(account)
            .stock(stock)
            .quantity(accountStockDTO.getQuantity())
            .build();

        accountStockRepository.save(accountStock);
    }

    public List<AccountStockResponseDTO> listStocks(String accountId) {

        Account account = accountRepository.findById(UUID.fromString(accountId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountStocks()
            .stream()
            .map(as -> new AccountStockResponseDTO(
                as.getStock().getTicker(),
                as.getQuantity(),
                getTotal(as.getQuantity(), as.getStock().getTicker())))
            .toList();
    }

    private Double getTotal(Integer quantity, String ticker) {
        BrapiResponseDTO response =  brapiService.getQuote(token, ticker);
        Double price = response.getResults().getFirst().getRegularMarketPrice();
        return quantity * price;
    }
}
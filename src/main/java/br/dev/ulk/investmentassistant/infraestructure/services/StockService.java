package br.dev.ulk.investmentassistant.infraestructure.services;

import br.dev.ulk.investmentassistant.application.controllers.v1.payloads.CreateStockDTO;
import br.dev.ulk.investmentassistant.domain.models.Stock;
import br.dev.ulk.investmentassistant.infraestructure.repositories.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(
        StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDTO createStockDto) {

        Stock stock = Stock.builder()
            .ticker(createStockDto.getTicker())
            .description(createStockDto.getDescription())
            .build();

        stockRepository.save(stock);
    }
}
package br.dev.ulk.investmentassistant.application.controllers.v1;

import br.dev.ulk.investmentassistant.application.controllers.v1.payloads.CreateStockDTO;
import br.dev.ulk.investmentassistant.infraestructure.services.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/stocks")
public class StockController {

    private StockService stockService;

    public StockController(
        StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<Void> createStock(@RequestBody CreateStockDTO createStockDto) {
        stockService.createStock(createStockDto);
        return ResponseEntity.ok().build();
    }

}
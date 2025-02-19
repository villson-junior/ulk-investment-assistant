package br.dev.ulk.investmentassistant.application.controllers.v1;

import br.dev.ulk.investmentassistant.application.controllers.v1.payloads.AccountStockDTO;
import br.dev.ulk.investmentassistant.application.controllers.v1.payloads.AccountStockResponseDTO;
import br.dev.ulk.investmentassistant.infraestructure.services.AccountServices;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/accounts")
public class AccountController {

    private AccountServices accountServices;

    public AccountController(AccountServices accountServices) {
        this.accountServices = accountServices;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId,
        @RequestBody AccountStockDTO accountStockDTO) {

        accountServices.associateStock(accountId, accountStockDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDTO>> associateStock(
        @PathVariable("accountId") String accountId) {
        List<AccountStockResponseDTO> stocks = accountServices.listStocks(accountId);
        return ResponseEntity.ok(stocks);
    }
}
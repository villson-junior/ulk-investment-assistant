package br.dev.ulk.investmentassistant.utils.brapi;

import br.dev.ulk.investmentassistant.utils.brapi.payloads.BrapiResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "BrapiClient",
    url = "https://brapi.dev/"
)
public interface BrapiService {

    @GetMapping("/api/quote/{ticker}")
    BrapiResponseDTO getQuote(@RequestParam("token") String token,
        @PathVariable("ticker") String ticker);

}
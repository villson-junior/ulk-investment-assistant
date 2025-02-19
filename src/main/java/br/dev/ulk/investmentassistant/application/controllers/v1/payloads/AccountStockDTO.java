package br.dev.ulk.investmentassistant.application.controllers.v1.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountStockDTO {

    private String ticker;
    private Integer quantity;

}
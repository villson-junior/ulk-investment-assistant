package br.dev.ulk.investmentassistant.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AccountStockId implements Serializable {

    @Column(name = "ACCOUNT_ID")
    private UUID accountId;

    @Column(name = "STOCK_ID")
    private UUID stockId;

}
package br.dev.ulk.investmentassistant.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
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
@Table(name = "ACCOUNT_STOCKS")
@Entity(name = "ACCOUNT_STOCK")
public class AccountStock {

    @EmbeddedId
    private AccountStockId id;

    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @ManyToOne
    @MapsId("stockId")
    @JoinColumn(name = "STOCK_ID")
    private Stock stock;

    @Column(name = "QUANTITY")
    private Integer quantity;

}
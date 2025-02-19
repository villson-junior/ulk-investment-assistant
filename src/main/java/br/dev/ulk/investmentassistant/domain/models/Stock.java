package br.dev.ulk.investmentassistant.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "STOCKS")
@Entity(name = "STOCK")
public class Stock extends BaseEntity {

    @Column(name = "TICKER")
    private String ticker;

    @Column(name = "DESCRIPTION")
    private String description;

}
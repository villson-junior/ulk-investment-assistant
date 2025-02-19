package br.dev.ulk.investmentassistant.domain.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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
@Table(name = "BILLING_ADDRESSES")
@Entity(name = "BILLING_ADDRESS")
public class BillingAddress extends BaseEntity{

    @Column(name = "STREET")
    private String street;

    @Column(name = "NUMBER")
    private Integer number;

    @MapsId
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

}
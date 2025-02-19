package br.dev.ulk.investmentassistant.utils.brapi.payloads;

import java.util.List;
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
public class BrapiResponseDTO {

    private List<StockDTO> results;

}
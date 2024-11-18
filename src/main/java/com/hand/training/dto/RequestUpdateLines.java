package com.hand.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestUpdateLines {
    private Long invoiceLineId;
    private String itemNumber;
    private String itemDescription;
    private Double unitPrice;
    private Double quantity;
    private String remark;
}

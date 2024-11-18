package com.hand.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestInvoiceLineForTheSakeEndPointOnly {
    private Long invoiceLineId;
    private Long invoiceHeaderId;
    private String itemNumber;
    private String itemDescription;
    private Double unitPrice;
    private Double quantity;
    private String remark;
}

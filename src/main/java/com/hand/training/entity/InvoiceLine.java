package com.hand.training.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceLine implements Serializable {
    private Long invoiceLineId;
    private Long invoiceHeaderId;
    private String itemNumber;
    private String itemDescription;
    private Double unitPrice;
    private Double quantity;
    private Double totalAmount;
    private String remark;
    private LocalDateTime creationDate;
    private Long createdBy;
    private Long lastUpdatedBy;
    private LocalDateTime lastUpdateDate;
}

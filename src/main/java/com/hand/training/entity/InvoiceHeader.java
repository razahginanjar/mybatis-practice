package com.hand.training.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceHeader {
    private Long invoiceHeaderId;
    private String invoiceNumber;
    private String status;
    private String invoiceType;
    private Double totalAmount;
    private Integer delFlag;
    private String remark;
    private LocalDateTime creationDate;
    private Long createdBy;
    private Long lastUpdatedBy;
    private LocalDateTime lastUpdateDate;
}

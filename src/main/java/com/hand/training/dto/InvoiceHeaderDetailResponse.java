package com.hand.training.dto;

import com.hand.training.entity.InvoiceLine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceHeaderDetailResponse {
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
    private List<InvoiceLine> listLine;
}
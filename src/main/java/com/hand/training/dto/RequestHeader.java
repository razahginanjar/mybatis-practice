package com.hand.training.dto;

import com.hand.training.constant.InvoiceType;
import com.hand.training.constant.StatusHeader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestHeader {
    private Long invoiceHeaderId;
    private StatusHeader status;
    private InvoiceType invoiceType;
    private Double totalAmount;
    private Integer delFlag;
    private String remark;
    List<RequestInvoiceLine> listLines;
}

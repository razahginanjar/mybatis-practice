package com.hand.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestHeaderUpdate {
    private Long invoiceHeaderId;
    private String invoiceNumber;
    private String status;
    private String invoiceType;
    private Double totalAmount;
    private Integer delFlag;
    private String remark;
    private LocalDateTime lastUpdateDate;
    private List<RequestUpdateLines> lines;
}

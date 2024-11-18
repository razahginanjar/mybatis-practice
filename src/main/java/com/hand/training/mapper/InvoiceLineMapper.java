package com.hand.training.mapper;

import com.hand.training.entity.InvoiceHeader;
import com.hand.training.entity.InvoiceLine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InvoiceLineMapper {
    List<InvoiceLine> getInvoiceLineQuantityLikeInList(
            @Param(value = "limit") Integer limit,
            @Param(value = "offset") Integer offset,
            @Param(value = "quantities") List<Double> quantities);
    List<InvoiceLine> getLineFromHeaderId(
            @Param(value = "id") Long id
    );
    void deleteByHeaderId(@Param(value = "idHeaderList") List<Long> idHeaderList);
    void insertingInvoiceLine(InvoiceLine invoiceLine);
    InvoiceLine getLinesForReturn(InvoiceLine invoiceline);
    void updateLine(InvoiceLine invoiceLine);
    InvoiceLine getById(Long id);
}

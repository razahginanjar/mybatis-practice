package com.hand.training.mapper;

import com.hand.training.entity.InvoiceHeader;
import com.hand.training.entity.InvoiceLine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//@Mapper
public interface ExampleMapper {
    List<InvoiceLine> getInvoiceLineQuantityLikeInList(
            @Param(value = "limit") Integer limit,
            @Param(value = "offset") Integer offset,
            @Param(value = "quantities") List<Double> quantities);

    List<InvoiceHeader> getInvoiceHeaderInvoiceTYpeLikeInList(
            @Param(value = "limit") Integer limit,
            @Param(value = "offset") Integer offset,
            @Param(value = "types") List<String> invoiceType);
}

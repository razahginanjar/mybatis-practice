package com.hand.training.mapper;

import com.hand.training.entity.InvoiceHeader;
import com.hand.training.entity.InvoiceLine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface InvoiceHeaderMapper {
    List<InvoiceHeader> getInvoiceHeaderInvoiceTYpeLikeInList(
            @Param(value = "limit") Integer limit,
            @Param(value = "offset") Integer offset,
            @Param(value = "types") List<String> invoiceType);

    InvoiceHeader getInvoiceById(
            @Param(value = "ihId") Long ihId
    );
    void updateDlFlag(@Param(value = "list") List<Long> idList);
    void insertInvoiceHeader(InvoiceHeader invoiceHeader);
    InvoiceHeader getHeaderForReturn(InvoiceHeader invoiceHeader);
    void updateHeader(InvoiceHeader invoiceHeader);
    void updateAmount(@Param(value = "totalAmount") Double amount,
                      @Param(value = "id") Long id);
    void insertOrUpdateInvoiceHeader(InvoiceHeader invoiceHeader);

}

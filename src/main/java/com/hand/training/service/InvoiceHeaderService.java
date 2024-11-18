package com.hand.training.service;

import com.hand.training.constant.InvoiceType;
import com.hand.training.dto.RequestHeader;
import com.hand.training.dto.RequestHeaderUpdate;
import com.hand.training.dto.ResponseHeader;
import com.hand.training.entity.InvoiceHeader;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface InvoiceHeaderService {
    List<InvoiceHeader> getListInvoiceHeaderThatIncluded(Integer limit, Integer offset, List<InvoiceType> invoiceType);
    InvoiceHeader getHeaderById(Long id);
    void updateDlFlag(List<Long> idHeader);
    InvoiceHeader createInvoiceHeader(InvoiceHeader invoiceHeader);
    InvoiceHeader getForReturn(InvoiceHeader invoiceHeader);
    InvoiceHeader updateInvoiceHeader(InvoiceHeader invoiceHeader);
    List<InvoiceHeader> createFromList(List<InvoiceHeader> invoiceHeaderList);
    List<InvoiceHeader> updateFromList(List<InvoiceHeader> invoiceHeaderList);
    ResponseHeader createWithLines(RequestHeader invoiceHeader);
    ResponseHeader updateWithLines(RequestHeader invoiceHeader);
    ResponseHeader getDetail(Long id);
    InvoiceHeader insertOrUpdate(InvoiceHeader invoiceHeader);
    List<InvoiceHeader> insertOrUpdates(List<InvoiceHeader> invoiceHeaderList);
    List<ResponseHeader> insertOrUpdatesWithLines(List<RequestHeader> invoiceHeaders);
    ResponseHeader insertOrUpdateWithLine(RequestHeader invoiceHeader);
    void updateAmount(Long idHeader, Double amount);
}

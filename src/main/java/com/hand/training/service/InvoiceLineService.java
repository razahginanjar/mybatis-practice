package com.hand.training.service;

import com.hand.training.dto.RequestInvoiceLine;
import com.hand.training.dto.RequestInvoiceLineForTheSakeEndPointOnly;
import com.hand.training.dto.RequestUpdateLines;
import com.hand.training.entity.InvoiceLine;

import java.util.List;

public interface InvoiceLineService {
    List<InvoiceLine> getListInvoiceLineThatIncluded(List<Double> quantity, Integer limit, Integer offset);
    List<InvoiceLine> getLineFromHeaderId(Long Id);
    void deleteLineFromHeaderId(List<Long> Id);
    InvoiceLine saveInvoiceLine(InvoiceLine invoiceLine);
    InvoiceLine getForReturn(InvoiceLine invoiceLine);
    InvoiceLine updateLine(InvoiceLine invoiceLine);
    InvoiceLine getBYIdLine(Long Id);
    List<InvoiceLine> createdByList(List<InvoiceLine> invoiceLines);
    List<InvoiceLine> updateByList(List<InvoiceLine> invoiceLines);
    InvoiceLine createLine(RequestInvoiceLine invoiceLine, Long invoiceHeaderId);
    InvoiceLine updateLineWithHeader(RequestInvoiceLine invoiceLine, Long invoiceHeaderId);
    InvoiceLine insertOrUpdate(InvoiceLine invoiceLine);
    List<InvoiceLine> insertOrUpdate(List<InvoiceLine> invoiceLines);
    List<InvoiceLine> insertOrUpdateFilters(List<RequestInvoiceLine> lines,  Long idHeader);
    InvoiceLine InsertOrUpdateFilter(RequestInvoiceLine invoiceLine,  Long idHeader);
    List<InvoiceLine> insertOrUpdateJustForEndPoint(List<RequestInvoiceLineForTheSakeEndPointOnly> requests);
}

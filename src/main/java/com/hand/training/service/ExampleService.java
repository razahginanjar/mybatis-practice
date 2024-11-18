package com.hand.training.service;

import com.hand.training.entity.InvoiceHeader;
import com.hand.training.entity.InvoiceLine;

import java.util.List;

public interface ExampleService {
    List<InvoiceHeader> getListInvoiceHeaderThatIncluded(Integer limit, Integer offset, List<String> invoiceType);
    List<InvoiceLine> getListInvoiceLineThatIncluded(List<Double> quantity, Integer limit, Integer offset);
}

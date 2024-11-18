package com.hand.training.service.impl;

import com.hand.training.entity.InvoiceHeader;
import com.hand.training.entity.InvoiceLine;
import com.hand.training.mapper.ExampleMapper;
import com.hand.training.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author shaoqin.zhou@hand-china.com
 * @since 2024-11-13 23:14:49
 */
@Service
public class ExampleServiceImpl implements ExampleService {
    @Autowired
    private ExampleMapper exampleMapper;

    @Transactional(readOnly = true)
    @Override
    public List<InvoiceHeader> getListInvoiceHeaderThatIncluded(Integer limit, Integer offset, List<String> invoiceType) {
        return exampleMapper.getInvoiceHeaderInvoiceTYpeLikeInList(limit, offset, invoiceType);
    }

    @Transactional(readOnly = true)
    @Override
    public List<InvoiceLine> getListInvoiceLineThatIncluded(List<Double> quantity, Integer limit, Integer offset) {
        return exampleMapper.getInvoiceLineQuantityLikeInList(limit, offset, quantity);
    }
}

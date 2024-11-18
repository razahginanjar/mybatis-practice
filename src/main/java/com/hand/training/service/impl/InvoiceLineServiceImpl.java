package com.hand.training.service.impl;

import com.hand.training.dto.RequestInvoiceLine;
import com.hand.training.dto.RequestInvoiceLineForTheSakeEndPointOnly;
import com.hand.training.dto.RequestUpdateLines;
import com.hand.training.entity.InvoiceHeader;
import com.hand.training.entity.InvoiceLine;
import com.hand.training.mapper.InvoiceHeaderMapper;
import com.hand.training.mapper.InvoiceLineMapper;
import com.hand.training.service.InvoiceLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceLineServiceImpl implements InvoiceLineService {
    private final InvoiceLineMapper invoiceLineMapper;
    private final InvoiceHeaderMapper invoiceHeaderMapper;

    @Override
    public List<InvoiceLine> getListInvoiceLineThatIncluded(List<Double> quantity, Integer limit, Integer offset) {
        return invoiceLineMapper.getInvoiceLineQuantityLikeInList(limit, offset, quantity);
    }

    @Override
    public List<InvoiceLine> getLineFromHeaderId(Long Id) {
        return invoiceLineMapper.getLineFromHeaderId(Id);
    }

    @Override
    public void deleteLineFromHeaderId(List<Long> Id) {
        invoiceLineMapper.deleteByHeaderId(Id);
    }

    @Override
    public InvoiceLine saveInvoiceLine(InvoiceLine invoiceLine) {
        invoiceLineMapper.insertingInvoiceLine(invoiceLine);
        return getForReturn(invoiceLine);
    }

    @Override
    public InvoiceLine getForReturn(InvoiceLine invoiceLine) {
        return invoiceLineMapper.getLinesForReturn(invoiceLine);
    }

    @Override
    public InvoiceLine updateLine(InvoiceLine invoiceLine) {
        InvoiceLine byIdLine = getBYIdLine(invoiceLine.getInvoiceLineId());
        byIdLine.setRemark(invoiceLine.getRemark());
        byIdLine.setInvoiceHeaderId(invoiceLine.getInvoiceHeaderId());
        byIdLine.setItemDescription(invoiceLine.getItemDescription());
        byIdLine.setItemNumber(invoiceLine.getItemNumber());
        byIdLine.setQuantity(invoiceLine.getQuantity());
        byIdLine.setLastUpdateDate(LocalDateTime.now());
        byIdLine.setTotalAmount(invoiceLine.getTotalAmount());
        byIdLine.setUnitPrice(invoiceLine.getUnitPrice());
        invoiceLineMapper.updateLine(invoiceLine);
        return getForReturn(byIdLine);
    }

    @Override
    public InvoiceLine getBYIdLine(Long Id) {
        return invoiceLineMapper.getById(Id);
    }

    @Override
    public List<InvoiceLine> createdByList(List<InvoiceLine> invoiceLines) {
        return invoiceLines.stream().map(
                this::saveInvoiceLine
        ).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceLine> updateByList(List<InvoiceLine> invoiceLines) {
        return invoiceLines.stream().map(
                this::updateLine
        ).collect(Collectors.toList());
    }

    @Override
    public InvoiceLine createLine(RequestInvoiceLine invoiceLine, Long invoiceHeaderId) {
        InvoiceLine byIdLine = new InvoiceLine();
        byIdLine.setRemark(invoiceLine.getRemark());
        byIdLine.setInvoiceHeaderId(invoiceHeaderId);
        byIdLine.setItemDescription(invoiceLine.getItemDescription());
        byIdLine.setItemNumber(invoiceLine.getItemNumber());
        if(invoiceLine.getQuantity() == null || invoiceLine.getQuantity() <= 0)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
        if(invoiceLine.getUnitPrice() == null || invoiceLine.getUnitPrice() <= 0)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());

        }
        byIdLine.setQuantity(invoiceLine.getQuantity());
        byIdLine.setLastUpdateDate(LocalDateTime.now());
        byIdLine.setTotalAmount(invoiceLine.getQuantity() * invoiceLine.getUnitPrice());
        byIdLine.setUnitPrice(invoiceLine.getUnitPrice());
        return saveInvoiceLine(byIdLine);
    }

    @Override
    public InvoiceLine updateLineWithHeader(RequestInvoiceLine invoiceLine,
                                            Long invoiceHeaderId) {
        InvoiceLine byIdLine = getBYIdLine(invoiceLine.getInvoiceLineId());
        byIdLine.setRemark(invoiceLine.getRemark());
        byIdLine.setInvoiceHeaderId(invoiceHeaderId);
        byIdLine.setItemDescription(invoiceLine.getItemDescription());
        byIdLine.setItemNumber(invoiceLine.getItemNumber());
        if(invoiceLine.getQuantity() == null || invoiceLine.getQuantity() <= 0)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
        if(invoiceLine.getUnitPrice() == null || invoiceLine.getUnitPrice() <= 0)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());

        }
        byIdLine.setQuantity(invoiceLine.getQuantity());
        byIdLine.setLastUpdateDate(LocalDateTime.now());
        byIdLine.setTotalAmount(invoiceLine.getQuantity() * invoiceLine.getUnitPrice());
        byIdLine.setUnitPrice(invoiceLine.getUnitPrice());
        return updateLine(byIdLine);
    }

    @Override
    public InvoiceLine insertOrUpdate(InvoiceLine invoiceLine) {
        if(invoiceLine.getInvoiceLineId() == null || invoiceLine.getInvoiceLineId() == 0L)
        {
            saveInvoiceLine(invoiceLine);
        }else {
            updateLine(invoiceLine);
        }
        return getForReturn(invoiceLine);
    }

    @Override
    public List<InvoiceLine> insertOrUpdate(List<InvoiceLine> invoiceLines) {
        return invoiceLines.stream().map(
                this::insertOrUpdate
        ).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceLine> insertOrUpdateFilters(List<RequestInvoiceLine> lines,  Long idHeader) {
        return lines.stream().map(
                requestInvoiceLine -> InsertOrUpdateFilter(requestInvoiceLine, idHeader)
        ).collect(Collectors.toList());
    }

    @Override
    public InvoiceLine InsertOrUpdateFilter(RequestInvoiceLine invoiceLine,  Long idHeader) {
        InvoiceLine line = new InvoiceLine();
        if(invoiceLine.getInvoiceLineId() == null || invoiceLine.getInvoiceLineId() == 0L)
        {
            line = createLine(invoiceLine, idHeader);
        }else {
            line = updateLineWithHeader(invoiceLine, idHeader);
        }
        return line;
    }

    @Override
    public List<InvoiceLine> insertOrUpdateJustForEndPoint(List<RequestInvoiceLineForTheSakeEndPointOnly> requests) {
        return requests.stream().map(
                requestInvoiceLineForTheSakeEndPointOnly ->
                {
                    RequestInvoiceLine build = RequestInvoiceLine.builder()
                            .invoiceLineId(requestInvoiceLineForTheSakeEndPointOnly.getInvoiceLineId())
                            .quantity(requestInvoiceLineForTheSakeEndPointOnly.getQuantity())
                            .itemDescription(requestInvoiceLineForTheSakeEndPointOnly.getItemDescription())
                            .remark(requestInvoiceLineForTheSakeEndPointOnly.getRemark())
                            .itemNumber(requestInvoiceLineForTheSakeEndPointOnly.getItemNumber())
                            .unitPrice(requestInvoiceLineForTheSakeEndPointOnly.getUnitPrice()).build();

                    updateAmountTotalInHeader(requestInvoiceLineForTheSakeEndPointOnly);
                    return InsertOrUpdateFilter(build, requestInvoiceLineForTheSakeEndPointOnly.getInvoiceLineId());
                }
        ).collect(Collectors.toList());
    }

    public void updateAmountTotalInHeader(RequestInvoiceLineForTheSakeEndPointOnly
                                                  requestInvoiceLineForTheSakeEndPointOnly)
    {
        if (requestInvoiceLineForTheSakeEndPointOnly.getInvoiceLineId() == null ||
                requestInvoiceLineForTheSakeEndPointOnly.getInvoiceLineId() == 0L)
        {
            InvoiceHeader invoiceById = invoiceHeaderMapper.
                    getInvoiceById(requestInvoiceLineForTheSakeEndPointOnly.getInvoiceHeaderId());
            invoiceById.setLastUpdateDate(LocalDateTime.now());
            invoiceById.setTotalAmount(invoiceById.getTotalAmount()
                    + requestInvoiceLineForTheSakeEndPointOnly.getQuantity() * requestInvoiceLineForTheSakeEndPointOnly.getUnitPrice());
            invoiceHeaderMapper.updateHeader(invoiceById);
        }else {
            InvoiceLine lineById = invoiceLineMapper
                    .getById(requestInvoiceLineForTheSakeEndPointOnly.getInvoiceLineId());
            InvoiceHeader headerById = invoiceHeaderMapper.
                    getInvoiceById(requestInvoiceLineForTheSakeEndPointOnly.getInvoiceHeaderId());
            headerById.setTotalAmount(
                    (headerById.getTotalAmount() - lineById.getTotalAmount()) +
                            requestInvoiceLineForTheSakeEndPointOnly.getQuantity() * requestInvoiceLineForTheSakeEndPointOnly.getUnitPrice()
            );
            headerById.setLastUpdateDate(LocalDateTime.now());
            invoiceHeaderMapper.updateHeader(headerById);
        }
    }
}

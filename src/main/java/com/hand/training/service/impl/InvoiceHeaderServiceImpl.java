package com.hand.training.service.impl;

import com.hand.training.constant.InvoiceType;
import com.hand.training.dto.RequestHeader;
import com.hand.training.dto.RequestHeaderUpdate;
import com.hand.training.dto.ResponseHeader;
import com.hand.training.entity.InvoiceHeader;
import com.hand.training.entity.InvoiceLine;
import com.hand.training.mapper.InvoiceHeaderMapper;
import com.hand.training.service.InvoiceHeaderService;
import com.hand.training.service.InvoiceLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InvoiceHeaderServiceImpl implements InvoiceHeaderService {
    private final InvoiceHeaderMapper invoiceHeaderMapper;
    private final InvoiceLineService invoiceLineService;

    @Override
    public List<InvoiceHeader> getListInvoiceHeaderThatIncluded(Integer limit, Integer offset, List<InvoiceType> invoiceType) {
        return invoiceHeaderMapper.getInvoiceHeaderInvoiceTYpeLikeInList(limit, offset, invoiceType.stream().map(Enum::toString).collect(Collectors.toList()));
    }

    @Override
    public InvoiceHeader getHeaderById(Long id) {
        return invoiceHeaderMapper.getInvoiceById(id);
    }

    @Override
    public void updateDlFlag(List<Long> idHeader) {
        invoiceHeaderMapper.updateDlFlag(idHeader);
        invoiceLineService.deleteLineFromHeaderId(idHeader);
    }

    @Override
    public InvoiceHeader createInvoiceHeader(InvoiceHeader invoiceHeader) {
        invoiceHeader.setInvoiceNumber(UUID.randomUUID().toString().substring(0, 30));
        invoiceHeaderMapper.insertInvoiceHeader(invoiceHeader);
        return getForReturn(invoiceHeader);
    }

    @Override
    public InvoiceHeader getForReturn(InvoiceHeader invoiceHeader) {
        return invoiceHeaderMapper.getHeaderForReturn(invoiceHeader);
    }

    @Override
    public InvoiceHeader updateInvoiceHeader(InvoiceHeader invoiceHeader) {
        InvoiceHeader headerById = getHeaderById(invoiceHeader.getInvoiceHeaderId());
        headerById.setInvoiceType(invoiceHeader.getInvoiceType());
        headerById.setInvoiceNumber(invoiceHeader.getInvoiceNumber());
        headerById.setTotalAmount(invoiceHeader.getTotalAmount());
        headerById.setRemark(invoiceHeader.getRemark());
        headerById.setStatus(invoiceHeader.getStatus());
        headerById.setLastUpdateDate(LocalDateTime.now());
        invoiceHeaderMapper.updateHeader(headerById);
        return getForReturn(headerById);
    }

    @Override
    public List<InvoiceHeader> createFromList(List<InvoiceHeader> invoiceHeaderList) {
        return invoiceHeaderList.stream().map(
                this::createInvoiceHeader
        ).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceHeader> updateFromList(List<InvoiceHeader> invoiceHeaderList) {
        return invoiceHeaderList.stream().map(
                this::updateInvoiceHeader
        ).collect(Collectors.toList());
    }

    @Override
    public ResponseHeader createWithLines(RequestHeader invoiceHeader) {
        InvoiceHeader headerById = new InvoiceHeader();
        headerById.setInvoiceType(invoiceHeader.getInvoiceType().toString());
        headerById.setInvoiceNumber(UUID.randomUUID().toString().substring(0, 30));
        headerById.setRemark(invoiceHeader.getRemark());
        headerById.setStatus(invoiceHeader.getStatus().toString());


        InvoiceHeader invoiceHeader1 = createInvoiceHeader(headerById);
        List<InvoiceLine> collect = invoiceHeader.getListLines().stream().map(
                requestInvoiceLine -> invoiceLineService.InsertOrUpdateFilter(requestInvoiceLine,
                        invoiceHeader1.getInvoiceHeaderId())
        ).collect(Collectors.toList());
        invoiceHeader1.setTotalAmount(totalAmountFromLines(collect));
        invoiceHeaderMapper.updateAmount(invoiceHeader1.getTotalAmount(), invoiceHeader1.getInvoiceHeaderId());
//        response
        return mappingHeaderToResponse(invoiceHeader1, collect);
    }

    @Override
    public ResponseHeader updateWithLines(RequestHeader invoiceHeader) {
        InvoiceHeader headerById = getHeaderById(invoiceHeader.getInvoiceHeaderId());
        headerById.setInvoiceType(invoiceHeader.getInvoiceType().toString());
        headerById.setRemark(invoiceHeader.getRemark());
        headerById.setStatus(invoiceHeader.getStatus().toString());
        headerById.setLastUpdateDate(LocalDateTime.now());

        InvoiceHeader invoiceHeader1 = updateInvoiceHeader(headerById);
        List<InvoiceLine> collect = invoiceHeader.getListLines().stream().map(
                requestInvoiceLine -> invoiceLineService.InsertOrUpdateFilter(requestInvoiceLine,
                        invoiceHeader1.getInvoiceHeaderId())
        ).collect(Collectors.toList());
        invoiceHeader1.setTotalAmount(totalAmountFromLines(collect));
        invoiceHeaderMapper.updateAmount(invoiceHeader1.getTotalAmount(), invoiceHeader1.getInvoiceHeaderId());
//
        return mappingHeaderToResponse(invoiceHeader1, collect);
    }

    @Override
    public ResponseHeader getDetail(Long id) {
        InvoiceHeader headerById = getHeaderById(id);
        List<InvoiceLine> lineFromHeaderId = invoiceLineService.getLineFromHeaderId(headerById.getInvoiceHeaderId());
        return mappingHeaderToResponse(headerById, lineFromHeaderId);
    }

    @Override
    public InvoiceHeader insertOrUpdate(InvoiceHeader invoiceHeader) {
        if(invoiceHeader.getInvoiceHeaderId() == null || invoiceHeader.getInvoiceHeaderId() == 0)
        {
            createInvoiceHeader(invoiceHeader);
        }else {
            updateInvoiceHeader(invoiceHeader);
        }
        return getForReturn(invoiceHeader);
    }

    @Override
    public List<InvoiceHeader> insertOrUpdates(List<InvoiceHeader> invoiceHeaderList) {
        return invoiceHeaderList.stream().map(
                this::insertOrUpdate
        ).collect(Collectors.toList());
    }

    @Override
    public List<ResponseHeader> insertOrUpdatesWithLines(List<RequestHeader> invoiceHeaders) {
        return invoiceHeaders.stream().map(
                this::insertOrUpdateWithLine
        ).collect(Collectors.toList());
    }

    @Override
    public ResponseHeader insertOrUpdateWithLine(RequestHeader invoiceHeader) {
        ResponseHeader header = new ResponseHeader();
        if(invoiceHeader.getInvoiceHeaderId() == null || invoiceHeader.getInvoiceHeaderId() == 0)
        {
            header = createWithLines(invoiceHeader);
        }else {
            header = updateWithLines(invoiceHeader);
        }
        return header;
    }

    @Override
    public void updateAmount(Long idHeader, Double amount) {
        invoiceHeaderMapper.updateAmount(amount, idHeader);
    }


    public Double totalAmountFromLines(List<InvoiceLine> lines)
    {
        Double result = 0.0;
        for (InvoiceLine line : lines) {
            result += line.getTotalAmount();
        }
        return result;
    }

    public ResponseHeader mappingHeaderToResponse(InvoiceHeader invoiceHeader1, List<InvoiceLine> lines) {
        ResponseHeader header = new ResponseHeader();
        header.setCreatedBy(invoiceHeader1.getCreatedBy());
        header.setInvoiceHeaderId(invoiceHeader1.getInvoiceHeaderId());
        header.setInvoiceType(invoiceHeader1.getInvoiceType());
        header.setLines(lines);
        header.setRemark(invoiceHeader1.getRemark());
        header.setStatus(invoiceHeader1.getStatus());
        header.setInvoiceNumber(invoiceHeader1.getInvoiceNumber());
        header.setCreationDate(invoiceHeader1.getCreationDate());
        header.setCreatedBy(invoiceHeader1.getCreatedBy());
        header.setTotalAmount(
                totalAmountFromLines(lines)
        );
        header.setDelFlag(invoiceHeader1.getDelFlag());
        header.setLastUpdateDate(invoiceHeader1.getLastUpdateDate());
        header.setLastUpdatedBy(invoiceHeader1.getLastUpdatedBy());
        return header;
    }
}

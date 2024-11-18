package com.hand.training.controller;

import com.hand.training.constant.InvoiceType;
import com.hand.training.dto.RequestHeader;
import com.hand.training.dto.ResponseHeader;
import com.hand.training.entity.InvoiceHeader;
import com.hand.training.entity.InvoiceLine;
import com.hand.training.service.InvoiceHeaderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Api(tags = "Invoice Header Management", value = "Invoice Header Controller" )
@RequestMapping(path = "/header")
public class InvoiceHeaderController {
    private final InvoiceHeaderService invoiceHeaderService;


    @ApiOperation(value = "Get Detail Specific Invoice Header")
    @ApiResponses(value =
            {
                    @ApiResponse(code = 500, message = "Error from the server"),
                    @ApiResponse(code = 200, message = "successfully selected data")

            })
    @GetMapping(
            path = "/{id}"
    )
    public ResponseEntity<?> detail(@PathVariable(name = "id") Long id) {
        ResponseHeader detail = invoiceHeaderService.getDetail(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                detail
        );
    }

    @ApiOperation(value = "List of Header with Pagination")
    @ApiResponses(value =
            {
                    @ApiResponse(code = 500, message = "Error from the server"),
                    @ApiResponse(code = 200, message = "successfully selected data")

            })

    @GetMapping(
            path = "/filter"
    )
    public ResponseEntity<?> list(
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit
            ,@RequestParam(name = "offset", required = false, defaultValue = "1") Integer offset,
            @RequestParam(name = "type") InvoiceType type
    ) {
        List<InvoiceType> types = new ArrayList<>();
        types.add(type);
        List<InvoiceHeader> listInvoiceHeaderThatIncluded = invoiceHeaderService.
                getListInvoiceHeaderThatIncluded(limit, offset, types);
        return ResponseEntity.status(HttpStatus.OK).body(
                listInvoiceHeaderThatIncluded
        );
    }

    @ApiOperation(value = "update dl_flag in header and remove data in invoice line")
    @ApiResponses(value =
            {
                    @ApiResponse(code = 500, message = "Error from the server"),
                    @ApiResponse(code = 200, message = "successfully selected data")

            })
    @DeleteMapping(
            path = "/delete"
    )
    public ResponseEntity<?> remove(@RequestBody List<Long> id) {
        invoiceHeaderService.updateDlFlag(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                "successfully removed and deleted invoice"
        );
    }

    @ApiOperation(value = "save or update invoice header without filter")
    @ApiResponses(value =
            {
                    @ApiResponse(code = 500, message = "Error from the server"),
                    @ApiResponse(code = 200, message = "successfully selected data")

            })
    @PostMapping(
    )
    public ResponseEntity<?> save(@RequestBody List<InvoiceHeader> invoiceHeaders) {
        List<InvoiceHeader> invoiceHeaders1 = invoiceHeaderService.insertOrUpdates(invoiceHeaders);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            invoiceHeaders1
        );
    }

    @ApiOperation(value = "Save or Update Date with Filter")
    @ApiResponses(value =
            {
                    @ApiResponse(code = 500, message = "Error from the server"),
                    @ApiResponse(code = 200, message = "successfully selected data")

            })
    @PostMapping(
            path = "/with-lines"
    )
    public ResponseEntity<?> saveData(@RequestBody List<RequestHeader> requests) {
        List<ResponseHeader> responseHeaders = invoiceHeaderService.insertOrUpdatesWithLines(requests);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                      responseHeaders
        );
    }

}

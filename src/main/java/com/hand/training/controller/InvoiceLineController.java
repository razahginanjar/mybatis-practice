package com.hand.training.controller;

import com.hand.training.dto.RequestInvoiceLine;
import com.hand.training.dto.RequestInvoiceLineForTheSakeEndPointOnly;
import com.hand.training.entity.InvoiceLine;
import com.hand.training.service.InvoiceHeaderService;
import com.hand.training.service.InvoiceLineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/line")
@Api(tags = "Invoice Line Management", value = "Invoice Line Controller" )
@RequiredArgsConstructor
public class InvoiceLineController {

    private final InvoiceLineService invoiceLineService;
    private final InvoiceHeaderService invoiceHeaderService;

    @ApiOperation(value = "List Invoice Line With Pagination")
    @ApiResponses(value =
            {
                    @ApiResponse(code = 500, message = "Error from the server"),
                    @ApiResponse(code = 200, message = "successfully selected data")

            })
    @GetMapping(
    )
    public ResponseEntity<?> list(@RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit
            , @RequestParam(name = "offset", required = false, defaultValue = "1") Integer offset,
                                  @RequestParam(name = "quantities") List<Double> quantities
    ) {
        List<InvoiceLine> listInvoiceLineThatIncluded = invoiceLineService.getListInvoiceLineThatIncluded(quantities, limit, offset);
        return ResponseEntity.status(HttpStatus.OK).body(
                listInvoiceLineThatIncluded
        );
    }


    @ApiOperation(value = "save or update invoice line")
    @ApiResponses(value =
            {
                    @ApiResponse(code = 500, message = "Error from the server"),
                    @ApiResponse(code = 200, message = "successfully selected data")

            })
    @PostMapping(
    )
    public ResponseEntity<?> save(@RequestBody List<InvoiceLine> invoiceLine) {
        List<InvoiceLine> invoiceLines = invoiceLineService.insertOrUpdate(invoiceLine);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                invoiceLines
        );
    }


    @ApiOperation(value = "insert or update invoice line with filter")
    @ApiResponses(value =
            {
                    @ApiResponse(code = 500, message = "Error from the server"),
                    @ApiResponse(code = 200, message = "successfully selected data")

            })
    @PostMapping(
            path = "/filter"
    )
    public ResponseEntity<?> saveData(@RequestBody List<RequestInvoiceLineForTheSakeEndPointOnly> requests) {
        List<InvoiceLine> invoiceLines = invoiceLineService.insertOrUpdateJustForEndPoint(requests);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                invoiceLines
        );
    }
}

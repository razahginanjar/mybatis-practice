package com.hand.training.controller;

import com.hand.training.entity.InvoiceHeader;
import com.hand.training.entity.InvoiceLine;
import com.hand.training.service.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shaoqin.zhou@hand-china.com
 * @since 2024-10-13 23:16:29
 */
@RestController
@RequestMapping("/example")
@RequiredArgsConstructor
public class ExampleController {
    private final ExampleService exampleService;

    @RequestMapping("/sayHi")
    public String sayHi() {
        return "Hi";
    }

    @GetMapping(
            path = "/invoice-line"
    )
    public ResponseEntity<?> getInvoiceLine(@RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit
                                 ,@RequestParam(name = "offset", required = false, defaultValue = "1") Integer offset,
                                 @RequestBody List<Double> quantities
    ) {
        List<InvoiceLine> listInvoiceLineThatIncluded = exampleService.getListInvoiceLineThatIncluded(quantities, limit, offset);
        return ResponseEntity.status(HttpStatus.OK).body(
                listInvoiceLineThatIncluded
        );
    }

    @GetMapping(
            path = "/invoice-header"
    )
    public ResponseEntity<?> getInvoiceHeader(@RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit
            ,@RequestParam(name = "offset", required = false, defaultValue = "1") Integer offset,
                                              @RequestBody List<String> invoiceType
    ) {
        List<InvoiceHeader> listInvoiceHeaderThatIncluded = exampleService.getListInvoiceHeaderThatIncluded(limit, offset, invoiceType);
        return ResponseEntity.status(HttpStatus.OK).body(
                listInvoiceHeaderThatIncluded
        );
    }
}

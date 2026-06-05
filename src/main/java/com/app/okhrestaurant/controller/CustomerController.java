package com.app.okhrestaurant.controller;

import com.app.okhrestaurant.dto.CustomerDetailDTO;
import com.app.okhrestaurant.dto.CustomerSummaryDTO;
import com.app.okhrestaurant.service.customer.CustomerSC;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerSC customerSC;

    public CustomerController(
            CustomerSC customerSC
    ) {
        this.customerSC =
                customerSC;
    }

    @GetMapping
    public List<CustomerSummaryDTO>
    getCustomers() {

        return customerSC
                .getCustomers();
    }

    @GetMapping("/{phone}")
    public CustomerDetailDTO
    getCustomerDetail(
            @PathVariable String phone
    ) {

        return customerSC
                .getCustomerDetail(
                        phone
                );
    }
    @GetMapping("/download")
    public ResponseEntity<byte[]>
    exportCustomers() {

        byte[] file =
                customerSC
                        .exportCustomers();

        return ResponseEntity.ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=customers.csv"
                )

                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM
                )

                .body(
                        file
                );
    }
    @GetMapping("/{phone}/download")
    public ResponseEntity<byte[]>
    exportCustomerHistory(
            @PathVariable
            String phone
    ) {

        byte[] file =
                customerSC
                        .exportCustomerHistory(
                                phone
                        );

        return ResponseEntity.ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=customer-history.csv"
                )

                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM
                )

                .body(
                        file
                );
    }
}
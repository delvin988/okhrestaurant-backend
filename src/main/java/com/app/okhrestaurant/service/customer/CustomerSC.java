package com.app.okhrestaurant.service.customer;

import com.app.okhrestaurant.dto.CustomerDetailDTO;
import com.app.okhrestaurant.dto.CustomerSummaryDTO;

import java.util.List;

public interface CustomerSC {

    List<CustomerSummaryDTO>
    getCustomers();

    CustomerDetailDTO
    getCustomerDetail(
            String phone
    );
    byte[] exportCustomers();

    byte[] exportCustomerHistory(
            String phone
    );
}
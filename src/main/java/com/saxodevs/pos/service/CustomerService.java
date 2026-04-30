package com.saxodevs.pos.service;

import com.saxodevs.pos.dto.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO dto) throws Exception;

    CustomerDTO updateCustomer(UUID id, CustomerDTO dto) throws Exception;

    List<CustomerDTO> getAll() throws Exception;

    void deleteCustomer(UUID id) throws Exception;

    CustomerDTO getById(UUID id) throws Exception;

    CustomerDTO getByName(String name) throws Exception;

    CustomerDTO getByPhone(String phone) throws Exception;

    CustomerDTO getByEmail(String email) throws Exception;

}

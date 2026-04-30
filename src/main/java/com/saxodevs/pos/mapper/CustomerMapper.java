package com.saxodevs.pos.mapper;

import com.saxodevs.pos.dto.CustomerDTO;
import com.saxodevs.pos.model.Customer;
import com.saxodevs.pos.model.User;

import java.util.List;
import java.util.Optional;

public class CustomerMapper {

    public static CustomerDTO toDTO( Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .isDefault(customer.getIsDefault() != null && customer.getIsDefault())
                .sku(customer.getSku())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .createdBy(customer.getCreatedBy() != null ? customer.getCreatedBy().getId() : null)
                .updatedBy(customer.getUpdatedBy() != null ? customer.getUpdatedBy().getId() : null)
                .build();

    }


    public static Customer toEntity(CustomerDTO dto, User user) {
        return Customer.builder()
                .isDefault(dto.isDefault())
                .sku(dto.getSku())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .createdBy(user)
                .updatedBy(null)
                .build();
    }

    public static List<CustomerDTO> toDTOList(List<Customer> customers) {
        return customers.stream().map(CustomerMapper::toDTO).toList();
    }
}

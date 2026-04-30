package com.saxodevs.pos.mapper;

import com.saxodevs.pos.dto.SupplierDTO;
import com.saxodevs.pos.model.Supplier;
import com.saxodevs.pos.model.User;
import lombok.Data;

import java.util.List;

@Data
public class SupplierMapper {

    public static SupplierDTO toDTO(Supplier supplier){
        return SupplierDTO.builder()
                .id(supplier.getId())
                .sku(supplier.getSku())
                .firstName(supplier.getFirstName())
                .lastName(supplier.getLastName())
                .email(supplier.getEmail())
                .address(supplier.getAddress())
                .phoneNumber(supplier.getPhoneNumber())
                .createdAt(supplier.getCreatedAt())
                .updatedAt(supplier.getUpdatedAt())
                .createdBy(supplier.getCreatedBy() != null ? supplier.getCreatedBy().getId() : null)
                .updatedBy(supplier.getUpdatedBy() != null ? supplier.getUpdatedBy().getId() : null)
                .build();
    }

    public static Supplier toEntity(SupplierDTO dto, User user){
        return Supplier.builder()
                .sku(dto.getSku())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .phoneNumber(dto.getPhoneNumber())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .createdBy(user)
                .updatedBy(null)
                .build();
    }

    public static List<SupplierDTO> toDTOList(List<Supplier> suppliers){
        return suppliers.stream().map(SupplierMapper::toDTO).toList();
    }
}

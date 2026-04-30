package com.saxodevs.pos.mapper;

import com.saxodevs.pos.dto.TaxDTO;
import com.saxodevs.pos.model.Tax;
import com.saxodevs.pos.model.User;

import java.util.List;

public class TaxMapper {

    public static TaxDTO toDTO(Tax tax) {
        return TaxDTO.builder()
                .id(tax.getId())
                .taxCode(tax.getTaxCode())
                .taxName(tax.getTaxName())
                .taxRate(tax.getTaxRate())
                .createdAt(tax.getCreatedAt())
                .updatedAt(tax.getUpdatedAt())
                .createdBy(tax.getCreatedBy() != null ? tax.getCreatedBy().getId() : null)
                .updatedBy(tax.getUpdatedBy() != null ? tax.getUpdatedBy().getId() : null)
                .build();
    }

    public static Tax toEntity(TaxDTO dto, User user) {
        return Tax.builder()
                .taxName(dto.getTaxName())
                .taxCode(dto.getTaxCode())
                .taxRate(dto.getTaxRate())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .createdBy(user)
                .updatedBy(null)
                .build();
    }

    public static List<TaxDTO> dtoList(List<Tax> taxes){
        return taxes.stream().map(TaxMapper::toDTO).toList();
    }
}

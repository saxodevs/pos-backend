package com.saxodevs.pos.mapper;

import com.saxodevs.pos.dto.ReturnDTO;
import com.saxodevs.pos.model.Returns;
import com.saxodevs.pos.model.Sale;
import com.saxodevs.pos.model.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ReturnMapper {

    public static ReturnDTO toDTO(Returns returns) {
        return ReturnDTO.builder()
                .id(returns.getId())
                .sku(returns.getSku())
                .total(returns.getTotal())
                .reason(returns.getReason())
                .saleId(returns.getSale() != null ? returns.getSale().getId() : null)
                .userId(returns.getUser() != null ? returns.getUser().getId() : null)
                .approvedBy(returns.getApprovedBy() != null ? returns.getApprovedBy().getId() : null)
                .rejectedBy(returns.getRejectedBy() != null ? returns.getRejectedBy().getId() : null)
                .status(returns.getStatus())
                .createdAt(returns.getCreatedAt())
                .items(returns.getItems().stream()
                        .map(ReturnItemMapper::toDTO)
                        .collect(Collectors.toList())
                )
                .build();
    }

    public static Returns toEntity(ReturnDTO dto, Sale sale, User user) {
        return Returns.builder()
                .total(dto.getTotal())
                .reason(dto.getReason())
                .createdAt(dto.getCreatedAt())
                .sale(sale)
                .user(user)
                .status(dto.getStatus())
                .approvedBy(null)
                .rejectedBy(null)
                .build();
    }

    public static List<ReturnDTO> toDTOList(List<Returns> returns) {
        return returns.stream().map(ReturnMapper::toDTO).toList();
    }

}

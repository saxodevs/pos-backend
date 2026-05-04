package com.saxodevs.pos.service.implementation;

import com.saxodevs.pos.domain.ChangeType;
import com.saxodevs.pos.domain.ReturnStatus;
import com.saxodevs.pos.dto.ReturnDTO;
import com.saxodevs.pos.dto.ReturnItemDTO;
import com.saxodevs.pos.exceptions.AppException;
import com.saxodevs.pos.mapper.ReturnMapper;
import com.saxodevs.pos.model.*;
import com.saxodevs.pos.repository.ProductRepository;
import com.saxodevs.pos.repository.ReturnRepository;
import com.saxodevs.pos.repository.SaleRepository;
import com.saxodevs.pos.repository.UserRepository;
import com.saxodevs.pos.service.InventoryLogService;
import com.saxodevs.pos.service.InventoryService;
import com.saxodevs.pos.service.ReturnService;
import com.saxodevs.pos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReturnImplementation implements ReturnService {

    private final ReturnRepository returnRepository;
    private final UserService userService;
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final InventoryLogService inventoryLogService;
    private final InventoryService inventoryService;
    private final CustomIdGenerator generator;
    private final UserRepository userRepository;


    @Override
    public ReturnDTO createReturn(ReturnDTO dto) {

        Sale sale = saleRepository.findById(dto.getSaleId()).orElseThrow(
                () -> new AppException("Sale with id " + dto.getSaleId() + " not found", HttpStatus.NOT_FOUND)
        );

        User user = userService.getCurrentUser();



        Returns returns = ReturnMapper.toEntity(dto, sale, user);

        List<ReturnItem> returnItems = new ArrayList<>();

        for (ReturnItemDTO returnItemDTO : dto.getItems()) {
            Product product = productRepository.findById(returnItemDTO.getProductId()).orElseThrow(
                    () -> new AppException("Product with id " + returnItemDTO.getProductId() + " not found", HttpStatus.NOT_FOUND)
            );

            ReturnItem returnItem = ReturnItem.builder()
                    .quantity(returnItemDTO.getQuantity())
                    .returns(returns)
                    .product(product)
                    .build();

            returnItems.add(returnItem);
        }


        returns.setItems(returnItems);

        returns.setSku(generator.generate("RTN"));

        returnRepository.save(returns);

        return ReturnMapper.toDTO(returns);
    }
    @Override
    public ReturnDTO updateReturn(UUID id, ReturnDTO dto) throws Exception {

        Returns returns = returnRepository.findById(id).orElseThrow(
                () -> new AppException("Return with id " + id + " not found", HttpStatus.NOT_FOUND)
        );

        if (dto.getStatus() == null) {
            throw new AppException("Status is required", HttpStatus.BAD_REQUEST);
        }

        if (returns.getStatus() == ReturnStatus.APPROVED) {
            throw new AppException("Return already approved", HttpStatus.BAD_REQUEST);
        }

        User user = userService.getCurrentUser();


        if (returns.getStatus() != ReturnStatus.APPROVED
                && dto.getStatus() == ReturnStatus.APPROVED) {

            for (ReturnItem item : returns.getItems()) {
                Product product = item.getProduct();

                inventoryService.reStock(product, item.getQuantity());
                inventoryLogService.logInventoryChange(
                        product,
                        ChangeType.ADJUSTMENT,
                        item.getQuantity()
                );
            }

            returns.setApprovedBy(user);
            returns.setRejectedBy(null);
        }

        if (dto.getStatus() == ReturnStatus.REJECTED) {
            returns.setRejectedBy(user);
            returns.setApprovedBy(null);
        }

        returns.setStatus(dto.getStatus());

        return ReturnMapper.toDTO(returnRepository.save(returns));
    }


    @Override
    public void deleteReturn(UUID id) {
        Returns returns = returnRepository.findById(id).orElseThrow(
                () -> new AppException("Return with id " + id + " not found", HttpStatus.NOT_FOUND)
        );
        returnRepository.delete(returns);

    }

    @Override
    public List<ReturnDTO> getReturnByStatus(ReturnStatus status) {

        List<Returns> returns = returnRepository.findByStatus(status);

        return ReturnMapper.toDTOList(returns);
    }


    @Override
    public ReturnDTO getReturnById(UUID id) {
        Returns returns = returnRepository.findById(id).orElseThrow(
                () -> new AppException("Return with id " + id + " not found", HttpStatus.NOT_FOUND)
        );
        return ReturnMapper.toDTO(returns);
    }

    @Override
    public List<ReturnDTO> getAllReturns() {
        return ReturnMapper.toDTOList(returnRepository.findAll());
    }

    @Override
    public List<ReturnDTO> getReturnsByUserIdAndToday(UUID id) {

        LocalDate today = LocalDate.now();

        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        List<Returns> returnsList =
                returnRepository.findByUserIdAndCreatedAtBetween(id, startOfDay, endOfDay);

        return ReturnMapper.toDTOList(returnsList);
    }



    @Override
    public List<ReturnDTO> getReturnsByStatus(ReturnStatus status) {
        return List.of();
    }


    @Override
    public ReturnDTO getReturnByUserId(UUID userId) {
        Returns returns = returnRepository.findById(userId).orElseThrow(
                () -> new AppException("User with id " + userId + " not found", HttpStatus.NOT_FOUND)
        );
        return ReturnMapper.toDTO(returns);
    }


}

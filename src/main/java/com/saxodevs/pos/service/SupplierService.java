package com.saxodevs.pos.service;

import com.saxodevs.pos.dto.SupplierDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface SupplierService {

    SupplierDTO addSupplier(SupplierDTO dto) throws Exception;

    SupplierDTO getById(UUID id) throws Exception;

    SupplierDTO getByEmail(String email) throws Exception;

    SupplierDTO getByPhoneNumber(String phone) throws Exception;

    SupplierDTO updateSupplier(UUID id, SupplierDTO dto) throws Exception;

    List<SupplierDTO> getAll() throws Exception;

    void deleteSupplier(UUID id) throws Exception;

}

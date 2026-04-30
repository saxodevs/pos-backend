package com.saxodevs.pos.service.implementation;

import com.saxodevs.pos.dto.SupplierDTO;
import com.saxodevs.pos.mapper.SupplierMapper;
import com.saxodevs.pos.model.Supplier;
import com.saxodevs.pos.model.User;
import com.saxodevs.pos.repository.SupplierRepository;
import com.saxodevs.pos.service.SupplierService;
import com.saxodevs.pos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupplierImplementation implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final UserService userService;
    private final CustomIdGenerator generator;

    @Override
    public SupplierDTO addSupplier(SupplierDTO dto) {

        User user = userService.getCurrentUser();
        dto.setSku(generator.generate("SPR"));

        Supplier supplier = SupplierMapper.toEntity(dto, user);
        Supplier savedSupplier = supplierRepository.save(supplier);

        return SupplierMapper.toDTO(savedSupplier);
    }

    @Override
    public SupplierDTO getById(UUID id) throws Exception {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(
                () -> new Exception("Supplier not found")
        );
        return SupplierMapper.toDTO(supplier);
    }

    @Override
    public SupplierDTO getByEmail(String email) throws Exception {
        Supplier supplier = supplierRepository.findByEmail(email);

        if (supplier == null) {
            throw new Exception("Supplier not found");
        }
        return SupplierMapper.toDTO(supplier);
    }

    @Override
    public SupplierDTO getByPhoneNumber(String phone) throws Exception {
        Supplier supplier = supplierRepository.findByPhoneNumber(phone);

        if (supplier == null) {
            throw new Exception("Supplier not found");
        }
        return SupplierMapper.toDTO(supplier);
    }

    @Override
    public SupplierDTO updateSupplier(UUID id, SupplierDTO dto) throws Exception {

        Supplier supplier = supplierRepository.findById(id).orElseThrow(
                () -> new Exception("Supplier not found")
        );

        User user = userService.getCurrentUser();

        supplier.setFirstName(dto.getFirstName());
        supplier.setLastName(dto.getLastName());
        supplier.setEmail(dto.getEmail());
        supplier.setPhoneNumber(dto.getPhoneNumber());
        supplier.setAddress(dto.getAddress());
        supplier.setUpdatedBy(user);

        Supplier updatedSupplier = supplierRepository.save(supplier);
        return SupplierMapper.toDTO(updatedSupplier);
    }

    @Override
    public List<SupplierDTO> getAll() throws Exception {
        return SupplierMapper.toDTOList(supplierRepository.findByDeletedFalse());
    }

    @Override
    public void deleteSupplier(UUID id) throws Exception {
        User user = userService.getCurrentUser();

        Supplier supplier = supplierRepository.findById(id).orElseThrow(
                () -> new Exception("Supplier not found")
        );
        supplier.setDeletedBy(user);
        supplier.setDeleted(true);
        supplierRepository.save(supplier);
    }
}

package com.saxodevs.pos.service.implementation;

import com.saxodevs.pos.dto.TaxDTO;
import com.saxodevs.pos.mapper.TaxMapper;
import com.saxodevs.pos.model.Tax;
import com.saxodevs.pos.model.User;
import com.saxodevs.pos.repository.TaxRepository;
import com.saxodevs.pos.service.TaxService;
import com.saxodevs.pos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TaxImplementation implements TaxService {

    private final TaxRepository taxRepository;
    private final UserService userService;
    private final CustomIdGenerator generator;

    @Override
    public TaxDTO getTaxById(UUID id) throws Exception {
        Tax tax = taxRepository.findById(id).orElseThrow(
                () -> new Exception("Tax not found")
        );
        return TaxMapper.toDTO(tax);
    }

    @Override
    public TaxDTO getTaxByName(String name) throws Exception {
        Tax tax = taxRepository.findByTaxName(name);
        if (tax == null) {
            throw new Exception("Tax not found");
        }
        return TaxMapper.toDTO(tax);
    }

    @Override
    public TaxDTO getTaxCode(String code) throws Exception {
        Tax tax = taxRepository.findByTaxCode(code);
        if (tax == null) {
            throw new Exception("Tax not found");
        }
        return TaxMapper.toDTO(tax);
    }

    @Override
    public List<TaxDTO> getAllTax()  {
        return TaxMapper.dtoList(taxRepository.findByDeletedFalse());
    }

    @Override
    public TaxDTO createTax(TaxDTO taxDTO) throws Exception {

        if (taxRepository.findByTaxName(taxDTO.getTaxName()) != null) {
            throw new Exception("Tax already exits");

        }
        User user = userService.getCurrentUser();

        taxDTO.setTaxCode(generator.generate("TAX"));

        Tax tax = TaxMapper.toEntity(taxDTO, user);

        Tax savedTax = taxRepository.save(tax);

        return TaxMapper.toDTO(savedTax);
    }

    @Override
    public TaxDTO updateTax(UUID id, TaxDTO taxDTO) throws Exception {
        Tax existingTax = taxRepository.findById(id).orElseThrow(
                () -> new Exception("Tax not found")
        );

        User user = userService.getCurrentUser();


        existingTax.setTaxName(taxDTO.getTaxName());
        existingTax.setTaxRate(taxDTO.getTaxRate());
        existingTax.setUpdatedBy(user);

        Tax savedTax = taxRepository.save(existingTax);

        return TaxMapper.toDTO(savedTax);
    }

    @Override
    public void deleteTax(UUID id) throws Exception {
        User user = userService.getCurrentUser();
        Tax existingTax = taxRepository.findById(id).orElseThrow(
                () -> new Exception("Tax not found")
        );

        existingTax.setDeleted(true);
        existingTax.setDeletedBy(user);

        taxRepository.save(existingTax);
    }
}

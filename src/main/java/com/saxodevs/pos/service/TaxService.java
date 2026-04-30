package com.saxodevs.pos.service;

import com.saxodevs.pos.dto.TaxDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TaxService {


    TaxDTO getTaxById(UUID Id) throws Exception;

    TaxDTO getTaxByName(String name) throws Exception;

    TaxDTO getTaxCode(String code) throws Exception;

    List<TaxDTO> getAllTax() throws Exception;

    TaxDTO createTax(TaxDTO taxDTO) throws Exception;

    TaxDTO updateTax(UUID id, TaxDTO taxDTO) throws Exception;

    void deleteTax(UUID id) throws Exception;
}

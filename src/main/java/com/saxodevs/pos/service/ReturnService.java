package com.saxodevs.pos.service;

import com.saxodevs.pos.domain.ReturnStatus;
import com.saxodevs.pos.dto.ReturnDTO;
import com.saxodevs.pos.model.Returns;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public interface ReturnService {

    ReturnDTO createReturn(ReturnDTO dto);

    ReturnDTO updateReturn(UUID id, ReturnDTO dto) throws Exception;

    void deleteReturn(UUID id);

    List<ReturnDTO> getReturnByStatus(ReturnStatus status);

    ReturnDTO getReturnById(UUID id);

    List<ReturnDTO> getAllReturns();

    List<ReturnDTO> getReturnsByUserIdAndToday(UUID id);



    List<ReturnDTO> getReturnsByStatus(ReturnStatus status);


    ReturnDTO getReturnByUserId(UUID userId);



}

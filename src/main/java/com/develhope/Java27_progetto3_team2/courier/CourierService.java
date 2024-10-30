package com.develhope.Java27_progetto3_team2.courier;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CourierService {
    private final CourierMapper courierMapper;
    private final CourierRepository courierRepository;

    public CourierDTO addNewCourier(CourierDTO courierDTO) {
        if(courierDTO.getOrderList()==null){
            courierDTO.setOrderList(new ArrayList<>());
        }
        Courier newCourier = courierMapper.toCourier(courierDTO);
        courierRepository.save(newCourier);
        return courierMapper.toCourierDTO(newCourier);
    }

    public CourierDTO updateCourier(Long idCourier, CourierDTO courierDTO) throws Exception {
        if(!courierRepository.existsById(idCourier)){
            throw new Exception("Courier not found");
        }
        Courier updateCourier = courierMapper.toCourier(courierDTO);
        updateCourier.setId(idCourier);
        courierRepository.save(updateCourier);
        courierDTO = courierMapper.toCourierDTO(updateCourier);
        return courierDTO;
    }
}

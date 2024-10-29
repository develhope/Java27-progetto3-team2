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
        i
        courierRepository.save(updateCourier);
        courierDTO = courierMapper.toCourierDTO(updateCourier);
        return courierDTO;
    }

    public boolean deleteOrder(Long idCourier) {
        if (!courierRepository.existsById(idCourier)) {
        return false; // Il courier non esiste, restituisci false
    }
        courierRepository.deleteById(idCourier); // Elimina il courier
        return true; // Restituisci true per indicare che il courier è stato eliminato con successo
    }

    public CourierDTO getCourierById(Long idCourier) throws Exception {
    Courier courierFound = courierRepository.getReferenceById(idCourier);
    if(courierFound==null){
        throw new Exception("Courier not found by id: "+idCourier);
    }
    return courierMapper.toCourierDTO(courierFound);
    }

    public CourierDTO getCourierByEmail(String emailCourier) throws Exception {
        Courier courierFound = courierRepository.findByEmail(emailCourier);
        if(courierFound==null){
            throw new Exception("Courier not found by email: "+emailCourier);
        }
        return courierMapper.toCourierDTO(courierFound);
    }
}

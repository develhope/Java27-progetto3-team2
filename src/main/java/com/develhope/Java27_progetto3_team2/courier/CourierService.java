package com.develhope.Java27_progetto3_team2.courier;

import com.develhope.Java27_progetto3_team2.exception.exceptions.CourierAlreadyExistException;
import com.develhope.Java27_progetto3_team2.exception.exceptions.EntityNotFoundException;
import com.develhope.Java27_progetto3_team2.order.entity.Order;
import com.develhope.Java27_progetto3_team2.order.repository.OrderRepository;
import com.develhope.Java27_progetto3_team2.user.Role;
import com.develhope.Java27_progetto3_team2.user.User;
import com.develhope.Java27_progetto3_team2.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourierService {
    private final CourierMapper courierMapper;
    private final CourierRepository courierRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

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
        courierRepository.findById(idCourier).orElseThrow(() -> new EntityNotFoundException("Courier with id: " + idCourier +" not found!"));
        Courier updateCourier = courierMapper.toCourier(courierDTO);
        updateCourier.setId(idCourier);
        courierRepository.save(updateCourier);
        return courierMapper.toCourierDTO(updateCourier);
    }

    public boolean deleteOrder(Long idCourier) {
        courierRepository.findById(idCourier).orElseThrow(() -> new EntityNotFoundException("Courier with id: " + idCourier + " not found!"));
        courierRepository.deleteById(idCourier); // Elimina il courier
        return true; // Restituisci true per indicare che il courier è stato eliminato con successo
    }

    public CourierDTO getCourierById(Long idCourier) throws Exception {
        Courier courier = courierRepository.findById(idCourier).orElseThrow(() -> new EntityNotFoundException("Courier with id: " + idCourier + " not found!"));
        return courierMapper.toCourierDTO(courier);
    }

    public CourierDTO getCourierByEmail(String emailCourier) throws Exception {
        Courier courierFound = courierRepository.findByEmail(emailCourier).orElseThrow(() -> new EntityNotFoundException("Courier with email: " + emailCourier + " not found!"));
        return courierMapper.toCourierDTO(courierFound);
    }

    public CourierDTO applyAsCourier(UserDetails userDetails){
        if(courierRepository.existsByEmail(userDetails.getUsername())){
            throw new CourierAlreadyExistException("Courier with email: " + userDetails.getUsername() + " already exists");
        }
        User user = (User) userDetails;
        CourierDTO courierDTO = CourierDTO.builder()
                .email(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .status(CourierStatus.APPLICATION_PENDING)
                .orderList(new ArrayList<>())
                .phoneNumber(user.getPhoneNumber())
                .build();
        courierRepository.save(courierMapper.toCourier(courierDTO));
        return courierDTO;
    }

    public CourierDTO changeApplicationStatus(Long coruerId, String newStatus){
        Courier courier = courierRepository.findById(coruerId).orElseThrow(() -> new EntityNotFoundException("Coruer with id: " + coruerId + " not found"));
        courier.setStatus(CourierStatus.valueOf(newStatus));
        if(courier.getStatus().equals(CourierStatus.ACCEPTED)){
            User user = userRepository.findByEmail(courier.getEmail()).orElseThrow(() -> new EntityNotFoundException("Coruer with email: " + courier.getEmail() + " not found"));
            user.setRole(Role.ROLE_COURIER);
        }
        courierRepository.save(courier);

        return courierMapper.toCourierDTO(courier);
    }

    public CourierDTO addOrderToCourier(UserDetails userDetails, Long orderId){
        if(userDetails.getUsername() == null || userDetails.getUsername().isEmpty()){
            throw new EntityNotFoundException("Courier not found");
        }
        Courier courier = courierRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new EntityNotFoundException("No courier found with email: " + userDetails.getUsername() ));
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order with id: " + orderId + " not found"));
        courier.setOrderList(List.of(order));
        courierRepository.save(courier);

        return courierMapper.toCourierDTO(courier);
    }
}

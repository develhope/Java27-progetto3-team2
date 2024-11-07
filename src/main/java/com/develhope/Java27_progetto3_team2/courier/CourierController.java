package com.develhope.Java27_progetto3_team2.courier;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class CourierController {
    private final CourierService courierService;

    @PostMapping("/user/courier_apply")
    public ResponseEntity<CourierDTO> applyForCourier(@AuthenticationPrincipal UserDetails userDetails){
        CourierDTO courierDTO = courierService.applyAsCourier(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(courierDTO);
    }

    @PatchMapping("/user/application/{courierId}")
    public ResponseEntity<CourierDTO> changeApplicationStatus(@PathVariable("courierId") Long courierId, @RequestParam String newStatus){
        CourierDTO courierDTO = courierService.changeApplicationStatus(courierId,newStatus);
        return ResponseEntity.status(HttpStatus.OK).body(courierDTO);
    }


    @PostMapping()
    public ResponseEntity<?> addNewCourier(@RequestBody CourierDTO courierDTO) {
        try{
        return ResponseEntity.status(HttpStatus.CREATED).body(courierService.addNewCourier(courierDTO));}
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{idCourier}")
    public ResponseEntity<?> updateCourier(@PathVariable("idCourier") Long idCourier,
                                           @RequestBody CourierDTO courierDTO) {
        try {
            CourierDTO updatedCourier = courierService.updateCourier(idCourier, courierDTO);
            return ResponseEntity.status(HttpStatus.FOUND).body(updatedCourier);
        }catch( Exception e ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{idCourier}")
    public ResponseEntity<?> deleteCourier(@PathVariable("idCourier") Long idCourier){
        try {
            boolean isDeleted = courierService.deleteOrder(idCourier);
            return ResponseEntity.ok("Courier with id " + idCourier + " has been deleted.");
            //return ResponseEntity.noContent().build(); <-- codice 204 No content, delete idempotente
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while deleting the order: " + e.getMessage());
        }
    }

    @GetMapping("/{idCourier}")
    public ResponseEntity<?> getCourierById(@PathVariable("idCourier") Long idCourier){
        try {
            CourierDTO updatedCourier = courierService.getCourierById(idCourier);
            return ResponseEntity.status(HttpStatus.FOUND).body(updatedCourier);
        }catch( Exception e ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/courier/order/{orderId}")
    public ResponseEntity<CourierDTO> addOrderToCourier(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("orderId") Long orderId){
        CourierDTO courierDTO = courierService.addOrderToCourier(userDetails,orderId);
        return ResponseEntity.status(HttpStatus.OK).body(courierDTO);
    }

}
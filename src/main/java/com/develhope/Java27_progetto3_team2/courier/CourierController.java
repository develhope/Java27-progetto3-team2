package com.develhope.Java27_progetto3_team2.courier;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courier")
@RequiredArgsConstructor
public class CourierController {
    private final CourierService courierService;

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

    @GetMapping("")
    public ResponseEntity<?> getCourierById(@RequestParam String emailCourier){
        try {
            CourierDTO updatedCourier = courierService.getCourierByEmail(emailCourier);
            return ResponseEntity.status(HttpStatus.FOUND).body(updatedCourier);
        }catch( Exception e ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
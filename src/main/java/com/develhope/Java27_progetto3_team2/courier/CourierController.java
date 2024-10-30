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

}
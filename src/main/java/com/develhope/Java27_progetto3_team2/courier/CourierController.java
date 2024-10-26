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
    public ResponseEntity<CourierDTO> addNewCourier(@RequestBody CourierDTO courierDTO) {
        CourierDTO courierAdded = courierService.addNewCourier(courierDTO);
        return ResponseEntity.ok(courierAdded);
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
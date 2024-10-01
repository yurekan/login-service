package com.ecommerce.login_service.controller;

import com.ecommerce.login_service.dto.VehicleDto;
import com.ecommerce.login_service.model.Vehicle;
import com.ecommerce.login_service.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/vehicles")
    public List<VehicleDto> getAllVehicles(){
        return vehicleService.getAllVehicle();
    }

    @GetMapping("vehicle/{id}")
    public ResponseEntity<VehicleDto> getVehicleById(@PathVariable(value = "id") int vehicleId){
        VehicleDto vehicleDto = vehicleService.getVehicleById(vehicleId);
        return new ResponseEntity<>(vehicleDto, HttpStatus.OK);
    }

    @PostMapping("vehicle/add")
    public ResponseEntity<VehicleDto> createVehicle(@RequestBody VehicleDto vehicleDto){
        VehicleDto _vehicleDto = vehicleService.createVehicle(vehicleDto);
        return new ResponseEntity<>(_vehicleDto, HttpStatus.CREATED);
    }

    @PutMapping("vehicle/{id}/update")
    public ResponseEntity<VehicleDto> updateVehicle(@RequestBody VehicleDto vehicleDto, @PathVariable(value = "id") int vehicleId){
        VehicleDto _vehicleDto = vehicleService.updateVehicle(vehicleDto, vehicleId);
        return new ResponseEntity<>(_vehicleDto, HttpStatus.OK);
    }

    @DeleteMapping("vehicle/{id}/delete")
    public void deleteVehicle(@PathVariable(value = "id") int vehicleId){
        vehicleService.deleteVehicle(vehicleId);
    }
}

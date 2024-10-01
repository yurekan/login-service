package com.ecommerce.login_service.service;

import com.ecommerce.login_service.dto.VehicleDto;
import com.ecommerce.login_service.model.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;

public interface VehicleService {
    VehicleDto createVehicle(VehicleDto vehicleDto);
    List<VehicleDto> getAllVehicle();
    VehicleDto getVehicleById(int id);
    VehicleDto updateVehicle(VehicleDto vehicleDto, int id);
    void deleteVehicle(int id);
}

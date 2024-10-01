package com.ecommerce.login_service.service.impl;

import com.ecommerce.login_service.dto.VehicleDto;
import com.ecommerce.login_service.exceptions.VehicleNotFoundException;
import com.ecommerce.login_service.model.Vehicle;
import com.ecommerce.login_service.repository.VehicleRepository;
import com.ecommerce.login_service.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public VehicleDto createVehicle(VehicleDto vehicleDto){
        Vehicle vehicle = vehicleRepository.save(mapToEntity(vehicleDto));
        return mapToDto(vehicle);
    }

    @Override
    public List<VehicleDto> getAllVehicle(){
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles.stream().map(vehicle -> mapToDto(vehicle)).collect(Collectors.toList());
    }

    @Override
    public VehicleDto getVehicleById(int vehicleId){
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new VehicleNotFoundException("Vehicle with associated review not found"));
        return mapToDto(vehicle);
    }

    @Override
    public VehicleDto updateVehicle(VehicleDto vehicleDto, int vehicleId){
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new VehicleNotFoundException("Vehicle with associated review not found"));
        vehicle.setId(vehicleDto.getId());
        vehicle.setBrand(vehicleDto.getBrand());
        vehicle.setVariant(vehicleDto.getVariant());
        vehicle.setPrice(vehicleDto.getPrice());
        vehicle.setRating(vehicleDto.getRating());
        vehicle.setYear(vehicleDto.getYear());
        vehicle.setAvailability(vehicleDto.isAvailability());
        return mapToDto(vehicle);
    }

    @Override
    public void deleteVehicle(int vehicleId) {
        vehicleRepository.deleteById(vehicleId);
    }

    private Vehicle mapToEntity(VehicleDto vehicleDto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleDto.getId());
        vehicle.setBrand(vehicleDto.getBrand());
        vehicle.setVariant(vehicleDto.getVariant());
        vehicle.setPrice(vehicleDto.getPrice());
        vehicle.setRating(vehicleDto.getRating());
        vehicle.setYear(vehicleDto.getYear());
        vehicle.setAvailability(vehicleDto.isAvailability());
        return vehicle;
    }

    private VehicleDto mapToDto(Vehicle vehicle) {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(vehicle.getId());
        vehicleDto.setBrand(vehicle.getBrand());
        vehicleDto.setVariant(vehicle.getVariant());
        vehicleDto.setPrice(vehicle.getPrice());
        vehicleDto.setRating(vehicle.getRating());
        vehicleDto.setYear(vehicle.getYear());
        vehicleDto.setAvailability(vehicle.isAvailability());
        return vehicleDto;
    }
}

package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot=new ParkingLot(name,address);
        ParkingLot parkingLot1=parkingLotRepository1.save(parkingLot);
        return parkingLot1;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        Spot spot=new Spot();
        spot.setPricePerHour(pricePerHour);
        if(numberOfWheels==2) spot.setSpotType(SpotType.TWO_WHEELER);
        else if(numberOfWheels==4) spot.setSpotType(SpotType.FOUR_WHEELER);
        else spot.setSpotType(SpotType.OTHERS);
        Optional<ParkingLot> optionalparkingLot=parkingLotRepository1.findById(parkingLotId);
        if(!optionalparkingLot.isPresent()){
            return new Spot();
        }
        spot.setOccupied(false);
        ParkingLot parkingLot=optionalparkingLot.get();
        parkingLot.getSpotList().add(spot);
        parkingLotRepository1.save(parkingLot);
        spotRepository1.save(spot);
        return spot;

    }

    @Override
    public void deleteSpot(int spotId) {
        Spot spot=spotRepository1.findById(spotId).get();
        spotRepository1.deleteById(spotId);
        ParkingLot parkingLot=spot.getParkingLot();
        parkingLot.getSpotList().remove(spot);

    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        Spot spot=spotRepository1.findById(spotId).get();
        ParkingLot parkingLot=parkingLotRepository1.findById(parkingLotId).get();
        parkingLot.getSpotList().remove(spot);

        spot.setPricePerHour(pricePerHour);
        Spot updatedSpot=spotRepository1.save(spot);
        parkingLot.getSpotList().add(updatedSpot);
        return updatedSpot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        ParkingLot parkingLot=parkingLotRepository1.findById(parkingLotId).get();
         List<Spot> spotList=parkingLot.getSpotList();
         for(Spot spot:spotList){
             spotRepository1.delete(spot);
         }
         parkingLotRepository1.delete(parkingLot);
    }
}

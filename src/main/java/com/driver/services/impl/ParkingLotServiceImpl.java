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
        ParkingLot parkingLot=parkingLotRepository1.findById(parkingLotId).get();
        SpotType spotType;
        if(numberOfWheels==2) spotType=SpotType.TWO_WHEELER;
        else if(numberOfWheels==4) spotType=SpotType.FOUR_WHEELER;
        else spotType=SpotType.OTHERS;
        Spot spot=new Spot(spotType,pricePerHour);
        spotRepository1.save(spot);
        parkingLot.getSpotList().add(spot);
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

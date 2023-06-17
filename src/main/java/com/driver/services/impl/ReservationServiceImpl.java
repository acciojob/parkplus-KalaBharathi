package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        Optional<ParkingLot> optionalParkingLot=parkingLotRepository3.findById(parkingLotId);
        if(!optionalParkingLot.isPresent()) {
            throw new Exception("Cannot make reservation");
        }
        Optional<User> optionalUser=userRepository3.findById(userId);
        if(!optionalUser.isPresent()){
            throw new Exception("Cannot make reservation");
        }
        ParkingLot parkingLot=optionalParkingLot.get();
        User user=optionalUser.get();
        List<Spot> spotList=parkingLot.getSpotList();

        Spot spot=null;
        SpotType spotType=null;

        if(numberOfWheels==4) spotType=SpotType.FOUR_WHEELER;
        else if(numberOfWheels==2) spotType=SpotType.TWO_WHEELER;
        else spotType=SpotType.OTHERS;

        for(Spot spot1:spotList){
            if(spot1.getSpotType().compareTo(spotType)==0 || !spot1.getOccupied()){
                spot=spot1;
            }
        }
        Reservation reservation=new Reservation();
        reservation.setNumberOfHours(timeInHours);
        if(spot==null) throw new Exception("Cannot make reservation");

        spot.setOccupied(true);

        spot.getReservationList().add(reservation);

        user.getReservationList().add(reservation);

        spotRepository3.save(spot);
        userRepository3.save(user);
        reservationRepository3.save(reservation);
        return reservation;
    }
}

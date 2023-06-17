package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        Payment payment=new Payment();
        Optional<Reservation> optionalReservation=reservationRepository2.findById(reservationId);
        Reservation reservation=optionalReservation.get();
        Spot spot=reservation.getSpot();
        if(spot.getPricePerHour()!=amountSent) {
            throw new Exception("Insufficient Amount");
        }
        mode=mode.toUpperCase();
        PaymentMode paymentMode=null;
        if((PaymentMode.valueOf(mode).compareTo(PaymentMode.CARD))==0) paymentMode=PaymentMode.CARD;
        else if((PaymentMode.valueOf(mode).compareTo(PaymentMode.CASH))==0) paymentMode=PaymentMode.CASH;
        else if((PaymentMode.valueOf(mode).compareTo(PaymentMode.UPI))==0) paymentMode=PaymentMode.UPI;

        if(paymentMode==null) {
            throw new Exception("Payment mode not detected");
        }
        payment.setPaymentMode(paymentMode);
        payment.isPaymentCompleted(true);
        paymentRepository2.save(payment);
        reservation.setPayment(payment);
        reservationRepository2.save(reservation);
        return payment;
    }
}

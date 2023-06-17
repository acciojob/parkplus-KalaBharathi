package com.driver.model;

import javax.persistence.*;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Boolean paymentCompleted;
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;
    @OneToOne
    @JoinColumn
    private Reservation reservation;

    public Payment() {
    }

    public Payment(PaymentMode paymentMode) {
        this.paymentCompleted = paymentCompleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getPaymentCompleted() {
        return paymentCompleted;
    }

    public void isPaymentCompleted(Boolean paymentCompleted) {
        this.paymentCompleted = paymentCompleted;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}

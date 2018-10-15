/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Consultas;

/**
 *
 * @author a1729756
 */
public class ConsultaPassagem {

    private boolean isOneWay;
    private String origin;
    private String destination;
    private int goingDate;
    private int returnDate;
    private int nPeople;

    public ConsultaPassagem(boolean isOneWay, String origin, String destination, int goingDate, int returnDate, int nPeople) {
        this.isOneWay = isOneWay;
        this.origin = origin;
        this.destination = destination;
        this.goingDate = goingDate;
        this.returnDate = returnDate;
        this.nPeople = nPeople;
    }

    public boolean isOneWay() {
        return isOneWay;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public int getGoingDate() {
        return goingDate;
    }

    public int getReturnDate() {
        return returnDate;
    }

    public int getnPeople() {
        return nPeople;
    }
}

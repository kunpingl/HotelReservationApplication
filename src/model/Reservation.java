package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
  private final Customer customer;
  private final IRoom room;
  private final Date checkInDate;
  private final Date checkOutDate;

  public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
    this.customer = customer;
    this.room = room;
    this.checkInDate = checkInDate;
    this.checkOutDate = checkOutDate;
  }

  public IRoom getRoom() {
    return room;
  }

  public Customer getCustomer() {
    return customer;
  }

  public Date getCheckInDate() {
    return checkInDate;
  }

  public Date getCheckOutDate() {
    return checkOutDate;
  }

  private String formatDate(Date date) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
    return simpleDateFormat.format(date);
  }

  @Override
  public String toString() {
    return "Reservation: {Customer: "
        + customer.getFirstName()
        + " "
        + customer.getLastName()
        + " -> [Room "
        + room.getRoomNumber()
        + ": {"
        + room.getRoomType()
        + ", $"
        + room.getRoomPrice()
        + "} CheckIn: "
        + formatDate(checkInDate)
        + ", CheckOut: "
        + formatDate(checkOutDate)
        + "]";
  }
}

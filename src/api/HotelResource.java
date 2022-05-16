package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HotelResource {
  private static HotelResource INSTANCE;
  private final CustomerService customerService = CustomerService.getInstance();
  private final ReservationService reservationService = ReservationService.getInstance();

  public static HotelResource getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new HotelResource();
    }
    return INSTANCE;
  }

  public Customer getCustomer(String email) {
    return customerService.getCustomer(email);
  }

  public boolean createACustomer(String firstName, String lastName, String email) {
    return customerService.addCustomer(firstName, lastName, email);
  }

  public boolean containsEmail(String email) {
    return customerService.containsEmail(email);
  }

  public IRoom getRoom(String roomNumber) {
    return reservationService.getARoom(roomNumber);
  }

  public Reservation bookARoom(
      String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
    return reservationService.reserveARoom(
        getCustomer(customerEmail), room, checkInDate, checkOutDate);
  }

  public List<Reservation> getCustomersReservations(String customerEmail) {
    return reservationService.getCustomersReservation(getCustomer(customerEmail));
  }

  public List<IRoom> findRoom(Date checkInDate, Date checkOutDate) {
    return new ArrayList<>(reservationService.findRooms(checkInDate, checkOutDate).values());
  }
}

package api;

import model.*;
import service.CustomerService;
import service.ReservationService;

import java.util.List;

public class AdminResource {
  private static AdminResource INSTANCE;
  private final CustomerService customerService = CustomerService.getInstance();
  private final ReservationService reservationService = ReservationService.getInstance();

  public static AdminResource getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new AdminResource();
    }
    return INSTANCE;
  }

  public Customer getCustomer(String email) {
    return customerService.getCustomer(email);
  }

  public boolean addARoom(String roomNumber, Double price, RoomType roomType) {
    IRoom room =
        (price == 0) ? new FreeRoom(roomNumber, roomType) : new Room(roomNumber, price, roomType);
    if (reservationService.addRoom(room)) {
      System.out.println(reservationService.getARoom(roomNumber));
      return true;
    }
    return false;
  }

  public boolean addRoom(List<IRoom> rooms) {
    if (rooms == null) return false;
    for (IRoom eachRoom : rooms) {
      reservationService.addRoom(eachRoom);
    }
    return true;
  }

  public List<IRoom> getAllRooms() {
    return reservationService.getAllRooms();
  }

  public List<Customer> getAllCustomers() {
    return customerService.getAllCustomer();
  }

  public void displayAllReservations() {
    reservationService.printAllReservations();
  }
}

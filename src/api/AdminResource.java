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

  public boolean addRooms(List<IRoom> rooms) {
    if (rooms == null) return false;
    for (IRoom eachRoom : rooms) {
      reservationService.addRoom(eachRoom);
    }
    return true;
  }

  public boolean addCustomers(String[] firstNames, String[] lastNames, String[] emails) {
    if (firstNames.length == lastNames.length && lastNames.length == emails.length) {
      int length = emails.length;
      for (int i = 0; i < length; i++) {
        customerService.addCustomer(firstNames[i], lastNames[i], emails[i]);
      }
      return true;
    }
    return false;
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

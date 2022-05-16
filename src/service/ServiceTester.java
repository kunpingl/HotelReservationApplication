package service;

import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.*;

public class ServiceTester {
  public static void main(String[] args) {
    Map<String, IRoom> allRooms = initRooms();
    Map<String, Customer> allCustomers = initCustomer();

    Customer aob = allCustomers.get("aobjohn@domian.com");
    Customer bob = allCustomers.get("bobjohn@domian.com");
    ReservationService reservationService = ReservationService.getInstance();

    for (IRoom eachRoom : allRooms.values()) {
      reservationService.addRoom(eachRoom);
    }

    // Set Dates
    Calendar calendar = Calendar.getInstance();

    calendar.set(2022, Calendar.JUNE, 13);
    Date checkInDate = calendar.getTime();
    calendar.set(2022, Calendar.JUNE, 15);
    Date checkOutDate = calendar.getTime();

    //    System.out.println(checkInDate);
    //    System.out.println(checkOutDate);

    // Make reservation

    //    System.out.println(reservationService.findRooms(checkInDate, checkOutDate));
    reservationService.reserveARoom(
        aob, reservationService.getARoom("0312"), checkInDate, checkOutDate);
    reservationService.reserveARoom(
        aob, reservationService.getARoom("9012"), checkInDate, checkOutDate);
    reservationService.reserveARoom(
        aob, reservationService.getARoom("9011"), checkInDate, checkOutDate);
    reservationService.reserveARoom(
        bob, reservationService.getARoom("0001"), checkInDate, checkOutDate);
    reservationService.reserveARoom(
        bob, reservationService.getARoom("0002"), checkInDate, checkOutDate);
    reservationService.reserveARoom(
        bob, reservationService.getARoom("0003"), checkInDate, checkOutDate);
    //    System.out.println(reservationService.findRooms(checkInDate, checkOutDate));
    reservationService.printAllReservations();
  }

  private static Map<String, Customer> initCustomer() {
    Map<String, Customer> allCustomers = new HashMap<>();

    allCustomers.put("bobjohn@domian.com", new Customer("Bob", "John", "bobjohn@domian.com"));
    allCustomers.put("aobjohn@domian.com", new Customer("Aob", "John", "aobjohn@domian.com"));
    allCustomers.put("cobjohn@domian.com", new Customer("cob", "John", "cobjohn@domian.com"));
    return allCustomers;
  }

  private static Map<String, IRoom> initRooms() {
    Map<String, IRoom> allRooms = new HashMap<>();

    allRooms.put("0001", new Room("0001", 55.0, RoomType.SINGLE));
    allRooms.put("0002", new Room("0002", 55.0, RoomType.SINGLE));
    allRooms.put("0003", new Room("0003", 65.0, RoomType.SINGLE));
    allRooms.put("0011", new Room("0011", 85.0, RoomType.DOUBLE));
    allRooms.put("0012", new Room("0012", 85.0, RoomType.DOUBLE));
    allRooms.put("0211", new Room("0211", 100.0, RoomType.DOUBLE));
    allRooms.put("0312", new Room("0312", 100.0, RoomType.DOUBLE));
    allRooms.put("9011", new Room("9011", 215.0, RoomType.SINGLE));
    allRooms.put("9012", new Room("9012", 215.0, RoomType.DOUBLE));
    return allRooms;
  }
}

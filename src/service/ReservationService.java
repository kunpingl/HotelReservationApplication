package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import utility.Pair;

import java.util.*;

public class ReservationService {
  private static final Map<String, IRoom> allRooms = new HashMap<>();
  private static final List<Pair<String, Reservation>> allReservations = new ArrayList<>();
  private static ReservationService INSTANCE;

  public static ReservationService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ReservationService();
    }
    return INSTANCE;
  }

  /**
   * Add a new IRoom to the allRooms Map. If the given IRoom is added successful, return true.
   * Otherwise, return false.
   *
   * @param room room to add into allRooms
   * @return true if the room is added successful. Otherwise, return false
   */
  public boolean addRoom(IRoom room) {
    if (allRooms.containsKey(room.getRoomNumber())) return false;
    allRooms.put(room.getRoomNumber(), room);
    return true;
  }

  /**
   * Returns all rooms as an array list
   *
   * @return all rooms as an array list
   */
  public List<IRoom> getAllRooms() {
    return new ArrayList<>(allRooms.values());
  }

  /**
   * Returns an IRoom that associates with the provided room number. If there is no such an IRoom
   * existed in the system, return null.
   *
   * @param roomNumber provided room number for searching an IRoom
   * @return associated IRoom or null if no such an IRoom existed in the system
   */
  public IRoom getARoom(String roomNumber) {
    return allRooms.getOrDefault(roomNumber, null);
  }

  /**
   * Returns true if the checkInDate is before the checkOutDate, or they are the same date.
   * Otherwise, return false.
   *
   * @param checkInDate the date the customer start moving in to the IRoom
   * @param checkOutDate the date the customer moving out of the IRoom
   * @return true if the checkInDate is before the checkOutDate, or they are the same date.
   *     Otherwise, return false.
   */
  public boolean areDatesValid(Date checkInDate, Date checkOutDate) {
    return checkInDate.compareTo(checkOutDate) <= 0;
  }

  /**
   * Returns a reservation for an IRoom in the system. The reservation will be added into the list
   * of allReservations and the list will get sorted. Assume that the checkInDate will be always
   * before the checkOutDate because it is checked in UI layer. Assume that only available room will
   * be reserved for a customer.
   *
   * @param customer customer who has an account in the system and will live in the given IRoom in
   *     the future
   * @param room the IRoom that the customer will be live in
   * @param checkInDate the date the customer start moving in to the IRoom
   * @param checkOutDate the date the customer moving out of the IRoom
   * @return the reservation associated with given customer and room within given date range
   */
  public Reservation reserveARoom(
      Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
    Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
    Pair<String, Reservation> pair = new Pair<>(room.getRoomNumber(), reservation);
    allReservations.add(pair);
    allReservations.sort(Comparator.comparing(Pair::getKey));
    return reservation;
  }

  /**
   * Returns true if the given IRoom is not reserved in the given date range. Assume that the
   * allReservations is sorted by room number. This method is implemented using binary search.
   *
   * @param room IRoom that the customer wants to book
   * @param checkInDate the incoming checkInDate for the IRoom
   * @param checkOutDate the incoming checkOutDate for the IRoom
   * @return true if the given IRoom is not reserved in the given date range. Otherwise, return
   *     false;
   */
  public boolean isReserved(IRoom room, Date checkInDate, Date checkOutDate) {
    int left = 0, right = allReservations.size() - 1;

    while (left <= right) {
      int mid = left + (right - left) / 2;
      Pair<String, Reservation> currentPair = allReservations.get(mid);
      String reservedRoomNumber = currentPair.getKey();
      if (reservedRoomNumber.equals(room.getRoomNumber())) {
        Reservation currentReservation = currentPair.getValue();
        Date reservedCheckInDate = currentReservation.getCheckInDate();
        Date reservedCheckOutDate = currentReservation.getCheckOutDate();
        return checkInDate.compareTo(reservedCheckOutDate) < 0
            && checkOutDate.compareTo(reservedCheckInDate) > 0;
      } else if (reservedRoomNumber.compareTo(room.getRoomNumber()) < 0) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }
    return false;
  }

  /**
   * Returns all available IRooms in the given date range. Time Complexity: O(NlogN) because
   * isReserved is O(logN)
   *
   * @param checkInDate Date that the customer wants to move in
   * @param checkOutDate Date that the customer wants to move out
   * @return a hashmap that key is the room number and the value is the associated IRoom
   */
  public HashMap<String, IRoom> findRooms(Date checkInDate, Date checkOutDate) {
    HashMap<String, IRoom> allAvailableRooms = new HashMap<>();

    for (IRoom eachRoom : allRooms.values()) {
      if (!isReserved(eachRoom, checkInDate, checkOutDate)) {
        allAvailableRooms.put(eachRoom.getRoomNumber(), eachRoom);
      }
    }
    return allAvailableRooms;
  }

  /**
   * Returns a list storing all reservations made by given customer.
   *
   * @param customer given customer for searching his or her reservations
   * @return a list storing all reservations made by given customer
   */
  public List<Reservation> getCustomersReservation(Customer customer) {
    List<Reservation> customersReservations = new ArrayList<>();

    for (Pair<String, Reservation> eachPair : allReservations) {
      Reservation currentReservation = eachPair.getValue();
      if (currentReservation.getCustomer().equals(customer)) {
        customersReservations.add(currentReservation);
      }
    }
    return customersReservations;
  }

  /** Prints all reservations in the system. */
  public void printAllReservations() {
    for (Pair<String, Reservation> eachPair : allReservations) {
      Reservation currentReservation = eachPair.getValue();
      System.out.println(currentReservation);
    }
  }
}

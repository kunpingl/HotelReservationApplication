package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

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

  int getLeftBound(IRoom room) {
    int left = 0, right = allReservations.size() - 1;

    while (left <= right) {
      int mid = left + (right - left) / 2;
      Pair<String, Reservation> currentPair = allReservations.get(mid);
      String reservedRoomNumber = currentPair.getKey();
      if (reservedRoomNumber.compareTo(room.getRoomNumber()) >= 0) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }

    return (left < allReservations.size()
            && allReservations.get(left).getKey().equals(room.getRoomNumber()))
        ? left
        : -1;
  }

  int getRightBound(IRoom room) {
    int left = 0, right = allReservations.size() - 1;

    while (left <= right) {
      int mid = left + (right - left) / 2;
      Pair<String, Reservation> currentPair = allReservations.get(mid);
      String reservedRoomNumber = currentPair.getKey();
      if (reservedRoomNumber.compareTo(room.getRoomNumber()) <= 0) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return (right >= 0 && allReservations.get(right).getKey().equals(room.getRoomNumber()))
        ? right
        : -1;
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
  boolean isReserved(IRoom room, Date checkInDate, Date checkOutDate) {
    int leftBound = getLeftBound(room);
    int rightBound = getRightBound(room);
    if (leftBound == -1 || rightBound == -1) return false;

    for (int i = leftBound; i <= rightBound; i++) {
      Reservation currentReservation = allReservations.get(i).getValue();
      Date reservedCheckInDate = currentReservation.getCheckInDate();
      Date reservedCheckOutDate = currentReservation.getCheckOutDate();
      if (!isRoomAvailableDuringDateRange(
          reservedCheckInDate, reservedCheckOutDate, checkInDate, checkOutDate)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns true if the room is available at the given date range. Since room needs to be cleaned,
   * for the same room, same day check-in and check-out is not working. For example, if room 0001
   * was checked out 05-20-2022, the new check-in date for room 0001 should be 05-21-2022 or later.
   *
   * @param reservedCheckInDate the reserved check in date for this room
   * @param reservedCheckOutDate the reserved check out date for this room
   * @param newCheckInDate the new check in date that the customer wants to move in for this room
   * @param newCheckOutDate the new check out date that the customer wants to move out for this room
   * @return true if the room is available at the given date range. Otherwise, return false
   */
  private boolean isRoomAvailableDuringDateRange(
      Date reservedCheckInDate,
      Date reservedCheckOutDate,
      Date newCheckInDate,
      Date newCheckOutDate) {
    return newCheckOutDate.compareTo(reservedCheckInDate) < 0
        || newCheckInDate.compareTo(reservedCheckOutDate) > 0;
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

  public static class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
      this.key = key;
      this.value = value;
    }

    public K getKey() {
      return key;
    }

    public void setKey(K key) {
      this.key = key;
    }

    public V getValue() {
      return value;
    }

    public void setValue(V value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return "{Key: " + key + ", Value: " + value + "}";
    }
  }
}

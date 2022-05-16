package model;

public class Room implements IRoom {
  private final String roomNumber;
  private final Double price;
  private final RoomType roomType;

  public Room(String roomNumber, Double price, RoomType roomType) {
    this.roomNumber = roomNumber;
    this.price = price;
    this.roomType = roomType;
  }

  @Override
  public String getRoomNumber() {
    return roomNumber;
  }

  @Override
  public Double getRoomPrice() {
    return price;
  }

  @Override
  public RoomType getRoomType() {
    return roomType;
  }

  @Override
  public boolean isFree() {
    return price == 0;
  }

  @Override
  public String toString() {
    return "Room " + getRoomNumber() + ": {" + getRoomType() + ", $" + getRoomPrice() + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) return false;
    if (this == o) return true;
    if (this.getClass() != o.getClass()) return false;
    return roomNumber.equals(((Room) o).roomNumber);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 31 * hash + ((roomNumber == null) ? 0 : roomNumber.hashCode());
    return hash;
  }
}

package UI;

import api.AdminResource;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TestingData {
  // [Min, Max] = [1, 9999]
  private static final int DATA_SIZE = 10;
  private static final double MAX_PRICE = 200.0;
  private static final double MIN_PRICE = 0.0;
  private static final int ROOM_TYPE_BOUND = 2;
  private static final int NAME_TOKEN_LENGTH = 7;
  private static final int EMAIL_TOKEN_LENGTH = 5;
  private static final AdminResource ADMIN_RESOURCE = AdminResource.getInstance();

  private static final Random RANDOM = new Random();
  private static TestingData INSTANCE;

  protected static TestingData getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new TestingData();
    }
    return INSTANCE;
  }

  protected void initTestingData() {
    initAllRooms();
    initAllCustomers();
  }

  private void initAllCustomers() {
    String[] firstNames = initNames();
    String[] lastNames = initNames();
    String[] emails = initEmails();
    ADMIN_RESOURCE.addCustomers(firstNames, lastNames, emails);
  }

  private String[] initEmails() {
    String[] emails = new String[DATA_SIZE];
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < DATA_SIZE; i++) {
      int tokenLength = RANDOM.nextInt(EMAIL_TOKEN_LENGTH) + 1;
      sb.append(initAToken(tokenLength)).append("@").append(initAToken(tokenLength)).append(".com");
      emails[i] = sb.toString();
      // empty the stringBuilder
      sb.setLength(0);
    }
    return emails;
  }

  private String[] initNames() {
    String[] names = new String[DATA_SIZE];
    for (int i = 0; i < names.length; i++) {
      int nameLength = RANDOM.nextInt(NAME_TOKEN_LENGTH) + 1;
      names[i] = initAToken(nameLength);
    }
    return names;
  }

  private String initAToken(int nameLength) {
    String allLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz";
    StringBuilder sb = new StringBuilder(nameLength);
    for (int i = 0; i < nameLength; i++)
      sb.append(allLetters.charAt(RANDOM.nextInt(allLetters.length())));
    return sb.toString();
  }

  private void initAllRooms() {
    String[] roomNumbers = initRoomNumbers();
    Double[] prices = initPrices();
    RoomType[] roomTypes = initRoomType();

    List<IRoom> rooms = new LinkedList<>();
    for (int i = 0; i < DATA_SIZE; i++) {
      rooms.add(new Room(roomNumbers[i], prices[i], roomTypes[i]));
    }
    ADMIN_RESOURCE.addRooms(rooms);
  }

  private RoomType[] initRoomType() {
    RoomType[] roomTypes = new RoomType[DATA_SIZE];
    for (int i = 0; i < roomTypes.length; i++) {
      int p = RANDOM.nextInt(ROOM_TYPE_BOUND);
      roomTypes[i] = (p == 0) ? RoomType.SINGLE : RoomType.DOUBLE;
    }
    return roomTypes;
  }

  private Double[] initPrices() {
    Double[] prices = new Double[DATA_SIZE];
    for (int i = 0; i < prices.length; i++) {
      double price = RANDOM.nextDouble() * (MAX_PRICE - MIN_PRICE) + MIN_PRICE;
      prices[i] = Math.floor(price * 100) / 100;
    }
    return prices;
  }

  private String[] initRoomNumbers() {
    String[] roomNumbers = new String[DATA_SIZE];
    for (int i = 1; i < roomNumbers.length; i++) {
      roomNumbers[i - 1] = String.format("%04d", i);
    }
    roomNumbers[DATA_SIZE - 1] = String.format("%04d", DATA_SIZE);
    return roomNumbers;
  }
}

package service;

import api.AdminResource;
import model.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class serviceTester {
    private static final ReservationService RESERVATION_SERVICE = ReservationService.getInstance();
    private static final AdminResource ADMIN_RESOURCE = AdminResource.getInstance();
    private static final Calendar CALENDAR = Calendar.getInstance();
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");

    public static void main(String[] args) {
        Customer customer1 = new Customer("test1", "user1", "test1@gmail.com");
        IRoom room = new Room("1111", 1.0, RoomType.SINGLE);
        IRoom room2 = new Room("1112", 1.0, RoomType.SINGLE);
        IRoom room3 = new Room("1113", 1.0, RoomType.SINGLE);

        Date checkInDate = setDate(2022, 5, 20);
        Date checkOutDate = setDate(2022, 5, 25);
        RESERVATION_SERVICE.reserveARoom(customer1, room, checkInDate, checkOutDate);
        RESERVATION_SERVICE.reserveARoom(customer1, room2, checkInDate, checkOutDate);

        checkInDate = setDate(2022, 5, 27);
        checkOutDate = setDate(2022, 6, 1);
        RESERVATION_SERVICE.reserveARoom(customer1, room, checkInDate, checkOutDate);


        checkInDate = setDate(2022, 6, 7);
        checkOutDate = setDate(2022, 6, 14);
        RESERVATION_SERVICE.reserveARoom(customer1, room, checkInDate, checkOutDate);

        checkInDate = setDate(2022, 5, 27);
        checkOutDate = setDate(2022, 6, 1);
        RESERVATION_SERVICE.reserveARoom(customer1, room, checkInDate, checkOutDate);

        System.out.println();


        RESERVATION_SERVICE.printAllReservations();
    }

    private static Date setDate(int year, int month, int day) {
        CALENDAR.set(year, month - 1, day);
        return CALENDAR.getTime();
    }

    private static String formatDate(Date date) {
        return SIMPLE_DATE_FORMAT.format(date);
    }

}



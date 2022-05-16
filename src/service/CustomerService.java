package service;

import model.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerService {
  private static final Map<String, Customer> allCustomers = new HashMap<>();
  private static CustomerService INSTANCE;

  public static CustomerService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new CustomerService();
    }
    return INSTANCE;
  }

  /**
   * Add a new customer to the allCustomers Map if the input email is new and unique. If the input
   * customer is added successful, return true. Otherwise, return false Assume the provided email is
   * always valid (in format: name@domain.com) because email's validation will be done at UI layer
   * when user is entering inputs.
   *
   * @param firstName the customer's firstname
   * @param lastName the customer's lastname
   * @param email the customer's email. It should be unique and can be used as customer's ID
   * @return true if the input customer (information) is new and has valid unique email. Otherwise,
   *     return false
   */
  public boolean addCustomer(String firstName, String lastName, String email) {
    //TODO: Delete since this checking logic might be trivial
    if (containsEmail(email)) return false;
    allCustomers.put(email, new Customer(firstName, lastName, email));
    return true;
  }

  /**
   * Returns true if our system contains the given email.
   *
   * @param email given email to check
   * @return true if our system contains the given email. Otherwise, return false;
   */
  public boolean containsEmail(String email) {
    return allCustomers.containsKey(email);
  }

  /**
   * Returns a customer that associates with the provided email. If there is no such a customer
   * existed in the system, return null.
   *
   * @param customerEmail provided email for searching a customer
   * @return associated customer or null if no such a customer existed in the system
   */
  public Customer getCustomer(String customerEmail) {
    return allCustomers.getOrDefault(customerEmail, null);
  }

  /**
   * Returns the collection that stores all data of customers
   *
   * @return An ArrayList contains all customers
   */
  public List<Customer> getAllCustomer() {
    return new ArrayList<>(allCustomers.values());
  }
}

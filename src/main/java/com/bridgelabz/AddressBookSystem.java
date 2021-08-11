
/*********************************************************************************************  * Purpose : Implementation Of Address Book System Program Using hashMap,ArrayList and Stream
 * @author Ganesh Gavad
 * @version 1.0;
 * @since 31.07.21
 * *******************************************************************************************/
package com.bridgelabz;

import au.com.bytecode.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class AddressBookSystem {
    static Scanner sc = new Scanner(System.in);
    private ArrayList<Person> personList = null;
    private Map<String,ArrayList<Person>> contactBook = new HashMap<>();
    public String city;

    /*
     * UC1 Taking User Input for person detail
     * UC2 this method adds object person and its element to ArrayList
     */
    private void addNewContact() {
        try {
            System.out.println("Which City You Want To Add");
            city = sc.next();
            Person person = new Person();
            System.out.println("Enter First Name");
            String firstName = sc.next();
            person.setFirstName(firstName);
            System.out.println("Enter last Name");
            String lastName = sc.next();
            person.setLastName(lastName);
            System.out.println("Enter the Address");
            String address = sc.next();
            person.setAddress(address);
            System.out.println("Enter the City");
            String city = sc.next();
            person.setCity(city);
            System.out.println("Enter the State");
            String state = sc.next();
            person.setState(state);
            System.out.println("Enter the PinCode");
            String pinCode = sc.next();
            person.setPinCode(pinCode);
            System.out.println("Enter the Phone Number");
            String phoneNumber = sc.next();
            person.setPhoneNumber(phoneNumber);
            System.out.println("Enter the Email");
            String emailId = sc.next();
            person.setEmailId(emailId);

            if (contactBook.containsKey(city)) {
                contactBook.get(city).add(person);
            } else {
                personList = new ArrayList<>();
                personList.add(person);
                contactBook.put(city, personList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
     * UC3
     * Ability to edit existing contact of person using there first name
     */
    private void editContact() {
        try {
            System.out.println("Enter City to which you want edit person details");
            city = sc.next();
            String enterName;
            System.out.println("Enter First Name To edit Your Contact Details");
            enterName = sc.next();
            for (int i = 0; i <= contactBook.get(city).size(); i++) {
                if (contactBook.get(city).get(i).equals(enterName)) ;
                {
                    System.out.println("Person Found, Enter option that you want to edit");
                    System.out.println("Enter\n1.First Name\n2.Last Name\n3.Address\n4.city" +
                            "\n5.State\n6.Pin Code\n7.Phone Number\n8.EmailId");
                    int check;
                    check = sc.nextInt();
                    switch (check) {
                        case 1:
                            System.out.println("Enter new first name");
                            contactBook.get(city).get(i).setFirstName(sc.next());
                            break;
                        case 2:
                            System.out.println("Enter new last name");
                            contactBook.get(city).get(i).setLastName(sc.next());
                            break;
                        case 3:
                            System.out.println("Enter new Address");
                            contactBook.get(city).get(i).setAddress(sc.next());
                            break;
                        case 4:
                            System.out.println("Enter new city");
                            contactBook.get(city).get(i).setCity(sc.next());
                            break;
                        case 5:
                            System.out.println("Enter new state");
                            contactBook.get(city).get(i).setState(sc.next());
                            break;
                        case 6:
                            System.out.println("Enter new zip");
                            contactBook.get(city).get(i).setPinCode(sc.next());
                            break;
                        case 7:
                            System.out.println("Enter new phone number");
                            contactBook.get(city).get(i).setPhoneNumber(sc.next());
                            break;
                        case 8:
                            System.out.println("Enter new email");
                            contactBook.get(city).get(i).setEmailId(sc.next());
                            break;
                        default:
                            System.out.println("Invalid Entry");
                    }
                }
            }
        } catch ( Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * UC4
     * This method removes person from list using  first name.
     */
    private void deletePerson () {
        try {
            String enteredName;
            System.out.println("Enter First name of contact to delete it ");
            enteredName = sc.next();
            for (int i = 0; i < contactBook.get(city).size(); i++) {
                if (contactBook.get(city).get(i).getFirstName().equals(enteredName))
                    contactBook.get(city).remove(i);
            }
            System.out.println("Person removed from Address book");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*
     *UC5
     * Added show method to see multiple person contact list
     */
    public void showPerson(){
        System.out.println(contactBook);
    }

    /**
     *UC8 : Ability to search person across all cities
     * And here name of cities are stored as key of hashmap
     * for loop iterates over all key
     * use of stream to search person by using filter method of stream
     */
    private void searchPersonInCity() {
        try {
            System.out.println("Enter name to search in all book");
            String nameToSearch = sc.next();
            for (String key : contactBook.keySet()) {
                contactBook.get(key).stream().filter(person -> person.getFirstName().equals(nameToSearch)).
                        collect(Collectors.toList()).forEach(Person -> System.out.println(Person.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *UC 9 : ability to view person by city, get city name by user input
     * using hashmap and key value is city name, if hashmap contain that user input
     * city as key it will display all contact in list using stream
     * else display city not found
     **/
    private void viewPersonByCity() {
        try {
            System.out.println("Enter Name Of city to find all person contact ");
            String cityName = sc.next();
            if (contactBook.containsKey(cityName)) {
                contactBook.get(cityName).stream().
                        forEach(person -> System.out.println(person.toString()));
            } else {
                System.out.println("City not Found");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *UC 10 : ability to get number of person by city, get city name by user input
     * using hashmap and key value is city name, if hashmap contain that user input
     * city as key it will collect all contact in stream and by using count method,
     * here i have used count long variable to print result.
     * else display city not found
     **/
    private void CountPersonByCity() {
        try {
            System.out.println("Enter Name Of city to find Number Of person contact ");
            String cityName = sc.next();
            if (contactBook.containsKey(cityName)) {
                long count = contactBook.get(cityName).stream().count();
                System.out.println(count);
            } else {
                System.out.println("City not Found");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * UC11 Sort by First name in Alphabetical order
     */
    private void sortByPersonFirstName() {
        try {
            for (String key : contactBook.keySet()) {
                contactBook.get(key).stream().sorted(Comparator.comparing(Person::getFirstName)).collect(Collectors.toList()).
                        forEach(Person-> System.out.println(Person.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * UC12 Sort by city name
     */
    private void sortByCity() {
        try {
            for (String key : contactBook.keySet()) {
                contactBook.get(key).stream().sorted(Comparator.comparing(Person::getCity)).collect(Collectors.toList())
                        .forEach(Person -> System.out.println(Person.toString()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * UC13 Write to file
     * Purpose : Writes contact book to file Person.txt
     * @throws IOException
     */
    private void writeToFile() throws IOException {

        FileOutputStream writeData = new FileOutputStream("Person.txt");
        ObjectOutputStream ObjectStream = new ObjectOutputStream(writeData);

        try {
            ObjectStream.writeObject(contactBook);
            ObjectStream.flush();
        }
        catch (Exception e) {
            e.printStackTrace();

        } finally {
            ObjectStream.close();
        }
    }

    /**
     * Reads objects from file
     * Prints them on console
     * @throws IOException
     */
    private void readFromFile() throws IOException {

        FileInputStream writeData = new FileInputStream("Person.txt");
        ObjectInputStream ObjectStream = new ObjectInputStream(writeData);

        try {
            Map<String ,ArrayList<Person>> newContactBook = (Map<String, ArrayList<Person>>) ObjectStream.readObject();
            System.out.println(newContactBook);
        }
        catch (Exception e) {
            e.printStackTrace();

        } finally {
            ObjectStream.close();
        }
    }

    /**
     * UC 14
     * Ability to Read the Address Book with Persons Contact
     * as CSV File
     */
    public void readCsv() {
        try (CSVReader reader = new CSVReader(new FileReader("contacts.csv"));)
        {
            String[] nextLine;
            while((nextLine = reader.readNext()) != null) {
                if(nextLine != null) {
                    System.out.println(Arrays.toString(nextLine));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Read complete");
    }

    /**
     * UC 14
     * Ability to write the Address Book with Persons Contact
     * as CSV File
     */

    public void writeCsv(){
        try{
            FileWriter fileWriter = new FileWriter("contacts.csv");
            ColumnPositionMappingStrategy mappingStrategy =
                    new ColumnPositionMappingStrategy();
            mappingStrategy.setType(Person.class);

            String[] columns = new String[]{"FirstName","LastName","Address",
                    "City","state","pinCode","phoneNumber","emailId"};
            mappingStrategy.setColumnMapping(columns);

            StatefulBeanToCsvBuilder<Person> builder =
                    new StatefulBeanToCsvBuilder(fileWriter);

            StatefulBeanToCsv beanWriter =
                    builder.withMappingStrategy(mappingStrategy).build();

            beanWriter.write(personList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes contacts to a json file.
     */
    public void convertToJson() {
        String result="";
        try{
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.writeValueAsString(personList);
            for(Person person : personList) {
                mapper.writeValue(new File("contact1.json"), person);
            }

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }

    public static void main(String[] args) {
        try {
            System.out.println("Welcome to Address Book Program");
            AddressBookSystem contact = new AddressBookSystem();
            boolean isExit = false;
            while (!isExit) {
                System.out.println("Enter your choice \n1.Add New Contact\n2.Edit Contact\n3.Delete Contact" +
                        "\n4.Show Person Contact\n5.Search Person\n6.Search By City\n7 Count Person By city" +
                        "\n8.Sort By Person Name\n9. Sort By City\n10. Write to file\n11.Read From File\n" +
                        "12.Read From Csv\n13.Write To Csv\n14.Convert To Json\n15.Exit");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        contact.addNewContact();
                        break;
                    case 2:
                        contact.editContact();
                        break;
                    case 3:
                        contact.deletePerson();
                        break;
                    case 4:
                        contact.showPerson();
                        break;
                    case 5:
                        contact.searchPersonInCity();
                        break;
                    case 6:
                        contact.viewPersonByCity();
                        break;
                    case 7:
                        contact.CountPersonByCity();
                        break;
                    case 8:
                        contact.sortByPersonFirstName();
                        break;
                    case 9:
                        contact.sortByCity();
                        break;
                    case 10:
                        contact.writeToFile();
                        break;
                    case 11:
                        contact.readFromFile();
                        break;
                    case 12 :
                        contact.readCsv();
                        break;
                    case 13:
                        contact.writeCsv();
                        break;
                    case 14:
                        contact.convertToJson();
                        break;
                    case 15:
                        isExit = true;
                        break;
                    default:
                        System.out.println("Invalid Input");
                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

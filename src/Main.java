import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.util.stream.Collectors.toList;

public class Main {

    Main(){

        Repository repo = new Repository();
        Scanner scanner = new Scanner(System.in);
        Customer logedInCustomer = new Customer();
        Order order = new Order();
        Shoe shoe = new Shoe();

        while(true) {
            System.out.println("Skriv in ditt användarnamn (ditt namn)");
            String username = scanner.nextLine().trim();
            System.out.println("Ditt lösenord");
            String password = scanner.nextLine().trim();
            List<Customer> customerList = new ArrayList<>();
            customerList = repo.getCustomers();
            for (Customer customer : customerList) {
                if (customer.getName().equalsIgnoreCase(username) && customer.getPassword().equalsIgnoreCase(password)) {
                    System.out.println("Inloggning lyckades");
                    logedInCustomer = customer;
                    List<Order> orderList = new ArrayList<>();
                    orderList = repo.getOrder();
                    for (Order order1 : orderList) {
                        if(order1.getCustomer() == customer.getId()){
                            order=order1;
                        }
                    }
                }
            }
            if (logedInCustomer.getName() == null) {
                System.out.println("Användarnamnet eller lösenordet är fel");
            } else {
                System.out.println(logedInCustomer.getAddress());
            }
            System.out.println("Vilken sko vill du lägga till i din beställning, ange märke och skonummer");
            List<Shoe> shoeList = new ArrayList<>();
            shoeList = repo.getShoes();
            for (Shoe s : shoeList) {
                System.out.println(s.getBrand() + " " + s.getSize() + " " + s.getColor() + " " + s.getPrice());
            }
            String chosenShoe = scanner.nextLine().trim();
            for (Shoe s : shoeList) {
                if(chosenShoe.equalsIgnoreCase(s.getBrand() + " " + s.getSize())){
                    shoe=s;
                }
            }
            repo.addShoeToCart(logedInCustomer.getId(), order.getId(), shoe.getId());
            System.out.println(shoe.getBrand() + " " + shoe.getSize() + " tillagd i din korg");
        }
    }
    public static void main(String[] args) {
        Main main = new Main();
    }
}
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GUIShop extends JFrame {
    String username;
    String password;

    Repository repo = new Repository();
    Customer logedInCustomer = new Customer();
    Order order = new Order();
    Shoe shoe = new Shoe();
    List<Shoe> shoeList;

    private JPanel loginPanel = new JPanel();
    private JPanel shopPanel = new JPanel();
    private JPanel southPanel = new JPanel();

    private JLabel usernameLabel = new JLabel("Användarnamn: ");
    private JTextField usernameField = new JTextField();
    private JLabel passwordLabel = new JLabel("Lösenord: ");
    private JPasswordField passwordField = new JPasswordField();
    private JLabel loginSuccess = new JLabel();
    private JButton loginButton = new JButton("Enter");
    private JButton newLoginButton = new JButton("Försök igen");

    private JLabel messageLabel = new JLabel();
    private JButton moreItemsButton = new JButton("Lägg till fler");
    private JLabel orderAmountLabel = new JLabel();



    GUIShop(){
        login();

        pack();
        setSize(1000,500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void login(){
        loginPanel.removeAll();

        setLayout(new BorderLayout());
        add(loginPanel , BorderLayout.SOUTH);

        loginPanel.setLayout(new GridLayout(5,1));
        loginPanel.add(usernameLabel); loginPanel.add(usernameField);
        loginPanel.add(passwordLabel); loginPanel.add(passwordField);
        loginPanel.add(loginButton);

        usernameLabel.setSize(300, 50);
        passwordLabel.setSize(300, 50);

        loginButton.addActionListener(l -> {
            username = usernameField.getText();
            password = passwordField.getText();
            boolean succesfullLogin = validateLogin(username, password);
            if(succesfullLogin){
                theShoeShop();
            }
            else {
                loginPanel.removeAll();
                loginSuccess.setText("Inloggningen misslyckades, var god försök igen");
                loginPanel.add(loginSuccess, BorderLayout.NORTH);
                loginPanel.add(newLoginButton);
                newLoginButton.addActionListener(n -> login());
                loginPanel.revalidate();
                loginPanel.repaint();
            }
        });



        loginPanel.revalidate();
        loginPanel.repaint();
    }

    public boolean validateLogin(String username, String password){
        List<Customer> customerList = repo.getCustomers();
        for (Customer customer : customerList) {
            if (customer.getName().equals(username) && customer.getPassword().equals(password)) {
                logedInCustomer = customer;
                List<Order> orderList = repo.getOrder();
                for (Order order1 : orderList) {
                    if(order1.getCustomer() == customer.getId() && !order1.isPayedOrder()){
                        order=order1;
                        break;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public void theShoeShop(){
        getContentPane().removeAll();
        shopPanel.removeAll();
        shopPanel.setLayout(new GridLayout(0,3));
        JScrollPane scrollPane = new JScrollPane(shopPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        orderAmountLabel.setText("Antal varor: " + repo.getOrderAmount(order.getId()));
        add(orderAmountLabel, BorderLayout.NORTH);

        shoeList = repo.getShoes();
        for (Shoe s : shoeList) {
            ImageIcon imageIcon;
            try {
                File imagefile = new File(s.getShoePath());
                if(imagefile.exists()){
                    imageIcon = new ImageIcon(imagefile.getAbsolutePath());
                    Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    imageIcon = new ImageIcon(image);

                    JLabel imageLabel = new JLabel(imageIcon);
                    shopPanel.add(imageLabel);
                }
                else {
                    System.out.println("File not found");
                    continue;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            JLabel shoeInfo = new JLabel(s.getBrand() + " " + s.getSize() + " " + s.getColor() + " " + s.getPrice() + "kr");
            shoeInfo.setPreferredSize(new Dimension(50, 5));
            ShoeButton shoeButton = new ShoeButton(s.getId());
            JButton button = shoeButton.getShoeButton();
            shopPanel.add(shoeInfo);
            shopPanel.add(button);
            button.setPreferredSize(new Dimension(50, 5));
            button.addActionListener(l -> {
                addShoeToCart(s.getId());
            });
        }
        shopPanel.revalidate();
        shopPanel.repaint();
        revalidate();
        repaint();
    }

    public void addShoeToCart(int shoeId){
        String message = repo.addShoeToCart(logedInCustomer.getId(), order.getId(), shoeId);
        orderAmountLabel.setText("Antal varor: " + repo.getOrderAmount(order.getId()));
        messageLabel.setText(message);
        add(southPanel, BorderLayout.SOUTH);
        southPanel.add(messageLabel); southPanel.add(moreItemsButton);
        moreItemsButton.addActionListener(l -> theShoeShop());

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        GUIShop shop = new GUIShop();
    }
}

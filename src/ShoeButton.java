import javax.swing.*;

public class ShoeButton {
    private JButton shoeButton;
    private int buttonId;

    public ShoeButton(int shoeId) {
        this.buttonId = shoeId;
        this.shoeButton = new JButton("Lägg till");
        }

    public JButton getShoeButton(){
        return this.shoeButton;
    }

    public int getButtonId() {
        return this.buttonId;
    }
}

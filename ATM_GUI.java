import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Account Class
class Account {

    int accountNumber;
    String name;
    int pin;
    double balance;

    Account(int accountNumber, String name, int pin, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.pin = pin;
        this.balance = balance;
    }

    void deposit(double amount) {
        balance += amount;
    }

    boolean withdraw(double amount) {

        if (amount > balance) {
            return false;
        }

        balance -= amount;
        return true;
    }

    public String toString() {
        return "Account Number : " + accountNumber +
                "\nName : " + name +
                "\nBalance : ₹" + balance;
    }
}

// Main ATM GUI
public class ATM_GUI extends JFrame implements ActionListener {

    ArrayList<Account> accounts = new ArrayList<>();

    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);

    // Login Panel
    JTextField accField;
    JPasswordField pinField;
    JButton loginBtn;

    // ATM Panel
    JLabel welcomeLabel;
    JButton depositBtn, withdrawBtn, balanceBtn, logoutBtn;

    Account currentAccount = null;

    ATM_GUI() {

        // Demo Accounts
        accounts.add(new Account(1001, "Ritesh", 1234, 50012340));
        accounts.add(new Account(1002, "Himanshi", 4321, 8054300));
        accounts.add(new Account(1003, "Om", 1111, 1200000));
        setTitle("ATM MACHINE");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createLoginPanel();
        createATMPanel();

        add(mainPanel);

        setVisible(true);
    }

    // Login Screen
    void createLoginPanel() {

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBackground(new Color(20, 20, 20));

        JLabel title = new JLabel("ATM LOGIN");
        title.setBounds(170, 30, 200, 40);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));

        JLabel accLabel = new JLabel("Account Number");
        accLabel.setBounds(70, 110, 150, 30);
        accLabel.setForeground(Color.WHITE);

        accField = new JTextField();
        accField.setBounds(220, 110, 180, 30);

        JLabel pinLabel = new JLabel("PIN");
        pinLabel.setBounds(70, 170, 150, 30);
        pinLabel.setForeground(Color.WHITE);

        pinField = new JPasswordField();
        pinField.setBounds(220, 170, 180, 30);

        loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(170, 250, 150, 40);
        loginBtn.setBackground(Color.GREEN);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.addActionListener(this);

        loginPanel.add(title);
        loginPanel.add(accLabel);
        loginPanel.add(accField);
        loginPanel.add(pinLabel);
        loginPanel.add(pinField);
        loginPanel.add(loginBtn);

        mainPanel.add(loginPanel, "login");
    }

    // ATM Dashboard
    void createATMPanel() {

        JPanel atmPanel = new JPanel();
        atmPanel.setLayout(null);
        atmPanel.setBackground(new Color(30, 30, 30));

        welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setBounds(120, 30, 300, 40);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        depositBtn = new JButton("Deposit");
        withdrawBtn = new JButton("Withdraw");
        balanceBtn = new JButton("Check Balance");
        logoutBtn = new JButton("Logout");

        depositBtn.setBounds(150, 100, 180, 40);
        withdrawBtn.setBounds(150, 160, 180, 40);
        balanceBtn.setBounds(150, 220, 180, 40);
        logoutBtn.setBounds(150, 280, 180, 40);

        depositBtn.addActionListener(this);
        withdrawBtn.addActionListener(this);
        balanceBtn.addActionListener(this);
        logoutBtn.addActionListener(this);

        atmPanel.add(welcomeLabel);
        atmPanel.add(depositBtn);
        atmPanel.add(withdrawBtn);
        atmPanel.add(balanceBtn);
        atmPanel.add(logoutBtn);

        mainPanel.add(atmPanel, "atm");
    }

    // Search Account
    Account searchAccount(int accNo, int pin) {

        for (Account acc : accounts) {

            if (acc.accountNumber == accNo && acc.pin == pin) {
                return acc;
            }
        }

        return null;
    }

    // Button Actions
    public void actionPerformed(ActionEvent e) {
if (e.getSource() == loginBtn) {

    try {

        int accNo =
                Integer.parseInt(accField.getText().trim());

        int pin =
                Integer.parseInt(pinField.getText().trim());

        currentAccount = searchAccount(accNo, pin);

        if (currentAccount != null) {

            welcomeLabel.setText(
                    "Welcome, " + currentAccount.name);

            cardLayout.show(mainPanel, "atm");

        } else {

            JOptionPane.showMessageDialog(this,
                    "Invalid Account Number or PIN");
        }

    } catch (Exception ex) {

        JOptionPane.showMessageDialog(this,
                "Please Enter Valid Numbers");
    }
}
        // Deposit
        else if (e.getSource() == depositBtn) {

            double amount = Double.parseDouble(
                    JOptionPane.showInputDialog(
                            "Enter Deposit Amount"));

            currentAccount.deposit(amount);

            JOptionPane.showMessageDialog(this,
                    "₹" + amount + " Deposited Successfully");
        }

        // Withdraw
        else if (e.getSource() == withdrawBtn) {

            double amount = Double.parseDouble(
                    JOptionPane.showInputDialog(
                            "Enter Withdraw Amount"));

            boolean success =
                    currentAccount.withdraw(amount);

            if (success) {

                JOptionPane.showMessageDialog(this,
                        "₹" + amount + " Withdraw Successful");

            } else {

                JOptionPane.showMessageDialog(this,
                        "Insufficient Balance");
            }
        }

        // Balance
        else if (e.getSource() == balanceBtn) {

            JOptionPane.showMessageDialog(this,
                    currentAccount.toString());
        }

        // Logout
        else if (e.getSource() == logoutBtn) {

            currentAccount = null;

            accField.setText("");
            pinField.setText("");

            cardLayout.show(mainPanel, "login");
        }
    }

    // Main Method
    public static void main(String[] args) {

        new ATM_GUI();
    }
}
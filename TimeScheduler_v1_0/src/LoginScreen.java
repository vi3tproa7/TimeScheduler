package src;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Component;
// import java.awt.*;

// login JFrame as the application's login frame
public class LoginScreen extends JFrame {
    ImageIcon icon = new ImageIcon("TimeScheduler_v1_0/lib/TimeSchedulerIcon.png");
    Username usernamePanel = new Username();
    Password passwordPanel = new Password();
    JPanel buttonPanel = new JPanel();
    User user = new User();

    LoginScreen() {
        this.setTitle("Login");
        this.setSize(300, 200);
        this.setPreferredSize(new Dimension(300, 200));
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setIconImage(icon.getImage());

        // login button
        JButton loginButton = new JButton("Login");
        try {
            loginButton.addActionListener(e -> {
                if (Database.existAdmin(usernamePanel.getUsername(),
                        Hash.hashPassword(passwordPanel.getPassword() + usernamePanel.getUsername()))) {
                    User admin = Database.getAdmin(usernamePanel.getUsername(),
                            Hash.hashPassword(passwordPanel.getPassword() + usernamePanel.getUsername()).toUpperCase());
                    System.out.println("Login Success as Administrator");
                    dispose();
                    new MainFrame(admin);
                } else if (Database.existUser(usernamePanel.getUsername())) {
                    User user = Database.getUser(usernamePanel.getUsername(),
                            Hash.hashPassword(passwordPanel.getPassword() + usernamePanel.getUsername()).toUpperCase());
                    System.out.println("Login Success");
                    dispose();
                    new MainFrame(user);
                } else {
                    JOptionPane.showMessageDialog(null, "Username or Password is incorrect");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        // register button
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            this.setVisible(false);
            this.dispose();
            new Register(user);
        });
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        this.add(usernamePanel);
        this.add(passwordPanel);
        this.add(buttonPanel);
        this.setVisible(true);
    }
}

class Username extends JPanel {
    private JTextField usernameField = new JTextField(10);

    Username() {
        setLayout(new FlowLayout());
        JLabel usernameLabel = new JLabel("Username");
        // JTextField usernameField = new JTextField(20);
        add(usernameLabel);
        add(usernameField);
    }

    public String getUsername() {
        return usernameField.getText();
    }
}

class Password extends JPanel {
    private JPasswordField passwordField = new JPasswordField(10);

    Password() {
        setLayout(new FlowLayout());
        JLabel passwordLabel = new JLabel("Password");
        add(passwordLabel);
        add(passwordField);
    }

    // get password
    public String getPassword() throws RuntimeException {
        return new String(passwordField.getPassword());
    }
}

// Register JFrame for registering new user with username and password
class Register extends JFrame {
    ImageIcon icon = new ImageIcon("TimeScheduler_v1_0/lib/TimeSchedulerIcon.png");
    private static JTextField usernameTextField = new JTextField(10);
    private static JPasswordField passwordField = new JPasswordField(10);
    private static GridBagConstraints gbc = new GridBagConstraints();
    private static JPanel registerPanel = new JPanel(new GridBagLayout());

    private void addComp(JPanel panel, JComponent comp, int x, int y, int gWidth, int gHeight, int fill, double weightx,
            double weighty) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = gWidth;
        gbc.gridheight = gHeight;
        gbc.fill = fill;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        panel.add(comp, gbc);
    }

    Register(User user) {
        registerPanel = new JPanel(new GridBagLayout());
        setTitle("Register");
        setSize(400, 200);
        setPreferredSize(new Dimension(400, 200));
        setMinimumSize(new Dimension(400, 200));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(icon.getImage());

        // username panel
        JLabel usernameLabel = new JLabel("Username");
        JLabel usernameDescriptionField = new JLabel("a-Z, 0-9, 3-15 characters");
        addComp(registerPanel, usernameLabel, 0, 0, 1, 1, GridBagConstraints.BOTH, 0.25, 0.33);
        addComp(registerPanel, usernameTextField, 1, 0, 1, 1, GridBagConstraints.HORIZONTAL, 0.4, 0.333);
        addComp(registerPanel, usernameDescriptionField, 2, 0, 1, 1, GridBagConstraints.BOTH, 0.25, 0.33);

        // password panel
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        addComp(registerPanel, passwordLabel, 0, 1, 1, 1, GridBagConstraints.BOTH, 0.25, 0.33);
        addComp(registerPanel, passwordField, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, 0.5, 0.33);

        // confirm password panel
        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        JPasswordField confirmPasswordField = new JPasswordField(10);
        addComp(registerPanel, confirmPasswordLabel, 0, 2, 1, 1, GridBagConstraints.BOTH, 0.25, 0.33);
        addComp(registerPanel, confirmPasswordField, 1, 2, 1, 1, GridBagConstraints.HORIZONTAL, 0.5, 0.33);

        // button panel
        JPanel buttonPanel = new JPanel();
        // register next button
        JButton registerNextButton = new JButton("Next");
        JPanel registerNextButtonPanel = new JPanel();
        registerNextButton.addActionListener(e -> {
            // check password == confirm password
            if (new String(passwordField.getPassword()).equals(new String(confirmPasswordField.getPassword()))) {
                System.out.println("Password match");
                // check if username is valid
                if (RegisterValidator.isValidUsername(usernameTextField.getText())) {
                    System.out.println("Username is valid");
                    user.setUsername(usernameTextField.getText());
                    // check if username is already exist
                    if (!Database.existUser(user.getUsername())) {
                        this.setVisible(false);
                        this.dispose();
                        new RegisterInfo(user);
                    } else {
                        JOptionPane.showMessageDialog(null, "Username already exists");
                    }
                } else {
                    System.out.println("Username is not valid");
                    JOptionPane.showMessageDialog(null, "Username is not valid");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Password does not match");
            }
        });
        registerNextButtonPanel.add(registerNextButton);

        // back button
        JButton registerBackButton = new JButton("Back");
        JPanel registerBackButtonPanel = new JPanel();
        registerBackButton.addActionListener(e -> {
            this.setVisible(false);
            this.dispose();
            new LoginScreen();
        });
        registerBackButtonPanel.add(registerBackButton);
        buttonPanel.add(registerBackButtonPanel);
        buttonPanel.add(registerNextButtonPanel);
        this.addComp(registerPanel, buttonPanel, 1, 3, 1, 1, GridBagConstraints.HORIZONTAL, 0.5, 1);
        this.setContentPane(registerPanel);
        this.setVisible(true);
    }

    static String getUsername() {
        return usernameTextField.getText();
    }

    static String getPassword() throws RuntimeException {
        try {
            // hash(password + username), username is the salt
            return Hash.hashPassword(new String(passwordField.getPassword()) + getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

// Second register JFrame to input name, email, phone number
class RegisterInfo extends JFrame {
    ImageIcon icon = new ImageIcon("TimeScheduler_v1_0/lib/TimeSchedulerIcon.png");
    private static GridBagConstraints gbc = new GridBagConstraints();
    private static JPanel registerInfoPanel = new JPanel(new GridBagLayout());

    private void addComp(JPanel panel, JComponent comp, int x, int y, int gWidth, int gHeight, int fill, double weightx,
            double weighty) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = gWidth;
        gbc.gridheight = gHeight;
        gbc.fill = fill;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        panel.add(comp, gbc);
    }

    RegisterInfo(User user) {
        registerInfoPanel = new JPanel(new GridBagLayout());
        this.setTitle("Register Info");
        this.setSize(400, 200);
        this.setPreferredSize(new Dimension(400, 200));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(icon.getImage());

        // name
        JLabel nameLabel = new JLabel("Name");
        JLabel nameDescriptionLabel = new JLabel("0-30 characters");
        JTextField nameTextField = new JTextField(10);
        this.addComp(registerInfoPanel, nameLabel, 0, 0, 1, 1, GridBagConstraints.BOTH, 0.25, 0.33);
        this.addComp(registerInfoPanel, nameTextField, 1, 0, 1, 1, GridBagConstraints.HORIZONTAL, 0.5, 0.33);
        this.addComp(registerInfoPanel, nameDescriptionLabel, 2, 0, 1, 1, GridBagConstraints.BOTH, 0.25, 0.33);

        // email
        JLabel emailLabel = new JLabel("Email");
        JLabel emailDescriptionLabel = new JLabel("e.g abc@email.com");
        JTextField emailTextField = new JTextField(10);
        this.addComp(registerInfoPanel, emailLabel, 0, 1, 1, 1, GridBagConstraints.BOTH, 0.25, 0.33);
        this.addComp(registerInfoPanel, emailTextField, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, 0.5, 0.33);
        this.addComp(registerInfoPanel, emailDescriptionLabel, 2, 1, 1, 1, GridBagConstraints.BOTH, 0.25, 0.33);

        // verify email
        JButton verifyButton = new JButton("Send verify code");
        JLabel verifyEmailLabel = new JLabel("Verify Email");
        JTextField verifyEmailTextField = new JTextField(10);
        this.addComp(registerInfoPanel, verifyButton, 2, 2, 1, 1, GridBagConstraints.BOTH, 0.25, 0.33);
        this.addComp(registerInfoPanel, verifyEmailLabel, 0, 2, 1, 1, GridBagConstraints.BOTH, 0.25, 0.33);
        this.addComp(registerInfoPanel, verifyEmailTextField, 1, 2, 1, 1, GridBagConstraints.HORIZONTAL, 0.5, 0.33);
        verifyButton.addActionListener(e -> {
            // send verify code to email
            String email = emailTextField.getText();
            user.setEmail(email);
            if (RegisterValidator.isValidEmail(email)) {
                System.out.println("Email is valid");
                // send verify code to user email
                Mail.sendVerifyCode(user);
                JOptionPane.showMessageDialog(null, "Verify code has been sent to your email");
            } else {
                System.out.println("Email is not valid");
                JOptionPane.showMessageDialog(null, "Email is not valid");
            }
        });

        // phone
        JLabel phoneLabel = new JLabel("Phone");
        JLabel phoneDescriptionLabel = new JLabel("e.g 0987654321, 10 digits");
        JTextField phoneTextField = new JTextField(10);
        this.addComp(registerInfoPanel, phoneLabel, 0, 3, 1, 1, GridBagConstraints.BOTH, 0.25, 0.33);
        this.addComp(registerInfoPanel, phoneTextField, 1, 3, 1, 1, GridBagConstraints.HORIZONTAL, 0.5, 0.33);
        this.addComp(registerInfoPanel, phoneDescriptionLabel, 2, 3, 1, 1, GridBagConstraints.BOTH, 0.25, 0.33);

        // button panel
        JPanel buttonPanel = new JPanel();

        // back button
        JPanel backButtonPanel = new JPanel();
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            this.setVisible(false);
            this.dispose();
            new Register(user);
        });
        backButtonPanel.add(backButton);

        // register button
        JPanel registerButtonPanel = new JPanel();
        JButton registerButton = new JButton("Register");
        registerButtonPanel.add(registerButton);

        // check validitiy of the inputs
        registerButton.addActionListener(e -> {
            // check if name, email and phone are valid
            if (RegisterValidator.isValidName(nameTextField.getText())
                    && RegisterValidator.isValidEmail(emailTextField.getText())
                    && RegisterValidator.isValidPhone(phoneTextField.getText())
                    && Mail.getVerifyCode().equals(verifyEmailTextField.getText())) {
                user.setName(nameTextField.getText());
                user.setEmail(emailTextField.getText());
                user.setPhone(phoneTextField.getText());
                System.out.println("Everything is valid");
                // check if the user is added successfully
                if (Database.addUser(user.getUsername(), Register.getPassword(), user.getName(),
                        user.getEmail(), user.getPhone())) {
                    JOptionPane.showMessageDialog(null, "Register Success, close this window to login");
                    System.out.println("Register Success");
                    this.setVisible(false);
                    this.dispose();
                    new LoginScreen();
                } else {
                    System.out.println("Register Failed");
                }

            } else if (!RegisterValidator.isValidName(nameTextField.getText())) { // name is not valid
                System.out.println("Name is invalid");
                JOptionPane.showMessageDialog(null, "Name is invalid");
            } else if (!RegisterValidator.isValidEmail(emailTextField.getText())) { // email is not valid
                System.out.println("Email is invalid");
                JOptionPane.showMessageDialog(null, "Email is invalid");
            } else if (!RegisterValidator.isValidPhone(phoneTextField.getText())) { // phone is not valid
                System.out.println("Phone is invalid");
                JOptionPane.showMessageDialog(null, "Phone is invalid");
            } else if (!Mail.getVerifyCode().equals(verifyEmailTextField.getText())) {
                System.out.println("Verify code is invalid");
                JOptionPane.showMessageDialog(null, "Verify code is invalid");
                Mail.setVerifyCode(Mail.generateVerifyCode());
            }
        });

        buttonPanel.add(backButtonPanel);
        buttonPanel.add(registerButtonPanel);
        this.addComp(registerInfoPanel, buttonPanel, 1, 4, 1, 1, GridBagConstraints.HORIZONTAL, 1, 1);
        this.setContentPane(registerInfoPanel);
        this.setVisible(true);
    }
}

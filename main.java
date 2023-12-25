import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

class Main {
 public static final String SHA256_ALGO = "SHA-256";

 public static void main(String[] args) {
    JFrame frame = new JFrame("SHA 256 - 32 BIT BOGUS");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 400);

    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.anchor = GridBagConstraints.CENTER;

    JLabel label = new JLabel("Enter Username :)");
    panel.add(label, gbc);

    gbc.gridy = 1;
    JTextField tf = new JTextField(20);
    panel.add(tf, gbc);

    gbc.gridy = 2;
    JButton calculate = new JButton("Generate Product Key");
    calculate.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String input = tf.getText();
            byte[] randomSalt = generateSalt();
            String saltedInput = input + new String(Base64.getEncoder().encode(randomSalt));
            try {
               String sha256HashedInput = calculateSHA256(saltedInput);
               JOptionPane.showMessageDialog(frame, "Product Key: " + sha256HashedInput);
            } catch (NoSuchAlgorithmException ex) {
               JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        }
    });
    panel.add(calculate, gbc);

    frame.getContentPane().add(BorderLayout.CENTER, panel);
    frame.setVisible(true);
 }

 public static byte[] generateSalt() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[32];
    random.nextBytes(salt);
    return salt;
 }

 public static String calculateSHA256(String input) throws NoSuchAlgorithmException {
    MessageDigest sha256 = MessageDigest.getInstance(SHA256_ALGO);
    byte[] hashBytes = sha256.digest(input.getBytes());
    return bytesToHex(hashBytes);
 }

 public static String bytesToHex(byte[] bytes) {
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b : bytes) {
        stringBuilder.append(String.format("%02x", b));
    }
    return stringBuilder.toString();
 }
}


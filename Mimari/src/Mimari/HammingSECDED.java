package Mimari;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HammingSECDED extends JFrame {
    private JTextField dataEntry;
    private JTextField errorPosEntry;
    private JLabel encodedLabel;
    private JLabel corruptedLabel;
    private JLabel decodedLabel;
    private String memory = "";

    // Renk teması
    private final Color arkaplan = new Color(208, 240, 192); // #d0f0c0
    private final Color butonRengi = new Color(34, 139, 34); // #228B22
    private final Color yaziRengi = Color.WHITE;

    public HammingSECDED() {
        setTitle("Hamming SEC-DED Simülatörü");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(9, 2, 10, 10));
        getContentPane().setBackground(arkaplan);

        // Başlık
        JLabel titleLabel = new JLabel("Hamming SEC-DED Simülatörü", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBackground(arkaplan);
        add(titleLabel);
        add(new JLabel("")); // Boşluk

        // Giriş Alanı
        JLabel dataLabel = new JLabel("Veriyi giriniz (binary):");
        dataLabel.setBackground(arkaplan);
        dataLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        add(dataLabel);

        dataEntry = new JTextField();
        dataEntry.setFont(new Font("Arial", Font.PLAIN, 12));
        add(dataEntry);

        JButton encodeButton = new JButton("Encode");
        encodeButton.setBackground(butonRengi);
        encodeButton.setForeground(yaziRengi);
        encodeButton.setFont(new Font("Arial", Font.PLAIN, 12));
        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encode();
            }
        });
        add(encodeButton);
        add(new JLabel("")); // Boşluk

        encodedLabel = new JLabel("Kodlanmış Veri: ");
        encodedLabel.setBackground(arkaplan);
        encodedLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        add(encodedLabel);
        add(new JLabel("")); // Boşluk

        JLabel errorPosLabel = new JLabel("Hata Konumu:");
        errorPosLabel.setBackground(arkaplan);
        errorPosLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        add(errorPosLabel);

        errorPosEntry = new JTextField();
        errorPosEntry.setFont(new Font("Arial", Font.PLAIN, 12));
        add(errorPosEntry);

        JButton simulateErrorButton = new JButton("Hatayı Simüle Et");
        simulateErrorButton.setBackground(butonRengi);
        simulateErrorButton.setForeground(yaziRengi);
        simulateErrorButton.setFont(new Font("Arial", Font.PLAIN, 12));
        simulateErrorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulateError();
            }
        });
        add(simulateErrorButton);
        add(new JLabel("")); // Boşluk

        corruptedLabel = new JLabel("Bozuk Veri: ");
        corruptedLabel.setBackground(arkaplan);
        corruptedLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        add(corruptedLabel);
        add(new JLabel("")); // Boşluk

        JButton decodeButton = new JButton("Decode");
        decodeButton.setBackground(butonRengi);
        decodeButton.setForeground(yaziRengi);
        decodeButton.setFont(new Font("Arial", Font.PLAIN, 12));
        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decode();
            }
        });
        add(decodeButton);
        add(new JLabel("")); // Boşluk

        decodedLabel = new JLabel("Çözülen Veri: ");
        decodedLabel.setBackground(arkaplan);
        decodedLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        add(decodedLabel);
        add(new JLabel("")); // Boşluk

        pack();
        setLocationRelativeTo(null);
    }

    private int parityBitleriHesapla(String veri) {
        int n = veri.length();
        int r = 0;
        while (Math.pow(2, r) < n + r + 1) {
            r++;
        }
        return r;
    }

    private String encodeData(String veri) {
        int n = veri.length();
        int r = parityBitleriHesapla(veri);
        char[] encoded = new char[n + r];
        for (int i = 0; i < n + r; i++) {
            encoded[i] = '0';
        }

        int j = 0;
        int k = 0;
        for (int i = 1; i <= n + r; i++) {
            if (i == Math.pow(2, j)) {
                j++;
            } else {
                encoded[i - 1] = veri.charAt(k);
                k++;
            }
        }

        for (int i = 0; i < r; i++) {
            int pozisyon = (int) Math.pow(2, i);
            int parity = 0;
            for (int j1 = 1; j1 <= n + r; j1++) {
                if ((j1 & pozisyon) == pozisyon && encoded[j1 - 1] == '1') {
                    parity = 1 - parity;
                }
            }
            encoded[pozisyon - 1] = (char) (parity + '0');
        }

        return new String(encoded);
    }

    private Object[] decodeData(String encoded) {
        int n = encoded.length();
        int r = parityBitleriHesapla(encoded);
        int hataliPozisyon = 0;

        for (int i = 0; i < r; i++) {
            int pos = (int) Math.pow(2, i);
            int parity = 0;
            for (int j = 1; j <= n; j++) {
                if ((j & pos) == pos && encoded.charAt(j - 1) == '1') {
                    parity = 1 - parity;
                }
            }
            hataliPozisyon += pos * parity;
        }

        if (hataliPozisyon != 0) {
            StringBuilder sb = new StringBuilder(encoded);
            sb.setCharAt(hataliPozisyon - 1, encoded.charAt(hataliPozisyon - 1) == '1' ? '0' : '1');
            encoded = sb.toString();
        }

        StringBuilder decoded = new StringBuilder();
        int j = 0;
        for (int i = 1; i <= n; i++) {
            if (i != Math.pow(2, j)) {
                decoded.append(encoded.charAt(i - 1));
            } else {
                j++;
            }
        }

        return new Object[]{decoded.toString(), hataliPozisyon};
    }

    private void encode() {
        String data = dataEntry.getText();
        if (!data.matches("[01]+")) {
            JOptionPane.showMessageDialog(this, "Lütfen geçerli bir binary veri girin!", "Geçersiz giriş", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String encoded = encodeData(data);
        encodedLabel.setText("Kodlanmış Veri: " + encoded);
        memory = encoded;
    }

    private void simulateError() {
        if (memory.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bellekte veri yok!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int errorPos;
        try {
            errorPos = Integer.parseInt(errorPosEntry.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Pozisyon için sayı girin!", "Hatalı giriş", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (errorPos < 1 || errorPos > memory.length()) {
            JOptionPane.showMessageDialog(this, "Pozisyon aralık dışında!", "Hatalı konum", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder corrupted = new StringBuilder(memory);
        corrupted.setCharAt(errorPos - 1, memory.charAt(errorPos - 1) == '1' ? '0' : '1');
        corruptedLabel.setText("Bozuk Veri: " + corrupted);
        memory = corrupted.toString();
    }

    private void decode() {
        if (memory.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bellekte veri yok!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Object[] result = decodeData(memory);
        String decoded = (String) result[0];
        int errorPos = (int) result[1];
        String errorMsg = errorPos == 0 ? "Hata tespit edilmedi." : "Hata tespit edildi ve " + errorPos + ". konumda düzeltildi.";
        decodedLabel.setText("Çözülen Veri: " + decoded + "\n" + errorMsg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HammingSECDED().setVisible(true);
        });
    }
}
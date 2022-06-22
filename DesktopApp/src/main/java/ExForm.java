import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExForm extends JFrame {

    private JLabel snpLabel;
    private JTextField snpField;
    private JButton button;
    private String surname, name, patronym;

    public ExForm(String fullName) {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setBounds(500, 300, 550, 150);
        setResizable(false);

        snpLabel = new JLabel("Фамилия Имя Отчество");

        snpField = new JTextField(fullName, 45);

        add(snpLabel);
        add(snpField);

        button = new JButton("Expand");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (snpField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Введите Фамилию, Имя, Отчество (не обязательно)");
                } else {
                    if (snpField.getText().matches("[0-9]+")) {
                        JOptionPane.showMessageDialog(null, "Вы робот. Ваш номер: " + snpField.getText(), "ПРЕДУПРЕЖДЕНИЕ", JOptionPane.WARNING_MESSAGE);
                    } else {
                        String[] arrSNP = snpField.getText().split(" ");
                        if (arrSNP.length < 2) {
                            JOptionPane.showMessageDialog(null, "Введите Фамилию или Имя");
                        } else {
                            if (arrSNP.length == 2) {
                                surname = arrSNP[0];
                                name = arrSNP[1];
                                patronym = "";
                                new SNPForm(surname, name, patronym);
                            } else {
                                surname = arrSNP[0];
                                name = arrSNP[1];
                                patronym = arrSNP[2];
                                new SNPForm(surname, name, patronym);
                            }

                        }
                    }

                }
            }

        });
        add(button);

        setVisible(true);
    }
}

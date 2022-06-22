import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SNPForm extends JFrame {
    private JTextField surname, name, patronym;
    private JLabel sLabel, nLabel, pLabel;
    private JPanel nameOfFields, fields;
    private JButton button;
    String fullName;

    public SNPForm(String defSurname, String defName, String defPatronym) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBounds(500, 300, 550, 150);
        setResizable(false);

        surname = new JTextField(defSurname, 38);
        surname.setHorizontalAlignment(SwingConstants.LEFT);

        name = new JTextField(defName, 38);
        name.setHorizontalAlignment(SwingConstants.LEFT);

        patronym = new JTextField(defPatronym, 38);
        patronym.setHorizontalAlignment(SwingConstants.LEFT);

        nameOfFields = new JPanel(new GridLayout(4, 1, 2, 1));
        nameOfFields.setSize(100, 100);

        sLabel = new JLabel("Фамилия:");
        sLabel.setFont(new Font("Arial", Font.BOLD, 15));
        nameOfFields.add(sLabel);

        nLabel = new JLabel("Имя:");
        nLabel.setFont(new Font("Arial", Font.BOLD, 15));
        nameOfFields.add(nLabel);

        pLabel = new JLabel("Отчество:");
        pLabel.setFont(new Font("Arial", Font.BOLD, 15));
        nameOfFields.add(pLabel);

        add(nameOfFields, BorderLayout.WEST);

        fields = new JPanel(new FlowLayout());
        fields.setSize(200, 500);

        fields.add(surname, BorderLayout.EAST);
        fields.add(name, BorderLayout.EAST);
        fields.add(patronym, BorderLayout.EAST);

        button = new JButton("Collapse");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (surname.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Введите Фамилию");
                } else {
                    if (name.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Введите Имя");
                    } else {
                        String twoName = surname.getText() + name.getText();
                        if (twoName.matches("[0-9]+")) {
                            JOptionPane.showMessageDialog(null, "Вы робот. Ваш номер: " + twoName, "ПРЕДУПРЕЖДЕНИЕ", JOptionPane.WARNING_MESSAGE);
                        } else {
                            fullName = surname.getText() + " " + name.getText() + " " + patronym.getText();
                            new ExForm(fullName);
                        }
                    }
                }
            }
        });
        fields.add(button, BorderLayout.EAST);
        add(fields);

        setVisible(true);
    }

    public static void main(String[] args) {

        new SNPForm("", "", "");
    }
}

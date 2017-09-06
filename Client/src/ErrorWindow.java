import javax.swing.*;
import java.awt.event.*;

public class ErrorWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonExit;
    private JLabel error;

    public ErrorWindow(String d) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonExit);

        error.setText(d);

        buttonExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
        System.exit(0);
    }

    public static void run(String d) {
        ErrorWindow dialog = new ErrorWindow(d);
        dialog.pack();
        dialog.setVisible(true);
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import javax.swing.JFrame;
import javax.swing.text.StyledEditorKit;

public class MainMenu
{
    public MainMenu()
    {
        JFrame startWindow = new JFrame("BattleShip");

        //Set window properties
        startWindow.setSize(600,600);
        startWindow.setResizable(false);
        Box components = Box.createVerticalBox();
        startWindow.add(components);

        //Game title
        JPanel titlePanel = new JPanel();
        components.add(titlePanel);
        JLabel titleLabel = new JLabel("BATTLESHIP");
//        ImageIcon icon = new ImageIcon();
//        Image image = new Image("newGameButton.jpg");
//        icon.setImage("newGameButton.jpg");
        Font font = new Font("TimesRoman", Font.BOLD, 50);
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(font);
        //titleLabel.setFont(titleLabel.getFont().deriveFont(64.0f));
        titleLabel.setPreferredSize(new Dimension(350, 350));
        titlePanel.add(titleLabel);

        //Buttons
        Box buttons = Box.createVerticalBox();
        JButton btnSinglePlayer = new JButton("Single Player");
        JButton btnOnline = new JButton("      Online     ");
        buttons.add(Box.createHorizontalStrut(150));
        buttons.add(btnSinglePlayer);
        buttons.add(Box.createVerticalStrut(10));
        buttons.add(btnOnline);
        buttons.add(Box.createVerticalStrut(200));
        components.add(buttons);

        startWindow.setVisible(true);
        startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Button Methods
        btnSinglePlayer.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new SinglePlayerGame();
                startWindow.dispose();
            }
        });

        btnOnline.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    new Client(InetAddress.getLocalHost(), 5000);
                    startWindow.dispose();
                }
                catch (Exception exception)
                {
                    System.out.println("Server is not running");
                }
            }
        });
    }
}

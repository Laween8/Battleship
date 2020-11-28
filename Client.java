import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client
{
    //Variable Declarations
    private DataOutputStream outToServer = null;
    private DataInputStream inFromServer = null;
    private ObjectOutputStream boardToServer = null;
    private ObjectInputStream boardFromServer = null;
    private ObjectInputStream coordinatesFromServer = null;
    private ObjectOutputStream coordinatesToServer = null;
    private Socket socket;
    private int MSG;
    private int AIRCRAFT_CARRIER_SIZE = 5;
    private int DESTROYER_SIZE = 3;
    private int p1A1Count = AIRCRAFT_CARRIER_SIZE;
    private int p1A2Count = AIRCRAFT_CARRIER_SIZE;
    private int p1D1Count = DESTROYER_SIZE;
    private int p1D2Count = DESTROYER_SIZE;
    private int p2A1Count = AIRCRAFT_CARRIER_SIZE;
    private int p2A2Count = AIRCRAFT_CARRIER_SIZE;
    private int p2D1Count = DESTROYER_SIZE;
    private int p2D2Count = DESTROYER_SIZE;
    private int p1ShipsLeft = 6;
    private int p2ShipsLeft = 6;
    private int shipCount = 0;
    private boolean leftRight = true;
    private boolean isGameOver = false;
    private Color ogColor;
    private Color Purple = new Color(128, 0, 140);
    private int BOARD_SIZE = 10;
    private JButton[][] p1BtnBoard = new JButton[10][10];
    private JButton[][] p2BtnBoard = new JButton[10][10];
    private BattleShipTable player1 = new BattleShipTable();
    private BattleShipTable player2 = new BattleShipTable();
    private Message message = new Message();
    private GridBagConstraints c = new GridBagConstraints();
    private JFrame gameWindow = new JFrame("BattleShip");
    private JPanel mainPanel = new JPanel();
    private JPanel top = new JPanel();
    private JPanel topMiddle = new JPanel();
    private JPanel bottomLeft = new JPanel();
    private JPanel bottomMiddle = new JPanel();
    private JPanel bottomRight = new JPanel();
    private JPanel board = new JPanel();
    private JPanel p2Board = new JPanel();
    private JPanel separationPanel = new JPanel();
    private JTextPane txtInitialWait = new JTextPane();
    private JTextPane txtWait = new JTextPane();
    private JTextPane txtSelect = new JTextPane();
    private JTextPane txt1A = new JTextPane();
    private JTextPane txt2A = new JTextPane();
    private JTextPane txt1D = new JTextPane();
    private JTextPane txt2D = new JTextPane();
    private JTextPane txt1S = new JTextPane();
    private JTextPane txt2S = new JTextPane();
    private JTextPane txtP1Miss = new JTextPane();
    private JTextPane txtP2Miss = new JTextPane();
    private JTextPane txtP1HA = new JTextPane();
    private JTextPane txtP2HA = new JTextPane();
    private JTextPane txtP1SA = new JTextPane();
    private JTextPane txtP2SA = new JTextPane();
    private JTextPane txtP1HD = new JTextPane();
    private JTextPane txtP2HD = new JTextPane();
    private JTextPane txtP1SD = new JTextPane();
    private JTextPane txtP2SD = new JTextPane();
    private JTextPane txtP1SS = new JTextPane();
    private JTextPane txtP2SS = new JTextPane();
    private JTextPane txtWin = new JTextPane();
    private JTextPane txtLose = new JTextPane();
    private JButton btnRestart = new JButton("Restart");
    private JButton btnMainMenu = new JButton("Main Menu");
    private JButton btnQuit = new JButton("Quit");

    public static void main(String[] args) throws Exception
    {
       new Client(InetAddress.getLocalHost(), 5000);
    }

    public Client(InetAddress address, int port) throws Exception
    {
        socket = new Socket(address, port);
        Message message = new Message();

        gameWindow.setSize(900, 800);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.setLayout(new GridBagLayout());

        ogColor = top.getBackground();
        txtSelect.setEditable(false);
        txtSelect.setText("Select a square to attack");
        txtSelect.setBackground(top.getBackground());
        txtSelect.setFont(txtSelect.getFont().deriveFont(12.0f));
        txtSelect.setVisible(false);
        txtInitialWait.setEditable(false);
        txtInitialWait.setText("Waiting for another player");
        txtInitialWait.setVisible(true);
        txtInitialWait.setBackground(ogColor);
        txtInitialWait.setFont(txtInitialWait.getFont().deriveFont(12.0f));
        txtInitialWait.setVisible(false);
        txtWait.setEditable(false);
        txtWait.setText("Waiting for other player");
        txtWait.setBackground(ogColor);
        txtWait.setFont(txtWait.getFont().deriveFont(18.0f));
        txtWait.setVisible(false);
        txt1A.setEditable(false);
        txt1A.setText("Select a location for your first Aircraft Carrier");
        txt1A.setBackground(top.getBackground());
        txt1A.setFont(txt1A.getFont().deriveFont(24.0f));
        txt2A.setEditable(false);
        txt2A.setText("Select a location for your second Aircraft Carrier");
        txt2A.setBackground(top.getBackground());
        txt2A.setFont(txt1A.getFont().deriveFont(24.0f));
        txt2A.setVisible(false);
        txt1D.setEditable(false);
        txt1D.setText("Select a location for your first Destroyer");
        txt1D.setBackground(top.getBackground());
        txt1D.setFont(txt1A.getFont().deriveFont(24.0f));
        txt1D.setVisible(false);
        txt2D.setEditable(false);
        txt2D.setText("Select a location for your second Destroyer");
        txt2D.setBackground(top.getBackground());
        txt2D.setFont(txt1A.getFont().deriveFont(24.0f));
        txt2D.setVisible(false);
        txt1S.setEditable(false);
        txt1S.setText("Select a location for your first Submarine");
        txt1S.setBackground(top.getBackground());
        txt1S.setFont(txt1A.getFont().deriveFont(24.0f));
        txt1S.setVisible(false);
        txt2S.setEditable(false);
        txt2S.setText("Select a location for your second Submarine");
        txt2S.setBackground(top.getBackground());
        txt2S.setFont(txt1A.getFont().deriveFont(24.0f));
        txt2S.setVisible(false);
        txtP1Miss.setText("You missed!");
        txtP1Miss.setFont(txtP1Miss.getFont().deriveFont(18.0f));
        txtP1Miss.setBackground(ogColor);
        txtP1Miss.setEditable(false);
        txtP1Miss.setVisible(false);
        txtP2Miss.setText("The enemy missed!");
        txtP2Miss.setFont(txtP2Miss.getFont().deriveFont(18.0f));
        txtP2Miss.setBackground(ogColor);
        txtP2Miss.setEditable(false);
        txtP2Miss.setVisible(false);
        txtP1HA.setText("You hit the enemy Aircraft Carrier!");
        txtP1HA.setFont(txtP1HA.getFont().deriveFont(18.0f));
        txtP1HA.setBackground(ogColor);
        txtP1HA.setEditable(false);
        txtP1HA.setVisible(false);
        txtP2HA.setText("Your Aircraft Carrier was hit!");
        txtP2HA.setFont(txtP2HA.getFont().deriveFont(18.0f));
        txtP2HA.setBackground(ogColor);
        txtP2HA.setEditable(false);
        txtP2HA.setVisible(false);
        txtP1SA.setText("You sank the enemy Aircraft Carrier!");
        txtP1SA.setFont(txtP1SA.getFont().deriveFont(18.0f));
        txtP1SA.setBackground(ogColor);
        txtP1SA.setEditable(false);
        txtP1SA.setVisible(false);
        txtP2SA.setText("Your Aircraft Carrier has sank!");
        txtP2SA.setFont(txtP2SA.getFont().deriveFont(18.0f));
        txtP2SA.setBackground(ogColor);
        txtP2SA.setEditable(false);
        txtP2SA.setVisible(false);
        txtP1HD.setText("You hit the enemy Destroyer");
        txtP1HD.setFont(txtP1HD.getFont().deriveFont(18.0f));
        txtP1HD.setBackground(ogColor);
        txtP1HD.setEditable(false);
        txtP1HD.setVisible(false);
        txtP2HD.setText("Your Destroyer was hit!");
        txtP2HD.setFont(txtP2HD.getFont().deriveFont(18.0f));
        txtP2HD.setBackground(ogColor);
        txtP2HD.setEditable(false);
        txtP2HD.setVisible(false);
        txtP1SD.setText("You sank the enemy Destroyer!");
        txtP1SD.setFont(txtP1SD.getFont().deriveFont(18.0f));
        txtP1SD.setBackground(ogColor);
        txtP1SD.setEditable(false);
        txtP1SD.setVisible(false);
        txtP2SD.setText("Your Destroyer has sank!");
        txtP2SD.setFont(txtP2SD.getFont().deriveFont(18.0f));
        txtP2SD.setBackground(ogColor);
        txtP2SD.setEditable(false);
        txtP2SD.setVisible(false);
        txtP1SS.setText("You sank the enemy Submarine!");
        txtP1SS.setFont(txtP1SS.getFont().deriveFont(18.0f));
        txtP1SS.setBackground(ogColor);
        txtP1SS.setEditable(false);
        txtP1SS.setVisible(false);
        txtP2SS.setText("Your Submarine has sank!");
        txtP2SS.setFont(txtP2SS.getFont().deriveFont(18.0f));
        txtP2SS.setBackground(ogColor);
        txtP2SS.setEditable(false);
        txtP2SS.setVisible(false);
        txtWin.setText("You Win!");
        txtWin.setFont(txtWin.getFont().deriveFont(18.0f));
        txtWin.setBackground(ogColor);
        txtWin.setEditable(false);
        txtWin.setVisible(false);
        txtLose.setText("You Lose");
        txtLose.setFont(txtSelect.getFont().deriveFont(18.0f));
        txtLose.setBackground(ogColor);
        txtLose.setEditable(false);
        txtLose.setVisible(false);

        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(btnRestart);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(btnMainMenu);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(btnQuit);
        bottomMiddle.add(verticalBox);

        top.add(txt1A);
        top.add(txt2A);
        top.add(txt1D);
        top.add(txt2D);
        top.add(txt1S);
        top.add(txt2S);
        top.add(txtInitialWait);
        topMiddle.add(txtSelect);
        bottomRight.add(txtP1Miss);
        bottomLeft.add(txtP2Miss);
        bottomRight.add(txtP1HA);
        bottomLeft.add(txtP2HA);
        bottomRight.add(txtP1SA);
        bottomLeft.add(txtP2SA);
        bottomRight.add(txtP1HD);
        bottomLeft.add(txtP2HD);
        bottomRight.add(txtP1SD);
        bottomLeft.add(txtP2SD);
        bottomRight.add(txtP1SS);
        bottomLeft.add(txtP2SS);
        topMiddle.add(txtWin);
        topMiddle.add(txtLose);
        topMiddle.add(txtSelect);

        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 40;
        mainPanel.add(top, c);
        c.gridx = 1;
        c.gridy = 0;
        mainPanel.add(topMiddle, c);
        c.gridx = 0;
        c.gridy = 1;
        mainPanel.add(board, c);
        c.gridx = 0;
        c.gridy = 2;
        mainPanel.add(bottomLeft, c);
        c.gridx = 2;
        c.gridy = 2;
        mainPanel.add(bottomRight, c);
        c.gridx = 1;
        c.gridy = 2;
        mainPanel.add(bottomMiddle, c);
        separationPanel.setPreferredSize(new Dimension(100, 300));
        c.gridx = 1;
        c.gridy = 1;
        mainPanel.add(separationPanel, c);
        p2Board.setLayout(new GridLayout(10, 10));
        p2Board.setPreferredSize(new Dimension(300, 300));
        board.setLayout(new GridLayout(10, 10));
        board.setPreferredSize(new Dimension(300, 300));
        c.gridx = 2;
        c.gridy = 1;
        mainPanel.add(p2Board, c);
        gameWindow.add(mainPanel);

        gameWindow.setVisible(true);
        topMiddle.setVisible(false);
        board.setVisible(false);
        bottomLeft.setVisible(false);
        bottomRight.setVisible(false);
        bottomMiddle.setVisible(false);
        separationPanel.setVisible(false);
        p2Board.setVisible(false);

        btnQuit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
                gameWindow.dispose();
            }
        });
        btnRestart.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    new Client(InetAddress.getLocalHost(), 5000);
                }
                catch (Exception exception)
                {

                }
            }
        });
        btnMainMenu.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new MainMenu();
                gameWindow.dispose();
            }
        });

        for(int i = 0; i < BOARD_SIZE; i++)
        {
            for(int j = 0; j < BOARD_SIZE; j++)
            {
                p1BtnBoard[i][j] = new JButton();
                p2BtnBoard[i][j] = new JButton();
                JButton btnP1 = p1BtnBoard[i][j];
                JButton btnP2 = p2BtnBoard[i][j];
                board.add(btnP1);
                p2Board.add(btnP2);
                btnP1.setFocusable(false);
                btnP1.addMouseListener(new MouseAdapter()
                {
                    public void mouseEntered(MouseEvent e)
                    {
                        if(btnP1.getBackground() != Color.BLUE)
                        {
                            int x = btnP1.getY() / 34;
                            int y = btnP1.getX() / 30;
                            highlightGrid(x, y);
                        }
                    }
                    @Override
                    public void mouseExited(MouseEvent e)
                    {
                        if(btnP1.getBackground() != Color.BLUE)
                        {
                            int x = btnP1.getY() / 34;
                            int y = btnP1.getX() / 30;
                            unHighlightGrid(x, y);
                        }
                    }
                });

                btnP2.addMouseListener(new MouseAdapter()
                {
                    public void mouseEntered(MouseEvent e)
                    {
                        if(btnP2.getBackground() == ogColor)
                        {
                            btnP2.setBackground(Color.YELLOW);
                        }
                    }
                    public void mouseExited(MouseEvent e)
                    {
                        if(btnP2.getBackground() == Color.YELLOW)
                        {
                            btnP2.setBackground(ogColor);
                        }
                    }
                });

                btnP1.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        int x = btnP1.getY() / 34;
                        int y = btnP1.getX() / 30;
                        addShipToGrid(x, y);
                    }
                });

                btnP2.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        if(btnP2.getBackground() == Color.YELLOW)
                        {
                            int x = btnP2.getY() / 34;
                            int y = btnP2.getX() / 30;
                            try {
                                p1Attack(x, y);
                            }
                            catch (Exception exception)
                            {

                            }
                        }
                    }
                });
            }
        }

        MSG = receiveMessage();
        if(MSG == message.MSG_REQUEST_INIT)
        {
            txtInitialWait.setVisible(false);
            startInitialRound();
        }
    }


    public void startInitialRound() throws Exception
    {
        board.setVisible(true);

        gameWindow.setFocusable(true);
        gameWindow.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    changeHighlightOrientation();
                }
            }
        });

    }


    public void addShipToGrid(int x, int y)
    {
        if(p1BtnBoard[x][y].getBackground() == Color.YELLOW)
        {
            switch (shipCount)
            {
                case 0:
                    if (leftRight)
                        player1.insertAirCarrier(x, y, x, y + 1);
                    else
                        player1.insertAirCarrier(x, y, x + 1, y);
                    txt1A.setVisible(false);
                    txt2A.setVisible(true);
                    shipCount++;
                    break;
                case 1:
                    if (leftRight)
                        player1.insertAirCarrier(x, y, x, y + 1);
                    else
                        player1.insertAirCarrier(x, y, x + 1, y);
                    txt2A.setVisible(false);
                    txt1D.setVisible(true);
                    shipCount++;
                    break;
                case 2:
                    if (leftRight)
                        player1.insertDestroyer(x, y, x, y + 1);
                    else
                        player1.insertDestroyer(x, y, x + 1, y);
                    txt1D.setVisible(false);
                    txt2D.setVisible(true);
                    shipCount++;
                    break;
                case 3:
                    if (leftRight)
                        player1.insertDestroyer(x, y, x, y + 1);
                    else
                        player1.insertDestroyer(x, y, x + 1, y);
                    shipCount++;
                    txt2D.setVisible(false);
                    txt1S.setVisible(true);
                    break;
                case 4:
                    player1.insertSubmarine(x, y);
                    txt1S.setVisible(false);
                    txt2S.setVisible(true);
                    shipCount++;
                    break;
                case 5:
                    player1.insertSubmarine(x, y);
                    txt2S.setVisible(false);
                    shipCount++;
                    disableClicking(p1BtnBoard);
                    p1BtnBoard[x][y].setBackground(Color.BLUE);
                    try
                    {
                        playGame();
                    }
                    catch (Exception exception)
                    {

                    }
                    break;
            }
            for(int i = 0; i < 10; i++)
            {
                for(int j = 0; j < 10; j++)
                {
                    if(p1BtnBoard[i][j].getBackground() == Color.YELLOW)
                    {
                        p1BtnBoard[i][j].setBackground(Color.BLUE);
                        p1BtnBoard[i][j].setEnabled(false);
                        p1BtnBoard[i][j].setRolloverEnabled(false);
                    }
                }
            }
        }
    }

    public void playGame() throws Exception
    {
        sendMessage(Message.MSG_RESPONSE_INIT);
        sendBoard(player1);
        player2 = receiveBoard();

        //Temp for 2 player 1 device
        for(int i = 0; i < BOARD_SIZE; i++)
        {
            for(int j = 0; j < BOARD_SIZE; j++)
            {
                p1BtnBoard[i][j].setBackground(ogColor);
            }
        }

        top.setVisible(false);
        topMiddle.setVisible(true);
        separationPanel.setVisible(true);
        bottomRight.setVisible(true);
        bottomLeft.setVisible(true);
        p2Board.setVisible(true);
        txtWait.setVisible(true);

        disableClicking(p2BtnBoard);
        txtInitialWait.setVisible(true);

        int[] coordinates;
        int MSG = receiveMessage();
        if (MSG == message.MSG_REQUEST_PLAY_FIRST)
        {
            txtSelect.setVisible(true);
            enableClicking(p2BtnBoard);
        }
        else if (MSG == message.MSG_REQUEST_PLAY)
        {
            txtSelect.setVisible(true);
            coordinates = receiveCoordinates();
            p2Attack(coordinates);
            enableClicking(p2BtnBoard);
        }
    }

    public void waitForResponse() throws Exception
    {
        txtSelect.setVisible(false);
        txtWait.setVisible(true);
        int[] coordinates;
        int MSG = receiveMessage();
        if (MSG == message.MSG_REQUEST_PLAY_FIRST)
        {
            txtInitialWait.setVisible(false);
            txtSelect.setVisible(true);
            enableClicking(p2BtnBoard);

        }
        else if (MSG == message.MSG_REQUEST_PLAY)
        {
            txtSelect.setVisible(true);
            coordinates = receiveCoordinates();
            p2Attack(coordinates);
            enableClicking(p2BtnBoard);
        }
        else if(MSG == message.MSG_REQUEST_GAME_OVER)
        {
            coordinates = receiveCoordinates();
            p2Attack(coordinates);
            txtLose.setVisible(true);
            GameOver();
        }
    }


    public void p1Attack(int x, int y) throws Exception {
        JButton button = p2BtnBoard[x][y];
        setPlayer1TextsInvisible();
        txtSelect.setVisible(false);
        switch (player2.table[x][y]) {
            case "Z":
                txtP1Miss.setVisible(true);
                button.setBackground(Color.BLACK);
                button.setEnabled(false);
                break;
            case "A1":
                if (p2A1Count == 1) {
                    txtP1SA.setVisible(true);
                    for (int i = 0; i < AIRCRAFT_CARRIER_SIZE; i++)
                    {
                        p2BtnBoard[player2.a1[i][0]][player2.a1[i][1]].setBackground(Purple);
                        p2BtnBoard[player2.a1[i][0]][player2.a1[i][1]].setEnabled(false);
                    }
                    p2ShipsLeft--;
                }
                else if (p2A1Count > 1)
                {
                    txtP1HA.setVisible(true);
                    button.setBackground(Color.RED);
                    button.setEnabled(false);
                    p2A1Count--;
                }
                break;
            case "A2":
                if (p2A2Count == 1) {
                    txtP1SA.setVisible(true);
                    for (int i = 0; i < AIRCRAFT_CARRIER_SIZE; i++) {
                        p2BtnBoard[player2.a2[i][0]][player2.a2[i][1]].setBackground(Purple);
                        p2BtnBoard[player2.a2[i][0]][player2.a2[i][1]].setEnabled(false);
                    }
                    p2ShipsLeft--;
                } else if (p2A2Count > 1) {
                    txtP1HA.setVisible(true);
                    button.setBackground(Color.RED);
                    button.setEnabled(false);
                    p2A2Count--;
                }
                break;
            case "D1":
                if (p2D1Count == 1) {
                    txtP1SD.setVisible(true);
                    for (int i = 0; i < DESTROYER_SIZE; i++) {
                        p2BtnBoard[player2.d1[i][0]][player2.d1[i][1]].setBackground(Purple);
                        p2BtnBoard[player2.d1[i][0]][player2.d1[i][1]].setEnabled(false);
                    }
                    p2ShipsLeft--;
                } else if (p2D1Count > 1) {
                    txtP1HD.setVisible(true);
                    button.setBackground(Color.RED);
                    button.setEnabled(false);
                    p2D1Count--;
                }
                break;
            case "D2":
                if (p2D2Count == 1) {
                    txtP1SD.setVisible(true);
                    for (int i = 0; i < DESTROYER_SIZE; i++) {
                        p2BtnBoard[player2.d2[i][0]][player2.d2[i][1]].setBackground(Purple);
                        p2BtnBoard[player2.d2[i][0]][player2.d2[i][1]].setEnabled(false);
                    }
                    p2ShipsLeft--;
                } else if (p1D2Count > 1) {
                    txtP1HD.setVisible(true);
                    button.setBackground(Color.RED);
                    button.setEnabled(false);
                    p2D2Count--;
                }
                break;
            case "S":
                txtP1SS.setVisible(true);
                button.setBackground(Purple);
                button.setEnabled(false);
                p2ShipsLeft--;
                break;
        }
        if (p2ShipsLeft == 0)
        {
            txtWin.setVisible(true);
            sendMessage(message.MSG_REQUEST_GAME_OVER);
            GameOver();
        }
        else
        {
            txtSelect.setVisible(false);
            txtInitialWait.setVisible(true);
            disableClicking(p2BtnBoard);
            int[] coordinates = {x, y};
            sendMessage(message.MSG_RESPONSE_PLAY);
            sendCoordinates(coordinates);
            waitForResponse();
        }
    }

    public void p2Attack(int[] coordinates)
    {
        int x = coordinates[0];
        int y = coordinates[1];
        JButton button = p1BtnBoard[x][y];
        setPlayer2TextsInvisible();
        switch (player1.table[x][y])
        {
            case "Z":
                txtP2Miss.setVisible(true);
                button.setBackground(Color.BLACK);
                button.setEnabled(false);
                break;
            case "A1":
                if(p1A1Count == 1)
                {
                    txtP2SA.setVisible(true);
                    for(int i = 0; i < AIRCRAFT_CARRIER_SIZE; i++)
                    {
                        p1BtnBoard[player1.a1[i][0]][player1.a1[i][1]].setBackground(Purple);
                        p1BtnBoard[player1.a1[i][0]][player1.a1[i][1]].setEnabled(false);
                    }
                    p1ShipsLeft--;
                }
                else if(p1A1Count > 1)
                {
                    txtP2HA.setVisible(true);
                    button.setBackground(Color.RED);
                    button.setEnabled(false);
                    p1A1Count--;
                }
                break;
            case "A2":
                if(p1A2Count == 1)
                {
                    txtP2SA.setVisible(true);
                    for(int i = 0; i < AIRCRAFT_CARRIER_SIZE; i++)
                    {
                        p1BtnBoard[player1.a2[i][0]][player1.a2[i][1]].setBackground(Purple);
                        p1BtnBoard[player1.a2[i][0]][player1.a2[i][1]].setEnabled(false);
                    }
                    p1ShipsLeft--;
                }
                else if(p1A2Count > 1)
                {
                    txtP2HA.setVisible(true);
                    button.setBackground(Color.RED);
                    button.setEnabled(false);
                    p1A2Count--;
                }
                break;
            case "D1":
                if(p1D1Count == 1)
                {
                    txtP2SD.setVisible(true);
                    for(int i = 0; i < DESTROYER_SIZE; i++)
                    {
                        p1BtnBoard[player1.d1[i][0]][player1.d1[i][1]].setBackground(Purple);
                        p1BtnBoard[player1.d1[i][0]][player1.d1[i][1]].setEnabled(false);
                    }
                    p1ShipsLeft--;
                }
                else if(p1D1Count > 1)
                {
                    txtP2HD.setVisible(true);
                    button.setBackground(Color.RED);
                    button.setEnabled(false);
                    p1D1Count--;
                }
                break;
            case "D2":
                if(p1D2Count == 1)
                {
                    txtP2SD.setVisible(true);
                    for(int i = 0; i < DESTROYER_SIZE; i++)
                    {
                        p1BtnBoard[player1.d2[i][0]][player1.d2[i][1]].setBackground(Purple);
                        p1BtnBoard[player1.d2[i][0]][player1.d2[i][1]].setEnabled(false);
                    }
                    p1ShipsLeft--;
                }
                else if(p1D2Count > 1)
                {
                    txtP2HD.setVisible(true);
                    button.setBackground(Color.RED);
                    button.setEnabled(false);
                    p1D2Count--;
                }
                break;
            case "S":
                txtP2SS.setVisible(true);
                button.setBackground(Purple);
                button.setEnabled(false);
                p1ShipsLeft--;
                break;
        }
    }

    public void GameOver()
    {
        txtWait.setVisible(false);
        txtSelect.setVisible(false);
        bottomMiddle.setVisible(true);
        disableClicking(p1BtnBoard);
    }

    public void setPlayer1TextsInvisible()
    {
        txtP1Miss.setVisible(false);
        txtP1SS.setVisible(false);
        txtP1SD.setVisible(false);
        txtP1HA.setVisible(false);
        txtP1SA.setVisible(false);
        txtP1HD.setVisible(false);
    }

    public void setPlayer2TextsInvisible()
    {
        txtP2Miss.setVisible(false);
        txtP2SD.setVisible(false);
        txtP2HA.setVisible(false);
        txtP2SA.setVisible(false);
        txtP2SS.setVisible(false);
        txtP2HD.setVisible(false);
    }

    public void disableClicking(JButton[][] btnBoard)
    {
        for(int i = 0; i < BOARD_SIZE; i++)
        {
            for(int j = 0; j < BOARD_SIZE; j++)
            {
                btnBoard[i][j].setEnabled(false);
                btnBoard[i][j].setRolloverEnabled(false);
            }
        }
    }

    public void enableClicking(JButton[][] btnBoard)
    {
        for(int i = 0; i < BOARD_SIZE; i++)
        {
            for(int j = 0; j < BOARD_SIZE; j++)
            {
                btnBoard[i][j].setEnabled(true);
                btnBoard[i][j].setRolloverEnabled(true);
            }
        }
    }

    public void toggleDirection()
    {
        if(leftRight)
            leftRight = false;
        else
            leftRight = true;
    }

    public void changeHighlightOrientation()
    {
        int x = 0;
        int y = 0;
        boolean isFirstHighlighted = true;
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                if (p1BtnBoard[i][j].getBackground() == Color.YELLOW && isFirstHighlighted)
                {
                    x = i;
                    y = j;
                    isFirstHighlighted = false;
                }
            }
        }
        if (!isFirstHighlighted)
        {
            unHighlightGrid(x, y);
            toggleDirection();
            highlightGrid(x, y);
        }
    }

    //Methods for sending and receiving messages, boards, and coordinates
    public void sendMessage(int msgType) throws Exception
    {
        outToServer = new DataOutputStream(socket.getOutputStream());
        outToServer.write(msgType);
    }
    public int receiveMessage() throws Exception
    {
        inFromServer = new DataInputStream(socket.getInputStream());
        return inFromServer.read();
    }
    public void sendBoard(BattleShipTable board) throws Exception
    {
        boardToServer = new ObjectOutputStream(socket.getOutputStream());
        boardToServer.writeObject(board);
    }
    public BattleShipTable receiveBoard() throws Exception
    {
        boardFromServer = new ObjectInputStream(socket.getInputStream());
        return (BattleShipTable) boardFromServer.readObject();
    }
    public void sendCoordinates(int[] coordinates) throws Exception
    {
        coordinatesToServer = new ObjectOutputStream(socket.getOutputStream());
        coordinatesToServer.writeObject(coordinates);
    }
    public int[] receiveCoordinates() throws Exception
    {
        coordinatesFromServer = new ObjectInputStream(socket.getInputStream());
        return (int[]) coordinatesFromServer.readObject();
    }

    public void highlightGrid(int i, int j)
    {
        JButton button = p1BtnBoard[i][j];
        if(leftRight)
        {
            if(shipCount == 0 || shipCount == 1)
            {
                if (player1.canShipBeInserted(i, j, i, j + 1, AIRCRAFT_CARRIER_SIZE))
                {
                    button.setBackground(Color.YELLOW);
                    p1BtnBoard[i][j+1].setBackground(Color.YELLOW);
                    p1BtnBoard[i][j+2].setBackground(Color.YELLOW);
                    p1BtnBoard[i][j+3].setBackground(Color.YELLOW);
                    p1BtnBoard[i][j+4].setBackground(Color.YELLOW);
                }
            }
            else if(shipCount == 2 || shipCount == 3)
            {
                if(player1.canShipBeInserted(i, j, i, j + 1, DESTROYER_SIZE))
                {
                    button.setBackground(Color.YELLOW);
                    p1BtnBoard[i][j+1].setBackground((Color.YELLOW));
                    p1BtnBoard[i][j+2].setBackground(Color.YELLOW);
                }
            }
            else if(shipCount == 4 || shipCount == 5)
            {
                if(player1.canSubmarineBeInserted(i, j))
                {
                    button.setBackground(Color.YELLOW);
                }
            }
        }
        else
        {
            if(shipCount == 0 || shipCount == 1)
            {
                if (player1.canShipBeInserted(i, j, i + 1, j, AIRCRAFT_CARRIER_SIZE))
                {
                    button.setBackground(Color.YELLOW);
                    p1BtnBoard[i+1][j].setBackground(Color.YELLOW);
                    p1BtnBoard[i+2][j].setBackground(Color.YELLOW);
                    p1BtnBoard[i+3][j].setBackground(Color.YELLOW);
                    p1BtnBoard[i+4][j].setBackground(Color.YELLOW);
                }
            }
            else if(shipCount == 2 || shipCount == 3)
            {
                if(player1.canShipBeInserted(i, j, i + 1, j, DESTROYER_SIZE))
                {
                    button.setBackground(Color.YELLOW);
                    p1BtnBoard[i+1][j].setBackground((Color.YELLOW));
                    p1BtnBoard[i+2][j].setBackground(Color.YELLOW);
                }
            }
            else if(shipCount == 4 || shipCount == 5)
            {
                if(player1.canSubmarineBeInserted(i, j))
                {
                    button.setBackground(Color.YELLOW);
                }
            }
        }
    }

    public void unHighlightGrid(int i, int j)
    {
        JButton button = p1BtnBoard[i][j];
        if(leftRight)
        {
            if (shipCount == 0 || shipCount == 1)
            {
                if (player1.canShipBeInserted(i, j, i, j + 1, AIRCRAFT_CARRIER_SIZE))
                {
                    button.setBackground(ogColor);
                    p1BtnBoard[i][j+1].setBackground(ogColor);
                    p1BtnBoard[i][j+2].setBackground(ogColor);
                    p1BtnBoard[i][j+3].setBackground(ogColor);
                    p1BtnBoard[i][j+4].setBackground(ogColor);
                }
            }
            else if(shipCount == 2 || shipCount == 3)
            {
                if(player1.canShipBeInserted(i, j, i, j + 1, DESTROYER_SIZE))
                {
                    button.setBackground(ogColor);
                    p1BtnBoard[i][j+1].setBackground(ogColor);
                    p1BtnBoard[i][j+2].setBackground(ogColor);
                }
            }
            else if(shipCount == 4 || shipCount == 5)
            {
                button.setBackground(ogColor);
            }
        }
        else
        {
            if(shipCount == 0 || shipCount == 1)
            {
                if (player1.canShipBeInserted(i, j, i + 1, j, AIRCRAFT_CARRIER_SIZE))
                {
                    button.setBackground(ogColor);
                    p1BtnBoard[i+1][j].setBackground(ogColor);
                    p1BtnBoard[i+2][j].setBackground(ogColor);
                    p1BtnBoard[i+3][j].setBackground(ogColor);
                    p1BtnBoard[i+4][j].setBackground(ogColor);
                }
            }
            else if(shipCount == 2 || shipCount == 3)
            {
                if(player1.canShipBeInserted(i, j, i + 1, j, DESTROYER_SIZE))
                {
                    button.setBackground(ogColor);
                    p1BtnBoard[i+1][j].setBackground(ogColor);
                    p1BtnBoard[i+2][j].setBackground(ogColor);
                }
            }
            else if(shipCount == 4 || shipCount == 5)
            {
                button.setBackground(ogColor);
            }
        }
    }
}

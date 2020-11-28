import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.JFrame;

public class SinglePlayerGame
{
    int shipCount = 0;
    int AIRCRAFT_CARRIER_SIZE = 5;
    int DESTROYER_SIZE = 3;
    int SUBMARINE_SIZE = 1;
    int BOARD_SIZE = 10;
    int pA1Count = 5;
    int pA2Count = 5;
    int pD1Count = 3;
    int pD2Count = 3;
    int CPUA1Count = 5;
    int CPUA2Count = 5;
    int CPUD1Count = 3;
    int CPUD2Count = 3;
    int pShipsLeft = 6;
    int CPUShipsLeft = 6;
    int prevX;
    int prevY;
    int cpuAttackX;
    int cpuAttackY;
    char previousAttackDirection;
    boolean wasPreviousShotHit = false;
    boolean leftRight = true;
    JFrame gameWindow = new JFrame("BattleShip");
    JPanel mainPanel = new JPanel();
    JPanel top = new JPanel();
    JPanel topMiddle = new JPanel();
    JPanel bottomLeft = new JPanel();
    JPanel bottomMiddle = new JPanel();
    JPanel bottomRight = new JPanel();
    GridBagConstraints c = new GridBagConstraints();
    JButton[][] btnBoard = new JButton[10][10];
    JButton[][] cpuBtnBoard = new JButton[10][10];
    JButton btnRestart = new JButton("Restart");
    JButton btnQuit = new JButton("Quit");
    JButton btnMainMenu = new JButton("Main Menu");
    Color ogColor;
    Color Purple = new Color(128, 0, 140);
    BattleShipTable player = new BattleShipTable();
    BattleShipTable cpu = new BattleShipTable();
    JTextPane txt1A = new JTextPane();
    JTextPane txt2A = new JTextPane();
    JTextPane txt1D = new JTextPane();
    JTextPane txt2D = new JTextPane();
    JTextPane txt1S = new JTextPane();
    JTextPane txt2S = new JTextPane();
    JTextPane txtSelect = new JTextPane();
    JTextPane txtPMiss = new JTextPane();
    JTextPane txtCPUMiss = new JTextPane();
    JTextPane txtPHA = new JTextPane();
    JTextPane txtCPUHA = new JTextPane();
    JTextPane txtPSA = new JTextPane();
    JTextPane txtCPUSA = new JTextPane();
    JTextPane txtPHD = new JTextPane();
    JTextPane txtCPUHD = new JTextPane();
    JTextPane txtPSD = new JTextPane();
    JTextPane txtCPUSD = new JTextPane();
    JTextPane txtPSS = new JTextPane();
    JTextPane txtCPUSS = new JTextPane();
    JTextPane txtWin = new JTextPane();
    JTextPane txtLose = new JTextPane();

    public SinglePlayerGame()
    {
        gameWindow.setSize(900, 800);
        mainPanel.setLayout(new GridBagLayout());
        JPanel board = new JPanel();
        board.setLayout(new GridLayout(10,10));
        board.setPreferredSize(new Dimension(300,300));
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
        top.add(txt1A);
        top.add(txt2A);
        top.add(txt1D);
        top.add(txt2D);
        top.add(txt1S);
        top.add(txt2S);
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(btnRestart);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(btnMainMenu);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(btnQuit);
        bottomMiddle.add(verticalBox);
        bottomMiddle.setVisible(false);
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
        gameWindow.add(mainPanel);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setVisible(true);

        //Creating Buttons and Listeners
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
                new SinglePlayerGame();
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
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                btnBoard[i][j] = new JButton();
                JButton button = btnBoard[i][j];
                board.add(button);
                ogColor = button.getBackground();
                button.setFocusable(false);
                button.addMouseListener(new MouseAdapter()
                {
                    public void mouseEntered(MouseEvent e)
                    {
                        if(button.getBackground() != Color.BLUE)
                        {
                            int x = button.getY() / 34;
                            int y = button.getX() / 30;
                            highlightGrid(x, y);
                        }
                    }
                    @Override
                    public void mouseExited(MouseEvent e)
                    {
                        if(button.getBackground() != Color.BLUE)
                        {
                            int x = button.getY() / 34;
                            int y = button.getX() / 30;
                            unHighlightGrid(x, y);
                        }
                    }
                });

                //Add ship when button on grid is clicked
                button.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        int x = button.getY() / 34;
                        int y = button.getX() / 30;
                        addShipToGrid(x, y);
                    }
                });
            }
        }
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

    public void playGame()
    {
        int shipsLeft = 6;
        int x1;
        int y1;
        int x2;
        int y2;
        long seed = System.currentTimeMillis();
        Random rand = new Random(seed);
        JPanel cpuBoard = new JPanel();
        JPanel separationPanel = new JPanel();

        separationPanel.setPreferredSize(new Dimension(100, 300));
        c.gridx = 1;
        c.gridy = 1;
        mainPanel.add(separationPanel, c);
        cpuBoard.setLayout(new GridLayout(10, 10));
        cpuBoard.setPreferredSize(new Dimension(300, 300));
        c.gridx = 2;
        c.gridy = 1;
        mainPanel.add(cpuBoard, c);
        txtSelect.setText("Select a square to attack");
        txtSelect.setFont(txtSelect.getFont().deriveFont(18.0f));
        txtSelect.setBackground(ogColor);
        txtSelect.setEditable(false);
        txtPMiss.setText("You missed!");
        txtPMiss.setFont(txtPMiss.getFont().deriveFont(18.0f));
        txtPMiss.setBackground(ogColor);
        txtPMiss.setEditable(false);
        txtPMiss.setVisible(false);
        txtCPUMiss.setText("The enemy missed!");
        txtCPUMiss.setFont(txtCPUMiss.getFont().deriveFont(18.0f));
        txtCPUMiss.setBackground(ogColor);
        txtCPUMiss.setEditable(false);
        txtCPUMiss.setVisible(false);
        txtPHA.setText("You hit the enemy Aircraft Carrier!");
        txtPHA.setFont(txtPHA.getFont().deriveFont(18.0f));
        txtPHA.setBackground(ogColor);
        txtPHA.setEditable(false);
        txtPHA.setVisible(false);
        txtCPUHA.setText("Your Aircraft Carrier was hit!");
        txtCPUHA.setFont(txtCPUHA.getFont().deriveFont(18.0f));
        txtCPUHA.setBackground(ogColor);
        txtCPUHA.setEditable(false);
        txtCPUHA.setVisible(false);
        txtPSA.setText("You sank the enemy Aircraft Carrier!");
        txtPSA.setFont(txtPSA.getFont().deriveFont(18.0f));
        txtPSA.setBackground(ogColor);
        txtPSA.setEditable(false);
        txtPSA.setVisible(false);
        txtCPUSA.setText("Your Aircraft Carrier has sank!");
        txtCPUSA.setFont(txtCPUSA.getFont().deriveFont(18.0f));
        txtCPUSA.setBackground(ogColor);
        txtCPUSA.setEditable(false);
        txtCPUSA.setVisible(false);
        txtPHD.setText("You hit the enemy Destroyer");
        txtPHD.setFont(txtPHD.getFont().deriveFont(18.0f));
        txtPHD.setBackground(ogColor);
        txtPHD.setEditable(false);
        txtPHD.setVisible(false);
        txtCPUHD.setText("Your Destroyer was hit!");
        txtCPUHD.setFont(txtCPUHD.getFont().deriveFont(18.0f));
        txtCPUHD.setBackground(ogColor);
        txtCPUHD.setEditable(false);
        txtCPUHD.setVisible(false);
        txtPSD.setText("You sank the enemy Destroyer!");
        txtPSD.setFont(txtPSD.getFont().deriveFont(18.0f));
        txtPSD.setBackground(ogColor);
        txtPSD.setEditable(false);
        txtPSD.setVisible(false);
        txtCPUSD.setText("Your Destroyer has sank!");
        txtCPUSD.setFont(txtCPUSD.getFont().deriveFont(18.0f));
        txtCPUSD.setBackground(ogColor);
        txtCPUSD.setEditable(false);
        txtCPUSD.setVisible(false);
        txtPSS.setText("You sank the enemy Submarine!");
        txtPSS.setFont(txtPSS.getFont().deriveFont(18.0f));
        txtPSS.setBackground(ogColor);
        txtPSS.setEditable(false);
        txtPSS.setVisible(false);
        txtCPUSS.setText("Your Submarine has sank!");
        txtCPUSS.setFont(txtCPUSS.getFont().deriveFont(18.0f));
        txtCPUSS.setBackground(ogColor);
        txtCPUSS.setEditable(false);
        txtCPUSS.setVisible(false);
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

        topMiddle.add(txtSelect);
        bottomRight.add(txtPMiss);
        bottomLeft.add(txtCPUMiss);
        bottomRight.add(txtPHA);
        bottomLeft.add(txtCPUHA);
        bottomRight.add(txtPSA);
        bottomLeft.add(txtCPUSA);
        bottomRight.add(txtPHD);
        bottomLeft.add(txtCPUHD);
        bottomRight.add(txtPSD);
        bottomLeft.add(txtCPUSD);
        bottomRight.add(txtPSS);
        bottomLeft.add(txtCPUSS);
        topMiddle.add(txtWin);
        topMiddle.add(txtLose);


        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                cpuBtnBoard[i][j] = new JButton();
                JButton button = cpuBtnBoard[i][j];
                cpuBoard.add(button);
                button.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        if (button.getBackground() == ogColor)
                            button.setBackground(Color.YELLOW);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (button.getBackground() == Color.YELLOW)
                            button.setBackground(ogColor);
                    }
                });
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            gameTurn(button);
                        } catch (Exception exception) {
                        }
                    }
                });
            }
        }

        //Randomizing CPU Board
        while (shipsLeft > 0) {
            x1 = rand.nextInt(BOARD_SIZE);
            y1 = rand.nextInt(BOARD_SIZE);
            if (rand.nextInt(2) == 0) {
                x2 = x1 + 1;
                y2 = y1;
            } else {
                x2 = x1;
                y2 = y1 + 1;
            }
            if (shipsLeft == 6 || shipsLeft == 5) {
                if (cpu.canShipBeInserted(x1, y1, x2, y2, AIRCRAFT_CARRIER_SIZE)) {
                    cpu.insertAirCarrier(x1, y1, x2, y2);
                    shipsLeft--;
                }
            } else if (shipsLeft == 4 || shipsLeft == 3) {
                if (cpu.canShipBeInserted(x1, y1, x2, y2, DESTROYER_SIZE)) {
                    cpu.insertDestroyer(x1, y1, x2, y2);
                    shipsLeft--;
                }
            } else if (shipsLeft == 2 || shipsLeft == 1) {
                if (cpu.canSubmarineBeInserted(x1, y1)) {
                    cpu.insertSubmarine(x1, y1);
                    shipsLeft--;
                }
            }
        }
    }

    public void gameTurn(JButton button) throws Exception
    {
        setPlayerTextsInvisible();
        int x = button.getY() / 34;
        int y = button.getX() / 30;
        //Player Turn
        switch (cpu.table[x][y]) {
            case "Z":
                txtPMiss.setVisible(true);
                button.setBackground(Color.BLACK);
                button.setEnabled(false);
                break;
            case "A1":
                if (CPUA1Count == 1)
                {
                    txtPSA.setVisible(true);
                    for (int i = 0; i < cpu.a1.length; i++)
                    {
                        cpuBtnBoard[cpu.a1[i][0]][cpu.a1[i][1]].setBackground(Purple);
                        cpuBtnBoard[cpu.a1[i][0]][cpu.a1[i][1]].setEnabled(false);
                    }
                    CPUShipsLeft--;
                }
                else
                    {
                    txtPHA.setVisible(true);
                    button.setBackground(Color.RED);
                    button.setEnabled(false);
                    CPUA1Count--;
                }
                break;
            case "A2":
                if (CPUA2Count == 1) {
                    txtPSA.setVisible(true);
                    for (int i = 0; i < cpu.a2.length; i++) {
                        cpuBtnBoard[cpu.a2[i][0]][cpu.a2[i][1]].setBackground(Purple);
                        cpuBtnBoard[cpu.a2[i][0]][cpu.a2[i][1]].setEnabled(false);
                    }
                    CPUShipsLeft--;
                } else {
                    txtPHA.setVisible(true);
                    button.setBackground(Color.RED);
                    button.setEnabled(false);
                    CPUA2Count--;
                }
                break;
            case "D1":
                if (CPUD1Count == 1) {
                    txtPSD.setVisible(true);
                    for (int i = 0; i < cpu.d1.length; i++) {
                        cpuBtnBoard[cpu.d1[i][0]][cpu.d1[i][1]].setBackground(Purple);
                        cpuBtnBoard[cpu.d1[i][0]][cpu.d1[i][1]].setEnabled(false);
                    }
                    CPUShipsLeft--;
                } else {
                    txtPHD.setVisible(true);
                    button.setBackground(Color.RED);
                    button.setEnabled(false);
                    CPUD1Count--;
                }
                break;
            case "D2":
                if (CPUD2Count == 1) {
                    txtPSD.setVisible(true);
                    for (int i = 0; i < cpu.d2.length; i++) {
                        cpuBtnBoard[cpu.d2[i][0]][cpu.d2[i][1]].setBackground(Purple);
                        cpuBtnBoard[cpu.d2[i][0]][cpu.d2[i][1]].setEnabled(false);
                    }
                    CPUShipsLeft--;
                } else {
                    txtPHD.setVisible(true);
                    button.setBackground(Color.RED);
                    button.setEnabled(false);
                    CPUD2Count--;
                }
                break;
            case "S":
                txtPSS.setVisible(true);
                button.setBackground(Purple);
                button.setEnabled(false);
                CPUShipsLeft--;
                break;
        }
        if (CPUShipsLeft == 0)
        {
            txtSelect.setVisible(false);
            txtWin.setVisible(true);
            GameOver();
        }

        //CPU Turn
        if(CPUShipsLeft > 0) {
            long seed = System.currentTimeMillis();
            Random rand = new Random(seed);
            //CPU attacks adjacent square if previous attack was a hit
            boolean exitLoop = false;
            if (wasPreviousShotHit && isAdjacentSquareAvailable(prevX, prevY))
            {
                while (!exitLoop) {
                    switch (rand.nextInt(4)) {
                        case 0:
                            if (prevX < BOARD_SIZE - 1 && isAttackableSquare(prevX + 1, prevY)) {
                                cpuAttackX = prevX + 1;
                                cpuAttackY = prevY;
                                exitLoop = true;
                            }
                            break;
                        case 1:
                            if (prevX > 0 && isAttackableSquare(prevX - 1, prevY)) {
                                cpuAttackX = prevX - 1;
                                cpuAttackY = prevY;
                                exitLoop = true;
                            }
                            break;
                        case 2:
                            if (prevY < BOARD_SIZE - 1 && isAttackableSquare(prevX, prevY + 1)) {
                                cpuAttackX = prevX;
                                cpuAttackY = prevY + 1;
                                exitLoop = true;
                            }
                            break;
                        case 3:
                            if (prevY > 0 && isAttackableSquare(prevX, prevY - 1)) {
                                cpuAttackX = prevX;
                                cpuAttackY = prevY - 1;
                                exitLoop = true;
                            }
                            break;
                    }
                }
            } else {
                attackRandomSquare();
            }
            prevX = cpuAttackX;
            prevY = cpuAttackY;
            JButton cpuButton = btnBoard[cpuAttackX][cpuAttackY];
            setCPUTextsInvisible();
            switch (player.table[cpuAttackX][cpuAttackY])
            {
                case "Z":
                    txtCPUMiss.setVisible(true);
                    cpuButton.setBackground(Color.BLACK);
                    cpuButton.setEnabled(false);
                    wasPreviousShotHit = false;
                    break;
                case "A1":
                    if (pA1Count == 1) {
                        txtCPUSA.setVisible(true);
                        for (int i = 0; i < player.a1.length; i++) {
                            btnBoard[player.a1[i][0]][player.a1[i][1]].setBackground(Purple);
                            //System.out.print("(" + player.a1[i][0] + ", " + player.a1[i][1] + ")");
                            btnBoard[player.a1[i][0]][player.a1[i][1]].setEnabled(false);
                        }
                        pShipsLeft--;
                    } else {
                        txtCPUHA.setVisible(true);
                        cpuButton.setBackground(Color.RED);
                        cpuButton.setEnabled(false);
                        pA1Count--;
                    }
                    wasPreviousShotHit = true;
                    break;
                case "A2":
                    if (pA2Count == 1) {
                        txtCPUSA.setVisible(true);
                        for (int i = 0; i < player.a2.length; i++) {
                            btnBoard[player.a2[i][0]][player.a2[i][1]].setBackground(Purple);
                            btnBoard[player.a2[i][0]][player.a2[i][1]].setEnabled(false);
                        }
                        pShipsLeft--;
                    } else {
                        txtCPUHA.setVisible(true);
                        cpuButton.setBackground(Color.RED);
                        cpuButton.setEnabled(false);
                        pA2Count--;
                    }
                    wasPreviousShotHit = true;
                    break;
                case "D1":
                    if (pD1Count == 1) {
                        txtCPUSD.setVisible(true);
                        for (int i = 0; i < player.d1.length; i++) {
                            btnBoard[player.d1[i][0]][player.d1[i][1]].setBackground(Purple);
                            btnBoard[player.d1[i][0]][player.d1[i][1]].setEnabled(false);
                        }
                        pShipsLeft--;
                    } else {
                        txtCPUHD.setVisible(true);
                        cpuButton.setBackground(Color.RED);
                        cpuButton.setEnabled(false);
                        pD1Count--;
                    }
                    wasPreviousShotHit = true;
                    break;
                case "D2":
                    if (pD2Count == 1) {
                        txtCPUSD.setVisible(true);
                        for (int i = 0; i < player.d2.length; i++) {
                            btnBoard[player.d2[i][0]][player.d2[i][1]].setBackground(Purple);
                            btnBoard[player.d2[i][0]][player.d2[i][1]].setEnabled(false);
                        }
                        pShipsLeft--;
                    } else {
                        txtCPUHD.setVisible(true);
                        cpuButton.setBackground(Color.RED);
                        cpuButton.setEnabled(false);
                        pD2Count--;
                    }
                    wasPreviousShotHit = true;
                    break;
                case "S":
                    txtCPUSS.setVisible(true);
                    cpuButton.setBackground(Purple);
                    cpuButton.setEnabled(false);
                    pShipsLeft--;
                    wasPreviousShotHit = true;
                    break;
            }
            if (pShipsLeft == 0) {
                txtSelect.setVisible(false);
                txtLose.setVisible(true);
                GameOver();
            }
        }
    }

    public void GameOver()
    {
        setPlayerTextsInvisible();
        setCPUTextsInvisible();
        for(int i = 0; i < BOARD_SIZE; i++)
        {
            for (int j = 0; j < BOARD_SIZE; j++) {
                cpuBtnBoard[i][j].setEnabled(false);
                cpuBtnBoard[i][j].setRolloverEnabled(false);
            }
        }
        bottomMiddle.setVisible(true);
    }

    public void setPlayerTextsInvisible()
    {
        txtPMiss.setVisible(false);
        txtPSS.setVisible(false);
        txtPSD.setVisible(false);
        txtPHA.setVisible(false);
        txtPSA.setVisible(false);
        txtPHD.setVisible(false);
    }

    public void setCPUTextsInvisible()
    {
        txtCPUMiss.setVisible(false);
        txtCPUSD.setVisible(false);
        txtCPUHA.setVisible(false);
        txtCPUSA.setVisible(false);
        txtCPUSS.setVisible(false);
        txtCPUHD.setVisible(false);
    }

    public boolean isAdjacentSquareAvailable(int x, int y)
    {
        if(isAttackableSquare(x + 1, y) || isAttackableSquare(x - 1, y) || isAttackableSquare(x, y + 1) || isAttackableSquare(x, y - 1))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void attackRandomSquare()
    {
        long seed = System.currentTimeMillis();
        Random rand = new Random(seed);
        do
        {
            cpuAttackX = rand.nextInt(BOARD_SIZE);
            cpuAttackY = rand.nextInt(BOARD_SIZE);
        }
        while(!isAttackableSquare(cpuAttackX, cpuAttackY));
    }

    public boolean isAttackableSquare(int x, int y)
    {
        if(x > BOARD_SIZE - 1 || y > BOARD_SIZE - 1 || x < 0 || y < 0)
        {
            return false;
        }
        else if(btnBoard[x][y].getBackground() == Color.BLUE || btnBoard[x][y].getBackground() == ogColor)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void disableMouseover()
    {
        for(int i = 0; i < BOARD_SIZE; i++)
        {
            for(int j = 0; j < BOARD_SIZE; j++)
            {
                btnBoard[i][j].setRolloverEnabled(false);
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
                if (btnBoard[i][j].getBackground() == Color.YELLOW && isFirstHighlighted)
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

    public void addShipToGrid(int x, int y)
    {
        if(btnBoard[x][y].getBackground() == Color.YELLOW)
        {
            switch (shipCount)
            {
                case 0:
                    if (leftRight)
                        player.insertAirCarrier(x, y, x, y + 1);
                    else
                        player.insertAirCarrier(x, y, x + 1, y);
                    txt1A.setVisible(false);
                    txt2A.setVisible(true);
                    shipCount++;
                    break;
                case 1:
                    if (leftRight)
                        player.insertAirCarrier(x, y, x, y + 1);
                    else
                        player.insertAirCarrier(x, y, x + 1, y);
                    txt2A.setVisible(false);
                    txt1D.setVisible(true);
                    shipCount++;
                    break;
                case 2:
                    if (leftRight)
                        player.insertDestroyer(x, y, x, y + 1);
                    else
                        player.insertDestroyer(x, y, x + 1, y);
                    txt1D.setVisible(false);
                    txt2D.setVisible(true);
                    shipCount++;
                    break;
                case 3:
                    if (leftRight)
                        player.insertDestroyer(x, y, x, y + 1);
                    else
                        player.insertDestroyer(x, y, x + 1, y);
                    shipCount++;
                    txt2D.setVisible(false);
                    txt1S.setVisible(true);
                    break;
                case 4:
                    player.insertSubmarine(x, y);
                    txt1S.setVisible(false);
                    txt2S.setVisible(true);
                    shipCount++;
                    break;
                case 5:
                    player.insertSubmarine(x, y);
                    txt2S.setVisible(false);
                    shipCount++;
                    disableMouseover();
                    playGame();
                    break;
            }
        }
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                if(btnBoard[i][j].getBackground() == Color.YELLOW)
                {
                    btnBoard[i][j].setBackground(Color.BLUE);
                    btnBoard[i][j].setEnabled(false);
                    btnBoard[i][j].setRolloverEnabled(false);
                }
            }
        }
    }

    public void highlightGrid(int i, int j)
    {
        JButton button = btnBoard[i][j];
        if(leftRight)
        {
            if(shipCount == 0 || shipCount == 1)
            {
                if (player.canShipBeInserted(i, j, i, j + 1, AIRCRAFT_CARRIER_SIZE))
                {
                    button.setBackground(Color.YELLOW);
                    btnBoard[i][j+1].setBackground(Color.YELLOW);
                    btnBoard[i][j+2].setBackground(Color.YELLOW);
                    btnBoard[i][j+3].setBackground(Color.YELLOW);
                    btnBoard[i][j+4].setBackground(Color.YELLOW);
                }
            }
            else if(shipCount == 2 || shipCount == 3)
            {
                if(player.canShipBeInserted(i, j, i, j + 1, DESTROYER_SIZE))
                {
                    button.setBackground(Color.YELLOW);
                    btnBoard[i][j+1].setBackground((Color.YELLOW));
                    btnBoard[i][j+2].setBackground(Color.YELLOW);
                }
            }
            else if(shipCount == 4 || shipCount == 5)
            {
                if(player.canSubmarineBeInserted(i, j))
                {
                    button.setBackground(Color.YELLOW);
                }
            }
        }
        else
        {
            if(shipCount == 0 || shipCount == 1)
            {
                if (player.canShipBeInserted(i, j, i + 1, j, AIRCRAFT_CARRIER_SIZE))
                {
                    button.setBackground(Color.YELLOW);
                    btnBoard[i+1][j].setBackground(Color.YELLOW);
                    btnBoard[i+2][j].setBackground(Color.YELLOW);
                    btnBoard[i+3][j].setBackground(Color.YELLOW);
                    btnBoard[i+4][j].setBackground(Color.YELLOW);
                }
            }
            else if(shipCount == 2 || shipCount == 3)
            {
                if(player.canShipBeInserted(i, j, i + 1, j, DESTROYER_SIZE))
                {
                    button.setBackground(Color.YELLOW);
                    btnBoard[i+1][j].setBackground((Color.YELLOW));
                    btnBoard[i+2][j].setBackground(Color.YELLOW);
                }
            }
            else if(shipCount == 4 || shipCount == 5)
            {
                if(player.canSubmarineBeInserted(i, j))
                {
                    button.setBackground(Color.YELLOW);
                }
            }

        }
    }

    public void unHighlightGrid(int i, int j)
    {
        JButton button = btnBoard[i][j];
        if(leftRight)
        {
            if (shipCount == 0 || shipCount == 1)
            {
                if (player.canShipBeInserted(i, j, i, j + 1, AIRCRAFT_CARRIER_SIZE))
                {
                    button.setBackground(ogColor);
                    btnBoard[i][j+1].setBackground(ogColor);
                    btnBoard[i][j+2].setBackground(ogColor);
                    btnBoard[i][j+3].setBackground(ogColor);
                    btnBoard[i][j+4].setBackground(ogColor);
                }
            }
            else if(shipCount == 2 || shipCount == 3)
            {
                if(player.canShipBeInserted(i, j, i, j + 1, DESTROYER_SIZE))
                {
                    button.setBackground(ogColor);
                    btnBoard[i][j+1].setBackground(ogColor);
                    btnBoard[i][j+2].setBackground(ogColor);
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
                if (player.canShipBeInserted(i, j, i + 1, j, AIRCRAFT_CARRIER_SIZE))
                {
                    button.setBackground(ogColor);
                    btnBoard[i+1][j].setBackground(ogColor);
                    btnBoard[i+2][j].setBackground(ogColor);
                    btnBoard[i+3][j].setBackground(ogColor);
                    btnBoard[i+4][j].setBackground(ogColor);
                }
            }
            else if(shipCount == 2 || shipCount == 3)
            {
                if(player.canShipBeInserted(i, j, i + 1, j, DESTROYER_SIZE))
                {
                    button.setBackground(ogColor);
                    btnBoard[i+1][j].setBackground(ogColor);
                    btnBoard[i+2][j].setBackground(ogColor);
                }
            }
            else if(shipCount == 4 || shipCount == 5)
            {
                button.setBackground(ogColor);
            }
        }
    }
}

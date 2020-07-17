import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    private DataOutputStream outToServer = null;
    private DataInputStream inFromServer = null;
    private ObjectOutputStream boardToServer = null;
    private ObjectInputStream boardFromServer = null;
    private Socket socket = null;
    private Scanner scanner = new Scanner(System.in);
    private int[] block;
    private int totalSank = 0;
    private String x;
    private String y;
    private int MSG;

    public static void main(String[] args) throws Exception
    {
        Client client = new Client(InetAddress.getLocalHost(), 5000);
    }

    public Client(InetAddress address, int port) throws Exception
    {
        socket = new Socket(address, port);
        Message message = new Message();
        message.Ftable = new BattleShipTable();
        message.Ptable = new BattleShipTable();
        BattleShipTable originalPtable;
        int[][] a1 = new int[5][2];
        int[][] a2 = new int[5][2];
        int numA = 0;
        int a1hit = 0;
        int a2hit = 0;
        int[][] d1 = new int[3][2];
        int[][] d2 = new int[3][2];
        int numD = 0;
        int d1hit = 0;
        int d2hit = 0;

        //Getting user input to perform initial round
        MSG = receiveMessage();
        if(MSG == message.MSG_REQUEST_INIT)
        {
            System.out.println(message.Ftable.toString());
            System.out.println("Choose first 2 squares for 1st Aircraft Carrier");
            x = scanner.next();
            y = scanner.next();
            message.Ftable.insertAirCarrier(x, y);
            System.out.println(message.Ftable.toString());
            System.out.println("Choose 2 squares for 2nd Aircraft Carrier");
            x = scanner.next();
            y = scanner.next();
            message.Ftable.insertAirCarrier(x, y);
            System.out.println(message.Ftable.toString());
            System.out.println("Choose 2 squares for 1st Destroyer");
            x = scanner.next();
            y = scanner.next();
            message.Ftable.insertDestroyer(x, y);
            System.out.println(message.Ftable.toString());
            System.out.println("Choose 2 squares for 2nd Destroyer");
            x = scanner.next();
            y = scanner.next();
            message.Ftable.insertDestroyer(x, y);
            System.out.println(message.Ftable.toString());
            System.out.println("Choose a square for 1st Submarine");
            x = scanner.next();
            message.Ftable.insertSubmarine(x);
            System.out.println(message.Ftable.toString());
            System.out.println("Choose a square for 2nd Submarine");
            x = scanner.next();
            message.Ftable.insertSubmarine(x);
            System.out.println(message.Ftable.toString());
            sendMessage(message.MSG_RESPONSE_INIT);
            sendBoard(message.Ftable);
            System.out.println("Waiting for other player");
        }
        //Receiving 2 p-tables from server, one to alter and send back to server, one to stay the same to create arrays for ships
        message.Ptable = receiveBoard();
        originalPtable = receiveBoard();
        //Filling in arrays for each ship and coordinates of their blocks
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++)
            {
                if(originalPtable.table[i][j].equals("D"))
                {
                    if(numD == 0)
                    {
                        if (j > 7 || (originalPtable.table[i + 1][j].equals("D") && originalPtable.table[i + 2][j].equals("D")))
                        {
                            d1[0][0] = i;
                            d1[0][1] = j;
                            d1[1][0] = i + 1;
                            d1[1][1] = j;
                            d1[2][0] = i + 2;
                            d1[2][1] = j;
                            originalPtable.table[i][j] = "S";
                            originalPtable.table[i + 1][j] = "S";
                            originalPtable.table[i + 2][j] = "S";
                            numD++;
                        }
                        else
                        {
                            d1[0][0] = i;
                            d1[0][1] = j;
                            d1[1][0] = i;
                            d1[1][1] = j + 1;
                            d1[2][0] = i;
                            d1[2][1] = j + 2;
                            originalPtable.table[i][j] = "S";
                            originalPtable.table[i + 1][j] = "S";
                            originalPtable.table[i + 2][j] = "S";
                            numD++;
                        }
                    }
                    else
                    {
                        if (j > 7 || (originalPtable.table[i + 1][j].equals("D") && originalPtable.table[i + 2][j].equals("D")))
                        {
                            d2[0][0] = i;
                            d2[0][1] = j;
                            d2[1][0] = i + 1;
                            d2[1][1] = j;
                            d2[2][0] = i + 2;
                            d2[2][1] = j;
                            originalPtable.table[i][j] = "S";
                            originalPtable.table[i + 1][j] = "S";
                            originalPtable.table[i + 2][j] = "S";
                            numD++;
                        }
                        else
                        {
                            d2[0][0] = i;
                            d2[0][1] = j;
                            d2[1][0] = i;
                            d2[1][1] = j + 1;
                            d2[2][0] = i;
                            d2[2][1] = j + 2;
                            originalPtable.table[i][j] = "S";
                            originalPtable.table[i + 1][j] = "S";
                            originalPtable.table[i + 2][j] = "S";
                            numD++;
                        }
                    }
                }
                else if(originalPtable.table[i][j].equals("A"))
                {
                    if(numA == 0)
                    {
                        if (j > 5 || (originalPtable.table[i + 1][j].equals("A") && originalPtable.table[i + 2][j].equals("A")))
                        {
                            a1[0][0] = i;
                            a1[0][1] = j;
                            a1[1][0] = i + 1;
                            a1[1][1] = j;
                            a1[2][0] = i + 2;
                            a1[2][1] = j;
                            a1[3][0] = i + 3;
                            a1[3][1] = j;
                            a1[4][0] = i + 4;
                            a1[4][1] = j;
                            originalPtable.table[i][j] = "S";
                            originalPtable.table[i + 1][j] = "S";
                            originalPtable.table[i + 2][j] = "S";
                            originalPtable.table[i + 3][j] = "S";
                            originalPtable.table[i + 4][j] = "S";
                            numA++;
                        }
                        else
                        {
                            a1[0][0] = i;
                            a1[0][1] = j;
                            a1[1][0] = i;
                            a1[1][1] = j + 1;
                            a1[2][0] = i;
                            a1[2][1] = j + 2;
                            a1[3][0] = i;
                            a1[3][1] = j + 3;
                            a1[4][0] = i;
                            a1[4][1] = j + 4;
                            originalPtable.table[i][j] = "S";
                            originalPtable.table[i][j + 1] = "S";
                            originalPtable.table[i][j + 2] = "S";
                            originalPtable.table[i][j + 3] = "S";
                            originalPtable.table[i][j + 4] = "S";
                            numA++;
                        }
                    }
                    else
                    {
                        if (j > 5 || (originalPtable.table[i + 1][j].equals("A") && originalPtable.table[i + 2][j].equals("A")))
                        {
                            a2[0][0] = i;
                            a2[0][1] = j;
                            a2[1][0] = i + 1;
                            a2[1][1] = j;
                            a2[2][0] = i + 2;
                            a2[2][1] = j;
                            a2[3][0] = i + 3;
                            a2[3][1] = j;
                            a2[4][0] = i + 4;
                            a2[4][1] = j;
                            originalPtable.table[i][j] = "S";
                            originalPtable.table[i + 1][j] = "S";
                            originalPtable.table[i + 2][j] = "S";
                            originalPtable.table[i + 3][j] = "S";
                            originalPtable.table[i + 4][j] = "S";
                            numA++;
                        }
                        else
                        {
                            a2[0][0] = i;
                            a2[0][1] = j;
                            a2[1][0] = i;
                            a2[1][1] = j + 1;
                            a2[2][0] = i;
                            a2[2][1] = j + 2;
                            a2[3][0] = i;
                            a2[3][1] = j + 3;
                            a2[4][0] = i;
                            a2[4][1] = j + 4;
                            originalPtable.table[i][j] = "S";
                            originalPtable.table[i][j + 1] = "S";
                            originalPtable.table[i][j + 2] = "S";
                            originalPtable.table[i][j + 3] = "S";
                            originalPtable.table[i][j + 4] = "S";
                            numA++;
                        }
                    }
                }
            }
            //Loop game rounds until game over
        while(true)
        {
            MSG = receiveMessage();
            if (MSG == message.MSG_REQUEST_PLAY)
            {
                message.Ftable = receiveBoard();
                System.out.println("F-Board");
                System.out.println(message.Ftable.toString());
                //Displaying P-board without showing enemy ships
                System.out.println("P-Board");
                System.out.println("    0   1   2   3   4   5   6   7   8   9  ");
                for (int i = 0; i < 10; i++)
                {
                    System.out.print("" + (char) ((int) 'A' + i) + " | ");
                    for (int j = 0; j < 10; j++)
                    {
                        if (message.Ptable.table[i][j].equals("X") || message.Ptable.table[i][j].equals("O"))
                            System.out.print(message.Ptable.table[i][j] + " | ");
                        else
                            System.out.print("Z | ");
                    }
                    System.out.println();
                }
                System.out.println("\nChoose a square to bomb");
                x = scanner.next();
                block = message.Ptable.AlphaNumerictoXY(x);
                if (message.Ptable.table[block[0]][block[1]].equals("S"))
                {
                    message.Ptable.table[block[0]][block[1]] = "X";
                    System.out.println("You sank the enemy's submarine!");
                    totalSank++;
                }
                else if (message.Ptable.table[block[0]][block[1]].equals("D"))
                {
                    message.Ptable.table[block[0]][block[1]] = "X";
                    //Determining whether ship hit was D1 or D2
                    if((d1[0][0] == block[0] && d1[0][1] == block[1]) || (d1[1][0] == block[0] && d1[1][1] == block[1])
                            || (d1[2][0] == block[0] && d1[2][1] == block[1]))
                    {
                        d1hit++;
                        if(d1hit == 3)
                        {
                            System.out.println("You sank the enemy's destroyer!");
                            totalSank++;
                        }
                        else
                            System.out.println("You hit the enemy's destroyer");
                    }
                    else
                    {
                        d2hit++;
                        if(d2hit == 3)
                        {
                            System.out.println("You sank the enemy's destroyer!");
                            totalSank++;
                        }
                        else
                            System.out.println("You hit the enemy's destroyer");
                    }
                }
                else if (message.Ptable.table[block[0]][block[1]].equals("A"))
                {
                    message.Ptable.table[block[0]][block[1]] = "X";
                    //Determining if ship hit is A1 or A2
                    if((a1[0][0] == block[0] && a1[0][1] == block[1]) || (a1[1][0] == block[0] && a1[1][1] == block[1])
                            || (a1[2][0] == block[0] && a1[2][1] == block[1]) || (a1[3][0] == block[0] && a1[3][1] == block[1])
                     || (a1[4][0] == block[0] && a1[4][1] == block[1]))
                    {
                        a1hit++;
                        if(a1hit == 5)
                        {
                            System.out.println("You sank the enemy's aircraft carrier!");
                            totalSank++;
                        }
                        else
                            System.out.println("You hit the enemy's aircraft carrier");
                    }
                    else
                    {
                        a2hit++;
                        if(a2hit == 5)
                        {
                            System.out.println("You sank the enemy's aircraft carrier!");
                            totalSank++;
                        }
                        else
                            System.out.println("You hit the enemy's aircraft carrier");
                    }
                }
                else if (message.Ptable.table[block[0]][block[1]].equals("Z"))
                {
                    message.Ptable.table[block[0]][block[1]] = "O";
                    System.out.println("You missed");
                }
                if (totalSank == 6)
                {
                    System.out.println("You Win!");
                    sendMessage(message.MSG_REQUEST_GAME_OVER);
                    break;
                }
            }
            else if (MSG == message.MSG_REQUEST_GAME_OVER)
            {
                System.out.println("Game Over You Lose");
                break;
            }
            System.out.println("Waiting for other player");
            sendMessage(message.MSG_RESPONSE_PLAY);
            sendBoard(message.Ptable);
        }
    }

    //Methods for sending and receiving messages and boards
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
}

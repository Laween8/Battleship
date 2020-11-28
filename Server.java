import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public class Server
{
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private Socket p1Socket = null;
    private Socket p2Socket = null;
    private DataOutputStream outToClient = null;
    private DataInputStream inFromClient = null;
    private ObjectOutputStream boardToClient = null;
    private ObjectInputStream boardFromClient = null;
    private ObjectInputStream coordinatesFromClient = null;
    private ObjectOutputStream coordinatesToClient = null;
    private boolean gameOver = false;
    public boolean isServerRunning = false;

    public static void main(String[] args) throws Exception
    {
        new Server(5000);
    }

    public Server(int port) throws Exception
    {
        isServerRunning = true;
        boolean isPlayer1 = true;
        serverSocket = new ServerSocket(port);
        //Waiting for connections and assigning them to appropriate socket variables
        while (true) {
            socket = serverSocket.accept();
            if (isPlayer1 && socket != null) {
                System.out.println("Player 1 connected");
                p1Socket = socket;
                socket = null;
                isPlayer1 = false;
            } else if (!isPlayer1 && socket != null) {
                p2Socket = socket;
                System.out.println("Player 2 connected");
                break;
            }
        }

            Message message1 = new Message();
            message1.p1Coordinates = new int[2];
            message1.p2Coordinates = new int[2];
            int MSG1;
            int MSG2;
            boolean p1BoardReceived = false;
            boolean p2BoardReceived = false;
            //Waiting until player 2 connection then performing initial round
                //Performing initial round for player 1
                sendMessage(message1.MSG_REQUEST_INIT, p1Socket);
                sendMessage(message1.MSG_REQUEST_INIT, p2Socket);
                MSG1 = receiveMessage(p1Socket);
                MSG2 = receiveMessage(p2Socket);
                while(!p1BoardReceived || !p2BoardReceived)
                {
                    if (MSG1 == message1.MSG_RESPONSE_INIT)
                    {
                        message1.p1Board = receiveBoard(p1Socket);
                        p1BoardReceived = true;
                    }
                    if(MSG2 == message1.MSG_RESPONSE_INIT)
                    {
                        message1.p2Board = receiveBoard(p2Socket);
                        p2BoardReceived = true;
                    }
                }

                //Sending 2 p-tables to both players
                sendBoard(message1.p2Board, p1Socket);
                sendBoard(message1.p1Board, p2Socket);
                //Game Rounds loop until game over
                boolean isFirstTurnDone = false;
                sendMessage(message1.MSG_REQUEST_PLAY_FIRST, p1Socket);
                while(!isFirstTurnDone)
                {
                    if(receiveMessage(p1Socket) == message1.MSG_RESPONSE_PLAY)
                    {
                        message1.p1Coordinates = receiveCoordinates(p1Socket);
                        isFirstTurnDone = true;
                    }
                }
                while (!gameOver)
                {
                    sendMessage(message1.MSG_REQUEST_PLAY, p2Socket);
                    sendCoordinates(message1.p1Coordinates, p2Socket);
                    System.out.println("Test");
                    MSG1 = receiveMessage(p2Socket);
                    if (MSG1 == message1.MSG_RESPONSE_PLAY)
                    {
                        message1.p2Coordinates = receiveCoordinates(p2Socket);
                    }
                    else if (MSG1 == message1.MSG_REQUEST_GAME_OVER)
                    {
                        sendMessage(message1.MSG_REQUEST_GAME_OVER, p1Socket);
                        sendCoordinates(message1.p2Coordinates, p1Socket);
                        gameOver = true;
                    }
                    sendMessage(message1.MSG_REQUEST_PLAY, p1Socket);
                    sendCoordinates(message1.p2Coordinates, p1Socket);
                    MSG1 = receiveMessage(p1Socket);
                    if (MSG1 == message1.MSG_RESPONSE_PLAY)
                    {
                        message1.p1Coordinates = receiveCoordinates(p1Socket);
                    }
                    else if (MSG1 == message1.MSG_REQUEST_GAME_OVER)
                    {
                        sendMessage(message1.MSG_REQUEST_GAME_OVER, p2Socket);
                        sendCoordinates(message1.p1Coordinates, p2Socket);
                        gameOver = true;
                    }
                }
            }
    //Methods for sending and receiving messages and boards
    public void sendMessage(int msgType, Socket pSocket) throws Exception
    {
        outToClient = new DataOutputStream(pSocket.getOutputStream());
        outToClient.write(msgType);
    }
    public int receiveMessage(Socket pSocket) throws Exception
    {
        inFromClient = new DataInputStream(pSocket.getInputStream());
        return inFromClient.read();
    }
    public void sendBoard(BattleShipTable board, Socket pSocket) throws Exception
    {
        boardToClient = new ObjectOutputStream(pSocket.getOutputStream());
        boardToClient.writeObject(board);
    }
    public BattleShipTable receiveBoard(Socket pSocket) throws Exception
    {
        boardFromClient = new ObjectInputStream(pSocket.getInputStream());
        return (BattleShipTable) boardFromClient.readObject();
    }
    public void sendCoordinates(int[] coordinates, Socket pSocket) throws Exception
    {
        coordinatesToClient = new ObjectOutputStream(pSocket.getOutputStream());
        coordinatesToClient.writeObject(coordinates);
    }
    public int[] receiveCoordinates(Socket pSocket) throws Exception
    {
        coordinatesFromClient = new ObjectInputStream(pSocket.getInputStream());
        return (int[]) coordinatesFromClient.readObject();
    }
}

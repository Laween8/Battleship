import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public class Server implements Runnable
{
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private Socket p1Socket = null;
    private Socket p2Socket = null;
    private DataOutputStream outToClient = null;
    private DataInputStream inFromClient = null;
    private ObjectOutputStream boardToClient = null;
    private ObjectInputStream boardFromClient = null;
    private boolean gameOver = false;

    public static void main(String[] args) throws Exception
    {
        Server server = new Server(5000);
    }

    public Server(int port) throws Exception
    {
        boolean isPlayer1 = true;
        serverSocket = new ServerSocket(port);
        Thread gameThread = new Thread(this::run);
        //Waiting for connections and assigning them to appropriate socket variables
        while(true)
        {
            socket = serverSocket.accept();
            if(isPlayer1 && socket != null)
            {
                p1Socket = socket;
                socket = null;
                isPlayer1 = false;
                gameThread.start();
            }
            else if(!isPlayer1 && socket != null)
            {
                p2Socket = socket;
                break;
            }
        }
    }
    public void run()
    {
        try
        {
            Message message1 = new Message();
            message1.Ftable = new BattleShipTable();
            message1.Ptable = new BattleShipTable();
            Message message2 = new Message();
            message2.Ftable = new BattleShipTable();
            message2.Ptable = new BattleShipTable();
            int MSG;

            //Performing initial round for player 1
            sendMessage(message1.MSG_REQUEST_INIT, p1Socket);
            MSG = receiveMessage(p1Socket);
            if(MSG == message1.MSG_RESPONSE_INIT)
            {
                message1.Ftable = receiveBoard(p1Socket);
                message2.Ptable = message1.Ftable;
            }
            while(true)//Waiting until player 2 connection then performing initial round
            {
                if(p2Socket != null)
                {
                    sendMessage(message2.MSG_REQUEST_INIT, p2Socket);
                    MSG = receiveMessage(p2Socket);
                    if(MSG == message2.MSG_RESPONSE_INIT)
                    {
                        message2.Ftable = receiveBoard(p2Socket);
                        message1.Ptable = message2.Ftable;
                    }
                    break;
                }
            }
            //Sending 2 p-tables to both players
            sendBoard(message1.Ptable, p1Socket);
            sendBoard(message1.Ptable, p1Socket);
            sendBoard(message2.Ptable, p2Socket);
            sendBoard(message2.Ptable, p2Socket);
            //Game Rounds loop until game over
            while(!gameOver)
            {
                sendMessage(message1.MSG_REQUEST_PLAY, p1Socket);
                sendBoard(message1.Ftable, p1Socket);
                MSG = receiveMessage(p1Socket);
                if(MSG == message1.MSG_RESPONSE_PLAY)
                {
                    message1.Ptable = receiveBoard(p1Socket);
                    message2.Ftable = message1.Ptable;
                }
                else if(MSG == message1.MSG_REQUEST_GAME_OVER)
                {
                    sendMessage(message2.MSG_REQUEST_GAME_OVER, p2Socket);
                    gameOver = true;
                }
                sendMessage(message2.MSG_REQUEST_PLAY ,p2Socket);
                sendBoard(message2.Ftable, p2Socket);
                MSG = receiveMessage(p2Socket);
                if(MSG == message2.MSG_RESPONSE_PLAY)
                {
                    message2.Ptable = receiveBoard(p2Socket);
                    message1.Ftable = message2.Ptable;
                }
                else if(MSG == message2.MSG_REQUEST_GAME_OVER)
                {
                    sendMessage(message2.MSG_REQUEST_GAME_OVER, p1Socket);
                    gameOver = true;
                }
            }
        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
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
}

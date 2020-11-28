import java.io.Serializable;

public class Message implements Serializable
{

    //message types
    static final int MSG_REQUEST_INIT = 1;//sent from server to client
    static final int MSG_RESPONSE_INIT = 2; //sent from client to server
    static final int MSG_REQUEST_PLAY = 3; //sent from server to client
    static final int MSG_RESPONSE_PLAY = 4;//sent from client to server
    static final int MSG_REQUEST_GAME_OVER = 5; //sent from server to client
    static final int MSG_REQUEST_PLAY_FIRST = 6;
    static final int MSG_RESPONSE_PLAY_FIRST = 7;

    private int msgType=-1;
    private String msg = null;
    public int[] p1Coordinates = new int[2]; //x, y coordinates of the block on the opponent's board ot be bombed; this is for the MSG_RESPONSE_PLAY message
    public int[] p2Coordinates = new int[2];
    public BattleShipTable p1Board = new BattleShipTable();
    public BattleShipTable p2Board = new BattleShipTable();

    //getters
    public String getMsg(){
        return this.msg;
    }

    public int getMsgType(){
        return this.msgType;
    }
    //setters
    public void setMsg(String m){
        this.msg = m;
    }

    public void setMsgType(int type){
        this.msgType = type;
    }

    // constructor
    public Message()
    {

    }
}

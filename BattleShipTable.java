import java.io.Serializable;
public class BattleShipTable implements Serializable
{
    /* Constants*/
    //Size of each type of ship
    static final int AIRCRAFT_CARRIER_SIZE = 5;
    static final int DESTROYER_SIZE = 3;
    static final int SUBMARINE_SIZE = 1;

    //symbols use on the board
	/*
	   "A": Aircraft
	   "D": Destroyer
	   "S": Submarine

	   "X": Hit
	   "O": Miss
	   "Z": default value
	*/

    static final String AIRCRAFT_CARRIER_SYMBOL = "A";
    static final String DESTROYER_SYMBOL = "D";
    static final String SUBMARINE_SYMBOL = "S";
    static final String HIT_SYMBOL = "X";
    static final String MISS_SYMBOL = "O";
    static final String DEFAULT_SYMBOL = "Z";

    String [][]table = null;
    Boolean isFirstAircraftCarrier = true;
    Boolean isFirstDestroyer = true;
    Boolean isFirstSubmarine = true;
    int[][] a1 = new int[5][2];
    int[][] a2 = new int[5][2];
    int[][] d1 = new int[3][2];
    int[][] d2 = new int[3][2];

    // constructor
    public BattleShipTable()
    {
        System.out.println("create table");
        this.table = new String[10][10];
        //set default values
        for(int i=0;i<10;++i){
            for(int j=0;j<10;++j){
                this.table[i][j] = "Z";
            }
        }
    }
    /*convert alpha_numeric to the X and Y coordinates*/
    public int[] AlphaNumerictoXY(String alpha_coordinates) throws NumberFormatException
    {
        //get the alpha part
        int []ret = new int[2];
        ret[0] = this.helperAlphaToX(alpha_coordinates.charAt(0));
        //get the numeric part
        ret[1] = Integer.parseInt(alpha_coordinates.substring(1));
        return ret;
    }
    private int helperAlphaToX(char alpha)
    {
        return (int)alpha - (int)'A';
    }

    private String XYToAlphaNumeric(int []xy)
    {
        return "" + ((char)(xy[0] + (int)'A')) + "" + xy[1];
    }
    //print out the table
    public String toString()
    {
        String ret = new String();
        System.out.println("    0   1   2   3   4   5   6   7   8   9  ");
        for(int i=0;i<10;++i){
            ret = ret + "" + (char)((int)'A' + i) + " | ";
            for(int j=0;j<10;++j){
                ret = ret + this.table[i][j] + " | ";
            }
            ret = ret + "\n";
        }
        return ret;
    }

    public void insertHit(String x1, String s)
    {
        this.insertSinglePoint(this.AlphaNumerictoXY(x1), s);
    }

    public void insertSubmarine(int x, int y)
    {
        int[] xy = {x, y};
        insertSinglePoint(xy, "S");
    }

    public void insertAirCarrier(int x1, int y1, int x2, int y2)
    {
        if(isFirstAircraftCarrier)
        {
            insertShip(x1, y1, x2, y2, AIRCRAFT_CARRIER_SIZE, "A1");
            isFirstAircraftCarrier = false;
        }
        else
        {
            insertShip(x1, y1, x2, y2, AIRCRAFT_CARRIER_SIZE, "A2");
        }
    }

    public void insertDestroyer(int x1, int y1, int x2, int y2)
    {
        if(isFirstDestroyer)
        {
            insertShip(x1, y1, x2, y2, DESTROYER_SIZE, "D1");
            isFirstDestroyer = false;
        }
        else
        {
            insertShip(x1, y1, x2, y2, DESTROYER_SIZE, "D2");
        }
    }

    private void insertShip(int x1, int y1, int x2, int y2, int len, String s)
    {
        int []xy1 = {x1, y1};
        int []xy2 = {x2, y2};
        if(xy1[0] == xy2[0] && (xy1[1]+1) == xy2[1])
        {// along the x axis
            if(checkAlongXAxis(xy1,len))
            {//insert the battleship
                insertAlongXAxis(xy1, len, s);
            }
        }
        else if(xy1[1] == xy2[1] && (xy1[0]+1) == xy2[0])
        {// along the y axis
            if
            (checkAlongYAxis(xy1, len))
            {//insert the battleship
                insertAlongYAxis(xy1, len, s);
            }
        }
    }

    public boolean canShipBeInserted(int x1, int y1, int x2, int y2, int len)
    {
        int []xy1 = {x1, y1};
        int []xy2 = {x2, y2};
        if(!(xy1[0]>=0 && xy1[0]<=9 && xy1[1]>=0 && xy1[1]<=9))
            return false;
        if(!(xy2[0]>=0 && xy2[0]<=9 && xy2[1]>=0 && xy2[1]<=9))
            return false;
        if(xy1[0] == xy2[0] && (xy1[1]+1) == xy2[1])
        {// along the x axis
            if(checkAlongXAxis(xy1,len))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else if(xy1[1] == xy2[1] && (xy1[0]+1) == xy2[0])
        {// along the y axis
            if
            (checkAlongYAxis(xy1, len))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
            return false;
    }

    private void insertSinglePoint(int[] xy, String s)
    {
        if(table[xy[0]][xy[1]].equals("Z"))
        {
            table[xy[0]][xy[1]] = s;
        }
    }

    public boolean canSubmarineBeInserted(int x, int y)
    {
        if(table[x][y].equals("Z"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean checkAlongXAxis(int[] xy, int len)
    {
        if(xy[1] + len > 10) return false;
        for(int j = xy[1]; j < xy[1] + len; j++)
        {
            if(!this.table[xy[0]][j].equals("Z"))
                return false;
        }
        return true;
    }

    private void insertAlongXAxis(int[] xy, int len, String s)
    {
        int k = 0;
        for(int j = xy[1]; j <  xy[1] + len; j++)
        {
            table[xy[0]][j] = s;
            if(len == 5 && isFirstAircraftCarrier)
            {
                a1[k][0] = xy[0];
                a1[k][1] = j;
            }
            else if(len == 5 && !isFirstAircraftCarrier)
            {
                a2[k][0] = xy[0];
                a2[k][1] = j;
            }
            else if(len == 3 && isFirstDestroyer)
            {
                d1[k][0] = xy[0];
                d1[k][1] = j;
            }
            else if(len == 3 && !isFirstDestroyer)
            {
                d2[k][0] = xy[0];
                d2[k][1] = j;
            }
            k++;
        }
    }

    private boolean checkAlongYAxis(int[] xy, int len)
    {
        if(xy[0]+len > 10) return false;
        for(int i = xy[0]; i < xy[0] + len; i++)
        {
            if(!this.table[i][xy[1]].equals("Z"))
                return false;
        }
        return true;
    }

    private void insertAlongYAxis(int[] xy, int len, String s)
    {
        int k = 0;
        for(int i = xy[0]; i < xy[0] + len; i++)
        {
            table[i][xy[1]] = s;
            if(len == 5 && isFirstAircraftCarrier)
            {
                a1[k][0] = i;
                a1[k][1] = xy[1];
            }
            else if(len == 5 && !isFirstAircraftCarrier)
            {
                a2[k][0] = i;
                a2[k][1] = xy[1];
            }
            else if(len == 3 && isFirstDestroyer)
            {
                d1[k][0] = i;
                d1[k][1] = xy[1];
            }
            else if(len == 3 && !isFirstDestroyer)
            {
                d2[k][0] = i;
                d2[k][1] = xy[1];
            }
            k++;
        }
    }

    public static void main(String args[])
    {
//        BattleShipTable t = new BattleShipTable();
//        t.insertAirCarrier("C5","C6");
//        System.out.println(t.toString());
//        if(!t.insertDestroyer("H9", "I9")){
//            System.out.println("not able to insert");
//        }
//        System.out.println(t.toString());
//        if(!t.insertDestroyer("H9", "I9")){
//            System.out.println("not able to insert");
//        }
//        System.out.println(t.toString());

    }
}

//-----------------------------------------
// every necessary information for a a square
//-----------------------------------------

public class Square
{
    private int xCoor;      //the row that the current square belongs to
    private int yCoor;      //the column that the current square belongs to
    private boolean isWall; //true if the current square is a wall
    private Square parent;  //previous square
    private int f;          //function cost = g + h
    private int g;          //actual cost from previous square to current square
    private int h;          //heuristic cost to the flag
    private int output;
    private int totalOutput;//how many time it has been visited

    //------------------------------------------------------
    // Square Constructor
    //
    // PURPOSE:	Initializes this object
    // PARAMETERS: None
    // Returns: None
    //------------------------------------------------------
    public Square()
    {
        xCoor = 0;
        yCoor = 0;
        isWall = false;
        parent = null;
        f = 0;
        g = 0;
        h = 0;
        if (isWall)
        {
            output = -1;
            totalOutput = -1;
        }
        else
        {
            output = 0;
            totalOutput = 0;
        }
    }

    //------------------------------------------------------
    // Square Constructor
    //
    // PURPOSE:	Initializes this object
    // PARAMETERS:
    // Returns: None
    //------------------------------------------------------
    public Square(int xCoor, int yCoor, boolean isWall, Square parent, int g, int h)
    {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        this.isWall = isWall;
        this.parent = parent;
        this.g = g;
        this.h = h;
        this.f = g + h;
        if (isWall)
        {
            output = -1;
            totalOutput = -1;
        }
        else
        {
            output = 0;
            totalOutput = 0;
        }
    }

    //------------------------------------------------------
    // Square Constructor
    //
    // PURPOSE:	Initializes this object
    // PARAMETERS: None
    // Returns: None
    //------------------------------------------------------
    public Square(int xCoor, int yCoor)
    {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        this.isWall = false;
        this.parent = parent;
        this.g = 0;
        this.h = 0;
        this.f = 0;
        if (isWall)
        {
            output = -1;
            totalOutput = -1;
        }
        else
        {
            output = 0;
            totalOutput = 0;
        }
    }

    //------------------------------------------------------
    // Square Constructor
    //
    // PURPOSE:	Initializes this object
    // PARAMETERS: None
    // Returns: None
    //------------------------------------------------------
    public Square(int xCoor, int yCoor, boolean isWall)
    {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        this.isWall = isWall;
        this.parent = null;
        this.g = 0;
        this.h = 0;
        this.f = 0;
        if (isWall)
        {
            output = -1;
            totalOutput = -1;
        }
        else
        {
            output = 0;
            totalOutput = 0;
        }
    }

    //------------------------------------------------------
    // equals (override)
    //
    // PURPOSE:	check if the two square are actually the same one by comparing their coordinates
    // PARAMETERS:
    //      Object: the square will be compared with
    // Returns: the true if they are the same, false otherwise
    //------------------------------------------------------
    @Override
    public boolean equals(Object o)
    {
        boolean result = false;
        if (xCoor == ((Square) o).xCoor && yCoor == ((Square) o).yCoor)
            result = true;
        return result;
    }

    //------------------------------------------------------
    // isDone
    //
    // PURPOSE:	check if the current square is the flag
    // PARAMETERS:
    //      Square: the flag will be checked with
    // Returns: the true if current square is the flag, false otherwise
    //------------------------------------------------------
    public boolean isDone(Square goal)
    {
        if (xCoor == goal.xCoor && yCoor == goal.yCoor)
            return true;
        return false;
    }

    //All necessary Mutators and Accessors to all the instance variables of this object
    public boolean isWall()
    {
        return isWall;
    }

    public void setIsWall(boolean isWall)
    {
        this.isWall = isWall;
    }

    public int getxCoor()
    {
        return xCoor;
    }

    public int getyCoor()
    {
        return yCoor;
    }

    public void setyCoor(int yCoor)
    {
        this.yCoor = yCoor;
    }

    public Square getParent()
    {
        return parent;
    }

    public void setG(int g)
    {
        this.g = g;
    }

    public int getH()
    {
        return h;
    }

    public void setH(int h)
    {
        this.h = h;
    }

    public int getG()
    {
        return g;
    }

    public void setParent(Square parent)
    {
        this.parent = parent;
    }

    public void setF(int f)
    {
        this.f = f;
    }

    public int getF()
    {
        return f;
    }

    public int getOutput()
    {
        return output;
    }

    public void setOutput(int output)
    {
        this.output = output;
    }

    public void setTotalOutput(int totalOutput)
    {
        this.totalOutput = totalOutput;
    }

    public int getTotalOutput()
    {
        return totalOutput;
    }
} //class

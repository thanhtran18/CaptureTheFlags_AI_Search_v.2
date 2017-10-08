public class Square
{
    private int xCoor;
    private int yCoor;
    private boolean isWall;
    private Square parent;
    private int f;
    private int g;
    private int h;

    public Square()
    {
        xCoor = 0;
        yCoor = 0;
        isWall = false;
        parent = null;
        f = 0;
        g = 0;
        h = 0;
    }

    public Square(int xCoor, int yCoor, boolean isWall, Square parent, int g, int h)
    {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        this.isWall = isWall;
        this.parent = parent;
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

    public Square(int xCoor, int yCoor)
    {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        this.isWall = false;
        this.parent = parent;
        this.g = 0;
        this.h = 0;
        this.f = 0;
    }

    public Square(int xCoor, int yCoor, boolean isWall)
    {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        this.isWall = isWall;
        this.parent = parent;
        this.g = 0;
        this.h = 0;
        this.f = 0;
    }

    @Override
    public boolean equals(Object o)
    {
        boolean result = false;
        if (xCoor == ((Square) o).xCoor && yCoor == ((Square) o).yCoor)
            result = true;
        return result;
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

    public boolean isDone(Square goal)
    {
        if (xCoor == goal.xCoor && yCoor == goal.yCoor)
            return true;
        return false;
    }

    public boolean isWall()
    {
        return isWall;
    }
}

import java.util.*;

//IF THIS DOESN't WORK, CHANGE TO THE ORIGINAL CONDITIONS OF GENERATE CHILDREN METHOD
public class AStarAlgorithm
{
    private int numRows;
    private int numCols;
    private Square hero;
    private ArrayList<Square> allSquares;
    private Square flag;
    //private ArrayList<Square> flags;
    private ArrayList<Square> walls;
    private int numOfVisitedSquares;
    private int finalNumOfVisitedSquares;
    private int numOfConsideredOps;

    public AStarAlgorithm(int numRows, int numCols, Square hero, Square flag, ArrayList<Square> walls, ArrayList<Square> allSquares)
    //public AStarAlgorithm(int numRows, int numCols, Square hero, ArrayList<Square> flags, ArrayList<Square> walls, ArrayList<Square> allSquares)
    {
        this.numRows = numRows;
        this.numCols = numCols;
        this.hero = hero;
        this.walls = walls;
        this.flag = flag;
        //this.flags = flags;
        this.allSquares = allSquares;
        for (Square square : allSquares)
        {
            if (!walls.contains(square))
                square.setOutput(0);
        }
        numOfVisitedSquares = 0;
        numOfConsideredOps = 0;
    } //constructor

    public int calculateH(Square start, Square goal)
    {
        return (Math.abs(start.getxCoor() - goal.getxCoor()) + Math.abs(start.getyCoor() - goal.getyCoor()));
    }

    public Square getSquare(int x, int y) //return a square from the square list
    {
        for (Square currSquare : allSquares)
        {
            if (currSquare.getxCoor() == x && currSquare.getyCoor() == y)
                return currSquare;
        }
        return null;
    } //getSquare

    public ArrayList<Square> generateChildren(Square curr)
    {
        ArrayList<Square> children = new ArrayList<>();
        if ( curr.getyCoor() > 0 && !walls.contains(getSquare(curr.getxCoor(), curr.getyCoor()-1)) && getSquare(curr.getxCoor(), curr.getyCoor()-1).getOutput() == 0)
            children.add( getSquare(curr.getxCoor(), curr.getyCoor()-1) );
        if ( curr.getyCoor() < numCols - 1 && !walls.contains(getSquare(curr.getxCoor(), curr.getyCoor()+1)) && getSquare(curr.getxCoor(), curr.getyCoor()+1).getOutput() == 00)
            children.add( getSquare(curr.getxCoor(), curr.getyCoor()+1) );
        if ( curr.getxCoor() > 0 && !walls.contains(getSquare(curr.getxCoor() - 1, curr.getyCoor())) && getSquare(curr.getxCoor() - 1, curr.getyCoor()).getOutput() == 0)
            children.add( getSquare(curr.getxCoor() - 1, curr.getyCoor()) );
        if ( curr.getxCoor() < numRows - 1 && !walls.contains( getSquare(curr.getxCoor() + 1, curr.getyCoor())) && getSquare(curr.getxCoor() + 1, curr.getyCoor()).getOutput() == 0)
            children.add( getSquare(curr.getxCoor() + 1, curr.getyCoor()) );
        return children;
    } //generateChildren

    public ArrayList<Square> getPath(Square start, Square end)
    {
        Square square = end;
        ArrayList<Square> path = new ArrayList<>();
        path.add(square);

        while ( square.getParent() != null && !square.getParent().equals(start) )
        {
            square = square.getParent();
            path.add(square);
        }

        Collections.reverse(path);
        return path;
    } //getPath

    public void updateSquare(Square currSquare, Square child, Square goal)
    {
        //update the child square
        child.setG(currSquare.getG() + 1);
        child.setH(calculateH(child, goal));
        child.setParent(currSquare);
        child.setF(child.getG() + child.getH());
    } //updateSquare

    //public ArrayList<Square> processAStar(Square currSquare, ArrayList<Square> flags)
    public ArrayList<Square> processAStar(Square currSquare, Square goal)
    {
        if ( !currSquare.isDone(goal) )
        {
            Comparator<Square> comparator = new ComparableSquare();
            Queue<Square> open = new PriorityQueue<>(10000, comparator);
            HashSet<Square> closed = new HashSet<>();
            open.add(currSquare);
            while ( !open.isEmpty() )
            {
                Square current = open.poll();
                closed.add(current);
                if (current.equals(goal))
                    return getPath(current, goal);

                for (Square newSquare : generateChildren(current))
                {
                    numOfVisitedSquares++;
                    newSquare.setOutput(newSquare.getOutput()+1);
                    newSquare.setTotalOutput(newSquare.getTotalOutput()+1);

                    if ( !newSquare.isWall() && !closed.contains(newSquare) )
                    {
                        if ( open.contains(newSquare) )
                        {
                            if ( newSquare.getG() > current.getG() + 1)
                                updateSquare(current, newSquare, goal);
                        }
                        else
                        {
                            updateSquare(current, newSquare, goal);
                            open.add(newSquare);
                        }
                    }
                } //for
            } //while
        }
        else
            return getPath(currSquare, goal);
        return null;
    } //processAStar

    public String displayPath(ArrayList<Square> path)
    {
        StringBuilder result = new StringBuilder();
        /*for (Square curr : path)
        {
            result.append("(" + curr.getxCoor() + "," + curr.getyCoor() + "); ");
        }*/
        for (int i = 0; i < numRows; i++)
        {
            for (int j = 0; j < numCols; j++)
            {
                Square currSquare = getSquare(i, j);

                if (currSquare.isWall())
                {
                    if (i == 0 && j == 0)
                        result.append("\n");
                    //System.out.print("#");
                    result.append("#");
                }
                else
                    result.append(currSquare.getTotalOutput());
                    //System.out.print(currSquare.getTotalOutput());
            }
            //System.out.println();
            result.append("\n");
        }
        //result.append("number of visited squares: " + finalNumOfVisitedSquares + "\n");
        //System.out.println("number of visited squares: " + numOfVisitedSquares);
        return result.toString();
    }


    public int getFinalNumOfVisitedSquares()
    {
        return finalNumOfVisitedSquares;
    }

    public int getNumOfVisitedSquares()
    {
        return numOfVisitedSquares;
    }
}

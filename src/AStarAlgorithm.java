import java.util.*;

public class AStarAlgorithm
{
    private int numRows;
    private int numCols;
    private Square hero;
    private ArrayList<Square> allSquares;
    private Square flag;
    private ArrayList<Square> walls;
    private int numOfVistedSquares;
    private int numOfConsideredOps;

    public AStarAlgorithm(int numRows, int numCols, Square hero, Square flag, ArrayList<Square> walls, ArrayList<Square> allSquares)
    {
        this.numRows = numRows;
        this.numCols = numCols;
        this.hero = hero;
        this.walls = walls;
        this.flag = flag;
        this.allSquares = allSquares;
        numOfVistedSquares = 0;
        numOfConsideredOps = 0;
        /*
        for (int i = 0; i < numRows; i++)
        {
            for (int j = 0; j < numCols; j++)
            {
                Square currSquare = new Square(i, j);
                for (Square currWall : walls)
                {
                    if (currSquare.equals(currWall))
                        currSquare.setIsWall(true);
                    else
                        currSquare.setIsWall(false);
                    allSquares.add(currSquare);
                }
            } //for j
        } //for i
        */
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
        if ( curr.getyCoor() > 0 && !walls.contains(getSquare(curr.getxCoor(), curr.getyCoor()-1)) )
            children.add( getSquare(curr.getxCoor(), curr.getyCoor()-1) );
        if ( curr.getyCoor() < numCols - 1 && !walls.contains(getSquare(curr.getxCoor(), curr.getyCoor()+1)) )
            children.add( getSquare(curr.getxCoor(), curr.getyCoor()+1) );
        if ( curr.getxCoor() > 0 && !walls.contains(getSquare(curr.getxCoor() - 1, curr.getyCoor())) )
            children.add( getSquare(curr.getxCoor() - 1, curr.getyCoor()) );
        if ( curr.getxCoor() < numRows - 1 && !walls.contains( getSquare(curr.getxCoor() + 1, curr.getyCoor())) )
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
        //path.add(start);
        Collections.reverse(path);
        System.out.println(displayPath(path));
        return path;
    } //getPath

    public void updateSquare(Square currSquare, Square child)
    {
        //update the child square
        child.setG(currSquare.getG() + 1);
        child.setH(calculateH(child, flag));
        child.setParent(currSquare);
        child.setF(child.getG() + child.getH());
    } //updateSquare

    public ArrayList<Square> processAStar(Square currSquare)
    {
        if ( !currSquare.isDone(flag) )
        {
            Comparator<Square> comparator = new ComparableSquare();
            Queue<Square> open = new PriorityQueue<>(10000, comparator);
            HashSet<Square> closed = new HashSet<>();
            open.add(currSquare);
            while ( !open.isEmpty() )
            {
                Square current = open.poll();
                closed.add(current);
                if (current.equals(flag))
                    return getPath(current, flag);

                for (Square newSquare : generateChildren(current))
                {

                    if ( !newSquare.isWall() && !closed.contains(newSquare) )
                    {
                        if ( open.contains(newSquare) )
                        {
                            if ( newSquare.getG() > current.getG() + 1)
                                updateSquare(current, newSquare);
                        }
                        else
                        {
                            updateSquare(current, newSquare);
                            open.add(newSquare);
                        }
                    }
                }
            }
        }
        else
        {
            return getPath(currSquare, flag);
            //System.out.println(displayPath(getPath(currSquare, flag)));
        }

        return null;

    } //processAStar

    public String displayPath(ArrayList<Square> path)
    {
        StringBuilder result = new StringBuilder();
        for (Square curr : path)
        {
            result.append("(" + curr.getxCoor() + "," + curr.getyCoor() + "); ");
        }
        return result.toString();
    }
}

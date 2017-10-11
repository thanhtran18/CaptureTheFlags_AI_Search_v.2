import java.util.*;

public class AStarAlgorithm
{
    private int numRows;
    private int numCols;
    private Square hero;
    private ArrayList<Square> allSquares;
    private Square flag;
    private ArrayList<Square> walls;
    private int numOfVisitedSquares;
    private int numOfConsideredOps;

    public AStarAlgorithm(int numRows, int numCols, Square hero, Square flag, ArrayList<Square> walls, ArrayList<Square> allSquares)
    {
        this.numRows = numRows;
        this.numCols = numCols;
        this.hero = hero;
        this.walls = walls;
        this.flag = flag;
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
        int h = (Math.abs(start.getxCoor() - goal.getxCoor()) + Math.abs(start.getyCoor() - goal.getyCoor()));
        return h;
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
        if ( curr.getyCoor() > 0 && !walls.contains(getSquare(curr.getxCoor(), curr.getyCoor()-1))
                && getSquare(curr.getxCoor(), curr.getyCoor()-1).getOutput() == 0)
        {
            if (curr.getParent() == null || (curr.getParent() != null && !getSquare(curr.getxCoor(), curr.getyCoor() - 1).equals(curr.getParent())))
                children.add(getSquare(curr.getxCoor(), curr.getyCoor() - 1));
        }

        if ( curr.getyCoor() < numCols - 1 && !walls.contains(getSquare(curr.getxCoor(), curr.getyCoor()+1))
                && getSquare(curr.getxCoor(), curr.getyCoor()+1).getOutput() == 0)
        {
            if (curr.getParent() == null || (curr.getParent() != null && !getSquare(curr.getxCoor(), curr.getyCoor()+1).equals(curr.getParent())))
                children.add(getSquare(curr.getxCoor(), curr.getyCoor() + 1));
        }

        if ( curr.getxCoor() > 0 && !walls.contains(getSquare(curr.getxCoor() - 1, curr.getyCoor()))
                && getSquare(curr.getxCoor() - 1, curr.getyCoor()).getOutput() == 0)
        {
            if (curr.getParent() == null || (curr.getParent() != null  && !getSquare(curr.getxCoor() - 1, curr.getyCoor()).equals(curr.getParent())))
                children.add(getSquare(curr.getxCoor() - 1, curr.getyCoor()));
        }

        if ( curr.getxCoor() < numRows - 1 && !walls.contains( getSquare(curr.getxCoor() + 1, curr.getyCoor()))
                && getSquare(curr.getxCoor() + 1, curr.getyCoor()).getOutput() == 0)
        {
            if (curr.getParent() == null || (curr.getParent() != null  && !getSquare(curr.getxCoor() + 1, curr.getyCoor()).equals(curr.getParent())))
                children.add(getSquare(curr.getxCoor() + 1, curr.getyCoor()));
        }
        return children;
    } //generateChildren

    public ArrayList<Square> getPath(Square start, Square end)
    {
        Square square = end;
        ArrayList<Square> path = new ArrayList<>();
        path.add(square);

        while ( square.getParent() != null && !square.getParent().equals(start) )
        {
            //PROBLEM HERE, PARENT GET LOOPED
            square = square.getParent();
            path.add(square);
        }

        Collections.reverse(path);
        return path;
    } //getPath

    public int updateSquare(Square currSquare, Square child, Square goal)
    {
        //update the child square
        child.setG(currSquare.getG() + 1);
        child.setH(calculateH(child, goal));
        child.setParent(currSquare);
        child.setF(child.getG() + child.getH());
        return child.getG() + child.getH();
    } //updateSquare

    public ArrayList<Square> processAStar(Square currSquare, Square goal, ArrayList<Square> flags)
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
                {
                    //return getPath(current, goal);
                    //return null;

                    for (Square square : allSquares)
                    {
                        square.setParent(null);
                    }
                    flags.remove(goal);
                    open = new PriorityQueue<>(10000, comparator);
                    closed = new HashSet<>();
                    open.add(current);

                }


                for (Square newSquare : generateChildren(current))
                {
                    //************
                    HashMap<Square, Integer> costs = new HashMap();
                    for (Square flag : flags)
                    {
                        ArrayList<Square> goalChildren = generateChildren(flag);
                        AStarAlgorithm solver = new AStarAlgorithm(numRows, numCols, newSquare, flag, walls, allSquares);
                        costs.put(flag, new Integer(updateSquare(current, newSquare, flag)));
                        if (newSquare.equals(flag) || (goalChildren.isEmpty() && flag.getParent() == null))
                        {
                            current = newSquare;
                            flags.remove(flag);
                            for (Square square : allSquares)
                                square.setParent(null);
                            break;

                        }

                        //AStarAlgorithm solver = new AStarAlgorithm(numRows, numCols, newSquare, flag, walls, allSquares);
                        //costs.put(flag, new Integer(updateSquare(current, newSquare, flag)));
                    }
                    List<Square> sortedFlagList = A1Q2.getSortedFlags(costs);
                    if (!flags.isEmpty() && !goal.equals(sortedFlagList.get(0)))
                        goal = sortedFlagList.get(0);
                        //processAStar(newSquare, sortedFlagList.get(0), flags);
                    //if (flags.isEmpty())
                    //    return null;


                    //************
                    numOfVisitedSquares++;
                    newSquare.setOutput(newSquare.getOutput()+1);
                    newSquare.setTotalOutput(newSquare.getTotalOutput()+1);

                    if (flags.isEmpty())
                        return null;

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
            flags.remove(goal);
            //return null;
            //return getPath(currSquare, goal);
        return null;
    } //processAStar

    public String displaySolvedMaze()
    {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numRows; i++)
        {
            for (int j = 0; j < numCols; j++)
            {
                Square currSquare = getSquare(i, j);

                if (currSquare.isWall())
                {
                    if (i == 0 && j == 0)
                        result.append("\n");
                    result.append("#");
                }
                else
                    result.append(currSquare.getTotalOutput());
            }
            result.append("\n");
        }
        return result.toString();
    } //displaySolvedMaze


    public int getNumOfVisitedSquares()
    {
        return numOfVisitedSquares;
    }
}

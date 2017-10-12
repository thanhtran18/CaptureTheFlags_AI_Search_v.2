//-----------------------------------------
// CLASS: AStarAlgorithm
//
// Author: Cong Thanh Tran
//
// REMARKS: A class that contains all methods needed to solve the problem by A* algorithm
//
//-----------------------------------------

import java.util.*;

public class AStarAlgorithm
{
    private int numRows;                    //number of rows of the map
    private int numCols;                    //number of columns of the map
    private Square hero;                    //the hero square
    private ArrayList<Square> allSquares;   //a list contains all the square of the map (or it is the map)
    private Square flag;                    //the current flag
    private ArrayList<Square> walls;        //a list of all the walls
    private int numOfVisitedSquares;
    private int numOfConsideredOps;

    //------------------------------------------------------
    // AStarAlgorithm Constructor
    //
    // PURPOSE:	Initializes this object
    //------------------------------------------------------
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

    //------------------------------------------------------
    // calculateH
    //
    // PURPOSE:	calculate the heuristic cost of the state using Manhattan distance
    // PARAMETERS:
    //      Square : start state (or square)
    //      Square : goal state (or the flag square)
    // Returns: the heuristic cost
    //------------------------------------------------------
    public int calculateH(Square start, Square goal)
    {
        int h = (Math.abs(start.getxCoor() - goal.getxCoor()) + Math.abs(start.getyCoor() - goal.getyCoor()));
        return h;
    }

    //------------------------------------------------------
    // calculateH
    //
    // PURPOSE:	return the wanted square from the list of all squares in the map
    // PARAMETERS:
    //      int: x coordinate of the wanted square
    //      int: y coordinate of the wanted square
    // Returns: the wanted square
    //------------------------------------------------------
    public Square getSquare(int x, int y)
    {
        for (Square currSquare : allSquares)
        {
            if (currSquare.getxCoor() == x && currSquare.getyCoor() == y)
                return currSquare;
        }
        return null;
    } //getSquare

    //------------------------------------------------------
    // generateChildren
    //
    // PURPOSE:	generate all possible children
    // PARAMETERS: none
    // Returns: a list of all children
    //------------------------------------------------------
    public ArrayList<Square> generateChildren(Square curr)
    {
        //check the case if the possible child is surrounded by 3 walls and 1 movable square (prevent cycle)
        ArrayList<Square> possibleChildren = new ArrayList<>();
        ArrayList<Square> children = new ArrayList<>();
        possibleChildren.add(getSquare(curr.getxCoor(), curr.getyCoor()-1));
        possibleChildren.add(getSquare(curr.getxCoor(), curr.getyCoor()+1));
        possibleChildren.add(getSquare(curr.getxCoor() - 1, curr.getyCoor()));
        possibleChildren.add(getSquare(curr.getxCoor() + 1, curr.getyCoor()));
        int numOfWalls = 0;
        for (Square currSquare : possibleChildren)
        {
            if (currSquare.isWall())
                numOfWalls++;
        }

        if (numOfWalls == 3 && curr.getParent() != null)
        {
            children.add(curr.getParent());
        }
        //if not, then generate all possible children (maximum 4)
        else
        {
            if (curr.getyCoor() > 0 && !walls.contains(getSquare(curr.getxCoor(), curr.getyCoor() - 1))
                    && getSquare(curr.getxCoor(), curr.getyCoor() - 1).getOutput() == 0)
            {
                if (curr.getParent() == null || (curr.getParent() != null
                        && !getSquare(curr.getxCoor(), curr.getyCoor() - 1).equals(curr.getParent())))
                    children.add(getSquare(curr.getxCoor(), curr.getyCoor() - 1));
            }

            if (curr.getyCoor() < numCols - 1 && !walls.contains(getSquare(curr.getxCoor(), curr.getyCoor() + 1))
                    && getSquare(curr.getxCoor(), curr.getyCoor() + 1).getOutput() == 0)
            {
                if (curr.getParent() == null || (curr.getParent() != null
                        && !getSquare(curr.getxCoor(), curr.getyCoor() + 1).equals(curr.getParent())))
                    children.add(getSquare(curr.getxCoor(), curr.getyCoor() + 1));
            }

            if (curr.getxCoor() > 0 && !walls.contains(getSquare(curr.getxCoor() - 1, curr.getyCoor()))
                    && getSquare(curr.getxCoor() - 1, curr.getyCoor()).getOutput() == 0)
            {
                if (curr.getParent() == null || (curr.getParent() != null
                        && !getSquare(curr.getxCoor() - 1, curr.getyCoor()).equals(curr.getParent())))
                    children.add(getSquare(curr.getxCoor() - 1, curr.getyCoor()));
            }

            if (curr.getxCoor() < numRows - 1 && !walls.contains(getSquare(curr.getxCoor() + 1, curr.getyCoor()))
                    && getSquare(curr.getxCoor() + 1, curr.getyCoor()).getOutput() == 0)
            {
                if (curr.getParent() == null || (curr.getParent() != null
                        && !getSquare(curr.getxCoor() + 1, curr.getyCoor()).equals(curr.getParent())))
                    children.add(getSquare(curr.getxCoor() + 1, curr.getyCoor()));
            }
        }
        return children;
    } //generateChildren

    //------------------------------------------------------
    // updateSquare
    //
    // PURPOSE:	update the function cost of the child
    // PARAMETERS: current square, child square, and goal square
    // Returns: the function cost
    //------------------------------------------------------
    public int updateSquare(Square currSquare, Square child, Square goal)
    {
        child.setG(currSquare.getG() + 1);
        child.setH(calculateH(child, goal));
        child.setParent(currSquare);
        child.setF(child.getG() + child.getH());
        return child.getG() + child.getH();
    } //updateSquare

    //------------------------------------------------------
    // processAStar
    //
    // PURPOSE:	solves the problem using A* algorithm (A* algorithm implementation)
    // PARAMETERS: current square, goal square (flag), and list of all flags to check which one will be the best flag
    //              to reach at current state
    // Returns a ProblemState which is the result
    //------------------------------------------------------
    public ArrayList<Square> processAStar(Square currSquare, Square goal, ArrayList<Square> flags)
    {
        currSquare.setOutput(currSquare.getOutput()+1);
        currSquare.setTotalOutput(currSquare.getTotalOutput()+1);
        numOfVisitedSquares++;

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
                    //reset the parents of all squares to null to start finding path to the new flag
                    for (Square square : allSquares)
                        square.setParent(null);

                    //reset the problem to be ready to the new flag
                    flags.remove(goal);
                    open = new PriorityQueue<>(10000, comparator);
                    closed = new HashSet<>();
                    open.add(current);
                }

                ArrayList<Square> possibleChildren = generateChildren(current);
                numOfConsideredOps += numOfVisitedSquares + possibleChildren.size();
                Square newSquare = current;
                if (!possibleChildren.isEmpty())
                {
                    int minCost = updateSquare(current, possibleChildren.get(0), goal);
                    newSquare = possibleChildren.get(0);

                    //choose the best child in all the possible children, which is the one with the lowest cost
                    for (Square child : possibleChildren)
                    {
                        if (updateSquare(current, child, goal) < minCost)
                        {
                            minCost = child.getF();
                            newSquare = child;
                        }
                    }
                }
                //prevent cycle
                if (current.getParent() != null && newSquare.equals(current.getParent()))
                {
                    closed.add(current);
                    closed.remove(newSquare);
                }

                HashMap<Square, Integer> costs = new HashMap();
                for (Square flag : flags)
                {
                    ArrayList<Square> goalChildren = generateChildren(flag);

                    AStarAlgorithm solver = new AStarAlgorithm(numRows, numCols, newSquare, flag, walls, allSquares);
                    costs.put(flag, new Integer(updateSquare(current, newSquare, flag)));
                    //check if get to the flag as the next child
                    if (newSquare.equals(flag) || (goalChildren.isEmpty() && flag.getParent() == null))
                    {
                        current = newSquare;
                        flags.remove(flag);
                        for (Square square : allSquares)
                            square.setParent(null);
                        break;
                    }
                }

                //get the closest flag to find path there
                List<Square> sortedFlagList = A1Q2.getSortedFlags(costs);
                if (!flags.isEmpty() && !goal.equals(sortedFlagList.get(0)))
                    goal = sortedFlagList.get(0);

                //update output
                numOfVisitedSquares++;
                newSquare.setOutput(newSquare.getOutput() + 1);
                newSquare.setTotalOutput(newSquare.getTotalOutput() + 1);

                //if there is no flag in the map
                if (flags.isEmpty())
                    return null;

                if (!newSquare.isWall() && !closed.contains(newSquare))
                {
                    if (open.contains(newSquare))
                    {
                        if (newSquare.getG() > current.getG() + 1)
                            updateSquare(current, newSquare, goal);
                    }
                    else
                    {
                        updateSquare(current, newSquare, goal);
                        open.add(newSquare);
                    }
                }
            } //while
        }
        else
            flags.remove(goal);
        return null;
    } //processAStar

    //------------------------------------------------------
    // displaySolvedMaze
    //
    // PURPOSE:	display the map properly after solving
    // PARAMETERS: None
    // Returns: the output
    //------------------------------------------------------
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

    public int getNumOfConsideredOps()
    {
        return numOfConsideredOps;
    }
}

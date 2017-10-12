//-----------------------------------------
// NAME		    : CONG THANH TRAN
// STUDENT NUMBER	: 7802106
// COURSE		: COMP 3190 - Introduction to Artificial Intelligence
// INSTRUCTOR	: JOHN BRAICO
// ASSIGNMENT	: assignment #1
// QUESTION	    : question #2
//
// REMARKS: Trying to solve the Capture the Flags problem with the minimum time.
//
//-----------------------------------------

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class A1Q2
{
    //------------------------------------------------------
    // main
    //
    // PURPOSE:	main method - gets things going
    // PARAMETERS:
    //		String[]: commandline argument list
    // Returns: none
    //------------------------------------------------------
    public static void main(String[] args)
    {
        try
        {
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please enter the size and the maze (after the input, press ENTER to get the result):");
            String line;

            String input = "";
            int lineCounter = 0;
            String[] size = new String[2];
            int numRows = 0;
            int numCols = 0;
            ArrayList<Square> flags = new ArrayList<>();
            Square hero = new Square();
            ArrayList<Square> allSquares = new ArrayList<>();
            ArrayList<Square> walls = new ArrayList<>();

            while ((line = stdin.readLine()) != null  && lineCounter <= numRows&& line.length() != 0)
            {
                if (lineCounter == 0)
                {
                    size = line.split(" ");
                    numRows = Integer.parseInt(size[0]);
                    numCols = Integer.parseInt(size[1]);
                }
                else
                {
                    char[][] map = new char[numRows][numCols];
                    int j = 0;
                    while (j < numCols)
                    {
                        map[lineCounter-1][j] = line.charAt(j);
                        if (map[lineCounter-1][j] == '!')
                        {
                            Square flag = new Square(lineCounter-1, j, false);
                            flags.add(flag);
                            allSquares.add(flag);
                        }
                        else if (map[lineCounter-1][j] == 'h')
                        {
                            hero = new Square(lineCounter-1, j, false);
                            allSquares.add(hero);
                        }
                        else if (map[lineCounter-1][j] == '#')
                        {
                            Square wall = new Square(lineCounter-1, j, true);
                            walls.add(wall);
                            allSquares.add(wall);
                        }
                        else
                        {
                            Square movableSquare = new Square(lineCounter-1, j, false);
                            allSquares.add(movableSquare);
                        }
                        j++;
                    }
                    input += line + "\n";
                } //big else

                lineCounter++;
            } //while

            if (flags.isEmpty())
            {
                System.out.println("There is no flags on the map!!!");
                System.exit(0);
            }

            StringBuilder solutionMap = new StringBuilder();

            System.out.println("A* Algorithm is attempting to to capture the flags...");
            HashMap<Square, Integer> costs = new HashMap<Square, Integer>();
            for (Square flag : flags)
            {
                AStarAlgorithm solver = new AStarAlgorithm(numRows, numCols, hero, flag, walls, allSquares);
                costs.put(flag, new Integer(solver.calculateH(hero, flag)));
            }
            List<Square> sortedFlagList = getSortedFlags(costs);

            Square flag = sortedFlagList.get(0);

            AStarAlgorithm solver = new AStarAlgorithm(numRows, numCols, hero, flag, walls, allSquares);

            solver.processAStar(hero, flag, flags);
            solutionMap.append(solver.displaySolvedMaze());
            System.out.println(solutionMap.toString());

            System.out.println("Total number of visited nodes: " + solver.getNumOfVisitedSquares());
            System.out.println("Total operations considered: " + solver.getNumOfConsideredOps());
        } //try
        catch (IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
    }

    public static List<Square> getSortedFlags(Map<Square, Integer> costs)
    {
        List<Map.Entry<Square, Integer>> flagList = new LinkedList<>(costs.entrySet());
        Collections.sort(flagList, new Comparator<Map.Entry<Square, Integer>>()
        {
            @Override
            public int compare(Map.Entry<Square, Integer> o1, Map.Entry<Square, Integer> o2)
            {
                return (o1.getValue().compareTo(o2.getValue()));
            }
        });

        HashMap<Square, Integer> sortedFlags = new LinkedHashMap<>();
        for (Map.Entry<Square, Integer> flag : flagList)
            sortedFlags.put(flag.getKey(), flag.getValue());

        List<Square> sortedFlagList = new ArrayList<>(sortedFlags.keySet());
        return sortedFlagList;
    } //getSortedFlags
} //class

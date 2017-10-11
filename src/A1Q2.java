

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class A1Q2 {

    public static void main(String[] args)
    {
        try
        {
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please enter the size of the maze (rows columns) (the press enter), then enter the maze on a new line: ");
            String line;

            String input = "";
            int lineCounter = 0;
            String[] size = new String[2];
            int numRows;
            int numCols;
            line = stdin.readLine();
            size = line.split(" ");
            numRows = Integer.parseInt(size[0]);
            numCols = Integer.parseInt(size[1]);
            char[][] map = new char[numRows][numCols];
            ArrayList<Square> flags = new ArrayList<>();
            Square hero = new Square();
            ArrayList<Square> allSquares = new ArrayList<>();
            ArrayList<Square> walls = new ArrayList<>();

            while (lineCounter < numRows && (line = stdin.readLine()) != null)
            {
                int j = 0;
                while (j < numCols)
                {
                    map[lineCounter][j] = line.charAt(j);
                    if (map[lineCounter][j] == '!')
                    {
                        Square flag = new Square(lineCounter, j, false);
                        flags.add(flag);
                        allSquares.add(flag);
                    }
                    else if (map[lineCounter][j] == 'h')
                    {
                        hero = new Square(lineCounter, j, false);
                        allSquares.add(hero);
                    }
                    else if (map[lineCounter][j] == '#')
                    {
                        Square wall = new Square(lineCounter, j, true);
                        walls.add(wall);
                        allSquares.add(wall);
                    }
                    else
                    {
                        Square movableSquare = new Square(lineCounter, j, false);
                        allSquares.add(movableSquare);
                    }
                    j++;
                }

                input += line + "\n";
                lineCounter++;
            } //while

            StringBuilder solutionMap = new StringBuilder();
            int visitedNodes = 0;
            int i = 1;

            HashMap<Square, Integer> costs = new HashMap();
            for (Square flag : flags)
            {
                AStarAlgorithm solver = new AStarAlgorithm(numRows, numCols, hero, flag, walls, allSquares);
                costs.put(flag, new Integer(solver.calculateH(hero, flag)));
            }
            List<Square> sortedFlagList = getSortedFlags(costs);

            int count = 0;
            int numOfFlags = sortedFlagList.size();
            Square flag = sortedFlagList.get(0);
            AStarAlgorithm solver = new AStarAlgorithm(numRows, numCols, hero, flag, walls, allSquares);
            //solver.processAStar(hero, flag);
            solver.processAStar(hero, flag, flags);
            solutionMap.append(solver.displaySolvedMaze());
            System.out.println(solutionMap.toString());
/*
            while (count < numOfFlags)
            {
                Square flag = sortedFlagList.get(0);
                AStarAlgorithm solver = new AStarAlgorithm(numRows, numCols, hero, flag, walls, allSquares);
                //solver.processAStar(hero, flag);
                solver.processAStar(hero, flag, flags);
                hero = flag;
                hero.setParent(null);
                visitedNodes += solver.getNumOfVisitedSquares();

                if (i == flags.size())
                {
                    solutionMap.append(solver.displaySolvedMaze());
                    System.out.println(solutionMap.toString());
                }
                i++;
                count++;

                costs.remove(flag);
                List<Square> remainingFlags = new ArrayList<>(costs.keySet());
                HashMap<Square, Integer> remainingCosts = new HashMap();

                for (Square nextFlag : remainingFlags)
                {
                    AStarAlgorithm nextSolver = new AStarAlgorithm(numRows, numCols, hero, nextFlag, walls, allSquares);
                    remainingCosts.put(nextFlag, new Integer(nextSolver.calculateH(hero, nextFlag)));
                }
                sortedFlagList = getSortedFlags(remainingCosts);
            } //why
            */
            System.out.println("Number of visited nodes: " + solver.getNumOfVisitedSquares());
        } //try
        catch (IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
    }

    public static List<Square> getSortedFlags(Map<Square, Integer> costs)
    {
        List<Map.Entry<Square, Integer>> flagList = new LinkedList<>(costs.entrySet());
        Collections.sort(flagList, new Comparator<Map.Entry<Square, Integer>>() {
            @Override
            public int compare(Map.Entry<Square, Integer> o1, Map.Entry<Square, Integer> o2)
            {
                return (o1.getValue().compareTo(o2.getValue()));
            }
        });

        HashMap<Square, Integer> sortedFlags = new LinkedHashMap<>();
        for (Map.Entry<Square, Integer> flag : flagList)
        {
            sortedFlags.put(flag.getKey(), flag.getValue());
        }

        List<Square> sortedFlagList = new ArrayList<>(sortedFlags.keySet());
        return sortedFlagList;
    }
}

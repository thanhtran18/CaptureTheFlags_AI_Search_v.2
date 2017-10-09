import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class A1Q2 {

    public static void main(String[] args) {
        try
        {
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the maze: ");
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
            //Square flag = new Square();
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
                        Square moveableSquare = new Square(lineCounter, j, false);
                        allSquares.add(moveableSquare);
                    }
                    j++;
                }

                input += line + "\n";
                lineCounter++;
            } //while

            //AStarAlgorithm solver = new AStarAlgorithm(numRows, numCols, hero, flags, walls, allSquares);
            StringBuilder solutionMap = new StringBuilder();
            int visitedNodes = 0;
            int i = 1;

            HashMap<Square, Integer> costs = new HashMap();
            for (Square flag : flags)
            {
                AStarAlgorithm solver = new AStarAlgorithm(numRows, numCols, hero, flag, walls, allSquares);
                costs.put(flag, new Integer(solver.calculateH(hero, flag)));
            }

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


            for (Square flag : sortedFlagList)
            {
                AStarAlgorithm solver = new AStarAlgorithm(numRows, numCols, hero, flag, walls, allSquares);
                ArrayList<Square> solution = solver.processAStar(hero, flag);
                hero = flag;
                hero.setParent(null);
                visitedNodes += solver.getNumOfVisitedSquares();

                if (i == flags.size())
                {

                    solutionMap.append(solver.displayPath(solution));

                    System.out.println(solutionMap.toString());
                }
                i++;

            }
            System.out.println("Number of visited nodes: " + visitedNodes);
            //solver.processAStar(hero, flags);

        } //try
        catch (IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
    }
}

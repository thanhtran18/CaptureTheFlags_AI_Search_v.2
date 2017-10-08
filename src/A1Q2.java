import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
            //ArrayList<Square> flags = new ArrayList<>();
            Square flag = new Square();
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
                        flag = new Square(lineCounter, j, false);
                        //flags.add(flag);
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
            }

            AStarAlgorithm solver = new AStarAlgorithm(numRows, numCols, hero, flag, walls, allSquares);
            solver.processAStar(hero);
            //input is correct
            /*
            for (int i = 0; i < map.length; i++)
            {
                for (int j = 0; j < map[i].length; j++)
                {
                    System.out.print(map[i][j]);
                }
                System.out.println();
            }*/

        } //try
        catch (IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
    }
}

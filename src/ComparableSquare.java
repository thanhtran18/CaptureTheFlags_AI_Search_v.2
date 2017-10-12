//-----------------------------------------
// CLASS: ComparableSquare
//
// Author: Cong Thanh Tran
//
// REMARKS: implemented in order to work with the queue
//
//-----------------------------------------

import java.util.Comparator;

public class ComparableSquare implements Comparator<Square>
{
    @Override
    public int compare(Square s1, Square s2)
    {
        if (s1.getF() > s2.getF())
            return 1;
        else if (s1.getF() < s2.getF())
            return -1;
        else
            return 0;
    }
}

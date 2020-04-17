import java.util.Comparator;

public class pathMinTimeComparator implements Comparator<PathStat> {

    
    @Override
    public int compare(PathStat w1, PathStat w2) {
        return w1.getTime() - w2.getTime();    // return comparison in regular order (min heap)
    }
}
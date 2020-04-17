import java.util.Comparator;

public class pathTimeComparator implements Comparator<PathStat> {

    
    @Override
    public int compare(PathStat w1, PathStat w2) {
        return -1 * (w1.getTime() - w2.getTime());    // return comparison in reverse order (max heap)
        // Same as w2.getTime() - w1.getTime()
    }
}
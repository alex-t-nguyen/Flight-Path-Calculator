import java.util.Comparator;

public class pathCostComparator implements Comparator<PathStat> {

    
    @Override
    public int compare(PathStat w1, PathStat w2) {
        return -1 * (w1.getCost() - w2.getCost());    // return comparison in reverse order (max heap)
        // Same as w2.weight - w1.weight
    }
}
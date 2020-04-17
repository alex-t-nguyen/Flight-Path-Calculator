import java.util.Comparator;

public class pathMinCostComparator implements Comparator<PathStat> {

    
    @Override
    public int compare(PathStat w1, PathStat w2) {
        return w1.getCost() - w2.getCost();    // return comparison in regular order (min heap)
    }
}
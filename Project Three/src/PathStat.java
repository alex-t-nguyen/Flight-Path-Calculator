
public class PathStat {
    private int cost;
    private int time;
    private int index;

    public PathStat()
    {
        cost = 0;
        time = 0;
        index = 0;
    }

    public PathStat(int cost, int time, int index)
    {
        this.cost = cost;
        this.time = time;
        this.index = index;
    }

    public int getCost()
    {
        return cost;
    }

    public int getTime()
    {
        return time;
    }

    public int getIndex()
    {
        return index;
    }

    public void setCost(int cost)
    {
        this.cost = cost;
    }

    public void setTime(int time)
    {
        this.time = time;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

}


package warczynski.jedrzej.game;
public class Point {
    public int x;
    public int y;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object ob)
    {
        if(ob == this) return true;
        if(ob == null) return false;
        if(getClass() != ob.getClass()) return false;
        Point other = (Point)ob;   
        if(other.x != x) return false;
        return other.y == y;
    }
}

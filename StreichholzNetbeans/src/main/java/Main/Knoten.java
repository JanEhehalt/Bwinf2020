
package Main;

public class Knoten {
    public int x;
    public int y;
    public int id;
    Knoten[] edges = new Knoten[12];
    public Knoten(int id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
    }
}

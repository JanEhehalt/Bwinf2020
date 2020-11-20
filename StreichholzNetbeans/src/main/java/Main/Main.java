
package Main;


public class Main {
    public static void main(String[] args){
        
        Knoten k = new Knoten(0,10,5);
        newNode(new Knoten(1,15,15),k,4);
        newNode(new Knoten(2,15,15),k,5);
        newNode(new Knoten(3,15,15),k,6);
        
        boolean[] nice = new boolean[10];
        for(int i = 0; i < 10; i++){
            nice[i] = false;
        }
        k.print(nice);
        
    }
    
    public static void newNode(Knoten k, Knoten src, int angle){
        src.edges[angle] = k;
        k.edges[getOpposite(angle)] = src;
    }
    
    public static int getOpposite(int i){
        int opposite = i+6;
        if(opposite > 11){
            opposite -= 12;
        }
        return opposite;
    }
}

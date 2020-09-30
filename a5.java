import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Main{
    public static void main(String[] args){
        try{
            File data = new File(args[0]);
            Scanner sc = new  Scanner(data);

            
            while(sc.hasNextLine()){
            	
            }
        }
        catch (FileNotFoundException e){
            System.out.println("The file does not exist");
            e.printStackTrace();
        }
    }
}

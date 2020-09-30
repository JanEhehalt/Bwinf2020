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

/*


class Student{
	boolean gifted;
	int metWish;
	int[] wishes = new int[3];
	public schueler(int wishOne, int wishTwo, int wishThree){
		wishes[0] = wishOne;
		wishes[1] = wishTwo;
		wishes[2] = wishThree;
	}
}

// ERSTWÜNSCHE VERTEILEN
static void geschenke(int n){
	for(int i = 0; i < students.length; i++){
		if(students[i].gifted) continue;
		if(presents[student.wishes[n]] = true) continue;
		else{
			student.gifted = true;
			student.metWish = n;
			presents[student.wishes[n]] = true;
		}
	}
}
static void nachverteilung(){
	for(int i = 0; i < students.length; i++){
		if(students[i].gifted) continue;
		else 
			for(int n = 0; n < presents.length; n++){
				if(!presents[i]){
					student.gifted = true;
					student.metWish = -1;
					presents[n] = true;
				}
			}
	}
}
*/

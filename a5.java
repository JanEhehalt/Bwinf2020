import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


class Student{
	boolean gifted;
	int metWish;
	int[] wishes = new int[3];
	public Student(int[] wishes){
		this.wishes = wishes;
	}
}

class Main{
    public static void main(String[] args){
        try{
            File data = new File(args[0]);
            Scanner sc = new  Scanner(data);
            
            ArrayList<Student> lol = new ArrayList<Student>();
            sc.nextLine();
            while(sc.hasNextLine()){
            	String line = sc.nextLine();
            	lol.add(new Student(parseFileLine(line)));
            	//System.out.println(line);
            }
            
            for(Student s : lol) {
            	System.out.println(s.wishes[0] + "|" + s.wishes[1] + "|" + s.wishes[2]);
            }
        }
        catch (FileNotFoundException e){
            System.out.println("The file does not exist");
            e.printStackTrace();
        }
        
        
    }
    static int[] parseFileLine(String line) {
    	int[] ints = new int[3];

    	int lastDigit;
    	
    	for(int i=0; i < line.length(); i++) {
    		if(line.substring(i, i+1).equals(" ")) {
    			for(int j=0; j<3; j++) {
    				if(ints[j] == 0) {
    					ints[j] = line.substring(lastDigit, i);
    					lastDigit = i;
    				}
    			}
    		}
    	}
    	
    	return ints;
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
}



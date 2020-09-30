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
        	if(args.length == 0) {
        		System.out.println("You need to give a filename as an argument");
        		return;
        	}
        	
            File data = new File(args[0]);
            Scanner sc = new  Scanner(data);
            
            ArrayList<Integer> ints = new ArrayList<Integer>();
            sc.nextLine();
            
            while (sc.hasNext()) {
                if (sc.hasNextInt()) {
                    ints.add(sc.nextInt());
                } else {
                    sc.next();
                }
            }
            sc.close();
            
            Student[] students = new Student[ints.size() / 3];
            for(int i=0; i < ints.size(); i++) {
            	if(i % 3 == 0) {
            		int[] wishes = new int[3];
            		int tempIndex = i/3;
            		
            		wishes[0] = ints.get(tempIndex);
            		wishes[1] = ints.get(tempIndex + 1);
            		wishes[2] = ints.get(tempIndex + 2);
            		
            		students[tempIndex] = new Student(wishes);
            	}
            }
            
            for(Student s : students) {
            	System.out.println(s.wishes[0] + "|" + s.wishes[1] + "|" + s.wishes[2]);
            }
            
        }
        catch (FileNotFoundException e){
            System.out.println("The file does not exist");
            e.printStackTrace();
        }
        
        
    }

    /*
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
    					students[i].gifted = true;
    					student[i].metWish = -1;
    					presents[n] = true;
    				}
    			}
    	}
    }
    */
}



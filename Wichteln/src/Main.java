import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


class Student{
	boolean gifted;
	int metWish;
	int presentId;
	int[] wishes = new int[3];
	public Student(int[] wishes){
		this.wishes = wishes;
	}
	
}

class Main{
    public static void main(String[] args){
        try{
        	File data;
    		if(args.length == 0) {
        		System.out.println("Ungültiger Dateiname: Automatische Einlesung der Datei: wichteln1.txt");
        		System.out.println();
        		data = new File("/wichteln1.txt");
        	}
        	else {
        		data = new File(args[0]);
        	}
            Scanner sc = new  Scanner(data);
            
            ArrayList<Integer> ints = new ArrayList<Integer>();
            sc.nextLine();
            
            while (sc.hasNext()) {
                if (sc.hasNextInt()) {
                    ints.add(sc.nextInt()-1);
                } else {
                    sc.next();
                }
            }
            sc.close();
            
            Student[] students = new Student[ints.size() / 3];
            for(int i=0; i < ints.size(); i+=3) {
        		int[] wishes = new int[3];
        		int tempIndex = i/3;
        		
        		wishes[0] = ints.get(i);
        		wishes[1] = ints.get(i + 1);
        		wishes[2] = ints.get(i + 2);

        		students[tempIndex] = new Student(wishes);
            }
            for(int i = 0; i < students.length; i++) {
            	System.out.println("Wünsche Schüler "+i + ": "+ students[i].wishes[0] + "|" + students[i].wishes[1] + "|" + students[i].wishes[2]);
            }
            boolean[] presents = new boolean[students.length];
            
            distributePresents(0, students, presents);
            distributePresents(1, students, presents);
            distributePresents(2, students, presents);
            fillLastWishes(students, presents);
            
            int falseAmount = 0;
            for(int i = 0; i < students.length; i++) {
            	if(!students[i].gifted) falseAmount++;
            	else {
            		System.out.println("Schüler "+i + " bekommt das Geschenk " + students[i].presentId+".");
            	}
            }
            int trueAmount = students.length - falseAmount;
            System.out.println("Unbeschenkt: "+ falseAmount + "    Beschenkt: " + trueAmount);
            
            int score = 0;
            int firstcounter = 0;
            int secondcounter = 0;
            int thirdcounter = 0;
            for(Student s : students) {
            	if(s.metWish == 0) {
            		firstcounter++;
            		score += 3;
            	}
            	else if(s.metWish == 1) {
            		secondcounter++;
            		score += 2;
            	}
            	else if(s.metWish == 2) {
            		thirdcounter++;
            		score += 1;
            	}
            }
            System.out.println("Insgesamter score: "+score);
            System.out.println("Erfüllte Erstwünsche: "+firstcounter);
            System.out.println("Erfüllte Zweitwünsche: "+secondcounter);
            System.out.println("Erfüllte Drittwünsche: "+thirdcounter);
            for(Student s : students) {
            	//System.out.println(s.presentId);
            }
            
        }
        catch (FileNotFoundException e){
            System.out.println("The file does not exist");
            e.printStackTrace();
        }
        
        
    }

    static void distributePresents(int n, Student[] students, boolean[] presents){
    	for(int i = 0; i < students.length; i++){
    		if(students[i].gifted) continue;
    		if(presents[students[i].wishes[n]]) continue;
			students[i].gifted = true;
			students[i].metWish = n;
			students[i].presentId = students[i].wishes[n];
			presents[students[i].wishes[n]] = true;
    	}
    }
    static void fillLastWishes(Student[] students, boolean[] presents){
    	for(int i = 0; i < students.length; i++){
    		if(!students[i].gifted) {
    			for(int n = 0; n < students.length; n++){
    				if(!presents[n]){
    					students[i].gifted = true;
    					students[i].metWish = -1;
    	    			students[i].presentId = n;
    					presents[n] = true;
    					break;
    				}
    			}
			}
    	}
    }
}



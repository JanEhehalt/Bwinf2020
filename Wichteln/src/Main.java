import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


class StudentOld{
	boolean gifted;
	int metWish;
	int presentId;
	int[] wishes = new int[3];
	public StudentOld(int[] wishes){
		this.wishes = wishes;
	}
	
}

class Main{
    public static void main(String[] args){
        try{
        	
        	if(args.length == 0 && false) {
        		System.out.println("You need to give a filename as an argument");
        		return;
        	}

            //File data = new File("wichteln1.txt");
            File data = new File(args[0]);
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
            
            StudentOld[] students = new StudentOld[ints.size() / 3];
            for(int i=0; i < ints.size(); i+=3) {
        		int[] wishes = new int[3];
        		int tempIndex = i/3;
        		
        		System.out.println(tempIndex);
        		
        		wishes[0] = ints.get(i);
        		wishes[1] = ints.get(i + 1);
        		wishes[2] = ints.get(i + 2);

        		students[tempIndex] = new StudentOld(wishes);
            }
            for(StudentOld s : students) {
            	System.out.println(s.wishes[0] + "|" + s.wishes[1] + "|" + s.wishes[2]);
            }
            boolean[] presents = new boolean[students.length];
            
            geschenke(0, students, presents);
            geschenke(1, students, presents);
            geschenke(2, students, presents);
            nachverteilung(students, presents);
            
            int falseAmount = 0;
            for(StudentOld s : students) {
                System.out.println(s.gifted);
            	if(!s.gifted) falseAmount++;
            }
            System.out.println("--");
            for(boolean s : presents) {
                System.out.println(s);
            }
            int trueAmount = students.length - falseAmount;
            System.out.println("Unbeschenkt: "+ falseAmount + "    Beschenkt: " + trueAmount);
            
            int score = 0;
            for(StudentOld s : students) {
            	if(s.metWish != -1)
            	score += 3-s.metWish;
            }
            System.out.println(score);
            
            for(StudentOld s : students) {
            	//System.out.println(s.presentId);
            }
            
        }
        catch (FileNotFoundException e){
            System.out.println("The file does not exist");
            e.printStackTrace();
        }
        
        
    }

    static void geschenke(int n, StudentOld[] students, boolean[] presents){
    	for(int i = 0; i < students.length; i++){
    		if(students[i].gifted) continue;
    		if(presents[students[i].wishes[n]]) continue;
			students[i].gifted = true;
			students[i].metWish = n;
			students[i].presentId = students[i].wishes[n];
			presents[students[i].wishes[n]] = true;
    	}
    }
    static void nachverteilung(StudentOld[] students, boolean[] presents){
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



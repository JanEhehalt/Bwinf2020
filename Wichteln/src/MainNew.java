import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


class Student{
	boolean hasGift;
	int metWish;
	int presentId;
	int[] wishes = new int[3];
	boolean[] asked;
	
	public Student(int[] wishes, int presentAmount){
		this.wishes = wishes;
		this.hasGift = false;
		this.asked = new boolean[presentAmount];
	}
	
}

class Present{
	int studentId;
	int wish;
	
	public Present() {
		studentId = -1;
		wish = 4;
	}
}

class MainNew{
	
	Student[] students;
	Present[] presents;
	
    public static void main(String[] args){
        try{
        	
        	if(args.length == 0 && false) {
        		System.out.println("You need to give a filename as an argument");
        		return;
        	}

            File data = new File("src/wichteln1.txt");
            //File data = new File(args[0]);
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
        		
        		System.out.println(tempIndex);
        		
        		wishes[0] = ints.get(i);
        		wishes[1] = ints.get(i + 1);
        		wishes[2] = ints.get(i + 2);

        		students[tempIndex] = new Student(wishes, ints.size() / 3, i);
            }
            for(Student s : students) {
            	System.out.println(s.wishes[0] + "|" + s.wishes[1] + "|" + s.wishes[2]);
            }
            
            Present[] presents = new Present[students.length];
            
            
            // Start of gale-shapley algorithm
            // Initial round
            for(int i=0; i<students.length; i++) {
            	students[i].tryPresent(presents, students[i].wishes[0], 0);
            }
            
            // Following rounds
            boolean finished = false;
            do {
            	finished = true;
            	for(int i=0; i<students.length; i++) {
            		if(!students[i].hasGift) {
            			finished = false;
            			
            			if(!students[i].asked[students[i].wishes[0]]) {
            				students[i].tryPresent(presents, students[i].wishes[0], 0);
            			} else if(!students[i].asked[students[i].wishes[1]]) {
            				students[i].tryPresent(presents, students[i].wishes[2], 2);
            			} else if(!students[i].asked[students[i].wishes[2]]) {
            				students[i].tryPresent(presents, students[i].wishes[2], 2);
            			} else {
            				for(int j = 0; j<presents.length; j++) {
            					if(!students[i].asked[j]) {
            						students[i].tryPresent(presents, j, 3);
            					}
            				}
            			}
            		}
            	}
            } while(!finished);
            
        }
        catch (FileNotFoundException e){
            System.out.println("The file does not exist");
            e.printStackTrace();
        }
        
        
    }

	private void tryPresentChange(int presentId, int wish) {
		if(presents[presentId].changeStudent(wish, index)) {
			hasGift = true;
			this.presentId = presentId;
		}
		asked[presentId] = true;
	}

	private boolean changeStudent(int wish, int studentId) {
		if(this.wish > wish){
			this.studentId = studentId;
			this.wish = wish;
			
			return true;
		}
		else {
			return false;
		}
	}
    
}



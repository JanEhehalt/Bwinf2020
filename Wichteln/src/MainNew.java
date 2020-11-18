import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


class Student{
	boolean hasGift;
	int index;
	int presentId;
	int[] wishes = new int[3];
	boolean[] asked;
	
	public Student(int[] wishes, int presentAmount, int index){
		this.presentId = -1;
		this.wishes = wishes;
		this.hasGift = false;
		this.asked = new boolean[presentAmount];
		this.index = index;
	}
	
	void requestPresent(Present[] presents, Student[] students, int presentId, int wish) {
		if(presents[presentId].changeStudent(students, wish, index)) {
			hasGift = true;
			if(this.presentId >= 0) {
				presents[this.presentId] = new Present();
			}
			this.presentId = presentId;
		}
		asked[presentId] = true;
	}
}

class Present{
	int studentId;
	int wish;
	
	public Present() {
		studentId = -1;
		wish = 4;
	}
	
	boolean changeStudent(Student[] students, int wish, int studentId) {
		if(this.wish > wish){
			if(this.studentId >= 0) {
				students[this.studentId].hasGift = false;
			}
			this.studentId = studentId;
			this.wish = wish;
			students[studentId].hasGift = true;
			
			return true;
		}
		else {
			return false;
		}
	}
}

class MainNew{
	
    public static void main(String[] args){
        try{
        	
        	File data;
        	if(args.length == 0) {
        		data = new File("src/wichteln0.txt");
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
        		
        		//System.out.println(tempIndex);
        		
        		wishes[0] = ints.get(i);
        		wishes[1] = ints.get(i + 1);
        		wishes[2] = ints.get(i + 2);

        		students[tempIndex] = new Student(wishes, ints.size() / 3, tempIndex);
            }
            for(Student s : students) {
            	//System.out.println(s.wishes[0] + "|" + s.wishes[1] + "|" + s.wishes[2]);
            }
            
            Present[] presents = new Present[students.length];
            for(int i=0; i<presents.length; i++) {
            	presents[i] = new Present();
            }
            
            int[][] wishMatrix = new int[students.length][presents.length];
            for(int i = 0; i < students.length; i++) {
            	Student temp = students[i];
            	wishMatrix[i][temp.wishes[0]] = 0;
            	wishMatrix[i][temp.wishes[1]] = 1;
            	wishMatrix[i][temp.wishes[2]] = 2;
            }
            
            // Beginn des Algorithmus
            // step 0: Eindeutige höchste Wünsche werden zugeteilt
            // step 1: Uneindeutige wünsche werden den mit einzigem Wunsch vergeben
            // step 2: Erstes Geschenk wird an ersten mich höchsten Wunsch darauf vergeben
            // Übergänge: 0 (false) -> 1	1 (true) -> 0		1(false) -> 2
            
            int step = 0;
            boolean finished = false;
            do {
            	if(step == 0) {
            		for(int i = 0; i < wishMatrix[0].length; i++) {
            			int amountFirst = 0;
            			int amountSecond = 0;
            			int amountThird = 0;
            			for(int j = 0; j < wishMatrix.length; j++) {
            				switch(wishMatrix[i][j]) {
            				case 0:
            					amountFirst++;
            					break;
            				case 1:
            					amountSecond++;
            					break;
            				case 2:
            					amountThird++;
            					break;
            				}
            			}
            			
            			int wish = 0;
            			if(amountFirst == 1) {
            				wish = 0;
            			}
            			else if(amountFirst == 0) {
            				if(amountSecond == 1) {
            					wish = 1;
            				}
            				else if(amountSecond == 0) {
            					if(amountThird == 1) {
            						wish = 2;
            					}
            				}
            			}
            			else {
            				continue;
            			}
            			
            			
            		}
            	}
            	else if(step == 1) {
            		
            	}
            	
            	finished = true;
            	for(Student s : students) {
            		if(!s.hasGift) {
            			finished = false;
            		}
            	}
            } while (!finished);
            
            /*
            // Start of gale-shapley algorithm
            // Initial round
            for(int i=0; i<students.length; i++) {
            	students[i].requestPresent(presents, students, students[i].wishes[0], 0);
            }
            
            // Following rounds
            boolean finished = false;
            do {
            	finished = true;
            	for(int i=0; i<students.length; i++) {
            		if(!students[i].hasGift) {
            			finished = false;
            			
            			if(!students[i].asked[students[i].wishes[0]]) {
            				students[i].requestPresent(presents, students, students[i].wishes[0], 0);
            			} else if(!students[i].asked[students[i].wishes[1]]) {
            				students[i].requestPresent(presents, students, students[i].wishes[1], 1);
            			} else if(!students[i].asked[students[i].wishes[2]]) {
            				students[i].requestPresent(presents, students, students[i].wishes[2], 2);
            			} else {
            				for(int j = 0; j<presents.length; j++) {
            					if(!students[i].asked[j]) {
            						students[i].requestPresent(presents, students, j, 3);
            					}
            				}
            			}
            		}
            	}
            } while(!finished);
            */
            for(int k=0; k<presents.length; k++) {
            	System.out.println(presents[k].studentId);
            }
            
            int score = 0;
            for(Student student : students) {
            	if(student.presentId == student.wishes[0])
            		score += 3;
            	else if(student.presentId == student.wishes[1])
            		score += 2;
            	else if(student.presentId == student.wishes[2])
            		score += 1;
            }
            System.out.println("");
            System.out.println("");
            System.out.println(score);
            
        }
        catch (FileNotFoundException e){
            System.out.println("The file does not exist");
            e.printStackTrace();
        }
        
        
    }
    
}



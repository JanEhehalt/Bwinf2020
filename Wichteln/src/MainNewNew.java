import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Student{
	public int id;
	public int present;
	public int[] wishes = new int[3];
	public Student(int id, int[] wishes) {
		this.id = id;
		this.wishes = wishes;
		this.present = -1;
	}
}
class Present{
	public int id;
	public int student;
	public int fulfillingWish;
	public ArrayList<Integer> applicantsForOne = new ArrayList<>();
	public ArrayList<Integer> applicantsForTwo = new ArrayList<>();
	public ArrayList<Integer> applicantsForThree = new ArrayList<>();
	public Present(int id) {
		this.id = id;
		this.student = -1;
		this.fulfillingWish = 4;
	}
}


public class MainNewNew {
	
	public static void main(String[] args) {
			
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

        		students[tempIndex] = new Student(tempIndex, wishes);
            }
            for(Student s : students) {
            	System.out.println(s.wishes[0] + "|" + s.wishes[1] + "|" + s.wishes[2]);
            }
        	System.out.println();
        	
        	Present[] presents = new Present[students.length];
            for(int i=0; i<presents.length; i++) {
            	presents[i] = new Present(i);
            	ArrayList<Integer> wishesForOne = new ArrayList<>();
            	ArrayList<Integer> wishesForTwo = new ArrayList<>();
            	ArrayList<Integer> wishesForThree = new ArrayList<>();
            	for(int j = 0; j < students.length; j++) {
            		if(students[j].wishes[0] == i) {
            			wishesForOne.add(j);
            		}
					if(students[j].wishes[1] == i) {
            			wishesForTwo.add(j);
		          	}
					if(students[j].wishes[2] == i) {
            			wishesForThree.add(j);
					}
            	}
            	presents[i].applicantsForOne = wishesForOne;
            	presents[i].applicantsForTwo = wishesForTwo;
            	presents[i].applicantsForThree = wishesForThree;
            }
            for(Present p : presents) {
            	System.out.print(p.applicantsForOne + " ");
            	System.out.print(p.applicantsForTwo + " ");
            	System.out.print(p.applicantsForThree+ " ");
            	System.out.println();
            }
            
            boolean finished = true;
            int step = 1;
            do {
            	if(step == 1) {
            		for(int i = 0; i < presents.length; i++) {
            			if(presents[i].applicantsForOne.size() >= 1) {
                			if(presents[i].applicantsForOne.size() == 1) {
                				presents[i].student = presents[i].applicantsForOne.get(0);
                				students[presents[i].applicantsForOne.get(0)].present = i;
                				continue;
                			}
                			else {
                				finished = false;
                				continue;
                			}
            			}
            			else if(presents[i].applicantsForTwo.size() >= 1) {
                			if(presents[i].applicantsForTwo.size() == 1) {
                				presents[i].student = presents[i].applicantsForTwo.get(0);
                				students[presents[i].applicantsForTwo.get(0)].present = i;
                				continue;
                			}
                			else {
                				finished = false;
                				continue;
                			}
            			}
            			else if(presents[i].applicantsForThree.size() >= 1) {
                			if(presents[i].applicantsForThree.size() == 1) {
                				presents[i].student = presents[i].applicantsForThree.get(0);
                				students[presents[i].applicantsForThree.get(0)].present = i;
                				continue;
                			}
                			else {
                				finished = false;
                				continue;
                			}
            			}
            		}
            	}
            	
            }while(!finished);
            
            
		}
		catch (FileNotFoundException e){
            System.out.println("The file does not exist");
            e.printStackTrace();
        }
	}
}

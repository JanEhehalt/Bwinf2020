import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


class Student{
	boolean hasGift;
	int presentId;
	int[] wishes = new int[3];
	
	public Student(int[] wishes){
		this.presentId = -1;
		this.wishes = wishes;
		this.hasGift = false;
	}
}

class Present{
	int studentId;
	
	public Present() {
		studentId = -1;
	}
}

class Wichteln{
	static Student[] students;
	static Present[] presents;
	static int[][] verboseMatrix;
	static int[][] matrix;
	
	private static void setStudent(int p, int s) {
		if(presents[p].studentId >= 0) {
			students[presents[p].studentId].hasGift = false;
		}
		presents[p].studentId = s;
		students[s].hasGift = true;
		
		for(int i = 0; i < presents.length; i++) {
			matrix[s][i] = 0;
		}
		for(int i = 0; i < presents.length; i++) {
			matrix[i][p] = 0;
		}
	}
	
    public static void main(String[] args){
    	ArrayList<Integer> ints = new ArrayList<Integer>();
        try{
        	
        	File data;
        	if(args.length == 0) {
        		data = new File("src/wichteln1.txt");
        	}
        	else {
        		data = new File(args[0]);
        	}
            Scanner sc = new  Scanner(data);
            sc.nextLine();
            
            while (sc.hasNext()) {
                if (sc.hasNextInt()) {
                    ints.add(sc.nextInt()-1);
                } else {
                    sc.next();
                }
            }
            sc.close();
        }   
        catch (FileNotFoundException e){
            System.out.println("The file does not exist");
            e.printStackTrace();
        }
            
            students = new Student[ints.size() / 3];
            for(int i=0; i < ints.size(); i+=3) {
        		int[] wishes = new int[3];
        		int tempIndex = i/3;
        		
        		//System.out.println(tempIndex);
        		
        		wishes[0] = ints.get(i);
        		wishes[1] = ints.get(i + 1);
        		wishes[2] = ints.get(i + 2);

        		students[tempIndex] = new Student(wishes);
            }
            
            presents = new Present[students.length];
            for(int i=0; i<presents.length; i++) {
            	presents[i] = new Present();
            }
            
            verboseMatrix = new int[students.length][presents.length];
            for(int i = 0; i < students.length; i++) {
            	for(int j = 0; j < students.length; j++) {
            		verboseMatrix[i][j] = 4;
            	}
            }
            
            for(int i = 0; i < students.length; i++) {
            	Student temp = students[i];
            	verboseMatrix[i][temp.wishes[0]] = 0;
            	verboseMatrix[i][temp.wishes[1]] = 1;
            	verboseMatrix[i][temp.wishes[2]] = 2;
            }
            
            matrix = new int[students.length][presents.length];
            // Die matrix wird so weit wie möglich vereinfacht p: Present, s: student
            for(int p = 0; p < students.length; p++) {
            	int highestWish = 2;
            	for(int s = 0; s < students.length; s++) {
            		if(verboseMatrix[s][p] < highestWish) {
            			highestWish = verboseMatrix[s][p];
            		}
            	}
            	
            	for(int s = 0; s < students.length; s++) {
            		if(verboseMatrix[s][p] == highestWish) {
            			matrix[s][p] = 1;
            		}
            	}
            }
            System.out.println();
            for(int i = 0; i < students.length; i++) {
            	for(int j = 0; j < students.length; j++) {
            		System.out.print(matrix[i][j] + " ");
            	}
            	System.out.println();
            }
            System.out.println();
            
            int step = 0;
            boolean finished = false;
            
            while(!finished) {
            	if(step == 0) {
            		boolean stepFinished = false;
            		while(!stepFinished) {
            			stepFinished = true;
            			
            			for(int p = 0; p < presents.length; p++) {
            				int counter = 0;
            				for(int s = 0; s < students.length; s++) {
            					if(matrix[s][p] == 1) {
            						counter++;
            					}
            				}
            				
            				if(counter == 1) {
            					stepFinished = false;
            					for(int s = 0; s < students.length; s++) {
                					if(matrix[s][p] == 1) {
                						setStudent(p, s);
                						students[s].presentId = p;
                					}
                				}
            				}
            			}
            		}
            		step = 1;
            	}
            	
            	if(step == 1) {
            		boolean stepFinished = false;
            		while(!stepFinished) {
            			stepFinished = true;
            			
            			for(int s = 0; s < presents.length; s++) {
            				int counter = 0;
            				for(int p = 0; p < students.length; p++) {
            					if(matrix[s][p] == 1) {
            						counter++;
            					}
            				}
            				
            				if(counter == 1) {
            					stepFinished = false;
            					for(int p = 0; p < students.length; p++) {
                					if(matrix[s][p] == 1) {
                						setStudent(p, s);
                						students[s].presentId = p;
                						step = 0;
                					}
                				}
            				}
            			}
            		}
            		step = 2;
            	}
            	
            	if(step == 2) {
            		for(int p = 0; p < presents.length; p++) {
            			for(int s = 0; s < students.length; s++) {
            				if(matrix[s][p] == 1) {
            					setStudent(p, s);
            					students[s].presentId = p;
        						step = 0;
        						break;
            				}
            			}
            			if(step == 0) {
            				break;
            			}
            		}
            		if(step == 2) {
            			finished = true;
            		}
            	}
            }
            
            // Die restlichen Geschenke werden einfach verteilt
            for(int p = 0; p < presents.length; p++) {
				if(presents[p].studentId == -1) {
					for(int s = 0; s < students.length; s++) {
						if(!students[s].hasGift) {
							setStudent(p, s);
							students[s].presentId = p;
						}
					}
				}
    		}
            
            for(int k=0; k<presents.length; k++) {
            	System.out.println(presents[k].studentId);
            }
            
            
            for(int i = 0; i < students.length; i++) {
            	for(int j = 0; j < students.length; j++) {
            		System.out.print(verboseMatrix[i][j] + " ");
            	}
            	System.out.println();
            }
            System.out.println();
            for(int i = 0; i < students.length; i++) {
            	for(int j = 0; j < students.length; j++) {
            		System.out.print(matrix[i][j] + " ");
            	}
            	System.out.println();
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
            System.out.println();
        
    }
    
}



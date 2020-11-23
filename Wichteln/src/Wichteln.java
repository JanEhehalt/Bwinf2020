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
		students[s].presentId = p;
		students[s].hasGift = true;
		
		for(int i = 0; i < presents.length; i++) {
			//matrix[s][i] = 0;
			verboseMatrix[s][i] = 3;
		}
		for(int i = 0; i < presents.length; i++) {
			//matrix[i][p] = 0;
			verboseMatrix[i][p] = 3;
		}
	}
	
	public static int getHighestWish(int[][] wishMatrix, int p) {
		int highestWish = 4;
		for(int i=0; i < students.length; i++) {
			if(wishMatrix[i][p] < highestWish) {
				highestWish = wishMatrix[i][p];
			}
		}
		return highestWish;
	}
	
	public static int getWishAmount(int[][] wishMatrix, int p, int wish) {
		int counter = 0;
		for(int i=0; i < students.length; i++) {
			if(wishMatrix[i][p] == wish) {
				counter++;
			}
		}
		return counter;
	}
	
	public static int getHighestPossibleWish(int[][] wishMatrix, int s) {
		int highestWish = 4;
		for(int i=0; i < students.length; i++) {
			if(wishMatrix[s][i] < highestWish) {
				highestWish = wishMatrix[s][i];
			}
		}
		return highestWish;
	}
	
	public static int getWishAmountStudent(int[][] wishMatrix, int s) {
		int counter = 0;
		for(int i=0; i < students.length; i++) {
			if(wishMatrix[s][i] < 3) {
				counter++;
			}
		}
		return counter;
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
            		verboseMatrix[i][j] = 3;
            	}
            }
            
            for(int i = 0; i < students.length; i++) {
            	Student temp = students[i];
            	verboseMatrix[i][temp.wishes[0]] = 0;
            	verboseMatrix[i][temp.wishes[1]] = 1;
            	verboseMatrix[i][temp.wishes[2]] = 2;
            }
            
        	int step = 0;
            boolean finished = false;
            
            while(!finished) {
            	if(step == 0) {
            		boolean stepFinished = false;
            		while(!stepFinished) {
            			stepFinished = true;
            			System.out.println("Step 0");
            			for(int p = 0; p < presents.length; p++) {
            				int highestWish = getHighestWish(verboseMatrix, p);
            				int amount = getWishAmount(verboseMatrix, p, highestWish);
            				
            				if(amount == 1 && highestWish < 4) {
            					stepFinished = false;
            					for(int s = 0; s < students.length; s++) {
            						if(verboseMatrix[s][p] == highestWish) {
            							setStudent(p, s);
                						break;
            						}
                				}
            				}
            			}
            		}
            		step = 1;
            	}
            	
            	if(step == 1) {
            		System.out.println("Step 1");
            		boolean stepFinished = false;
            			
        			for(int s = 0; s < presents.length && !stepFinished; s++) {
        				int highestWish = getHighestPossibleWish(verboseMatrix, s);
        				int amount = getWishAmountStudent(verboseMatrix, s);
        				
        				if(amount == 1) {
        					for(int p = 0; p < students.length; p++) {
        						int highestWishPresent = getHighestWish(verboseMatrix, p);
            					if(verboseMatrix[s][p] == highestWish && highestWish <= highestWishPresent) {
            						setStudent(p, s);
            						stepFinished = true;
            						break;
            					}
            				}
        				}
        			}
            		if(stepFinished) {
            			step = 0;
            		}
            		else {
            			step = 2;
            		}
            	}
            	
            	if(step == 2) {
            		System.out.println("Step 2");
            		int highestWish = 3;
            		for(int p = 0; p < presents.length; p++) {
            			int highestWishPresent = getHighestWish(verboseMatrix, p);
            			if(highestWishPresent < highestWish) {
            				highestWish = highestWishPresent;
            			}
            		}
            		if(highestWish == 3) {
            			break;
            		}
            		
            		for(int p = 0; p < presents.length; p++) {
            			for(int s = 0; s < students.length; s++) {
            				if(verboseMatrix[s][p] == highestWish) {
            					setStudent(p, s);
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
							break;
						}
					}
				}
    		}
            
            /*
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
            }*/

            int first = 0;
            int second = 0;
            int third = 0;
            
            int score = 0;
            for(Student student : students) {
            	if(student.presentId == student.wishes[0]) {
            		score += 3;
            		first++;
            	}
            	else if(student.presentId == student.wishes[1]) {
            		score += 2;
            		second++;
            	}
            	else if(student.presentId == student.wishes[2]) {
            		score += 1;
            		third++;
            	}
            }
            System.out.println("");
            System.out.println("");
            System.out.println(score);
            System.out.println();
            System.out.println(first);
            System.out.println(second);
            System.out.println(third);
            System.out.println();
        
    }
    
}



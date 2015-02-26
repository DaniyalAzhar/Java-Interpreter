package interpreter;
import java.io.*;
import java.util.HashMap;

public class interpreter {
	
	void executeCommand(String line,HashMap hashTable,int lineNumber){
		int x;
		lineNumber+=1;
		int temp1=0,temp2=0,temp3;
		String[] index = new String[x=line.split(" ").length];
		index=line.split(" ");
		if(index[0].equals("Let") || index[0].equals("let")){ // if let keyword used:
			myCharacter a = new myCharacter();                //create a new object.
			a.str=index[1];                                   //whose name is at the next index.
			if(x>2 && index[2].equals("=")){                  //if next index is '=', then ready to assign a value.
				if(x<4){
					System.out.println("Error(line "+lineNumber+"): expression cannot end with an operator.");
					return;
				}
				else
					a.value=Integer.parseInt(index[3]);           //put entry in hashtable.
			}
			else if(x>3 && !index[2].equals("=")){
				System.out.println("Error(line "+lineNumber+"): Invalid operator.");
				a.value=338;
			}
			else{                                             //if there is no next index then assign a special value(338) which indicates that the variable is not initialized.
				a.value=338;
			}
			hashTable.put(a.str, a.value);
		}
		else if(!index[0].equals("Let") || !index[0].equals("let")){ //if let keyword not used
			if((hashTable.get(index[0])) != null){ //then it should be a variable name.			
				if(x>1 && index[1].equals("=")){                  //if next index is '=', then ready to assign a value.
					String regex2 = "\\d+";
					if(index[2].matches(regex2)){ //if operand 2 is a digit.
						temp1=Integer.parseInt(index[2]);
					}
					else if((hashTable.get(index[2])) != null){  //if operand 2 is a hashtable entry

						temp1=Integer.parseInt(hashTable.get(index[2]).toString()); //then assign value.
						if(temp1 == 338){                                           //If special value(338), then it means garbage value.
							System.out.println("Error(line "+lineNumber+"): variable not initialized.");
							return;
						}
					}
					else{
						System.out.println("Error(line "+lineNumber+"): variable doesn't exist."); //if not a digit nor an entry.
						return;
					}
				}
				else{
					System.out.println("Error(line "+lineNumber+"): Syntax error.");    //if a character written between program.
					return;
				}
					
				if(index[3].equals("+") ||index[3].equals("-") ||index[3].equals("*") ||index[3].equals("/")){
					if(index[4] == null){
						System.out.println("Error(line "+lineNumber+"): expression cannot end with an operator.");
						return;
					}
					else{
						String regex1 = "\\d+";
						if(index[4].matches(regex1)){ //if operand 2 is a digit.
							temp2=Integer.parseInt(index[4]);
						}
						else if((hashTable.get(index[4])) != null){  //if operand 2 is a hashtable entry
							temp2=Integer.parseInt(hashTable.get(index[4]).toString()); //then assign value.
							if(temp2 == 338){                                           //If special value(338), then it means garbage value.
								System.out.println("Error(line "+lineNumber+"): variable not initialized.");
								return;
							}
						}
						else{
							System.out.println("Error(line "+lineNumber+"): variable doesn't exist."); //if not a digit nor an entry.
						}
						//operator logics:
						if(index[3].equals("+")){
							temp3=temp1+temp2;
							hashTable.put(index[0], temp3);
						}
						else if(index[3].equals("-")){
							temp3=temp1-temp2;
							hashTable.put(index[0], temp3);
						}
						else if(index[3].equals("*")){
							temp3=temp1*temp2;
							hashTable.put(index[0], temp3);
						}
						else if(index[3].equals("/")){
							temp3=temp1/temp2;
							hashTable.put(index[0], temp3);
						}
					}
				}
				else{
					System.out.println("Error(line "+lineNumber+" ): operator not valid.");
				}
			}
			else if(index[0].equals("print") || index[0].equals("Print")){
				if(x<3)
					System.out.println(hashTable.get(index[1]));
				else
					System.out.println("Syntax error.");
			}
			else{
				System.out.println("Error(line "+lineNumber+" ): The variable " + index[0]+ " is not declared."); //if first index not variable name: print error.
				return;
			}
		}
	}

	public static void main(String[] args) {

		HashMap <String, Object> hashTable = new HashMap<String, Object>();
		BufferedReader br = null;
		try {
			interpreter obj = new interpreter();
			String content="",str;
			int numOfLines;
			br = new BufferedReader(new FileReader("file.txt"));
			while((str=br.readLine())!=null){
				content=content.concat(str);
				content=content.concat("\n");
			}
			numOfLines = content.split("\n").length;
			String[] lines = new String[numOfLines];
			lines=content.split("\n");
				for(int i=0;i<numOfLines;i++){
					obj.executeCommand(lines[i],hashTable,i);
				}
		}catch (IOException e){
					e.printStackTrace();
			} finally {
					try {
						if (br != null)
							br.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
			}
	}
}

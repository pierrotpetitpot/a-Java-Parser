package assignment_3;



/*
 * This program works if the latex files are in the same directory as the workspace. 
 * Also, the new created files are created inside the workspace.
 * 
 * 
 * 
 * */


import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;
import java.io.BufferedReader;




/**
 * @author Pierre Tran 40007601
 *
 */
public class BibCreator {
	
	
	
	/**
	 * @param array: this is the array of Latex files to be sorted
	 */
	public static void sorting(File[] array) {	//sorts the folder of files from 1 to 10 
		Arrays.sort(array, new Comparator<File>() {
		    @Override
		    public int compare(File f1, File f2) {
		        String name1 = f1.getName();
		        String name2 = f2.getName();
		        Integer num1 = Integer.valueOf(name1.substring(5,name1.indexOf(".")));
		        Integer num2 = Integer.valueOf(name2.substring(5,name2.indexOf(".")));
		        return num1.compareTo(num2);
		    }
		});
		
	}
	
	/**
	 * @param array: array of any files that user wants to read 
	 */
	public static void readingFile(File[] array) throws FileInvalidException {		//reading the content of the files in folder
		Scanner sc = null;	
		String s1;
		try
		{
			for(int i = 0 ; i<array.length; i++) {
				if(array[i]==null) {
					continue;
				}
				if(!(array[i].exists())){
					throw new FileInvalidException("Could not open input file "+array[i]+ " for reading. Please check if file exists! Program will terminate after closing any opened files.");
					//System.out.print("Could not open input file "+array[i]+ " for reading. Please check if file exists! Program will terminate after closing any opened files.");
					
				}
				System.out.println("\n------------------------------------------------File: " + array[i] +"--------------------------------------------------------\n");
				sc = new Scanner(new FileInputStream(array[i]));
				while(sc.hasNextLine()) {
					s1 = sc.nextLine();
					System.out.println(s1);
				}
				sc.close();
			}
		}
		catch(FileNotFoundException e) 
		{							   
			System.out.println(e.getMessage());	
			 System.exit(0);
		}
		
	}
	
	/**
	 * @param array: takes the array of Latex files and creates 3 files for each file 
	 * @return: array of newly created files 
	 */
	public static ArrayList<File> creatingFile(File[] array) { //creating 3 files (IEEE, ACM, NJ) for each file in the folder and stores all files in "files" 

		ArrayList<File> anArray = new ArrayList<File>();
		File temp = null;
		PrintWriter pw1 = null;
		PrintWriter pw2 = null;
		PrintWriter pw3 = null;
		

		try {
			
			for(int i = 0; i < array.length; i++) {
			pw1 = new PrintWriter(new FileOutputStream("IEEE"+(i+1)+".json"));
			temp = new File("IEEE"+(i+1)+".json");
			anArray.add(temp);
			
			pw2 = new PrintWriter(new FileOutputStream("ACM"+(i+1)+".json"));
			temp = new File("ACM"+(i+1)+".json");
			anArray.add(temp);

			pw3 = new PrintWriter(new FileOutputStream("NJ"+(i+1)+".json"));
			temp = new File("NJ"+(i+1)+".json");
			anArray.add(temp);


			pw1.close();
			pw2.close();
			pw3.close();

			}
		}
		catch(FileNotFoundException e) {
			 e.printStackTrace();	
			 System.exit(0);
		}
		
		return anArray;
	
	}

	/**
	 * @param array : array of Latex files to be validated
	 * @param anArray : if invalid file found, delete the corresponding files in the array of newly created files
	 */
	public static void processFilesForValidation(File[] array, File[] anArray) { //validating the files in the folder, if invalid delete the corresponding files in "files"

		ArrayList<Integer> invalids = new ArrayList<Integer>();
		Scanner sc = null;
		String s1;
		String s2 = "{}";
		try
		{
			for(int i = 0 ; i<array.length; i++) {
				sc = new Scanner(new FileInputStream(array[i]));
				while(sc.hasNextLine()) {
					s1 = sc.nextLine();
					if(s1.contains(s2)) {
						String[] s3 = s1.split(" ");
						s3[0]= s3[0].substring(0,s3[0].indexOf("="));
						System.out.println(array[i]+" is invalid, because "+s3[0]+" is empty. Will delete the corresponding j.son files");
						invalids.add(i);
						array[i] = null;
						break;
					}
				}
				sc.close();
			}
			//System.out.println(invalids); for deubbing purpose, checking if the invalids are correct 
			for(int i = 0; i<invalids.size(); i++) {
				int delete = invalids.get(i)*3+2;
				anArray[delete].delete();
				anArray[delete] =null;
				
				anArray[delete-1].delete();
				anArray[delete-1]=null;
				
				anArray[delete-2].delete();
				anArray[delete-2]=null;
				}
		}
		
		catch(FileNotFoundException e) 
		{							   
			System.out.println(e.getMessage());	
			 System.exit(0);
		}
		
	}
	
	/**
	 * @param arrayList : takes the Arraylist of informations such as authors, titles and etc 
	 * @return : arrayList of formatted IEEE strings 
	 */
	public static ArrayList<String> toStringIEEE(ArrayList<ArrayList<String>> arrayList) { //formatting the informations in IEEE format 
		ArrayList<String> temp = new ArrayList<String> ();
		for(int i = 0; i< arrayList.get(0).size(); i++) {
		String s = arrayList.get(0).get(i)+". \""+ arrayList.get(1).get(i)+"\", " + arrayList.get(2).get(i) +", vol "+arrayList.get(4).get(i)+", no."+
				   arrayList.get(3).get(i)+", p. " +arrayList.get(5).get(i)+", "+arrayList.get(6).get(i)+" " + arrayList.get(7).get(i)+".";
		temp.add(s);
		}
		return temp;
	}
	
	/**
	 * @param arrayList : arraylist of arraylist of strings of all the infos such as authors, titles, and etc 
	 * @return arraylist of ACM formated strings 
	 */
	public static ArrayList<String> toStringACM(ArrayList<ArrayList<String>> arrayList){  //formatting the informations in ACM format
		ArrayList<String> temp = new ArrayList<String> ();
		for(int i = 0; i< arrayList.get(0).size(); i++) {
		String s = arrayList.get(8).get(i)+" et al. "+ arrayList.get(7).get(i)+". " + arrayList.get(1).get(i) +". "+arrayList.get(2).get(i)+". "+
				   arrayList.get(4).get(i)+", " +arrayList.get(3).get(i)+" ("+arrayList.get(7).get(i)+"), " + arrayList.get(5).get(i)+". DOI: https://doi.org/.";
		temp.add(s);
		}
		return temp;
	}
	
	/**
	 * @param arrayList arraylist of arraylist of strings of infos such as authors and titles 
	 * @return arraylist of NJ formatted info strings 
	 */
	public static ArrayList<String> toStringNJ(ArrayList<ArrayList<String>> arrayList) { //formatting the informations in NJ format 
		ArrayList<String> temp = new ArrayList<String> ();
		for(int i = 0; i< arrayList.get(0).size(); i++) {
		String s = arrayList.get(0).get(i)+". "+ arrayList.get(1).get(i)+". " + arrayList.get(2).get(i) +". "+arrayList.get(4).get(i)+
				", " +arrayList.get(5).get(i) +"("+ arrayList.get(7).get(i)+").";
		temp.add(s);
		}
		return temp;
	}
	
	/**
	 * @param array array of all the valid latex files
	 * @return all the infos such as authors, titles..infos.get(0) would be the arraylist of all the authors 
	 */
	public static ArrayList<ArrayList<String>> parsingFile(File[] array) {		//finding all the informations from the files in folder 

		
		Scanner sc = null;
		String s1;
		
		String author="";
		ArrayList<String> authors = new ArrayList<String>();

		String title="";
		ArrayList<String> titles = new ArrayList<String>();

		String journal="";
		ArrayList<String> journals = new ArrayList<String>();

		String volume="";
		ArrayList<String> volumes = new ArrayList<String>();

		String number="";
		ArrayList<String> numbers = new ArrayList<String>();

		String pages="";
		ArrayList<String> pagess = new ArrayList<String>();

		String month="";
		ArrayList<String> months= new ArrayList<String>();

		String year="";
		ArrayList<String> years = new ArrayList<String>();
		
		String firstAuthor = "";
		ArrayList<String> firstAuthors = new ArrayList<String>();

	
		try
		{
			for(int i = 0 ; i<array.length; i++) {
				if(array[i] ==null) {
					continue;
				}
				//System.out.println("------------------------------------File: " + array[i] +"------------------------------------------"); for debugging purposes 

				sc = new Scanner(new FileInputStream(array[i]));
				while(sc.hasNextLine()) {
					
					s1 = sc.nextLine();
					
					if (s1.contains("author=")) {
						author = s1.substring(s1.indexOf("{")+1, s1.indexOf("}"));
						//System.out.println(author);
						authors.add(author);
						String[] split = author.split(" ");
						
						if(split[0].contains(".") && split[1].contains(".")) {
							firstAuthor = split[0]+" "+split[1]+" "+split[2];
							firstAuthors.add(firstAuthor);
						}
						else if(split[0].contains(".")) {
							
							 firstAuthor = split[0]+" "+split[1];
							 firstAuthors.add(firstAuthor);
						}
						else {
						firstAuthor = split[0];
						firstAuthors.add(split[0]);
						}
						
					}
					

					else if(s1.contains("title=")) {
						title = s1.substring(s1.indexOf("{")+1, s1.indexOf("}"));
						//System.out.println(title);
						titles.add(title);
					}
					
					else if(s1.contains("journal=")) {
						journal = s1.substring(s1.indexOf("{")+1, s1.indexOf("}"));
						//System.out.println(journal);
						journals.add(journal);
					}
					
					else if(s1.contains("volume=")) {
						volume = s1.substring(s1.indexOf("{")+1, s1.indexOf("}"));
						//System.out.println(volume);
						volumes.add(volume);
					}
					
					else if(s1.contains("number=")) {
						number = s1.substring(s1.indexOf("{")+1, s1.indexOf("}"));
						//System.out.println(number);
						numbers.add(number);
					}
					else if(s1.contains("pages=")) {
						pages = s1.substring(s1.indexOf("{")+1, s1.indexOf("}"));
						//System.out.println(pages);
						pagess.add(pages);
					}
					else if(s1.contains("month=")) {
						month = s1.substring(s1.indexOf("{")+1, s1.indexOf("}"));
						//System.out.println(month);
						months.add(month);
					}
					else if(s1.contains("year=")) {
						year = s1.substring(s1.indexOf("{")+1, s1.indexOf("}"));
						//System.out.println(year);
						years.add(year);
					}
					
					//String s = toStringIEEE(author,title, journal,  volume,  number, pages, month, year);
					//System.out.println(author);
					//s = null;
				}
				
			}
		}
		catch(FileNotFoundException e) 
		{							   
			System.out.println(e.getMessage());	
			 System.exit(0);
		}
		ArrayList<ArrayList<String>> infos = new ArrayList<ArrayList<String>>();
		infos.add(authors);
		infos.add(titles);
		infos.add(journals);
		infos.add(numbers);
		infos.add(volumes);
		infos.add(pagess);
		infos.add(months);
		infos.add(years);
		infos.add(firstAuthors);
		return infos;
		
	}
	
	/**
	 * @param array array of valid Latex files
	 * @return arrayList of number of articles for each file. first element would be the number of articles in the first latex file 
	 */
	public static ArrayList<Integer> nbOfArticles(File[] array){		//finding the number of articles in each file in folder 
		Scanner sc = null;	
		String s1;
		int article =0;
		ArrayList<Integer> articles = new ArrayList<Integer>();
		
		try
		{
			for(int i = 0 ; i<array.length; i++) {
				if(array[i]==null) {
					articles.add(null);
					continue;
				}
				sc = new Scanner(new FileInputStream(array[i]));
				while(sc.hasNextLine()) {
					s1 = sc.nextLine();
					if(s1.contains("@ARTICLE")) {
						article++;
					}
				}
				articles.add(article);
				article=0;
				sc.close();
			}
		}
		catch(FileNotFoundException e) 
		{							   
			System.out.println(e.getMessage());	
			 System.exit(0);
		}
		return articles;
		
	}
	
	/**
	 * @param strings all the infos of every file mixed together...
	 * @param integers arraylist of number of articles for each file 
	 * @return well defined set of strings of infos for each file 
	 */
	public static ArrayList<String> corresponding(ArrayList<String> strings, ArrayList<Integer> integers) {  //in the informations found, find which information is in which file in folder 
		int current =0;
		ArrayList<String> anArrayList = new ArrayList<String>();
		
		for(int i = 0; i < integers.size(); i++){
			if(integers.get(i)==null) {
				continue;
			}
			for(int j = 0; j < (integers.get(i)); j++) {
				anArrayList.add(strings.get(j+current));

			}
			current +=integers.get(i);
			anArrayList.add(null);
			
		}
		return anArrayList;
	}
	
	/**
	 * @param strings strings that are to be transferred into the files 
	 * @param files files that will be written 
	 * @param option option depending if the file to be written is IEEE,ACM or NJ
	 */
	public static void writingFile(ArrayList<String> strings, File[] files,int option) {		//writing to the files in "files"
		int j = 0;
		PrintWriter pw = null;
		try
        {
			if(option == 1) {
			for(int i = 0; i < files.length; i=i+3) {
				if(files[i]==null) {
					continue;
				}
            pw = new PrintWriter(new FileOutputStream(files[i]));
           // pw.println(strings.get(0));
           while(strings.get(j)!=null) {
           	pw.println(strings.get(j));
          	j++;
           	}
            j++;
            pw.close();
        }
        }
			if(option==2) {
				for(int i = 2; i < files.length; i=i+3) {
					if(files[i]==null) {
						continue;
					}
	            pw = new PrintWriter(new FileOutputStream(files[i]));
	           // pw.println(strings.get(0));
	           while(strings.get(j)!=null) {
	           	pw.println(strings.get(j));
	          	j++;
	           	}
	            j++;
	            pw.close();
	        }
			}
			if(option==3) {
				int current =0;
				for(int i = 1; i < files.length; i=i+3) {
					if(files[i]==null) {
						continue;
					}
	            pw = new PrintWriter(new FileOutputStream(files[i]));
	           // pw.println(strings.get(0));
	           while(strings.get(j)!=null) {
	           	pw.println("["+(current+1) +"]      "+strings.get(j));
	          	j++;
	          	current++;
	           	}
	           current=0;
	            j++; //skip over the null element 
	            pw.close();
	        }
			}
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
		
	}
	
	
	/**
	 * @param args just the main function 
	 * @throws FileInvalidException custom error handling
	 */
	public static void main(String[] args) throws FileInvalidException 
	
		{
			Scanner input = new Scanner(System.in);
			try {
			File dir = new File("latex files"); //importing folder called "latex files"
			
			File[] folder = dir.listFiles(); //putting files from folder in an array of files 
			ArrayList<File> temp;
			
			sorting(folder);		//sort the files in the folder 
			readingFile(folder);	//read the files from the folder 
			temp = creatingFile(folder); 
			File [] files = temp.toArray(new File[temp.size()]); //"files" is an array which contains all the JSON files created 
			System.out.println("Creating files....");
			readingFile(files); //checking if the files have been created.. they are and they're all empty at this point 
			System.out.println("\n\n\n");
			processFilesForValidation(folder,files);   //checking which file in folder is invalid, then deleting the JSON files corresponding 
										//example: if latex3 is invalid, then IEEE3 ACM3 and NJ3 are deleted 
			
			ArrayList<ArrayList<String>> infos = parsingFile(folder); //storing all the informations from the files in folder, e.g infos.get(0) contains the 
																	//ArrayList of authors and infos.get(1) contains the ArrayList of titles 
			
			ArrayList<Integer> articles = new ArrayList<Integer>();
			articles = nbOfArticles(folder);	//finding the number of articles in each valid files, stores the result in ArrayList of integer 
			
			
			ArrayList<String> IEEE = new ArrayList<String>();
			IEEE = toStringIEEE(infos);			//formatting the information into IEEE format, contains IEEE strings of all the files..but we do not know which one belongs to which file 
			ArrayList<String> IEEEclassified = new ArrayList<String>();
			IEEEclassified = corresponding(IEEE, articles);	//we now know which string belongs to which file, each file is delimited by "null" 
			writingFile(IEEEclassified, files,1);	//writing to IEEE formated strings to IEEEi.JSON files 
			
			
			ArrayList<String> NJ = new ArrayList<String>();	
			NJ = toStringNJ(infos); //Formating the infos into NJ format 
			ArrayList<String> NJclassified = new ArrayList<String>(); 
			NJclassified = corresponding(NJ, articles);	//NJ strings are delimited by null so we know which string belongs in which file
			writingFile(NJclassified, files,2); //writing to the correspond NJi.JSON files 
			//readingFile(files);		//checking if NJi.JSON files were written 
			
			ArrayList<String> ACM = new ArrayList<String>();
			ACM = toStringACM(infos); //formating the infos into ACM format 
			ArrayList<String> ACMclassified = new ArrayList<String>();
			ACMclassified = corresponding(ACM, articles);	//ACM strings are delimited by null so we know which string belongs in which file  
			writingFile(ACMclassified, files,3); 	//writing to the ACM corresponding files 
			
			}
			catch(NullPointerException e) 
			{							   
				System.out.println("Cannot find the latex files.You must put folder: latex files in workspace. Program will terminate.");
				System.out.println(e.getMessage());	
				 System.exit(0);
			}
			
			
			
			
			System.out.println("What file do you want to read?");
			String newFileName = input.next();
			BufferedReader br = null;
			String currentLine = "";
			
			try {
				br = new BufferedReader(new FileReader(newFileName));
				 while ((currentLine=br.readLine())!= null) {
				        System.out.println(currentLine);
				    }
				 br.close();
			}
			catch(FileNotFoundException e) 
			{							   
				System.out.println("Problem opening files. Cannot find file\n");
				System.out.println("Please enter valid file name. This is your last chance.\n");
				
				System.out.println("What file do you want to read?");
				 newFileName = input.next();
				try {
					br = new BufferedReader(new FileReader(newFileName));
					 while ((currentLine=br.readLine())!= null) {
					        System.out.println(currentLine);
					    }
					 br.close();
				}
				catch(FileNotFoundException e2) {
					System.out.println("Problem opening files. Cannot find file");
					System.out.println("Program will terminate");
					System.exit(0);
				}
				catch(IOException e2)
				{
					System.out.println("Error: An error has occurred while reading from the " + newFileName + " file. ");
					System.out.println("Program will terminate.");
					System.exit(0);		
				}
				
				
			}
			catch(IOException e)
			{
				System.out.println("Error: An error has occurred while reading from the " + newFileName + " file. ");
				System.out.println("Program will terminate.");
				System.exit(0);		
			}
			
		}
	

}

package com.idocs.readFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.idocs.readFile.domain.Movie;
import com.idocs.readFile.domain.Person;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	
        List<Movie> totalMovies = new ArrayList<Movie>();
        String outputFileName = args[args.length-1];
        BufferedWriter writer = null;
        try{
        
       
        
            //create an output file
            File logFile = new File(outputFileName);

            // This will output the full path where the file will be written to...
            System.out.println(logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));
            
        for(int i = 0; i < args.length-1; i++) {
        
    		String fileName = args[i];
            System.out.println(fileName);
            
            String personName = args[i];

            personName = personName.substring(personName.lastIndexOf("\\") + 1);
            personName = personName.substring(0, personName.indexOf(".txt"));

            System.out.println(personName);
            String input = "^(\\d+)/(\\d+)/(\\d+) (.*) (\\d+)min (\\d+)min (\\S+)$";
            FileInputStream fstream = new FileInputStream(fileName);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            try {
                File f = new File(fileName);
                Scanner sc = new Scanner(f);

                List<Person> people = new ArrayList<Person>();
                Person p = new Person(personName);
                List<Movie> movies = new ArrayList<Movie>();


                while(sc.hasNextLine()){
                	

                    String line = sc.nextLine();
                    String[] details = line.split(":");
                    String date = details[0];
                    String name = details[1];
                    String lenghtStr = details[2].replace("min", "");
                    String watchedStr = details[3].replace("min", "");

                    int length = Integer.parseInt(lenghtStr);
                    int watched = Integer.parseInt(watchedStr);
                    String genre = details[4];
                    
                    Movie movie = new Movie(date, name, length, watched, genre);
                   movies.add(movie);
                   totalMovies.add(movie);
                }
                
                p.setMovies(movies);
                people.add(p);
               

                
             Collections.sort(totalMovies);
             for (Person person:people){
                 writeOutputFile(person,totalMovies,outputFileName,writer);

             }
             
            
            } catch (FileNotFoundException e) {         
                e.printStackTrace();
            }
        }
        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }
    		
    }


	public static  void writeOutputFile(Person person,List<Movie> movies,String outputFileName, BufferedWriter writer ){
		try{
			writer.write("************");
			writer.newLine();
			writer.write(person.getName());
			writer.newLine();
			writer.newLine();
			
			writer.newLine();
             for ( Movie movie : movies){
            	 writer.write(movie.toString());
				 writer.newLine();
             }
            
             List <String> averages=calcMoviesPercentage(person.getMovies());
             for ( String avegare : averages){
            	 writer.write(avegare);
            	 writer.newLine();
             }
             writer.newLine();
             writer.newLine();
             writer.write("************");
     		writer.newLine();
     		
		} catch (IOException e) {
			e.printStackTrace();
		}
     
		
	}
	
	
	
	public  static List<String> calcMoviesPercentage(List<Movie> movies){
		
		List<String> percentageList = new ArrayList<>();
		List<Movie> favouriteMovies = new ArrayList<>();
		int totalMovies = movies.size();
		int totalPercentage = 0;
		for (Movie movie : movies){
			int movieLength = movie.getLenght();
			int movieWatched = movie.getMinutesWatched();
			
			int moviePercentage = (movieWatched * 100)/movieLength;
			
			String moviePercentageStr = "For movie with title " +movie.getTitle()+" average percentage is "+moviePercentage +"%";
			percentageList.add(moviePercentageStr);
			System.out.println(moviePercentageStr);
			totalPercentage +=moviePercentage;
			if (moviePercentage>60){
				movie.setPercentage(moviePercentage);
				favouriteMovies.add(movie);
			}

			 
		}
		
		Map<String, Integer> duplicates = new HashMap<String, Integer>();
		 
	      for (Movie str : favouriteMovies) {
	         if (duplicates.containsKey(str.getGenre())) {
	            duplicates.put(str.getGenre(), duplicates.get(str.getGenre()) + 1);
	         } else {
	            duplicates.put(str.getGenre(), 1);
	         }
	      }
	 
	      for (Map.Entry<String, Integer> entry : duplicates.entrySet()) {
	         System.out.println(entry.getKey() + " = " + entry.getValue());
	      }
	      
	      Map.Entry<String, Integer> maxEntry = null;

	      for (Map.Entry<String, Integer> entry : duplicates.entrySet())
	      {
	          if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
	          {
	              maxEntry = entry;
	              System.out.println("Max Entry :"+maxEntry);
	          }
	      }
		 int overallPercentage = totalPercentage / totalMovies;
		 String overallPercentageStr = "The average percentage for all movies is :"+ overallPercentage+"%";
		 percentageList.add(overallPercentageStr);
		 String favouriteGenre = maxEntry.getKey() ;
		 percentageList.add("The favourite genre is :"+favouriteGenre);
		return percentageList;
	}

}


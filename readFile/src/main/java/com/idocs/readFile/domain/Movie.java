package com.idocs.readFile.domain;

public class Movie implements Comparable<Movie> {
	
	private String watchDate;
	
	private String title;
	
	private int lenght;
	
	private int minutesWatched;
	
	private String genre;
	
	private int percentage;
	


	public Movie(String watchDate, String title, int lenght, int minutesWatched, String genre) {
		super();
		this.watchDate = watchDate;
		this.title = title;
		this.lenght = lenght;
		this.minutesWatched = minutesWatched;
		this.genre = genre;
	}

	public String getWatchDate() {
		return watchDate;
	}

	public void setWatchDate(String watchDate) {
		this.watchDate = watchDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

	public int getLenght() {
		return lenght;
	}

	public void setLenght(int lenght) {
		this.lenght = lenght;
	}

	public int getMinutesWatched() {
		return minutesWatched;
	}

	public void setMinutesWatched(int minutesWatched) {
		this.minutesWatched = minutesWatched;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	
	
	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	@Override
	public int compareTo(Movie o) {
	    int result = (this.getMinutesWatched() < ((Movie) o).getMinutesWatched() ? -1 : (this.getMinutesWatched() == ((Movie) o).getMinutesWatched() ? 0 : 1));
	      
	        return  result;
	 }


	
	@Override
	public String toString(){ 
		  return watchDate+" "+title+" "+lenght+" "+minutesWatched+" "+genre;  
		 } 
}

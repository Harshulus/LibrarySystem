import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Book{
    String title;
    String author;
    String genre;
    String serialNumber;
    boolean rented;
    private Member currentRenter;
    public static ArrayList<Book> bookCollection = new ArrayList<Book>();
    public ArrayList<Member> renterHis = new ArrayList<>();
    

    //Constructor
    public Book(String title, String author, String genre, String serialNumber){
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.serialNumber = serialNumber;
        boolean rented = false;
    }

    //Methods
    public String getAuthor(){
        return this.author;
    }
    public String getGenre(){
        return this.genre;
    }

    public String getSerialNumber(){
        return this.serialNumber;
    }

    public String getTitle(){
        return this.title;
    }

    public String longString(){
        String longS = (serialNumber + ": " + title + " (" + author + ", " + genre + ")\n");
        boolean rent = isRented();

        //If rented return longString with Current Renter, else return short string of book.
        if (rent == true){
            return longS + "Rented by: " + getCurrentRenter().getMemberNumber() + ".";
        }
        else{
            return longS + "Currently available.";
        }
    }

    public String shortString(){
        String shortS = title + " (" + author + ")";
        return shortS;
    }

    public static Book readBook(String filename, String serialNumber){

        //Retrieves the book from the given file based on its serial number.
        //Putting in try and catch block for exception.
        boolean bookExist = false;

        if (serialNumber == null || filename == null){
            return null;
        }
        else{
            try{
                File f = new File(filename);
                Scanner scan = new Scanner(f);

                while (scan.hasNextLine()){
                    String line = scan.nextLine();
                    String[] obj = line.split(",");

                    //Checking and storing to variables if there is a match
                    for (int i =0; i < obj.length ; i++){
                        if (obj[i].equals(serialNumber)){
                            bookExist = true;
                            if(bookExist == true){
                                Book b = new Book(obj[i + 1], obj[i + 2], obj[i + 3], obj[i]);
                                return b;
                            }
                        }
                    }
                }
            }
            catch(FileNotFoundException e){
                return null;
            }

        }
        return null;
        
    }

    public static ArrayList<Book> readBookCollection(String filename){

        //Reads in the collection of books from the given file
        ArrayList<Book> bookCollection = new ArrayList<Book>();

        if (filename == null){
            return null;
        }
        else{
            try{
                File f = new File(filename);
                Scanner scan = new Scanner(f);
                String line = scan.nextLine();

                while (scan.hasNextLine()){
                    line = scan.nextLine();
                    String[] obj = line.split(",");
                   
                    Book b = new Book(obj[1], obj[2], obj[3], obj[0]);
                    bookCollection.add(b);
                    
                }
            }
            catch(FileNotFoundException e){
                return null;
            }
        }

        return bookCollection;
    }
    
    public static void saveBookCollection(String filename, Collection<Book> books){

        //Save the collection of books to the given file.
        if (filename == null || books == null){
            return;
        }
        else{
            
            try{
                File f = new File(filename);
                PrintWriter pw = new PrintWriter(f);
                pw.println("serialNumber,title,author,genre");
   
                for (Book elem : books){
                    pw.println(elem.getSerialNumber()+","+elem.getTitle()+","+elem.getAuthor()+","+elem.getGenre());
                }
                pw.close();
            }
            catch(FileNotFoundException e){
                return;
            }
        }
       
    }

    public static ArrayList<Book> filterAuthor(List<Book> books, String author){

        ArrayList<Book> matchingAuthors = new ArrayList<Book>();

        //Defensive Programming
        if(books == null){
            return null;
        }
        else if(author == null){
            return null;
        }

        //Checking for matches
        else{
            for (Book book : books){
                String authorName = book.getAuthor();
                if (authorName.equals(author)){
                    matchingAuthors.add(book);
                } 
            }
        }

        //If no matches ,return null.
        if(matchingAuthors == null){
            return null;
        }
        else{

            //Return the matches sorted by serial number.
            matchingAuthors.sort((x,y) -> x.getSerialNumber().compareTo(y.getSerialNumber()));
            return matchingAuthors;
        }
    }

    public static ArrayList<Book> filterGenre(List<Book> books, String genre){

        ArrayList<Book> matchingGenre = new ArrayList<Book>();

        //Defensive Programming
        if(books == null){
            return null;
        }
        else if(genre == null){
            return null;
        }

        //Checking for matches
        else{
            for(Book book : books){
                String genreType = book.getGenre();

                if(genreType.equals(genre)){
                    matchingGenre.add(book);
                } 
            }
        }

        //If no matches ,return null.
        if(matchingGenre == null){
            return null;
        }
        else{
            //Return the matches sorted by serial number.
            matchingGenre.sort((x,y) -> x.getSerialNumber().compareTo(y.getSerialNumber()));
            return matchingGenre;
        }
    }

    public boolean isRented(){

        if(getCurrentRenter() != null){
            rented = true;
        }
        return rented;
    }

    public boolean relinquish(Member member){
        
        if(member == null){
            return false;
        }
        else if(getCurrentRenter() != member){
            return false;
        }
        else{
            renterHis.add(getCurrentRenter());
            setCurrentRenter(null);
            rented = false;
            return true; 
        }
    }

    public boolean rent(Member member){

        if(member == null){
            return false;
        }
        else if(rented == true){
            return false;
        }
        else{
            setCurrentRenter(member); 
            rented = true;
            return true; 
        }
    }

    public void setCurrentRenter(Member member){
        currentRenter = member;
    }

    public Member getCurrentRenter(){
        return currentRenter;
    }

    public ArrayList<Member> renterHistory(){
        
        if(renterHis == null){
            return null;
        }
        else{
            return renterHis;
        }
    }
    
}

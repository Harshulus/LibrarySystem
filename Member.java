import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Member{
    String name;
    String memberNumber;
    public ArrayList<Book> booksRentHis = new ArrayList<>();
    public ArrayList<Book> booksRenting = new ArrayList<>();
    
    
    //Constructor
    public Member(String name, String memberNumber){
        this.name = name;
        this.memberNumber = memberNumber;
    }

    //Methods
    public String getMemberNumber(){
        return memberNumber;
    }

    public String getName(){
        return name;
    }

    public boolean relinquish(Book book){

        if(book == null){
            return false;
        }

        else if(book.getCurrentRenter() == null){
            return false;
        }

        else{
            booksRentHis.add(book);
            book.setCurrentRenter(null);
            //Add to rental history
            return true;

        } 
    }

    public void relinquishAll(){

        if(booksRenting == null){
            return;
        }

        else{

            for(Book elem : booksRenting){
                
                if(elem != null){

                    relinquish(elem);
                    booksRenting.remove(elem);
                }

                else{
                    return;
                }
                
            }
        }
    }

    public boolean rent(Book book){
        
        if(book == null){
            return false;
        }

        else if(book.isRented() == true){
            return false;
        }
        
        else{

            book.setCurrentRenter(this);
            booksRenting.add(book);
            return true;
        }
    }

    public static ArrayList <Book> commonBooks(Member[] members){

        if (members == null){
            return null;
        }

        else{

            //Checking for null members in Member[]
            for (Member m : members){
                if (m == null) {
                    return null;
                }
            }
        }

        ArrayList<Book> combinedHis = new ArrayList<>();
        ArrayList<Book> commonList = new ArrayList<>();
        
        for (Member m : members) {

            //Copying list of each member history
            ArrayList<Book> memHis = new ArrayList<>();
            for (Book b : m.history()){
                memHis.add(b);
            }

            //Removing duplicates from memHis
            for(Book b : m.history() ){
                if (Collections.frequency(m.history(), b) > 1){
                    for(int i=0 ; i < Collections.frequency(m.history(), b)-1 ; i++) {
                        memHis.remove(b);
                    }
                }
            }

            //Adding new filtered list to combinedHis list
            for (Book b: memHis){
                combinedHis.add(b);
            }
        }

        //Checking for duplicates in combinedHis
        for (Book b : combinedHis){

            if (Collections.frequency(combinedHis, b) > 1) {
                commonList.add(b);
            }
        }

        //Sorting according to serial number
        commonList.sort((x,y) -> x.getSerialNumber().compareTo(y.getSerialNumber()));
        return commonList;
    }

    public ArrayList<Book> history(){

        if(booksRentHis == null){
            return null;
        }

        else{
            return booksRentHis;
        }
    }

    public ArrayList<Book> renting(){

        if(booksRenting == null){
            return null;
        }
        
        else{
            return booksRenting;
        }

    }
}


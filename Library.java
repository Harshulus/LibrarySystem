import java.util.*;
import java.io.*;
public class Library{

    //Constructor
    //public library()
    public static String HELP_STRING = HELP_STRING = "EXIT ends the library process\nCOMMANDS outputs this help string\n\nLIST ALL [LONG] outputs either the short or long string for all books\nLIST AVAILABLE [LONG] outputs either the short of long string for all available books\nNUMBER COPIES outputs the number of copies of each book\nLIST GENRES outputs the name of every genre in the system\nLIST AUTHORS outputs the name of every author in the system\n\nGENRE <genre> outputs the short string of every book with the specified genre\nAUTHOR <author> outputs the short string of every book by the specified author\n\nBOOK <serialNumber> [LONG] outputs either the short or long string for the specified book\nBOOK HISTORY <serialNumber> outputs the rental history of the specified book\n\nMEMBER <memberNumber> outputs the information of the specified member\nMEMBER BOOKS <memberNumber> outputs the books currently rented by the specified member\nMEMBER HISTORY <memberNumber> outputs the rental history of the specified member\n\nRENT <memberNumber> <serialNumber> loans out the specified book to the given member\nRELINQUISH <memberNumber> <serialNumber> returns the specified book from the member\nRELINQUISH ALL <memberNumber> returns all books rented by the specified member\n\nADD MEMBER <name> adds a member to the system\nADD BOOK <filename> <serialNumber> adds a book to the system\n\nADD COLLECTION <filename> adds a collection of books to the system\nSAVE COLLECTION <filename> saves the system to a csv file\n\nCOMMON <memberNumber1> <memberNumber2> ... outputs the common books in members\' history";

    ArrayList<Book> bookCollection = new ArrayList<>();
    ArrayList<Member> membersList = new ArrayList<>();
    Integer memberNumber = 100000;
    

    public Library(){
    }
    public void getAllBooks(boolean fullString){
        //No books in system
        if(bookCollection.size() == 0){
            System.out.print("No books in system.\n");
            System.out.println();
            return;
        }
        else{
            //Checking for dups
            Set<Book> set = new HashSet<>(bookCollection);
            bookCollection.clear();
            bookCollection.addAll(set);

            for(int i =0; i < bookCollection.size()-1 ; i++){
                        Book b = bookCollection.get(i);
                        String bSerialNum = b.getSerialNumber();
                        Book c = bookCollection.get(i+1);
                        String cSerialNum = c.getSerialNumber();
                        if(bSerialNum.equals(cSerialNum)){
                            bookCollection.remove(i);
                        }
            }

            //Sorting by Serial Number
            bookCollection.sort((x,y) -> x.getSerialNumber().compareTo(y.getSerialNumber()));
            if(fullString == true){
                for(Book b : bookCollection){
                    System.out.println(b.longString());
                    System.out.println();
                }
                return;
            }
            else{
                for(Book b : bookCollection){
                    System.out.println(b.shortString());
                }
                System.out.println();
                return;
            }
        }
        
    }
    
    public void getAvailableBooks(boolean fullString){
        if(bookCollection.size() == 0){
            System.out.println("No books in system.");
            System.out.println();
            return;
        }
        else{
            //If available add to list
            ArrayList<Book> available = new ArrayList<>();
            for(Book b : bookCollection){
                boolean rented = b.isRented();
                if(rented == false){
                    available.add(b);
                }
            }
            //No books available
            if(available.size() == 0){
                System.out.println("No books available.\n");
                return;
            }
            else{
                //Checking for dups
                Set<Book> set = new HashSet<>(available);
                available.clear();
                available.addAll(set);

                //Sorting by Serial Number
                available.sort((x,y) -> x.getSerialNumber().compareTo(y.getSerialNumber()));
                if(fullString == true){
                    for(Book b : available){
                        System.out.println(b.longString());
                        System.out.println();
                    }
                    return;
                }
                else{
                    for(Book b : available){
                        System.out.println(b.shortString());
                    }
                    System.out.println();
                    return;
                }
            }
        }
    }

    public void getCopies(){

        if(bookCollection.size() == 0){
            System.out.println("No books in system.\n");
            return;
        }

        Iterator iterator = bookCollection.iterator();

        int count=0;

        ArrayList<String> arr = new ArrayList<>();

         while(iterator.hasNext()){

           Book b=(Book)iterator.next();
           arr.add(b.shortString());
         }

         Collections.sort(arr);
         Object[] objs = arr.toArray();

         int noCops = 0;

         for(int i=0; i<objs.length; i++)
         {
             String s = (String)objs[i];

             for(int j=0;j<objs.length;j++)
             {
                 if(s!=null && (String)objs[j]!=null )
                 {
                   if(s.equalsIgnoreCase((String)objs[j]))
                   {
                       noCops+=1;
                       objs[j]=null;
                   }
                 }
             }

             if(s!=null)
             {
                 System.out.println(s+": "+noCops);
             }
         }
         System.out.println();
    }

    public void getGenres(){
        if(bookCollection.size() == 0){
            System.out.println("No books in system.\n");
            return;
        }
        else{
            ArrayList<String> genres = new ArrayList<>();
            for(Book elem : bookCollection){
                String g = elem.getGenre();
                genres.add(g);
            }
            if(genres.size() == 0){
                System.out.println("No books in system.\n");
                return;
            }
            else{
                //Checking for dups
                Set<String> set = new HashSet<>(genres);
                genres.clear();
                genres.addAll(set);
            
                //Sort in alphabetical
                Collections.sort(genres, String.CASE_INSENSITIVE_ORDER);
                for(String g : genres){
                    System.out.println(g);
                    System.out.println();
                }
                return;
            }
        }
    }
    public void getAuthors(){
        if(bookCollection.size() == 0){
            System.out.println("No books in system.\n");
            return;
        }
        else{
            ArrayList<String> authors = new ArrayList<>();
            for(Book elem : bookCollection){
                String g = elem.getAuthor();
                authors.add(g);
            }
            if(authors.size() == 0){
                System.out.println("No books in system.\n");
                return;
            }
            else{
                //Checking for dups
                Set<String> set = new HashSet<>(authors);
                authors.clear();
                authors.addAll(set);

                //Sort in alphabetical
                Collections.sort(authors, String.CASE_INSENSITIVE_ORDER);
                for(String g : authors){
                    System.out.println(g);
                    System.out.println();
                }
                return;
            }
        }
    }
    public void getBooksByGenre(String genre){
        if(genre == null){
            return;
        }
        else if(bookCollection.size() == 0){
            System.out.println("No books in system.\n");
            return;
        }
        else{
            ArrayList<Book> booksByGenre = Book.filterGenre(bookCollection, genre);
            //No matches
            if(booksByGenre.size() == 0){
                System.out.println("No books with genre " + genre + ".\n");
                return;
            }
            else{
                //Checking for dups
                Set<Book> set = new HashSet<>(booksByGenre);
                booksByGenre.clear();
                booksByGenre.addAll(set);

                //Sorting by Serial Number
                booksByGenre.sort((x,y) -> x.getSerialNumber().compareTo(y.getSerialNumber()));
                for(Book b : booksByGenre){
                    System.out.println(b.shortString());
                }  
                System.out.println();
                return; 
            }
        }
    }
    public void getBooksByAuthor(String author){
        if(author == null){
            return;
        }
        else if(bookCollection.size() == 0){
            System.out.println("No books in system.\n");
            return;
        }
        else{
            ArrayList<Book> booksByAuthor = Book.filterAuthor(bookCollection, author);
            //No matches
            if(booksByAuthor.size() == 0){
                System.out.println("No books by " + author + ".\n");
                return;
            }
            else{
                //Checking for dups
                Set<Book> set = new HashSet<>(booksByAuthor);
                booksByAuthor.clear();
                booksByAuthor.addAll(set);

                //Sorting by Serial Number
                booksByAuthor.sort((x,y) -> x.getSerialNumber().compareTo(y.getSerialNumber()));
                for(Book b : booksByAuthor){
                    System.out.println(b.shortString());
                }  
                System.out.println();
                return; 
            }
        }
    }
    public void getBook(String serialNumber, boolean fillString){
        if(serialNumber == null){
            System.out.println("No such book in system.\n");
            return;
        }
        else{
            if(bookCollection.size() == 0){
                System.out.println("No books in system.\n");
                return;
            }
            else{
                for(Book b : bookCollection){
                    String serialNum = b.getSerialNumber();
                    if(serialNum.equals(serialNumber)){
                        if(fillString == true){
                            System.out.println(b.longString());
                            System.out.println();
                            return;
                        }
                        else{
                            System.out.println(b.shortString());
                            System.out.println();
                            return;
                        }
                    }
                }
                System.out.println("No such book in system.\n");
                return;
            }
        }
    }
    public void bookHistory(String serialNumber){
        if(serialNumber == null){
            System.out.println("No such book in system.\n");
            return;
        }
        // else if(bookCollection.size() == 0){
        //     System.out.println("No such book in system.\n");
        //     return;
        // }
        else{
            for(Book b : bookCollection){
                String serialNum = b.getSerialNumber();
                if(serialNum.equals(serialNumber)){
                    ArrayList<Member> rentedBy = b.renterHistory();
                    if(rentedBy.size() == 0){
                        System.out.println("No rental history.\n");
                    }
                    else{
                        for(Member m : rentedBy){
                            System.out.println(m + "\n");
                        }
                        return;
                    }
                }
                else{
                    System.out.println("No such book in system.\n");
                    return;
                }
            }
            
        }
    }
    public void addBook(String bookFile, String serialNumber){
        if(bookFile == null){
            System.out.println("No such file.\n");
            return;
        }
        else if(serialNumber == null){
            System.out.println("No such book in file.\n");
            return;
        }
        // else if(bookCollection.size() == 0){
        //     System.out.println("No such file.\n");
        //     return;
        // }
        else{
            File check = new File(bookFile);
            if(check.exists()){
                ArrayList<String> match = new ArrayList<>();
                try{
                    File f = new File(bookFile);
                    Scanner scan = new Scanner(f);
                    while(scan.hasNextLine()){
                        String line = scan.nextLine();
                        String[] str = line.split(",");
                        if(str[0].equals(serialNumber)){
                            match.add(serialNumber);
                        }
                    }
                }
                catch(FileNotFoundException e){
                    System.out.println("No such file.\n");
                    return;
                }
                if(match.size() == 0){
                    System.out.println("No such book in file.\n");
                    return;
                }
                else{
                    Book book = Book.readBook(bookFile, serialNumber);
                    if(!bookCollection.contains(book)){
                        bookCollection.add(book);
                        System.out.println("Successfully added: " + book.shortString() + ".\n");
                        return;
                    }
                    else{
                        System.out.println("Book already exists in system.\n");
                        return;
                    }
                }
            }
            else{
                System.out.println("No such file.\n");
                return;
            }
            
        }
    }
    public void rentBook(String memberNumber, String serialNumber){
        //Loans out a book to a member within the system.

        //Empty argument.
        if(memberNumber == null){

            System.out.println("No members in system.\n");
            return;
        }

        //Empty argument.
        else if(serialNumber == null){

            System.out.println("No such book in system.\n");
            return;
        }

        //No members in system.
        else if(membersList.size() == 0){

            System.out.println("No members in system.\n");
            return;
        }

        //No books in system.
        else if(bookCollection.size() == 0){

            System.out.println("No books in system.\n");
            return;
        }

        else{
            int count = 0;
            for(Member m : membersList){
                String num = m.getMemberNumber();

                if(num.equals(memberNumber)){

                    count += 1;
                    boolean match = false;

                    for(Book b : bookCollection){

                        String serialNum = b.getSerialNumber();

                        if(serialNum.equals(serialNumber)){

                            match = true;
                            boolean rented = b.isRented();

                            //Checking if book is rented.
                            if(rented == true){

                                System.out.println("Book is currently unavailable.\n");
                                return;
                            }

                            else{

                                m.rent(b);
                                System.out.println("Success.\n");
                                return;
                            }
                        }
                    }

                    if(match == false){
                        
                        System.out.println("No such book in system.\n");
                        return;
                    } 
                }
            }

            if(count == 0){

                System.out.println("No such member in system.\n");
                return;
            }
        }
    }

    public void relinquishBook(String memberNumber, String serialNumber){

        if(memberNumber == null){

            System.out.println("No such member in system.\n");
            return;
        }

        else if(serialNumber == null){

            System.out.println("No such book in system.\n");
            return;
        }

        else if(membersList.size() == 0){

            System.out.println("No members in system.\n");
            return;
        }

        else if(bookCollection.size() == 0){

            System.out.println("No books in system.\n");
            return;
        }

        else{

            int count = 0;
            for(Member m : membersList){
                String num = m.getMemberNumber();

                if(num.equals(memberNumber)){
                    count += 1;
                    boolean match = false;

                    for(Book b : bookCollection){
                        String serialNum = b.getSerialNumber();

                        if(serialNum.equals(serialNumber)){
                            match = true;
                            Member renter = b.getCurrentRenter();
                            //String numRenter = renter.getMemberNumber();

                            if(renter.equals(m)){
                                m.relinquish(b);
                                System.out.println("Success.\n");
                                return;
                            }
                            else{
                                System.out.println("Unable to return book.\n");
                                return;
                            }
                        }
                    }

                    if(match == false){
                        System.out.println("No such book in system.\n");
                        return;
                    }
                }
            }

            if(count == 0){
                System.out.println("No such member in system.\n");
                return;
            } 
        }
    }
    public void relinquishAll(String memberNumber){
        if(memberNumber == null){
            System.out.println("No such member in system.\n");
            return;
        }
        else if(membersList.size() == 0){
            System.out.println("No members in system.\n");
            return;
        }
        else{
            for(Member m : membersList){
                String num = m.getMemberNumber();
                if(num.equals(memberNumber)){
                    m.relinquishAll();
                    System.out.println("Success.\n");
                    return;
                }
            }
            System.out.println("No such member in system.\n");
            return;
        }

    }
    public void getMember(String memberNumber){
        if(memberNumber == null){
            System.out.println("No such member in system.\n");
            return;
        }
        else if(membersList.size() == 0){
            System.out.println("No members in system.\n");
            return;
        }
        else{
            for(Member m : membersList){
                String num = m.getMemberNumber();
                if(num.equals(memberNumber)){
                    System.out.println(num + ": " + m.getName() + "\n");
                    return;
                }
            }
            System.out.println("No such member in system.\n");
            return;
        }
    }
    public void getMemberBooks(String memberNumber){
        if(memberNumber == null){
            System.out.println("No such member in system.\n");
            return;
        }
        else if(membersList.size() == 0){
            System.out.println("No members in system.\n");
            return;
        }
        else{
            for(Member m : membersList){
                String num = m.getMemberNumber();
                if(num.equals(memberNumber)){
                    ArrayList<Book> renting = m.renting();
                    if(renting.size() == 0){
                        System.out.println("Member is not currently renting.\n");
                        return;
                    }
                    else{
                        //Checking for dups
                        Set<Book> set = new HashSet<>(renting);
                        renting.clear();
                        renting.addAll(set);

                        //Sort by Serial Number
                        renting.sort((x,y) -> x.getSerialNumber().compareTo(y.getSerialNumber()));
                        
                        for(Book b : renting){
                            System.out.println(b.shortString());
                        }
                        System.out.println();
                        return;
                    }
                }
            }
            System.out.println("No such member in system.\n");
            return;
        }
    }
    public void memberRentalHistory(String memberNumber){

        if(memberNumber == null){
            System.out.println("No such member in system.\n");
            return;
        }

        else if(membersList.size() == 0){
            System.out.println("No members in system.\n");
            return;
        }

        else{

            for(Member m : membersList){
                String num = m.getMemberNumber();

                if(num.equals(memberNumber)){
                    ArrayList<Book> rentingHis = m.history();

                    if(rentingHis.size() == 0){

                        System.out.println("No rental history for member.\n");
                        return;
                    }

                    else{

                        for(Book b : rentingHis){

                            System.out.println(b.shortString());
                        }
                        System.out.println();
                        return;
                    }
                }
            }
            System.out.println("No such member in system.\n");
            return;
        }
    }
    public void addMember(String name){
        if(name == null){
            return;
        }
        else{
            //memberNumber += count;
            String memNum = this.memberNumber.toString();
            Member m = new Member(name, memNum );
            this.memberNumber++;
            if(membersList.contains(m)){
                return;
            }
            else{
                membersList.add(m);
                System.out.println("Success.\n");
            }
        } 
    }

    public void saveCollection(String filename){
        if(filename == null){
            System.out.println("No such collection.\n");
            return;
        }
        else if(bookCollection.size() == 0){
            System.out.println("No books in system.\n");
            return;
        }
        else{
            File f = new File(filename);
            if(f.exists()){
                Book.saveBookCollection(filename, bookCollection);
                System.out.println("Success.\n");
                return;
            }
            else{
                System.out.println("No books have been added to the system.\n");
                return;
            }  
        }
    }

    public void addCollection(String filename){
        if(filename == null){
            System.out.println("No such collection.\n");
                return;
        }
        else{
            File f = new File(filename);
            if(f.exists()){
                ArrayList<Book> temp = Book.readBookCollection(filename);
                if(temp.size() == 0){
                    System.out.println("No such collection.\n");
                    return;
                }
                else{
                    //Checking for dups
                    // Set<Book> set = new HashSet<>(temp);
                    // temp.clear();
                    // temp.addAll(set);
                    temp.sort((x,y) -> x.getSerialNumber().compareTo(y.getSerialNumber()));
                    
                    for(int i =0; i < temp.size()-1 ; i++){
                        Book b = temp.get(i);
                        String bSerialNum = b.getSerialNumber();
                        Book c = temp.get(i+1);
                        String cSerialNum = c.getSerialNumber();
                        if(bSerialNum.equals(cSerialNum)){
                            temp.remove(i);
                        }
                    }

                    int num = 0;
                    for(Book b : temp){
                        bookCollection.add(b);
                        num ++;
                    }
                    
                    if(num == 0){
                        System.out.println("No books have been added to the system.\n");
                        return;
                    }
                    else{
                        System.out.println(num + " books successfully added.\n");
                        return;
                    }
                }

            }
            else{
                System.out.println("No such collection.\n");
                return;
            }
            
            
        }
        
    }
    public void common(String[] memberNumbers){
        if(membersList.size() == 0){
            System.out.println("No members in system.\n");
            return;
        }
        else if(bookCollection.size() == 0){
            System.out.println("No books in system.\n");
            return;
        }
        else{
            //Checking for unmatched members
            ArrayList<Member> providedMembers = new ArrayList<>();
            boolean member = false;
            for(Member m : membersList){
                for(String s : memberNumbers){
                    if(m.getMemberNumber().equals(s)){
                        providedMembers.add(m);
                        member = true;
                    }
                }
                if(member == false){
                    System.out.println("No such member in system.\n");
                    return;
                }
            }
            //Checking for dups
            for(int i =0; i < memberNumbers.length ; i++){
                for(int j = 0; j < memberNumbers.length ; j++){
                    if(i!=j && memberNumbers[i].equals(memberNumbers[j])){
                        System.out.println("Duplicate members provided.\n");
                        return;
                    }
                }
            }
            Member[] members = new Member[providedMembers.size()];
            providedMembers.toArray(members);
            ArrayList<Book> common = Member.commonBooks(members);

            if(common.size() == 0){
                System.out.println("No common books.\n");
                return;
            }
            else{
                for(Book b : common){
                    System.out.println(b.shortString() + "\n");
                }
                return;
            }
        }
    }
    public void run(){
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()){
            System.out.print("user: ");
            scan.useDelimiter("\r?\n");
            String input = scan.next();
            String[] arr = input.split(" ");

            if(arr[0].equals("COMMANDS")){
                System.out.println(HELP_STRING + "\n");
            }

            else if(arr[0].toUpperCase().equals("EXIT")){
            
                System.out.println("Ending Library process.");
                return;
            }

            else if(arr[0].toUpperCase().equals("LIST") && arr[1].toUpperCase().equals("ALL")){
                
                if(arr.length == 3){
                    
                    if(arr[2].toUpperCase().equals("LONG")){
                        getAllBooks(true);
                    }
                }

                else{
                    getAllBooks(false);
                }
            }

            else if(arr[0].toUpperCase().equals("LIST") && arr[1].toUpperCase().equals("AVAILABLE")){
                
                if(arr.length == 3){

                    if(arr[2].toUpperCase().equals("LONG")){
                        getAvailableBooks(true);
                    }
                }

                else{
                    getAvailableBooks(false);
                }
            }

            else if(arr[0].toUpperCase().equals("LIST") && arr[1].toUpperCase().equals("GENRES")){
                getGenres();
            }

            else if(arr[0].toUpperCase().equals("LIST") && arr[1].toUpperCase().equals("AUTHORS")){
                getAuthors();
            }

            else if(arr[0].toUpperCase().equals("GENRE")){
                //own test case
                if(arr.length < 2){
                    System.out.println("Provide genre type.\n");
                }
                else{
                    String genre = "";
                    for(int i =0 ; i < arr.length ; i++){
                        if(i == 1){
                            genre += arr[i];
                        }
                        else if(i > 1){
                            genre += " " + arr[i];
                        }
                    }
                    getBooksByGenre(genre);
                }
            }

            else if(arr[0].toUpperCase().equals("AUTHOR")){

                //Own test case
                if(arr.length < 2){
                    System.out.println("Provide author name.\n");
                }
                else{
                    String author = "";
                    for(int i =0 ; i < arr.length ; i++){
                        if(i == 1){
                            author += arr[i];
                        }
                        else if(i > 1){
                            author += " " + arr[i];
                        }
                    }
                    getBooksByAuthor(author);
                }
            }

            else if(arr[0].toUpperCase().equals("BOOK")){
                String serialNumber = arr[1];
                if(arr.length == 3){
                    if(arr[2].toUpperCase().equals("LONG")){
                        getBook(serialNumber, true);
                    }
                }
                else{
                    getBook(serialNumber, false);
                }
            }

            else if(arr[0].toUpperCase().equals("BOOK") && arr[1].toUpperCase().equals("HISTORY")){
                
                //Own test cases
                if(arr.length != 3){
                    System.out.println("Provide book serial number.\n");
                }

                else{
                    String s = arr[2];
                    bookHistory(s);
                }
            }

            else if(arr[0].toUpperCase().equals("ADD") && arr[1].toUpperCase().equals("BOOK")){

                //own test case 
                if(arr.length != 4){
                    System.out.println("Enter filename and serialNumber.\n");
                    
                }
                else{
                    String filename = arr[2];
                    String serialNumber = arr[3];
                    addBook(filename, serialNumber);
                }  
            }

            else if(arr[0].toUpperCase().equals("ADD") && arr[1].toUpperCase().equals("COLLECTION")){

                //Own testing
                if(arr.length < 3){
                    System.out.println("Provide filename.\n");
                }
                else{
                    String filename = arr[2];
                    addCollection(filename);
                } 
            }

            else if(arr[0].toUpperCase().equals("SAVE") && arr[1].toUpperCase().equals("COLLECTION")){

                //Own testing
                if(arr.length < 3){
                    System.out.println("Provide filename.\n");
                }
                else{
                    String filename = arr[2];
                    saveCollection(filename);
                } 
            }

            else if(arr[0].toUpperCase().equals("ADD") && arr[1].toUpperCase().equals("MEMBER")){

                //Own testing
                if(arr.length < 3){
                    System.out.println("Provide member name.\n");
                }
                else{
                    String name = "";
                    for(int i =0 ; i < arr.length ; i++){
                        if(i == 2){
                            name += arr[i];
                        }
                        else if(i > 2){
                            name += " " + arr[i];
                        }
                    }
                    addMember(name);
                }
            }
            else if(arr[0].toUpperCase().equals("RENT")){

                //Own testing
                if(arr.length != 3){
                    System.out.println("Please provide one member number and one book serialNumber.\n");
                }
                else{
                    String memNum = arr[1];
                    String bookNum = arr[2];
                    rentBook(memNum, bookNum);
                } 
            }
            
            

            else if(arr[0].toUpperCase().equals("MEMBER") && arr[1].toUpperCase().equals("BOOKS")){

                String memNum = arr[2];
                getMemberBooks(memNum);
            }

            else if(arr[0].toUpperCase().equals("MEMBER") && arr[1].toUpperCase().equals("HISTORY")){

                String memNum = arr[2];
                memberRentalHistory(memNum);
            
            }

            else if(arr[0].toUpperCase().equals("MEMBER")){

                //Own testing
                if(arr.length != 2){
                    System.out.println("Please provide one Member Number.\n");
                }

                else{
                    String memNum = arr[1];
                    getMember(memNum);
                }
            }

            else if(arr[0].toUpperCase().equals("RELINQUISH") && arr[1].toUpperCase().equals("ALL")){

                //Own testing
                if(arr.length !=  3){
                    System.out.println("Please provide one Member Number.\n");
                }

                else{
                    String memNum = arr[2];
                    relinquishAll(memNum);
                } 
            }

            else if(arr[0].toUpperCase().equals("RELINQUISH")){
                
                //Own testing
                if(arr.length != 3){
                    System.out.println("Please provide one Member Number and one Book Serial Number.\n");
                }

                else{
                    String memNum = arr[1];
                    String bookNum = arr[2];
                    relinquishBook(memNum, bookNum);
                } 
            }

            else if(arr[0].toUpperCase().equals("COMMON")){

                ArrayList<String> memNums = new ArrayList<>();
                for(int i = 1 ; i < arr.length ; i ++){
                    memNums.add(arr[i]);
                }
                String[] memNumbers = new String[memNums.size()];
                memNums.toArray(memNumbers);
                common(memNumbers);
            }

            else{
                System.out.println("Give the correct command.\n");
            }
        }
    }

    public static void main(String[] args){
        // main method of entire program 
        Library runUI = new Library();
        runUI.run();
    }
}

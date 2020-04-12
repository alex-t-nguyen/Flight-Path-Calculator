import java.util.Scanner;
import java.io.File;

public class Main {

    public static void main(String[] args) throws java.io.FileNotFoundException, java.io.IOException {
        
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        Scanner fileReader = null;
        File file = new File(filename);
        try
        {
            fileReader = new Scanner(file);
        }
        catch(java.io.FileNotFoundException exception)
        {
            scanner.close();
            throw exception;
        }

        while(fileReader.hasNextLine())
        {
            String line = fileReader.nextLine();
            // Break line into parts by using substrings with "|" as delimiter
            
        }
        fileReader.close();
        scanner.close();
    }
}
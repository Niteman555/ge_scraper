import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;


public class updatePrice
{
    public static String[] items(){ }
    public static String GE_API = "http://services.runescape.com/m="
            + "itemdb_rs/api/catalogue/detail.json?item=";

    public static void main(String[] args) throws IOException
    {
        // Check for input args.
        if (args.length < 1)
        {
            System.err.println("usage is updatePrice <itemID>");
            System.exit(-1);
        }
        
        for (String item : args)
        {
            // prepare scanner.
            String address = GE_API + item;
            URL pageLocation = new URL(address);
            Scanner in = new Scanner(pageLocation.openStream());
            
            // Read GE API data.
            String request = in.nextLine();
            in.close();
            
            // Parse data for name and price.
            String[] item_elements = request.split(",\"");
//            int i = 0;
//            for (String element : item_elements) System.out.println(i++ + ": "
//                    + element);
            String name = item_elements[5]; // name of file to update.
            String priceStr = item_elements[8];
                   
            // clean up name and price.
            name = name.split(":")[1].split("\"")[1] + ".txt";
            priceStr = priceStr.split(":")[1];
            priceStr = priceStr.substring(0,priceStr.length()-1).replace("\"","");
            
            System.out.println(name + "," + priceStr);
            
            // update price file, or create one if it doesn't already exist.
            FileWriter fp = new FileWriter(name,true);
            fp.write(priceStr + "\n");
            fp.close();
        }
    }
}

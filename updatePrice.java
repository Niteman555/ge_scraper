import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.SimpleDateFormat;


public class updatePrice
{
    // GE API address for downloading item info.
    public static String GE_API = "http://services.runescape.com/m="
            + "itemdb_rs/api/catalogue/detail.json?item=";
    
    // item id dictionary name
    public static final String ITEM_DICT = "item_dict.txt";
    
    // data file name
    public static final String DATA_LOG = "data.txt";
    
    // item name dictionary filename
    public static final String NAME_DICT = "item_names.txt";
    
    public static ArrayList<String> loadItems() throws IOException
    {
        ArrayList<String> result = new ArrayList<String>();
        File fp = new File(ITEM_DICT);
        Scanner reader = new Scanner(fp);
        while (reader.hasNext())
        {
            result.add(reader.nextLine());
        }
        return result;
    }

    public static void main(String[] args) throws IOException
    {        
        // Load previous items from item dictionary.
        ArrayList<String> items = loadItems();
        System.out.println(items);
        
        // Add command line args to items and update dictionary
        FileWriter dictUpdate = new FileWriter(ITEM_DICT,true);
        for (String arg : args)
        {
            if (!items.contains(arg))
            {
                items.add(arg);
                dictUpdate.write(arg + "\n");
            }
        }
        dictUpdate.close();
        
        /* Prepare file for writing and create timestamp. */
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmm").format(new java.util.Date());
        FileWriter fp = new FileWriter(DATA_LOG,true);
        fp.write(timeStamp);
        
        /* Update item names log */
        FileWriter fp2 = new FileWriter(NAME_DICT);
        
        /* Pull data from GE */
        for (String item : items)
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
            String name = item_elements[5]; // name of file to update.
            String priceStr = item_elements[8];
                   
            // clean up name and price.
            name = name.split(":")[1].split("\"")[1] + ".txt";
            // name = "logs/" + name;
            priceStr = priceStr.split(":")[1];
            priceStr = priceStr.substring(0,priceStr.length()-1).replace("\"","");
            
            System.out.println(name + "," + priceStr);
            
            // update price file, or create one if it doesn't already exist,
            // with timestamp and new price data.
            String timeStamp = new SimpleDateFormat("yyyy/MM/dd/HH:mm").format(new java.util.Date());
            fp.write("\t" + priceStr);
            fp2.write(name + "\n");
        }
        /* close all streams */
        fp.write("\n");
        fp.close();
        fp2.close();
    }
}

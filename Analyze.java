import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Arrays;

public class Analyze
{
    /* item name dictionary file name */
    public static final String NAME_DICT = "item_names.txt";

    /* data file name */
    public static final String DATA = "data.txt";

    public static void main(String[] args) throws IOException
    {
        /* First, import item names */
        ArrayList<String> names = new ArrayList<String>();
        File fp = new File(NAME_DICT);
        Scanner reader = new Scanner(fp);
        while (reader.hasNext())
        {
            names.add(reader.nextLine());
        }
        reader.close();

        // output the names
        System.out.println(names);

        /* Import data */

        // Import data as ArrayList of Strings.
        File fp2 = new File(DATA);
        Scanner reader2 = new Scanner(fp2);
        ArrayList<String> rawData = new ArrayList<String>();
        while (reader2.hasNext())
        {
            rawData.add(reader2.nextLine());
        }
        reader2.close();

        // Clean up the data
        ArrayList<String[]> interimData = new ArrayList<String[]>();
        for (String str : rawData)
        {
            interimData.add(str.split(","));
        }

        // Get Matrix size.
        int m = interimData.size();
        int n = interimData.get(0).length-1;
        System.out.println(m + "," + n);
        
        // Create new int Matrix and fill it with values.
        int[][] data = new int[m][n];
        for (int i = 0; i < m; i++)
        {
            for (int j = 0; j < n; j++)
            {
                data[i][j] = Integer.parseInt(interimData.get(i)[j+1]);
            }
        }

        // output the data.
        System.out.println(Arrays.deepToString(data));
    }

    /**
    * Calculates cross correlation matrix for data array at some lag.
    * @Return cross-correlation matrix
    */
    public static double[][] crossCorrelate(int[][] data, int lag)
    {
        int size = data[0].length;

        double[][] ccMatrix = new double[size][size];
        double[] avgs = new double[size];

        // First calculate the average value for each series.
        for(int j = 0; j < size; j++)
        {
            double sum;
            for (int i = 0; i < data.length; i++)
            {
                sum += data[i][j];
            }
            sum = sum / data.length;
            avgs[j] = sum;
        }

        
    }
}

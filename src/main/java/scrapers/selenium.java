package scrapers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Amanda on 06/03/2017.
 */
public class selenium {
    public static void getHTMLSourceFromURL(String url) {

        WebDriver driver = new ChromeDriver();
        driver.get(url);

        try {
            Thread.sleep(5000);   //the page gets loaded completely

            List<String> pageSource = new ArrayList<String>(Arrays.asList(driver.getPageSource().split("\n")));

            //String originalFile = null;

           // writeTextToFile(pageSource, originalFile);

            System.out.println(pageSource);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("quitting webdriver");
        driver.quit();
    }
    public static void main(String[] args){
        getHTMLSourceFromURL("http://www.boylesports.com/betting/football/premier-league/matches/?navigationid=23.1,183099.1,183100.1");
    }

    /**
     * creates file with fileName and writes the content
     *
     * @param content
     * @param fileName
     */
   /* private static void writeTextToFile(List<String> content, String fileName) {
        PrintWriter pw = null;
        String outputFolder = ".";
        File output = null;
        try {
            File dir = new File(outputFolder + '/' + "HTML Sources");
            if (!dir.exists()) {
                boolean success = dir.mkdirs();
                if (success == false) {
                    try {
                        throw new Exception(dir + " could not be created");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            output = new File(dir + "/" + fileName);
            if (!output.exists()) {
                try {
                    output.createNewFile();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            pw = new PrintWriter(new FileWriter(output, true));
            for (String line : content) {
                pw.print(line);
                pw.print("\n");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            pw.close();
        }

    }*/
}
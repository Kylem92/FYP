package scrapers;

import java.io.IOException;

/**
 * Created by t00174978 on 27/04/2017.
 */
public class Driver {
    public static void main(String[] args) {
        /*CoralScraper c = null;
        try {
            c = new CoralScraper("Arsenal");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //c.start();

*/
        WilliamHillScraper w = null;
        try {
            w = new WilliamHillScraper("Arsenal");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //w.start();

/*
        BoylesportsScraper b = null;
        try {
            b = new BoylesportsScraper("Arsenal");
        } catch (IOException e) {
            e.printStackTrace();
        }




        PaddyPowerScraper p = null;

        try {
            p = new PaddyPowerScraper("Arsenal");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //p.start();



        BetFairScraper f = null;
        try {
            f = new BetFairScraper("Arsenal");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //f.start();

*/
    }
}

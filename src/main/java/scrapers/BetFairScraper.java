package scrapers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Amanda on 20/02/2017.
 */
public class BetFairScraper {


    public BetFairScraper(String Team) {

        Element home = null;
        try {
            home = Jsoup.connect("http://www.betfair.com").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements next = home.select("a#SPORTSBOOK");
        String next1 = next.attr("href");
        System.out.println(next1);

        Element doc = null;
        try {
            doc = Jsoup.connect(next1).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements link = doc.select("a[data-galabel=Premier League]");
        System.out.println(link);

        String url = link.attr("href");
        System.out.println("This is the url " + url);
        Document doc1 = null;
        try {
            doc1 = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*

        Elements prem = doc1.select("a[title=English Premier League]");
        String textLink = prem.attr("href");
        System.out.println(textLink);
        Document docPrem = Jsoup.connect("http://www.betfair.com"+textLink).get();
*/

        Element events = doc1.select("a[data-event*="+Team+"]").get(1);
        String urlEventId = events.attr("href");

        String eventId = urlEventId.substring(urlEventId.length() - 8);

        System.out.println(eventId);

        //Elements odds = docPrem.select("span.ui-fraction-price && a[bseId*="+eventId+"] ");
        Elements odds = doc1.select("div[data-eventId="+eventId+"]");

        String display = html2text(String.valueOf(odds));
        System.out.println(display);



    }
        public static String html2text(String html) {
            return Jsoup.parse(html).text();
        }


        //Document doc2 = Jsoup.connect(urlPrem).get();
       // Elements linkA = doc.select("a[href]");
       // Elements linkZ = doc.select("a[href*=arsenal]");
       // System.out.println(linkA);
        //System.out.println(linkZ);*/

    }




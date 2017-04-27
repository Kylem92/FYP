package scrapers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Amanda on 25/04/2017.
 */
public class WilliamHillScraper {
    public WilliamHillScraper(String Team) {

        Element doc = null;
        try {
            doc = Jsoup.connect("http://sports.williamhill.com/bet/en-gb").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element link = doc.select("a[href*=English+Premier]").first();
        String textLink = link.attr("href");
        //System.out.println(link);

        Element doc1 = null;
        try {
            doc1 = Jsoup.connect(textLink).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(doc1);
        Elements links = doc1.select("a[href*="+Team+"]");
        String eventLink = links.attr("href");
        System.out.println(eventLink);

        Element doc2 = null;
        try {
            doc2 = Jsoup.connect(eventLink).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(doc2);

        //Elements spans = doc2.select("div.suspended");
        //Element test = doc2.getElementsByClass("suspended").first();

        Element win = doc2.select("div.eventprice").get(0);
        Element drw = doc2.select("div.eventprice").get(1);
        Element lose = doc2.select("div.eventprice").get(2);
        String w = win.text();
        String d = drw.text();
        String l = lose.text();
        System.out.println("To win "+w + " To draw " + d + " To lose " + l);



    }
    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

}

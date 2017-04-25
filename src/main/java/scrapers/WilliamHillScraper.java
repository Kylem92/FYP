package scrapers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Amanda on 25/04/2017.
 */
public class WilliamHillScraper {
    public static void main(String [] args) throws IOException {

        Element doc = Jsoup.connect("http://sports.williamhill.com/bet/en-gb").get();
        Element link = doc.select("a[href*=English+Premier]").first();
        String textLink = link.attr("href");
        //System.out.println(link);

        Element doc1 = Jsoup.connect(textLink).get();
        //System.out.println(doc1);
        Elements links = doc1.select("a[href*=Chelsea]");
        String eventLink = links.attr("href");
        System.out.println(eventLink);

        Element doc2 = Jsoup.connect(eventLink).get();
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

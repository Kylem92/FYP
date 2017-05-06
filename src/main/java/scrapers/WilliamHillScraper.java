package scrapers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Amanda on 25/04/2017.
 */
public class WilliamHillScraper implements Runnable {

    private String[] odds1 = new String[10];
    private String Team;
    private Thread t;

    public WilliamHillScraper(String Team) throws IOException {
        this.Team=Team;
        this.start();
    }

    public void run() {


        Element doc = null;
        try {
            doc = Jsoup.connect("http://sports.williamhill.com/bet/en-gb").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements link = doc.select("a[href*=football]");
        String textLink = link.attr("href");
        //System.out.println(textLink);

        Element doc1 = null;
        try {
            doc1 = Jsoup.connect(textLink).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(doc1);
        Elements links = doc1.select("a[href*=premier-league]");
        String match = links.attr("href");
        //System.out.println(match);

        Element doc2 = null;
        try {
            doc2 = Jsoup.connect(match).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(doc2);

        Elements team = doc2.select("a[href*="+Team+"]");
        String t = team.attr("href");
        //System.out.println(t);
        //Elements spans = doc2.select("div.suspended");
        //Element test = doc2.getElementsByClass("suspended").first();
        Element doc3 = null;
        try {
            doc3 = Jsoup.connect(t).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element win = doc3.select("div.eventprice").get(0);
        Element drw = doc3.select("div.eventprice").get(1);
        Element lose = doc3.select("div.eventprice").get(2);
        String w = win.text();
        String d = drw.text();
        String l = lose.text();
        System.out.println("william To win "+w + " To draw " + d + " To lose " + l);


        odds1[0] = w;
        odds1[1] = d;
        odds1[2] = l;

    }
    public void start () {

        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }

    public String[] getOdds() {
        return odds1;
    }

    public String getOddsString() {
        return "win: " + odds1[0] + "\ndraw: " + odds1[1] + "\nlose: " + odds1[2];
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }


}

package scrapers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Amanda on 20/02/2017.
 */
public class BetFairScraper implements Runnable{

    private String[] odds1 = new String[10];
    private String Team;
    private Thread t;

    public BetFairScraper(String Team) throws IOException {
        this.Team = Team;
        this.start();

    }

    public void run(){
        Element home = null;
        try {
            home = Jsoup.connect("http://www.betfair.com").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements next = home.select("a#SPORTSBOOK");
        String next1 = next.attr("href");
        //System.out.println(next1);

        Element doc = null;
        try {
            doc = Jsoup.connect(next1).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements link = doc.select("a[data-galabel=Premier League]");
        //System.out.println(link);

        String url = link.attr("href");
        //System.out.println("This is the url " + url);
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

        //System.out.println(eventId);

        //Elements odds = docPrem.select("span.ui-fraction-price && a[bseId*="+eventId+"] ");
        Elements odds = doc1.select("div[data-eventId="+eventId+"]");

        String display = html2text(String.valueOf(odds));
        String finOd = odds.select("ul.runner-list-selections").text();
        String homeTeam = odds.select("span.home-team-name").text();
        String awayTeam = odds.select("span.away-team-name").text();
        String date = odds.select("span.date").text();
        System.out.println("Betfair    "+date+"\n" + homeTeam+ " vs " + awayTeam + " " + finOd);



    }
    public void start () {

        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }

    public static String html2text(String html) {
            return Jsoup.parse(html).text();
        }


    public String[] getOdds() {
        return odds1;
    }

    public String getOddsString() {
        return "win: " + odds1[0] + "\ndraw: " + odds1[1] + "\nlose: " + odds1[2];
    }


    }




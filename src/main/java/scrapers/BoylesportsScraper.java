package scrapers;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Kyle on 13/02/2017.
 */
public class BoylesportsScraper implements Runnable{

    private String[] odds1 = new String[10];
    private String Team;
    private Thread t;

    public BoylesportsScraper(String Team) throws IOException {
        this.Team = Team;
        this.start();
    }

    public void run() {
        Element doc = null;
        try {
            doc = Jsoup.connect("http://www.boylesports.com").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements link = doc.select("a[href=http://www.boylesports.com/betting]");
        //System.out.println(link);
        String linkHref = link.attr("href");
        //Element doc1 = Jsoup.connect(linkHref+"/football").get();
        //System.out.println(linkHref+"/football");


        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        WebClient client1 = new WebClient(BrowserVersion.CHROME);
        client1.getOptions().setJavaScriptEnabled(true);
        client1.getOptions().setThrowExceptionOnScriptError(false);
        client1.getOptions().setThrowExceptionOnFailingStatusCode(false);
        client1.setCssErrorHandler(new SilentCssErrorHandler());
        client1.setJavaScriptTimeout(3000);


        HtmlPage page = null;
        try {
            page = client1.getPage(linkHref+"/football");
        } catch (IOException e) {
            e.printStackTrace();
        }
        client1.closeAllWindows();

        //System.out.println(page.asXml());

        DomElement premier = page.getElementById("PremierLeague1");
        String linkPrem = premier.getAttribute("href");
        //System.out.println(linkPrem + " short 20588"); //this gets me the link below



        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        WebClient client = new WebClient(BrowserVersion.CHROME);
        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setThrowExceptionOnScriptError(false);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);
        client.setCssErrorHandler(new SilentCssErrorHandler());
        client.setJavaScriptTimeout(3000);


        HtmlPage page1 = null;
        try {
            page1 = client.getPage(linkPrem);
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.closeAllWindows();

        //System.out.println(page1.asXml());

        Document doc2 = Jsoup.parse(String.valueOf(page1.asXml()));
        //System.out.println(doc2 + "  \n\n\n\nthis is doc2");
        Elements event = doc2.select("td.two-column"); // > span[title*="+Team+"]"
        Element match1 = event.select("span[title*="+Team+"]").get(0);
        //Elements odds = doc2.select("td.ten-column");
        //Elements link = doc.select("span[title*=Liverpool]");
        //System.out.print("This is odds:" + odds.text() + "\n\n");


        //looking for id number after moremarket=""
        //can use this to match the name inputted by user to the odds as they are contained in separate tags
        String line = String.valueOf(match1);
        int startIndex = line.indexOf("moremarket=");
        int endIndex = line.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = line.length();
        }
        String links = line.substring(startIndex, endIndex);
        String eventId = links.substring(11,20);
        //System.out.println(links);
        //System.out.println(eventId);


        //System.out.println(oddsa);
        //String match = doc2.select("a[href*="+eventId+"]").text();
        Elements finalOdds = doc2.select("span[onclick*="+eventId+"]");
        String win = finalOdds.select("span.red").get(0).text();
        String draw = finalOdds.select("span.red").get(1).text();
        String lose = finalOdds.select("span.red").get(2).text();

        System.out.println(match1.text());
        System.out.println("bS To win "+win + " To draw " + draw + " To lose " + lose);
        odds1[0] = win;
        odds1[1] = draw;
        odds1[2] = lose;
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


        //link below might be used if odds are easier get

       /*java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        WebClient client = new WebClient(BrowserVersion.CHROME);
        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setThrowExceptionOnScriptError(false);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);
        client.setCssErrorHandler(new SilentCssErrorHandler());
        client.setJavaScriptTimeout(3000);


        HtmlPage pages = null;
        try {
            pages = client.getPage("http://www.boylesports.com/betting/football/premier-league/matches/tottenham-v-arsenal/?navigationid=23.1,183099.1,183100.1&moremarket=8047123.1,16671.1");
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.closeAllWindows();

        System.out.println(pages.asXml());*/


        //look into current link and one before, see which is easier to access odds


}

package scrapers;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Amanda on 25/04/2017.
 */
public class CoralScraper implements Runnable{

    private String[] odds1 = new String[10];
    private String Team;
    private Thread t;

    public CoralScraper(String Team) throws IOException {
        this.Team = Team;
        this.start();

    }
    public void run(){
        Element doc = null;
        try {
            doc = Jsoup.connect("http://www.coral.co.uk").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements link = doc.select("a[href*=sports]");
        String textLink = link.attr("href");
        //System.out.println(textLink);

        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        WebClient client = new WebClient(BrowserVersion.CHROME);
        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setThrowExceptionOnScriptError(false);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);
        client.getOptions().setUseInsecureSSL(true);

        client.setCssErrorHandler(new SilentCssErrorHandler());
        client.setJavaScriptTimeout(3000);


        HtmlPage page = null;
        try {
            page = client.getPage(textLink);
        } catch (IOException e) {
            e.printStackTrace();
        }

        client.waitForBackgroundJavaScript(3000);
        //System.out.println(page.asXml());
        client.closeAllWindows();


        Document docs = Jsoup.parse(String.valueOf(page.asXml()));
        Elements links = docs.select("a[href*=england/premier-league]");
        //System.out.println(links);
        String premLink = links.attr("href");
        //System.out.println(premLink);

        Element prem = null;
        try {
            prem = Jsoup.connect(premLink).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements event = prem.select("a[href*="+Team+"]");
        String eventLink = event.attr("href");
        //System.out.println(eventLink);

        Document oddPage = null;
        try {
            oddPage = Jsoup.connect(eventLink).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* Elements odds = oddPage.select("tr.body-row");
        Elements duv = odds.select("div.bet-price");
        Document doc1 = Jsoup.parse(String.valueOf(odds));
        System.out.print(duv);*/



        Element win = oddPage.select("span.odds-fractional").get(267);
        Element drw = oddPage.select("span.odds-fractional").get(268);
        Element lose = oddPage.select("span.odds-fractional").get(269);
        String w = win.text();
        String d = drw.text();
        String l = lose.text();

        System.out.println("Coral To win "+w + " To draw " + d + " To lose " + l);
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
}

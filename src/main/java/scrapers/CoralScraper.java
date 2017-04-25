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
public class CoralScraper {

    public static void main(String [] args) throws IOException {
        Element doc = Jsoup.connect("http://www.coral.co.uk").get();
        Elements link = doc.select("a[href*=sports]");
        String textLink = link.attr("href");
        System.out.println(textLink);

        //Element doc1 = Jsoup.connect(textLink).get();
        //Elements links = doc1.select("a[href]");
        //String textLink = link.attr("href");
        //System.out.println(doc1);

        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        WebClient client = new WebClient(BrowserVersion.CHROME);
        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setThrowExceptionOnScriptError(false);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);
        client.getOptions().setUseInsecureSSL(true);

        client.setCssErrorHandler(new SilentCssErrorHandler());
        client.setJavaScriptTimeout(3000);



        HtmlPage page = client.getPage(textLink);

        client.waitForBackgroundJavaScript(5000);
        //System.out.println(page.asXml());
        client.closeAllWindows();


        Document docs = Jsoup.parse(String.valueOf(page.asXml()));
        Elements links = docs.select("a[href*=england/premier-league]");
        //System.out.println(links);
        String premLink = links.attr("href");
        System.out.println(premLink);

        Element prem = Jsoup.connect(premLink).get();
        Elements event = prem.select("a[href*=Arsenal]");
        String eventLink = event.attr("href");
        System.out.println(eventLink);

        Element oddPage = Jsoup.connect(eventLink).get();
        Elements odds = oddPage.select("span.odds-fractional");
        System.out.println(odds);


        Element win = oddPage.select("div.bet-price > span.odds-fractional").get(0);
        Element drw = oddPage.select("div.bet-price > span.odds-fractional").get(1);
        Element lose = oddPage.select("div.bet-price > span.odds-fractional").get(2);
        String w = win.text();
        String d = drw.text();
        String l = lose.text();
        System.out.println("To win "+w + " To draw " + d + " To lose " + l);
    }
}

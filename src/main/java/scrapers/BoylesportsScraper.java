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
public class BoylesportsScraper {


    public static void main (String[]args) throws IOException {
        Element doc = Jsoup.connect("http://www.boylesports.com").get();
        Elements link = doc.select("a[href=http://www.boylesports.com/betting]");
        System.out.println(link);
        String linkHref = link.attr("href");
        //Element doc1 = Jsoup.connect(linkHref+"/football").get();
        //System.out.println(doc1);


        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        WebClient client1 = new WebClient(BrowserVersion.CHROME);
        client1.getOptions().setJavaScriptEnabled(true);
        client1.getOptions().setThrowExceptionOnScriptError(false);
        client1.getOptions().setThrowExceptionOnFailingStatusCode(false);
        client1.setCssErrorHandler(new SilentCssErrorHandler());
        client1.setJavaScriptTimeout(3000);


        HtmlPage page = client1.getPage(linkHref+"/football");
        client1.closeAllWindows();

        //System.out.println(page.asXml());

        DomElement premier = page.getElementById("PremierLeague1");
        String linkPrem = premier.getAttribute("href");
        System.out.println(linkPrem); //this gets me the link below



        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        WebClient client = new WebClient(BrowserVersion.CHROME);
        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setThrowExceptionOnScriptError(false);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);
        client.setCssErrorHandler(new SilentCssErrorHandler());
        client.setJavaScriptTimeout(3000);


        HtmlPage page1 = client.getPage(linkPrem);
        client.closeAllWindows();

        //System.out.println(page.asXml());

        Document doc2 = Jsoup.parse(String.valueOf(page1.asXml()));
        //System.out.println(doc2);
        Elements event = doc2.select("td.two-column > span[title*=Liverpool]");
        Elements odds = doc2.select("td.ten-column");
        //Elements link = doc.select("span[title*=Liverpool]");
        System.out.print(event);


        //looking for id number after moremarket=""
        //can use this to match the name inputted by user to the odds as they are contained in separate tags
        String line = String.valueOf(event);


        int startIndex = line.indexOf("moremarket=");
        int endIndex = line.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = line.length();
        }
        String links = line.substring(startIndex, endIndex);
        System.out.println(links);
        String eventId = links.substring(11,20);
        System.out.println(eventId);

        Elements oddsa = doc.select("span[onclick*="+eventId+"]");
        System.out.println(oddsa);

    }


}

package scrapers;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Amanda on 20/02/2017.
 */
public class LadbrokesScraper {
    public static void main (String[]args) throws Exception {

        Element doc = Jsoup.connect("http://www.ladbrokes.com").get();
        Elements link = doc.select("a[href*=football]");
        String textLink = link.attr("href");
        System.out.println(textLink);

        //Element doc1 = Jsoup.connect(textLink).get();
        //System.out.println(doc1);





            java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
            WebClient client = new WebClient(BrowserVersion.CHROME);
            client.getOptions().setJavaScriptEnabled(true);
            client.getOptions().setThrowExceptionOnScriptError(false);
            client.getOptions().setThrowExceptionOnFailingStatusCode(false);
            client.getOptions().setUseInsecureSSL(true);

            client.setCssErrorHandler(new SilentCssErrorHandler());
            client.setJavaScriptTimeout(5000);



            HtmlPage page = client.getPage(textLink);

            client.waitForBackgroundJavaScript(5000);
            System.out.println(page.asText());
            client.closeAllWindows();

        }


        /*WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.setCssErrorHandler(new SilentCssErrorHandler());

        String url = "https://sports.ladbrokes.com/en-gb/betting/football/";
        System.out.println("accessing " + url);

        HtmlPage page = webClient.getPage(url);

        System.out.println("waiting for js");
        webClient.waitForBackgroundJavaScriptStartingBefore(200);
        webClient.waitForBackgroundJavaScript(20000);

        System.out.println(page.asXml());*/





        /*Element doc1 = Jsoup.connect(textLink).get();
        Elements link1 = doc1.select("link[href]");
        System.out.println(link1);*/



    }



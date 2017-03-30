package scrapers;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Amanda on 20/02/2017.
 */
public class Bet365Scraper {
    public static void main(String[] args) throws IOException, InterruptedException {

        /*Element doc = Jsoup.connect("http://www.bet365.com").get();
        Elements link = doc.select("a[href]");
        //System.out.println(link);

        //scrape first link
        Element doc1 = Jsoup.connect("https://www.bet365.com/?lng=1&cb=1032558600#/HO/").get();
        //System.out.println(doc1);
        Elements scripts = doc.select("script");
        //System.out.println(scripts);*/

        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
        webClient.setIncorrectnessListener(new IncorrectnessListener() {
            public void notify(String s, Object o) {

            }
        });
        webClient.setCssErrorHandler(new SilentCssErrorHandler());
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        WebRequest request = new WebRequest(new URL("https://paddypower.com"));
        HtmlPage page = webClient.getPage(request);

        int i = webClient.waitForBackgroundJavaScript(1000);

        while (i > 0)
        {

            i = webClient.waitForBackgroundJavaScript(1000);

            if (i == 0)
            {
                break;
            }
            synchronized (page)
            {
                System.out.println("wait");
                page.wait(500);
            }
            i--;
        }

        webClient.getAjaxController().processSynchron(page, request, false);

        System.out.println(page.asXml());

       /* WebClient webClient = new WebClient();//htmlunit
        webClient.getJavaScriptEngine();
        Page page = webClient.getPage("http://www.boylesports.com/betting");
        webClient.waitForBackgroundJavaScript(10000);
        WebResponse response = page.getWebResponse();
        String content = response.getContentAsString();
        System.out.println(content);*/

        //String url = "http://www.boylesports.com/betting";
        //WebClient webClient = new WebClient();
        //HtmlPage myPage = ((HtmlPage) webClient.getPage(url));
        //System.out.println(myPage);


        /*WebClient webClient = new WebClient();
        HtmlPage page = null;
        try {
            page = webClient.getPage("http://www.boylesports.com/betting");
        } catch (Exception e) {
            System.out.println("Get page error");
        }
        JavaScriptJobManager manager = page.getEnclosingWindow().getJobManager();
        while (manager.getJobCount() > 0) {
            Thread.sleep(1000);
        }
        System.out.println(page.asXml());*/

}


    }





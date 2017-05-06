package scrapers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kyle on 17/10/2016.
 */
public class PaddyPowerScraper implements Runnable{

    private String[] odds1 = new String[10];
    private String Team;
    private Thread t;

        public PaddyPowerScraper(String Team) throws IOException {
            this.Team= Team;

            this.start();
        }

        public void run() {

            Element doc = null;
            try {
                doc = Jsoup.connect("http://www.paddypower.com").timeout(1000).userAgent("Mozilla").get().getElementById("nav_quicklinks");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements link = doc.select("a[title*=Football Betting]");

            //System.out.println(link);
            String url = link.attr("href");
            //System.out.println("This is the url " + url);

            Document doc1 = null;
            try {
                doc1 = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements link2 = doc1.select("a[href*=premier-league]");
            String url2 = link2.attr("href");
            //System.out.println(url2);

            Document doc2 = null;
            try {
                doc2 = Jsoup.connect(url2).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println(doc2);

            ArrayList links = new ArrayList();
            String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+@#/%=~_()|]";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(String.valueOf(doc2));
            while (m.find()) {
                String urlStr = m.group();
                if (urlStr.contains(Team)) {
                    links.add(urlStr+"\n");
                }
            }
            //System.out.println(links);

            Document finalDoc = null;
            try {
                finalDoc = Jsoup.connect(String.valueOf(links.get(0))).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println(finalDoc);


            //Elements spans = doc2.select("span.odds-value:lt(2)");
            //Elements spans = doc2.select("span.sub_market_name:contains(Win-Draw-Win) > span.odds-value");
            Element spans = finalDoc.select("div.fb-odds-group.item").get(0);
            //Elements finOdd = spans.select("span.sub_market_name:contains(Win-Draw-Win)");
            //System.out.println(finOdd);
            String finalOdds = html2text(String.valueOf(spans));
            System.out.println("paddy   "+finalOdds);
        }

    public void start () {

        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }

           /*

           Document doc1 = Jsoup.connect(url).get();
            Elements link2 = doc1.select("a[title*=Football Team Pages]");
            String url2 = link2.attr("href");
            System.out.println(url2);

            Document doc2 = Jsoup.connect(url2).get();
            Elements linkTeam = doc2.select("a[href*=arsenal]");
            String url3 = linkTeam.attr("href");
            System.out.println(url3);

            Document doc3 = Jsoup.connect(url3).get();
            //System.out.println(doc3);
           // Elements spans = doc3.select("div.fb-odds-group.item");
            //Elements spans = doc3.select("span.odds-value");
            //System.out.println(spans);

            */

                    //Elements scripts = doc1.getElementsByTag("script");
                    // System.out.println(scripts);

            /*ArrayList links = new ArrayList();
            String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+@#/%=~_()|]";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(String.valueOf(scripts));
            while (m.find()) {
                String urlStr = m.group();
                if (urlStr.contains("Arsenal")) {
                    links.add(urlStr);
                }

            }*/

                    //System.out.println(links);

/*
            Document doc2 = Jsoup.connect(String.valueOf(links.get(0))).get();
            //System.out.println(doc2);


            //Elements spans = doc2.select("span.odds-value:lt(2)");
            //Elements spans = doc2.select("span.sub_market_name:contains(Win-Draw-Win) > span.odds-value");
            Elements spans = doc2.select("div.fb-odds-group.item");
            //Elements finOdd = spans.select("span.sub_market_name:contains(Win-Draw-Win)");
            //System.out.println(finOdd);
            String finalOdds = html2text(String.valueOf(spans));
            System.out.println(finalOdds);

            */






    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

    public String getTeam() throws IOException {
        System.out.println("Enter Team");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String team = in.readLine();
        return team;

    }

    public void scrapeUrl(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

    }

    public String[] getOdds() {
        return odds1;
    }

    public String getOddsString() {
        return "win: " + odds1[0] + "\ndraw: " + odds1[1] + "\nlose: " + odds1[2];
    }



}


package com.company;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static ArrayList<News> newsHolder = new ArrayList<>();
    static ArrayList<News> possibleNewsHolder = new ArrayList<>();
    static String headLine,link,description,imageSource;

    public static void main(String[] args) throws IOException {

        MyInstanciatingThread server = new MyInstanciatingThread();
        server.run();

        while(true){

            addisFortuneFetcher();
            cnnFetcher();
            theReporterEthiopiaFetcher();

            String header = WriterReader.readFile("src\\header.txt");
            String footer = WriterReader.readFile("src\\footer.txt");

            for (News news:possibleNewsHolder) {
                checkAndAddNews(news);
            }

            WriterReader.writeToFile("src\\Public\\created.html",header);
            for (News news:newsHolder) {
                String result = news.createNews();
                WriterReader.appendToFile("src\\Public\\created.html",result);
            }

            WriterReader.appendToFile("src\\Public\\created.html",footer);

            try {
                System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
                WebDriver newsOpener = new ChromeDriver();
                newsOpener.get("localhost:5000/created.html");
                Thread.sleep(120000);
                newsOpener.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop()));

        }

    }

    public static void theReporterEthiopiaFetcher(){

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //driver.get("file:///C:/Users/Milky/Desktop/Sources/News%20_%20The%20Reporter%20Ethiopia%20English.html");
        driver.get("http://www.thereporterethiopia.com/news");

        WebElement container = driver.findElement(By.id("page-main-content"));
        List<WebElement> items = container.findElements(By.className("item"));

        for (WebElement item:items) {
            //System.out.println(item.findElement(By.tagName("article")).findElement(By.tagName("a")).getText());
            //headLine = item.findElement(By.tagName("article")).findElement(By.tagName("a")).findElement(By.tagName("span")).getText();
            headLine = item.findElement(By.tagName("article")).findElement(By.className("post-title")).findElement(By.tagName("span")).getText();
            //System.out.println(item.findElement(By.tagName("article")).findElement(By.tagName("a")).getAttribute("href"));
            link = item.findElement(By.tagName("article")).findElement(By.tagName("a")).getAttribute("href");
            //System.out.println(item.findElement(By.tagName("article")).findElement(By.className("post-body")).getText());
            description = item.findElement(By.tagName("article")).findElement(By.className("post-body")).getText();
            try{
                //System.out.println(item.findElement(By.tagName("article")).findElement(By.tagName("img")).getAttribute("src"));
                imageSource = item.findElement(By.tagName("article")).findElement(By.tagName("img")).getAttribute("src");
                possibleNewsHolder.add(new News(headLine, link, description, imageSource));
            }catch(Exception e){
                possibleNewsHolder.add(new News(headLine,link,description));
                System.out.println("No image file set to default");
            }

        }

        driver.close();
    }

    public static void cnnFetcher(){

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //driver.get("file:///C:/Users/Milky/Desktop/Sources/Africa%20news%20-%20breaking%20news,%20video,%20headlines%20and%20opinion%20-%20CNN.html");
        driver.get("http://edition.cnn.com/africa");

        WebElement container1 = driver.findElement(By.id("africa-zone-1"));
        WebElement latestNewsContainer = container1.findElement(By.className("zn__column--idx-1"));
        List<WebElement> articles = latestNewsContainer.findElements(By.tagName("article"));

        for (WebElement article:articles) {
            //System.out.println(article.getText());
            headLine = article.getText();
            //System.out.println(article.findElement(By.tagName("a")).getAttribute("href"));
            link = article.findElement(By.tagName("a")).getAttribute("href");
            //System.out.println(article.findElement(By.tagName("img")).getAttribute("src"));
            imageSource = article.findElement(By.tagName("img")).getAttribute("src");
            possibleNewsHolder.add(new News(headLine, link, "NO Description provided", imageSource));
        }

        WebElement container2 = driver.findElement(By.id("africa-zone-2"));
        WebElement newAndBuzzContainer = container2.findElement(By.className("zn__column--idx-0"));
        List<WebElement> lists = newAndBuzzContainer.findElements(By.tagName("li"));

        for (WebElement list:lists) {
            //System.out.println(list.findElement(By.tagName("a")).getAttribute("href"));
            link = list.findElement(By.tagName("a")).getAttribute("href");
            //System.out.println(list.findElement(By.tagName("article")).getText());
            headLine = list.findElement(By.tagName("article")).getText();
            possibleNewsHolder.add(new News(headLine, link));
        }

        driver.close();
    }

    public static void addisFortuneFetcher(){

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //driver.get("file:///C:/Users/Milky/Desktop/Sources/Addisfortune-The%20Largest%20English%20Weekly%20in%20Ethiopia.html");
        driver.get("http://addisfortune.net/");
        driver.navigate().refresh();

        WebElement container = driver.findElement(By.className("span6")).findElement(By.className("row")).findElement(By.className("span6"));
        List<WebElement> links = container.findElements(By.tagName("a"));
        List<WebElement> images = container.findElements(By.tagName("img"));
        List<WebElement> rows = container.findElements(By.className("row"));

        List<String> newsLinks = new ArrayList<>();
        List<String> imgSrc = new ArrayList<>();
        List<String> headLineArr = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();

        for (WebElement image:images){
            imgSrc.add(image.getAttribute("src"));
        }
        for (WebElement row:rows) {
            descriptions.add(row.getText());
        }
        for (WebElement link:links) {
            headLineArr.add(link.getText());
            newsLinks.add(link.getAttribute("href"));
        }

        int i = imgSrc.size();
        for (int j = 0; j < i; j++){
            headLine = headLineArr.get(j);
            link = newsLinks.get(j);
            description = descriptions.get(j);
            imageSource = imgSrc.get(j);

            possibleNewsHolder.add(new News(headLine, link, description, imageSource));
        }

        driver.close();
    }

    public static void checkAndAddNews(News newNews){

        boolean exist = false;
        ArrayList<News> intermediateNewsHolder = new ArrayList<>();

        if(newsHolder.size() > 0){
            for (News previousNews:newsHolder) {
                if(previousNews.headLine.equals(newNews.headLine) || previousNews.description.equals(newNews.description)) {
                    exist = true;
                    break;
                }
            }
        }

        if(!exist && newsHolder.size() < 40){
            newsHolder.add(newNews);
        }else if(!exist && newsHolder.size() >= 40){
            newsHolder.remove(newsHolder.size() - 1);
            intermediateNewsHolder.add(newNews);
            intermediateNewsHolder.addAll(newsHolder);
            newsHolder = intermediateNewsHolder;
        }else if(exist){
            System.out.println("news already present");
        }
    }
}

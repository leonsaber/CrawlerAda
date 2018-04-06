package com.leonsaber;

import com.leonsaber.model.crawler.AbafruitCrawler;

public class App {

    public static void main(String[] x) throws Exception{
        AbafruitCrawler abafruitCrawler = new AbafruitCrawler("crawl", true);
        abafruitCrawler.start(4);
    }
}

package com.leonsaber.model.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.leonsaber.model.utils.MySQLUtils;

public class AbafruitCrawler extends BreadthCrawler{

    public AbafruitCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        /*start page*/
        this.addSeed("https://www.adafruit.com/categories");

        /*fetch url like http://news.hfut.edu.cn/show-xxxxxxhtml*/
        this.addRegex("https://www.adafruit.com/product/.*");
        /*do not fetch jpg|png|gif*/
        this.addRegex("-.*\\.(jpg|png|gif).*");
        /*do not fetch url contains #*/
        this.addRegex("-.*#.*");

        setThreads(100);
        getConf().setTopN(100);

//        setResumable(true);
    }

    public void visit(Page page, CrawlDatums next) {
        String url = page.url();
        /*if page is news page*/
        if (page.matchUrl("https://www.adafruit.com/product/.*")) {

            /*extract title and content of news by css selector*/
            String productName = page.select("meta[name = twitter:title]").attr("content");
            int productID = Integer.valueOf(page.selectText("div.product_id>span"));
            double productPrice = Double.parseDouble(page.selectText("span[itemprop = price]").substring(1));
            String inventory = page.select("meta[name = twitter:data2]").attr("content");
            int productQty;
            String productStatus;
            if(!inventory.equals("IN STOCK")) {
                if(inventory.equals("OUT OF STOCK")) {
                    productQty = 0;
                    productStatus = "OUT OF STOCK";
                } else {
                    String[] tempArr = inventory.split(" ");
                    productQty = Integer.valueOf(tempArr[0]);
                    productStatus = "IN STOCK";
                }
            }
            else {
                productQty = 100;
                productStatus = "IN STOCK";
            }

            System.out.println("URL: " + url);
            System.out.println("PRODUCT NAME: " + productName);
            System.out.println("PRODUCT ID: " + productID);
            System.out.println("PRICE: " + productPrice);
            System.out.println("QTY " + productQty);
            System.out.println("INVENTORY STATUS: " + productStatus);

            MySQLUtils mySQLUtils = new MySQLUtils();
            mySQLUtils.createProduct(url, productName, productID, productPrice, productStatus, productQty);
            /*If you want to add urls to crawl,add them to nextLink*/
            /*WebCollector automatically filters links that have been fetched before*/
            /*If autoParse is true and the link you add to nextLinks does not match the
              regex rules,the link will also been filtered.*/
            //next.add("http://xxxxxx.com");
        }
    }
}

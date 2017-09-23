/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translate_vietNamese_chinese.download_application;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author quang
 */
public class ScrapingThread extends Thread {

    private String className;
    private String pageName;
    private int maxPageNumber;
    private int currentPage;
    private int articleSize;
    private int position;
    public static volatile boolean stop;
    private boolean downloadDone;
    private CountDownLatch latch;

    public ScrapingThread(String className, String pageName) {
        this.className = className;
        this.currentPage = 1;
        this.articleSize = 0;
        this.position = 0;
        this.pageName = pageName;
        this.latch = new CountDownLatch(1);
        ScrapingThread.stop = false;
        this.downloadDone = false;
        this.maxPageNumber = 0;
    }

    public ScrapingThread(String className, String pageName, int currentPage, int position, int articleSize) {
        this.className = className;
        this.currentPage = currentPage;
        this.articleSize = articleSize;
        this.position = position;
        this.pageName = pageName;
        ScrapingThread.stop = false;
        this.latch = new CountDownLatch(1);
        this.downloadDone = false;
        this.maxPageNumber = 0;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public boolean getStateDownload() {
        return this.downloadDone;
    }

    public int getArticleSize() {
        return this.articleSize;
    }
    public int getPosition() {
        return this.position;
    }

    public String getPageName() {
        return this.pageName;
    }

    public String getClassName() {
        return this.className;
    }

    public int getDownloadPercent() {
        if (this.maxPageNumber == 0) {
            return 0;
        }
        return this.currentPage * 100 / this.maxPageNumber;
    }

    public  void requestStop() {
        try {
            stop = true;
            latch.await();
            System.out.println("Stopped");
        } catch (InterruptedException ex) {
            Logger.getLogger(ScrapingThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            // java create class instance from string

            Class<?> c = Class.forName(className);
            Constructor<?> ctor = c.getConstructor();
            Object object = ctor.newInstance();
            Method scrap = c.getDeclaredMethod("scrap", int.class, int.class);
            Method getArticleSize = c.getDeclaredMethod("getArticleSize");
            Method getPosition = c.getDeclaredMethod("getPosition");
            Method getMaxPageNumber = c.getDeclaredMethod("getMaxPageNumber");

            if (articleSize == 0) {
                Method init = c.getDeclaredMethod("init", String.class);
                init.invoke(object, pageName);
            } else {
                Method init = c.getDeclaredMethod("init", String.class, int.class);
                init.invoke(object, pageName, articleSize);
            }
            maxPageNumber = (int) getMaxPageNumber.invoke(object);
            System.out.println(maxPageNumber);
            int current=currentPage;
            for (int i = current;; i++) {
                if (i > maxPageNumber || maxPageNumber == -1) {
                    downloadDone = true;
                    break;
                }
                if(i == current){
                     scrap.invoke(object, i,this.position);
                }else{
                    scrap.invoke(object, i,0);
                }
                currentPage = i;
                if (stop == true) {
                    articleSize = (int) getArticleSize.invoke(object);
                    position=(int) getPosition.invoke(object);
                    latch.countDown();
                    System.out.println("manual Stop at " + currentPage + " " + position + " "+ articleSize);
                    break;
                }
            }
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(ScrapingThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        ScrapingThread t = new ScrapingThread("downloadapplication.VNExpress", "http://vnexpress.net/tin-tuc/thoi-su");
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(60000);
//                    System.out.println(".run()");
//
//                    t.requestStop();
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(ScrapingThread.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }).start();
//        t.start();
//        System.out.println(t.className + " " + t.currentPage);

    }
}

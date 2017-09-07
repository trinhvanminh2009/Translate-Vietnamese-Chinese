package translate_vietNamese_chinese.download_application;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.concurrent.CountDownLatch;


public class RequestStopThread extends Thread{

    private ScrapingThread th;
    private CountDownLatch latch;

    public RequestStopThread(ScrapingThread th,CountDownLatch latch) {
        this.th = th;
        this.latch=latch;
    }

    @Override
    public void run() {
        th.requestStop();
        latch.countDown();
        System.out.println("Thread " + th.getPageName() + "stop page: " + th.getPageName());
    }

}

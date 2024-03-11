package org.flimwip.design;

import org.flimwip.design.utility.Runnables.JobHistoryFetcher;

public class HistoryFetcherMain {

    public static void main(String[] args) {
        Thread t = new Thread(new JobHistoryFetcher());
        t.setDaemon(true);
        t.setPriority(2);
        t.start();

    }
}

package org.flimwip.design.utility.Runnables;

import javafx.concurrent.Task;
import org.flimwip.design.NetCon;
import org.flimwip.design.Views.Temp.BranchView;
import org.flimwip.design.Views.helpers.LogFile;

import java.io.*;
import java.nio.file.Files;
import java.util.concurrent.Semaphore;

public class Downloader implements Runnable{

    private LogFile lf;
    private NetCon connection;
    private String destination;
    private long length;

    private Semaphore semaphore;

    private BranchView bv;
    public Downloader(LogFile lf, NetCon connection, String destination, Semaphore semaphore, BranchView bv){
        this.lf = lf;
        this.connection = connection;
        this.destination = destination;
        this.semaphore = semaphore;
        this.bv = bv;
        this.bv.print_my_message(STR."Downloader started for \{this.lf.getId()}");
    }

    public File check_sub_dir(){
        File sub_one = new File(STR."\{destination}\\\{this.lf.get_nl_id()}");
        File sub_two = new File(STR."\{sub_one.getAbsolutePath()}\\\{this.lf.get_checkout_id()}");
        if(!sub_one.exists()){
            sub_one.mkdirs();
            this.bv.print_my_message(STR."Build directory \{sub_one.getAbsolutePath()}");
        }else{
            //System.out.println(STR."\{sub_one.getAbsolutePath()} existed");
        }

        if(!sub_two.exists()){
            sub_two.mkdirs();
           this.bv.print_my_message(STR."Build directory \{sub_two.getAbsolutePath()}");
        }else{
            //System.out.println(STR."\{sub_two.getAbsolutePath()} existed");
        }

        return sub_two;
    }

    @Override
    public void run() {

        lf.set_is_downloading();
        while(!semaphore.tryAcquire()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        this.bv.print_my_message(STR."Aquired Semaphore. Currently open Permits: \{semaphore.availablePermits()}");
        long downloadsize = 0;
        try {
            downloadsize = Files.size(new File(lf.getId()).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long downloaded = 0;
        File dest = check_sub_dir();
        try(FileOutputStream fos = new FileOutputStream(STR."\{dest.getAbsolutePath()}\\\{lf.get_log_file_name()}");
            FileInputStream fis = new FileInputStream(lf.getId())){

            byte[] buffer = new byte[1024];
            while((length = fis.read(buffer)) > 0){
                downloaded += length;
                fos.write(buffer);
                this.lf.push_percantage(((100/(double)downloadsize) * downloaded) / 100);
            }
            lf.set_downloaded();
            if(bv.downloaded_a_file()){
                connection.close_connection();
                System.out.println("Connection was closed");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        semaphore.release();
        this.bv.print_my_message(STR."Released Semaphore. Permits \{semaphore.availablePermits()}");

    }
}

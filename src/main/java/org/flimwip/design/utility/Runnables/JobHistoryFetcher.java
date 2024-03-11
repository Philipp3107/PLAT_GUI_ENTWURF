package org.flimwip.design.utility.Runnables;

import javafx.concurrent.Task;
import org.flimwip.design.TesterStart;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class JobHistoryFetcher extends Task<Void> {

    private TesterStart ts;

    public JobHistoryFetcher(TesterStart ts){
        this.ts = ts;
    }

    public JobHistoryFetcher(){
    }

    @Override
    protected Void call() throws Exception {
        System.out.println("Starting fetcher");
        File f = new File("L:\\POS-Systeme\\TeamPOS_INTERN\\02 Mitarbeiter\\Philipp Kotte\\PLAT_Files\\job-history.txt");
        long line_size = 0;
        try {
            line_size = Files.lines(Path.of(f.getAbsolutePath()), StandardCharsets.UTF_8).toList().size();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Line_size is : " + line_size);
        while(true){
            List<String> temp;
            try {
                temp = Files.lines(Path.of(f.getAbsolutePath()), StandardCharsets.UTF_8).toList();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            long lines = temp.size();
            if(line_size !=  lines){
                System.out.println("making view upadte");
                line_size = lines;
                ts.update_history();
            }
            String timeStamp = new SimpleDateFormat("dd.MM.yyyy;HH:mm:ss").format(Calendar.getInstance().getTime());
            System.out.println("Zeilen: " + lines + " " + timeStamp);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

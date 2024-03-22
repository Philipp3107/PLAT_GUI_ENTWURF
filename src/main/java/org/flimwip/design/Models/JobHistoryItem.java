package org.flimwip.design.Models;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.flimwip.design.Views.helpers.Spacer;

import java.util.Arrays;
import java.util.Objects;

public class JobHistoryItem extends VBox implements Comparable<JobHistoryItem>{

    //11.03.2024;11:20:25;Push to 107;107;RandomArtGenerator.txt;1;Philipp Kotte
    private String day, month, year, hour, minute, second, title, author;
    private String[] nl, files, checkouts;

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getSecond() {
        return second;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String[] getNl() {
        return nl;
    }

    public String[] getFiles() {
        return files;
    }

    public String[] getCheckouts() {
        return checkouts;
    }

    private Label author_label, time_label;

    private Font sub = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 10);
    public JobHistoryItem(String date, String time, String title, String nl, String files, String checkouts, String author){


        this.nl = split_string(nl);
        this.files = split_string(files);
        this.checkouts = split_string(checkouts);
        String[] temp_date = date.split("\\.");
        this.day = temp_date[0];
        this.month = temp_date[1];
        this.year = temp_date[2];

        String[] temp_time = time.split(":");
        this.hour = temp_time[0];
        this.minute = temp_time[1];
        this.second = temp_time[2];

        this.title = title;
        this.author = author;

        init();
    }

    private String[] split_string(String to_split){

        if(to_split.contains(",")){
            return to_split.split(",");
        }else{
            return new String[]{to_split};
        }
    }

    /**
     * Initializes the JobHistoryItem object by setting properties and adding child nodes.
     */
    private void init(){
        this.setSpacing(5);
        this.setPadding(new Insets(5));
        this.setStyle("-fx-border-color: #555555");

        StringBuilder nl_s = new StringBuilder("[" + nl[0]);
        for(int i = 1; i < this.nl.length; i++){
            nl_s.append(", ").append(nl[i]);
        }
        nl_s.append("]");

        Label l = new Label( this.title +" -> " + nl_s);
        l.setFont(sub);
        HBox info = new HBox();

        this.author_label = new Label(this.author);
        this.author_label.setTextFill(Color.valueOf("#AAAAAA"));
        this.time_label = new Label(this.hour + ":" + this.minute);
        this.time_label.setTextFill(Color.valueOf("#AAAAAA"));
        info.getChildren().addAll(this.author_label, new Spacer(), this.time_label);

        //Published to
        Label pub_to = new Label("Published(" + files.length + ") files to (" + checkouts.length + ") checkouts.");
        pub_to.setTextFill(Color.valueOf("#AAAAAA"));

        this.getChildren().addAll(l, pub_to, info);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobHistoryItem that)) return false;
        return Objects.equals(day, that.day) && Objects.equals(month, that.month) && Objects.equals(year, that.year) && Objects.equals(hour, that.hour) && Objects.equals(minute, that.minute) && Objects.equals(second, that.second) && Objects.equals(title, that.title) && Objects.equals(author, that.author) && Arrays.equals(nl, that.nl) && Arrays.equals(files, that.files) && Arrays.equals(checkouts, that.checkouts) && Objects.equals(author_label, that.author_label) && Objects.equals(time_label, that.time_label) && Objects.equals(sub, that.sub);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(day, month, year, hour, minute, second, title, author, author_label, time_label, sub);
        result = 31 * result + Arrays.hashCode(nl);
        result = 31 * result + Arrays.hashCode(files);
        result = 31 * result + Arrays.hashCode(checkouts);
        return result;
    }

    @Override
    public int compareTo(JobHistoryItem o) {
        if(this.year.compareTo(o.getYear()) == 0){
            if(this.month.compareTo(o.getMonth()) == 0){
                if(this.day.compareTo(o.getDay()) == 0){
                    return 0;
                }else{
                    return this.day.compareTo(o.getDay());
                }
            }else{
                return this.month.compareTo(o.getMonth());
            }
        }else{
            return this.year.compareTo(o.getYear());
        }
    }
}

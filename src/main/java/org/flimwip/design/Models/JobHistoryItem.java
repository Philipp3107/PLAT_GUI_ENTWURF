package org.flimwip.design.Models;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.flimwip.design.Documentationhandler.*;
import org.flimwip.design.Views.helpers.Spacer;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a single item in the job history.
 * Extends VBox and implements Comparable<JobHistoryItem>.
 */
@ServiceC(desc="Represents a single item in the job history.",
related={"Job", "Vendor"})
public class JobHistoryItem extends VBox implements Comparable<JobHistoryItem>{

    /**
     * Represents a specific day in the JobHistoryItem.
     * <p>
     * The day is stored as a string in the format "dd.mm.yyyy".
     * </p>
     */
    //11.03.2024;11:20:25;Push to 107;107;RandomArtGenerator.txt;1;Philipp Kotte
    @ServiceATT(desc="Represents a specific day in the JobHistoryItem. The day represents the timestamp of when the JobHistoryItem was created or modified.",
                type="String",
                related={"Job", "Vendor"})
    private String day;
    /**
     * Private variable representing the month.
     *
     * The month variable stores the month of a specific date.
     * It is a String representing the month in a numeric or textual format.
     *
     * Example usage:
     *
     *      JobHistoryItem item = new JobHistoryItem();
     *      String month = item.getMonth();
     *      System.out.println(month); // Output: "12"
     *
     * @see JobHistoryItem
     * @see JobHistoryItem#getMonth()
     */
    @ServiceATT(desc="Private variable representing the month. The month represents the timestamp of when the JobHistoryItem was created or modified.",
                type="String",
                related={"Job", "Vendor"})
    private String month;
    /**
     * The year of a JobHistoryItem object.
     */
    @ServiceATT(desc="The year of a JobHistoryItem object. The year represents the timestamp of when the JobHistoryItem was created or modified.",
                type="String",
                related={"Job", "Vendor"})
    private String year;
    /**
     * Represents the hour of a job history item.
     *
     * This variable stores the hour of a job history item as a string.
     * It is a private variable, accessible only within the containing class.
     *
     * Example usage:
     *     JobHistoryItem item = new JobHistoryItem();
     *     String hour = item.getHour();
     *
     * @see JobHistoryItem
     * @see JobHistoryItem#getHour()
     * @see JobHistoryItem#init()
     */
    @ServiceATT(desc="Represents the hour of a job history item. The hours represents the timestamp of when the JobHistoryItem was created or modified.",
                type="String",
                related={"Job", "Vendor"})
    private String hour;
    /**
     * Represents the minute of a JobHistoryItem.
     */
    @ServiceATT(desc="Represents the minute of a JobHistoryItem. The minutes represents the timestamp of when the JobHistoryItem was created or modified.",
                type="String",
                related={"Job", "Vendor"})
    private String  minute;

    /**
     * Represents the second of a JobHistoryItem.
     *
     * This variable is a private String that stores the second of a JobHistoryItem object.
     * The second represents the timestamp of when the JobHistoryItem was created or modified.
     * It is used to provide additional information about the JobHistoryItem.
     */
    @ServiceATT(desc="Represents the second of a JobHistoryItem. The second represents the timestamp of when the JobHistoryItem was created or modified.",
                type="String",
                related={"Job", "Vendor"})
    private String  second;
    /**
     * The title of a JobHistoryItem.
     */
    @ServiceATT(desc="The title of a JobHistoryItem.",
                type="String",
                related={"Job", "Vendor"})
    private String  title;
    /**
     * Represents the author of the document.
     *
     * This variable stores the name of the author who created or contributed to the document.
     *
     * Example usage:
     *
     * JobHistoryItem job = new JobHistoryItem();
     * String author = job.getAuthor();
     */
    @ServiceATT(desc="Represents the author of the document.",
                type="String",
                related={"Job", "Vendor"})
    private String author;
    /**
     * Private array of String elements.
     */
    @ServiceATT(desc="Private array of String elements.",
                type="String[]",
                related={"Job", "Vendor"})
    private String[] nl;
    /**
     * Retrieves the list of files.
     * @return The string array containing the files.
     */
    @ServiceATT(desc="Retrieves the list of files.",
                type="String[]",
                related={"Job", "Vendor"})
    private String[] files;
    /**
     * Array of strings representing checkouts.
     */
    @ServiceATT(desc="Array of strings representing checkouts.",
                type="String[]",
                related={"Job", "Vendor"})
    private String[] checkouts;

    /**
     * Represents a label for the author of a JobHistoryItem.
     *
     * This class is a private member of the JobHistoryItem class and provides a label for displaying the author's name.
     */
    @ServiceATT(desc="Represents a label for the author of a JobHistoryItem.",
                type="Label",
                related={"Job", "Vendor"})
    private Label author_label;
    /**
     * Represents a label that displays the time information of a JobHistoryItem object.
     */
    @ServiceATT(desc="Represents a label that displays the time information of a JobHistoryItem object.",
                type="Label",
                related={"Job", "Vendor"})
    private Label time_label;

    
    /**
     * Private variable representing a font used for subtitles.
     * The font is created using the "Verdana" font family, with a bold weight, regular posture,
     * and a size of 10.
     */
    @ServiceATT(desc="Private variable representing a font used for subtitles. The font is created using the \"Verdana\" font family, with a bold weight, regular posture, and a size of 10.",
                type="Font",
                related={"Job", "Vendor"})
    private Font sub = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 10);
    /**
     * Represents a job history item.
     *
     * This class provides a way to store and initialize a job history item with the given parameters.
     *
     */
    @ServiceCR(desc="Represents a job history item. This class provides a way to store and initialize a job history item with the given parameters.",
               params={"date: String -> Date representing the day of when the JobHistoryItem was created", "time: String -> Time representing the time of when the JobHistoryItem was created", "title: String -> Title of the JobHistoryItem", "nl: String -> NL influenced by the Job", "files: String -> files pushed to the Checkout", "checkouts: String -> String representing all the checkouts where files have been pushed to", "author: String -> String representing the Author of the JobHistoryItem"},
               related={"Job", "Vendor"})
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

    /**
     * Splits a given string by comma (",") and returns an array of substrings.
     *
     * @param to_split the string to be split
     * @return an array of substrings obtained by splitting the input string by comma (",")
     */
    @ServiceM(desc="<##>Splits a given string by comma (\",\") and returns an array of substrings.",
              category="Method",
              params={"to_split: String -> the string to be split"},
              returns="String[]",
              thrown={"None"},
              related={"None"})
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
    @ServiceM(desc="<##>Initializes the JobHistoryItem object by setting properties and adding child nodes.",
              category="Method",
              params={"none"},
              returns="void",
              thrown={"None"},
              related={"None"})
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
        this.time_label = new Label(this.day+"." + this.month + "." + this.year + " "+this.hour + ":" + this.minute);
        this.time_label.setTextFill(Color.valueOf("#AAAAAA"));
        info.getChildren().addAll(this.author_label, new Spacer(), this.time_label);

        //Published to
        Label pub_to = new Label("Published(" + files.length + ") files to (" + checkouts.length + ") checkouts.");
        pub_to.setTextFill(Color.valueOf("#AAAAAA"));

        this.getChildren().addAll(l, pub_to, info);
    }

    /**
     * Retrieves the value of the day.
     *
     * @return The value of the day.
     */
    @ServiceM(desc="<##>Retrieves the value of the day.",
              category="Getter",
              params={"None"},
              returns="String -> The value of the day.",
              thrown={"None"},
              related={"None"})
    public String getDay() {
        return day;
    }

    /**
     * Returns the month.
     *
     * @return the month.
     */
    @ServiceM(desc="<##>Returns the month.",
              category="Getter",
              params={"None"},
              returns="String -> the month",
              thrown={"None"},
              related={"None"})
    public String getMonth() {
        return month;
    }

    /**
     * Retrieves the year value of an object.
     *
     * @return The year value as a String.
     */
    @ServiceM(desc="Retrieves the year value of an object.",
              category="Getter",
              params={"None"},
              returns="String -> The year.",
              thrown={"None"},
              related={"None"})
    public String getYear() {
        return year;
    }

    /**
     * Returns the hour in a string format.
     *
     * @return the hour as a string.
     */
    @ServiceM(desc="<##>Returns the hour in a string format.",
              category="Getter",
              params={"None"},
              returns="String -> the hour.",
              thrown={"None"},
              related={"None"})
    public String getHour() {
        return hour;
    }

    /**
     * Retrieves the minute value of an object.
     *
     * @return The minute value as a String.
     */
    @ServiceM(desc="<##>Retrieves the minute value of an object.",
              category="Getter",
              params={"None"},
              returns="String -> The minute value as a String.",
              thrown={"None"},
              related={"None"})
    public String getMinute() {
        return minute;
    }

    /**
     * Retrieves the second value of an object.
     *
     * @return The second value as a String.
     */
    @ServiceM(desc="<##>Retrieves the second value of an object.",
              category="Getter",
              params={"None"},
              returns="String -> the seconds",
              thrown={"None"},
              related={"None"})
    public String getSecond() {
        return second;
    }

    /**
     * Retrieves the title of the JobHistoryItem.
     *
     * @return The title as a String.
     */
    @ServiceM(desc="Retrieves the title of the JobHistoryItem.",
              category="Getter",
              params={"None"},
              returns="String -> the title.",
              thrown={"None"},
              related={"None"})
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the author of the document.
     *
     * @return The author of the document as a String.
     */
    @ServiceM(desc="Retrieves the author of the document.",
              category="Getter",
              params={"None"},
              returns="String -> the author.",
              thrown={"None"},
              related={"None"})
    public String getAuthor() {
        return author;
    }

    /**
     * Retrieves the nl (new line) values stored in the JobHistoryItem object.
     *
     * @return an array of String containing the nl values.
     */
    @ServiceM(desc="Retrieves the nl (new line) values stored in the JobHistoryItem object.",
              category="Getter",
              params={"None"},
              returns="String[] -> an array of String containing the nl values.",
              thrown={"None"},
              related={"None"})
    public String[] getNl() {
        return nl;
    }

    /**
     * Retrieves an array of file names.
     *
     * @return an array of file names
     */
    @ServiceM(desc="Retrieves an array of file names.",
              category="Getter",
              params={"None"},
              returns="String[] -> an array of file names.",
              thrown={"None"},
              related={"None"})
    public String[] getFiles() {
        return files;
    }

    /**
     * Retrieves the array of checkouts.
     *
     * @return The array of checkouts.
     */
    @ServiceM(desc="Retrieves the array of checkouts.",
              category="Getter",
              params={"None"},
              returns="String[] -> the array of checkouts.",
              thrown={"None"},
              related={"None"})
    public String[] getCheckouts() {
        return checkouts;
    }

    /**
     * Retrieves the author of the document.
     *
     * @return The author of the document as a String.
     */
    @ServiceM(desc="Retrieves the author of the document.",
              category="Getter",
              params={"None"},
              returns="String[] -> The author of the document as a String.",
              thrown={"None"},
              related={"None"})
    public String get_author(){return this.author;}

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * This method overrides the equals method of the Object class.
     *
     * @param o the reference object with which to compare
     * @return true if this object is the same as the o argument; false otherwise
     */
    @Override
    @ServiceM(desc="Indicates whether some other object is \"equal to\" this one.",
              category="Method",
              params={"o: Object -> the reference object with which to compare"},
              returns="true if this object is the same as the o argument, false otherwise",
              thrown={"None"},
              related={"None"})
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobHistoryItem that)) return false;
        return Objects.equals(day, that.day) && Objects.equals(month, that.month) && Objects.equals(year, that.year) && Objects.equals(hour, that.hour) && Objects.equals(minute, that.minute) && Objects.equals(second, that.second) && Objects.equals(title, that.title) && Objects.equals(author, that.author) && Arrays.equals(nl, that.nl) && Arrays.equals(files, that.files) && Arrays.equals(checkouts, that.checkouts) && Objects.equals(author_label, that.author_label) && Objects.equals(time_label, that.time_label) && Objects.equals(sub, that.sub);
    }

    /**
     * Calculates the hash code value for this object.
     *
     * @return the hash code value for this object.
     */
    @ServiceM(desc="Calculates the hash code value for this object.",
              category="Method",
              params={"None"},
              returns="int -> the hash code value for this object.",
              thrown={"None"},
              related={"None"})
    @Override
    public int hashCode() {
        int result = Objects.hash(day, month, year, hour, minute, second, title, author, author_label, time_label, sub);
        result = 31 * result + Arrays.hashCode(nl);
        result = 31 * result + Arrays.hashCode(files);
        result = 31 * result + Arrays.hashCode(checkouts);
        return result;
    }

    /**
     * Compare the current instance of JobHistoryItem with another instance.
     *
     * @param o - The JobHistoryItem instance to be compared.
     *
     * @return A negative integer if the current instance is less than the specified instance.
     *          Zero if the current instance is equal to the specified instance.
     *          A positive integer if the current instance is greater than the specified instance.
     */
    @ServiceM(desc="Compare the current instance of JobHistoryItem with another instance.",
              category="Method",
              params={"o: JobHistoryItem -> The JobHistoryItem instance to be compared."},
              returns="A negative integer if the current instance is less than the specified instance. Zero if the current instance is equal to the specified instance. A positive integer if the current instance is greater than the specified instance.",
              thrown={"None"},
              related={"None"})
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

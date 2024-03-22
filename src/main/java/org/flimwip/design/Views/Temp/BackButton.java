package org.flimwip.design.Views.Temp;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.flimwip.design.Documentationhandler.*;


/**
 * The BackButton class represents a custom button that, when clicked, triggers the "go_back" method of the specified BranchView.
 * It extends the VBox class.
 */
@ServiceC(desc="The BackButton class represents a custom button that, when clicked, triggers the \"go_back\" method of the specified BranchView. It extends the VBox class.",
related={"BranchView"})
public class BackButton extends VBox {

    /**
     * The private variable l is an instance of the Label class.
     * It is used as a label for the BackButton in the containing BackButton class.
     */
    @ServiceATT(desc="The private variable l is an instance of the Label class. It is used as a label for the BackButton in the containing BackButton class.",
                type="Label",
                related={"None"})
    private Label l;
    /**
     * The BranchView class represents a view of a branch in a tree structure.
     * It contains information about the branch's children, parent, and data.
     */
    @ServiceATT(desc="The BranchView class represents a view of a branch in a tree structure. It contains information about the branch's children, parent, and data.",
                type="BranchView",
                related={"BranchView"})
    BranchView view;

    /**
     * The BackButton class represents a custom button that, when clicked, triggers the "go_back" method of the specified BranchView.
     * It extends the VBox class.
     */
    @ServiceCR(desc="The BackButton class represents a custom button that, when clicked, triggers the \"go_back\" method of the specified BranchView., It extends the VBox class",
               params={"view: BranchView -> The BranchView this class belongs to"},
               related={"BranchView"})
    public BackButton(BranchView view){
        this.view = view;
        this.l = new Label("Zurück");
        l.setStyle("-fx-font-weight: bold");
        this.l.setTextFill(Color.WHITE);
        this.setStyle("-fx-background-color: #565656; -fx-border-color: #565656; -fx-border-radius: 15; -fx-background-radius: 15;");
        this.setMinHeight(30);
        this.setMaxHeight(30);
        this.setAlignment(Pos.CENTER);
        this.setOnMouseClicked(event -> {
            this.view.go_back();
        });
        this.getChildren().add(this.l);
    }
}

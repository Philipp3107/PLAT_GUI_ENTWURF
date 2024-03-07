package org.flimwip.design.Views.helpers;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;

public class CircleLoader extends Group {

    private Circle outer;

    private Circle inner;

    private Arc arc;

    private int outer_radius = 15;
    private int inner_radius = 10;

    private int start_angle = 90;

    private Color standart_fill = Color.GRAY;

    private Color inner_fill = Color.valueOf("#6c708c");

    private Color standart_green = Color.valueOf("#50ad50");
    public CircleLoader(){
        init();
    }


    private void init()
    {
        //Builing outer circle
        outer = new Circle();
        outer.setRadius(outer_radius);
        outer.setFill(standart_fill);


        //Building inner circle
        inner = new Circle();
        inner.setRadius(inner_radius);
        inner.setFill(inner_fill);

        //Building acr
        arc = new Arc(0, 0, outer_radius, outer_radius, start_angle, 0);
        arc.setType(ArcType.ROUND);
        arc.setFill(standart_green);
        getChildren().addAll(outer, arc, inner);

    }



    public void update(double percentage){
        this.arc.setLength(-360 * percentage);
    }
}

package org.percepta.mgrankvi.demo;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.percepta.mgrankvi.Room;
import org.percepta.mgrankvi.SeatingMap;
import org.percepta.mgrankvi.Table;
import org.percepta.mgrankvi.client.geometry.Line;
import org.percepta.mgrankvi.client.geometry.Point;
import org.percepta.mgrankvi.util.ImageToLines;

import javax.servlet.annotation.WebServlet;
import java.util.List;

@Theme("demo")
@SpringUI(path = "")
@SuppressWarnings("serial")
public class DemoUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {

        // Initialize our new UI component
        final SeatingMap component = new SeatingMap();
        component.setSizeFull();

        ImageToLines imageToLines = new ImageToLines();
        List<Line> thirdLines = imageToLines.getLines("/org/percepta/mgrankvi/demo/ThirdFloorMarketing.png", new Point(150, 150));
        Room room = component.addRoom(1, thirdLines);
        room.addComponent(new Table(imageToLines.getLines("/org/percepta/mgrankvi/demo/ThirdFloorSales-t5.png", new Point(150, 150))));

        thirdLines = imageToLines.getLines("/org/percepta/mgrankvi/demo/ThirdFloorSales.png", new Point(150, 150));
        room = component.addRoom(1, thirdLines);
        Table table = new Table(imageToLines.getLines("/org/percepta/mgrankvi/demo/ThirdFloorSales-t1.png", new Point(150, 150)));
        table.setName("Terhi Testi");
        table.setImageUrl("https://vaadin.com/vaadin-theme/images/vaadin/vaadin-logo-small.png");
        room.addComponent(table);
        room.addComponent(new Table(imageToLines.getLines("/org/percepta/mgrankvi/demo/ThirdFloorSales-t2.png", new Point(150, 150))));
        room.addComponent(new Table(imageToLines.getLines("/org/percepta/mgrankvi/demo/ThirdFloorSales-t3.png", new Point(150, 150))));
        room.addComponent(new Table(imageToLines.getLines("/org/percepta/mgrankvi/demo/ThirdFloorSales-t4.png", new Point(150, 150))));

        thirdLines = imageToLines.getLines("/org/percepta/mgrankvi/demo/ThirdFloorUtilities.png", new Point(150, 150));
        component.addLines(1, thirdLines);

        List<Line> pathLines = imageToLines.getLines("/org/percepta/mgrankvi/demo/ThirdFloorPaths.png", new Point(150, 150));

        component.addPaths(pathLines, 1);

        // Show it in the middle of the screen
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setStyleName("demoContentLayout");
        layout.setSizeFull();
        layout.addComponent(component);
        layout.setComponentAlignment(component, Alignment.MIDDLE_CENTER);
        setContent(layout);
    }
}

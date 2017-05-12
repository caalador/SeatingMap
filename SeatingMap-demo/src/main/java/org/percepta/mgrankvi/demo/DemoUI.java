package org.percepta.mgrankvi.demo;

import javax.servlet.annotation.WebServlet;
import java.util.List;

import org.percepta.mgrankvi.Room;
import org.percepta.mgrankvi.SeatingMap;
import org.percepta.mgrankvi.Table;
import org.percepta.mgrankvi.client.geometry.Line;
import org.percepta.mgrankvi.client.geometry.Point;
import org.percepta.mgrankvi.util.ImageToLines;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

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
        List<Line> thirdLines = imageToLines.getLines(
                "/org/percepta/mgrankvi/demo/RightSide.png",
                new Point(150, 150));
        Room room = component.addRoom(1, thirdLines);
        Table table2 = new Table(imageToLines.getLines(
                "/org/percepta/mgrankvi/demo/Table5.png", new Point(150, 150)));
        room.addComponent(table2);

        thirdLines = imageToLines.getLines(
                "/org/percepta/mgrankvi/demo/LeftSide.png",
                new Point(150, 150));
        room = component.addRoom(1, thirdLines);
        Table table = new Table(imageToLines.getLines(
                "/org/percepta/mgrankvi/demo/Table1.png", new Point(150, 150)));
        table.setName("Terhi Testi");
        table.setImageUrl(
                "https://vaadin.com/vaadin-theme/images/vaadin/vaadin-logo-small.png");
        room.addComponent(table);
        room.addComponent(new Table(
                imageToLines.getLines("/org/percepta/mgrankvi/demo/Table2.png",
                        new Point(150, 150))));
        room.addComponent(new Table(
                imageToLines.getLines("/org/percepta/mgrankvi/demo/Table3.png",
                        new Point(150, 150))));
        room.addComponent(new Table(
                imageToLines.getLines("/org/percepta/mgrankvi/demo/Table4.png",
                        new Point(150, 150))));

        thirdLines = imageToLines.getLines(
                "/org/percepta/mgrankvi/demo/FloorUtilities.png",
                new Point(150, 150));
        component.addLines(1, thirdLines);

        List<Line> pathLines = imageToLines.getLines(
                "/org/percepta/mgrankvi/demo/Paths.png", new Point(150, 150));

        component.addPaths(pathLines, 1);
        component.connectTablesToPaths();

        component.getPath(table.getNodeId(), table2.getNodeId());

        component.addSelectionListener(event -> {
            System.out.println("Click selection event recieved with payload: "
                    + event.getRoom() + " :: " + event.getTable());
        });

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

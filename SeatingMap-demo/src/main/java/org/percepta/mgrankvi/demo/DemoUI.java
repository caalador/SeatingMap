package org.percepta.mgrankvi.demo;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.percepta.mgrankvi.FloorMap;
import org.percepta.mgrankvi.SeatingMap;
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

    List<Line> thirdLines = new ImageToLines().getLines("/org/percepta/mgrankvi/demo/ThirdFloorMarketing.png",new Point(150,150));
    component.addRoom(1, thirdLines);
    thirdLines = new ImageToLines().getLines("/org/percepta/mgrankvi/demo/ThirdFloorSales.png",new Point(150,150));
    component.addRoom(1, thirdLines);
    thirdLines = new ImageToLines().getLines("/org/percepta/mgrankvi/demo/ThirdFloorUtilities.png",new Point(150,150));
    component.addRoom(1, thirdLines);

    // Show it in the middle of the screen
    final VerticalLayout layout = new VerticalLayout();
    layout.setStyleName("demoContentLayout");
    layout.setSizeFull();
    layout.addComponent(component);
    layout.setComponentAlignment(component, Alignment.MIDDLE_CENTER);
    setContent(layout);
  }
}

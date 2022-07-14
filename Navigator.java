import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Node;
import javafx.event.*;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * The Navigator class creates the navigation (back and forward) buttons
 * and implements their functionality.
 *
 * @author  Immanuel Rajadurai, Maya Dejonge, Giorgio Grimesty, Aaron Gomes
 * @version 06/04/2021
 */
public class Navigator
{
    // Stores all panels.
    private ArrayList<Node> panelArrayList;
    // Keeps track of current panel.
    private int panelIndex = 0;
    // Navigation buttons
    private Button backButton;
    private Button forwardButton;

    /**
     * Constructor for objects of class Navigator
     */
    public Navigator()
    {
        panelArrayList = new ArrayList<>();
    }

    /**
     * Creates a HBox containing the navigation buttons.
     * @return the navigation buttons.
     */
    public HBox createButtons()
    {
        //These buttons toggle which panel is in view
        backButton = new Button("<");
        forwardButton = new Button(">");

        backButton.setVisible(false);
        forwardButton.setVisible(false);

        //Initialises the buttons
        HBox buttonBox = new HBox(5);
        backButton.setOnAction(this::backClick);
        forwardButton.setOnAction(this::forwardClick);

        //This section contains forward and back buttons
        final Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(10, 1);

        buttonBox.getChildren().addAll(backButton, spacer, forwardButton);
        return buttonBox;
    }

    /**
     * Goes forward 1 panel when the > button is clicked
     */
    private void forwardClick(ActionEvent event)
    {
        panelArrayList.get(panelIndex).setVisible(false);
        panelIndex = (panelIndex + 1) % panelArrayList.size();
        panelArrayList.get(panelIndex).setVisible(true);
    }

    /**
     * Goes back 1 panel when the < button is clicked
     */
    private void backClick(ActionEvent event)
    {
        panelArrayList.get(panelIndex).setVisible(false);

        if(panelIndex == 0)
        {
            panelIndex = panelArrayList.size() -1;
        } else {
            panelIndex--;
        }

        panelArrayList.get(panelIndex).setVisible(true);
    }

    public Button getBackButton()
    {
        return backButton;
    }

    public Button getForwardButton()
    {
        return forwardButton;
    }
    
    /**
     * Adds a Pane to the list of all Panes.
     */
    public void addPanel(Pane newPanel)
    {
        panelArrayList.add(newPanel);
    }
    
    /**
     * Removes a Pane from the list of all Panes.
     */
    public void removePanel(Pane panelToRemove) {
        panelArrayList.remove(panelArrayList.indexOf(panelToRemove));
    }
}
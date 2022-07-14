import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Node;
import javafx.event.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;

/**
 * The StatsBox class creates one box which can display multiple statistics.
 * The statistics can be changed by using the box's navigation buttons.
 *
 * @author  Immanuel Rajadurai, Maya Dejonge, Giorgio Grimesty, Aaron Gomes
 * @version 06/04/2021
 */
public class StatsBox
{
    // components
    private BorderPane pane;
    private Button leftButton;
    private Button rightButton;

    private HashMap<String, String> statistics;
    private ArrayList<Node> statPanelList; 

    private StackPane masterStackPane;

    private int currentIndex;

    private VBox statBox;

    /**
     * Constructor for objects of class MapPage
     */
    public StatsBox()
    {
        statistics = new HashMap<>();
        statPanelList = new ArrayList<>();
        statBox = new VBox();
        pane = new BorderPane();
        masterStackPane = new StackPane();
        currentIndex = 0;

        pane.getStyleClass().add("stat_box");
    }

    /**
     * Returns the number of statistics.
     */
    public int getNoOfStatistics() {
        return statistics.size();
    }
    
    /**
     * Adds a statistic to the list of all statistics.
     */
    public void addStat(String newTitle, String newInfo) {        
        statistics.put(newTitle, newInfo);
    }

    /**
     * Sets up the stats box.
     */
    public void setUpStatsBox()
    {
        // set up navigation buttons
        leftButton = new Button("<");
        rightButton = new Button(">");
        leftButton.setOnAction(this::forwardClick);
        rightButton.setOnAction(this::backClick);
        pane.setLeft(leftButton);
        pane.setRight(rightButton);
        
        // set up stat display.
        Set<String> statKeys = statistics.keySet();
        
        for (String key : statKeys) {
            VBox tempBox = new VBox();

            Label titleLabel = new Label(key);
            titleLabel.setPadding(new Insets(0,5,5,5));
            titleLabel.setMaxWidth(500);
            titleLabel.getStyleClass().add("stat_label");

            Label statsLabel = new Label(statistics.get(key));
            statsLabel.setPadding(new Insets(0,5,5,5));            
            statsLabel.setMaxWidth(500);      

            tempBox.getChildren().add(titleLabel);
            tempBox.getChildren().add(statsLabel);

            statPanelList.add(tempBox);
            masterStackPane.getChildren().add(tempBox);
        }
    }
    
    /**
     * Shows the current statistic.
     */
    public Pane show()
    {
        pane.setCenter(statPanelList.get(currentIndex));
        return pane;
    }
    
    /**
     * Goes forward 1 panel when the > button is clicked
     */
    private void forwardClick(ActionEvent event)
    {
        statPanelList.get(currentIndex).setVisible(false);
        currentIndex = (currentIndex + 1) % statPanelList.size();
        statPanelList.get(currentIndex).setVisible(true);
        show();
    }

    /**
     * Goes back 1 panel when the < button is clicked
     */
    private void backClick(ActionEvent event)
    {
        statPanelList.get(currentIndex).setVisible(false);

        if(currentIndex == 0)
        {
            currentIndex = statPanelList.size()-1;
        } else {
            currentIndex--;
        }

        statPanelList.get(currentIndex).setVisible(true);
        show();
    }

}
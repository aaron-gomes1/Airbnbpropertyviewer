import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import java.util.HashSet;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * This mainStage class is responsible for starting up the application 
 * and making sure all objects are initialized
 *
 * @author  Immanuel Rajadurai, Maya Dejonge, Giorgio Grimesty, Aaron Gomes
 * @version 06/04/2021
 */
public class MainStage extends Application
{
    private Stage stage;

    // All the tools.
    private PriceSelector priceSelector;
    private Navigator navigator;
    private Favouriter favouriter;
    private AirbnbDataLoader dataLoader;
    
    // Page components.
    private Label priceDisplay;
    private Page recommendations;

    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage)
    {
        // Create a new BorderPane to contain the master stack pane as well as the buttons and drop-downs
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("background_image");
        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(pane, 1000, 700);
        scene.getStylesheets().add("main.css");

        //Contains all panes to flick through i.e. welcome, maps, and statistics
        StackPane masterStackPane = new StackPane();

        //Details for main stage
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setMinSize(600, 600); 
        stage.setTitle("AirBNB Viewer");
        stage.setScene(scene);
        this.stage = stage;

        //The individual panels i.e. welcome, map, statistics...

        dataLoader = new AirbnbDataLoader();
        favouriter = new Favouriter();

        // Initialize all component creators.
        navigator = new Navigator();
        priceSelector = new PriceSelector(navigator);

        Label priceDisplay = priceSelector.createPriceRangeLabel();

        Page welcomePage = new WelcomePage(priceSelector, dataLoader);
        Page map = new MapPage(priceSelector, dataLoader, favouriter);
        Page stats = new StatisticsPage(priceSelector, dataLoader, favouriter, (MapPage) map);
        recommendations = new RecommendationsPage(priceSelector, dataLoader, favouriter);

        MapPage m = (MapPage) map;
        m.addUpdatePage(stats);
        m.addUpdatePage(recommendations);
        
        priceSelector.addPage(map);
        priceSelector.addPage(stats);

        // Creates the Welcome panel
        Pane panel1Welcome = welcomePage.getPane();
        panel1Welcome.getStyleClass().add("welcome_pane");

        // Creates the Map panel.
        Pane panel2Map = map.getPane();
        panel2Map.getStyleClass().add("page");
        panel2Map.getStyleClass().add("map");

        // Creates the Statistics panel
        Pane panel3Stats = stats.getPane();
        panel3Stats.getStyleClass().add("page");
        // Creates the Recommendations panel
        Pane panel4Recommendations = recommendations.getPane();
        panel4Recommendations.getStyleClass().add("page");

        //Adds individual panels to the ArrayList therefore we can scroll through them with arrow buttons
        navigator.addPanel(panel1Welcome);
        navigator.addPanel(panel2Map);
        navigator.addPanel(panel3Stats);
        navigator.addPanel(panel4Recommendations);
        panel2Map.setVisible(false);
        panel3Stats.setVisible(false);
        panel4Recommendations.setVisible(false);

        // Creates the back + forward buttons and toolbar.
        HBox buttonBox = navigator.createButtons();
        HBox priceBox = priceSelector.create();

        Button viewFavouritesButton = new Button("View Favourites");
        viewFavouritesButton.setOnAction(this::showFavourites);
        HBox toolbar = new HBox();

        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        toolbar.getChildren().addAll(priceBox, region1, viewFavouritesButton);

        toolbar.getStyleClass().add("toolbar");
        // Set base layout of application.
        pane.setBottom(buttonBox);
        pane.setTop(toolbar);
        pane.setCenter(masterStackPane);

        //MasterStackPane occupies the center of the stage- displays the individual panels
        masterStackPane.getChildren().add(panel1Welcome);
        masterStackPane.getChildren().add(panel2Map);  
        masterStackPane.getChildren().add(panel3Stats);
        masterStackPane.getChildren().add(panel4Recommendations);

        // Show the Stage (window)
        stage.show();
    }

    /**
     * Generates a list of all favourited properties and displays in a
     * separate window.
     * 
     */
    private void showFavourites(ActionEvent event)
    {
        HashSet favListingsSet = favouriter.getListings();
        ArrayList favListingsList = new ArrayList(favListingsSet);
        PropertyDisplay favouritesDisplay = new PropertyDisplay(favListingsList, "favourites", dataLoader, favouriter);
        favouritesDisplay.addPage(recommendations);
    }
}
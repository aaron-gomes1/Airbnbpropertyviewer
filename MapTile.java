import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.stage.*;
import javafx.geometry.Insets;
import java.lang.IllegalArgumentException;

import java.util.ArrayList;

/**
 * The MapTile class creates a tile for a specific borough in the map.
 *
 * @author  Immanuel Rajadurai, Maya Dejonge, Giorgio Grimesty, Aaron Gomes
 * @version 06/04/2021
 */
public class MapTile extends PageUpdater
{
    // tools
    private AirbnbDataLoader dataLoader;
    private PriceSelector selector;
    private Favouriter favouriter;
    private PageUpdater pageUpdater;
    
    private StackPane stack;
    
    // borough information
    private ImageView boroughImage;
    private Label noOfProperties;
    private String code;
    private String boroughName;
    
    private MapPage map;

    /**
     * Constructor for objects of class MapTile
     */
    public MapTile(String code, String boroughName, AirbnbDataLoader dataLoader, PriceSelector selector, Favouriter favouriter)
    {
        this.code = code;
        this.boroughName = boroughName;
        this.dataLoader = dataLoader;
        this.selector = selector;
        this.favouriter = favouriter;
        
        Label boroughCode = new Label(code.toUpperCase());
        boroughCode.setOnMouseClicked(this::selectProperties);
        boroughCode.setPadding(new Insets(0, 0, 10, 0));
        
        noOfProperties = new Label("");
        noOfProperties.setOnMouseClicked(this::selectProperties);
        noOfProperties.setPadding(new Insets(30, 0, 0, 0));
        
        // Tries to open the image, if it is not there then it produces an error message
        try {
            boroughImage = new ImageView("tile.png");
            boroughImage.setFitHeight(70);
            boroughImage.setFitWidth(70);
            boroughImage.setOnMouseClicked(this::selectProperties);
            stack = new StackPane(boroughImage, noOfProperties, boroughCode);
        } catch (IllegalArgumentException exception) {
            System.out.println("Cannot map tile");
            stack = new StackPane(noOfProperties, boroughCode);
        } catch (NullPointerException exception) {
            System.out.println("Invalid file path selected");
        }
    }

    /**
     * Gets the StackPane
     */
    public StackPane getStack()
    {
        return stack;
    }
    
    /**
     * Opens the window with the properties for that borough and price range
     */
    private void selectProperties(MouseEvent event)
    {
        // Updates the stats page when the borough is clicked
        updatePages();
        
        int startingPrice = getStartingPrice();
        int endingPrice = getEndingPrice();
        ArrayList<AirbnbListing> listings = dataLoader.getPropertiesInNeighbourhood(boroughName, startingPrice, endingPrice);
        PropertyDisplay display = new PropertyDisplay(listings, boroughName, dataLoader, favouriter);
        addUpdate(display);
    }
    
    /**
     * Adds the pages to be updated to the property display
     */
    private void addUpdate(PropertyDisplay display)
    {
        for (Page page : getPages()) {
            if (page instanceof RecommendationsPage) {
                display.addPage(page);
            }
        }
    }

    /**
     * Updates the number of properties when the price range is changed
     */
    public void updateNoOfProperties()
    {
        int startingPrice = getStartingPrice();
        int endingPrice = getEndingPrice();
        ArrayList<AirbnbListing> listings = dataLoader.getPropertiesInNeighbourhood(boroughName, startingPrice, endingPrice);
        noOfProperties.setText("" + listings.size());
    }
    
    /**
     * Gets the from price
     */
    private int getStartingPrice()
    {
        Object from = selector.getFromPrice();
        int startingPrice;
        if (from == null) {
            startingPrice = 0;
        }
        else {
            startingPrice = (int) from;
        }
        return startingPrice / 1000;
    }
    
    /**
     * Gets the to price
     */
    private int getEndingPrice()
    {
        Object to = selector.getToPrice();
        int endingPrice;
        if (to == null) {
            endingPrice = 250000;
        }
        else {
            endingPrice = (int) to;
        }
        return endingPrice / 1000;
    }
    
    /**
     * Overrides the updatePages method in PageUpdater
     */
    @Override
    public void updatePages()
    {
        for (Page page : getPages()) {
            //Uses the override method if the page is a StatisticsPage
            if (page instanceof StatisticsPage) {
                StatisticsPage stat = (StatisticsPage) page;
                stat.update(boroughName);
            }
            //Uses the override method if the page is a RecommendationsPage
            else if (page instanceof RecommendationsPage) {
                RecommendationsPage rec = (RecommendationsPage) page;
                rec.update(boroughName);
            }
            else {
                page.update();
            }
        }   
    }
    
    /**
     * @return Gets the borough Name
     */
    public String getBoroughName()
    {
        return boroughName;
    }
}
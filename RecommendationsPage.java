import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.scene.Node;

import java.util.ArrayList;
/**
 * A page that recommends properties in a certain borough based on the users favourite properties
 * in that borough.
 *
 * @author  Immanuel Rajadurai, Maya Dejonge, Giorgio Grimesty, Aaron Gomes
 * @version 06/04/2021
 */
public class RecommendationsPage extends Page
{
    private Favouriter favouriter;
    private String boroughName;
    private GridPane propertyListings;
    private PropertyDisplay propertyDisplay;
    private VBox container;
    private Label description;
    
    /**
     * Constructor for objects of class RecommendationsPage.
     */
    public RecommendationsPage(PriceSelector selector, AirbnbDataLoader dataLoader, Favouriter favouriter)
    {
        super(selector, dataLoader);
        
        this.favouriter = favouriter;
        
        description = new Label("RECOMMENDED PROPERTIES");
        description.getStyleClass().add("title");
        
        container = new VBox(description);
        container.setFillWidth(true);
        container.getStyleClass().add("embedded_property_display");
        
        setPane(container);
        
        update();
    }
    
    /**
     * Refreshes the properties displayed in the page
     */
    public void update()
    {
        ArrayList<AirbnbListing> properties = calculateRecommendedProperties();
        
        container.getChildren().clear();
        
        container.getChildren().add(description);
        
        // Embeds the property display into the page
        propertyDisplay = new PropertyDisplay(properties, boroughName, getDataLoader(), favouriter, container);
        propertyDisplay.addPage(this);
    }
    
    /**
     * Changes the borough and updates
     */
    public void update(String boroughName)
    {
        this.boroughName = boroughName;
        update();
    }
    
    /**
     * Gets the properties that are similar to the properties the user has as a favourite
     */
    private ArrayList<AirbnbListing> calculateRecommendedProperties() 
    {
        int fromPrice = 0;
        int toPrice = 0;
        try {
            Object from = getSelector().getFromPrice();
            Object to = getSelector().getToPrice();
            fromPrice = (int) from;
            toPrice = (int) to;
        } catch (NullPointerException exception) {
        }
        
        ArrayList<AirbnbListing> boroughData = getDataLoader().getPropertiesInNeighbourhood(boroughName, fromPrice/1000, toPrice/1000);
              
        ArrayList<AirbnbListing> recommendedList = new ArrayList<>();
        for (Object favListing : favouriter.getListings()) {
            AirbnbListing favouriteListing = (AirbnbListing) favListing;
                for (AirbnbListing listing : boroughData) {
                    if (  compare(favouriteListing.getMinimumNights(), listing.getMinimumNights(), 1) 
                          && compare(favouriteListing.getAvailability365(), listing.getAvailability365(), 20)
                          && compare(favouriteListing.getPrice(), listing.getPrice(), 20)  ) {
                              
                          recommendedList.add(listing);
                    }
            }
        }

        return recommendedList;
    }
    
    /**
     * Simple comparator mechanism that compares 2 values against each other with a leeway (bound)
     * as to what max, min value should be
     * 
     * @param valToCompare The value that is being compared
     * @param valToCompareAgainst The value that is being compared against
     * @param bounds The maximum difference allowed between the value being compared and the value being compared against
     * @return If the two values are within the bounds
     */
    public boolean compare(int valToCompare, int valToCompareAgainst, int bounds)
    {    
        int upper = valToCompareAgainst + bounds;
        int lower = valToCompareAgainst - bounds;
        
        return (valToCompare >= lower && valToCompare <= upper);    
    }
}

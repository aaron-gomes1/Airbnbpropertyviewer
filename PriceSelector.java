import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.*;

/**
 * The PriceSelector class creates the dropdown for price selection and keeps track of the
 * selected prices.
 *
 * @author  Immanuel Rajadurai, Maya Dejonge, Giorgio Grimesty, Aaron Gomes
 * @version 06/04/2021
 */
public class PriceSelector extends PageUpdater
{
    private Label priceRangeDisplay;
    //The price selectors
    private ComboBox priceListFrom;
    private ComboBox priceListTo;

    private Navigator navigator;

    
    /**
     * Constructor for objects of class PriceSelector
     */
    public PriceSelector(Navigator navigator)
    {
        this.navigator = navigator;
    }

    /**
     * Creates the selectors
     */
    public HBox create()
    {
        //These will filter the prices of the properties shown
        ObservableList<Integer> pricesFrom = 
            FXCollections.observableArrayList
            (
                0,
                100000,
                150000,
                200000
            );

        ObservableList<Integer> pricesTo = 
            FXCollections.observableArrayList
            (
                250000,
                350000,
                450000
            );

        Label priceFromX = new Label("From");
        Label priceToX = new Label("To");

        priceListFrom = new ComboBox(pricesFrom);
        priceListTo = new ComboBox(pricesTo);
        
        // Update pages when price range has been selected
        priceListTo.setOnAction(e -> {displayNavigation(); displayPrice(); updatePages();});
        priceListFrom.setOnAction(e -> {displayNavigation(); displayPrice(); updatePages();});
        
        HBox priceBox = new HBox(5);
        priceBox.getChildren().addAll(priceFromX, priceListFrom, priceToX, priceListTo);
        priceBox.getStyleClass().add("price_box");
        return priceBox;
    }

    
    /**
     * Gets the minimum price
     */
    public Object getFromPrice()
    {
        return priceListFrom.getValue();
    }

    
    /**
     * Gets the maximum price
     */
    public Object getToPrice()
    {
        return priceListTo.getValue();
    }

    //Returns the labels containing price points
    public Label createPriceRangeLabel()
    {
        priceRangeDisplay = new Label("Please select a price range.");
        return priceRangeDisplay;
    }

    //Displays the selected price range
    private void displayPrice()
    {
        if(getFromPrice() != null && getToPrice() != null)
        {
            String prices = "You have selected " + getFromPrice() + " to " + getToPrice() + ".";
            priceRangeDisplay.setText(prices);
        }
    }

    //Makes the left/right buttons visible
    private void displayNavigation()
    {
        if(getFromPrice() != null && getToPrice() != null)
        {
            navigator.getBackButton().setVisible(true); 
            navigator.getForwardButton().setVisible(true);
        }
    }
}
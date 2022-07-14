import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.geometry.Pos;

import java.util.ArrayList;

/**
 * Creates a new window that displays a list of properties.
 *
 * @author  Immanuel Rajadurai, Maya Dejonge, Giorgio Grimesty, Aaron Gomes
 * @version 06/04/2021
 */
public class PropertyDisplay extends PageUpdater
{
    // tools
    private ArrayList<AirbnbListing> listings;
    private AirbnbDataLoader dataLoader;
    private GridPane propertyListings;
    private Favouriter favouriter;
    
    // page components
    private HBox taskbar;
    private ScrollPane scroll;

    private AirbnbListing property;

    /**
     * Sets up the property display in a new window
     */
    public PropertyDisplay(ArrayList<AirbnbListing> listings, String neighbourhood, AirbnbDataLoader dataLoader, Favouriter favouriter)
    {
        this.listings = listings;
        this.dataLoader = dataLoader;
        this.favouriter = favouriter;
        
        BorderPane pane = setupDisplay();
        
        pane.getStyleClass().add("property_display");
        
        Stage stage = new Stage();
        Button close = new Button("Close");
        close.setOnAction(event -> stage.close());
        taskbar.getChildren().add(close);
        Scene scene = new Scene(pane, 700, 600);

        scene.getStylesheets().add("main.css");
        
        stage.setTitle("Properties in " + neighbourhood);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Sets up the property display in an embedded pane
     */
    public PropertyDisplay(ArrayList<AirbnbListing> listings, String neighbourhood, AirbnbDataLoader dataLoader, Favouriter favouriter, Pane container)
    {
        this.listings = listings;
        this.dataLoader = dataLoader;
        this.favouriter = favouriter;
        
        BorderPane pane = setupDisplay();
        
        if(listings.size() == 0) {
            Label noRecommendations = new Label("Favourite some properties to see some recommendations.");
            noRecommendations.getStyleClass().add("no_recommendations_label");
            scroll = new ScrollPane(noRecommendations);
            pane.setCenter(scroll);
        }

        if (container != null) {
            container.getChildren().add(pane);
        }
    }
    
    /**
     * Sets up the basic display
     */
    private BorderPane setupDisplay()
    {
        //Sets up the pane that will display the properties
        propertyListings = new GridPane();
        propertyListings.setPadding(new Insets(10, 10, 10, 10));
        propertyListings.setHgap(30);
        propertyListings.setAlignment(Pos.CENTER);

        //The options the properties can be ordered
        ObservableList<String> sortOptions = 
            FXCollections.observableArrayList
            (
                "Price",
                "Host Name",
                "Number of reviews"
            );

        Label sort = new Label("Sort By");
        ComboBox order = new ComboBox(sortOptions);
        order.setOnAction(this::orderProperties);
        
        final Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(10, 1);
        
        taskbar = new HBox(sort, order, spacer);
        taskbar.setPadding(new Insets(10, 10, 10, 10));
        taskbar.setSpacing(10);

        //Makes the window scrollable
        scroll = new ScrollPane(propertyListings);
        
        BorderPane pane = new BorderPane();
        
        pane.setTop(taskbar);
        pane.setCenter(scroll);

        addProperties();
        
        
        return pane;
    }

    /**
     * Orders the properties depending on what the user has selected
     */
    private void orderProperties(Event event)
    {
        ComboBox box = (ComboBox) event.getSource();
        String sortOption = (String) box.getValue();
        if (sortOption.equals("Price")) {
            orderByPrice();
        }

        else if (sortOption.equals("Number of reviews")) {
            orderByReviews();
        }
        else if (sortOption.equals("Host Name")) {
            orderByHost();
        }
        update();
    }

    /**
     * Updates the property order
     */
    private void update()
    {
        propertyListings.getChildren().clear();
        addProperties();
    }

    /**
     * Displays the information about the properties
     */
    private void addProperties()
    {
        Label hostname = new Label("Host Name");
        hostname.getStyleClass().add("property_header");
        Label price = new Label("Price");
        price.getStyleClass().add("property_header");
        Label reviews = new Label("Reviews per month");
        reviews.getStyleClass().add("property_header");
        Label minNights = new Label("Minimum nights");
        minNights.getStyleClass().add("property_header");
        
        propertyListings.add(hostname, 0, 0);
        propertyListings.add(price, 1, 0);
        propertyListings.add(reviews, 2, 0);
        propertyListings.add(minNights, 3, 0);
        
        int count = 1;
        for (AirbnbListing listing : listings) {
            Label hostLabel = new Label(listing.getHost_name());
            hostLabel.setOnMouseClicked(this::displayDetails);
            hostLabel.setId(listing.toString());
            Label priceLabel = new Label("" + listing.getPrice());
            priceLabel.setOnMouseClicked(this::displayDetails);
            priceLabel.setId(listing.toString());
            Label reviewsLabel = new Label("" + listing.getReviewsPerMonth());
            reviewsLabel.setOnMouseClicked(this::displayDetails);
            reviewsLabel.setId(listing.toString());
            Label minNightsLabel = new Label("" + listing.getMinimumNights());
            minNightsLabel.setOnMouseClicked(this::displayDetails);
            minNightsLabel.setId(listing.toString());
            propertyListings.addRow(count, hostLabel, priceLabel, reviewsLabel, minNightsLabel);
            count++;
        }
    }

    /**
     * Orders the properties by price
     */
    private void orderByPrice()
    {
        for(int count = 0; count < listings.size(); count++) {
            for(int count1 = 0; count1 < listings.size() - 1; count1++) {
                if (listings.get(count1).getPrice() > listings.get(count1+1).getPrice()) {
                    AirbnbListing listing = listings.get(count1+1);
                    listings.set(count1+1, listings.get(count1));
                    listings.set(count1, listing);
                }
            }
        }
    }

    /**
     * Orders the properties by number of reviews
     */
    private void orderByReviews()
    {
        for(int count = 0; count < listings.size(); count++) {
            for(int count1 = 0; count1 < listings.size() - 1; count1++) {
                if (listings.get(count1).getNumberOfReviews() < listings.get(count1+1).getNumberOfReviews()) {
                    AirbnbListing listing = listings.get(count1+1);
                    listings.set(count1+1, listings.get(count1));
                    listings.set(count1, listing);
                }
            }
        }
    }

    /**
     * Orders the properties by host name
     */
    private void orderByHost()
    {
        for(int count = 0; count < listings.size(); count++) {
            for(int count1 = 0; count1 < listings.size() - 1; count1++) {
                boolean isAfter = listings.get(count1).getHost_name().toLowerCase().compareTo(listings.get(count1+1).getHost_name().toLowerCase()) > 0;
                if (isAfter) {
                    AirbnbListing listing = listings.get(count1+1);
                    listings.set(count1+1, listings.get(count1));
                    listings.set(count1, listing);
                }
            }
        }
    }

    /**
     * A new window that displays the details of the property
     */
    private void displayDetails(MouseEvent event)
    {
        //Get the property that was clicked
        Label text = (Label) event.getSource();
        property = dataLoader.getProperty(text.getId());
        
        PropertyDetails propertyWindow = new PropertyDetails(property, favouriter);
        
        for (Page page : getPages()) {
            propertyWindow.addPage(page);
        }
    }
}
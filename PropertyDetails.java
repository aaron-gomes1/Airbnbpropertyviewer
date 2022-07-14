import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.event.*;
import javafx.scene.image.*;

/**
 * The PropertyDetails class displays the details of a given AirbnbListing.
 *
 * @author  Immanuel Rajadurai, Maya Dejonge, Giorgio Grimesty, Aaron Gomes
 * @version 06/04/2021
 */
public class PropertyDetails extends PageUpdater
{
    private AirbnbListing property;
    
    private Favouriter favouriter;
    private Button favouriteButton;
    
    // stores the favourite icons
    private ImageView favourited;
    private ImageView notFavourited;

    /**
     * Constructor for objects of class PropertyDetails
     * 
     * @param property The property whose details are being shown
     * @param favouriter Contains the favourite properties
     */
    public PropertyDetails(AirbnbListing property, Favouriter favouriter)
    {
        this.property = property;
        this.favouriter = favouriter;
        
        favourited = new ImageView("heart-filled.png");
        favourited.setFitWidth(20);
        favourited.setFitHeight(20);
        
        notFavourited = new ImageView("heart-empty.png");
        notFavourited.setFitWidth(20);
        notFavourited.setFitHeight(20);
        
        createWindow();
    }
    
    /**
     * Creates a new window which displays the property details.
     */
    private void createWindow()
    {
        Stage stage = new Stage();
        BorderPane background = new BorderPane();
        
        background.getStyleClass().add("property_details");

        Button back = new Button("Back");
        back.setOnAction(e -> stage.close());

        favouriteButton = createFavouriteButton();
        favouriteButton.setOnAction(this::updateFavourites);
        favouriteButton.getStyleClass().add("favourite_button");
        
        HBox buttons = new HBox();
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        buttons.getChildren().addAll(back, region1, favouriteButton);

        background.setTop(buttons);

        GridPane grid = new GridPane();
        String[] details = property.getDetails();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);

        //Enter all of the details
        grid.addRow(0, new Label("Property Id:"), new Label(details[0]));
        grid.addRow(1, new Label("Description:"), new Label(details[1]));
        grid.addRow(2, new Label("Host ID:"), new Label(details[2]));
        grid.addRow(3, new Label("Host Name:"), new Label(details[3]));
        grid.addRow(4, new Label("Neighbourhood:"), new Label(details[4]));
        grid.addRow(5, new Label("Latitude:"), new Label(details[5]));
        grid.addRow(6, new Label("Longitude:"), new Label(details[6]));
        grid.addRow(7, new Label("Room Type:"), new Label(details[7]));
        grid.addRow(8, new Label("Price:"), new Label(details[8]));
        grid.addRow(9, new Label("Minimum Nights:"), new Label(details[9]));
        grid.addRow(10, new Label("Number of Reviews:"), new Label(details[10]));
        grid.addRow(11, new Label("Last Review:"), new Label(details[11]));
        grid.addRow(12, new Label("Reviews Per Month:"), new Label(details[12]));
        grid.addRow(13, new Label("Total Host Listings Count:"), new Label(details[13]));
        grid.addRow(14, new Label("Availability 365:"), new Label(details[14]));

        background.setCenter(grid);
        Scene scene = new Scene(background, 600, 400);
        scene.getStylesheets().add("main.css");

        stage.setTitle("Property Details");
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Creates the favourite button
     */
    private Button createFavouriteButton()
    {
        Button favButton = new Button();
        favButton.setGraphic(notFavourited);
        if(property.isFavourited())
        {
            favButton.setGraphic(favourited);
        }
        return favButton;
    }

    /**
     * Updates the favourite button when clicked
     */
    private void updateFavourites(ActionEvent event)
    {
        if(property.isFavourited())
        {
            unfavourite();
        }
        else
        {
            favourite();
        }
        updatePages();
    }
    
    /**
     * Sets a particular property as a favourite
     */
    private void favourite()
    {
        property.favourite();
        favouriter.addProperty(property);
        favouriteButton.setGraphic(favourited);
    }
    
     /**
     * Undoes the favourite method
     */
    private void unfavourite()
    {
        property.unfavourite();
        favouriter.removeProperty(property);
        favouriteButton.setGraphic(notFavourited);
    }
}

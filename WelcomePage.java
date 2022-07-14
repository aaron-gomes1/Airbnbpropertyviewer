import javafx.scene.layout.*;
import javafx.scene.control.*;

/**
 * The WelcomePage creates the first page of the application.
 *
 * @author  Immanuel Rajadurai, Maya Dejonge, Giorgio Grimesty, Aaron Gomes
 * @version 06/04/2021
 */
public class WelcomePage extends Page
{
    private Label priceDisplay;
    
    /**
     * Constructor for objects of class WelcomePage.
     */
    public WelcomePage(PriceSelector selector, AirbnbDataLoader dataLoader)
    {
        super(selector, dataLoader);
        
        setPane(new VBox());
        
        // Welcome message
        Label welcome1 = new Label("Welcome to the \nAirbnb Property Viewer");
        welcome1.getStyleClass().add("welcome1");
        Label welcome2 = new Label("Use the arrows below to flick between the map and statistics section.");
        Label welcome3 = new Label("On the top, the drop down prices will filter the property prices shown to you.");
        Label priceDisplay = selector.createPriceRangeLabel();
        
        VBox instructions = new VBox();
        instructions.getChildren().addAll(welcome2, welcome3, priceDisplay);
        instructions.getStyleClass().add("instructions");
        
        getPane().getChildren().addAll(welcome1, instructions);
        getPane().getStyleClass().add("welcome_page");
    }

    /**
     * Updates the page
     */
    public void update()
    {
    }
}

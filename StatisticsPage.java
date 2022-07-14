import javafx.scene.layout.*;
import javafx.scene.control.Label;

/**
 * The StatisticsPage class brings the whole statistics page together
 * and also updates the statistics if different boroughs are selected.
 *
 * @author  Immanuel Rajadurai, Maya Dejonge, Giorgio Grimesty, Aaron Gomes
 * @version 06/04/2021
 */
public class StatisticsPage extends Page
{
    private String currentID;

    private int currentFromPrice;
    private int currentToPrice;

    // page components
    private StatsPage statPage;
    private GridPane currentStatsPage;
    private BorderPane statsPageHolderPane;
    private Label description;

    private Favouriter favouriter;

    private MapPage map;

    /**
     * Constructor for StatisticsPage. Brings the whole stats page together.
     */
    public StatisticsPage(PriceSelector selector, AirbnbDataLoader dataLoader,Favouriter inputFavouriter, MapPage map)
    {
        super(selector, dataLoader);
        this.map = map;
        favouriter = inputFavouriter;
        currentID = null;
        createStatsPage();
    }

    /**
     * Creates the stats page.
     */
    public void createStatsPage() {
        statPage = new StatsPage(getDataLoader());
        statPage.setMap(map);
        currentStatsPage = statPage.setupStats(currentID, 100, 300);

        description = new Label("Please select a borough to view statistics.");

        description.getStyleClass().add("title");

        statsPageHolderPane = new BorderPane();

        VBox vBox = new VBox(description, statsPageHolderPane);
        vBox.setVgrow(statsPageHolderPane, Priority.ALWAYS);
        setPane(vBox);

        statsPageHolderPane.setMaxWidth(5000);
        statsPageHolderPane.setMaxHeight(5000);
        statsPageHolderPane.setCenter(currentStatsPage);
        statsPageHolderPane.getStyleClass().add("all_statistics");

    }

    /**
     * Updates the stats page
     */
    public void updateStatsPage() {

        statPage.clearStatPage();
        currentStatsPage = null;

        int fromPrice = getFromPrice();
        int toPrice = getToPrice();

        if(currentID != null)
        {
            description.setText("STATISTICS FOR " + currentID.toUpperCase());
        }

        currentStatsPage = statPage.setupStats(currentID, fromPrice/1000 , toPrice/1000);
        statsPageHolderPane.setCenter(currentStatsPage);
    }

    /**
     * Gets the from price
     */
    private int getFromPrice()
    {
        Object from = getSelector().getFromPrice();
        int fromPrice = 0;
        if (from != null) {
            fromPrice = (int) from;
        }
        return fromPrice;
    }

    /**
     * Gets the to price
     */
    private int getToPrice()
    {
        Object to = getSelector().getToPrice();
        int toPrice = 0;
        if (to != null) {
            toPrice = (int) to;
        }
        return toPrice;
    }

    /**
     * Updates the page
     */
    public void update()
    {
        updateStatsPage();

    }

    /**
     * Updates the page with a new id
     */
    public void update(String id)
    {
        currentID = id;
        update();
    }
}

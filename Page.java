import javafx.scene.layout.*;

/**
 * A Page which can be filled with anything.
 * 
 * @author  Immanuel Rajadurai, Maya Dejonge, Giorgio Grimesty, Aaron Gomes
 * @version 06/04/2021
 */
public abstract class Page
{
    private Pane pane;
    private PriceSelector selector;
    private AirbnbDataLoader dataLoader;

    /**
     * Constructor for page
     */
    public Page(PriceSelector selector, AirbnbDataLoader dataLoader)
    {
        this.selector = selector;
        this.dataLoader = dataLoader;
        pane = new Pane();
    }

    //Abstract method built upon by every page (subclasses)

    public abstract void update();

    /**
     * Returns the price selector.
     *
     * @return the price selector.
     */
    public PriceSelector getSelector()
    {
        return selector;
    }

    /**
     * Returns the data loader.
     *
     * @return the data loader.
     */
    public AirbnbDataLoader getDataLoader()
    {
        return dataLoader;
    }
    
    /**
     * Returns the pane.
     *
     * @return the pane.
     */
    public Pane getPane()
    {
        return pane;
    }

    public void setPane(Pane pane)
    {
        this.pane = pane;
    }
}

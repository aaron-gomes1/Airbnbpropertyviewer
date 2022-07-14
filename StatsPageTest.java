import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * The test class StatsPageTest which tests the functioning of 
 * The Stats Page
 *
 * @author  Immanuel Rajadurai, Maya Dejonge, Giorgio Grimesty, Aaron Gomez
 * @version 06/04/2021
 */
public class StatsPageTest
{
    /**
     * Default constructor for test class StatsPageTest
     */
    public StatsPageTest()
    {

    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {

    }

    /**
     * Test the initial construction of objects within the StatsPage and tests whether fields are populated 
     * correctly
     */
    @Test
    public void initial() {
        AirbnbDataLoader dataLoader = new AirbnbDataLoader();
        StatsPage statsPage = new StatsPage(dataLoader);

        assertNotNull(statsPage.getDataLoader());
        assertNotNull(statsPage.getStackPane());
        assertNotNull(statsPage.getStatBoxesList());
        assertNotNull(statsPage.getBoroughData());
        assertEquals(100,statsPage.getFromPrice());
        assertEquals(250,statsPage.getToPrice());
        assertNull(statsPage.getCurrentID());

    }
    
    /**
     * Tests whether the cheapest neighbouring borough is calculated
     */
    @Test
    public void testCalcCheapestNeighbouringBorough() {
        AirbnbDataLoader dataLoader = new AirbnbDataLoader();
        StatsPage statsPage = new StatsPage(dataLoader);
        
        
        
        PriceSelector selector = null;
        AirbnbDataLoader loader = new AirbnbDataLoader();
        Favouriter fav = new Favouriter();
        
        MapPage map = new MapPage(selector, loader, fav);
        
        statsPage.setMap(map);
        
        statsPage.setupStats("Croydon", 100, 250);
        assertEquals("Bromley", statsPage.calculateCheapestNeighbouringBorough());

    }
    
    /**
     * Tests whether the cheapest neighbouring borough is calculated
     */
    @Test
    public void testcalculatePropertiesInNeighbouringBoroughs() {
        AirbnbDataLoader dataLoader = new AirbnbDataLoader();
        StatsPage statsPage = new StatsPage(dataLoader);
               
        PriceSelector selector = null;
        AirbnbDataLoader loader = new AirbnbDataLoader();
        Favouriter fav = new Favouriter();
        
        MapPage map = new MapPage(selector, loader, fav);
        
        statsPage.setMap(map);
        
        statsPage.setupStats("Haringey", 100, 350);
        assertEquals(2510, statsPage.calculatePropertiesInNeighbouringBoroughs());

    }
    
    
     /**
     * Tests whether the available number of properties in the borough is being calculated properly
     */
    @Test
    public void testCalcAvailableProperties() {
        AirbnbDataLoader dataLoader = new AirbnbDataLoader();
        StatsPage statsPage = new StatsPage(dataLoader);
      
        statsPage.setupStats("Havering", 100, 350);
        assertEquals(17, statsPage.calculateAvailableProperties());

    }

    /**
     * Tests whether a stats Box is being built properly
     * By ensuring that a BorderPane is being returned and that it is not null
     */
    @Test
    public void testBuildStatsBox() {
        AirbnbDataLoader dataLoader = new AirbnbDataLoader();
        StatsPage statsPage = new StatsPage(dataLoader);

        assertNotNull(statsPage.buildStatBox("TestTitle1", "TestInfo1", "TestTitle2", "TestInfo2"));

    }
    
     /**
     * Tests whether the from price is being updated correctly
     */
    @Test
    public void testFromPrice() {
        AirbnbDataLoader dataLoader = new AirbnbDataLoader();
        StatsPage statsPage = new StatsPage(dataLoader);

        statsPage.setupStats("Croydon", 0, 250);
        
        assertEquals(0, statsPage.getFromPrice());
        
        statsPage.clearStatPage();
        statsPage.setupStats("Ealing", 100, 350);
        assertEquals(100, statsPage.getFromPrice());

    }
    
     /**
     * Tests whether the To price is being updated correctly
     */
    @Test
    public void testToPrice() {
        AirbnbDataLoader dataLoader = new AirbnbDataLoader();
        StatsPage statsPage = new StatsPage(dataLoader);

        statsPage.setupStats("Croydon", 0, 250);
        
        assertEquals(250, statsPage.getToPrice());
        
        statsPage.clearStatPage();
        statsPage.setupStats("Ealing", 100, 350);
        assertEquals(350, statsPage.getToPrice());

    }

    /**
     * Tests whether the cheapest borough is found by findMostExpensiveOrCheapestBorough() method
     */
    @Test
    public void testCheapestBorough() {
        AirbnbDataLoader dataLoader = new AirbnbDataLoader();
        StatsPage statsPage = new StatsPage(dataLoader);

        //Test for price range 0 - 250
        statsPage.setupStats("Croydon", 0, 250);

        assertEquals("Sutton", statsPage.getCurrentCheapestBorough());

        // Test for price range 0 - 350
        statsPage.clearStatPage();
        statsPage.setupStats("Croydon", 0, 350);

        assertEquals("Sutton", statsPage.getCurrentCheapestBorough());

        // Test for price range 0 - 450
        statsPage.clearStatPage();
        statsPage.setupStats("Croydon", 0, 450);

        assertEquals("Sutton", statsPage.getCurrentCheapestBorough());

        // Test for price range 100 - 450
        statsPage.clearStatPage();
        statsPage.setupStats("Croydon", 100, 450);

        assertEquals("Bexley", statsPage.getCurrentCheapestBorough());

        // Test for price range 150 - 450
        statsPage.clearStatPage();
        statsPage.setupStats("Croydon", 150, 450);

        assertEquals("Bexley", statsPage.getCurrentCheapestBorough());

        // Test for price range 200 - 450
        statsPage.clearStatPage();
        statsPage.setupStats("Croydon", 200, 450);

        assertEquals("Barking and Dagenham", statsPage.getCurrentCheapestBorough());

    }

    /**
     * Tests whether the most expensive borough is found by findMostExpensiveOrCheapestBorough() method
     */
    @Test
    public void testMostExpensiveBorough() {
        AirbnbDataLoader dataLoader = new AirbnbDataLoader();
        StatsPage statsPage = new StatsPage(dataLoader);

        //Test for price range 0 - 250
        statsPage.setupStats("Croydon", 0, 250);

        assertEquals("City of London", statsPage.getCurrentMostExpensiveBorough());

        // Test for price range 0 - 350
        statsPage.clearStatPage();
        statsPage.setupStats("Croydon", 0, 350);

        assertEquals("City of London", statsPage.getCurrentMostExpensiveBorough());

        // Test for price range 0 - 450
        statsPage.clearStatPage();
        statsPage.setupStats("Croydon", 0, 450);

        assertEquals("Richmond upon Thames", statsPage.getCurrentMostExpensiveBorough());

        // Test for price range 100 - 450
        statsPage.clearStatPage();
        statsPage.setupStats("Croydon", 100, 450);

        assertEquals("Bexley", statsPage.getCurrentCheapestBorough());

        // Test for price range 150 - 450
        statsPage.clearStatPage();
        statsPage.setupStats("Croydon", 150, 450);

        assertEquals("Richmond upon Thames", statsPage.getCurrentMostExpensiveBorough());

        // Test for price range 200 - 450
        statsPage.clearStatPage();
        statsPage.setupStats("Croydon", 200, 250);

        assertEquals("Redbridge", statsPage.getCurrentMostExpensiveBorough());

    }

    /**
     * Tests whether the averate Number of Reviews per property is being calculated correctly
     * Tests against random price ranges and random borough selections
     */
    @Test
    public void testAveragNoReviews() {
        AirbnbDataLoader dataLoader = new AirbnbDataLoader();
        StatsPage statsPage = new StatsPage(dataLoader);

        statsPage.setupStats("Havering", 0, 250);
        assertEquals(6, statsPage.getAvNoReviewsForBorough());

        statsPage.clearStatPage();
        statsPage.setupStats("Newham", 100, 250);
        assertEquals(8, statsPage.getAvNoReviewsForBorough());

        statsPage.clearStatPage();
        statsPage.setupStats("Croydon", 100, 250);
        assertEquals(5, statsPage.getAvNoReviewsForBorough());

        statsPage.clearStatPage();
        statsPage.setupStats("Ealing", 0, 250);
        assertEquals(11, statsPage.getAvNoReviewsForBorough());
    }

    /**
     *  Tests whether the total Number of Properties is being calculated correctly
     * Tests against random price ranges and random borough selections
     */
    @Test
    public void testBoroughPropSize() {
        AirbnbDataLoader dataLoader = new AirbnbDataLoader();
        StatsPage statsPage = new StatsPage(dataLoader);

        statsPage.setupStats("Havering", 0, 250);
        assertEquals(97, statsPage.getNoOfPropertiesInBorough());

        statsPage.clearStatPage();
        statsPage.setupStats("Newham", 100, 250);
        assertEquals(217, statsPage.getNoOfPropertiesInBorough());

        statsPage.clearStatPage();
        statsPage.setupStats("Croydon", 100, 250);
        assertEquals(47, statsPage.getNoOfPropertiesInBorough());

        statsPage.clearStatPage();
        statsPage.setupStats("Ealing", 0, 250);
        assertEquals(973, statsPage.getNoOfPropertiesInBorough());

    }

    /**
     * Tests whether the total Number of Proper Homes is being calculated correctly
     * Tests against random price ranges and random borough selections
     */
    @Test
    public void testTotalNoProperHomes() {
        AirbnbDataLoader dataLoader = new AirbnbDataLoader();
        StatsPage statsPage = new StatsPage(dataLoader);

        statsPage.setupStats("Havering", 0, 250);
        assertEquals(34, statsPage.getTotalProperHomes());

        statsPage.clearStatPage();
        statsPage.setupStats("Newham", 100, 250);
        assertEquals(197, statsPage.getTotalProperHomes());

        statsPage.clearStatPage();
        statsPage.setupStats("Croydon", 100, 250);
        assertEquals(42, statsPage.getTotalProperHomes());

        statsPage.clearStatPage();
        statsPage.setupStats("Ealing", 0, 250);
        assertEquals(384, statsPage.getTotalProperHomes());

    }

    /**
     *  Tests whether the total number of rooms in a borough is being calculated correctly
     * Tests against random price ranges and random borough selections
     */
    /*
    @Test
    public void testNoRoomsInBorough() {
        AirbnbDataLoader dataLoader = new AirbnbDataLoader();
        StatsPage statsPage = new StatsPage(dataLoader);

        statsPage.setupStats("Havering", 0, 250);
        assertEquals(63, statsPage.getNoRoomsInBorough());

        statsPage.clearStatPage();
        statsPage.setupStats("Newham", 100, 250);
        assertEquals(20, statsPage.getNoRoomsInBorough());

        statsPage.clearStatPage();
        statsPage.setupStats("Croydon", 100, 250);
        assertEquals(5, statsPage.getNoRoomsInBorough());

        statsPage.clearStatPage();
        statsPage.setupStats("Ealing", 0, 250);
        assertEquals(589, statsPage.getNoRoomsInBorough());

    }
    */

    /**
     *  Tests whether average number of minimum nights per borough is being calculated correctly
     * Tests against random price ranges and random borough selections
     */
    @Test
    public void testAvgMinNightsInBorough() {
        AirbnbDataLoader dataLoader = new AirbnbDataLoader();
        StatsPage statsPage = new StatsPage(dataLoader);

        statsPage.setupStats("Havering", 0, 250);
        assertEquals(2.0, statsPage.getAvgMinNightsInBorough());

        statsPage.clearStatPage();
        statsPage.setupStats("Newham", 100, 250);
        assertEquals(3.0, statsPage.getAvgMinNightsInBorough());

        statsPage.clearStatPage();
        statsPage.setupStats("Croydon", 100, 250);
        assertEquals(2.0, statsPage.getAvgMinNightsInBorough());

        statsPage.clearStatPage();
        statsPage.setupStats("Ealing", 0, 250);
        assertEquals(2.0, statsPage.getAvgMinNightsInBorough());

    }

    /**
     * Tests whether the ID of the chosen borough is being updated properly
     * Tests against random price ranges and random borough selections
     */
    @Test
    public void testIDUpdate() {
        AirbnbDataLoader dataLoader = new AirbnbDataLoader();
        StatsPage statsPage = new StatsPage(dataLoader);

        statsPage.setupStats("Havering", 0, 250);
        assertEquals("Havering", statsPage.getCurrentID());

        statsPage.clearStatPage();
        statsPage.setupStats("Newham", 100, 250);
        assertEquals("Newham", statsPage.getCurrentID());

        statsPage.clearStatPage();
        statsPage.setupStats("Croydon", 100, 250);
        assertEquals("Croydon", statsPage.getCurrentID());

        statsPage.clearStatPage();
        statsPage.setupStats("Ealing", 0, 250);
        assertEquals("Ealing", statsPage.getCurrentID());

    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }

    
}



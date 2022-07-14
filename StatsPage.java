import javafx.geometry.Insets;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * The class StatsPage calculates statistics for given inputs and
 * displays them in stat boxes.
 *
 * @author  Immanuel Rajadurai, Maya Dejonge, Giorgio Grimesty, Aaron Gomes
 * @version 06/04/2021
 */
public class StatsPage
{
    private MapPage map;
    
    // tools
    private PriceSelector selector;
    private AirbnbDataLoader dataLoader;
    private Navigator navigator;
    private StackPane sp;
    
    // inputs
    private String currentIDSelected;
    private int currentPriceFrom;
    private int currentPriceTo;
    
    private ArrayList<AirbnbListing> boroughData;
    private ArrayList<AirbnbListing> allPropertyData;
    
    // statistics
    private int avNoReviewsForBorough;
    private int boroughPropSize;
    private int totalProperHomes;
    private int availableProperties;
    private int propertiesInNeighbouringBoroughs;
    private String cheapestNeighbouringBorough;
    private double avgMinNightsInBorough; 
    private String mostExpensiveBorough; 
    private String mostCheapestBorough;
    
    // page components
    private ArrayList<BorderPane> statBoxes;
    GridPane currentGrid;
    
    private String boroughName;

    /**
     * Constructor for objects of class StatsPage
     */
    public StatsPage(AirbnbDataLoader dataLoader)
    {
        this.dataLoader = dataLoader;

        sp = new StackPane();
        statBoxes = new ArrayList<>();
        currentIDSelected = null;
        boroughData = new  ArrayList<>();

        currentPriceFrom = 100;
        currentPriceTo = 250;

    }
    
    /**
     * @param map Sets the map for the cheapest neighbouring borough stat
     */
    public void setMap(MapPage map)
    {
        this.map = map;
    }

    
    /**
     * @return the selected from price
     */
    public int getFromPrice() {
      return currentPriceFrom;   
    }
    
    /**
     * @return the selected to price
     */
    public int getToPrice() {
      return currentPriceTo;   
    }
    
    /**
     * @return the avNoReviewsForBorough
     */
    public int getAvNoReviewsForBorough() {
      return avNoReviewsForBorough;   
    }
    
    /**
     * @return the number of propertiesInNeighbouringBoroughs
     */
    public int getPropertiesInNeighbouringBoroughs() {
        return propertiesInNeighbouringBoroughs;
    }
    
    /**
     * @return the number of Properties in borough
     */
    public int getNoOfPropertiesInBorough() {
        
      return boroughPropSize;   
    }
    
    /**
     * @return the cheapest neighbouring property
     */
    public String getCheapestNeighbouringProperty() {
        return cheapestNeighbouringBorough;
    }
    
    
    /**
     * @return total complete homes
     */
    public int getTotalProperHomes() {
      return totalProperHomes;  
    }
    
    /**
     * @return number available properties
     */
    public int getNoAvailableProperties() {
      return availableProperties;   
    }
    
    /**
     * @return avgMinNightsInBorough
     */
    public double getAvgMinNightsInBorough() {
        return avgMinNightsInBorough;
    }
    
    /**
     * @return the stack pane 
     */
    public StackPane getStackPane() {
        return sp;
    }
    
    /**
     * @return ArrayList of statBoxes
     */
    public ArrayList getStatBoxesList() {      
        return statBoxes;
    }
    
    /**
     * @return ArrayList of boroughData 
     */
    public ArrayList getBoroughData() {      
        return boroughData;
    }
    
    /**
     * @return the AirbnbDataLoader 
     */
    public AirbnbDataLoader getDataLoader() {
        return dataLoader;
    }
    
    /**
     * @return the current id selected 
     */
    public String getCurrentID() {
        return currentIDSelected;
    }
    
    /**
     * @return the mostCheapestBorough
     */
    public String getCurrentCheapestBorough() {
        return mostCheapestBorough;
    }
    
    /**
     * @return the mostExpensiveBorough 
     */
    public String getCurrentMostExpensiveBorough() {
        return mostExpensiveBorough;
    }
     
    /**
     * Calculates all the necessary data for stats Page.
     */
    private void calculations() {

        boroughData = dataLoader.getPropertiesInNeighbourhood(currentIDSelected, (int) currentPriceFrom, (int) currentPriceTo);
        boroughPropSize = boroughData.size();
        int reviewTotal = 0;
        totalProperHomes = 0;
        
        // calculate total available properties in borough.
        availableProperties = calculateAvailableProperties();
        
        // calculate average no. reviews per property
        for (AirbnbListing listing : boroughData) {

            reviewTotal+=listing.getNumberOfReviews();

        }

        // calculate total number of home/apartments
        for (AirbnbListing listing : boroughData) {

            if (listing.getRoom_type().equals("Entire home/apt")) {

                totalProperHomes++;
            }

        }

        mostExpensiveBorough = findMostExpensiveOrCheapestBorough(true);
        mostCheapestBorough = findMostExpensiveOrCheapestBorough(false);

        avNoReviewsForBorough = 0;
        if (boroughPropSize != 0) {
            avNoReviewsForBorough = reviewTotal/boroughPropSize;
        }
        
        //Calculates the number of available properties in the neighbouring boroughs
        propertiesInNeighbouringBoroughs = calculatePropertiesInNeighbouringBoroughs();
        
        //Calculates the cheapest borough that neighbours the current borough selected
        cheapestNeighbouringBorough = calculateCheapestNeighbouringBorough();     

        //calculate avgMinNightsInBorough
        int totalMinNights = 0;
        for (AirbnbListing listing : boroughData) {

            totalMinNights+=listing.getMinimumNights();

        }

        avgMinNightsInBorough = 0;
        if (boroughData.size() != 0) {
            avgMinNightsInBorough = totalMinNights/boroughData.size();
        }
    }

    /**
     * Calculates the most expensive or cheapest borough
     * @return String
     */
    public String findMostExpensiveOrCheapestBorough(boolean findMostExpensive) {

        String currentMostExpensiveOrCheapestBorough = "";
        Set<String> allBoroughs = new HashSet<>();
        allBoroughs = dataLoader.getAllBoroughs();
        int currentHighestOrLowestAveragePrice = 0;

        if (!findMostExpensive) {

            currentHighestOrLowestAveragePrice = Integer.MAX_VALUE;
        } 

        for (String borough : allBoroughs) {

            ArrayList<AirbnbListing> allPropertiesInBorough = new ArrayList<>();

            allPropertiesInBorough = dataLoader.getPropertiesInNeighbourhood(borough, (int) currentPriceFrom, (int) currentPriceTo);

            int totalPrice = 0;
            for (AirbnbListing listing : allPropertiesInBorough) {

                int propertyPrice = listing.getMinimumNights() * listing.getPrice();

                totalPrice+=propertyPrice;
            }

            int averagePriceForThisBorough = 0;
            if (totalPrice != 0 && allPropertiesInBorough.size() != 0) {
                averagePriceForThisBorough = totalPrice/allPropertiesInBorough.size();
            }

            if (findMostExpensive) {
                if (averagePriceForThisBorough >= currentHighestOrLowestAveragePrice) {                
                    currentHighestOrLowestAveragePrice = averagePriceForThisBorough;
                    currentMostExpensiveOrCheapestBorough = borough;               
                }
            } else {
                if (averagePriceForThisBorough <= currentHighestOrLowestAveragePrice) {                
                    currentHighestOrLowestAveragePrice = averagePriceForThisBorough;
                    currentMostExpensiveOrCheapestBorough = borough;               
                }
            }
        }
        return currentMostExpensiveOrCheapestBorough;
    }
    
    /**
     * Calculates the number of available properties in neighbouring boroughs
     * @return the number of properties
     */
    public int calculatePropertiesInNeighbouringBoroughs() {
        int numberOfProperties = 0;
        ArrayList<AirbnbListing> allPropertiesInNeighbouringBoroughs = new ArrayList<>();
        ArrayList<String> neighbouringBoroughs = new ArrayList<String>();
        try {
            neighbouringBoroughs = map.getNeighbouringBoroughs(currentIDSelected);
        } catch (NullPointerException e) {
        }
        for (String borough : neighbouringBoroughs) {
            allPropertiesInNeighbouringBoroughs = dataLoader.getPropertiesInNeighbourhood(borough, (int) currentPriceFrom, (int) currentPriceTo);
            for (AirbnbListing property : allPropertiesInNeighbouringBoroughs) {
                if (property.getAvailability365() != 0) {
                    numberOfProperties+=1;
                }
            }
        }
        return numberOfProperties;
    }
    
    /**
     * Calculates the cheapest borough in the neighbouring boroughs
     * @return the cheapest borough
     */
    public String calculateCheapestNeighbouringBorough() {
        String currentCheapestBorough = "";
        int currentLowestAveragePrice = 0;
        
        ArrayList<String> neighbouringBoroughs = new ArrayList<String>();
        try {
            neighbouringBoroughs = map.getNeighbouringBoroughs(currentIDSelected);

        } catch (NullPointerException e) {
            
        }
        
        for (String borough : neighbouringBoroughs) {

            ArrayList<AirbnbListing> allPropertiesInBorough = new ArrayList<>();

            allPropertiesInBorough = dataLoader.getPropertiesInNeighbourhood(borough, (int) currentPriceFrom, (int) currentPriceTo);

            int totalPrice = 0;
            for (AirbnbListing listing : allPropertiesInBorough) {

                int propertyPrice = listing.getMinimumNights() * listing.getPrice();

                totalPrice+=propertyPrice;
            }

            int averagePriceForThisBorough = 0;
            if (totalPrice != 0 && allPropertiesInBorough.size() != 0) {
                averagePriceForThisBorough = totalPrice/allPropertiesInBorough.size();
            }

            if (averagePriceForThisBorough >= currentLowestAveragePrice) {                
                currentLowestAveragePrice = averagePriceForThisBorough;
                currentCheapestBorough = borough;               
            }
        }
        return currentCheapestBorough;
    }
    
    /**
     * @return int number of properties available
     */
    public int calculateAvailableProperties()
    {
        int numberOfProperties = 0;
        for (AirbnbListing listing : boroughData) {
            if (listing.getAvailability365() > 0) {
                numberOfProperties++;
            }
        }
        return numberOfProperties;
    }

    /**
     * Sets up stats panel
     * @return A gridpane containing stat boxes.
     */
    public GridPane setupStats(String idSelected, int fromPrice, int toPrice) {

        currentIDSelected = idSelected;
        currentPriceFrom =  (int) fromPrice;
        currentPriceTo = (int) toPrice;

        if (idSelected != null) {
            calculations();
        }

        GridPane grid = new GridPane();
        grid.setMaxHeight(5000);
        grid.setMaxWidth(5000);

        BorderPane stat1 = buildStatBox("Cheapest \nNeighbouring \nBorough" , cheapestNeighbouringBorough, "Available \nProperties in \n" + currentIDSelected,"" + availableProperties);
        BorderPane stat2 = buildStatBox("Average Reviews\n per Property:" , "" + avNoReviewsForBorough, " Total Number of \n Complete Homes", "" + totalProperHomes);
        BorderPane stat3 = buildStatBox("Most Expensive \nBorough" , "" + mostExpensiveBorough, "Cheapest \nBorough", "" + mostCheapestBorough);
        BorderPane stat4 = buildStatBox("Total Available \nProperties in \nNeighbouring \nBoroughs" , "" + propertiesInNeighbouringBoroughs, "Average \nminimum nights", "" + avgMinNightsInBorough);
        
        grid.add(stat1,0,0);
        grid.add(stat2,1,0);
        grid.add(stat3,0,1);
        grid.add(stat4,1,1);
        ColumnConstraints column = new ColumnConstraints(250);
        grid.getColumnConstraints().add(column);
        grid.getColumnConstraints().add(column);
        
        grid.setPadding(new Insets(10,10,10,10));
        currentGrid = grid;
        return currentGrid;
    }
    
    /**
     * builds individual stat boxes.
     * @return BorderPane
     */
    public BorderPane buildStatBox(String title1, String info1, String title2, String info2) {

        //creates a variable for current stat boxes for testing purposes
        int currentNumberOfStatBoxes = statBoxes.size();

        StatsBox stat = new StatsBox();
        stat.addStat(title1, info1);
        stat.addStat(title2, info2);
        stat.setUpStatsBox();

        BorderPane statBox = (BorderPane) stat.show();

        statBoxes.add(statBox);

        return statBox;
    }
    
    /**
     * clears stats page
     */
    public void clearStatPage() {
        currentGrid.getChildren().clear();
        currentGrid = null;
        statBoxes.clear();

    }
    
    /**
     * @return the current Grid
     */
    public GridPane getCurrentGrid() {
        return currentGrid;
    }
}
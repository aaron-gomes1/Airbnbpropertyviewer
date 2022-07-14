import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;

/**
 * The MapPage is the second Panel of the AirBnB London Data Set Loader. It displays a map of London
 * with clickable boroughs, which displays the relevant data.
 *
 * @author  Immanuel Rajadurai, Maya Dejonge, Giorgio Grimesty, Aaron Gomes
 * @version 06/04/2021
 */
public class MapPage extends Page
{
    private ArrayList<MapTile> boroughs;
    private ArrayList<Page> updatePages;
    private Favouriter favouriter;
    private MapTile[][] mapArray;

    /**
     * Constructor for objects of class MapPage
     */
    public MapPage(PriceSelector priceSelector, AirbnbDataLoader dataLoader, Favouriter favouriter)
    {
        super(priceSelector, dataLoader);
        this.favouriter = favouriter;
        mapArray = new MapTile[7][14];
        updatePages = new ArrayList<>();
        setupMap();
    }

    /**
     * Sets up the map for panel 2
     */
    public void setupMap()
    {
        boroughs = new ArrayList<>();
        
        Label offset1 = new Label("");
        Label offset2 = new Label("");
        Label offset3 = new Label("");
        Label offset4 = new Label("");
        Label offset5 = new Label("");
        Label offset6 = new Label("");
        Label offset7 = new Label("");
        
        int offX = 50;
        
        //Positions the rows of the map in the right place
        offset1.setPadding(new Insets(0, offX + 130, 0, offX + 140));        
        offset2.setPadding(new Insets(0, offX + 80, 0, offX + 80));
        offset3.setPadding(new Insets(0, offX + 20, 0, offX + 20));
        offset4.setPadding(new Insets(0, offX + 0, 0, offX + 0));
        offset5.setPadding(new Insets(0, offX + 20, 0, offX + 20));
        offset6.setPadding(new Insets(0, offX + 40, 0, offX + 40));
        offset7.setPadding(new Insets(0, offX + 60, 0, offX + 60));
        
        StackPane enfiStack = addMapTile("enfi", "Enfield", 7, 0);
        
        StackPane barnStack = addMapTile("barn", "Barnet", 4, 1);
        StackPane hrgyStack = addMapTile("hrgy", "Haringey", 6, 1);
        StackPane waltStack = addMapTile("walt", "Waltham Forest", 8, 1);

        StackPane hrrwStack = addMapTile("hrrw", "Harrow", 1, 2);
        StackPane brenStack = addMapTile("bren", "Brent", 3, 2);
        StackPane camdStack = addMapTile("camd", "Camden", 5, 2);
        StackPane isliStack = addMapTile("isli", "Islington", 7, 2);
        StackPane hackStack = addMapTile("hack", "Hackney", 9, 2);
        StackPane redbStack = addMapTile("redb", "Redbridge", 11, 2);
        StackPane haveStack = addMapTile("have", "Havering", 13, 2);
        
        StackPane hillStack = addMapTile("hill", "Hillingdon", 0, 3);
        StackPane ealiStack = addMapTile("eali", "Ealing", 2, 3);
        StackPane kensStack = addMapTile("kens", "Kensington and Chelsea", 4, 3);
        StackPane wstmStack = addMapTile("wstm", "Westminster", 6, 3);
        StackPane towhStack = addMapTile("towh", "Tower Hamlets", 8, 3);
        StackPane newhStack = addMapTile("newh", "Newham", 10, 3);
        StackPane barkStack = addMapTile("bark", "Barking and Dagenham", 12, 3);

        StackPane hounStack = addMapTile("houn", "Hounslow", 1, 4);
        StackPane hammStack = addMapTile("hamm", "Hammersmith and Fulham", 3, 4);
        StackPane wandStack = addMapTile("wand", "Wandsworth", 5, 4);
        StackPane cityStack = addMapTile("city", "City of London", 7, 4);
        StackPane gwchStack = addMapTile("gwch", "Greenwich", 9, 4);
        StackPane bexlStack = addMapTile("bexl", "Bexley", 11, 4);
        
        StackPane richStack = addMapTile("rich", "Richmond upon Thames", 2, 5);
        StackPane mertStack = addMapTile("mert", "Merton", 4, 5);
        StackPane lambStack = addMapTile("lamb", "Lambeth", 6, 5);
        StackPane sthwStack = addMapTile("sthw", "Southwark", 8, 5);
        StackPane lewsStack = addMapTile("lews", "Lewisham", 10, 5);
        
        StackPane kingStack = addMapTile("king", "Kingston upon Thames", 3, 6);
        StackPane suttStack = addMapTile("sutt", "Sutton", 5, 6);
        StackPane croyStack = addMapTile("croy", "Croydon", 7, 6);
        StackPane bromStack = addMapTile("brom", "Bromley", 9, 6);
        
        onBoroughClicked();
        
        //Creates the rows for the map
        HBox row1 = new HBox(offset1, enfiStack);
        HBox row2 = new HBox(offset2, barnStack, hrgyStack, waltStack);
        HBox row3 = new HBox(offset3, hrrwStack, brenStack, camdStack, isliStack, hackStack, redbStack, haveStack);
        HBox row4 = new HBox(offset4, hillStack, ealiStack, kensStack, wstmStack, towhStack, newhStack, barkStack);
        HBox row5 = new HBox(offset5, hounStack, hammStack, wandStack, cityStack, gwchStack, bexlStack);
        HBox row6 = new HBox(offset6, richStack, mertStack, lambStack, sthwStack, lewsStack);
        HBox row7 = new HBox(offset7, kingStack, suttStack, croyStack, bromStack);
        
        row1.setSpacing(4);
        row2.setSpacing(4);
        row3.setSpacing(4);
        row4.setSpacing(4);
        row5.setSpacing(4);
        row6.setSpacing(4);
        row7.setSpacing(4);
        
        //Used to contain all of the map rows
        setPane( new VBox(row1, row2, row3, row4, row5, row6, row7));
        getPane().getStyleClass().add("boroughs");
    }
    
    /**
     * Adds a maptile to the map
     */
    private StackPane addMapTile(String filename, String boroughName, int x, int y)
    {
        MapTile mapTile = new MapTile(filename, boroughName, getDataLoader(), getSelector(), favouriter);
        StackPane stack = mapTile.getStack();
        boroughs.add(mapTile);
        try {
            mapArray[y][x] = mapTile;
        } catch (ArrayIndexOutOfBoundsException e) {}
        return stack;
    }

    /**
     * Updates the page
     */
    public void update()
    {
        updateMapTiles();
    }
    
    /**
     * Updates the number of properties of each borough
     */
    public void updateMapTiles()
    {
        for (MapTile borough : boroughs) {
            borough.updateNoOfProperties();
        }
    }
    
    /**
     * Sets the page to be updated by the map tiles
     */
    public void addUpdatePage(Page page)
    {
        updatePages.add(page);
        for (MapTile borough : boroughs) {
            borough.addPage(page);
        }
    }
    
    /**
     * Sets the stats page to be updated when borough is clicked
     */
    public void onBoroughClicked()
    {
        for (MapTile borough : boroughs) {
            borough.addPage(this);
        }
    }
    
    /**
     * @param boroughName The current borough selected
     * @return Gets the neighbouring boroughs
     */
    public ArrayList<String> getNeighbouringBoroughs(String boroughName)
    {        
        int xpos = 0;
        int ypos = 0;
        ArrayList<String> neighbouringBoroughs = new ArrayList<>();
        
        // Goes through the array and gets the position of the borough
        for(int y = 0; y < mapArray.length; y++) {
            MapTile[] row = mapArray[y];
            for (int x = 0; x < row.length; x++) {
                if (row[x] != null) {
                    if (row[x].getBoroughName().equals(boroughName)) { 
                        xpos = x;
                        ypos = y;
                    }
                }
            }
        }
        
        addNeighbouringBorough(xpos-1, ypos-1, neighbouringBoroughs);
        addNeighbouringBorough(xpos-1, ypos+1, neighbouringBoroughs);
        addNeighbouringBorough(xpos+1, ypos-1, neighbouringBoroughs);
        addNeighbouringBorough(xpos+1, ypos+1, neighbouringBoroughs);
        addNeighbouringBorough(xpos+2, ypos, neighbouringBoroughs);
        addNeighbouringBorough(xpos-2, ypos, neighbouringBoroughs);
        
        return neighbouringBoroughs;
    }
    
    /**
     * Adds a neighbouring borough
     */
    private void addNeighbouringBorough(int x, int y, ArrayList<String> neighbouringBoroughs)
    {
        try {
            MapTile tile = mapArray[y][x];
            if (tile != null) {
                neighbouringBoroughs.add(tile.getBoroughName());
            }
        } catch(ArrayIndexOutOfBoundsException e) {}
    }
}
import java.util.HashSet;

/**
 * The Favouriter class allows properties to be favourited by the user.
 *
 * @author  Immanuel Rajadurai, Maya Dejonge, Giorgio Grimesty, Aaron Gomes
 * @version 06/04/2021
 */
public class Favouriter
{
    private HashSet<AirbnbListing> favourites;

    /**
     * Constructor for objects of class Favouriter
     */
    public Favouriter()
    {
        favourites = new HashSet();
    }

    /**
     * Adds a property to the list of favourites.
     */
    public void addProperty(AirbnbListing listing)
    {
        favourites.add(listing);
    }
    
    /**
     * Removes a property from the list of favourites.
     */
    public void removeProperty(AirbnbListing id)
    {
        favourites.remove(id);
    }
    
    /**
     * @returns hashSet of favourite listings.
     */
    public HashSet getListings()
    {
        return favourites;
    }
}

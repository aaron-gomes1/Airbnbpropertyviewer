import java.util.ArrayList;

/**
 * A class that updates the pages when certain events occur.
 *
 * @author  Immanuel Rajadurai, Maya Dejonge, Giorgio Grimesty, Aaron Gomes
 * @version 06/04/2021
 */
public class PageUpdater
{
    //The pages that get updated
    private ArrayList<Page> pages;
    
    /**
     * Constructor for PageUpdater
     */
    public PageUpdater()
    {
        pages = new ArrayList<>();
    }
    
    /**
     * Gets all the pages
     */
    public ArrayList<Page> getPages()
    {
        return pages;
    }
    
    /**
     * Adds a page to the pages to be updated
     */
    public void addPage(Page page)
    {
        pages.add(page);
    }
    
    /**
     * Updates each page
     */
    public void updatePages()
    {
        for (Page page : pages) {
            page.update();
        }
    }
}

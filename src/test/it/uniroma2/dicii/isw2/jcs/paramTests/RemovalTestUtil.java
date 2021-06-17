package it.uniroma2.dicii.isw2.jcs.paramTests;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collection;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;



/**
 * Simple methods to be run by active test suites that test removal.
 *
 */
@RunWith(Parameterized.class)
public class RemovalTestUtil
{

    /**
     * Constructor for the TestSimpleLoad object
     *
     * @param testName
     *            
     */
    
    private JCS jcs;
    private int startParam;
    private int endParam;
    private Boolean checkParam;
    
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { 0, 5, false },
                { 1, 10, false }
        });
    }
    
    public RemovalTestUtil(int start, int end, Boolean check) {
    	this.startParam = start;
    	this.endParam = end;
    	this.checkParam = check;
    }
    
    /**
     * Inizializza una cache vuota             
     * @throws CacheException 
     */
    private void configureJCS() throws CacheException {
        
    	jcs = JCS.getInstance( "testCache1" );

    }
    
    @Test
    public void runRemovalTests() throws Exception {
    	runTestPutThenRemoveCategorical(startParam, endParam);
    	runTestPutInRange(startParam, endParam);
    	runTestGetInRange(startParam, endParam, checkParam);
    }

    /**
     * Adds elements in the range specified and then removes them using the
     * categorical or substring removal method.
     *
     * @param start
     * @param end
     *
     * @exception Exception
     *              
     */
    public void runTestPutThenRemoveCategorical( int start, int end )
        throws Exception
    {
    	configureJCS();
    	
    	for ( int i = start; i <= end; i++ )
        {
            jcs.put( i + ":key", "data" + i );
        }


        for ( int i = end; i >= start; i-- )
        {
            String res = (String) jcs.get( i + ":key" );
            if ( res == null )
            {
                assertNotNull( "[" + i + ":key] should not be null", res );
            }
        }
        System.out.println( "Confirmed that " + ( end - start ) + " items could be found" );

        for ( int i = start; i <= end; i++ )
        {
            jcs.remove( i + ":" );
            assertNull( jcs.get( i + ":key" ) );
        }
        System.out.println( "Confirmed that " + ( end - start ) + " items were removed" );

        System.out.println( jcs.getStats() );

    }

    /**
     * Put items in the cache in this key range. Can be used to verify that
     * concurrent operations are not effected by things like hierchical removal.
     *
     * @param start
     *            int
     * @param end
     *            int
     * @throws Exception
     */
    public void runTestPutInRange( int start, int end )
        throws Exception
    {
        configureJCS();
        
        for ( int i = start; i <= end; i++ )
        {
            jcs.put( i + ":key", "data" + i );
        }


        for ( int i = end; i >= start; i-- )
        {
            String res = (String) jcs.get( i + ":key" );
            if ( res == null )
            {
                assertNotNull( "[" + i + ":key] should not be null", res );
            }
        }

    }

    /**
     * Just get from start to end.
     *
     * @param start
     *            int
     * @param end
     *            int
     * @param check
     *            boolean -- check to see if the items are in the cache.
     * @throws Exception
     */
    public void runTestGetInRange( int start, int end, boolean check )
        throws Exception
    {
        configureJCS();

        // don't care if they are found
        for ( int i = end; i >= start; i-- )
        {
            String res = (String) jcs.get( i + ":key" );
            if ( check && res == null )
            {
                assertNotNull( "[" + i + ":key] should not be null", res );
            }

        }

    }

}

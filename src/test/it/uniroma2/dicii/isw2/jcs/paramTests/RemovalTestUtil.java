package it.uniroma2.dicii.isw2.jcs.paramTests;

import static org.junit.Assert.assertNull;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;

import static org.junit.Assert.assertNotNull;

/**
 * Simple methods to be run by active test suites that test removal.
 *
 */
public class RemovalTestUtil
{

    /**
     * Constructor for the TestSimpleLoad object
     *
     * @param testName
     *            
     */
    
    private JCS jcs;
    
    /**
     * Inizializza una cache con end-start elementi 
     *
     * @param start
     * @param end
     * @throws CacheException 
     *              
     */
    private void configureJCSData( int start, int end) throws CacheException {
        
    	jcs = JCS.getInstance( "testCache1" );

        for ( int i = start; i <= end; i++ )
        {
            jcs.put( i + ":key", "data" + i );
        }

    }
    
    /**
     * Inizializza una cache vuota             
     * @throws CacheException 
     */
    private void configureJCSNoData() throws CacheException {
        
    	jcs = JCS.getInstance( "testCache1" );

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
    public void runPutThenRemoveCategoricalTest( int start, int end )
        throws Exception
    {
    	configureJCSData(start, end);

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
    public void runPutInRangeTest( int start, int end )
        throws Exception
    {
        configureJCSData(start, end);

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
    public void runGetInRangeTest( int start, int end, boolean check )
        throws Exception
    {
        configureJCSNoData();

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

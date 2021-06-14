package it.uniroma2.dicii.isw2.jcs.paramTests;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Random;

/**
 * Simple test for the JCS class.
 */
public class JCSUniTest
{
    private Random random;
    private JCS jcs;
	private LinkedList<?> list;
    
    @Before 
    public void configure() throws CacheException {
    	
    	random = new Random();
    	jcs = JCS.getInstance( "testCache1" );
        list = buildList();
    }


    /**
     * @param args
     */
    public static void main( String args[] )
    {
        String[] testCaseName = { JCSUniTest.class.getName() };
        org.junit.runner.JUnitCore.main( testCaseName );
    }

    /**
     * @throws Exception
     */
    @Test 
    public void JCSTest()
        throws Exception
    {

        jcs.put( "some:key", list );

        assertEquals( list, jcs.get( "some:key" ) );
    }

    private LinkedList<HashMap<String, String>> buildList()
    {
        LinkedList<HashMap<String, String>> list = new LinkedList<HashMap<String, String>>();

        for ( int i = 0; i < 100; i++ )
        {
            list.add( this.buildMap() );
        }

        return list;
    }

    private HashMap<String, String> buildMap()
    {
        HashMap<String, String> map = new HashMap<String, String>();

        byte[] keyBytes = new byte[32];
        byte[] valBytes = new byte[128];

        for ( int i = 0; i < 10; i++ )
        {
            random.nextBytes( keyBytes );
            random.nextBytes( valBytes );

            map.put( new String( keyBytes ), new String( valBytes ) );
        }

        return map;
    }

}

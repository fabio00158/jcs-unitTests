package it.uniroma2.dicii.isw2.jcs.paramTests;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import org.apache.jcs.JCS;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Random;

/**
 * Simple test for the JCS class.
 */
public class JCSUniTest
{
    Random random;
    JCS jcs;
    LinkedList list;
    
    @Before 
    public static void configure() {
    	
    	random = new Random();
    	JCS jcs = JCS.getInstance( "testCache1" );
        LinkedList list = buildList();
    }
    
    /**
     * @param testName
     */
    public JCSUniTest( String testName )
    {
        super( testName );
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
    public void testJCS()
        throws Exception
    {

        jcs.put( "some:key", list );

        assertEquals( list, jcs.get( "some:key" ) );
    }

    private LinkedList buildList()
    {
        LinkedList list = new LinkedList();

        for ( int i = 0; i < 100; i++ )
        {
            list.add( buildMap() );
        }

        return list;
    }

    private HashMap buildMap()
    {
        HashMap map = new HashMap();

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

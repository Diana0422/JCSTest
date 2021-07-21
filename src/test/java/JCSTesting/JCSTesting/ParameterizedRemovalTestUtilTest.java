package JCSTesting.JCSTesting;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;

import org.apache.jcs.JCS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ParameterizedRemovalTestUtilTest {
	
	private Integer start;
	private Integer end;
	private boolean check;
	private String regionName;
	
	@Parameters
	public static Collection<Object[]> getParameters() {
		return Arrays.asList(new Object[][] {
			{1, 10, true, "testCache1"} // start, end, check, regionName
		});
	}
	
	public ParameterizedRemovalTestUtilTest(Integer s, Integer e, boolean c, String region) {
		this.configure(s, e, c, region);
	}

	private void configure(Integer s, Integer e, boolean c, String region) {
		this.start = s;
		this.end = e;
		this.check = c;
		this.regionName = region;
	}
    
	@Test
    public void runTestPutThenRemoveCategorical() throws Exception {
        JCS jcs = JCS.getInstance(regionName);

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

	@Test
    public void runPutInRange() throws Exception {
        JCS jcs = JCS.getInstance(regionName);

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
	
	@Test
    public void runGetInRange() throws Exception {
        JCS jcs = JCS.getInstance(regionName);

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

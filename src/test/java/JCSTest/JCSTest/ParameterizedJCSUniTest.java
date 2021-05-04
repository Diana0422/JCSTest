package JCSTest.JCSTest;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import org.apache.jcs.JCS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ParameterizedJCSUniTest {
	private Random random;
	private String keyString;
	private String instanceName;
	
	@Parameters
	public static Collection<String[]> getParameters() {
		return Arrays.asList(new String[][] {
			{"some:key", "testCache1"}
		});
	}
	
	public ParameterizedJCSUniTest(String key, String name) {
		this.configure(key, name);
	}
	
	private void configure(String key, String name) {
		this.random = new Random();
		this.keyString = key;
		this.instanceName = name;
	}
	
	@Test
	public void testJCS() throws Exception {
		System.out.println(instanceName);
		JCS jcs = JCS.getInstance(instanceName);
		LinkedList<HashMap<String,String>> list = buildList();
		jcs.put(keyString, list);
		assertEquals(list, jcs.get(keyString));
	}
	
    private LinkedList<HashMap<String,String>> buildList()
    {
        LinkedList<HashMap<String,String>> list = new LinkedList<HashMap<String,String>>();

        for ( int i = 0; i < 100; i++ )
        {
            list.add( buildMap() );
        }

        return list;
    }
	
    private HashMap<String, String> buildMap()
    {
        HashMap<String,String> map = new HashMap<String,String>();

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

import edu.uwm.cs351.util.MyHashTable;
import junit.framework.TestCase;

public class TestEfficiency extends TestCase{
	
	private MyHashTable<String,Integer> ht;
	private final int BIG=19,BIGGER=20,BIGGEST=21;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		try {assert 1/0 == 42 : "OK";}
		catch (ArithmeticException ex) {
			assertFalse("Assertions must NOT be enabled while running efficiency tests.",true);}
		ht = new MyHashTable<>();
	}
	
	//Big
	public void test00(){
		for (int i=0;i<(2<<BIG);i++)
			ht.put(""+i, i);
		assertEquals((2<<BIG),ht.size());
		
		for (int i=0;i<(2<<BIG);i++)
			assertEquals(i,(int)ht.remove(""+i));
		assertEquals(0,ht.size());
	}
	
	//Bigger
	public void test01(){
		for (int i=0;i<(2<<BIGGER);i++)
			ht.put(""+i, i);
		assertEquals((2<<BIGGER),ht.size());
		
		for (int i=0;i<(2<<BIGGER);i++)
			assertEquals(i,(int)ht.remove(""+i));
		assertEquals(0,ht.size());
	}
	
	//Biggest
	public void test02(){
		for (int i=0;i<(2<<BIGGEST);i++)
			ht.put(""+i, i);
		assertEquals((2<<BIGGEST),ht.size());
		
		for (int i=0;i<(2<<BIGGEST);i++)
			assertEquals(i,(int)ht.remove(""+i));
		assertEquals(0,ht.size());
	}
}

import org.example.HashMapLRUCache;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HashMapLRUCacheTest {
    HashMapLRUCache<Integer, Integer> map;

    @Before
    public void init(){
        map = new HashMapLRUCache<>(2);
    }

    @Test
    public void InitTest(){
        Assert.assertNotEquals( null, map);
        Assert.assertEquals(2, map.capacity());
        Assert.assertEquals(0, map.size());
    }

    @Test
    public void putAndGetTest(){
        map.put(1, 1);
        map.put(2, 2);
        Assert.assertEquals((Integer) 1, map.get(1));
        Assert.assertEquals((Integer) 2, map.get(2));

        map.put(3, 3);
        Assert.assertEquals((Integer) 3, map.get(3));
        Assert.assertNull(map.get(1));

        map.put(2, 3);
        Assert.assertEquals((Integer) 3, map.get(2));
    }

    @Test(expected = AssertionError.class)
    public void testNull(){
        map.put(null, 1);
    }

    @Test
    public void removeTest(){
        map.put(1, 1);
        map.put(2, 2);
        Assert.assertEquals((Integer) 1, map.get(1));
        Assert.assertEquals((Integer) 2, map.get(2));

        map.remove(1);
        Assert.assertNull(map.get(1));
        Assert.assertEquals((Integer) 2, map.get(2));
    }

    @Test
    public void sizeTest(){
        Assert.assertEquals(0, map.size());
        map.put(1, 1);
        Assert.assertEquals(1, map.size());

        map.put(2, 2);
        Assert.assertEquals(2, map.size());

        map.remove(1);
        Assert.assertEquals(1, map.size());
    }

    @Test
    public void capacityTest(){
        Assert.assertEquals(2, map.capacity());

        map.put(1, 1);
        map.put(2, 2);
        Assert.assertEquals(2, map.capacity());

        map.put(3, 3);
        Assert.assertEquals(2, map.capacity());

        map.remove(3);
        Assert.assertEquals(2, map.capacity());
    }

    @Test
    public void changeCapacityTest(){
        Assert.assertEquals(2, map.capacity());

        map.changeCapacity(3);
        Assert.assertEquals(3, map.capacity());

        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        Assert.assertEquals((Integer) 1, map.get(1));
        
        map.changeCapacity(1);
        Assert.assertEquals((Integer) 1, map.get(1));
        Assert.assertNull(map.get(2));
        Assert.assertNull(map.get(3));
    }
}

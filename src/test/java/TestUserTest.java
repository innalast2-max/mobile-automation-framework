import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class TestUserTest {
    @Test
    public void testUser() {
        TestUser testUser = new TestUser("Inna", "inna.yu@gmail.com", 35);
        assertEquals(testUser.getName(), "Inna");
        assertEquals(testUser.getEmail(), "inna.yu@gmail.com");
        assertEquals(testUser.getAge(), 35);
        assertTrue(testUser.toString().contains("Inna"),
                "toString should contain the user's name");
    }
}

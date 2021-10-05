import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class TestTest {

    @Test
    public void controlTEst() {
        assertThat(1, is(1));
    }
}
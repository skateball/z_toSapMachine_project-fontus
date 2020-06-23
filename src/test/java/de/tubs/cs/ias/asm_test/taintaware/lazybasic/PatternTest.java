package de.tubs.cs.ias.asm_test.taintaware.lazybasic;

import de.tubs.cs.ias.asm_test.AbstractTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PatternTest extends AbstractTest {

    @Test
    public void testSplitWithLimit_1() {
        IASString s1 = new IASString("bye");
        IASString s2 = new IASString(",bye");

        this.getTaintChecker().setTaint(s1, false);
        this.getTaintChecker().setTaint(s2, false);

        IASString result = new IASString(s1);
        for (int i = 0; i < 1000; i++) {
            result = result.concat(s2);
        }

        IASString[] t = result.split(new IASString(","), 500);

        for (int i = 0; i < t.length - 1; i++) {
            assertEquals("bye", t[i].getString());
            assertFalse(this.getTaintChecker().getTaint(t[i]));
        }
        assertEquals("bye", s1.getString());
        assertEquals(",bye", s2.getString());
        assertFalse(this.getTaintChecker().getTaint(t[t.length - 2]));
        assertFalse(this.getTaintChecker().getTaint(s1));
        assertFalse(this.getTaintChecker().getTaint(s2));
    }
    
    @Test
    public void testSplitWithLimit_3() {
        IASString s1 = new IASString("hello");
        IASString s2 = new IASString(",hi");
        IASString s3 = new IASString(",bye");
        IASString s4 = new IASString(",bye!");

        this.getTaintChecker().setTaint(s1, false);
        this.getTaintChecker().setTaint(s2, true);
        this.getTaintChecker().setTaint(s3, false);
        this.getTaintChecker().setTaint(s4, true);

        IASString s = s1.concat(s2).concat(s3).concat(s4);

        IASString[] t = s.split(new IASString(","), -1);

        assertEquals("hello", s1.getString());
        assertEquals(",hi", s2.getString());
        assertEquals(",bye", s3.getString());
        assertEquals(",bye!", s4.getString());
        assertEquals("hello,hi,bye,bye!", s.getString());
        assertEquals("hello", t[0].getString());
        assertEquals("hi", t[1].getString());
        assertEquals("bye", t[2].getString());
        assertEquals("bye!", t[3].getString());
        assertTrue(this.getTaintChecker().getTaint(s));
        assertFalse(this.getTaintChecker().getTaint(s1));
        assertTrue(this.getTaintChecker().getTaint(s2));
        assertFalse(this.getTaintChecker().getTaint(s3));
        assertTrue(this.getTaintChecker().getTaint(s4));
        assertFalse(this.getTaintChecker().getTaint(t[0]));
        assertTrue(this.getTaintChecker().getTaint(t[1]));
        assertFalse(this.getTaintChecker().getTaint(t[2]));
        assertTrue(this.getTaintChecker().getTaint(t[3]));
    }

    @Test
    public void testSplit_1() {
        IASString s = new IASString("hello,bye");

        IASString[] t = s.split(new IASString(","));

        assertEquals("hello,bye", s.getString());
        assertEquals("hello", t[0].getString());
        assertEquals("bye", t[1].getString());
        assertFalse(this.getTaintChecker().getTaint(s));
        assertFalse(this.getTaintChecker().getTaint(t[0]));
        assertFalse(this.getTaintChecker().getTaint(t[1]));
    }
}

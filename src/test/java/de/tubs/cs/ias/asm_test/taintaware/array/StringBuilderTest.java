package de.tubs.cs.ias.asm_test.taintaware.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringBuilderTest {
    private static final int TAINT = IASTaintSource.TS_CS_UNKNOWN_ORIGIN.getId();
    @Test
    public void testAppend1() {
        IASStringBuilder sb = new IASStringBuilder();
        IASString toAppend = IASString.fromString("Hello World");

        sb.append(toAppend);

        assertEquals("Hello World", sb.toString());
        assertFalse(sb.isTainted());
    }

    @Test
    public void testAppend2() {
        IASStringBuilder sb = new IASStringBuilder("Hello");
        IASString toAppend = IASString.fromString(" World");

        sb.append(toAppend);

        assertEquals("Hello World", sb.toString());
        assertFalse(sb.isTainted());
    }

    @Test
    public void testAppend3() {
        IASStringBuilder sb = new IASStringBuilder("Hello");
        IASString toAppend = new IASString(" World", true);

        sb.append(toAppend);

        assertEquals("Hello World", sb.toString());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, TAINT, TAINT, TAINT, TAINT, TAINT, TAINT}, sb.getTaints());
    }

    @Test
    public void testAppend4() {
        IASStringBuilder sb = new IASStringBuilder("Hello");
        sb.setTaint(true);
        IASString toAppend = new IASString(" World", true);

        sb.append(toAppend);

        assertEquals("Hello World", sb.toString());
        assertArrayEquals(new int[]{TAINT, TAINT, TAINT, TAINT, TAINT, TAINT, TAINT, TAINT, TAINT, TAINT, TAINT}, sb.getTaints());
    }

    @Test
    public void testAppend5() {
        IASStringBuilder sb = new IASStringBuilder("Hello");
        sb.setTaint(true);
        IASString toAppend = new IASString(" World");

        sb.append(toAppend);

        assertEquals("Hello World", sb.toString());
        assertArrayEquals(new int[]{TAINT, TAINT, TAINT, TAINT, TAINT, 0, 0, 0, 0, 0, 0}, sb.getTaints());
    }
}

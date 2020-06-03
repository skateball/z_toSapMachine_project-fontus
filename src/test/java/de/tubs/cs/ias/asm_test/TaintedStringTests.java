package de.tubs.cs.ias.asm_test;

import de.tubs.cs.ias.asm_test.taintaware.bool.IASString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"DuplicateStringLiteralInspection", "ClassIndependentOfModule", "ClassOnlyUsedInOneModule", "ClassUnconnectedToPackage", "ClassOnlyUsedInOnePackage"})
class TaintedStringTests {

    @Test
    void regularlyCreatedStringIsUntainted() {
        IASString str = new IASString("hello");
        assertFalse(str.isTainted(), "If we construct a taint-aware string without setting a taint, it should not be tainted.");
    }

    @Test
    void concatUntainted() {
        IASString lhs = new IASString("hello ");
        IASString rhs = new IASString("world");
        IASString result = (IASString) lhs.concat(rhs);
        assertFalse(result.isTainted(), "Concatenation of two untainted String should be untainted");
    }

    @Test
    void concatTaintedWithUntainted() {
        IASString lhs = new IASString("hello ", true);
        IASString rhs = new IASString("world");
        IASString result = (IASString) lhs.concat(rhs);
        assertTrue(result.isTainted(), "Concatenation of a tainted with an untainted String should be tainted");
    }

    @Test
    void concatUntaintedWithTainted() {
        IASString lhs = new IASString("hello ", true);
        IASString rhs = new IASString("world");
        IASString result = (IASString) lhs.concat(rhs);
        assertTrue(result.isTainted(), "Concatenation of an untainted with a tainted String should be tainted");
    }

    @Test
    void replaceFirstUntainted() {
        IASString base = new IASString("Hello welt");
        IASString regex = new IASString("welt");
        IASString replacement = new IASString("world");
        IASString result = (IASString) base.replaceFirst(regex, replacement);
        assertFalse(result.isTainted(), "Replacing a part of an untainted string with an untainted string should not be tainted");
    }

    @Test
    void replaceFirstBaseTainted() {
        IASString base = new IASString("Hello welt", true);
        IASString regex = new IASString("welt");
        IASString replacement = new IASString("world");
        IASString result = (IASString) base.replaceFirst(regex, replacement);
        assertTrue(result.isTainted(), "Replacing a part of an tainted string with an untainted string should be tainted");
    }

    @Test
    void replaceFirstReplacementTainted() {
        IASString base = new IASString("Hello welt");
        IASString regex = new IASString("welt");
        IASString replacement = new IASString("world", true);
        IASString result = (IASString) base.replaceFirst(regex, replacement);
        assertTrue(result.isTainted(), "Replacing a part of an untainted string with a tainted string should be tainted");
    }

    @Test
    void replaceFirstReplacementTaintedButNoMatch() {
        IASString base = new IASString("Hello welt");
        IASString regex = new IASString("Welt");
        IASString replacement = new IASString("world", true);
        IASString result = (IASString) base.replaceFirst(regex, replacement);
        assertFalse(result.isTainted(), "Trying to replace a part of an untainted string with a tainted string that does not match should not be tainted");
    }

    @Test
    void replaceAllUntainted() {
        IASString base = new IASString("Hello welt");
        IASString regex = new IASString("welt");
        IASString replacement = new IASString("world");
        IASString result = (IASString) base.replaceAll(regex, replacement);
        assertFalse(result.isTainted(), "Replacing a part of an untainted string with an untainted string should not be tainted");
    }

    @Test
    void replaceAllBaseTainted() {
        IASString base = new IASString("Hello welt", true);
        IASString regex = new IASString("welt");
        IASString replacement = new IASString("world");
        IASString result = (IASString) base.replaceAll(regex, replacement);
        assertTrue(result.isTainted(), "Replacing a part of an tainted string with an untainted string should be tainted");
    }

    @Test
    void replaceAllReplacementTainted() {
        IASString base = new IASString("Hello welt");
        IASString regex = new IASString("welt");
        IASString replacement = new IASString("world", true);
        IASString result = (IASString) base.replaceAll(regex, replacement);
        assertTrue(result.isTainted(), "Replacing a part of an untainted string with a tainted string should be tainted");
    }

    @Test
    void replaceAllReplacementTaintedButNoMatch() {
        IASString base = new IASString("Hello welt");
        IASString regex = new IASString("Welt");
        IASString replacement = new IASString("world", true);
        IASString result = (IASString) base.replaceAll(regex, replacement);
        assertFalse(result.isTainted(), "Trying to replace a part of an untainted string with a tainted string that does not match should not be tainted");
    }
}

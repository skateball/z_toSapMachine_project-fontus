package de.tubs.cs.ias.asm_test;

import de.tubs.cs.ias.asm_test.asm.Descriptor;
import de.tubs.cs.ias.asm_test.config.TaintMethod;
import de.tubs.cs.ias.asm_test.config.TaintStringConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.util.stream.Stream;

@SuppressWarnings({"DuplicateStringLiteralInspection", "SpellCheckingInspection", "ClassIndependentOfModule", "ClassOnlyUsedInOneModule"})
class DescriptorTests {
    private static final TaintStringConfig taintStringConfig = new TaintStringConfig(TaintMethod.defaultTaintMethod());

    @Test
    void parseCharArrayDescriptor() {
        Descriptor d = new Descriptor("[C", "V");
        Descriptor pd = Descriptor.parseDescriptor("([C)V");
        assertEquals(d, pd, "Both descriptors should have one char array parameter and void return type.");
    }

    @Test
    void parseArrayDescriptor() {
        Descriptor d = new Descriptor("[Ljava/lang/String;", "[C");
        Descriptor pd = Descriptor.parseDescriptor("([Ljava/lang/String;)[C");
        assertEquals(d, pd, "Both descriptors should have one String array array parameter and a char array return type.");
    }

    @ParameterizedTest(name = "(){0} = no parameters and {0} as return type.")
    @ValueSource(strings = {"Z", "B", "C", "S", "I", "F", "D", "J"})
    void parseNoParamReturnPrimitive(String primitive) {
        Descriptor d = new Descriptor(primitive);
        String descriptor = String.format("()%s", primitive);
        Descriptor pd = Descriptor.parseDescriptor(descriptor);
        assertEquals(d, pd, String.format("%s should parse to no params and %s as return type.", descriptor, primitive));
    }

    @ParameterizedTest(name = "({0})V = a {0} parameter and V as return type.")
    @ValueSource(strings = {"Z", "B", "C", "S", "I", "F", "D", "J"})
    void parsePrimitiveToVoid(String primitive) {
        String descriptor = String.format("(%s)V", primitive);
        Descriptor d = new Descriptor(new String[]{primitive}, "V");
        Descriptor pd = Descriptor.parseDescriptor(descriptor);
        assertEquals(d, pd, String.format("'%s' should parse to a '%s' param and 'V' as return type.", descriptor, primitive));

    }

    @ParameterizedTest
    @MethodSource("provideDescriptorData")
    void reassembleDescriptor(String descriptor, Descriptor d) {
        Descriptor pd = Descriptor.parseDescriptor(descriptor);
        assertEquals(descriptor, pd.toDescriptor(), String.format("%s should be equal to %s", descriptor, pd.toDescriptor()));
    }

    @ParameterizedTest
    @MethodSource("provideDescriptorData")
    void parseDescriptor(String descriptor, Descriptor d) {
        Descriptor pd = Descriptor.parseDescriptor(descriptor);
        assertEquals(d, pd, String.format("%s and %s should be equal.", d, pd));
    }

    @ParameterizedTest
    @MethodSource("provideDescriptorReplacementData")
    void replaceTypeInDescriptor(Descriptor initial, String from, String to, Descriptor result) {
        Descriptor rd = initial.replaceType(from, to);
        assertEquals(rd, result, String.format("After replacing all occurrences of '%s' in '%s' with '%s', the result should be: '%s'!", from, initial, to, result));
    }

    private static Stream<Arguments> provideDescriptorReplacementData() {
        return Stream.of(
                Arguments.of(
                        new Descriptor("I",
                                Constants.StringDesc,
                                "[Ljava/lang/Object;",
                                "F",
                                "Ljava/util/function/Function;",
                                "Ljava/lang/Float;",
                                "Ljava/util/List;"
                        ),
                        Constants.StringDesc,
                        taintStringConfig.getTStringDesc(),
                        new Descriptor("I",
                                taintStringConfig.getTStringDesc(),
                                "[Ljava/lang/Object;",
                                "F",
                                "Ljava/util/function/Function;",
                                "Ljava/lang/Float;",
                                "Ljava/util/List;"
                        )
                ),
                Arguments.of(
                        new Descriptor("[Ljava/lang/Object;", "Ljava/util/List;"),
                        Constants.StringDesc,
                        taintStringConfig.getTStringDesc(),
                        new Descriptor("[Ljava/lang/Object;", "Ljava/util/List;")),
                Arguments.of(
                        new Descriptor("[Ljava/lang/String;", "V"),
                        Constants.StringDesc,
                        taintStringConfig.getTStringDesc(),
                        new Descriptor("[" + taintStringConfig.getTStringDesc(), "V")
                )
        );
    }

    private static Stream<Arguments> provideDescriptorData() {
        return Stream.of(
                Arguments.of("(ILjava/lang/String;[Ljava/lang/Object;FLjava/util/function/Function;Ljava/lang/Float;)Ljava/util/List;",
                        new Descriptor("I",
                                "Ljava/lang/String;",
                                "[Ljava/lang/Object;",
                                "F",
                                "Ljava/util/function/Function;",
                                "Ljava/lang/Float;",
                                "Ljava/util/List;"
                        )),
                Arguments.of("([Ljava/lang/Object;)Ljava/util/List;",
                        new Descriptor("[Ljava/lang/Object;", "Ljava/util/List;")),
                Arguments.of(Constants.MAIN_METHOD_DESC,
                        new Descriptor("[Ljava/lang/String;", "V")),
                Arguments.of("(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                        new Descriptor("Ljava/lang/String;",
                                "[Ljava/lang/Object;",
                                "Ljava/lang/String;"
                        ))
        );
    }

    @Test
    public void testParseMethodPrimitive() throws NoSuchMethodException {
        Method m = Dummy.class.getDeclaredMethod("primitives", byte.class, short.class, int.class, long.class, boolean.class, double.class, float.class);

        Descriptor d = Descriptor.parseMethod(m);
        String desc = d.toDescriptor();

        assertEquals("(BSIJZDF)V", desc);
    }

    @Test
    public void testParseMethodPrimitiveArrays() throws NoSuchMethodException {
        Method m = Dummy.class.getDeclaredMethod("primitiveArrays", byte[].class, short[].class, int[].class, long[].class, boolean[].class, double[].class, float[].class);

        Descriptor d = Descriptor.parseMethod(m);
        String desc = d.toDescriptor();

        assertEquals("([B[S[I[J[Z[D[F)V", desc);
    }

    @Test
    public void testParseMethodClass() throws NoSuchMethodException {
        Method m = Dummy.class.getDeclaredMethod("clazz", String.class);

        Descriptor d = Descriptor.parseMethod(m);
        String desc = d.toDescriptor();

        assertEquals("(Ljava/lang/String;)V", desc);
    }

    @Test
    public void testParseMethodClassArray() throws NoSuchMethodException {
        Method m = Dummy.class.getDeclaredMethod("clazzArrays", String[].class);

        Descriptor d = Descriptor.parseMethod(m);
        String desc = d.toDescriptor();

        assertEquals("([Ljava/lang/String;)V", desc);
    }

    @Test
    public void testParseMethodReturnTypeClass() throws NoSuchMethodException {
        Method m = Dummy.class.getDeclaredMethod("returnTypeClass");

        Descriptor d = Descriptor.parseMethod(m);
        String desc = d.toDescriptor();

        assertEquals("()Ljava/lang/String;", desc);
    }

    class Dummy {
        public void primitives(byte b, short s, int i, long l, boolean bool, double d, float f) {
        }

        public void primitiveArrays(byte[] b, short[] s, int[] i, long[] l, boolean[] bool, double[] d, float[] f) {
        }

        public void clazz(String s) {
        }

        public void clazzArrays(String[] s) {
        }

        public String returnTypeClass() {
            return null;
        }
    }
}

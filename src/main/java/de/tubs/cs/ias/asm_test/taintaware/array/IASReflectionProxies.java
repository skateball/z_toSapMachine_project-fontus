package de.tubs.cs.ias.asm_test.taintaware.array;

import de.tubs.cs.ias.asm_test.config.TaintMethod;
import de.tubs.cs.ias.asm_test.config.TaintStringConfig;
import de.tubs.cs.ias.asm_test.instrumentation.strategies.InstrumentationHelper;

public class IASReflectionProxies {
    private static final TaintStringConfig tsc = new TaintStringConfig(TaintMethod.ARRAY);

    public static Class<?> classForName(IASString str) throws ClassNotFoundException {
        String s = str.getString();
        String clazz = InstrumentationHelper.getInstance(tsc).translateClassName(s);
        return Class.forName(clazz);
    }

    public static Class<?> classForName(IASString str, boolean initialize,
                                        ClassLoader loader) throws ClassNotFoundException {
        String s = str.getString();
        String clazz = InstrumentationHelper.getInstance(tsc).translateClassName(s);
        return Class.forName(clazz, initialize, loader);
    }
}

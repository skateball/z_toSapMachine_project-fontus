package de.tubs.cs.ias.asm_test.agent;

import de.tubs.cs.ias.asm_test.Configuration;
import de.tubs.cs.ias.asm_test.Instrumenter;
import de.tubs.cs.ias.asm_test.JdkClassesLookupTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.ProtectionDomain;

class TaintingTransformer implements ClassFileTransformer {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Configuration config = Configuration.instance;
    private Instrumenter instrumenter;
    private final boolean verbose;
    private static final JdkClassesLookupTable jdkClasses = JdkClassesLookupTable.instance;

    TaintingTransformer(boolean verbose) {
        this.verbose = verbose;
        this.instrumenter = new Instrumenter();
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        if (isJdkClass(className)) {
            logger.info("Skipping JDK class: {}", className);
            return classfileBuffer;
        }
        if (className.startsWith("de/tubs/cs/ias/asm_test")) {
            logger.info("Skipping Tainting Framework class: {}", className);
            return classfileBuffer;
        }

        logger.info("Tainting class: {}", className);
        byte[] outArray = this.instrumenter.instrumentClass(classfileBuffer);
        if(this.verbose) {
            String baseName = "/tmp";
            File outFile = new File(baseName, className + ".class");
            File parent = new File(outFile.getParent());
            parent.mkdirs();
            try {
                outFile.createNewFile();
                Path p = outFile.toPath();
                Files.write(p, outArray);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outArray;
    }

    static boolean isJdkClass(String className) {
        return (jdkClasses.isJdkClass(className) || className.startsWith("sun") || className.startsWith("com/sun") || className.startsWith("jdk") || className.startsWith("java") || className.startsWith("sun/misc/") || className.startsWith("org/objectweb/asm/")) && !className.startsWith("javax/servlet");
    }
}

package de.tubs.cs.ias.asm_test.instrumentation.strategies.clazz;

import de.tubs.cs.ias.asm_test.Constants;
import de.tubs.cs.ias.asm_test.TriConsumer;
import de.tubs.cs.ias.asm_test.config.TaintStringConfig;
import de.tubs.cs.ias.asm_test.instrumentation.strategies.StringBuilderInstrumentation;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import de.tubs.cs.ias.asm_test.utils.ParentLogger;
import de.tubs.cs.ias.asm_test.utils.LogUtils;

import java.util.Optional;
import java.util.regex.Matcher;

public class StringBuilderClassInstrumentationStrategy extends StringBuilderInstrumentation implements ClassInstrumentationStrategy {
    private static final ParentLogger logger = LogUtils.getLogger();

    private final ClassVisitor visitor;

    public StringBuilderClassInstrumentationStrategy(ClassVisitor cv, TaintStringConfig configuration) {
        super(configuration);
        this.visitor = cv;
    }

    @Override
    public Optional<FieldVisitor> instrumentFieldInstruction(int access, String name, String descriptor, String signature, Object value, TriConsumer tc) {
        Matcher descMatcher = Constants.strBuilderPattern.matcher(descriptor);
        if(descMatcher.find()) {
            String newDescriptor = descMatcher.replaceAll(this.stringConfig.getTStringBuilderDesc());
            logger.info("Replacing StringBuilder field [{}]{}.{} with [{}]{}.{}", access, name, descriptor, access, name, newDescriptor);
            return Optional.of(this.visitor.visitField(access, name, newDescriptor, signature, value));
        }
        return Optional.empty();
    }

    @Override
    public String getGetOriginalTypeMethod() {
        return Constants.TStringBuilderToStringBuilderName;
    }
}
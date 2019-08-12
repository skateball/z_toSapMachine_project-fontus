package de.tubs.cs.ias.asm_test.strategies.method;

import de.tubs.cs.ias.asm_test.Descriptor;
import de.tubs.cs.ias.asm_test.strategies.DefaultInstrumentation;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class DefaultMethodInstrumentationStrategy extends DefaultInstrumentation implements MethodInstrumentationStrategy {
    private final MethodVisitor mv;
    public DefaultMethodInstrumentationStrategy(MethodVisitor mv) {
        this.mv = mv;
    }

    @Override
    public boolean instrumentFieldIns(int opcode, String owner, String name, String descriptor) {
        this.mv.visitFieldInsn(opcode, owner, name, descriptor);
        return true;
    }

    @Override
    public void insertJdkMethodParameterConversion(String parameter) {    }

    @Override
    public boolean rewriteOwnerMethod(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        return false;
    }

    @Override
    public void instrumentReturnType(String owner, String name, Descriptor desc) {    }

    @Override
    public boolean handleLdc(Object value) {
        return false;
    }

    @Override
    public boolean handleLdcType(Type type) {
        return false;
    }

    @Override
    public String rewriteTypeIns(String type) {
        return type;
    }
}

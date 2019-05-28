package de.tubs.cs.ias.asm_test;

import java.util.regex.Pattern;

final class Constants {
    static final Pattern strPattern = Pattern.compile("Ljava/lang/String\\b");

    static final String ObjectQN = "java/lang/Object";

    static final String ToString = "toString";



    static final String ObjectDesc = java.lang.String.format("L%s;", ObjectQN);
    /**
     * The fully qualified name of the String class
     */
    static final String StringQN = "java/lang/String";
    /**
     * The fully qualified name of the StringBuilder class
     */
    static final String StringBuilder = "java/lang/StringBuilder";
    /**
     * The type of our taint-aware String
     */
    static final String TPackage = "de/tubs/cs/ias/asm_test/";
    /**
     * The type of our taint-aware String
     */
    static final String TString = TPackage + "IASString";
    /**
     * The bytecode descriptor of our taint aware string
     */
    static final String TStringDesc = java.lang.String.format("L%s", TString);

    static final String StringDesc = java.lang.String.format("L%s;", StringQN);
    /**
     * The bytecode descriptor of an array of our taint aware string
     */
    static final String TStringArrayDesc = "[" + TStringDesc + ";";

    static final Pattern strBuilderPattern = Pattern.compile("Ljava/lang/StringBuilder\\b");
    /**
     * The type of our taint-aware StringBuilder
     */
    static final String TStringBuilder = TPackage + "IASStringBuilder";
    /**
     * The bytecode descriptor of our taint aware StringBuilder
     */
    static final String TStringBuilderDesc = "L" + TStringBuilder;

    /**
     * Constructor name
     */
    static final String Init = "<init>";

    /**
     * Autogenerated name of the main wrapper function
     */
    static final String MainWrapper = "$main";

    /**
     * Descriptor of the untainted init/constructor method.
     */
    static final String TStringInitUntaintedDesc = "(Ljava/lang/String;)V";

    /**
     * Name of the method that converts taint-aware Strings to regular ones
     */
    static final String TStringToStringName = "getString";
    /**
     * Descriptor of an object to regular String conversion method
     */
    static final String ToStringDesc = "()Ljava/lang/String;";

    static final String ConcatDesc = String.format("(%s[Ljava/lang/Object;)%s;", StringDesc, TStringDesc);
    static final String ToStringInstrumented = "$toString";
    static final String ToStringInstrumentedDesc = String.format("()%s;", TStringDesc);
    private Constants() {}

}

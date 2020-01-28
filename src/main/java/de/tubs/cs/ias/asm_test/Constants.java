package de.tubs.cs.ias.asm_test;

import java.util.regex.Pattern;

public final class Constants {
    /**
     * The package our taint-aware classes are in
     */
    public static final String TPackage = "de/tubs/cs/ias/asm_test/taintaware/";

    /**
     * Fully qualified name of the java Object class.
     */
    public static final String ObjectQN = "java/lang/Object";

    /**
     * The fully qualified name of the String class
     */
    public static final String StringQN = "java/lang/String";

    /**
     * The fully qualified name of the StringBuilder class
     */
    public static final String StringBuilderQN = "java/lang/StringBuilder";

    /**
     * The fully qualified name of the StringBuffer class
     */
    public static final String StringBufferQN = "java/lang/StringBuffer";

    /**
     * The fully qualified name type of our taint-aware String
     */
    public static final String TStringQN = TPackage + "IASString";

    /**
     * The fully qualified name type of our taint-aware StringBuffer
     */
    public static final String TStringBufferQN = TPackage + "IASStringBuffer";

    public static final String TStringUtilsQN = TPackage + "IASStringUtils";

    /**
     * Name of the toString method.
     */
    public static final String ToString = "toString";

    /**
     * Descriptor of the java Object class
     */
    public static final String ObjectDesc = java.lang.String.format("L%s;", ObjectQN);

    /**
     * The fully qualified name of the class containing the Reflection proxies.
     */
    public static final String ReflectionProxiesQN = TPackage + "IASReflectionProxies";

    /**
     * The bytecode descriptor of our taint aware string
     */
    public static final String TStringDesc = java.lang.String.format("L%s;", TStringQN);

    /**
     * Descriptor of the java String class
     */
    public static final String StringDesc = java.lang.String.format("L%s;", StringQN);

    /**
     * Descriptor of an array of regular Java Strings
     */
    public static final String StringArrayDesc = String.format("[%s", StringDesc);

    /**
     * Descriptor of the java StringBuilder class
     */
    public static final String StringBuilderDesc = java.lang.String.format("L%s;", StringBuilderQN);
    /**
     * Descriptor of the java StringBuffer class
     */
    public static final String StringBufferDesc = java.lang.String.format("L%s;", StringBufferQN);
    /**
     * The bytecode descriptor of an array of our taint aware string
     */
    public static final String TStringArrayDesc = "[" + TStringDesc;

    /**
     * The type of our taint-aware StringBuilder
     */
    public static final String TStringBuilderQN = TPackage + "IASStringBuilder";

    /**
     * The bytecode descriptor of our taint aware StringBuilder
     */
    public static final String TStringBuilderDesc = String.format("L%s;", TStringBuilderQN);
    /**
     * The bytecode descriptor of our taint aware StringBuffer
     */
    public static final String TStringBufferDesc = String.format("L%s;", TStringBufferQN);
    /**
     * Constructor name
     */
    public static final String Init = "<init>";

    /**
     * Static initializer name
     */
    public static final String ClInit = "<clinit>";

    /**
     * Autogenerated name of the main wrapper function
     */
    public static final String MainWrapper = "$main";

    /**
     * Name of the instrumented toString method
     */
    public static final String ToStringInstrumented = "$toString";

    /**
     * Name of the method that converts taint-aware Strings to regular ones
     */
    public static final String TStringToStringName = "getString";

    /**
     * Descriptor of an object to regular String conversion method
     */
    public static final String ToStringDesc = "()Ljava/lang/String;";

    /**
     * Descriptor of the untainted init/constructor method.
     */
    public static final String TStringInitUntaintedDesc = "(Ljava/lang/String;)V";

    /**
     * Descriptor of the concat method
     */
    public static final String ConcatDesc = String.format("(%s[Ljava/lang/Object;)%s", StringDesc, TStringDesc);

    /**
     * Descriptor of the instrumented toString method.
     */
    public static final String ToStringInstrumentedDesc = String.format("()%s", TStringDesc);

    /**
     * Descriptor of the 'tainted' method that turns a regular String into a tainted one
     */
    public static final String CreateTaintedStringDesc = String.format("(Ljava/lang/String;)%s", TStringDesc);

    /**
     * Matches fully qualified String names
     */
    public static final Pattern strPattern = Pattern.compile(StringDesc);

    /**
     * Matches fully qualified StringBuilder names
     */
    public static final Pattern strBuilderPattern = Pattern.compile(StringBuilderDesc);
    /**
     * Matches fully qualified StringBuilder names
     */
    public static final Pattern strBufferPattern = Pattern.compile(StringBufferDesc);
    /**
     * The Taint-aware String method to check and act on a potential taint
     */
    public static final String ABORT_IF_TAINTED = "abortIfTainted";
    static final String MAIN_METHOD_DESC = "([Ljava/lang/String;)V";
    public static final String TO_TSTRING = "toIASString";
    public static final String STRING_FULL_NAME = "java.lang.String";
    public static final String STRINGBUFFER_FULL_NAME = "java.lang.StringBuffer";
    public static final String STRINGBUILDER_FULL_NAME = "java.lang.StringBuilder";

    public static final String AS_STRING = "asString";
    public static final String AS_STRING_DESC = String.format("(%s)%s", TStringDesc, StringDesc);

    public static final String FROM_STRING = "fromString";
    public static final String FROM_STRING_DESC = String.format("(%s)%s", StringDesc, TStringDesc);

    /**
     * Suffix of class files.
     */
    public static final String CLASS_FILE_SUFFIX = ".class";

    /**
     * Suffix of jar files.
     */
    public static final String JAR_FILE_SUFFIX = ".jar";

    /**
     * Suffix of json
     */
    public static final String JSON_FILE_SUFFIX = ".json";

    /**
     * Suffix of XML
     */
    public static final String XML_FILE_SUFFIX = ".xml";

    private Constants() {
    }

}

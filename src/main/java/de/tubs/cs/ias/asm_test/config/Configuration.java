package de.tubs.cs.ias.asm_test.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import de.tubs.cs.ias.asm_test.agent.AgentConfig;
import de.tubs.cs.ias.asm_test.asm.FunctionCall;
import de.tubs.cs.ias.asm_test.utils.LogUtils;
import org.apache.commons.text.StringSubstitutor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import de.tubs.cs.ias.asm_test.utils.Logger;

import java.util.stream.Collectors;

@XmlRootElement(name = "configuration")
public class Configuration {
    private static Configuration configuration;

    private static final Logger logger = LogUtils.getLogger();
    @JsonIgnore
    private TaintMethod taintMethod;
    @JsonIgnore
    private TaintStringConfig taintStringConfig;

    private boolean useCaching = defaultUseCaching();

    private int layerThreshold = defaultLayerThreshold();

    private boolean countRanges = defaultCountRanges();

    private boolean isOfflineInstrumentation = true;

    public boolean isOfflineInstrumentation() {
        return isOfflineInstrumentation;
    }

    public void setOfflineInstrumentation(boolean offlineInstrumentation) {
        isOfflineInstrumentation = offlineInstrumentation;
    }

    public static boolean defaultUseCaching() {
        return true;
    }

    public static int defaultLayerThreshold() {
        return 30;
    }

    public static boolean defaultCountRanges() {
        return false;
    }

    public void setCountRanges(boolean countRanges) {
        this.countRanges = countRanges;
    }

    public boolean useCaching() {
        return useCaching;
    }

    public void setUseCaching(boolean useCaching) {
        this.useCaching = useCaching;
    }

    public int getLayerThreshold() {
        return layerThreshold;
    }

    public void setLayerThreshold(int layerThreshold) {
        this.layerThreshold = layerThreshold;
    }

    public Configuration() {
        this.verbose = false;
        this.sourceConfig = new SourceConfig();
        this.sinkConfig = new SinkConfig();
        this.converters = new ArrayList<>();
        this.returnGeneric = new ArrayList<>();
        this.takeGeneric = new ArrayList<>();
        this.blacklistedMainClasses = new ArrayList<>();
    }

    public Configuration(boolean verbose, SourceConfig sourceConfig, SinkConfig sinkConfig, List<FunctionCall> converters, List<ReturnsGeneric> returnGeneric, List<TakesGeneric> takeGeneric, List<String> blacklistedMainClasses) {
        this.verbose = verbose;
        this.sourceConfig = sourceConfig;
        this.sinkConfig = sinkConfig;
        this.converters = converters;
        this.returnGeneric = returnGeneric;
        this.takeGeneric = takeGeneric;
        this.blacklistedMainClasses = blacklistedMainClasses;
    }

    public void append(Configuration other) {
        if (other != null) {
            this.verbose |= other.verbose;
            this.sourceConfig.append(other.sourceConfig);
            this.sinkConfig.append(other.sinkConfig);
            this.converters.addAll(other.converters);
            this.returnGeneric.addAll(other.returnGeneric);
            this.takeGeneric.addAll(other.takeGeneric);
            this.blacklistedMainClasses.addAll(other.blacklistedMainClasses);
        }
    }

    public void setTaintMethod(TaintMethod taintMethod) {
        this.taintMethod = taintMethod;
        this.taintStringConfig = new TaintStringConfig(taintMethod);
    }

    public void transformConverters() {
        HashMap<String, String> replacements = new HashMap<>();
        replacements.put("subpath", this.taintMethod.getSubPath());
        StringSubstitutor sub = new StringSubstitutor(replacements);

        List<FunctionCall> converted = this.converters.stream().map(functionCall -> {
            String oldOwner = functionCall.getOwner();

            String newOwner = sub.replace(oldOwner);
            return new FunctionCall(functionCall.getOpcode(), newOwner, functionCall.getName(), functionCall.getDescriptor(), functionCall.isInterface());
        }).collect(Collectors.toList());

        this.converters.clear();
        this.converters.addAll(converted);
    }

    public TaintStringConfig getTaintStringConfig() {
        return this.taintStringConfig;
    }

    void appendBlacklist(Collection<String> other) {
        if (this.blacklistedMainClasses != null) {
            this.blacklistedMainClasses.addAll(other);
        }
    }

    public SourceConfig getSourceConfig() {
        return this.sourceConfig;
    }

    public SinkConfig getSinkConfig() {
        return this.sinkConfig;
    }

    public List<FunctionCall> getConverters() {
        return this.converters;
    }

    public List<ReturnsGeneric> getReturnGeneric() {
        return this.returnGeneric;
    }

    public List<TakesGeneric> getTakeGeneric() {
        return this.takeGeneric;
    }

    public List<String> getBlacklistedMainClasses() {
        return this.blacklistedMainClasses;
    }

    private FunctionCall getConverter(String name) {
        for (FunctionCall fc : this.converters) {
            if (fc.getName().equals(name)) {
                return fc;
            }
        }
        return null;
    }

    public boolean needsParameterConversion(FunctionCall c) {
        for (TakesGeneric tg : this.takeGeneric) {
            if (tg.getFunctionCall().equals(c)) {
                return true;
            }
        }
        return false;
    }

    public FunctionCall getConverterForParameter(FunctionCall c, int index) {
        for (TakesGeneric tg : this.takeGeneric) {
            if (tg.getFunctionCall().equals(c)) {
                Conversion conversion = tg.getConversionAt(index);
                if (conversion != null) {
                    String converterName = conversion.getConverter();
                    FunctionCall converter = this.getConverter(converterName);
                    logger.info("Found converter for {} at index {}: {}", c, index, converter);
                    return converter;
                }
            }
        }
        return null;
    }

    public FunctionCall getConverterForReturnValue(FunctionCall c) {
        for (ReturnsGeneric rg : this.returnGeneric) {
            if (rg.getFunctionCall().equals(c)) {
                String converterName = rg.getConverter();
                FunctionCall converter = this.getConverter(converterName);
                logger.info("Found converter for rv of {}: {}", c, converter);
                return converter;
            }
        }
        return null;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isVerbose() {
        return this.verbose;
    }

    public boolean isClassMainBlacklisted(String owner) {
        return this.blacklistedMainClasses.contains(owner);
    }

    @XmlElement
    private boolean verbose = false;

    @XmlElement
    private final SourceConfig sourceConfig;
    /**
     * All functions listed here consume Strings that need to be checked first.
     */
    @XmlElement
    private final SinkConfig sinkConfig;

    @JacksonXmlElementWrapper(localName = "converters")
    @XmlElement(name = "converter")
    private final List<FunctionCall> converters;

    @JacksonXmlElementWrapper(localName = "returnGenerics")
    @XmlElement(name = "returnGeneric")
    private final List<ReturnsGeneric> returnGeneric;

    @JacksonXmlElementWrapper(localName = "takeGenerics")
    @XmlElement(name = "takeGeneric")
    private final List<TakesGeneric> takeGeneric;

    @JacksonXmlElementWrapper(localName = "blacklistedMainClasses")
    @XmlElement(name = "class")
    private final List<String> blacklistedMainClasses;

    public TaintMethod getTaintMethod() {
        return this.taintMethod;
    }

    public boolean countRanges() {
        return this.countRanges;
    }

    public static Configuration getConfiguration() {
        if (configuration == null) {
            throw new IllegalStateException("Configuration not initialized! This should never happen!");
        }
        return configuration;
    }

    public static void parseAgent(String args) {
        Configuration configuration = AgentConfig.parseConfig(args);
        configuration.setOfflineInstrumentation(false);

        setConfiguration(configuration);
    }

    public static void parseOffline(TaintMethod method) {
        Configuration configuration = new Configuration();
        configuration.setTaintMethod(method);

        String countRangesString = System.getenv("ASM_COUNT_RANGES");
        if(countRangesString != null) {
            try {
                boolean countRanges = Boolean.parseBoolean(countRangesString);
                configuration.setCountRanges(countRanges);
                logger.info("Set count_ranges to {}", countRanges);
            } catch (Exception ex) {
                logger.error("Couldn't parse ASM_COUNT_RANGES environment variable: {}", countRangesString);
            }
        }

        String useCachingString = System.getenv("ASM_USE_CACHING");
        if(useCachingString != null) {
            try {
                boolean useCaching = Boolean.parseBoolean(useCachingString);
                configuration.setUseCaching(useCaching);
                logger.info("Set use_caching to {}", useCaching);
            } catch (Exception ex) {
                logger.error("Couldn't parse ASM_USE_CACHING environment variable: {}", countRangesString);
            }
        }

        String layerThresholdString = System.getenv("ASM_LAYER_THRESHOLD");
        if(layerThresholdString != null) {
            try {
                int layerThreshold = Integer.parseInt(layerThresholdString);
                configuration.setLayerThreshold(layerThreshold);
                logger.info("Set layer_threshold to {}", layerThreshold);
            } catch (Exception ex) {
                logger.error("Couldn't parse ASM_LAYER_THRESHOLD environment variable: {}", countRangesString);
            }
        }

        setConfiguration(configuration);
    }

    public static void setConfiguration(Configuration configuration) {
        if (Configuration.configuration != null) {
            throw new IllegalStateException("Configuration already initialized");
        }
        Configuration.configuration = configuration;
    }
}

package de.tubs.cs.ias.asm_test.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import de.tubs.cs.ias.asm_test.asm.FunctionCall;

public class SourceConfig {

    public SourceConfig() {
        this.sources = new ArrayList<>();
    }

    public SourceConfig(List<Source> sources) {
        this.sources = sources;
    }

    public void append(SourceConfig sourceConfig) {
        this.sources.addAll(sourceConfig.sources);
    }

    public Source getSourceForFunction(FunctionCall fc) {
        for (Source s : this.sources) {
            if (s.getFunction().equals(fc)) {
                return s;
            }
        }
        return null;
    }

    public boolean containsFunction(FunctionCall fc) {
        return (this.getSourceForFunction(fc) != null);
    }

    public List<Source> getSources() {
        return Collections.unmodifiableList(this.sources);
    }

    @JacksonXmlElementWrapper(localName = "sources")
    @XmlElement(name = "source")
    private final List<Source> sources;

}

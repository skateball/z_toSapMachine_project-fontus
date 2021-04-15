package com.sap.fontus.taintaware.untainted;

import com.sap.fontus.config.taintloss.TaintlossHandler;
import com.sap.fontus.taintaware.IASTaintAware;
import com.sap.fontus.taintaware.shared.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@SuppressWarnings("ALL")
public final class IASString implements IASTaintAware, IASStringable {

    private String string;
    private static ConcurrentHashMap<String, IASString> internPool = new ConcurrentHashMap<>();

    public IASString() {
        this.string = "";
    }

    public IASString(IASStringable s) {
        this.string = s.getString();
    }

    public IASString(String s) {
        this.string = Objects.requireNonNull(s);
    }

    public IASString(IASAbstractStringBuilderable strb) {
        this.string = strb.toString();
    }

    public IASString(String s, boolean tainted) {
        this.string = Objects.requireNonNull(s);
    }

    public static IASString tainted(IASString tstr) {
        return tstr;
    }

    @Override
    public boolean isTainted() {
        return false;
    }

    @Override
    public List<IASTaintRange> getTaintRanges() {
        return Collections.emptyList();
    }

    @Override
    public void setTaint(boolean b) {
    }

    @Override
    public void setTaint(IASTaintSource source) {
    }

    @Override
    public void setContent(String content, List<IASTaintRange> taintRanges) {
        this.string = content;
    }

    public IASString(char value[]) {
        this.string = new String(value);
    }

    public IASString(char value[], int offset, int count) {
        this.string = new String(value, offset, count);
    }

    public IASString(int[] codePoints, int offset, int count) {
        this.string = new String(codePoints, offset, count);
    }

    public IASString(byte ascii[], int hibyte, int offset, int count) {
        this.string = new String(ascii, hibyte, offset, count);
    }

    public IASString(byte ascii[], int hibyte) {
        this.string = new String(ascii, hibyte);
    }

    public IASString(byte bytes[], int offset, int length, IASStringable charsetName)
            throws UnsupportedEncodingException {
        this.string = new String(bytes, offset, length, charsetName.getString());
    }

    public IASString(byte bytes[], int offset, int length, Charset charset) {
        // TODO: howto handle this? Does the charset affect tainting?
        this.string = new String(bytes, offset, length, charset);
    }

    public IASString(byte bytes[], IASStringable charsetName) throws UnsupportedEncodingException {
        // TODO: howto handle this? Does the charset affect tainting?
        this.string = new String(bytes, charsetName.getString());
    }

    public IASString(byte bytes[], Charset charset) {
        this.string = new String(bytes, charset);
    }

    public IASString(byte bytes[], int offset, int length) {
        this.string = new String(bytes, offset, length);
    }

    public IASString(byte[] bytes) {
        this.string = new String(bytes);
    }

    public IASString(StringBuffer buffer) {
        this.string = new String(buffer);
    }

    public IASString(IASStringBuilder builder) {
        this.string = builder.toString();
    }

    public IASString(IASStringBuffer buffer) {
        this.string = buffer.toString();
    }

    public IASString(IASString string) {
        this.string = string.string;
    }

    private IASString(CharSequence cs, boolean tainted) {
        this.string = cs.toString();
    }

    @Override
    public int length() {
        return this.string.length();
    }

    @Override
    public boolean isEmpty() {
        return this.string.isEmpty();
    }

    @Override
    public char charAt(int index) {
        TaintlossHandler.logTaintloss(this);
        return this.string.charAt(index);
    }

    @Override
    public int codePointAt(int index) {
        TaintlossHandler.logTaintloss(this);
        return this.string.codePointAt(index);
    }

    @Override
    public int codePointBefore(int index) {
        TaintlossHandler.logTaintloss(this);
        return this.string.codePointBefore(index);
    }

    @Override
    public int codePointCount(int beginIndex, int endIndex) {
        return this.string.codePointCount(beginIndex, endIndex);
    }

    @Override
    public int offsetByCodePoints(int index, int codePointOffset) {
        return this.string.offsetByCodePoints(index, codePointOffset);
    }

    @Override
    public void getChars(int srcBegin, int srcEnd, char dst[], int dstBegin) {
        TaintlossHandler.logTaintloss(this);
        this.string.getChars(srcBegin, srcEnd, dst, dstBegin);
    }

    @Override
    public void getBytes(int srcBegin, int srcEnd, byte dst[], int dstBegin) {
        TaintlossHandler.logTaintloss(this);
        this.string.getBytes(srcBegin, srcEnd, dst, dstBegin);
    }

    @Override
    public byte[] getBytes(IASStringable charsetName) throws UnsupportedEncodingException {
        TaintlossHandler.logTaintloss(this);
        return this.string.getBytes(charsetName.getString());
    }

    @Override
    public byte[] getBytes(Charset charset) {
        TaintlossHandler.logTaintloss(this);
        return this.string.getBytes(charset);
    }

    @Override
    public byte[] getBytes() {
        TaintlossHandler.logTaintloss(this);
        return this.string.getBytes();
    }

    @Override
    public boolean equals(Object anObject) {
        if (anObject instanceof String) return this.string.equals(anObject);
        if (!(anObject instanceof IASString)) return false;
        IASString other = (IASString) anObject;
        return this.string.equals(other.string);
    }

    @Override
    public boolean contentEquals(IASAbstractStringBuilderable sb) {
        return this.string.contentEquals(sb.getStringBuilder());
    }

    public boolean contentEquals(StringBuffer sb) {
        return this.string.contentEquals(sb);
    }

    @Override
    public boolean contentEquals(CharSequence cs) {
        return this.string.contentEquals(cs);
    }

    @Override
    public boolean equalsIgnoreCase(IASStringable anotherString) {
        if (anotherString == null) {
            return false;
        }
        return this.string.equalsIgnoreCase(anotherString.getString());
    }

    @Override
    public int compareTo(IASStringable anotherString) {
        return this.string.compareTo(anotherString.getString());
    }

    @Override
    public int compareToIgnoreCase(IASStringable str) {
        return this.string.compareToIgnoreCase(str.getString());
    }

    @Override
    public boolean regionMatches(int toffset, IASStringable other, int ooffset, int len) {
        return this.string.regionMatches(toffset, other.getString(), ooffset, len);
    }

    @Override
    public boolean regionMatches(boolean ignoreCase, int toffset, IASStringable other, int ooffset, int len) {
        return this.string.regionMatches(ignoreCase, toffset, other.getString(), ooffset, len);
    }

    @Override
    public boolean startsWith(IASStringable prefix, int toffset) {
        return this.string.startsWith(prefix.getString(), toffset);
    }

    @Override
    public boolean startsWith(IASStringable prefix) {
        return this.string.startsWith(prefix.getString());
    }

    @Override
    public boolean endsWith(IASStringable suffix) {
        return this.string.endsWith(suffix.getString());
    }

    //TODO: sound?
    @Override
    public int hashCode() {
        return this.string.hashCode();
    }

    @Override
    public int indexOf(int ch) {
        return this.string.indexOf(ch);
    }

    @Override
    public int indexOf(int ch, int fromIndex) {
        return this.string.indexOf(ch, fromIndex);
    }

    @Override
    public int lastIndexOf(int ch) {
        return this.string.lastIndexOf(ch);
    }

    @Override
    public int lastIndexOf(int ch, int fromIndex) {
        return this.string.lastIndexOf(ch, fromIndex);
    }

    @Override
    public int indexOf(IASStringable str) {
        return this.string.indexOf(str.getString());
    }

    @Override
    public int indexOf(IASStringable str, int fromIndex) {
        return this.string.indexOf(str.getString(), fromIndex);
    }

    @Override
    public int lastIndexOf(IASStringable str) {
        return this.string.lastIndexOf(str.getString());
    }

    @Override
    public int lastIndexOf(IASStringable str, int fromIndex) {
        return this.string.lastIndexOf(str.getString(), fromIndex);
    }

    @Override
    public IASString substring(int beginIndex) {
        return new IASString(this.string.substring(beginIndex));
    }

    @Override
    public IASString substring(int beginIndex, int endIndex) {
        return new IASString(this.string.substring(beginIndex, endIndex));
    }

    @Override
    public CharSequence subSequence(int beginIndex, int endIndex) {
        return new IASString(this.string.subSequence(beginIndex, endIndex).toString());
    }

    @Override
    public IASString concat(IASStringable str) {
        return new IASString(this.string.concat(str.getString()));
    }

    @Override
    public IASString replace(char oldChar, char newChar) {
        return new IASString(this.string.replace(oldChar, newChar));
    }

    @Override
    public boolean matches(IASStringable regex) {
        return this.string.matches(regex.getString());
    }

    @Override
    public boolean contains(CharSequence s) {
        return this.string.contains(s);
    }

    @Override
    public IASString replaceFirst(IASStringable regex, IASStringable replacement) {
        String result = this.string.replaceFirst(regex.getString(), replacement.getString());
        return new IASString(result);
    }

    @Override
    public IASString replaceAll(IASStringable regex, IASStringable replacement) {
        String result = this.string.replaceAll(regex.getString(), replacement.getString());
        return new IASString(result);
    }

    @Override
    public IASString replace(CharSequence target, CharSequence replacement) {
        return new IASString(this.string.replace(target, replacement));
    }

    // TODO: this propagates the taint for the whole string
    @Override
    public IASString[] split(IASStringable regex, int limit) {
        String[] split = this.string.split(regex.getString(), limit);
        IASString[] splitted = new IASString[split.length];
        for (int i = 0; i < split.length; i++) {
            splitted[i] = new IASString(split[i]);
        }
        return splitted;
    }

    // TODO: this propagates the taint for the whole string
    @Override
    public IASString[] split(IASStringable regex) {
        String[] split = this.string.split(regex.getString());
        IASString[] splitted = new IASString[split.length];
        for (int i = 0; i < split.length; i++) {
            splitted[i] = new IASString(split[i]);
        }
        return splitted;
    }

    public static IASString join(CharSequence delimiter, CharSequence... elements) {
        return new IASString(String.join(delimiter, elements));
    }


    public static IASString join(CharSequence delimiter,
                                 Iterable<? extends CharSequence> elements) {
        return new IASString(String.join(delimiter, elements));
    }

    @Override
    public IASString toLowerCase(Locale locale) {
        return new IASString(this.string.toLowerCase(locale));
    }

    @Override
    public IASString toLowerCase() {
        return new IASString(this.string.toLowerCase());
    }

    @Override
    public IASString toUpperCase(Locale locale) {
        return new IASString(this.string.toUpperCase(locale));
    }

    @Override
    public IASString toUpperCase() {
        return new IASString(this.string.toUpperCase());
    }

    @Override
    public IASString trim() {
        return new IASString(this.string.trim());
    }

    /* JDK 11 BEGIN */
    @Override
    public IASString strip() {
        return new IASString(this.string.strip());
    }

    @Override
    public IASString stripLeading() {
        return new IASString(this.string.stripLeading());
    }

    @Override
    public IASString stripTrailing() {
        return new IASString(this.string.stripTrailing());
    }

    @Override
    public boolean isBlank() {
        return this.string.isBlank();
    }

    public Stream<IASString> lines() {
        return this.string.lines().map(s -> new IASString(s));
    }

    @Override
    public IASTaintSource getTaintFor(int position) {
        return null;
    }

    @Override
    public IASString repeat(int count) {
        return new IASString(this.string.repeat(count));
    }
    /* JDK 11 END */

    //TODO: sound?
    @Override
    public String toString() {
        return this.string.toString();
    }

    @Override
    public IASString toIASString() {
        return this;
    }

    @Override
    public IntStream chars() {
        TaintlossHandler.logTaintloss(this);
        return this.string.chars();
    }

    @Override
    public IntStream codePoints() {
        TaintlossHandler.logTaintloss(this);
        return this.string.codePoints();
    }

    @Override
    public char[] toCharArray() {
        TaintlossHandler.logTaintloss(this);
        return this.string.toCharArray();
    }

    static boolean isTainted(Object[] args) {
        return false;
    }

    //TODO: sound?
    public static IASString format(IASStringable format, Object... args) {
        //return new IASString(String.format(format.toString(), args), isTainted(args));
        return new IASFormatter().format(format, args).toIASString();
    }

    //TODO: sound?
    public static IASString format(Locale l, IASStringable format, Object... args) {
        //return new IASString(String.format(l, format.toString(), args), isTainted(args));
        return new IASFormatter(l).format(format, args).toIASString();
    }

    public static IASString valueOf(Object obj) {
        if (obj instanceof IASString) {
            return (IASString) obj;
        } else if (obj instanceof IASStringBuffer) {
            return (IASString) ((IASStringBuffer) obj).toIASString();
        } else if (obj instanceof IASStringBuilder) {
            return (IASString) ((IASStringBuilder) obj).toIASString();
        } else {
            return new IASString(String.valueOf(obj));
        }
    }

    public static IASString valueOf(char data[]) {
        return new IASString(String.valueOf(data));
    }

    public static IASString valueOf(char data[], int offset, int count) {
        return new IASString(String.valueOf(data, offset, count));
    }

    public static IASString copyValueOf(char data[], int offset, int count) {
        return new IASString(String.copyValueOf(data, offset, count));
    }

    public static IASString copyValueOf(char data[]) {
        return new IASString(String.copyValueOf(data));
    }

    public static IASString valueOf(boolean b) {
        return new IASString(String.valueOf(b));
    }

    public static IASString valueOf(char c) {
        return new IASString(String.valueOf(c));
    }

    public static IASString valueOf(int i) {
        return new IASString(String.valueOf(i));
    }

    public static IASString valueOf(long l) {
        return new IASString(String.valueOf(l));
    }

    public static IASString valueOf(float f) {
        return new IASString(String.valueOf(f));
    }

    public static IASString valueOf(double d) {
        return new IASString(String.valueOf(d));
    }

    //TODO: sound?
    @Override
    public IASString intern() {
        this.string = this.string.intern();
        TaintlossHandler.logTaintloss(this);
        return (IASString) IASStringPool.intern(this);
    }


    public static IASString fromString(String str) {
        if (str == null) return null;
        return new IASString(str);
    }

    public static String asString(IASString str) {
        if (str == null) return null;
        return str.getString();
    }

    @Override
    public String getString() {
        return this.string;
    }

    public static final Comparator<IASString> CASE_INSENSITIVE_ORDER
            = new CaseInsensitiveComparator();

    private static class CaseInsensitiveComparator
            implements Comparator<IASString>, java.io.Serializable {
        private static final long serialVersionUID = 8575799808933029326L;

        @Override
        public int compare(IASString s1, IASString s2) {
            return s1.compareToIgnoreCase(s2);
        }
    }

}

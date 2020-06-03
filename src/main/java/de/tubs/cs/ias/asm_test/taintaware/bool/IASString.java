package de.tubs.cs.ias.asm_test.taintaware.bool;

import de.tubs.cs.ias.asm_test.taintaware.IASTaintAware;
import de.tubs.cs.ias.asm_test.taintaware.shared.IASStringBuilderable;
import de.tubs.cs.ias.asm_test.taintaware.shared.IASStringPool;
import de.tubs.cs.ias.asm_test.taintaware.shared.IASStringable;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@SuppressWarnings("ALL")
public final class IASString implements IASTaintAware, IASStringable {

    private String str;
    private boolean tainted = false;
    private static ConcurrentHashMap<String, IASString> internPool = new ConcurrentHashMap<>();

    public IASString() {
        this.str = "";
        this.tainted = false;
    }

    public IASString(String s) {
        this.str = s;
        this.tainted = false;
    }

    public IASString(String s, boolean tainted) {
        this.str = s;
        this.tainted = tainted;
    }

    public static IASString tainted(IASString tstr) {
        tstr.tainted = true;
        return tstr;
    }

    @Override
    public boolean isTainted() {
        return this.tainted;
    }

    @Override
    public void setTaint(boolean b) {
        // Prevent tainting of empty strings
        if (str.length() > 0) {
            this.tainted = b;
        }
    }

    private void mergeTaint(IASTaintAware other) {
        this.tainted |= other.isTainted();
    }

    public void abortIfTainted() {
        if (this.tainted) {
            System.err.printf("String %s is tainted!\nAborting..!\n", this.str);
            System.exit(1);
        }
    }

    public IASString(char value[]) {
        this.str = new String(value);
    }

    public IASString(char value[], int offset, int count) {
        this.str = new String(value, offset, count);
    }

    public IASString(int[] codePoints, int offset, int count) {
        this.str = new String(codePoints, offset, count);
    }

    public IASString(byte ascii[], int hibyte, int offset, int count) {
        this.str = new String(ascii, hibyte, offset, count);
    }

    public IASString(byte ascii[], int hibyte) {
        this.str = new String(ascii, hibyte);
    }

    public IASString(byte bytes[], int offset, int length, IASString charsetName)
            throws UnsupportedEncodingException {
        this.str = new String(bytes, offset, length, charsetName.str);
    }

    public IASString(byte bytes[], int offset, int length, Charset charset) {
        // TODO: howto handle this? Does the charset affect tainting?
        this.str = new String(bytes, offset, length, charset);
    }

    public IASString(byte bytes[], IASString charsetName) throws UnsupportedEncodingException {
        // TODO: howto handle this? Does the charset affect tainting?
        this.str = new String(bytes, charsetName.str);
    }

    public IASString(byte bytes[], Charset charset) {
        this.str = new String(bytes, charset);
    }

    public IASString(byte bytes[], int offset, int length) {
        this.str = new String(bytes, offset, length);
    }

    public IASString(byte[] bytes) {
        this.str = new String(bytes);
    }

    public IASString(StringBuffer buffer) {
        this.str = new String(buffer);
    }

    public IASString(IASStringBuilder builder) {
        this.str = builder.toString();
        this.tainted = builder.isTainted();
    }

    public IASString(IASStringBuffer buffer) {
        this.str = buffer.toString();
        this.tainted = buffer.isTainted();
    }

    public IASString(IASString string) {
        this.str = string.str;
        this.tainted = string.tainted;
    }

    private IASString(CharSequence cs, boolean tainted) {
        this.str = cs.toString();
        this.tainted = tainted;
    }

    public int length() {
        return this.str.length();
    }

    public boolean isEmpty() {
        return this.str.isEmpty();
    }

    public char charAt(int index) {
        return this.str.charAt(index);
    }

    public int codePointAt(int index) {
        return this.str.codePointAt(index);
    }

    public int codePointBefore(int index) {
        return this.str.codePointBefore(index);
    }

    public int codePointCount(int beginIndex, int endIndex) {
        return this.str.codePointCount(beginIndex, endIndex);
    }

    public int offsetByCodePoints(int index, int codePointOffset) {
        return this.str.offsetByCodePoints(index, codePointOffset);
    }

    public void getChars(int srcBegin, int srcEnd, char dst[], int dstBegin) {
        this.str.getChars(srcBegin, srcEnd, dst, dstBegin);
    }

    public void getBytes(int srcBegin, int srcEnd, byte dst[], int dstBegin) {
        this.str.getBytes(srcBegin, srcEnd, dst, dstBegin);
    }

    public byte[] getBytes(IASStringable charsetName) throws UnsupportedEncodingException {
        return this.str.getBytes(charsetName.getString());
    }

    public byte[] getBytes(Charset charset) {
        return this.str.getBytes(charset);
    }

    public byte[] getBytes() {
        return this.str.getBytes();
    }

    public boolean equals(Object anObject) {
        if (!(anObject instanceof IASString)) return false;
        IASString other = (IASString) anObject;
        return this.str.equals(other.str);
    }

    public boolean contentEquals(IASStringBuilderable sb) {
        return this.str.contentEquals(sb.getBuilder());
    }

    public boolean contentEquals(StringBuffer sb) {
        return this.str.contentEquals(sb);
    }

    public boolean contentEquals(CharSequence cs) {
        return this.str.contentEquals(cs);
    }

    public boolean equalsIgnoreCase(IASStringable anotherString) {
        return this.str.equalsIgnoreCase(anotherString.getString());
    }

    @Override
    public int compareTo(IASStringable anotherString) {
        return this.str.compareTo(anotherString.getString());
    }

    public int compareToIgnoreCase(IASStringable str) {
        return this.str.compareToIgnoreCase(str.getString());
    }

    public boolean regionMatches(int toffset, IASStringable other, int ooffset, int len) {
        return this.str.regionMatches(toffset, other.getString(), ooffset, len);
    }

    public boolean regionMatches(boolean ignoreCase, int toffset, IASStringable other, int ooffset, int len) {
        return this.str.regionMatches(ignoreCase, toffset, other.getString(), ooffset, len);
    }

    public boolean startsWith(IASStringable prefix, int toffset) {
        return this.str.startsWith(prefix.getString(), toffset);
    }

    public boolean startsWith(IASStringable prefix) {
        return this.str.startsWith(prefix.getString());
    }

    public boolean endsWith(IASStringable suffix) {
        return this.str.endsWith(suffix.getString());
    }

    //TODO: sound?
    public int hashCode() {
        return this.str.hashCode();
    }

    public int indexOf(int ch) {
        return this.str.indexOf(ch);
    }

    public int indexOf(int ch, int fromIndex) {
        return this.str.indexOf(ch, fromIndex);
    }

    public int lastIndexOf(int ch) {
        return this.str.lastIndexOf(ch);
    }

    public int lastIndexOf(int ch, int fromIndex) {
        return this.str.lastIndexOf(ch, fromIndex);
    }

    public int indexOf(IASStringable str) {
        return this.str.indexOf(str.getString());
    }

    public int indexOf(IASStringable str, int fromIndex) {
        return this.str.indexOf(str.getString(), fromIndex);
    }

    public int lastIndexOf(IASStringable str) {
        return this.str.lastIndexOf(str.getString());
    }

    public int lastIndexOf(IASStringable str, int fromIndex) {
        return this.str.lastIndexOf(str.getString(), fromIndex);
    }

    public IASStringable substring(int beginIndex) {
        boolean taint = this.tainted;
        if (beginIndex == this.str.length()) {
            taint = false;
        }
        return new IASString(this.str.substring(beginIndex), taint);
    }

    public IASStringable substring(int beginIndex, int endIndex) {
        boolean taint = this.tainted;
        if (beginIndex == endIndex) {
            taint = false;
        }
        return new IASString(this.str.substring(beginIndex, endIndex), taint);
    }

    public CharSequence subSequence(int beginIndex, int endIndex) {
        boolean taint = this.tainted;
        if (beginIndex == endIndex) {
            taint = false;
        }
        return new IASString(this.str.subSequence(beginIndex, endIndex), taint);
    }

    public IASStringable concat(IASStringable str) {
        return new IASString(this.str.concat(str.getString()), this.tainted || str.isTainted());
    }

    public IASStringable replace(char oldChar, char newChar) {
        return new IASString(this.str.replace(oldChar, newChar), this.tainted);
    }

    public boolean matches(IASStringable regex) {
        return this.str.matches(regex.getString());
    }

    public boolean contains(CharSequence s) {
        return this.str.contains(s);
    }

    public IASStringable replaceFirst(IASStringable regex, IASStringable replacement) {
        // TODO: this seems pretty expensive..
        boolean taint = this.tainted;
        Pattern p = Pattern.compile(regex.getString());
        Matcher m = p.matcher(this.str);
        if (m.find()) {
            taint |= replacement.isTainted();
        }
        String result = this.str.replaceFirst(regex.getString(), replacement.getString());
        if (result.isEmpty()) {
            taint = false;
        }
        return new IASString(result, taint);
    }

    public IASStringable replaceAll(IASStringable regex, IASStringable replacement) {
        // TODO: this seems pretty expensive..
        boolean taint = this.tainted;
        Pattern p = Pattern.compile(regex.getString());
        Matcher m = p.matcher(this.str);
        if (m.find()) {
            taint |= replacement.isTainted();
        }
        String result = this.str.replaceAll(regex.getString(), replacement.getString());
        if (result.isEmpty()) {
            taint = false;
        }
        return new IASString(result, taint);
    }

    public IASStringable replace(CharSequence target, CharSequence replacement) {
        boolean taint = this.tainted;
        if (this.str.contains(target)) {
            if (replacement instanceof IASTaintAware) {
                IASTaintAware t = (IASTaintAware) replacement;
                taint |= t.isTainted();
            }
        }
        return new IASString(this.str.replace(target, replacement), taint);
    }

    // TODO: this propagates the taint for the whole string
    public IASStringable[] split(IASStringable regex, int limit) {
        String[] split = this.str.split(regex.getString(), limit);
        IASString[] splitted = new IASString[split.length];
        for (int i = 0; i < split.length; i++) {
            splitted[i] = new IASString(split[i], this.tainted);
        }
        return splitted;
    }

    // TODO: this propagates the taint for the whole string
    public IASStringable[] split(IASStringable regex) {
        String[] split = this.str.split(regex.getString());
        IASString[] splitted = new IASString[split.length];
        for (int i = 0; i < split.length; i++) {
            splitted[i] = new IASString(split[i], this.tainted);
        }
        return splitted;
    }

    public static IASString join(CharSequence delimiter, CharSequence... elements) {
        boolean taint = false;
        for (CharSequence cs : elements) {
            if (cs instanceof IASTaintAware) {
                IASTaintAware t = (IASTaintAware) cs;
                taint |= t.isTainted();
            }
        }
        // Don't forget the delimiter!
        if (delimiter instanceof IASTaintAware) {
            IASTaintAware t = (IASTaintAware) delimiter;
            taint |= t.isTainted();
        }
        return new IASString(String.join(delimiter, elements), taint);
    }


    public static IASString join(CharSequence delimiter,
                                 Iterable<? extends CharSequence> elements) {
        boolean taint = false;
        for (CharSequence cs : elements) {
            if (cs instanceof IASTaintAware) {
                IASTaintAware t = (IASTaintAware) cs;
                taint |= t.isTainted();
            }
        }
        // Don't forget the delimiter!
        if (delimiter instanceof IASTaintAware) {
            IASTaintAware t = (IASTaintAware) delimiter;
            taint |= t.isTainted();
        }
        return new IASString(String.join(delimiter, elements), taint);
    }

    public IASStringable toLowerCase(Locale locale) {
        return new IASString(this.str.toLowerCase(locale), this.tainted);
    }

    public IASStringable toLowerCase() {
        return new IASString(this.str.toLowerCase(), this.tainted);
    }

    public IASStringable toUpperCase(Locale locale) {
        return new IASString(this.str.toUpperCase(locale), this.tainted);
    }

    public IASStringable toUpperCase() {
        return new IASString(this.str.toUpperCase(), this.tainted);
    }

    public IASStringable trim() {
        String trimmed = this.str.trim();
        if (trimmed.isEmpty()) {
            return new IASString("");
        }
        return new IASString(trimmed, this.tainted);
    }

    /* JDK 11 BEGIN */
    public IASStringable strip() {
        String stripped = this.str.strip();
        if (stripped.isEmpty()) {
            return new IASString("");
        }
        return new IASString(stripped, this.tainted);
    }

    public IASStringable stripLeading() {
        String stripped = this.str.stripLeading();
        if (stripped.isEmpty()) {
            return new IASString("");
        }
        return new IASString(stripped, this.tainted);
    }

    public IASStringable stripTrailing() {
        String stripped = this.str.stripTrailing();
        if (stripped.isEmpty()) {
            return new IASString("");
        }
        return new IASString(stripped, this.tainted);
    }

    public boolean isBlank() {
        return this.str.isBlank();
    }

    public Stream<IASStringable> lines() {
        return this.str.lines().map(s -> new IASString(s, this.tainted));
    }

    public IASStringable repeat(int count) {
        if (count == 0) {
            return new IASString("");
        }
        return new IASString(this.str.repeat(count), this.tainted);
    }
    /* JDK 11 END */

    //TODO: sound?
    public String toString() {
        return this.str.toString();
    }

    public IASStringable toIASString() {
        return this;
    }

    public IntStream chars() {
        return this.str.chars();
    }

    public IntStream codePoints() {
        return this.str.codePoints();
    }

    public char[] toCharArray() {
        return this.str.toCharArray();
    }

    static boolean isTainted(Object[] args) {
        boolean tainted = false;
        for (Object obj : args) {
            if (obj instanceof IASTaintAware) {
                IASTaintAware ta = (IASTaintAware) obj;
                tainted |= ta.isTainted();
            }
        }
        return tainted;
    }

    //TODO: sound?
    public static IASString format(IASString format, Object... args) {
        //return new IASString(String.format(format.toString(), args), isTainted(args));
        return new IASFormatter().format(format, args).toIASString();
    }

    //TODO: sound?
    public static IASString format(Locale l, IASString format, Object... args) {
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
    public IASStringable intern() {
        return IASStringPool.intern(this);
    }


    public static IASString fromString(String str) {
        if (str == null) return null;

        return new IASString(str);
    }

    public static String asString(IASString str) {
        if (str == null) return null;
        return str.getString();
    }

    public String getString() {
        return this.str;
    }

    public static final Comparator<IASString> CASE_INSENSITIVE_ORDER
            = new CaseInsensitiveComparator();

    private static class CaseInsensitiveComparator
            implements Comparator<IASString>, java.io.Serializable {
        private static final long serialVersionUID = 8575799808933029326L;

        public int compare(IASString s1, IASString s2) {
            return s1.compareToIgnoreCase(s2);
        }
    }

}

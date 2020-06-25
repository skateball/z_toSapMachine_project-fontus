package de.tubs.cs.ias.asm_test.taintaware.range;

import de.tubs.cs.ias.asm_test.Constants;
import de.tubs.cs.ias.asm_test.taintaware.shared.IASStringBuilderable;
import de.tubs.cs.ias.asm_test.taintaware.shared.IASStringable;
import de.tubs.cs.ias.asm_test.taintaware.shared.IASTaintRange;
import de.tubs.cs.ias.asm_test.taintaware.shared.IASTaintSource;

import java.util.List;
import java.util.stream.IntStream;

@SuppressWarnings("Since15")
public final class IASStringBuffer extends IASAbstractStringBuilder {

    public IASStringBuffer(StringBuffer sb, IASTaintInformation taintInformation) {
        super();
        this.appendShifted(taintInformation.getAllRanges());
        this.builder.append(sb);
    }

    public IASStringBuffer(StringBuffer sb) {
        super(sb);
    }

    public IASStringBuffer() {
        super();
    }

    public IASStringBuffer(int capacity) {
        super(capacity);
    }

    public IASStringBuffer(IASStringable str) {
        super(str);
    }

    public IASStringBuffer(IASString str) {
        super(str);
    }

    public IASStringBuffer(CharSequence seq) {
        super(seq);
    }

    @Override
    public synchronized void setTaint(boolean taint) {
        super.setTaint(taint);
    }

    @Override
    public synchronized void setTaint(List<IASTaintRange> ranges) {
        super.setTaint(ranges);
    }

    @Override
    public synchronized boolean isTainted() {
        return super.isTainted();
    }

    @Override
    public synchronized IASStringBuffer append(Object obj) {
        return (IASStringBuffer) super.append(obj);
    }

    @Override
    public synchronized IASStringBuffer append(IASStringable str) {
        return (IASStringBuffer) super.append(str);
    }

    @Override
    public synchronized IASStringBuffer append(IASStringable str, boolean merge) {
        return (IASStringBuffer) super.append(str, merge);
    }

    @Override
    public synchronized IASStringBuffer append(IASStringBuilderable strb) {
        return (IASStringBuffer) super.append(strb);
    }

    @Override
    public synchronized IASStringBuffer append(CharSequence cs) {
        return (IASStringBuffer) super.append(cs);
    }

    @Override
    public synchronized IASStringBuffer append(CharSequence s, int start, int end) {
        return (IASStringBuffer) super.append(s, start, end);
    }

    @Override
    public synchronized IASStringBuffer append(char[] s, int offset, int len) {
        return (IASStringBuffer) super.append(s, offset, len);
    }

    @Override
    public synchronized IASStringBuffer append(char[] str) {
        return (IASStringBuffer) super.append(str);
    }

    @Override
    public synchronized IASStringBuffer append(boolean b) {
        return (IASStringBuffer) super.append(b);
    }

    @Override
    public synchronized IASStringBuffer append(char c) {
        return (IASStringBuffer) super.append(c);
    }

    @Override
    public synchronized IASStringBuffer append(int i) {
        return (IASStringBuffer) super.append(i);
    }

    @Override
    public synchronized IASStringBuffer append(long lng) {
        return (IASStringBuffer) super.append(lng);
    }

    @Override
    public synchronized IASStringBuffer append(float f) {
        return (IASStringBuffer) super.append(f);
    }

    @Override
    public synchronized IASStringBuffer append(double d) {
        return (IASStringBuffer) super.append(d);
    }

    @Override
    public synchronized IASStringBuffer appendCodePoint(int codePoint) {
        return (IASStringBuffer) super.appendCodePoint(codePoint);
    }

    @Override
    public synchronized IASStringBuffer delete(int start, int end) {
        return (IASStringBuffer) super.delete(start, end);
    }

    @Override
    public synchronized IASStringBuffer deleteCharAt(int index) {
        return (IASStringBuffer) super.deleteCharAt(index);
    }

    @Override
    public synchronized IASStringBuffer replace(int start, int end, IASStringable str) {
        return (IASStringBuffer) super.replace(start, end, str);
    }

    @Override
    public synchronized IASStringBuffer insert(int index, char[] str, int offset, int len) {
        return (IASStringBuffer) super.insert(index, str, offset, len);
    }

    @Override
    public synchronized IASStringBuffer insert(int offset, Object obj) {
        return (IASStringBuffer) super.insert(offset, obj);
    }

    @Override
    public synchronized IASStringBuffer insert(int offset, IASStringable str) {
        return (IASStringBuffer) super.insert(offset, str);
    }

    @Override
    public synchronized IASStringBuffer insert(int offset, char[] str) {
        return (IASStringBuffer) super.insert(offset, str);
    }

    @Override
    public synchronized IASStringBuffer insert(int dstOffset, CharSequence s) {
        return (IASStringBuffer) super.insert(dstOffset, s);
    }

    @Override
    public synchronized IASStringBuffer insert(int dstOffset, CharSequence s, int start, int end) {
        return (IASStringBuffer) super.insert(dstOffset, s, start, end);
    }

    @Override
    public synchronized IASStringBuffer insert(int offset, boolean b) {
        return (IASStringBuffer) super.insert(offset, b);
    }

    @Override
    public synchronized IASStringBuffer insert(int offset, char c) {
        return (IASStringBuffer) super.insert(offset, c);
    }

    @Override
    public synchronized IASStringBuffer insert(int offset, int i) {
        return (IASStringBuffer) super.insert(offset, i);
    }

    @Override
    public synchronized IASStringBuffer insert(int offset, long l) {
        return (IASStringBuffer) super.insert(offset, l);
    }

    @Override
    public synchronized IASStringBuffer insert(int offset, float f) {
        return (IASStringBuffer) super.insert(offset, f);
    }

    @Override
    public synchronized IASStringBuffer insert(int offset, double d) {
        return (IASStringBuffer) super.insert(offset, d);
    }

    @Override
    public synchronized int indexOf(IASStringable str) {
        return super.indexOf(str);
    }

    @Override
    public synchronized int indexOf(IASStringable str, int fromIndex) {
        return super.indexOf(str, fromIndex);
    }

    @Override
    public synchronized int lastIndexOf(IASStringable str) {
        return super.lastIndexOf(str);
    }

    @Override
    public synchronized int lastIndexOf(IASStringable str, int fromIndex) {
        return super.lastIndexOf(str, fromIndex);
    }

    @Override
    public synchronized IASStringBuffer reverse() {
        return (IASStringBuffer) super.reverse();
    }

    @Override
    public synchronized String toString() {
        return super.toString();
    }

    @Override
    public synchronized IASString toIASString() {
        return super.toIASString();
    }

    @Override
    public synchronized int capacity() {
        return super.capacity();
    }

    @Override
    public synchronized IASString substring(int start) {
        return super.substring(start);
    }

    @Override
    public synchronized IASString substring(int start, int end) {
        return super.substring(start, end);
    }

    @Override
    public synchronized void setCharAt(int index, char c) {
        super.setCharAt(index, c);
    }

    @Override
    public synchronized void ensureCapacity(int minimumCapacity) {
        super.ensureCapacity(minimumCapacity);
    }

    @Override
    public synchronized void trimToSize() {
        super.trimToSize();
    }

    @Override
    public synchronized int length() {
        return super.length();
    }

    @Override
    public synchronized char charAt(int index) {
        return super.charAt(index);
    }

    @Override
    public synchronized CharSequence subSequence(int start, int end) {
        return super.subSequence(start, end);
    }

    @Override
    public synchronized IntStream chars() {
        return super.chars();
    }

    @Override
    public synchronized IntStream codePoints() {
        return super.codePoints();
    }

    @Override
    public synchronized StringBuilder getBuilder() {
        return super.getBuilder();
    }

    public synchronized StringBuffer getBuffer() {
        return new StringBuffer(super.getBuilder());
    }

    @Override
    public synchronized void setLength(int newLength) {
        super.setLength(newLength);
    }

    @Override
    public synchronized IASTaintInformation getTaintInformation() {
        return super.getTaintInformation();
    }

    @Override
    public synchronized boolean isUninitialized() {
        return super.isUninitialized();
    }

    @Override
    public synchronized boolean isTaintedAt(int index) {
        return super.isTaintedAt(index);
    }

    @Override
    public synchronized void initialize() {
        super.initialize();
    }

    @Override
    public synchronized void setTaint(IASTaintSource source) {
        super.setTaint(source);
    }

    @Override
    protected synchronized void appendShifted(List<IASTaintRange> ranges) {
        super.appendShifted(ranges);
    }

    @Override
    protected synchronized List<IASTaintRange> getAllRanges() {
        return super.getAllRanges();
    }

    @Override
    public synchronized List<IASTaintRange> getTaintRanges() {
        return super.getTaintRanges();
    }

    @Override
    public synchronized int compareTo(IASStringBuilderable o) {
        return super.compareTo(o);
    }
}

package de.tubs.cs.ias.asm_test.taintaware.bool;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

public final class IASStringUtils {
    private static final Pattern CONCAT_PLACEHOLDER = Pattern.compile("\u0001");

    public static void arraycopy(Object src,
                                 int srcPos,
                                 Object dest,
                                 int destPos,
                                 int length) {
        Object source = src;
        if (src instanceof String[]) {
            String[] strSrc = (String[]) src;
            source = convertStringArray(strSrc);
        }
        System.arraycopy(source, srcPos, dest, destPos, length);
    }

    public static IASString fromObject(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return new IASString((String) obj);
        } else if (obj instanceof IASString) {
            return (IASString) obj;
        } else if (obj instanceof IASStringBuilder) {
            IASStringBuilder b = (IASStringBuilder) obj;
            return new IASString((IASString) b.toIASString());
        } else if (obj instanceof IASStringBuffer) {
            IASStringBuffer b = (IASStringBuffer) obj;
            return new IASString((IASString) b.toIASString());
        } else {

            throw new IllegalArgumentException(String.format("Obj is of type %s, but only String or TString are allowed!", obj.getClass().getName()));
        }
    }

    public static List<IASString> convertStringList(List<String> lst) {
        List<IASString> alst = new ArrayList<>(lst.size());
        for (String s : lst) {
            alst.add(IASString.fromString(s));
        }
        return alst;
    }

    public static List<String> convertTStringList(List<IASString> tlst) {
        List<String> alst = new ArrayList<>(tlst.size());
        for (IASString s : tlst) {
            alst.add(s.getString());
        }
        return alst;
    }

    public static IASString[] convertStringArray(String[] arr) {
        if (arr == null) return null;
        IASString[] ret = new IASString[arr.length];
        for (int i = 0; i < arr.length; i++) {
            String s = arr[i];
            IASString ts = new IASString(s);
            ret[i] = ts;
        }
        return ret;
    }

    public static String[] convertTaintAwareStringArray(IASString[] arr) {
        if (arr == null) return null;
        String[] ret = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            IASString s = arr[i];
            ret[i] = s.getString();
        }
        return ret;
    }

    public static Hashtable<String, String> convertTStringToTStringHashTable(Hashtable<IASString, IASString> tbl) {
        Hashtable<String, String> result = new Hashtable<>();
        tbl.forEach((key, value) -> result.put(key.getString(), value.getString()));
        return result;
    }

    public static IASString concat(String format, Object... args) {
        String ret = format;
        boolean taint = false;
        for (int i = args.length - 1; i >= 0; i--) {
            Object a = args[i];
            if (a instanceof IASString) {
                IASString strArg = (IASString) a;
                taint |= strArg.isTainted();
            }
            String arg = a == null ? "null" : a.toString();
            ret = CONCAT_PLACEHOLDER.matcher(ret).replaceFirst(arg);
        }
        return new IASString(ret, taint);

    }

    private IASStringUtils() {

    }
}

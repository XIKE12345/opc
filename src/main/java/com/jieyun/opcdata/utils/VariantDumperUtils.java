package com.jieyun.opcdata.utils;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIVariant;

/**
 * @CalssName VariantDumperUtils
 * @Desc TODO
 * @Author huiKe
 * @email <link>754873891@qq.com </link>
 * @CreateDate 2020/1/3
 * @Version 1.0
 **/
public class VariantDumperUtils {
//    protected Object dumpArray (final String prefix, final JIArray array ) throws JIException
//    {
//        System.out.println ( prefix + String.format ( "IsConformant: %s, IsVarying: %s", array.isConformant () ? "yes" : "no", array.isVarying () ? "yes" : "no" ) );
//        System.out.println ( prefix + String.format ( "Dimensions: %d", array.getDimensions () ) );
//        for ( int i = 0; i < array.getDimensions (); i++ )
//        {
//            System.out.println ( prefix + String.format ( "Dimension #%d: Upper Bound: %d", i, array.getUpperBounds ()[i] ) );
//        }
//
//        final Object o = array.getArrayInstance ();
//        System.out.println ( prefix + "Array Instance: " + o.getClass () );
//        final Object[] a = (Object[])o;
//        System.out.println ( prefix + "Array Size: " + a.length );
//
//        for ( final Object value : a )
//        {
//            String s = dumpValue(prefix + "\t", value);
//        }
//        return null;
//    }

    public static String dumpValue(final Object value) throws JIException {
        String finalValue = dumpValue("", value);
        return finalValue;
    }

    public static String dumpValue(final String prefix, final Object value) throws JIException {
        String finalValue = "";
        if (value instanceof JIVariant) {
            System.out.println(prefix + "JIVariant");
            final JIVariant variant = (JIVariant) value;
            System.out.println(prefix + String.format("IsArray: %s, IsByRef: %s, IsNull: %s", variant.isArray() ? "yes" : "no", variant.isByRefFlagSet() ? "yes" : "no", variant.isNull() ? "yes" : "no"));

            if (variant.isArray()) {
                // dumpArray ( prefix, variant.getObjectAsArray () );
            } else {
                finalValue = dumpValue(prefix + "\t", variant.getObject());
            }
        } else if (value instanceof JIString) {
            final JIString string = (JIString) value;

            String strType;
            switch (string.getType()) {
                case JIFlags.FLAG_REPRESENTATION_STRING_BSTR:
                    strType = "BSTR";
                    break;
                case JIFlags.FLAG_REPRESENTATION_STRING_LPCTSTR:
                    strType = "LPCSTR";
                    break;
                case JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR:
                    strType = "LPWSTR";
                    break;
                default:
                    strType = "unknown";
                    break;
            }
            System.out.println(prefix + String.format("JIString: '%s' (%s)", string.getString(), strType));
        } else if (value instanceof Double) {
            System.out.println(prefix + "Double: " + value);
            finalValue = String.valueOf(value);
        } else if (value instanceof Float) {
            System.out.println(prefix + "Float: " + value);
            finalValue = String.valueOf(value);
        } else if (value instanceof Byte) {
            System.out.println(prefix + "Byte: " + value);
            finalValue = String.valueOf(value);
        } else if (value instanceof Character) {
            System.out.println(prefix + "Character: " + value);
            finalValue = String.valueOf(value);
        } else if (value instanceof Integer) {
            System.out.println(prefix + "Integer: " + value);
            finalValue = String.valueOf(value);
        } else if (value instanceof Short) {
            System.out.println(prefix + "Short: " + value);
            finalValue = String.valueOf(value);
        } else if (value instanceof Long) {
            System.out.println(prefix + "Long: " + value);
            finalValue = String.valueOf(value);
        } else if (value instanceof Boolean) {
            System.out.println(prefix + "Boolean: " + value);
            finalValue = String.valueOf(value);
        } else {
            System.out.println(prefix + String.format("Unknown value type (%s): %s", value.getClass(), value.toString()));
            finalValue = String.valueOf(value);
        }
        return finalValue;
    }

}

package com.landenlabs.all_webviewtester.util;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldennis on 8/15/16.
 */
enum ClassMember { CONSTRUCTOR, FIELD, METHOD, CLASS, ALL }

public class ClassSpy {
    public static List<String> getClassData(Object inClass) {
        List<String> list = new ArrayList<>();

        try {
            Class<?> c = inClass.getClass();    // Class.forName(args[0]);
            list.add(String.format("Class:%n  %s%n%n", c.getCanonicalName()));

            Package p = c.getPackage();
            list.add(String.format("Package:%n  %s%n%n",
                    (p != null ? p.getName() : "-- No Package --")));

            getMembers(list, inClass, c.getFields(), "Fields");
            /*
            for (int i = 1; i < args.length; i++) {
                switch (ClassMember.valueOf(args[i])) {
                    case CONSTRUCTOR:
                        printMembers(c.getConstructors(), "Constructor");
                        break;
                    case FIELD:
                        printMembers(c.getFields(), "Fields");
                        break;
                    case METHOD:
                        printMembers(c.getMethods(), "Methods");
                        break;
                    case CLASS:
                        printClasses(c);
                        break;
                    case ALL:
                        printMembers(c.getConstructors(), "Constuctors");
                        printMembers(c.getFields(), "Fields");
                        printMembers(c.getMethods(), "Methods");
                        printClasses(c);
                        break;
                    default:
                        assert false;
                }
            }
            */

            // production code should handle these exceptions more gracefully
        } catch (Exception x) { // ClassNotFoundException x) {
            x.printStackTrace();
        }

        return list;
    }

    private static void getMembers(List<String> outList, Object obj, Member[] mbrs, String s) {
        for (Member mbr : mbrs) {
            if (mbr instanceof Field) {
                Field field = (Field)mbr;
                try {
                    outList.add(String.format("%s = %s", field.toGenericString(), field.get(obj).toString()));
                } catch (Exception ex) {
                    outList.add(String.format("%s = <error>", field.toGenericString()));
                }
            }
        }
    }

    /*
    private static void printMembers(Member[] mbrs, String s) {
        out.format("%s:%n", s);
        for (Member mbr : mbrs) {
            if (mbr instanceof Field)
                out.format("  %s%n", ((Field)mbr).toGenericString());
            else if (mbr instanceof Constructor)
                out.format("  %s%n", ((Constructor)mbr).toGenericString());
            else if (mbr instanceof Method)
                out.format("  %s%n", ((Method)mbr).toGenericString());
        }
        if (mbrs.length == 0)
            out.format("  -- No %s --%n", s);
        out.format("%n");
    }

    private static void printClasses(Class<?> c) {
        out.format("Classes:%n");
        Class<?>[] clss = c.getClasses();
        for (Class<?> cls : clss)
            out.format("  %s%n", cls.getCanonicalName());
        if (clss.length == 0)
            out.format("  -- No member interfaces, classes, or enums --%n");
        out.format("%n");
    }
    */
}
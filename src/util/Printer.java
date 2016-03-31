package util;

import java.util.Collection;

import org.eclipse.swt.graphics.Point;

//TODO: refactoring - all methods should return String and don't use "System.out.print" at all
//      after refactoring rename class to Convertor 
public class Printer
{
    public static void print(Collection<Point> collection)
    {
        System.out.print("[" + collection.size() + "]");
        System.out.print("{");
        for (Point point : collection)
        {
            System.out.print("(" + point.x + ", " + point.y + ")");
        }

        System.out.println("}");
    }

    public static void print(String name, Collection<Point> collection)
    {
        System.out.print(name + "=");
        print(collection);
    }

    public static void print(int x, int y)
    {
        System.out.println("(" + x + ", " + y + ")");
    }
}

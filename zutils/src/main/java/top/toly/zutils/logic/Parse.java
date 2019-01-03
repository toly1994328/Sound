package top.toly.zutils.logic;


import top.toly.zutils.core.domain.DrawInfo;
import top.toly.zutils.core.domain.Pos;
import top.toly.zutils.core.domain.Triangle;

/**
 * 作者：张风捷特烈
 * 时间：2018/7/5:9:14
 * 邮箱：1981462002@qq.com
 * 说明：图形解析类
 */
public class Parse {

    private static boolean canParse = true;

    /**
     * 解析直线
     *
     * @param line
     * @returns {*}
     */
    public static DrawInfo line(DrawInfo line) {

        Parse.getLine(line);


        if (Logic.isExist(line.p0, line.p1)) {
            Pos p0 = line.p0;
            Pos p1 = line.p1;

            float c = Logic.disPos2d(p0, p1);
            float ang = Logic.angleOf2Pos(p0, p1);
            line.c = c;
            line.ang = ang;
            Parse.getLine(line);
            return line;
        }

        if (Logic.isExist(line.p0, line.c, line.ang)) {
            Pos p0 = line.p0;
            float c = line.c;
            float ang = line.ang;

            line.p1 = new Pos(Math.round(p0.x + Math.sin(ang) * c), Math.round(p0.y + Math.cos(ang) * c));
            Parse.getLine(line);
            return line;
        }


        return line;
    }


    private static DrawInfo getLine(DrawInfo line) {


        //存在两点时
        if (Logic.isExist(line.p0, line.p1)) {
            Pos p0 = line.p0;
            Pos p1 = line.p1;

            if (p1.x == p0.x) {
                line.ang = (float) (Math.PI / 2);
                return line;
            }

            float k = (p1.y - p0.y) / (p1.x - p0.x);
            float ang = (float)Math.atan(k);
            line.ang = ang;

            //两点式
            if (Logic.isExist(line.x)) {
                float x = line.x;

                line.y = k * (x - p0.x) + p0.y;
                return line;
            }
            if (Logic.isExist(line.y)) {
                float y = line.y;
                line.x = (y - p0.y) / k + p0.x;
                return line;
            }

            line.k = k;
            line.b0 = p0.y - k * p0.x;
            return line;
        }

        //点斜式
        if (Logic.isExist(line.p0, line.k)) {
            Pos p0 = line.p0;
            float k = line.k;

            line.p1.x = p0.x + 1;
            line.p1.y = p0.y + k;
        }
        if (Logic.isExist(line.p1, line.k)) {
            Pos p1 = line.p1;
            float k = line.k;

            line.p0.x = p1.x + 1;
            line.p0.y = p1.y + k;
        }
        if (Logic.isExist(line.k, line.b0)) {

            line.p0.x = 0.f;
            line.p0.y = line.b0;
        }


        return Parse.getLine(line);
    }


    /**
     * 根据三角形边角三参，推算其他信息：自辨函数
     *
     * @param triangle 注：度数返回弧度制的
     * @returns {*} 2018-6-17 00:38:33
     */
    public static Triangle triangle(Triangle triangle) {

//        if (canParse) {
//            // abB     abA     acA     acC     bcC     bcB     ABC
//            //两边+非夹角及三个角无法确定唯一三角形，故不可解
//            if (Logic.isExist(triangle.a, triangle.b, triangle.degB) ||
//                    Logic.isExist(triangle.a, triangle.b, triangle.degA) ||
//                    Logic.isExist(triangle.a, triangle.c, triangle.degA) ||
//                    Logic.isExist(triangle.a, triangle.c, triangle.degC) ||
//                    Logic.isExist(triangle.c, triangle.b, triangle.degB) ||
//                    Logic.isExist(triangle.c, triangle.b, triangle.degC) ||
//                    Logic.isExist(triangle.degA, triangle.degB, triangle.degC)) {
//                canParse = false;
//                return null;
//            }
//
//        }

/////////////////////3///////两边一角--解三角形///////////////////////////////////
        if (Logic.isExist(triangle.a, triangle.b, triangle.degC)) {
            float a = triangle.a;
            float b = triangle.b;
            float degC = triangle.degC;

            float c = Law.cosGetL(degC, a, b);
            float degA = Law.cosGetDeg(a, b, c);
            float degB = Law.cosGetDeg(b, a, c);
            triangle.c = c;
            triangle.degA = degA;
            triangle.degB = degB;

            if (a + b <= c || b + c <= a || a + c <= b || a <= 0 || b <= 0 || c <= 0) {
                return null;
            }
            return triangle;
        }

        float a = 0.f, b = 0.f, c = 0.f, degA = 0.f, degB = 0.f, degC = 0.f;
        if (Logic.isExist(triangle.c, triangle.a, triangle.degB)) {
            c = triangle.c;
            a = triangle.a;
            degB = triangle.degB;

            b = Law.cosGetL(c, a, degB);
            degC = Law.cosGetDeg(c, b, a);
        }

        if (Logic.isExist(triangle.b, triangle.c, triangle.degA)) {
            b = triangle.b;
            c = triangle.c;
            degA = triangle.degA;

            a = Law.cosGetL(degA, b, c);
            degC = Law.cosGetDeg(c, b, a);
        }
/////////////////1///////////三边--解三角形///////////////////////////////////
        if (Logic.isExist(triangle.a, triangle.b, triangle.c)) {
            a = triangle.a;
            b = triangle.b;
            c = triangle.c;
            degC = Law.cosGetDeg(a, b, c);
        }
/////////////////9///////////两脚一边--解三角形///////////////////////////////////
        if (Logic.isExist(triangle.degA, triangle.degB, triangle.a)) {
            a = triangle.a;
            degA = triangle.degA;
            degB = triangle.degB;

            degC = (float) (Math.PI - degA - degB);
            c = Law.sinGetL(degC, degA, a);
            b = Law.cosGetL(degB, a, c);
        }

        if (Logic.isExist(triangle.degA, triangle.degB, triangle.b)) {
            b = triangle.b;
            degA = triangle.degA;
            degB = triangle.degB;

            degC = (float) (Math.PI - degA - degB);
            a = Law.sinGetL(degA, degB, b);

        }

        if (Logic.isExist(triangle.degA, triangle.degB, triangle.c)) {
            c = triangle.c;
            degA = triangle.degA;
            degB = triangle.degB;

            degC = (float) (Math.PI - degA - degB);
            a = Law.sinGetL(degA, degC, c);
            b = Law.cosGetL(degB, a, c);
        }
        ////////////////
        if (Logic.isExist(triangle.degA, triangle.degC, triangle.a)) {
            a = triangle.a;
            degA = triangle.degA;
            degC = triangle.degC;

            degB = (float) (Math.PI - degA - degC);
            c = Law.sinGetL(degC, degA, a);
            b = Law.cosGetL(degB, a, c);
        }
        if (Logic.isExist(triangle.degA, triangle.degC, triangle.b)) {
            b = triangle.b;
            degA = triangle.degA;
            degC = triangle.degC;

            degB = (float) (Math.PI - degA - degC);
            a = Law.sinGetL(degA, degB, b);
            c = Law.cosGetL(degC, a, b);
        }
        if (Logic.isExist(triangle.degA, triangle.degC, triangle.c)) {
            c = triangle.c;
            degA = triangle.degA;
            degC = triangle.degC;

            degB = (float) (Math.PI - degA - degC);
            a = Law.sinGetL(degA, degC, c);
            b = Law.cosGetL(degB, a, c);

        }

        ///////////
        if (Logic.isExist(triangle.degB, triangle.degC, triangle.a)) {
            a = triangle.a;
            degB = triangle.degB;
            degC = triangle.degC;

            degA = (float) (Math.PI - degB - degC);
            c = Law.sinGetL(degC, degA, a);
            b = Law.cosGetL(degB, a, c);

        }
        if (Logic.isExist(triangle.degB, triangle.degC, triangle.b)) {
            b = triangle.b;
            degB = triangle.degB;
            degC = triangle.degC;

            degA = (float) (Math.PI - degB - degC);
            a = Law.sinGetL(degA, degB, b);
            c = Law.sinGetL(degC, degA, a);

        }
        if (Logic.isExist(triangle.degB, triangle.degC, triangle.c)) {
            c = triangle.c;
            degB = triangle.degB;
            degC = triangle.degC;

            degA = (float) (Math.PI - degB - degC);
            a = Law.sinGetL(degA, degC, c);
            b = Law.cosGetL(degB, a, c);

        }
        triangle.a = a;
        triangle.b = b;
        triangle.c = c;
        triangle.degA = degA;
        triangle.degB = degB;
        triangle.degC = degC;

        return Parse.triangle(triangle);
    }


//    static getPosBy2Line(line1, line2) {
//        var x = (line2.b0 - line1.b0) / (line1.k - line2.k);
//        line1._x = x;
//        this.getLine(line1);
//        return {x:line1._x, y:line1._y}
//    }
}

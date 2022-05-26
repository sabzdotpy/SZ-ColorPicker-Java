package szcolorpicker;

import java.awt.Color;

class Conversion {

    private static float[] hsvStore = new float[3];
    private static String[] rgbStore = new String[3];
    private static StringBuilder builder = new StringBuilder();

    static String rgbToHex(int r, int g, int b) {
        return (Integer.toHexString(new Color(r, g, b).getRGB()));
    }

    static String rgbToHsv(int r, int g, int b) {
        hsvStore = Color.RGBtoHSB( r, g, b, null );

        return (int)(hsvStore[0]*360) + "," + (int)(hsvStore[1]*100) + "," + (int)(hsvStore[2]*100);
    }

    static String hsvToRGB(double h, double s, double v) {

        h = h / 360;
        s = s / 100;
        v = v / 100;

        System.out.println(h + ", " + s + ", " + v);


        if (s == 0) { return (v + "," + v + "," + v); }
        int i = (int)(h * 6.0);
        double f = (h * 6.0) - i;
        double p = v * (1.0 - s);
        double q = v * (1.0 - s * f);
        double t = v * (1.0 - s * (1.0 - f));
        i = i % 6;

        if (i == 0) {
            return (int)(v*255) + "," + (int)(t*255) + "," + (int)(p*255);
        }
        if (i == 1) {
            return (int)(q*255) + "," + (int)(v*255) + "," + (int)(p*255);
        }
        if (i == 2) {
            return (int)(p*255) + "," + (int)(v*255) + "," + (int)(t*255);
        }
        if (i == 3) {
            return (int)(p*255) + "," + (int)(q*255) + "," + (int)(v*255);
        }
        if (i == 4) {
            return (int)(t*255) + "," + (int)(p*255) + "," + (int)(v*255);
        }
        if (i == 5) {
            return (int)(v*255) + "," + (int)(p*255) + "," + (int)(q*255);
        }

        return "x,x,x";
    }

    static String hsvToHEX(double h, double s, double v) {
        String[] hStore = (hsvToRGB(h, s, v)).split(",");

        return rgbToHex( Integer.parseInt(hStore[0]), Integer.parseInt(hStore[1]), Integer.parseInt(hStore[2]) );
    }

    static String hexToRGB(String hex) {
        builder.setLength(0);

        for (int i = 0; i < 5; i+=2 ) {
            builder.append(Integer.parseInt(hex.substring(i, i + 2), 16)).append(",");
        }

        return ( builder.toString().substring(0, builder.toString().length() - 1) );
    }

    static String hexToHSV(String hex) {
        rgbStore = hexToRGB(hex).split(",");
        return rgbToHsv( Integer.parseInt(rgbStore[0]), Integer.parseInt(rgbStore[1]), Integer.parseInt(rgbStore[2]) );
    }

}

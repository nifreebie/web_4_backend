package web.web4_backend.util;

public class AreaChecker {
    public static boolean isHit(float x, float y, float r) {
        return inRect(x, y, r) || inTriangle(x, y, r) || inCircle(x, y, r);
    }

    private static boolean inRect(float x, float y, float r) {
        return x <= r / 2 && x >= 0 && y <= r && y >= 0;
    }

    private static boolean inTriangle(float x, float y, float r) {
        return x <= 0 && y <= 0 && (y >= -0.5 * x - r / 2);
    }

    private static boolean inCircle(float x, float y, float r) {
        return x >= 0 && y <= 0 && (Math.pow(x, 2) + Math.pow(y, 2)) <= Math.pow(r, 2);
    }
}

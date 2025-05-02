public final class Assertion {

    private Assertion() {}

    public static boolean assertEquals(Object expected, Object actual) {

        expected = expected != null ? expected : "null";
        actual = actual != null ? actual: "null";

        if (!expected.equals(actual)) {
            System.out.println("FAIL - Expected: " + expected + ", Got: " + actual);
            return false;
        }
        return true;
    }
    
    public static boolean assertTrue(boolean condition, String message) {
        if (!condition) {
            System.out.println("FAIL - " + message);
            return false;
        }
        return true;
    }
    
    public static boolean assertFalse(boolean condition, String message) {
        if (condition) {
            System.out.println("FAIL - " + message);
            return false;
        }
        return true;
    }

}

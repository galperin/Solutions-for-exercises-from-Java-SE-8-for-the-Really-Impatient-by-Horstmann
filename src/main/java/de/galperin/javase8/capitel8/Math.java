package de.galperin.javase8.capitel8;

/**
* User: eugen
* Date: 13.12.14
*/
class Math {

    @TestCase(params = 4, expected = 24)
    @TestCase(params = 0, expected = 1)
    public static int factorial(int n) {
        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
}

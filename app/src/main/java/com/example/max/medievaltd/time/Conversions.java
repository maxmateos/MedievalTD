package com.example.max.medievaltd.time;

public class Conversions {

    public static final int MILLIS_TO_NANOS_FACTOR = 1000000; // (1e+6) =  millis (1e+3) / nanos (1e+9)
    public static final int SECONDS_TO_MILLIS_FACTOR = 1000;

    public static long nanosElapsedSince(long nanos) {
        return System.nanoTime() - nanos;
    }

    public static long millisElapsedSince(long nanos) {
        return convertNanosToMillis(nanosElapsedSince(nanos));
    }

    public static long secondsElapsedSince(long nanos) {
        return convertMillisToSeconds(millisElapsedSince(nanos));
    }

    public static long convertNanosToMillis(long nanos) {
        return nanos / MILLIS_TO_NANOS_FACTOR;
    }

    public static long convertMillisToSeconds(long nanos) {
        return nanos / SECONDS_TO_MILLIS_FACTOR;
    }
}

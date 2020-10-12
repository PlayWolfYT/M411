package ch.bbzsogr.playwolf.hashmap.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import ch.bbzsogr.playwolf.hashmap.exceptions.MathException;

public class MathHelper {
    

    /**
     * 
     * Get the average value for an ArrayList with numbers.
     * 
     * @param <T> Class of return type
     * @param data The data in form of an ArrayList
     * @param returnType The type that should get returned
     * @return The average of the array.
     * @throws MathException
     */
    @SuppressWarnings("unchecked")
    public static <T> T averageOfArray(Object[] data, Class<T> returnType) throws MathException {

        // Check what kind of value we need to return. (Java doesnt allow switch for undeclared types...)
        if (returnType == Integer.class) {

            int avg = 0;

            for (int i = 0; i < data.length; i++) {
                avg += Integer.valueOf("" + data[i]);
            }

            return (T) Integer.valueOf(avg / data.length);
        } else if (returnType == Double.class) {

            double avg = 0;

            for (int i = 0; i < data.length; i++) {
                avg += Double.valueOf("" + data[i].toString());
            }

            return (T) Double.valueOf(avg / data.length);

        } else if (returnType == Float.class) {

            float avg = 0;

            for (int i = 0; i < data.length; i++) {
                avg += Float.valueOf("" + data[i].toString());
            }

            return (T) Float.valueOf(avg / data.length);
        } else if (returnType == Long.class) {

            long avg = 0;

            for (int i = 0; i < data.length; i++) {
                avg += Long.valueOf("" + data[i]);
            }

            return (T) Long.valueOf(avg / data.length);
        } else {
            throw new MathException("Unsupported Type of data.");
        }

    }
    
    @SuppressWarnings("unchecked")
    public static <T> T sumOfArray(T[] data, Class<T> returnType) throws MathException {

        // Check what kind of value we need to return. (Java doesnt allow switch for undeclared types...)
        if (returnType == Integer.class) {

            int sum = 0;

            for (int i = 0; i < data.length; i++) {
                sum += Integer.valueOf("" + data[i]);
            }

            return (T) Integer.valueOf(sum);
        } else if (returnType == Double.class) {

            double sum = 0;

            for (int i = 0; i < data.length; i++) {
                sum += Double.valueOf("" + data[i]);
            }

            return (T) Double.valueOf(sum);

        } else if (returnType == Float.class) {

            float sum = 0;

            for (int i = 0; i < data.length; i++) {
                sum += Float.valueOf("" + data[i]);
            }

            return (T) Float.valueOf(sum);
        } else if (returnType == Long.class) {

            long sum = 0;

            for (int i = 0; i < data.length; i++) {
                sum += Long.valueOf("" + data[i]);
            }

            return (T) Long.valueOf(sum);
        } else {
            throw new MathException("Unsupported Type of data.");
        }
    }
    

    public static class BigInt {

    }

    public static class BigDec {

        private BigDecimal decimal;

        public BigDec(String val) {
            this.decimal = new BigDecimal(val);
        }

        public BigDec(Number val) {
            this.decimal = new BigDecimal(val.floatValue());
        }

        public void mult(Number val) {
            this.decimal = this.decimal.multiply(new BigDec(val).getDecimal());
        }

        public void multiply(Number val) {
            mult(val);
        }

        public void add(Number val) {
            this.decimal = this.decimal.add(new BigDec(val).getDecimal());
        }

        public void sub(Number val) {
            this.decimal = this.decimal.subtract(new BigDec(val).getDecimal());
        }

        public void subtract(Number val) {
            sub(val);
        }

        public void div(Number val) {
            this.decimal = this.decimal.divide(new BigDec(val).getDecimal());
        }

        public void div(Number val, RoundingMode roundingMode) {
            this.decimal = this.decimal.divide(new BigDec(val).getDecimal(), roundingMode);
        }

        public void divide(Number val) {
            div(val);
        }

        public void divide(Number val, RoundingMode roundingMode) {
            div(val, roundingMode);
        }

        public BigDecimal getDecimal() {
            return this.decimal;
        }

        @SuppressWarnings("unchecked")
        public <T> T getAs(Class<T> returnType) throws MathException {

            if (Number.class.isAssignableFrom(returnType)) {
                if (returnType == Float.class)
                    return (T) Float.valueOf(this.decimal.floatValue());
                else if(returnType == Double.class)
                    return (T) Double.valueOf(this.decimal.doubleValue());
                else if(returnType == Long.class)
                    return (T) Long.valueOf(this.decimal.longValue());
                else if(returnType == Integer.class)
                    return (T) Integer.valueOf(this.decimal.intValue());
                else
                    throw new MathException("Unsupported Type of data.");
            } else {
                throw new MathException("Unsupported Type of data.");
            }
        }

    }

}

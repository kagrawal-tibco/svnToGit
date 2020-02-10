package com.tibco.cep.query.stream.util;

/*
 * Author: Ashwin Jayaprakash Date: Jan 22, 2008 Time: 6:32:18 PM
 */

public final class ArrayPool {
    /**
     * {@value}.
     */
    public static final int DEFAULT_FRAGMENT_START_SIZE = 16;

    /**
     * {@value}
     */
    public static final int DEFAULT_FRAGMENT_MAX_SIZE = 8192;

    /**
     * Indicates the pool size for {@value} classes/array sizes. Anything larger will not be pooled,
     * hoping that they are requested very rarely. Classes/array sizes are: 1 << ({@link
     * #fragmentStartSizeLogBase2}) to 1 << ({@link # fragmentStartSizeLogBase2} + {@value}).
     */
    // log_2(2^13) - log_2(2^4) = 9;
    public static final int DEFAULT_NUM_CLASSES = Helper.floorLogBase2(DEFAULT_FRAGMENT_MAX_SIZE)
            - Helper.floorLogBase2(DEFAULT_FRAGMENT_START_SIZE);

    protected final int numClasses;

    protected final int fragmentStartSizeLogBase2;

    protected final SimplePool<Object[]>[] freePool;

    /**
     * @param numClasses
     * @param fragmentStartSize Must be a power of 2.
     * @param customizer
     */
    public ArrayPool(int numClasses, int fragmentStartSize, Customizer customizer) {
        this.numClasses = numClasses;
        this.fragmentStartSizeLogBase2 = Helper.floorLogBase2(fragmentStartSize);

        this.freePool = (SimplePool<Object[]>[]) new SimplePool[this.numClasses];
        for (int i = 0; i < this.numClasses; i++) {
            int maxPooledItemsForClass =
                    customizer.calcMaxElementsForPooledClass(numClasses, fragmentStartSizeLogBase2,
                            i);

            SimplePool<Object[]> poolForClass = new SimplePool<Object[]>(maxPooledItemsForClass);
            this.freePool[i] = poolForClass;
        }
    }

    /**
     * Same as invoking {@link #ArrayPool(int, int, com.tibco.cep.query.stream.util.ArrayPool.Customizer)}
     * with {@link #DEFAULT_NUM_CLASSES}, {@link #DEFAULT_FRAGMENT_START_SIZE} and {@link
     * com.tibco.cep.query.stream.util.ArrayPool.DefaultCustomizer}.
     */
    public ArrayPool() {
        this(DEFAULT_NUM_CLASSES, DEFAULT_FRAGMENT_START_SIZE, new DefaultCustomizer());
    }

    public int getFragmentStartSizeLogBase2() {
        return fragmentStartSizeLogBase2;
    }

    public int getNumClasses() {
        return numClasses;
    }

    /**
     * @param length should be (1 << ({@link #fragmentStartSizeLogBase2} + 0..m) )
     * @return
     */
    public Object[] createArray(int length) {
        Object[] array = null;

        int sizeClass = Helper.floorLogBase2(length) - fragmentStartSizeLogBase2;
        if (sizeClass < numClasses) {
            SimplePool<Object[]> poolForClass = freePool[sizeClass];

            array = poolForClass.fetch();
        }

        if (array == null) {
            // Unusual request or nothing in the pool for this class.
            array = new Object[length];
        }

        return array;
    }

    /**
     * @param array
     * @param clearArrayElements If the individual array elements are to be cleared before returning
     *                           to the pool.
     */
    public void returnArray(Object[] array, boolean clearArrayElements) {
        if (clearArrayElements) {
            // Clean the array before placing it in the pool.
            for (int i = 0; i < array.length; i++) {
                array[i] = null;
            }
        }

        int sizeClass = Helper.floorLogBase2(array.length) - fragmentStartSizeLogBase2;

        // Unusual request. No pooling for this class.        
        if (sizeClass >= numClasses) {
            return;
        }

        SimplePool<Object[]> poolForClass = freePool[sizeClass];
        poolForClass.returnOrAdd(array);
    }

    public void discard() {
        for (int i = 0; i < freePool.length; i++) {
            freePool[i].discard();
            freePool[i] = null;
        }
    }

    //----------

    public interface Customizer {
        public int calcMaxElementsForPooledClass(int numClasses, int fragmentStartSizePow2,
                                                 int currentClass);
    }

    public static class DefaultCustomizer implements Customizer {
        /**
         * {@value}.
         */
        public static final int DEFAULT_MAX_ITEMS_PER_CLASS_FACTOR = 4;

        protected final int maxItemsPerClassFactor;

        /**
         * @see #DEFAULT_MAX_ITEMS_PER_CLASS_FACTOR
         */
        public DefaultCustomizer() {
            this(DEFAULT_MAX_ITEMS_PER_CLASS_FACTOR);
        }

        public DefaultCustomizer(int maxItemsPerClassFactor) {
            this.maxItemsPerClassFactor = maxItemsPerClassFactor;
        }

        /**
         * <code>{@link #maxItemsPerClassFactor} * (numClasses - currentClass)</code>.
         *
         * @param numClasses
         * @param fragmentStartSizeLogBase2
         * @param currentClass
         * @return
         */
        public int calcMaxElementsForPooledClass(int numClasses, int fragmentStartSizeLogBase2,
                                                 int currentClass) {
            // Gradually decreases as the size of the class increases.
            int x = (numClasses - currentClass) * maxItemsPerClassFactor;

            return (x <= 0) ? DEFAULT_MAX_ITEMS_PER_CLASS_FACTOR : x;
        }
    }
}

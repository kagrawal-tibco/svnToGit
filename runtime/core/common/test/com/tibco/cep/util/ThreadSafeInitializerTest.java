package com.tibco.cep.util;

import java.util.concurrent.Callable;

/*
* Author: Ashwin Jayaprakash / Date: Oct 26, 2009 / Time: 6:05:12 PM
*/
public class ThreadSafeInitializerTest {
    public static void main(String[] args) throws Exception {
        test1();

        System.out.println("------------");

        test2();

        System.out.println("------------");

        test3();
    }

    public static void test1() throws Exception {
        final ThreadSafeInitializer<Integer> init =
                new ThreadSafeInitializer<Integer>(3, new DefaultLogger());

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    Integer i = init.getCachedOrCallJob(new Callable<Integer>() {
                        public Integer call() {
                            return 1;
                        }
                    });

                    System.out.println("Got: " + i);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    Integer i = init.getCachedOrCallJob(new Callable<Integer>() {
                        public Integer call() {
                            return 2;
                        }
                    });

                    System.out.println("Got: " + i);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            public void run() {
                try {
                    Integer i = init.getCachedOrCallJob(new Callable<Integer>() {
                        public Integer call() {
                            return 3;
                        }
                    });

                    System.out.println("Got: " + i);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();

        t3.join(10000);
        t2.join(10000);
        t1.join(10000);

        Integer i = init.getCachedOrCallJob(new Callable<Integer>() {
            public Integer call() {
                return 1000;
            }
        });

        System.out.println("Sync call got: " + i);
    }

    public static void test2() throws Exception {
        final ThreadSafeInitializer<Integer> init =
                new ThreadSafeInitializer<Integer>(3, new DefaultLogger());

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    Integer i = init.getCachedOrCallJob(new Callable<Integer>() {
                        public Integer call() throws Exception {
                            throw new Exception("1");
                        }
                    });

                    System.out.println("Got: " + i);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    Integer i = init.getCachedOrCallJob(new Callable<Integer>() {
                        public Integer call() throws Exception {
                            throw new Exception("2");
                        }
                    });

                    System.out.println("Got: " + i);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            public void run() {
                try {
                    Integer i = init.getCachedOrCallJob(new Callable<Integer>() {
                        public Integer call() throws Exception {
                            throw new Exception("3");
                        }
                    });

                    System.out.println("Got: " + i);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();

        t3.join(10000);
        t2.join(10000);
        t1.join(10000);

        Integer i = init.getCachedOrCallJob(new Callable<Integer>() {
            public Integer call() {
                return 1000;
            }
        });

        System.out.println("Sync call got: " + i);
    }

    public static void test3() throws Exception {
        final ThreadSafeInitializer<Integer> init =
                new ThreadSafeInitializer<Integer>(3, new DefaultLogger());

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    Integer i = init.getCachedOrCallJob(new Callable<Integer>() {
                        public Integer call() throws InterruptedException {
                            System.out.println("Sleeping...");
                            Thread.sleep(5000);

                            return 1;
                        }
                    });

                    System.out.println("Got: " + i);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    Integer i = init.getCachedOrCallJob(new Callable<Integer>() {
                        public Integer call() throws InterruptedException {
                            System.out.println("Sleeping...");
                            Thread.sleep(5000);

                            return 2;
                        }
                    });

                    System.out.println("Got: " + i);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            public void run() {
                try {
                    Integer i = init.getCachedOrCallJob(new Callable<Integer>() {
                        public Integer call() throws InterruptedException {
                            System.out.println("Sleeping...");
                            Thread.sleep(5000);

                            return 3;
                        }
                    });

                    System.out.println("Got: " + i);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();

        t3.join(10000);
        t2.join(10000);
        t1.join(10000);

        Integer i = init.getCachedOrCallJob(new Callable<Integer>() {
            public Integer call() {
                return 1000;
            }
        });

        System.out.println("Sync call got: " + i);
    }
}

package com.tibco.cep.query.stream.impl.rete.join;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import com.tibco.cep.query.stream.tuple.LiteTuple;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
 * Author: Ashwin Jayaprakash Date: Jan 7, 2008 Time: 11:13:00 AM
 */

public abstract class Trigger {
    public abstract static class AbstractTrigger extends LiteTuple {
        protected Number[] groupMembers;

        protected AbstractTrigger(Long id) {
            super(id);
        }

        @Override
        public Long getId() {
            return (Long) id;
        }

        public Number[] getGroupMembers() {
            return groupMembers;
        }

        public void setGroupMembers(Number[] groupMembers) {
            this.groupMembers = groupMembers;
        }

        public boolean areSameGroupMembers(Tuple[] tuples) {
            if (tuples.length != getGroupMembers().length) {
                return false;
            }

            Number[] others = new Number[tuples.length];
            for (int i = 0; i < others.length; i++) {
                others[i] = tuples[i].getId();
            }

            return Arrays.equals(getGroupMembers(), others);
        }
    }

    public static class CustomClassLoader extends URLClassLoader {
        protected final String className;

        protected final String classNameAsPath;

        public CustomClassLoader(String className) {
            super(new URL[0]);

            this.className = className;

            String name = className.replace('.', '/');
            this.classNameAsPath = "/" + name + ".class";
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            if (name.equals(className) == false) {
                // Delegate to System ClassLoader.
                return getParent().loadClass(name);
            }

            Class clazz = null;

            try {
                InputStream inputStream = getClass().getResourceAsStream(classNameAsPath);
                if (inputStream == null) {
                    throw new NullPointerException("Could not find Class file: " + classNameAsPath);
                }

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                byte[] buffer = new byte[512];
                int c = 0;
                while ((c = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, c);
                }

                byte[] classAsBytes = outputStream.toByteArray();

                clazz = defineClass(name, classAsBytes, 0, classAsBytes.length);
            }
            catch (Exception e) {
                throw new ClassNotFoundException(e.getMessage(), e);
            }

            return clazz;
        }
    }
}

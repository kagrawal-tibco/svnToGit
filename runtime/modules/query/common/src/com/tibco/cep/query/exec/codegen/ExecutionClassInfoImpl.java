package com.tibco.cep.query.exec.codegen;

import com.tibco.cep.query.exec.ExecutionClassInfo;
import com.tibco.cep.runtime.service.loader.ClassManager;

/**
 * Holds generated class's bytecode information
 */
public class ExecutionClassInfoImpl implements ExecutionClassInfo {
    private byte[] bytecode;
    private Class clazz;
    private String clazzName;

    /**
     * constructor
     *
     * @param clazzName
     * @param clazz
     * @param bytecode
     */
    ExecutionClassInfoImpl(String clazzName, Class clazz, byte[] bytecode) {
        this.clazzName = clazzName;
        this.clazz = clazz;
        this.bytecode = bytecode;
    }

    /**
     * Returns byte array of an execution class
     *
     * @return byte[]
     */
    byte[] getBytecode() {
        return bytecode;
    }

    /**
     * Returns an execution class
     *
     * @return Class
     */
    public Class getClazz() {
        return clazz;
    }
    /**
     * Returns the execution class name
     *
     * @return String
     */
    public String getClazzName() {
        return clazzName;
    }

    /**
     * sets the bytecode
     * @param bytecode
     */
    void setBytecode(byte[] bytecode) {
        this.bytecode = bytecode;
    }

    /**
     * sets the class
     * @param clazz
     */
    void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    /**
     * sets the class name
     * @param clazzName
     */
    void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    /**
     * Returns the execution class information packaged in ClassManager.ClassLoadingResult wrapper
     *
     * @return ClassManager.ClassLoadingResult
     */
    ClassManager.ClassLoadingResult getClassLoadingResult() {
        return new ClassManager.ClassLoadingResult(clazzName, bytecode, ClassManager.ClassLoadingResult.NEW);
    }


}

package com.tibco.cep.studio.wizard.as.commons.utils;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import com.tibco.cep.studio.wizard.as.internal.utils.Messages;

/**
 * Some helper methods to introspect bean classes and interfaces.
 *
 */
public class BeanUtils
{
  public static BeanInfo getBeanInfo(Class<?> beanClass)
  {
    try {
      BeanInfo info = Introspector.getBeanInfo(beanClass);
      if (beanClass.isInterface()) {
        info = new InterfaceBeanInfo(info, getInheritedBeanInfo(beanClass.getInterfaces()));
      }
      return info;
    } catch (IntrospectionException e) {
      throw new IllegalArgumentException(Messages.getString("BeanUtils.cannot_get_beaninfo") + beanClass.getName()); //$NON-NLS-1$
    }
  }

  private static Iterable<BeanInfo> getInheritedBeanInfo(Class<?>[] interfaces)
      throws IntrospectionException
  {
    ArrayList<BeanInfo> infos = new ArrayList<BeanInfo>();
    for (Class<?> i : interfaces) {
      infos.add(getBeanInfo(i));
    }
    return infos;
  }

  private static class InterfaceBeanInfo
      extends SimpleBeanInfo
  {
    private final BeanInfo main;
    private final Iterable<BeanInfo> inherited;

    public InterfaceBeanInfo(BeanInfo main, Iterable<BeanInfo> inherited)
    {
      this.main = main;
      this.inherited = inherited;
    }

    @Override
    public BeanDescriptor getBeanDescriptor()
    {
      return main.getBeanDescriptor();
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors()
    {
      Set<PropertyDescriptor> properties = new LinkedHashSet<PropertyDescriptor>();
      properties.addAll(Arrays.asList(main.getPropertyDescriptors()));
      for (BeanInfo i : inherited) {
        properties.addAll(Arrays.asList(i.getPropertyDescriptors()));
      }
      return properties.toArray(new PropertyDescriptor[properties.size()]);
    }

    @Override
    public MethodDescriptor[] getMethodDescriptors()
    {
      Set<MethodDescriptor> methods = new LinkedHashSet<MethodDescriptor>();
      methods.addAll(Arrays.asList(main.getMethodDescriptors()));
      for (BeanInfo i : inherited) {
        methods.addAll(Arrays.asList(i.getMethodDescriptors()));
      }
      return methods.toArray(new MethodDescriptor[methods.size()]);
    }

    @Override
    public BeanInfo[] getAdditionalBeanInfo()
    {
      Set<BeanInfo> infos = new LinkedHashSet<BeanInfo>();
      infos.addAll(Arrays.asList(main.getAdditionalBeanInfo()));
      for (BeanInfo i : inherited) {
        infos.addAll(Arrays.asList(i.getAdditionalBeanInfo()));
      }
      return infos.toArray(new BeanInfo[infos.size()]);
    }
  }
}

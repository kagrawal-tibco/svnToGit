package com.tibco.be.util.config.cdd.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;

import com.tibco.be.util.config.cdd.CddRoot;
import com.tibco.be.util.config.cdd.DomainObjectConfig;
import com.tibco.be.util.config.cdd.DomainObjectModeConfig;
import com.tibco.be.util.config.cdd.DomainObjectsConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

public class DomainObjectsConfigSerializableWrapper implements
		DomainObjectsConfig, Serializable {

	private static final long serialVersionUID = 510001L;

	private DomainObjectsConfig delegate;

	public DomainObjectsConfigSerializableWrapper(DomainObjectsConfig config) {
		if (config instanceof DomainObjectsConfigSerializableWrapper) {
			this.delegate = ((DomainObjectsConfigSerializableWrapper) config).delegate;
		} else {
			this.delegate = config;
		}
	}

	@Override
	public EClass eClass() {
		return this.delegate.eClass();
	}

	@Override
	public Resource eResource() {
		return this.delegate.eResource();
	}

	@Override
	public EObject eContainer() {
		return this.delegate.eContainer();
	}

	@Override
	public EStructuralFeature eContainingFeature() {
		return this.delegate.eContainingFeature();
	}

	@Override
	public EReference eContainmentFeature() {
		return this.delegate.eContainmentFeature();
	}

	@Override
	public EList<EObject> eContents() {

		return this.delegate.eContents();
	}

	@Override
	public TreeIterator<EObject> eAllContents() {
		return this.delegate.eAllContents();
	}

	@Override
	public boolean eIsProxy() {
		return this.delegate.eIsProxy();
	}

	@Override
	public EList<EObject> eCrossReferences() {
		return this.delegate.eCrossReferences();
	}

	@Override
	public Object eGet(EStructuralFeature feature) {
		return this.delegate.eGet(feature);
	}

	@Override
	public Object eGet(EStructuralFeature feature, boolean resolve) {
		return this.delegate.eGet(feature, resolve);
	}

	@Override
	public void eSet(EStructuralFeature feature, Object newValue) {
		this.delegate.eSet(feature, newValue);
	}

	@Override
	public boolean eIsSet(EStructuralFeature feature) {
		return this.delegate.eIsSet(feature);
	}

	@Override
	public void eUnset(EStructuralFeature feature) {
		this.delegate.eUnset(feature);
	}

	@Override
	public Object eInvoke(EOperation operation, EList<?> arguments)
			throws InvocationTargetException {
		return this.delegate.eInvoke(operation, arguments);
	}

	@Override
	public EList<Adapter> eAdapters() {
		return this.delegate.eAdapters();
	}

	@Override
	public boolean eDeliver() {
		return this.delegate.eDeliver();
	}

	@Override
	public void eNotify(Notification arg0) {
		this.delegate.eNotify(arg0);
	}

	@Override
	public void eSetDeliver(boolean arg0) {
		this.delegate.eSetDeliver(arg0);
	}

	@Override
	public OverrideConfig getCacheLimited() {
		return this.delegate.getCacheLimited();
	}

	@Override
	public void setCacheLimited(OverrideConfig value) {
		this.delegate.setCacheLimited(value);
	}

	@Override
	public OverrideConfig getCheckForVersion() {
		return this.delegate.getCheckForVersion();
	}

	@Override
	public void setCheckForVersion(OverrideConfig value) {
		this.delegate.setCheckForVersion(value);
	}

	@Override
	public OverrideConfig getConstant() {
		return this.delegate.getConstant();
	}

	@Override
	public void setConstant(OverrideConfig value) {
		this.delegate.setConstant(value);
	}

	@Override
	public OverrideConfig getEnableTracking() {
		return this.delegate.getEnableTracking();
	}

	@Override
	public void setEnableTracking(OverrideConfig value) {
		this.delegate.setEnableTracking(value);
	}

	@Override
	public OverrideConfig getEvictOnUpdate() {
		return this.delegate.getEvictOnUpdate();
	}

	@Override
	public void setEvictOnUpdate(OverrideConfig value) {
		this.delegate.setEvictOnUpdate(value);
	}

	@Override
	public OverrideConfig getPreLoadEnabled() {
		return this.delegate.getPreLoadEnabled();
	}

	@Override
	public void setPreLoadEnabled(OverrideConfig value) {
		this.delegate.setPreLoadEnabled(value);
	}

	@Override
	public OverrideConfig getPreLoadFetchSize() {
		return this.delegate.getPreLoadFetchSize();
	}

	@Override
	public void setPreLoadFetchSize(OverrideConfig value) {
		this.delegate.setPreLoadFetchSize(value);
	}

	@Override
	public OverrideConfig getPreLoadHandles() {
		return this.delegate.getPreLoadHandles();
	}

	@Override
	public void setPreLoadHandles(OverrideConfig value) {
		this.delegate.setPreLoadHandles(value);
	}

	@Override
	public OverrideConfig getSubscribe() {
		return this.delegate.getSubscribe();
	}

	@Override
	public void setSubscribe(OverrideConfig value) {
		this.delegate.setSubscribe(value);
	}

	@Override
	public Map<Object, Object> toProperties() {
		return this.delegate.toProperties();
	}

	@Override
	public String getId() {
		return this.delegate.getId();
	}

	@Override
	public void setId(String value) {
		this.delegate.setId(value);
	}

	@Override
	public DomainObjectModeConfig getDefaultMode() {
		return this.delegate.getDefaultMode();
	}

	@Override
	public void setDefaultMode(DomainObjectModeConfig value) {
		this.delegate.setDefaultMode(value);
	}

	@Override
	public void unsetDefaultMode() {
		this.delegate.unsetDefaultMode();
	}

	@Override
	public boolean isSetDefaultMode() {
		return this.delegate.isSetDefaultMode();
	}

	@Override
	public EList<DomainObjectConfig> getDomainObject() {
		return this.delegate.getDomainObject();
	}

	@Override
	public Boolean getCacheLimited(String uri) {
		return this.delegate.getCacheLimited(uri);
	}

	@Override
	public Boolean getCheckForVersion(String uri) {
		return this.delegate.getCheckForVersion(uri);
	}

	@Override
	public Boolean getConstant(String uri) {
		return this.delegate.getConstant(uri);
	}

	@Override
	public Boolean getEnableTracking(String uri) {
		return this.delegate.getEnableTracking(uri);
	}

	@Override
	public Boolean getEvictOnUpdate(String uri) {
		return this.delegate.getEvictOnUpdate(uri);
	}

	@Override
	public String getMode(String uri) {
		return this.delegate.getMode(uri);
	}

	@Override
	public Boolean getPreLoadEnabled(String uri) {
		return this.delegate.getPreLoadEnabled(uri);
	}

	@Override
	public Long getPreLoadFetchSize(String uri) {
		return this.delegate.getPreLoadFetchSize(uri);
	}

	@Override
	public Boolean getPreLoadHandles(String uri) {
		return this.delegate.getPreLoadHandles(uri);
	}

	@Override
	public String getPreprocessor(String uri) {
		return this.delegate.getPreprocessor(uri);
	}

	@Override
	public Boolean getSubscribe(String uri) {
		return this.delegate.getSubscribe(uri);
	}

	public void writeExternal(DataOutput out) throws IOException {

        ClassLoader classLoader =
                (ClassLoader) RuleServiceProviderManager.getInstance().getDefaultProvider().getTypeManager();
        Thread.currentThread().setContextClassLoader(classLoader);

        CddPackageImpl.eINSTANCE.eClass();
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put(
				"domainObjectConfig", new GenericXMLResourceFactoryImpl());
		
		final ResourceSet set = new ResourceSetImpl();
		final Resource resource = set.createResource(URI
				.createURI("s.domainObjectConfig"));
		final Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");

		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		final CddRoot root = CddFactoryImpl.eINSTANCE.createCddRoot();
		root.setDomainObjects(EcoreUtil.copy(this.delegate));
		
		resource.getContents().add(root);
		resource.save(baos, options);

		baos.close();

		out.write(baos.toByteArray());
	}

	public void readExternal(DataInput in) throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte b;
		for (;;) {
			try {
				b = in.readByte();
			} catch (EOFException done) {
				break;
			}
			baos.write(b);
		}
		baos.close();

        ClassLoader classLoader =
                (ClassLoader) RuleServiceProviderManager.getInstance().getDefaultProvider().getTypeManager();
        Thread.currentThread().setContextClassLoader(classLoader);

        CddPackageImpl.eINSTANCE.eClass();
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put(
				"domainObjectConfig", new GenericXMLResourceFactoryImpl());

		final ResourceSet set = new ResourceSetImpl();
		final Resource resource = set.createResource(URI
				.createURI("s.domainObjectConfig"));
		final Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");

		resource.load(new ByteArrayInputStream(baos.toByteArray()), options);

		this.delegate = ((CddRoot) resource.getContents().get(0))
				.getDomainObjects();
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		this.writeExternal(out);
	}

	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		this.readExternal(in);
	}

	@SuppressWarnings("unused")
	private void readObjectNoData() throws ObjectStreamException {
	}
	
	public boolean equals(Object o) {
		if(o == delegate) return true;
		if(o instanceof DomainObjectsConfigSerializableWrapper) o = ((DomainObjectsConfigSerializableWrapper)o).delegate;
		if(o instanceof DomainObjectsConfig ) {
			return o.equals(delegate);
		}
		return false;
	}

	@Override
	public OverrideConfig getConceptTTL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConceptTTL(OverrideConfig value) {
		// TODO Auto-generated method stub
		
	}
}

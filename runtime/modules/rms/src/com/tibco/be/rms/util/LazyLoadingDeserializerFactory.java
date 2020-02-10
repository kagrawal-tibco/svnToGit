package com.tibco.be.rms.util;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Dec 8, 2009
 * Time: 1:46:21 PM
 * <!--
 * Add Description of the class here
 * -->
 */
public class LazyLoadingDeserializerFactory {

    public static final LazyLoadingDeserializerFactory INSTANCE = new LazyLoadingDeserializerFactory();

    private LazyLoadingDeserializerFactory() {}

    public ArtifactAttributesReader getDeserializer(final String artifactExtension) {
        ArtifactTypes artifactType = ArtifactTypes.get(artifactExtension);
        switch (artifactType) {
            case DECSISIONTABLE:
                return new LazyLoadingImplDeserializer();
            case DOMAIN:
                return new LazyLoadingDomainDeserializer();
            default:
               break;
        }
        return null;
    }

    private enum ArtifactTypes {
        DECSISIONTABLE("rulefunctionimpl"),
        DOMAIN("domain"),
        RULE("rule"),
        RULEFUNCTION("rulefunction");

        String extension;

        ArtifactTypes(String extension) {
            this.extension = extension;
        }

        private static final ArtifactTypes[] VALUES_ARRAY =
		    new ArtifactTypes[] {
		        DECSISIONTABLE,
		        DOMAIN,
                RULE,
                RULEFUNCTION
        };

        public static ArtifactTypes get(String extension) {
            for (int i = 0; i < VALUES_ARRAY.length; ++i) {
                ArtifactTypes result = VALUES_ARRAY[i];
                if (result.extension.equals(extension)) {
                    return result;
                }
            }
            return null;
	    }
    }
}

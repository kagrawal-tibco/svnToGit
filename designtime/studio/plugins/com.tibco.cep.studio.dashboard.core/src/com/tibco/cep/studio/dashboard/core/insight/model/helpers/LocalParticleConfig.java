package com.tibco.cep.studio.dashboard.core.insight.model.helpers;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;

/**
 * ASSUMPTION: (1) The path of particle does not have intermediate type name. (2) The path of
 * particle does not have subscripts. (3) The path of particle can be prefixed by '@' to denote
 * a reference. (4) The type can be either MDConfigType or MDElement.
 */
public class LocalParticleConfig {

    public LocalParticle particle;

    String originalPath; // path has @ sign, intermediate path name, and intermediate type
                            // name.

    String path;

    public String[] pathToken;

    boolean isMDConfigType = true;

    String type; // the type of the leaf property or particle

    LocalParticleConfig(LocalParticle particle, String path, String type) throws Exception {
        this.particle = particle;
        this.originalPath = path;
        this.path = (path.startsWith("@") ? path.substring(1, path.length()) : path);
        this.pathToken = new String[] { this.path };
        this.parseType(type);
    }

    private void parseType(String type) throws Exception {

        // MD Type are enclosed by curly bracket.
        int start = type.indexOf(ViewsConfigReader.TYPE_START);
        if (start >= 0) {
            int end = type.indexOf(ViewsConfigReader.TYPE_END, start);
            if (end <= start) {
                throw new Exception("invalid frormat" + path);
            }
            this.isMDConfigType = false;
            this.type = type.substring(start + 1, end);
        } else {
            this.isMDConfigType = true;
            this.type = type;
        }
    }

	public String getPath() {
		return path;
	}

	public String getType() {
		return type;
	}
}


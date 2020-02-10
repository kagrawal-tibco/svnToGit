package com.tibco.cep.mapper.xml;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;

/* (c) Copyright 1999-2003, TIBCO Software Inc.  All rights reserved.
 *
 * LEGAL NOTICE:  This source code is provided to specific authorized end
 * users pursuant to a separate license agreement.  You MAY NOT use this
 * source code if you do not have a separate license from TIBCO Software
 * Inc.  Except as expressly set forth in such license agreement, this
 * source code, or any portion thereof, may not be used, modified,
 * reproduced, transmitted, or distributed in any form or by any means,
 * electronic or mechanical, without written permission from  TIBCO
 * Software Inc.
 *
 * User: jbaysdon
 * Date: Dec 10, 2004
 * Version ${VERSION}
 * See LOG at end of file for modification notes.
 */

public class ChainedURIResolver implements URIResolver {

   public ChainedURIResolver(URIResolver firstResolver, URIResolver secondResolver) {
      m_firstResolver = firstResolver;
      m_secondResolver = secondResolver;
   }
   public Source resolve(String href, String base) throws TransformerException {
      Source source = null;
      if(m_firstResolver != null) {
         source = m_firstResolver.resolve(href, base);
      }
      if(source == null && m_secondResolver != null) {
         source = m_secondResolver.resolve(href, base);
      }
      return source;
   }
   private URIResolver m_firstResolver = null;
   private URIResolver m_secondResolver = null;
}

/*
 * ${Log}
 */
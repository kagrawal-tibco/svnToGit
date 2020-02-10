package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.xtype.SMDT;

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
 * Date: Sep 9, 2004
 * Version ${VERSION}
 * See LOG at end of file for modification notes.
 */

public class CurrentDateXFunction extends DefaultLastArgRequiredXFunction {
   public static final ExpandedName NAME = TibXPath20Functions.makeName("current-date");

   public CurrentDateXFunction()
   {
       super(NAME,SMDT.STRING);
   }
}

/*
 * ${Log}
 */
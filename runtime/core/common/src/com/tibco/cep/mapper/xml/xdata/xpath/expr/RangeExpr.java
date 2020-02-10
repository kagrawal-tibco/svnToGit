package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import java.math.BigDecimal;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

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
 * Date: Nov 10, 2004
 * Version ${VERSION}
 * See LOG at end of file for modification notes.
 */

public class RangeExpr extends Expr {
   private Expr m_firstNumber = null;
   private Expr m_secondNumber = null;

   public RangeExpr(Expr firstNumber, Expr secondNumber, TextRange range, String whitespace) {
      super(range, whitespace);
      m_firstNumber = firstNumber;
      m_secondNumber = secondNumber;
   }

   public SmSequenceType evalType(ExprContext context, EvalTypeInfo info) {

      SmSequenceType r1 = m_firstNumber.evalType(context,info);
      SmSequenceType r2 = m_secondNumber.evalType(context,info);

      BigDecimal bd = new BigDecimal(m_firstNumber.getExprValue());
      double value1 = bd.doubleValue();

      bd = new BigDecimal(m_secondNumber.getExprValue());
      double value2 = bd.doubleValue();

      int occurs = (int)(value2 - value1);
      SmCardinality xOccurs = SmCardinality.create(occurs, occurs, true);

      SmSequenceType ret = SmSequenceTypeFactory.createRepeats(r1, xOccurs);

      // nice & easy!
//      XType ret = XTypeFactory.createSimplifiedSequence(r1,r2);
      return info.recordReturnType(this,ret);
   }

   public Expr[] getChildren() {
      return new Expr[] {m_firstNumber, m_secondNumber};
   }

   public int getExprTypeCode() {
      return ExprTypeCode.EXPR_RANGE;
   }

   public String getExprValue() {
      return null;
   }

   public void format(StringBuffer toBuffer, int style) {
      boolean isExact = style==STYLE_TO_EXACT_STRING;
      m_firstNumber.format(toBuffer,style);
      toBuffer.append("to ");
      if (isExact)
      {
          toBuffer.append(getWhitespace());
      }
      m_secondNumber.format(toBuffer,style);
   }
}

/*
 * ${Log}
 */
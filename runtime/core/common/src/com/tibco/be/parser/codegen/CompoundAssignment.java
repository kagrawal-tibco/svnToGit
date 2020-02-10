package com.tibco.be.parser.codegen;

import com.tibco.cep.runtime.model.element.PropertyAtomDouble;
import com.tibco.cep.runtime.model.element.PropertyAtomInt;
import com.tibco.cep.runtime.model.element.PropertyAtomLong;
import com.tibco.cep.runtime.model.element.PropertyAtomString;

/**
 * Created by IntelliJ IDEA.
* User: aamaya
* Date: May 13, 2009
* Time: 10:30:08 PM
* To change this template use File | Settings | File Templates.
*/
public enum CompoundAssignment {
    INCR ("preIncrement"){
        public int op(int val, int arg) {
            return val + 1;             }
        public long op(long val, long arg) {
            return val + 1;                }
        public double op(double val, double arg) {
            return val + 1;                      }
        public void op(PropertyAtomInt pa, Object arg) {
            pa.preIncrement();                         }
        public void op(PropertyAtomLong pa, Object arg) {
            pa.preIncrement();                          }
        public void op(PropertyAtomDouble pa, Object arg) {
            pa.preIncrement();                            }
        public String op(String val, Object arg) {
            throw new UnsupportedOperationException("++ on String");    }
        public void op(PropertyAtomString pa, Object arg) {
            throw new UnsupportedOperationException("++ on PropertyAtomString");    }
    },
    
    DECR("preDecrement") {
        public int op(int val, int arg) {
            return val - 1;             }
        public long op(long val, long arg) {
            return val - 1;                }
        public double op(double val, double arg) {
            return val - 1;                      }
        public void op(PropertyAtomInt pa, Object arg) {
            pa.preDecrement();                         }
        public void op(PropertyAtomLong pa, Object arg) {
            pa.preDecrement();                          }
        public void op(PropertyAtomDouble pa, Object arg) {
            pa.preDecrement();                            }
        public String op(String val, Object arg) {
            throw new UnsupportedOperationException("-- on String");    }
        public void op(PropertyAtomString pa, Object arg) {
            throw new UnsupportedOperationException("-- on PropertyAtomString");    }
    },
         
    POST_INCR("postIncrement") {
        public int op(int val, int arg) {
            return val + 1;             }
        public long op(long val, long arg) {
            return val + 1;                }
        public double op(double val, double arg) {
            return val + 1;                      }
        public void op(PropertyAtomInt pa, Object arg) {
            pa.postIncrement();                         }
        public void op(PropertyAtomLong pa, Object arg) {
            pa.postIncrement();                          }
        public void op(PropertyAtomDouble pa, Object arg) {
            pa.postIncrement();                            }
        public String op(String val, Object arg) {
            throw new UnsupportedOperationException("++ on String");    }
        public void op(PropertyAtomString pa, Object arg) {
            throw new UnsupportedOperationException("++ on PropertyAtomString");    }
    },
    
    POST_DECR("postDecrement") {
        public int op(int val, int arg) {
            return val - 1;             }
        public long op(long val, long arg) {
            return val - 1;                }
        public double op(double val, double arg) {
            return val - 1;                      }
        public void op(PropertyAtomInt pa, Object arg) {
            pa.postDecrement();                         }
        public void op(PropertyAtomLong pa, Object arg) {
            pa.postDecrement();                          }
        public void op(PropertyAtomDouble pa, Object arg) {
            pa.postDecrement();                            }
        public String op(String val, Object arg) {
            throw new UnsupportedOperationException("-- on String");    }
        public void op(PropertyAtomString pa, Object arg) {
            throw new UnsupportedOperationException("-- on PropertyAtomString");    }
    },
     
    PLUSEQ("addAssign") {
        public int op(int val, int arg) {
            return val + arg;           }
        public long op(long val, long arg) {
            return val + arg;              }
        public double op(double val, double arg) {
            return val + arg;                    }
        public void op(PropertyAtomInt pa, Object arg) {
            pa.addAssign((Integer)arg);                }
        public void op(PropertyAtomLong pa, Object arg) {
            pa.addAssign((Long)arg);                    }
        public void op(PropertyAtomDouble pa, Object arg) {
            pa.addAssign((Double)arg);                    }
        public String op(String val, Object arg) {
            return val + String.valueOf(arg);    }
        public void op(PropertyAtomString pa, Object arg) {
            pa.addAssign(String.valueOf(arg));            }
    },
    
    MINUSEQ("subAssign") {
        public int op(int val, int arg) {
            return val - arg;           }
        public long op(long val, long arg) {
            return val - arg;              }
        public double op(double val, double arg) {
            return val - arg;                    }
        public void op(PropertyAtomInt pa, Object arg) {
            pa.subAssign((Integer)arg);                }
        public void op(PropertyAtomLong pa, Object arg) {
            pa.subAssign((Long)arg);                    }
        public void op(PropertyAtomDouble pa, Object arg) {
            pa.subAssign((Double)arg);                    }
        public String op(String val, Object arg) {
            throw new UnsupportedOperationException("-= on String");    }
        public void op(PropertyAtomString pa, Object arg) {
            throw new UnsupportedOperationException("-= on PropertyAtomString");    }
    },

    STAREQ("multAssign") {
        public int op(int val, int arg) {
            return val * arg;           }
        public long op(long val, long arg) {
            return val * arg;              }
        public double op(double val, double arg) {
            return val * arg;                    }
        public void op(PropertyAtomInt pa, Object arg) {
            pa.multAssign((Integer)arg);                }
        public void op(PropertyAtomLong pa, Object arg) {
            pa.multAssign((Long)arg);                    }
        public void op(PropertyAtomDouble pa, Object arg) {
            pa.multAssign((Double)arg);                    }
        public String op(String val, Object arg) {
            throw new UnsupportedOperationException("*= on String");    }
        public void op(PropertyAtomString pa, Object arg) {
            throw new UnsupportedOperationException("*= on PropertyAtomString");    }
    },
    
    SLASHEQ("divAssign") {
        public int op(int val, int arg) {
            return val / arg;           }
        public long op(long val, long arg) {
            return val / arg;              }
        public double op(double val, double arg) {
            return val / arg;                    }
        public void op(PropertyAtomInt pa, Object arg) {
            pa.divAssign((Integer)arg);                }
        public void op(PropertyAtomLong pa, Object arg) {
            pa.divAssign((Long)arg);                    }
        public void op(PropertyAtomDouble pa, Object arg) {
            pa.divAssign((Double)arg);                    }
        public String op(String val, Object arg) {
            throw new UnsupportedOperationException("/= on String");    }
        public void op(PropertyAtomString pa, Object arg) {
            throw new UnsupportedOperationException("/= on PropertyAtomString");    }
    },
    
    PCNTEQ("modAssign") {
        public int op(int val, int arg) {
            return val % arg;           }
        public long op(long val, long arg) {
            return val % arg;              }
        public double op(double val, double arg) {
            return val % arg;                    }
        public void op(PropertyAtomInt pa, Object arg) {
            pa.modAssign((Integer)arg);                }
        public void op(PropertyAtomLong pa, Object arg) {
            pa.modAssign((Long)arg);                    }
        public void op(PropertyAtomDouble pa, Object arg) {
            pa.modAssign((Double)arg);                    }
        public String op(String val, Object arg) {
            throw new UnsupportedOperationException("%= on String");    }
        public void op(PropertyAtomString pa, Object arg) {
            throw new UnsupportedOperationException("%= on PropertyAtomString");    }
    };

    private String methodName;
    
    private CompoundAssignment(String methodName) {
        this.methodName = methodName;
    }
    
    public String getMethodName() {
        return methodName;
    }
    // Moved to cep.core plugin class CompoundAssignmentHelper
//    public static CompoundAssignment fromToken(Token tok) {
//        return fromTokenKind(tok.kind);
//    }
//
//    public static CompoundAssignment fromTokenKind(int kind) {
//        switch(kind) {
//            case RuleGrammarConstants.INCR: return INCR;
//            case RuleGrammarConstants.DECR: return DECR;
//            case RuleGrammarConstants.PLUSEQ: return PLUSEQ;
//            case RuleGrammarConstants.MINUSEQ: return MINUSEQ;
//            case RuleGrammarConstants.STAREQ: return STAREQ;
//            case RuleGrammarConstants.SLASHEQ: return SLASHEQ;
//            case RuleGrammarConstants.PCNTEQ: return PCNTEQ;
//            case RuleGrammarConstants.POST_INCR: return POST_INCR;
//            case RuleGrammarConstants.POST_DECR: return POST_DECR;
//        }
//        return null;
//    }
    
    public abstract int op(int val, int arg);
    public abstract long op(long val, long arg);
    public abstract double op(double val, double arg);

    public abstract void op(PropertyAtomInt pa, Object arg);
    public abstract void op(PropertyAtomLong pa, Object arg);
    public abstract void op(PropertyAtomDouble pa, Object arg);

    public abstract String op(String val, Object arg);
    public abstract void op(PropertyAtomString pa, Object arg);
    
    /*
        public abstract boolean op(boolean val, boolean arg);
        public abstract void op(PropertyAtomBoolean pa, boolean arg);
     */
    
}
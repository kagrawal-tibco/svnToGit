/******************************************************************************
    Description: This file defines all the base types that correspond to the 
    BusinessEvents® object data structure. 

    Naming convention: In this file "*" are always UPPERCASE. In generated files
    "*" are the common name as defined by user. Exceptions are annotated in
    following whenever they appear.
        - Object type: T_*
        - Nested table: T_*_TABLE
        - Meta information type: T_*_$
        - Non user attribute: *$
        - Static meta information attribute: *$
        - User table: D_*
        - Index: i_[user table]
        - View: v_[user table]    
 *****************************************************************************/
 
/** The ontology types need to be dropped before this line */

drop table ALL_ENTITIES;

drop type T_SIMPLE_EVENT;

drop type T_STATEMACHINE_TIMEOUT;

drop type T_TIME_EVENT;

drop type T_EVENT;

drop type T_CONCEPT;

drop type T_ENTITY;

drop type T_ENTITYREF_COL_HIST;

drop type T_ENTITYREF_HIST;

drop type T_ENTITYREF_HIST_TABLE;

drop type T_ENTITYREF_HIST_TUPLE;



/* Type = Type to support BE PropertyArrayString with HIST > 1*/


drop type T_ENTITY_REF_TABLE;

drop type T_ENTITY_REF;

drop type T_REVERSE_REF;

drop type T_REVERSE_REF_TABLE;


/** Types to Support BE AtomString and Array String Implementations */

drop type T_STRING_COL_HIST;

drop type T_STRING_HIST;

drop type T_STRING_HIST_TABLE;

drop type T_STRING_HIST_TUPLE;

drop type T_STRING_COL;


/* Create Global Types */

/* Type = COL of BE PropertyAtomString */

create or replace type T_STRING_COL as TABLE of varchar2(4000);
/

/* Type = HIST Tuple of  BE PropertyAtomString */

create or replace type T_STRING_HIST_TUPLE as OBJECT (timeIdx TIMESTAMP , val varchar2(4000));
/
/* Type = BE HIST Table of PropertyAtomString with HIST > 1*/

create or replace type T_STRING_HIST_TABLE as TABLE of T_STRING_HIST_TUPLE;
/
/* Type = Type to support BE PropertyAtomString with HIST > 1*/

create or replace type T_STRING_HIST as OBJECT (howMany INTEGER, vals T_STRING_HIST_TABLE);
/
/* Type = Type to support BE PropertyArrayString with HIST > 1*/
create or replace type T_STRING_COL_HIST as TABLE of T_STRING_HIST;
/

/** End of Types to Support BE AtomString and Array String Implementations */

/** Types to Support BE AtomString and Array String Implementations */

drop type T_INT_COL_HIST;

drop type T_INT_HIST;

drop type T_INT_HIST_TABLE;

drop type T_INT_HIST_TUPLE;

drop type T_INT_COL;



/* Type = COL of BE PropertyAtomINT */

create or replace type T_INT_COL as TABLE of INT;
/

/* Type = HIST Tuple of  BE PropertyAtomINT */

create or replace type T_INT_HIST_TUPLE as OBJECT (timeIdx TIMESTAMP , val INT);
/
/* Type = BE HIST Table of PropertyAtomINT with HIST > 1*/

create or replace type T_INT_HIST_TABLE as TABLE of T_INT_HIST_TUPLE;
/
/* Type = Type to support BE PropertyAtomINT with HIST > 1*/

create or replace type T_INT_HIST as OBJECT (howMany INTEGER, vals T_INT_HIST_TABLE);
/

/* Type = Type to support BE PropertyArrayInt with HIST > 1*/
create or replace type T_INT_COL_HIST as TABLE of T_INT_HIST;
/

/** End of Types to Support BE AtomINT and Array INT Implementations */

/** Types to Support BE AtomString and Array String Implementations */

drop type T_BOOLEAN_COL_HIST;

drop type T_BOOLEAN_HIST;

drop type T_BOOLEAN_HIST_TABLE;

drop type T_BOOLEAN_HIST_TUPLE;

drop type T_BOOLEAN_COL;



/* Type = COL of BE PropertyAtomBOOLEAN */

create or replace type T_BOOLEAN_COL as TABLE of INT;
/

/* Type = HIST Tuple of  BE PropertyAtomBOOLEAN */

create or replace type T_BOOLEAN_HIST_TUPLE as OBJECT (timeIdx TIMESTAMP , val INT);
/
/* Type = BE HIST Table of PropertyAtomBOOLEAN with HIST > 1*/

create or replace type T_BOOLEAN_HIST_TABLE as TABLE of T_BOOLEAN_HIST_TUPLE;
/
/* Type = Type to support BE PropertyAtomBOOLEAN with HIST > 1*/

create or replace type T_BOOLEAN_HIST as OBJECT (howMany INTEGER, vals T_BOOLEAN_HIST_TABLE);
/
/* Type = Type to support BE PropertyArrayInt with HIST > 1*/
create or replace type T_BOOLEAN_COL_HIST as TABLE of T_BOOLEAN_HIST;
/


/** End of Types to Support BE AtomBOOLEAN and Array BOOLEAN Implementations */

/** Types to Support BE AtomString and Array String Implementations */

drop type T_NUMBER_COL_HIST;

drop type T_NUMBER_HIST;

drop type T_NUMBER_HIST_TABLE;

drop type T_NUMBER_HIST_TUPLE;

drop type T_NUMBER_COL;



/* Type = COL of BE PropertyAtomNUMBER */

create or replace type T_NUMBER_COL as TABLE of NUMBER;
/

/* Type = HIST Tuple of  BE PropertyAtomNUMBER */

create or replace type T_NUMBER_HIST_TUPLE as OBJECT (timeIdx TIMESTAMP , val NUMBER);
/
/* Type = BE HIST Table of PropertyAtomNUMBER with HIST > 1*/

create or replace type T_NUMBER_HIST_TABLE as TABLE of T_NUMBER_HIST_TUPLE;
/
/* Type = Type to support BE PropertyAtomNUMBER with HIST > 1*/

create or replace type T_NUMBER_HIST as OBJECT (howMany INTEGER, vals T_NUMBER_HIST_TABLE);
/
create or replace type T_NUMBER_COL_HIST as TABLE of T_NUMBER_HIST;
/

/** End of Types to Support BE AtomNUMBER and Array NUMBER Implementations */

/** Types to Support BE AtomString and Array String Implementations */

drop type T_DATETIME_COL_HIST;

drop type T_DATETIME_HIST;

drop type T_DATETIME_HIST_TABLE;

drop type T_DATETIME_HIST_TUPLE;

drop type T_DATETIME_COL;

drop type T_DATETIME;


create or replace type T_DATETIME as OBJECT(tm TIMESTAMP, tz VARCHAR2(100));
/

/* Type = COL of BE PropertyAtomDATETIME */

create or replace type T_DATETIME_COL as TABLE of T_DATETIME;
/

/* Type = HIST Tuple of  BE PropertyAtomDATETIME */

create or replace type T_DATETIME_HIST_TUPLE as OBJECT (timeIdx TIMESTAMP , val T_DATETIME);
/
/* Type = BE HIST Table of PropertyAtomDATETIME with HIST > 1*/

create or replace type T_DATETIME_HIST_TABLE as TABLE of T_DATETIME_HIST_TUPLE;
/
/* Type = Type to support BE PropertyAtomDATETIME with HIST > 1*/

create or replace type T_DATETIME_HIST as OBJECT (howMany INTEGER, vals T_DATETIME_HIST_TABLE);
/
create or replace type T_DATETIME_COL_HIST as TABLE of T_DATETIME_HIST;
/

/** Base Entity Types */




create or replace type T_ENTITY as object (id$ NUMBER, extId$ varchar2(2000), state$ char(1)) NOT FINAL ;
/

create or replace type T_ENTITY_REF as object (id$ NUMBER);
/

create or replace type T_REVERSE_REF as object (propertyName$ varchar2(1000), id$ NUMBER);
/

create or replace type T_REVERSE_REF_TABLE as TABLE of T_REVERSE_REF;
/

create or replace type T_ENTITY_REF_TABLE as TABLE OF T_ENTITY_REF;
/

/* Type = HIST Tuple of  BE PropertyAtomString */

create or replace type T_ENTITYREF_HIST_TUPLE as OBJECT (timeIdx TIMESTAMP , val T_ENTITY_REF);
/
/* Type = BE HIST Table of PropertyAtomString with HIST > 1*/

create or replace type T_ENTITYREF_HIST_TABLE as TABLE of T_ENTITYREF_HIST_TUPLE;
/
/* Type = Type to support BE PropertyAtomString with HIST > 1*/

create or replace type T_ENTITYREF_HIST as OBJECT (howMany INTEGER, vals T_ENTITYREF_HIST_TABLE);
/
/* Type = Type to support BE PropertyArrayString with HIST > 1*/
create or replace type T_ENTITYREF_COL_HIST as TABLE of T_ENTITYREF_HIST;
/

create or replace type T_CONCEPT UNDER T_ENTITY (time_created$ TIMESTAMP, time_last_modified$ TIMESTAMP, parent$ T_ENTITY_REF, reverseRefs$ T_REVERSE_REF_TABLE) NOT FINAL;
/

create or replace type T_EVENT UNDER T_ENTITY (time_created$ TIMESTAMP) NOT FINAL;
/

create or replace type T_SIMPLE_EVENT UNDER T_EVENT(time_acknowledged$ TIMESTAMP, time_sent$ TIMESTAMP) NOT FINAL;
/

create or replace type T_TIME_EVENT UNDER T_EVENT(currentTime NUMBER, nextTime NUMBER, closure varchar2(4000), ttl NUMBER, fired INT) NOT FINAL;
/

create or replace type T_STATEMACHINE_TIMEOUT UNDER T_TIME_EVENT(smid T_ENTITY_REF, propertyName varchar2(4000)) NOT FINAL;
/

/* Start of Types to store metadata */

drop type T_EVENT_DESCRIPTION$;

drop type T_CONCEPT_DESCRIPTION$;

drop type T_ENTITY_DESCRIPTION$;

drop type T_CONCEPT_PROPERTY_TABLE$;

drop type T_EVENT_PROPERTY_TABLE$;

drop type T_CONCEPT_PROPERTY$;

drop type T_EVENT_PROPERTY$;

create or replace type T_CONCEPT_PROPERTY$ AS OBJECT(
	propertyName$	varchar2(2000),
	propertyIndex$	NUMBER,
	propertyType$	NUMBER,
	isArray$		NUMBER,
	historySize		NUMBER,
	referredPath	varchar(4000) ) NOT FINAL ;
/

create or replace type T_EVENT_PROPERTY$ AS OBJECT(
	propertyName$	varchar2(2000),
	propertyIndex$	NUMBER,
	propertyType$	NUMBER ) NOT FINAL;
/

create or replace type T_CONCEPT_PROPERTY_TABLE$ as TABLE of T_CONCEPT_PROPERTY$;
/

create or replace type T_EVENT_PROPERTY_TABLE$ as TABLE of T_EVENT_PROPERTY$;
/


create or replace type T_ENTITY_DESCRIPTION$ as OBJECT (masterCache$ varchar2(2000), entityCache$ varchar2(2000), entityClass$ varchar2(2000),  cacheId NUMBER, version NUMBER) NOT FINAL;
/

create or replace type T_CONCEPT_DESCRIPTION$ UNDER T_ENTITY_DESCRIPTION$ (
	isContained		NUMBER,
	properties		T_CONCEPT_PROPERTY_TABLE$) NOT FINAL;
/

create or replace type T_EVENT_DESCRIPTION$ UNDER T_ENTITY_DESCRIPTION$ (
	properties		T_EVENT_PROPERTY_TABLE$) NOT FINAL;
/


# This script is a Pseudo-Installer, and is used to substitute the values of Installer Configurable parameters/tags.
# *******  STRICTLY FOR INTERNAL USE ONLY ********
# 

# If used from the Build's Export area...
export ORION_DIR=c:/build/be/orion/build/Enterprise/exp/win/x86/dbg

# If used from the Installed area...
export TIBCO_DIR=c:/tibco
export TIBCO_BE_DIR=${TIBCO_DIR}/be/3.0

# Use ORION_DIR or TIBCO_BE_DIR
export BASE_DIR=${ORION_DIR}


# **********  No Configuration needed below this line **********

export BUI_CONFIG_TRA=${BASE_DIR}/DecisionManager/configuration/bui-config.tra
export BE_RMS_TRA=${BASE_DIR}/rms/bin/be-rms.tra
export BUI_INI=${BASE_DIR}/DecisionManager/DecisionManager.ini

sed 's!%BE_HOME%!${TIBCO_BE_DIR}!g' ${BUI_CONFIG_TRA} > ${BUI_CONFIG_TRA}.rep
mv ${BUI_CONFIG_TRA}.rep ${BUI_CONFIG_TRA}

sed -e 's!%PS%!;!g' \
	-e  's!%TIBCO_HOME_ESC%!${TIBCO_DIR}!g' \
	-e  's!%TIBCO_BE_HOME_ESC%!${TIBCO_BE_DIR}!g' \
	-e  's!%TIBCO_JVM_LIB_ESC%!${TIBCO_DIR}/jre/1.5.0/bin/client/jvm.dll!g'  \
	-e  's!%TIBCO_JVM_LIB_DIR_ESC%!${TIBCO_DIR}/jre/1.5.0/bin!g' \
	-e  's!%TIBCO_RV_HOME_ESC%!${TIBCO_DIR}/tibrv!g' \
	-e  's!%TIBCO_JAVA_HOME_ESC%!${TIBCO_DIR}/jre/1.5.0!g' \
	-e  's!%TIBCO_TPCL_HOME_ESC%!${TIBCO_DIR}/tpcl/5.5!g' \
	-e  's!%TIBCO_TRA_HOME_ESC%!${TIBCO_DIR}/tra/5.5!g' \
	-e  's!%TIBCO_HAWK_HOME_ESC%!${TIBCO_DIR}/hawk!g' \
	${BE_RMS_TRA} > ${BE_RMS_TRA}.rep
mv ${BE_RMS_TRA}.rep ${BE_RMS_TRA}

sed s!%TIBCO_JVM_LIB_DIR_ESC%!$TIBCO_DIR/jre/1.5.0/bin!g ${BUI_INI} > ${BUI_INI}.rep
mv ${BUI_INI}.rep ${BUI_INI}


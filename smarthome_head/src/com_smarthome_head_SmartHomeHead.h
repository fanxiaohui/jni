/* DO NOT EDIT THIS FILE - it is machine generated */
#include "jni.h"
/* Header for class com_smarthome_head_SmartHomeHead */

#ifndef _Included_com_smarthome_head_SmartHomeHead
#define _Included_com_smarthome_head_SmartHomeHead
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_smarthome_head_SmartHomeHead
 * Method:    addHead
 * Signature: (Lcom/smarthome/head/SmartHomeData;Ljava/lang/String;[B)[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_smarthome_head_SmartHomeHead_addHead
  (JNIEnv *, jclass, jobject, jbyteArray, jbyteArray);

/*
 * Class:     com_smarthome_head_SmartHomeHead
 * Method:    parseData
 * Signature: ([BISLjava/lang/String;[BZ)Lcom/smarthome/head/SmartHomeData;
 */
JNIEXPORT jobject JNICALL Java_com_smarthome_head_SmartHomeHead_parseData
  (JNIEnv *, jclass, jbyteArray, jint, jshort, jbyteArray, jbyteArray, jboolean);

/*
 * Class:     com_smarthome_head_SmartHomeHead
 * Method:    addZigbeeHead
 * Signature: (Lcom/smarthome/head/SmartHomeData;Ljava/lang/String;[B)[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_smarthome_head_SmartHomeHead_addZigbeeHead
  (JNIEnv *, jclass, jobject, jbyteArray, jbyteArray);

/*
 * Class:     com_smarthome_head_SmartHomeHead
 * Method:    parseZigbeeData
 * Signature: ([BISLjava/lang/String;J[BZ)Lcom/smarthome/head/SmartHomeData;
 */
JNIEXPORT jobject JNICALL Java_com_smarthome_head_SmartHomeHead_parseZigbeeData
  (JNIEnv *, jclass, jbyteArray, jint, jshort, jbyteArray, jlong, jbyteArray, jboolean);

#ifdef __cplusplus
}
#endif
#endif

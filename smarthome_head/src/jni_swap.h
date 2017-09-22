/*
 * jni_swap.h
 *
 *  Created on: 2015年11月30日
 *      Author: Lal
 */
#include "jni.h"
#include "smarthome_head.h"

#ifndef JNI_SWAP_H_
#define JNI_SWAP_H_

frame_data_context loadObject(JNIEnv *env, jobject jdata);
jobject setObject(JNIEnv *env, frame_data_context fdc , jbyteArray data);

#endif /* JNI_SWAP_H_ */

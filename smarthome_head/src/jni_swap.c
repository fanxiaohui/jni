/*
 * jni_swap.c
 *
 *  Created on: 2015年11月30日
 *      Author: Lal
 */
#include "jni_swap.h"
#include <stdio.h>
#include <stdlib.h>
#include "string.h"
#include "smarthome_head.h"


frame_data_context loadObject(JNIEnv *env, jobject jdata)
{
	frame_data_context context;

	//获取java类对象
	jclass stucls = (*env)->FindClass(env,"com/smarthome/head/SmartHomeData");
	//获取对象属性
	jfieldID isFinF = (*env)->GetFieldID(env, stucls, "isFin", "Z");
	jfieldID isReadF = (*env)->GetFieldID(env, stucls, "isRead", "Z");
	jfieldID isACKF = (*env)->GetFieldID(env, stucls, "isACK", "Z");
	jfieldID isMaskF = (*env)->GetFieldID(env, stucls, "isMask", "Z");

	jfieldID dataFormatF = (*env)->GetFieldID(env, stucls, "dataFormat", "B");
	jfieldID keyLevelF = (*env)->GetFieldID(env, stucls, "keyLevel", "B");
	jfieldID encryptTypeF = (*env)->GetFieldID(env, stucls, "encryptType", "B");
	jfieldID opcodeF = (*env)->GetFieldID(env, stucls, "opcode", "B");

	jfieldID msgIDF = (*env)->GetFieldID(env, stucls, "msgID", "S");
	jfieldID dataSequF = (*env)->GetFieldID(env, stucls, "dataSequ", "S");

	jfieldID sequenceF = (*env)->GetFieldID(env, stucls, "sequence", "I");

	jfieldID timeF = (*env)->GetFieldID(env, stucls, "time", "J");
	jfieldID dstIDF = (*env)->GetFieldID(env, stucls, "dstID", "J");
	jfieldID wsDstIDF = (*env)->GetFieldID(env, stucls, "wsDstID", "J");
	jfieldID srcIDF = (*env)->GetFieldID(env, stucls, "srcID", "J");

	jfieldID sessionIdF = (*env)->GetFieldID(env, stucls, "sessionId", "[B");
	jfieldID dataF = (*env)->GetFieldID(env, stucls, "data", "[B");


	jfieldID codeF = (*env)->GetFieldID(env, stucls, "code", "I");

	//获取属性值并存到结构体内
	{
		//获取boolean属性
		jboolean temp;

		temp = (*env)->GetBooleanField(env,jdata, isFinF);
		context.fin = temp?1:0;

		temp = (*env)->GetBooleanField(env,jdata, isReadF);
		context.read = temp?1:0;

		temp = (*env)->GetBooleanField(env,jdata, isACKF);
		context.ack = temp?1:0;

		temp = (*env)->GetBooleanField(env,jdata, isMaskF);
		context.mask = temp?1:0;

	}

	{
		//获取byte属性
		jbyte temp;

		temp = (*env)->GetByteField(env,jdata, dataFormatF);
		context.dataFormat = temp&0x03;

		temp = (*env)->GetByteField(env,jdata, keyLevelF);
		context.keyLevel = temp&0x03;

		temp = (*env)->GetByteField(env,jdata, encryptTypeF);
		context.encryptType = temp&0x03;

		temp = (*env)->GetByteField(env,jdata, opcodeF);
		context.opcode = temp&0x07;
	}

	{
		//获取short属性
		jshort temp;

		temp = (*env)->GetShortField(env,jdata, msgIDF);
		context.msgID = temp;

		temp = (*env)->GetShortField(env,jdata, dataSequF);
		context.dataSequ = temp&0x00ff;
	}

	{
		jint temp;

		temp = (*env)->GetIntField(env,jdata, sequenceF);
		context.sequence = temp &0x0000ffff;
	}

	{
		jlong temp;

		temp = (*env)->GetLongField(env,jdata, timeF);
		context.time = temp&0x00ffffffff;

		temp = (*env)->GetLongField(env,jdata, dstIDF);
		context.dstID = temp;

		temp = (*env)->GetLongField(env,jdata, wsDstIDF);
		context.wsDstID = temp;

		temp = (*env)->GetLongField(env,jdata, srcIDF);
		context.srcID = temp;
	}

	{
		jbyteArray temp;

		temp = (*env)->GetObjectField(env,jdata, sessionIdF);
		int sessionLen = 2;
		if (context.opcode == 2  || context.opcode == 5)
		{
			sessionLen = 8;
		}
		context.sessionId = malloc(sessionLen);
		if(temp == NULL)
		{
			memset(context.sessionId,0,sessionLen);
		}
		else{
			int length = (*env)->GetArrayLength(env,temp);

			if(length != sessionLen)
			{
				#ifdef IS_ANDROID
				(*env)->FatalError(env,"When opcode is 2or5 then sessionId should be 8 other cases should be 2.");
				#else
				printf ("When opcode is 2 then sessionId should be 8 other cases should be 2.");
				#endif
			}
			else
			{
				char *_session = (char*)(*env)->GetByteArrayElements(env,temp, 0);
				memmove(context.sessionId,_session,length);

				if(temp != NULL && _session != NULL)
				{
					(*env)->ReleaseByteArrayElements(env,temp,_session,0);
				}
			}
		}


		temp = (*env)->GetObjectField(env,jdata, dataF);
		if(temp == NULL)
		{
			context.length = 0;
			context.data = malloc(context.length);
		}
		else{
			context.length = (*env)->GetArrayLength(env,temp);
			context.data = malloc(context.length);

			char *_data = (char*)(*env)->GetByteArrayElements(env,temp, 0);
			memmove(context.data,_data,context.length);

			if(temp != NULL && _data != NULL)
			{
				(*env)->ReleaseByteArrayElements(env,temp,_data,0);
			}
		}

	}
	return context;
}

jobject setObject(JNIEnv *env, frame_data_context fdc , jbyteArray data)
{

	jclass stucls = (*env)->FindClass(env,"com/smarthome/head/SmartHomeData");
	jobject obj = (*env)->AllocObject(env, stucls);

	//获取对象属性
	jfieldID isFinF = (*env)->GetFieldID(env, stucls, "isFin", "Z");
	jfieldID isReadF = (*env)->GetFieldID(env, stucls, "isRead", "Z");
	jfieldID isACKF = (*env)->GetFieldID(env, stucls, "isACK", "Z");
	jfieldID isMaskF = (*env)->GetFieldID(env, stucls, "isMask", "Z");

	jfieldID dataFormatF = (*env)->GetFieldID(env, stucls, "dataFormat", "B");
	jfieldID keyLevelF = (*env)->GetFieldID(env, stucls, "keyLevel", "B");
	jfieldID encryptTypeF = (*env)->GetFieldID(env, stucls, "encryptType", "B");
	jfieldID opcodeF = (*env)->GetFieldID(env, stucls, "opcode", "B");

	jfieldID msgIDF = (*env)->GetFieldID(env, stucls, "msgID", "S");
	jfieldID dataSequF = (*env)->GetFieldID(env, stucls, "dataSequ", "S");

	jfieldID sequenceF = (*env)->GetFieldID(env, stucls, "sequence", "I");

	jfieldID timeF = (*env)->GetFieldID(env, stucls, "time", "J");
	jfieldID dstIDF = (*env)->GetFieldID(env, stucls, "dstID", "J");
	jfieldID srcIDF = (*env)->GetFieldID(env, stucls, "srcID", "J");

	jfieldID sessionIdF = (*env)->GetFieldID(env, stucls, "sessionId", "[B");
	jfieldID dataF = (*env)->GetFieldID(env, stucls, "data", "[B");

	jfieldID codeF = (*env)->GetFieldID(env, stucls, "code", "I");

	{
		(*env)->SetBooleanField(env,obj, isFinF,fdc.fin);
		(*env)->SetBooleanField(env,obj, isReadF,fdc.read);
		(*env)->SetBooleanField(env,obj, isACKF,fdc.ack);
		(*env)->SetBooleanField(env,obj, isMaskF,fdc.mask);

	}

	{
		(*env)->SetByteField(env,obj, dataFormatF,fdc.dataFormat);
		(*env)->SetByteField(env,obj, keyLevelF,fdc.keyLevel);
		(*env)->SetByteField(env,obj, encryptTypeF,fdc.encryptType);
		(*env)->SetByteField(env,obj, opcodeF,fdc.opcode);
	}

	{
		(*env)->SetShortField(env,obj, msgIDF,fdc.msgID);
		(*env)->SetShortField(env,obj, dataSequF,fdc.dataSequ);
	}

	{
		(*env)->SetIntField(env,obj, sequenceF,fdc.sequence);
		(*env)->SetIntField(env,obj, codeF,fdc.code);
	}

	{
		(*env)->SetLongField(env,obj, timeF,fdc.time);
		(*env)->SetLongField(env,obj, dstIDF,fdc.dstID);
		(*env)->SetLongField(env,obj, srcIDF,fdc.srcID);
	}

	{
		if(fdc.code ==0){
			if(fdc.length != 0)
			{
				jbyteArray array;

				int sessionLen = 2;
				if(fdc.opcode == 2 || fdc.opcode == 5)
				{
					sessionLen = 8;
				}

				array = (*env)->NewByteArray(env,sessionLen);

				(*env)->SetByteArrayRegion(env,array,0,sessionLen,(char*)fdc.sessionId);
				(*env)->SetObjectField(env,obj, sessionIdF, array);

				array = (*env)->NewByteArray(env,fdc.length);

				(*env)->SetByteArrayRegion(env,array,0,fdc.length,(char*)fdc.data);
				(*env)->SetObjectField(env,obj, dataF, array);

			}
		}
		else
		{
			(*env)->SetObjectField(env,obj, dataF, data);
		}
	}

	return obj;
}

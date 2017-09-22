/*Please use UTF-8 encoding*/
#include "com_smarthome_head_SmartHomeHead.h"
#include "jni_swap.h"

#include <stdio.h>
#include "string.h"
#include "smarthome_head.h"

JNIEXPORT jbyteArray JNICALL Java_com_smarthome_head_SmartHomeHead_addHead(JNIEnv *env, jobject thiz, jobject jdata, jbyteArray password, jbyteArray sessionAuth) {
	frame_data_context context = loadObject(env, jdata);
	if (context.length > 0x00ffff) {
#ifdef IS_ANDROID
		(*env)->FatalError(env,"Data length exceeds the maximum allowed length.");
#else
		printf("Data length exceeds the maximum allowed length.");
#endif
		return NULL;
	}

	char *_password;
	int passLen;
	if (password == NULL) {
		passLen = 0;
	} else {
		passLen = (*env)->GetArrayLength(env, password);
		_password = (char*) (*env)->GetByteArrayElements(env, password, 0);
	}

	char* passwordCopy = NULL;

	if (passLen > 0) {
		passwordCopy = malloc(passLen);
		memset(passwordCopy, 0, passLen);
		//		dataCopy = memmove(dataCopy,_data,16);
		memcpy(passwordCopy, _password, passLen);
	}

	unsigned char *_sessionAuth;
	unsigned char *sessionAuthCopy = malloc(16);
	if (sessionAuth == NULL) {
		memset(sessionAuthCopy, 0, 16);
	} else {
		int len = (*env)->GetArrayLength(env, sessionAuth);
		if (len == 16) {
			_sessionAuth = (unsigned char *) (*env)->GetByteArrayElements(env, sessionAuth, 0);
			sessionAuthCopy = memmove(sessionAuthCopy, _sessionAuth, 16);
		} else {
#ifdef IS_ANDROID
			(*env)->FatalError(env,"sessionAuth length should be 16.");
#else
			printf("sessionAuth length should be 16.");
#endif
			return NULL;
		}
	}

	int len = getHeadLength();
	len += 2; //默认session为2字节长
	if (context.opcode == 3) {
		len = getWSHeadLength();
	} else if (context.opcode == 2 || context.opcode == 5) {
		len += 6; //opcode为2时session为8（2+6）字节长
	}

	len += 0x00ffff;

	char *out = (char *) malloc(len);

	unsigned int length = addHead((frame_data_context *) &context, passwordCopy, passLen, sessionAuthCopy, out);
	jbyteArray array = (*env)->NewByteArray(env, length);
	(*env)->SetByteArrayRegion(env, array, 0, length, out);

	if (password != NULL && _password != NULL) {
		(*env)->ReleaseByteArrayElements(env, password, _password, 0);
	}
	if (sessionAuth != NULL && _sessionAuth != NULL) {
		(*env)->ReleaseByteArrayElements(env, sessionAuth, _sessionAuth, 0);
	}
	(*env)->DeleteLocalRef(env, jdata);
	(*env)->DeleteLocalRef(env, password);
	(*env)->DeleteLocalRef(env, sessionAuth);
	(*env)->DeleteLocalRef(env, thiz);
	free(out);
	free(sessionAuthCopy);
	free(context.sessionId);
	free(context.data);

	if (passwordCopy != NULL) {
		free(passwordCopy);
	}

	return array;
}

JNIEXPORT jobject JNICALL Java_com_smarthome_head_SmartHomeHead_parseData(JNIEnv *env, jobject thiz, jbyteArray data, jint s, jshort ds, jbyteArray password, jbyteArray sessionAuth, jboolean needCheck) {
	unsigned char *_data;
	int dataLen;
	if (data == NULL) {
		dataLen = 0;
	} else {
		dataLen = (*env)->GetArrayLength(env, data);
		_data = (char*) (*env)->GetByteArrayElements(env, data, 0);
	}
	char* dataCopy = NULL;

	if (dataLen > 0) {
		dataCopy = malloc(dataLen);
		memset(dataCopy, 0, dataLen);

//		dataCopy = memmove(dataCopy,_data,16);
		memcpy(dataCopy, _data, dataLen);
	}

	char *_password;
	int passLen;
	if (password == NULL) {
		passLen = 0;
	} else {
		passLen = (*env)->GetArrayLength(env, password);
		_password = (char*) (*env)->GetByteArrayElements(env, password, 0);
	}
	char* passwordCopy = NULL;

	if (passLen > 0) {
		passwordCopy = malloc(passLen);
		memset(passwordCopy, 0, passLen);

//		dataCopy = memmove(dataCopy,_data,16);
		memcpy(passwordCopy, _password, passLen);
	}

	unsigned char *_sessionAuth;
	unsigned char *sessionAuthCopy = malloc(16);
	if (sessionAuth == NULL) {
		memset(sessionAuthCopy, 0, 16);
	} else {
		int len = (*env)->GetArrayLength(env, sessionAuth);
		if (len == 16) {
			_sessionAuth = (unsigned char *) (*env)->GetByteArrayElements(env, sessionAuth, 0);
			sessionAuthCopy = memmove(sessionAuthCopy, _sessionAuth, 16);
		} else {
#ifdef IS_ANDROID
			(*env)->FatalError(env,"sessionAuth length should be 16.");
#else
			printf("sessionAuth length should be 16.");
#endif
			return NULL;
		}
	}

	char _needCheck = needCheck ? 1 : 0;

	frame_data_context fdc = parseData(dataCopy, dataLen, (int) s, (short) ds, passwordCopy, passLen, sessionAuthCopy, _needCheck);
	jobject obj = setObject(env, fdc, data);

	if (password != NULL && _password != NULL) {
		(*env)->ReleaseByteArrayElements(env, password, _password, 0);
	}
	if (data != NULL && _data != NULL) {
		(*env)->ReleaseByteArrayElements(env, data, _data, 0);
	}
	if (sessionAuth != NULL && _sessionAuth != NULL) {
		(*env)->ReleaseByteArrayElements(env, sessionAuth, _sessionAuth, 0);
	}
	(*env)->DeleteLocalRef(env, data);
	(*env)->DeleteLocalRef(env, password);
	(*env)->DeleteLocalRef(env, sessionAuth);
	(*env)->DeleteLocalRef(env, thiz);
	printf("free sessionAuthCopy");

	free(sessionAuthCopy);

	if (dataCopy != NULL) {
		printf("free dataCopy");
		free(dataCopy);
	}
	if (passwordCopy != NULL) {
		printf("free passwordCopy");
		free(passwordCopy);
	}
	return obj;
}

JNIEXPORT jbyteArray JNICALL Java_com_smarthome_head_SmartHomeHead_addZigbeeHead(JNIEnv *env, jobject thiz, jobject jdata, jbyteArray password, jbyteArray sessionAuth) {
	frame_data_context context = loadObject(env, jdata);
	if (context.length > 0x00ff) {
#ifdef IS_ANDROID
		(*env)->FatalError(env,"Data length exceeds the maximum allowed length.");
#else
		printf("Data length exceeds the maximum allowed length.");
#endif
		return NULL;
	}

	char *_password;
	int passLen;
	if (password == NULL) {
		passLen = 0;
	} else {
		passLen = (*env)->GetStringUTFLength(env, password);
		_password = (char*) (*env)->GetStringUTFChars(env, password, 0);
	}

	unsigned char *_sessionAuth;
	unsigned char *sessionAuthCopy = malloc(16);
	if (sessionAuth == NULL) {
		memset(sessionAuthCopy, 0, 16);
	} else {
		int len = (*env)->GetArrayLength(env, sessionAuth);
		if (len == 16) {
			_sessionAuth = (unsigned char *) (*env)->GetByteArrayElements(env, sessionAuth, 0);
			sessionAuthCopy = memmove(sessionAuthCopy, _sessionAuth, 16);
		} else {
#ifdef IS_ANDROID
			(*env)->FatalError(env,"sessionAuth length should be 16.");
#else
			printf("sessionAuth length should be 16.");
#endif
			return NULL;
		}
	}

	int len = getHeadLength();
	len += 2; //默认session为2字节长
	len += 0x00ff;

	char *out = (char *) malloc(len);

	unsigned int length = addZigbeeHeard((frame_data_context *) &context, _password, passLen, sessionAuthCopy, out);

	jbyteArray array = (*env)->NewByteArray(env, length);
	(*env)->SetByteArrayRegion(env, array, 0, length, out);

	if (password != NULL && _password != NULL) {
		(*env)->ReleaseStringUTFChars(env, password, _password);
	}
	if (sessionAuth != NULL && _sessionAuth != NULL) {
		(*env)->ReleaseByteArrayElements(env, sessionAuth, _sessionAuth, 0);
	}
	(*env)->DeleteLocalRef(env, jdata);
	(*env)->DeleteLocalRef(env, password);
	(*env)->DeleteLocalRef(env, sessionAuth);
	(*env)->DeleteLocalRef(env, thiz);
	free(out);
	free(sessionAuthCopy);
	free(context.sessionId);
	free(context.data);

	return array;
}
JNIEXPORT jobject JNICALL Java_com_smarthome_head_SmartHomeHead_parseZigbeeData(JNIEnv *env, jobject thiz, jbyteArray data, jint s, jshort ds, jbyteArray password, jlong mac, jbyteArray sessionAuth, jboolean needCheck) {
	printf("DFASDFDSFDSF------");
	unsigned char *_data;
	int dataLen;
	if (data == NULL) {
		dataLen = 0;
	} else {
		dataLen = (*env)->GetArrayLength(env, data);
		_data = (char*) (*env)->GetByteArrayElements(env, data, 0);
	}

	char *_password;
	int passLen;
	if (password == NULL) {
		passLen = 0;
	} else {
		passLen = (*env)->GetStringUTFLength(env, password);
		_password = (char*) (*env)->GetStringUTFChars(env, password, 0);
	}

	unsigned char *_sessionAuth;
	unsigned char *sessionAuthCopy = malloc(16);
	if (sessionAuth == NULL) {
		memset(sessionAuthCopy, 0, 16);
	} else {
		int len = (*env)->GetArrayLength(env, sessionAuth);
		printf("DFASDFDSFDSF------%d", len);

		if (len == 16) {
			_sessionAuth = (unsigned char *) (*env)->GetByteArrayElements(env, sessionAuth, 0);
			sessionAuthCopy = memmove(sessionAuthCopy, _sessionAuth, 16);
		} else {

#ifdef IS_ANDROID
			(*env)->FatalError(env,"sessionAuth length should be 16.");
#else
			printf("sessionAuth length should be 16.");
#endif
			return NULL;
		}
	}

	char _needCheck = needCheck ? 1 : 0;
	frame_data_context fdc = parseZigbeeData(_data, dataLen, (int) s, (short) ds, _password, passLen, (long long) mac, sessionAuthCopy, _needCheck);
	jobject obj = setObject(env, fdc, data);

	if (password != NULL && _password != NULL) {
		(*env)->ReleaseStringUTFChars(env, password, _password);
	}
	if (data != NULL && _data != NULL) {
		(*env)->ReleaseByteArrayElements(env, data, _data, 0);
	}
	if (sessionAuth != NULL && _sessionAuth != NULL) {
		(*env)->ReleaseByteArrayElements(env, sessionAuth, _sessionAuth, 0);
	}
	(*env)->DeleteLocalRef(env, data);
	(*env)->DeleteLocalRef(env, password);
	(*env)->DeleteLocalRef(env, sessionAuth);
	(*env)->DeleteLocalRef(env, thiz);
	free(sessionAuthCopy);

	return obj;
}

#include "encryption.h"
#include "key_manage.h"

#include "rc4.h"
#include "aes.h"
#include "des.h"

#include <stdio.h>
#include "string.h"

#include "stdlib.h"


unsigned int encryptData(char* data, int dataLen,
		KEY_LEVEL level, ENCRYPT_TYPE type,
		unsigned short seqnum,
		unsigned int time,
		unsigned char srcID[8],
		unsigned char session_auth[16],
		unsigned char passwd[16],
        char isCloud)
{
	unsigned char *key = (unsigned char *)malloc(16);
	memset(key,0,16);

	switch(level){
		case KEYLEVEL_NONE:
			return dataLen;
		case KEYLEVEL_RANDOM:
			key_random_get(seqnum, time,srcID,key);
			break;
		case KEYLEVEL_SESSION_AUTH:
			key_session_auth(passwd,session_auth,key,isCloud);
			break;
		case KEYLEVEL_FORMAL:
			key_formal_get(seqnum,time,srcID,session_auth,passwd,key,isCloud);
			break;
	}

	switch (type) {
		case ENCRYPT_RC4:
			RC4_EnDecrypt((unsigned char *)data,dataLen,key,16);
			break;
		case ENCRYPT_DES:
			dataLen = des_encrypt_ex((const char*)key,data,dataLen);
			break;
		case ENCRYPT_AES:
			dataLen = aes_encrypt((unsigned char *)data,dataLen,key,16);
		default:
			return dataLen;
	}
	return dataLen;
}

unsigned int decryptData(char* data, int dataLen,
		KEY_LEVEL level, ENCRYPT_TYPE type,
		unsigned short seqnum,
		unsigned int time,
		unsigned char srcID[8],
		unsigned char session_auth[16],
		unsigned char passwd[16],
        char isCloud)
{
	unsigned char *key = (unsigned char *)malloc(16);
	memset(key,0,16);

	switch(level){
		case KEYLEVEL_NONE:
			return dataLen;
		case KEYLEVEL_RANDOM:
			key_random_get(seqnum,time,srcID,key);
			break;
		case KEYLEVEL_SESSION_AUTH:
			key_session_auth(passwd,session_auth,key,isCloud);
			break;
		case KEYLEVEL_FORMAL:
			key_formal_get(seqnum,time,srcID,session_auth,passwd,key,isCloud);
			break;
	}

	switch (type) {
		case ENCRYPT_RC4:
			RC4_EnDecrypt((unsigned char *)data,dataLen,key,16);
			break;
		case ENCRYPT_DES:
			dataLen = des_decrypt_ex((const char *)key,data,dataLen);
			break;
		case ENCRYPT_AES:
			dataLen = aes_decrypt((unsigned char *)data,dataLen,key,16);
		default:
			return dataLen;
	}
	free(key);
	return dataLen;
}

#ifndef __ENCRYPTION_H__
#define __ENCRYPTION_H__

#ifdef __cplusplus
extern "C"{
#endif


typedef enum {
	KEYLEVEL_NONE = 0,
	KEYLEVEL_RANDOM,
	KEYLEVEL_SESSION_AUTH,
	KEYLEVEL_FORMAL
}KEY_LEVEL;

typedef enum {
	ENCRYPT_RC4 = 0,
	ENCRYPT_DES,
	ENCRYPT_AES
}ENCRYPT_TYPE;


unsigned int encryptData(char* data, int dataLen,
		KEY_LEVEL level, ENCRYPT_TYPE type,
		unsigned short seqnum,
		unsigned int time,
		unsigned char srcID[8],
		unsigned char session_auth[16],
		unsigned char passwd[16],
        char isCloud);

unsigned int decryptData(char* data, int dataLen,
		KEY_LEVEL level, ENCRYPT_TYPE type,
		unsigned short seqnum,
		unsigned int time,
		unsigned char srcID[8],
		unsigned char session_auth[16],
		unsigned char passwd[16],
        char isCloud);

#ifdef __cplusplus

#endif

#endif

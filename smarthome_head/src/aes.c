
#include "aes.h"
#include "string.h"


#define CBC 1
#define ECB 1
#include "aes128.h"

#define		BLOCK_LEN		16		//定义AES块长度

//AES128加密算法，ECB模式，支持自动补齐，输入数组能接受的最大长度要大于实际输入长度16个字节
signed int aes_encrypt(unsigned char *data, unsigned short dataLen, unsigned char *key, unsigned char keyLen)
{
	unsigned char 	i;
	unsigned char	readBuf[16];
//	unsigned int readLen = 16;
	unsigned short addLen = 0;

	addLen = BLOCK_LEN - (dataLen%BLOCK_LEN);

	for(i=0;i<addLen;i++)
	{
		data[dataLen+i] = addLen;
	}

	dataLen += addLen;

	for(i=0; i<(dataLen/BLOCK_LEN); i++)
	{
		AES128_ECB_encrypt(&data[i*BLOCK_LEN],key,readBuf);
//		RT_AES_Encrypt(&data[i*BLOCK_LEN], BLOCK_LEN, key, keyLen, readBuf, &readLen);
		memcpy(&data[i*BLOCK_LEN], readBuf, BLOCK_LEN);
	}

	return dataLen;
}


//AES128解密算法，ECB模式.
signed int aes_decrypt(unsigned char *data, unsigned short dataLen, unsigned char *key, unsigned char keyLen)
{
	unsigned char 	i;
	unsigned char	readBuf[16];
//	unsigned int readLen = 16;
	unsigned char 	patch;
	unsigned short retLen;

	if((dataLen < 16) || (dataLen % 16) != 0)
	{
		return -1;
	}

	for(i=0; i<dataLen/BLOCK_LEN; i++)
	{
		AES128_ECB_decrypt(&data[i*BLOCK_LEN],key,readBuf);
//		RT_AES_Decrypt(&data[i*BLOCK_LEN], BLOCK_LEN, key, keyLen, readBuf, &readLen);
		memcpy(&data[i*BLOCK_LEN], readBuf, BLOCK_LEN);
	}

	patch = data[dataLen-1];

	if (patch > 16)
	{
		return -2;
	}

	for (i = 1; i <= patch; i++)
	{
		retLen = dataLen - i;
		if (data[retLen] != patch)
			break;
		data[retLen] = '\0';
	}

	dataLen -= patch;

	return dataLen;
}

signed int aes_original_encrypt(unsigned char *data, unsigned short dataLen, unsigned char *key, unsigned char keyLen)
{
	unsigned char 	i;
	unsigned char	readBuf[16];
//	unsigned int readLen = 16;

	if((dataLen % 16) != 0)
	{
		return -1;
	}

	for(i=0; i<(dataLen/BLOCK_LEN); i++)
	{
		AES128_ECB_encrypt(&data[i*BLOCK_LEN],key,readBuf);
		//		RT_AES_Encrypt(&data[i*BLOCK_LEN], BLOCK_LEN, key, keyLen, readBuf, &readLen);
		memcpy(&data[i*BLOCK_LEN], readBuf, BLOCK_LEN);
	}

	return dataLen;
}
signed int aes_original_decrypt(unsigned char *data, unsigned short dataLen, unsigned char *key, unsigned char keyLen)
{
	{
		unsigned char 	i;
		unsigned char	readBuf[16];
//		unsigned int readLen = 16;
//		unsigned char 	patch;
//		unsigned short retLen;

		if((dataLen < 16) || (dataLen % 16) != 0)
		{
			return -1;
		}

		for(i=0; i<dataLen/BLOCK_LEN; i++)
		{
			AES128_ECB_decrypt(&data[i*BLOCK_LEN],key,readBuf);
	//		RT_AES_Decrypt(&data[i*BLOCK_LEN], BLOCK_LEN, key, keyLen, readBuf, &readLen);
			memcpy(&data[i*BLOCK_LEN], readBuf, BLOCK_LEN);
		}
		return dataLen;
	}
}





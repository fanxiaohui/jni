
#include "key_manage.h"
#include "md5.h"
#include "aes.h"
#include "string.h"
# include <stdio.h>
# include <stdlib.h>
#include "BCLog.h"


const unsigned char  randomIV[16] = {0x0f, 0x1f, 0x2f, 0x3f, 0x4f, 0x5f, 0x6f, 0x7f,
		0x8f, 0x9f, 0xaf, 0xbf, 0xcf, 0xdf, 0xef, 0xff};

//产生16个字节的随机数，范围0~255
void random_data_creat(unsigned char *randomData, unsigned int time)
{
	unsigned char i;

	for(i=0; i<16; i++)
	{
		randomData[i] = time%randomIV[i];
	}
}

//随机密钥获取

//void key_random_get(u16 seqnum, u32 time, u8 *id, u8 *key)
//{
//	unsigned char timeXor, i, n, *pTime = (unsigned char*)&time;
//	unsigned int ntime = ~time;
//
//	if((id == NULL) || (key == NULL))
//		return;
//
//	timeXor = pTime[0]^pTime[1]^pTime[2]^pTime[3];
//	memcpy((void*)&key[0], (void*)&time, 4);
//	memcpy((void*)&key[12], (void*)&seqnum, 2);
//	memcpy((void*)&key[14], (void*)&seqnum, 2);
//	memcpy((void*)&key[4], (void*)&id[0], 8);
//	for(i = 0; i<((timeXor&0x03)+1); i++)
//	{
//		for(n =0; n<16; n++)
//		{
//			key[n] ^= id[(timeXor+(i+n))&0x07]^pTime[((i+n)&0x03)];
//		}
//	}
//}


void key_random_get(unsigned short seqnum, unsigned int time, unsigned char *id, unsigned char *key)
{
	unsigned char timeXor, i, n, *pTime = (unsigned char*)&time;
//	unsigned int ntime = ~time;

	if((id == NULL) || (key == NULL))
		return;

	timeXor = pTime[0]^pTime[1]^pTime[2]^pTime[3];
	memcpy((void*)&key[0], (void*)&time, 4);
	memcpy((void*)&key[12], (void*)&seqnum, 2);
	memcpy((void*)&key[14], (void*)&seqnum, 2);
	memcpy((void*)&key[4], (void*)&id[0], 8);
	for(i = 0; i<((timeXor&0x03)+1); i++)
	{
		for(n =0; n<16; n++)
		{
			key[n] ^= id[(timeXor+(i+n))&0x07]^pTime[((i+n)&0x03)];
		}
	}
}

//手机登陆成功后生成密证，并返回手机用于产生密证的加密数据
void key_session_auth(unsigned char *privateKey, unsigned char *retAuth, unsigned char *sessionKey,char isCloud)
{
	unsigned char data[32];
	unsigned char md5PrivateKey[16];
	printToHex("privateKey",privateKey,16);

	MD5_CTX md5;
	MD5Init(&md5);
	MD5Update(&md5,privateKey,16);
	MD5Final(&md5,md5PrivateKey);
	printToHex("md5PrivateKey",md5PrivateKey,16);

	//	random_data_creat(retAuth, time);


    if (isCloud) {
        memcpy(data, retAuth, 16);
        memcpy(data+16, privateKey, 16);
    }else{
        aes_original_decrypt(retAuth,16,md5PrivateKey,16);
        printToHex("retAuth",retAuth,16);
        memcpy(data, retAuth, 16);
        memcpy(data+16, md5PrivateKey, 16);
    }
	printToHex("data",data,32);

	MD5Init(&md5);
	MD5Update(&md5,data,32);
	MD5Final(&md5,sessionKey);


	//	md5_caculate(sessionKey, data, 32);			//设备私钥与临时密钥拼接后产生密证

	//	aes_encrypt(retAuth, 16, privateKey, 16);	//设备私钥对临时密钥进行加密后返回给手机

}

//根据从平台获取到的加密数据计算平台密证
void key_cloud_auth(unsigned char *cookie, unsigned char cookieLen, unsigned char *getAuth, unsigned char *cloudKey)
{
	//	unsigned char i;
	//	unsigned char privateKey[16];
	//	unsigned char data[32];
	//
	//	md5_caculate(privateKey, cookie, cookieLen);
	//	aes_decrypt(getAuth, 16, privateKey, 16);
	//	memcpy(data, getAuth, 16);
	//	memcpy(data+16, privateKey, 16);
	//
	//	md5_caculate(cloudKey, data, 32);
}

//获取手机或平台登陆成功后用于加解密的密钥(密证+随机密钥)
void key_formal_get(unsigned short seqnum, unsigned int time, unsigned char *srcid, unsigned char *authKey,unsigned char *privateKey, unsigned char *keyOut,char isCloud)
{
	unsigned char i;
	unsigned char randomKey[16];
//	unsigned char md5PrivateKey[16];
//
//	MD5_CTX md5;
//	MD5Init(&md5);
//	MD5Update(&md5,privateKey,16);
//	MD5Final(&md5,md5PrivateKey);
//
//	myP("md5PrivateKey",md5PrivateKey,16);
	printf("time--%d",time);
	printToHex("srcid",srcid,8);
	printToHex("authKey",authKey,16);

	key_random_get(seqnum, time, srcid, randomKey);
	printToHex("randomKey",randomKey,16);

	unsigned char session[16];
	key_session_auth(privateKey,authKey,session,isCloud);
//	aes_original_decrypt(authKey,16,md5PrivateKey,16);
	printToHex("authKeyAfter",session,16);

	for(i=0; i<16; i++)
	{
		keyOut[i] = randomKey[i] ^ session[i];
	}
	printToHex("keyOut",keyOut,16);


}

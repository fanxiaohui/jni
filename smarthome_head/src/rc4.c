
#include "rc4.h"

/******************************************************************************
 * @fn      rc4_init
 *
 * @brief  初始化函数
 *
 * input parameters
 *
 * @param   *s  S盒数组，256字节
 * @param   *key  密钥数组 
 * @param   len 密钥数组长度 长度范围 1~256
 *
 * output parameters
 *
 * @param  
 *
 * @return  None
 *
 */
void rc4_init(unsigned char *s, unsigned char *key,unsigned short Len)
{
	  unsigned short i=0,j=0;
  unsigned char k[256];      //堆栈空间太小？
  unsigned char tmp=0;
  
  for(i=0;i<256;i++)
  {
    s[i]=i;
    k[i]=key[i%Len];
  }
  for(i=0;i<256;i++)
  {
    j=(j+s[i]+k[i])%256;
    tmp=s[i];
    s[i]=s[j];                    //交换s[i]和s[j]
    s[j]=tmp;
  }
}
 
/******************************************************************************
 * @fn      rc4_crypt
 *
 * @brief  RC4加解密函数
 *
 * input parameters
 *
 * @param   *s  init函数搅乱的S盒数组
 * @param   *Data  需要加密或解密的数据
 * @param   len  加/解密数据的长度
 *
 * output parameters
 *
 * @param  
 *
 * @return  None
 *
 */
void rc4_crypt(unsigned char *s,unsigned char *Data,unsigned short Len)
{
	unsigned short i=0,j=0,t=0;
	unsigned short k=0;
	unsigned char tmp;
  
  for(k=0;k<Len;k++)
  {
    i=(i+1)%256;
    j=(j+s[i])%256;
    tmp=s[i];
    s[i]=s[j];                    //交换s[x]和s[y]
    s[j]=tmp;
    t=(s[i]+s[j])%256;
    Data[k]^=s[t];
  }
}

void RC4_EnDecrypt(unsigned char *Data, unsigned short datalen, unsigned char *RC4key, unsigned char keylen)
{  
  unsigned char sbox2[256];   //存放打乱后的SBOX
  rc4_init(sbox2,RC4key,keylen);
  rc4_crypt(sbox2,Data,datalen);
}

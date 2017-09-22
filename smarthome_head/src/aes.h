/*
 * aes2.h
 *
 *  Created on: 2015年11月13日
 *      Author: Lal
 */

#ifndef AES_H_
#define AES_H_

signed int aes_encrypt(unsigned char *data, unsigned short dataLen, unsigned char *key, unsigned char keyLen);
signed int aes_decrypt(unsigned char *data, unsigned short dataLen, unsigned char *key, unsigned char keyLen);

signed int aes_original_encrypt(unsigned char *data, unsigned short dataLen, unsigned char *key, unsigned char keyLen);
signed int aes_original_decrypt(unsigned char *data, unsigned short dataLen, unsigned char *key, unsigned char keyLen);


#endif /* AES_H_ */

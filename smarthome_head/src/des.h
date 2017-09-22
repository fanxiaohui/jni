#ifndef __DES_H__
#define __DES_H__

#ifdef __cplusplus
extern "C"{
#endif
int des_encrypt_ex(const char *key, char *buf, unsigned int length);
int des_decrypt_ex(const char *key, char *buf, unsigned int length);

#ifdef __cplusplus
}
#endif
#endif

/*
 * key_manage.h
 *
 *  Created on: 2015年11月13日
 *      Author: Lal
 */

#ifndef KEY_MANAGE_H_
#define KEY_MANAGE_H_
void random_data_creat(unsigned char *randomData, unsigned int time);
void key_random_get(unsigned short seqnum, unsigned int time, unsigned char *id, unsigned char *key);
void key_session_auth(unsigned char *privateKey, unsigned char *retAuth, unsigned char *sessionKey,char isCloud);
void key_cloud_auth(unsigned char *cookie, unsigned char cookieLen, unsigned char *getAuth, unsigned char *cloudKey);
void key_formal_get(unsigned short seqnum, unsigned int time, unsigned char *srcid, unsigned char *authKey,unsigned char *privateKey, unsigned char *keyOut,char isCloud);

#endif /* KEY_MANAGE_H_ */

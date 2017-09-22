#ifndef SMARTHOME_HEAD_H
#define SMARTHOME_HEAD_H
#ifdef __cplusplus
extern "C" {
#endif
#pragma pack(1)
typedef struct
{
	unsigned char fin:1;
	unsigned char read:1;
	unsigned char ack:1;
	unsigned char mask:1;
	unsigned char dataFormat:2;
	unsigned char keyLevel:2;
	unsigned char encryptType:2;
	unsigned char opcode:3;
	unsigned char dataSequ;
	unsigned short msgID;
	unsigned short sequence;
	unsigned int time;
	unsigned long long dstID;
	unsigned long long wsDstID;
	unsigned long long srcID;
	int code;
	unsigned short length;
	unsigned char *sessionId;
	char *data;

}frame_data_context;

int addHead(frame_data_context *fdc,char *password, int passLen,unsigned char session_auth[16], char * out);
frame_data_context parseData(const char *data, int dataLen, int s, short ds, char *password, int passLen,unsigned char session_auth[16],char needCheck);

int addZigbeeHeard(frame_data_context *fdc,char *password, int passLen,unsigned char session_auth[16], char * out);
frame_data_context parseZigbeeData(const char *data, int dataLen, int s, short ds, char *password, int passLen,long long mac,unsigned char session_auth[16], char needCheck);

unsigned char crc8_caculate(const char *data, unsigned short lenght);

unsigned int getHeadLength();
unsigned int getWSHeadLength();
unsigned int getZigbeeHeadLength();
void conn_mask_content(char * payload, unsigned char payload_size, char * mask);

#ifdef __cplusplus
}
#endif
#endif

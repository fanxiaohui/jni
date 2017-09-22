/*Please use UTF-8 encoding*/

#include "smarthome_head.h"
#include "BCLog.h"
#include "encryption.h"
#include "string.h"


#define VER 0
#define POLY    0x131
#define DEBUG_PRINTF 0

//基本帧头数据
#pragma pack(1)
typedef struct
{
	unsigned char opcode:3;
	unsigned char fshort:1;
	unsigned char ver:2;
	unsigned char mask:1;
	unsigned char fin:1;
	unsigned char reserve;
	unsigned short sequence;
	unsigned char time[4];
}BasicFrameHead;

//长帧头扩展数据
#pragma pack(1)
typedef struct
{
	unsigned char dstid[8];
	unsigned char srcid[8];
}LongFrameHead;

//代理转发扩展数据
#pragma pack(1)
typedef struct
{
	unsigned char guid[16];
}ProxyFrameHead;


#pragma pack(1)
typedef struct
{
	unsigned char encrypt:2;
	unsigned char key:2;
	unsigned char format:2;
	unsigned char ack:1;
	unsigned char read:1;
	unsigned char sequ;
	unsigned short msgID;
	unsigned short lenght;
	unsigned char headcheck;
	unsigned char check;
	unsigned char session[0];
//	unsigned char data[0];
}LongDataHeard;


//#pragma pack(1)
//typedef struct
//{
//	unsigned char encrypt:2;
//	unsigned char key:2;
//	unsigned char format:2;
//	unsigned char ack:1;
//	unsigned char read:1;
//	unsigned char sequ;
//	unsigned short msgID;
//	unsigned short lenght;
//	unsigned char headcheck;
//	unsigned char check;
//	unsigned char session[0];
////	unsigned char data[0];
//}ShortDataHeard;


int addHead(frame_data_context *fdc,char *password, int passLen, unsigned char session_auth[16],char * out)
{

	int _len = 	sizeof(BasicFrameHead)+sizeof(LongFrameHead)+sizeof(LongDataHeard);
	int sessionLen = 2;
	unsigned char opcode = fdc->opcode;

	//如果opcode == 3则需要添加一个额外的帧头和一个16字节的GUID
	if(opcode == 3)
	{
		opcode = 1;
		BasicFrameHead *_bfh = (BasicFrameHead *)out;
		_bfh->fin = fdc->fin;
		_bfh->mask = fdc->mask ? 1:0;
		_bfh->ver = VER &0x08;
		_bfh->opcode = fdc->opcode;
		_bfh->sequence = fdc->sequence;
		_bfh->fshort = 0;
		memcpy(_bfh->time, &(fdc->time), sizeof(_bfh->time));
		out += sizeof(BasicFrameHead);
		LongFrameHead *_lfh = (LongFrameHead *)out;
		memcpy(_lfh->dstid, &(fdc->wsDstID), sizeof(_lfh->dstid));
		memcpy(_lfh->srcid, &(fdc->srcID), sizeof(_lfh->srcid));

		if(fdc->mask){
			conn_mask_content(out, sizeof(LongDataHeard)+ sizeof(ProxyFrameHead),(char *)_bfh->time );
		}

		out += sizeof(LongFrameHead);
		out	+= sizeof(ProxyFrameHead);

		_len += sizeof(BasicFrameHead)+sizeof(LongFrameHead)+sizeof(ProxyFrameHead);

	}else if(opcode == 2 || opcode == 5)
	{
		sessionLen = 8;
	}
	_len+=sessionLen;

	BasicFrameHead *bfh = (BasicFrameHead *)out;
	LongFrameHead *lfh = (LongFrameHead *)(out + sizeof(BasicFrameHead));
	LongDataHeard *ldh = (LongDataHeard *)(out + sizeof(BasicFrameHead)+ sizeof(LongFrameHead));

	bfh->fin = fdc->fin;
	bfh->mask = fdc->mask ? 1:0;
	bfh->ver = VER &0x08;
	bfh->opcode = opcode;
	bfh->sequence = fdc->sequence;
	bfh->fshort = 0;

	memcpy(bfh->time, &(fdc->time), sizeof(bfh->time));

	memcpy(lfh->dstid, &(fdc->dstID), sizeof(lfh->dstid));
	memcpy(lfh->srcid, &(fdc->srcID), sizeof(lfh->srcid));

	ldh->read = fdc->read;
	ldh->ack = fdc->ack;
	ldh->format = fdc->dataFormat;
	ldh->key = fdc->keyLevel;
	ldh->encrypt = fdc->encryptType;
	ldh->sequ = fdc->dataSequ;
	ldh->msgID = fdc->msgID;
	ldh->headcheck = 0;
	ldh->check = 0;
	memcpy(ldh->session, fdc->sessionId, sessionLen);

	if(fdc->length > 0) {
		memmove((ldh->session+sessionLen), fdc->data, fdc->length);
	}
	printToHex("session",ldh->session,8);
	ldh->check = crc8_caculate((const char *)&(ldh->session),(short)fdc->length+sessionLen);

	unsigned int dataLen = encryptData((char *)&(ldh->session),fdc->length+sessionLen,
			ldh->key,ldh->encrypt,
			fdc->sequence,
			fdc->time,
			lfh->srcid,
			session_auth,
			(unsigned char *)password,
            opcode== 5);

	ldh->lenght = dataLen - sessionLen;

	ldh->headcheck = crc8_caculate((const char *)out,
			(sizeof(BasicFrameHead)
			+ sizeof(LongFrameHead)
			+ sizeof(LongDataHeard)
			- sizeof(ldh->headcheck)
			- sizeof(ldh->check)));
	_len += ldh->lenght;

	if(fdc->mask) {
		conn_mask_content((char *)lfh->dstid, sizeof(LongDataHeard) + sizeof(LongFrameHead), (char *)bfh->time);
	}
	return _len;

}

frame_data_context parseData(const char *data, int dataLen, int s, short ds, char *password, int passLen,unsigned char session_auth[16],char needCheck)
{
	BasicFrameHead *_bfh = (BasicFrameHead *)data;
	BasicFrameHead *bfh;
	int sessionLen = 2;
	//判断是不是平台代理转发
	if (_bfh->opcode == 3) {
		data += sizeof(BasicFrameHead) + sizeof(LongFrameHead) + sizeof(ProxyFrameHead);//GUID占位
		dataLen -= sizeof(BasicFrameHead) + sizeof(LongFrameHead) + sizeof(ProxyFrameHead);
		bfh = (BasicFrameHead *)data;
	}
	else
	{

		bfh = _bfh;
	}
    if (bfh->opcode == 2 || bfh->opcode == 5)
    {
        sessionLen = 8;
    }
	LongFrameHead *lfh = (LongFrameHead *)(data + sizeof(BasicFrameHead));
	LongDataHeard *ldh = (LongDataHeard *)(data + sizeof(BasicFrameHead)+ sizeof(LongFrameHead));
	frame_data_context fdc;
	fdc.mask = bfh->mask;

	if (fdc.mask)
	{
		conn_mask_content((char *)lfh->dstid, sizeof(LongDataHeard)+ sizeof(LongFrameHead) ,(char *)bfh->time );
	}


	fdc.code = 0;
	unsigned int _len = ldh->lenght;
	unsigned short seq =0;
	unsigned int time = 0;
	memcpy(&(time),bfh->time,sizeof(bfh->time));
	seq = bfh->sequence;
	fdc.length = ldh->lenght;

	if(needCheck)
	{
		if(_len != (dataLen - sizeof(BasicFrameHead) - sizeof(LongFrameHead) - sizeof(LongDataHeard) - sessionLen))
		{
			fdc.code = -1;
			return fdc;
		}
		unsigned char headcheck = crc8_caculate(data,
				(sizeof(BasicFrameHead)
				+ sizeof(LongFrameHead)
				+ sizeof(LongDataHeard)
				- sizeof(ldh->headcheck)
				- sizeof(ldh->check)));

		if(headcheck != ldh->headcheck)
		{
			fdc.code = -2;
			return fdc;
		}
		int dataLen = decryptData((char *)&(ldh->session),_len+sessionLen,
				ldh->key,ldh->encrypt,
				bfh->sequence,
				time,lfh->srcid,session_auth,(unsigned char *)password,
                _bfh->opcode == 5);

		if (dataLen < 0) {
			fdc.code = -3;
			return fdc;
		}

		unsigned char check = crc8_caculate((const char *)&(ldh->session),dataLen);
		if(check != ldh->check)
		{
			fdc.code = -3;
			return fdc;
		}


		fdc.length = dataLen-sessionLen;

	}
	fdc.fin =  bfh->fin;
	fdc.read = ldh->read;
	fdc.ack = ldh->ack;

	fdc.dataFormat = ldh->format;
	fdc.keyLevel = ldh->key;
	fdc.encryptType = ldh->encrypt;
	//始终返回第一个帧头的opcode
	fdc.opcode = _bfh->opcode;
	fdc.dataSequ = ldh->sequ;

	fdc.msgID = ldh->msgID;
	fdc.sessionId = ldh->session;
	fdc.sequence = bfh->sequence;

	fdc.time = 0;
	memcpy(&(fdc.time),bfh->time,sizeof(bfh->time));
	memcpy(&(fdc.dstID),lfh->dstid,sizeof(lfh->dstid));
	memcpy(&(fdc.srcID),lfh->srcid,sizeof(lfh->srcid));
	if(needCheck)
	{
		fdc.data = (char *)(ldh->session+sessionLen);
//        printToHex("数据头",fdc.data,10);
	}
	else
	{
		fdc.length = 0;
	}
	return fdc;
}

int addZigbeeHeard(frame_data_context *fdc,char *password, int passLen, unsigned char session_auth[16],char * out)
{
	return addHead(fdc,password,passLen,session_auth,out);
//	int _len = 	sizeof(BasicFrameHead)+sizeof(ShortDataHeard);
//	int sessionLen = 2;
//	_len += sessionLen;
//	unsigned char opcode = fdc->opcode;
//
//	BasicFrameHead *bfh = (BasicFrameHead *)out;
//	LongDataHeard *sdh = (LongDataHeard *)(out + sizeof(BasicFrameHead));
//
//	bfh->fin = fdc->fin;
//	bfh->mask = MASK ? 1:0;
//	bfh->ver = VER &0x08;
//	bfh->opcode = opcode;
//	bfh->sequence = fdc->sequence;
//	bfh->fshort = 1;
//
//	memcpy(bfh->time, &(fdc->time), sizeof(bfh->time));
//
//	sdh->read = fdc->read;
//	sdh->ack = fdc->ack;
//	sdh->format = fdc->dataFormat;
//	sdh->key = fdc->keyLevel;
//	sdh->encrypt = fdc->encryptType;
//	sdh->sequ = fdc->dataSequ;
//	sdh->msgID = fdc->msgID;
//	sdh->headcheck = 0;
//	sdh->check = 0;
//	memcpy(sdh->session, fdc->sessionId, sessionLen);
//
//	if(fdc->length > 0) {
//		memmove((sdh->session+sessionLen), fdc->data, fdc->length);
//	}
//	printToHex("session",sdh->session,2);
//	sdh->check = crc8_caculate((const char *)&(sdh->session),(short)fdc->length+sessionLen);
//
//	char _src[8];
//	memcpy(_src,&(fdc->srcID),8);
//
//	short dataLen = encryptData((char *)&(sdh->session),fdc->length+sessionLen,
//			sdh->key,sdh->encrypt,
//			fdc->time,
//			_src,
//			session_auth,
//			(unsigned char *)password);
//	sdh->lenght = dataLen - sessionLen;
//
//	sdh->headcheck = crc8_caculate((const char *)out,
//			(sizeof(BasicFrameHead)
//					+ sizeof(ShortDataHeard)
//					- sizeof(sdh->headcheck)
//					- sizeof(sdh->check)));
//
//	_len += sdh->lenght;
//
//	return _len;
}



frame_data_context parseZigbeeData(const char *data, int dataLen, int s, short ds, char *password, int passLen,long long mac, unsigned char session_auth[16],char needCheck)
{
	return parseData(data,dataLen,s,ds,password,passLen,session_auth,needCheck);
//	BasicFrameHead *bfh = (BasicFrameHead *)data;
//	int sessionLen = 2;
//	LongDataHeard *sdh = (LongDataHeard *)(data + sizeof(BasicFrameHead));
//	frame_data_context fdc;
//	fdc.code = 0;
//	unsigned int _len = sdh->lenght;
//	unsigned short seq =0;
//	unsigned int time = 0;
//	memcpy(&(time),bfh->time,sizeof(bfh->time));
//	seq = bfh->sequence;
//	fdc.length = sdh->lenght;
//	if(needCheck)
//	{
//		if(_len != (dataLen - sizeof(BasicFrameHead) - sizeof(ShortDataHeard) - sessionLen))
//		{
//			fdc.code = -1;
//			return fdc;
//		}
//		unsigned char headcheck = crc8_caculate((const char *)data,
//				(sizeof(BasicFrameHead)
//						+ sizeof(ShortDataHeard)
//						- sizeof(sdh->headcheck)
//						- sizeof(sdh->check)));
//		char aa[16] = {0x8C,0xDA,0x00,0x00,0x01,0x0D,0x3C,0x57,0x46,0x05,0x03,0x00,0x1E,0x00};
//
//		unsigned char hhh = crc8_caculate(aa,12);
//
//
////		if(headcheck != sdh->headcheck)
////		{
////			fdc.code = -2;
////			return fdc;
////		}
//		char _src[8];
//		memcpy(_src,&(mac),8);
//
//		int dataLen = decryptData((char *)&(sdh->session),_len+sessionLen,
//				sdh->key,sdh->encrypt,
//				time,_src,session_auth,(unsigned char *)password);
//		LOGI("%d---------",dataLen);
//
//		if (dataLen < 0) {
//			fdc.code = -3;
//			return fdc;
//		}
//		unsigned char check = crc8_caculate((const char *)&(sdh->session),dataLen);
//		LOGI("%02x-----%02x----",check,sdh->check);
//
//		if(check != sdh->check)
//		{
//			fdc.code = -3;
//			return fdc;
//		}
//
//		fdc.length = dataLen-sessionLen;
//
//	}
//	fdc.fin =  bfh->fin;
//	fdc.read = sdh->read;
//	fdc.ack = sdh->ack;
//	fdc.dataFormat = sdh->format;
//	fdc.keyLevel = sdh->key;
//	fdc.encryptType = sdh->encrypt;
//
//	fdc.opcode = bfh->opcode;
//	fdc.dataSequ = sdh->sequ;
//
//	fdc.msgID = sdh->msgID;
//	fdc.sessionId = sdh->session;
//	fdc.sequence = bfh->sequence;
//
//	fdc.time = 0;
//	memcpy(&(fdc.time),bfh->time,sizeof(bfh->time));
//
//	if(needCheck)
//	{
//		fdc.data = (char *)(sdh->session+sessionLen);
//	}
//	else
//	{
//		fdc.length = 0;
//	}
//	return fdc;
}


unsigned char crc8_caculate(const char *data, unsigned short lenght)
{
	unsigned char			crc = 0, bit = 8;
	unsigned short          i;

	for(i=0; i<lenght; i++) {

		crc ^= (data[i]);

		for(bit=8; bit>0; bit--) {
			if(crc & 0x80) {
				crc = (crc << 1) ^ POLY;
			}else {
				crc = (crc << 1);
			}
		}
	}
	return crc;
}
unsigned int getHeadLength()
{
	return sizeof(BasicFrameHead)+ sizeof(LongFrameHead)+sizeof(LongDataHeard);
}
unsigned int getWSHeadLength()
{
	return sizeof(BasicFrameHead)+ sizeof(LongFrameHead)+sizeof(ProxyFrameHead) +sizeof(BasicFrameHead)+ sizeof(LongFrameHead)+sizeof(LongDataHeard);
}
unsigned int getZigbeeHeadLength()
{
	return sizeof(BasicFrameHead)+ +sizeof(LongDataHeard);
}

void conn_mask_content(char * payload, unsigned char payload_size, char * mask)
{
	unsigned char desp = mask[0];

	unsigned short iter = 0;
	unsigned char	 mask_index = 0;
    while (iter < payload_size)
    {
        /* rotate mask and apply it */
        mask_index = (iter + desp) % 4;
        payload[iter] ^= mask[mask_index];
        iter++;
    } /* end while */
    return;
}

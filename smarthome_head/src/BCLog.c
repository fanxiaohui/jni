/*
 * BCLog.c
 *
 *  Created on: 2015年11月25日
 *      Author: Lal
 */
#include "BCLog.h"
#include "string.h"
# include <stdio.h>
#include <stdarg.h>
#include <stdlib.h> 

const unsigned char hx[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
void printToHex(char * s,unsigned char * p, int len) {
	printf("%s",s);
	for (int i = 0; i < len; i++) {
		unsigned char t = *p++;
		printf("%c", hx[t >> 4]);
		printf("%c", hx[(t & 0x0f)]);
	}
	printf("\n");
}

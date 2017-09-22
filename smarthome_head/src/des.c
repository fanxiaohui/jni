/*
 * FreeSec: libcrypt for NetBSD
 *
 * Copyright (c) 1994 David Burren
 * All rights reserved.
 *
 * Adapted for FreeBSD-2.0 by Geoffrey M. Rehmet
 *	this file should now *only* export crypt(), in order to make
 *	binaries of libcrypt exportable from the USA
 *
 * Adapted for FreeBSD-4.0 by Mark R V Murray
 *	this file should now *only* export crypt_des(), in order to make
 *	a module that can be optionally included in libcrypt.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the author nor the names of other contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 * This is an original implementation of the DES and the crypt(3) interfaces
 * by David Burren <davidb@werj.com.au>.
 *
 * An excellent reference on the underlying algorithm (and related
 * algorithms) is:
 *
 *	B. Schneier, Applied Cryptography: protocols, algorithms,
 *	and source code in C, John Wiley & Sons, 1994.
 *
 * Note that in that book's description of DES the lookups for the initial,
 * pbox, and final permutations are inverted (this has been brought to the
 * attention of the author).  A list of errata for this book has been
 * posted to the sci.crypt newsgroup by the author and is available for FTP.
 *
 * ARCHITECTURE ASSUMPTIONS:
 *	It is assumed that the 8-byte arrays passed by reference can be
 *	addressed as arrays of u_int32_t's (ie. the CPU is not picky about
 *	alignment).
 */

///////////////////////////////////////////////
///
/// 
#include <stdio.h>
#include <string.h>
#include "rpc_des.h"


extern int _des_crypt (char *, unsigned, struct desparams *);

static const char partab[128] =
{
  0x01, 0x01, 0x02, 0x02, 0x04, 0x04, 0x07, 0x07,
  0x08, 0x08, 0x0b, 0x0b, 0x0d, 0x0d, 0x0e, 0x0e,
  0x10, 0x10, 0x13, 0x13, 0x15, 0x15, 0x16, 0x16,
  0x19, 0x19, 0x1a, 0x1a, 0x1c, 0x1c, 0x1f, 0x1f,
  0x20, 0x20, 0x23, 0x23, 0x25, 0x25, 0x26, 0x26,
  0x29, 0x29, 0x2a, 0x2a, 0x2c, 0x2c, 0x2f, 0x2f,
  0x31, 0x31, 0x32, 0x32, 0x34, 0x34, 0x37, 0x37,
  0x38, 0x38, 0x3b, 0x3b, 0x3d, 0x3d, 0x3e, 0x3e,
  0x40, 0x40, 0x43, 0x43, 0x45, 0x45, 0x46, 0x46,
  0x49, 0x49, 0x4a, 0x4a, 0x4c, 0x4c, 0x4f, 0x4f,
  0x51, 0x51, 0x52, 0x52, 0x54, 0x54, 0x57, 0x57,
  0x58, 0x58, 0x5b, 0x5b, 0x5d, 0x5d, 0x5e, 0x5e,
  0x61, 0x61, 0x62, 0x62, 0x64, 0x64, 0x67, 0x67,
  0x68, 0x68, 0x6b, 0x6b, 0x6d, 0x6d, 0x6e, 0x6e,
  0x70, 0x70, 0x73, 0x73, 0x75, 0x75, 0x76, 0x76,
  0x79, 0x79, 0x7a, 0x7a, 0x7c, 0x7c, 0x7f, 0x7f,
};

/*
 * Copy 8 bytes
 */
#define COPY8(src, dst) { \
	register char *a = (char *) dst; \
	register char *b = (char *) src; \
	*a++ = *b++; *a++ = *b++; *a++ = *b++; *a++ = *b++; \
	*a++ = *b++; *a++ = *b++; *a++ = *b++; *a++ = *b++; \
}

/*
 * Copy multiple of 8 bytes
 */
#define DESCOPY(src, dst, len) { \
	register char *a = (char *) dst; \
	register char *b = (char *) src; \
	register int i; \
	for (i = (int) len; i > 0; i -= 8) { \
		*a++ = *b++; *a++ = *b++; *a++ = *b++; *a++ = *b++; \
		*a++ = *b++; *a++ = *b++; *a++ = *b++; *a++ = *b++; \
	} \
}

/*
 * Add odd parity to low bit of 8 byte key
 */
void
des_setparity (char *p)
{
  int i;

  for (i = 0; i < 8; i++)
    {
      *p = partab[*p & 0x7f];
      p++;
    }
}

/*
 * Common code to cbc_crypt() & ecb_crypt()
 */
static int
common_crypt (char *key, char *buf, register unsigned len,
	      unsigned mode, register struct desparams *desp)
{
  register int desdev;

  if ((len % 8) != 0 || len > DES_MAXDATA)
    return DESERR_BADPARAM;

  desp->des_dir =
    ((mode & DES_DIRMASK) == DES_ENCRYPT) ? ENCRYPT : DECRYPT;

  desdev = mode & DES_DEVMASK;
  COPY8 (key, desp->des_key);
  /*
   * software
   */
  if (!_des_crypt (buf, len, desp))
    return DESERR_HWERROR;

  return desdev == DES_SW ? DESERR_NONE : DESERR_NOHWDEVICE;
}

/*
 * CBC mode encryption
 */
int
cbc_crypt (char *key, char *buf, unsigned int len, unsigned int mode,
	   char *ivec)
{
  int err;
  struct desparams dp;

  dp.des_mode = CBC;
  COPY8 (ivec, dp.des_ivec);
  err = common_crypt (key, buf, len, mode, &dp);
  COPY8 (dp.des_ivec, ivec);
  return err;
}

/*
 * ECB mode encryption
 */
int
ecb_crypt (char *key, char *buf, unsigned int len, unsigned int mode)
{
  struct desparams dp;

  dp.des_mode = ECB;
  return common_crypt (key, buf, len, mode, &dp);
}

/////////////////////////////////////////////////////
///
int des_encrypt_ex(const char *key, char *buf, unsigned int length)
{
	char pkey[8], i = 0;
	unsigned char pad = 0;

	memcpy(pkey, key, 8);
	des_setparity(pkey);
	
	pad = length%8;
	if(pad) {
		pad = (8-pad+8);
	}

	for(i=0; i<pad; i++) {
		buf[length++] = pad;
	}

	ecb_crypt(pkey, buf, length, DES_ENCRYPT|DES_HW);

    return length;
}

int des_decrypt_ex(const char *key, char *buf, unsigned int length)
{
	char pkey[8], i;
	unsigned char pad = 0;
	unsigned int bit;
	
	memcpy(pkey, key, 8);
	des_setparity(pkey);
	
	ecb_crypt(pkey, buf, length, DES_DECRYPT|DES_HW);
	pad = buf[length-1];

	if(pad > 15 || pad < 9)
		return length;

	for(i=1; i<=pad; i++) {
		bit = length-i;
		if(buf[bit] != pad)
			return length;
//		buf[bit] = '\0';
	}

	length -= pad;

	return length;
}

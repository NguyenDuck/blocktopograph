package com.mithrilmania.blocktopograph.util;

// This java version of the Mersenne Twister ``MT19937'' is translated
// from the C version by Takuji Nishimura and Makoto Matsumoto
// distributed in the file // mt19937ar-cok.c.  They describe
// this as ``a faster version by taking Shawn Cokus's optimization,
// Matthe Bellew's simplification, Isaku Wada's real version.''
// Hopefully my translation to java has not introduced any new errors.
// One new feature I have added is the capacity to save or restore the
// state of the random number generator, which allows us, for example,
// to rerun the simulation at new parameter values but the same
// random draws, without having to save the entire vector of random draws
// used in the simulation.  I also added a method to simulate a Gaussian
// draw.

// Copyright (c) Philip H. Dybvig 2002
// Philip H. Dybvig <http://phildybvig.com>

// One warning: this implementation is not serialized (so it may become
// confused if different threads call the same instantiation of the
// class at the same time).  In principle, this should be faster than
// a serialized version but some care is required in the use, for
// example, by using different instantiations in different threads that
// may run at the same time.  I would also be wary of code like

//   MTwister mt = new MTwister(274983L);
//   double x;
//   x = mt.genrand_real1() + mt.genrand_real1();

// because some java engine may try to execute the two calls simultaneously
// (good for MP machines perhaps) in different threads.  Just to be safe,
// I would do something like

//   MTwister mt = new MTwister(274983L);
//   double x;
//   x = mt.genrand_real1();
//   x += mt.genrand_real1();

// instead.

// Notes: (1) I applied the C pre-processor to implement the #define
// lines in the original C program.  The resulting code looks ugly
// but should be fast because it moves everything in-line (as in the
// C program).
// (2) I removed technical comments about the algorithm from the original
// program.  For reference, I have posted the version of the Matsumoto-
// Nishimura C program I am working from in the directory
// <http://phildybvig.com/misc/MTwister>.
// (3) While I did spot checks of the code against the distributed output
// from the C program, I do not guarantee conformance to that code and
// in general I offer the same ``caveat emptor'' license as originally
// given by Matsumoto and Nishimura below.
// (4) I have made all the unsigned variables into long's.  Making them
// int's should be possible since int's in java are 32 bit numbers.
// However, they would have to be converted to longs (using the sign
// bit as high order bit) when using them.  I am not sure there would
// be any net savings, and doing it this way requires less debugging.

// Here is the original copyright and contact information for Makoto
// Matsumoto and Takuji Nishimura.

//  Copyright (C) 1997 - 2002, Makoto Matsumoto and Takuji Nishimura,
//  All rights reserved.

//  Redistribution and use in source and binary forms, with or without
//  modification, are permitted provided that the following conditions
//  are met:

//    1. Redistributions of source code must retain the above copyright
//       notice, this list of conditions and the following disclaimer.

//    2. Redistributions in binary form must reproduce the above copyright
//       notice, this list of conditions and the following disclaimer in the
//       documentation and/or other materials provided with the distribution.

//    3. The names of its contributors may not be used to endorse or promote
//       products derived from this software without specific prior written
//       permission.

//  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
//  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
//  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
//  A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
//  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
//  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
//  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
//  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
//  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
//  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
//  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


//  Any feedback is very welcome.
//  http://www.math.keio.ac.jp/matumoto/emt.html
//  email: matumoto@math.keio.ac.jp

public class MTwister {
    long[] state=new long[624];
    int left = 1;
    int initf = 0;
    int inext;
    public MTwister() {}
    public MTwister(long s) {
        init_genrand(s);}
    public MTwister(long[] init_key) {
        init_by_array(init_key);}
    public void init_genrand(long s) {
        int j;
        state[0]= s & 0xffffffffL;
        for (j=1; j<624; j++) {
            state[j] = (1812433253L * (state[j-1] ^ (state[j-1] >> 30)) + j);
            state[j] &= 0xffffffffL;}
        left = 1; initf = 1;}
    public void init_by_array(long[] init_key) {
        int i, j, k;
        int key_length;
        key_length = init_key.length;
        init_genrand(19650218L);
        i=1; j=0;
        k = (624>key_length ? 624 : key_length);
        for (; k>0; k--) {
            state[i] = (state[i] ^ ((state[i-1] ^ (state[i-1] >> 30)) * 1664525L))
                    + init_key[j] + j;
            state[i] &= 0xffffffffL;
            i++; j++;
            if (i>=624) { state[0] = state[624 -1]; i=1; }
            if (j>=key_length) j=0;}
        for (k=624 -1; k>0; k--) {
            state[i] = (state[i] ^ ((state[i-1] ^ (state[i-1] >> 30)) * 1566083941L))
                    - i;
            state[i] &= 0xffffffffL;
            i++;
            if (i>=624) { state[0] = state[624 -1]; i=1; }
        }
        state[0] = 0x80000000L;
        left = 1; initf = 1;}
    public void next_state() {
        int ip=0;
        int j;
        if (initf==0) init_genrand(5489L);
        left = 624;
        inext = 0;
        for (j=624 -397 +1; (--j)>0; ip++)
            state[ip] = state[ip+397] ^ ((( ((state[ip+0]) & 0x80000000L) | ((state[ip+1]) & 0x7fffffffL) ) >> 1) ^ (((state[ip+1]) & 1L) != 0L ? 0x9908b0dfL : 0L));
        for (j=397; (--j)>0; ip++)
            state[ip] = state[ip+397 -624] ^ ((( ((state[ip+0]) & 0x80000000L) | ((state[ip+1]) & 0x7fffffffL) ) >> 1) ^ (((state[ip+1]) & 1L) != 0L ? 0x9908b0dfL : 0L));
        state[ip] = state[ip+397 -624] ^ ((( ((state[ip+0]) & 0x80000000L) | ((state[0]) & 0x7fffffffL) ) >> 1) ^ (((state[0]) & 1L) != 0L ? 0x9908b0dfL : 0L));}

    // generates a random number on [0,0xffffffff]-interval
    public long genrand_int32() {
        long y;
        if (--left == 0) next_state();
        y = state[inext++];
        y ^= (y >> 11);
        y ^= (y << 7) & 0x9d2c5680L;
        y ^= (y << 15) & 0xefc60000L;
        y ^= (y >> 18);
        return y;}

    // generates a random number on [0,0x7fffffff]-interval
    public long genrand_int31() {
        long y;
        if (--left == 0) next_state();
        y = state[inext++];
        y ^= (y >> 11);
        y ^= (y << 7) & 0x9d2c5680L;
        y ^= (y << 15) & 0xefc60000L;
        y ^= (y >> 18);
        return (long)(y>>1);}

    // generates a random number on [0,1]-real-interval
    public double genrand_real1() {
        long y;
        if (--left == 0) next_state();
        y = state[inext++];
        y ^= (y >> 11);
        y ^= (y << 7) & 0x9d2c5680L;
        y ^= (y << 15) & 0xefc60000L;
        y ^= (y >> 18);
        return (double)y * (1.0/4294967295.0);}

    // generates a random number on [0,1)-real-interval
    public double genrand_real2() {
        long y;
        if (--left == 0) next_state();
        y = state[inext++];
        y ^= (y >> 11);
        y ^= (y << 7) & 0x9d2c5680L;
        y ^= (y << 15) & 0xefc60000L;
        y ^= (y >> 18);
        return (double)y * (1.0/4294967296.0);}

    // generates a random number on (0,1)-real-interval
    public double genrand_real3() {
        long y;
        if (--left == 0) next_state();
        y = state[inext++];
        y ^= (y >> 11);
        y ^= (y << 7) & 0x9d2c5680L;
        y ^= (y << 15) & 0xefc60000L;
        y ^= (y >> 18);
        return ((double)y + 0.5) * (1.0/4294967296.0);}

    // generates a random number on [0,1) with 53-bit resolution
    public double genrand_res53() {
        long a=genrand_int32()>>5, b=genrand_int32()>>6;
        return(a*67108864.0+b)*(1.0/9007199254740992.0);}

    // generates a standardized gaussian random number
    public double genrand_gaussian() {
        int i;
        double a;
        a=0.0;
        for(i=0; i<6; i++) {
            a += genrand_real1();
            a -= genrand_real1();}
        return a;}

    // returns the state of the random number generator
    public long[] getState() {
        int i;
        long[] savedState=new long[627];
        for(i=0; i<624; i++) savedState[i] = state[i];
        savedState[624] = (long) left;
        savedState[625] = (long) initf;
        savedState[626] = (long) inext;
        return savedState;}

    // restores the state of the random number generator
    public void setState(long[] savedState) {
        int i;
        for(i=0; i<624; i++) state[i] = savedState[i];
        left = (int) savedState[624];
        initf = (int) savedState[625];
        inext = (int) savedState[626];}

    // main program for testing as a standalone program
// same variables printed as in the C program plus
// 100000 gaussian draws
    public static void main(String[] args) {
        int i;
        long[] init={0x123, 0x234, 0x345, 0x456};
        long length=4;
        MTwister mt0 = new MTwister();
        mt0.init_by_array(init);
        System.out.println("1000 outputs of genrand_int32()");
        for (i=0; i<1000; i++) {
            System.out.println(mt0.genrand_int32());
            if (i%5==4) System.out.println("\n");}
        System.out.println("\n1000 outputs of genrand_real2()\n");
        for (i=0; i<1000; i++) {
            System.out.println(mt0.genrand_real2());
            if (i%5==4) System.out.println("\n");}
        System.out.println("\n100000 outputs of genrand_gaussian()\n");
        for (i=0; i<100000; i++) {
            System.out.println(mt0.genrand_gaussian());
            if (i%5==4) System.out.println("\n");}}}


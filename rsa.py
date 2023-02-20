#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Feb 20 14:49:02 2023

@author: breeze
"""

import rsa
numBits = 512

def genKeys():
    (pubKey, privKey) = rsa.newkeys(numBits)
    return (pubKey, privKey)
def encrypt(string, key):
    #using private key
    string = string.encode('utf8')
    signature = rsa.sign(string, key, 'SHA-256')
    return signature
def decrypt(string, signature, key):
    #using public key
    string = string.encode('utf8')
    string = rsa.verify(string, signature, key)
    return string
(pubKey, privKey) = genKeys()
S = "hello world oiqwneakjns;lekjfbnkbakzcnjkaeshr leadfnlaewjszdbckaewlshf "
sign = encrypt(S, privKey)
print(sign)
result = decrypt(S, sign, pubKey)
print(S)
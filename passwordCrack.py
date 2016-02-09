#!/usr/bin/python
# -*- coding: utf-8 -*-
import sys, re, hashlib, os, getopt, datetime
# Check hash length
def chklength(hashes):
    if len(hashes) != 32:
        sys.exit(1)

# Attempts to crack hash against any givin wordlist.
def dict_attack(md5table, dictionary):
    print "Checking wordlist for actual words"
    try:
        dictionary != None
    except:
        print("Check your wordlist path.")
        sys.exit(-1)
    print "Cracking the md5 hashes"
    start = datetime.datetime.now()

    with open(dictionary, 'r', 1) as wordlist:
        with open(md5table, 'r') as hashlist:
            for word in wordlist:
                for hash_2_crack in hashlist:
                    chklength(hash_2_crack.strip())
                    hash_2_crack.strip()
                    word.strip()
                    print word
                    if hashlib.md5(word).hexdigest() == hash_2_crack:
                        finish = datetime.datetime.now()
                        print "MD5: ", hash_2_crack ,", Password: ", word ,", Time: ", finish -start
            print "Finished"

def main(argv):
    md5table = ""
    dictionary = ""
    try:
        opts, args = getopt.getopt(sys.argv[1:],"t:d:",["md5table=","dictionary="])
    except getopt.GetoptError:
        print 'passwordCrack.py -t <md5table> -d <dictionary>'
        sys.exit(2)
    for opt, arg in opts:
        if opt == '-h':
            print 'passwordCrack.py -t <md5table> -d <dictionary>'
            sys.exit()
        elif opt in ('-t', '--table'):
            md5table = arg
        elif opt in ('-d', '--dictionary'):
            dictionary = arg
    print "md5table is ", md5table
    print "dictionary file is ", dictionary
    dict_attack(md5table, dictionary)

if __name__ == "__main__":
   main(sys.argv[1:])

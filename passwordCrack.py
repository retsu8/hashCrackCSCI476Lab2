#!/usr/bin/python
# -*- coding: utf-8 -*-
import sys, re, hashlib, os, getopt
# Check hash length
def chklength(md5table):
    with open(md5table) as fileobj:
        for hashes in fileobj:
            print hashes
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
    chklength(md5table)

    with open(dictionary) as fileobj:
        for hash_2_crack in md5table:
            for word in fileobj:
                word.word.strip()
                if hashlib.md5(word).hexdigest() == hash_2_crack:
                    print "MD5 : %s , Password: %s" % (hash_2_crack, word)
            print "Failed to crack file."

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

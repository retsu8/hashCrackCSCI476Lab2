#!/usr/bin/python
# -*- coding: utf-8 -*-
import sys, re, hashlib, os, getopt, datetime, multiprocessing, thread
from collections import Counter

# Check hash length
def md5Cracker(crackedPassword, dictionary):
    with open(dictionary, 'r') as wordlist:
        start = datetime.datetime.now()
        for word in wordlist:
            #print "Checking: ", word.strip()
            if hashlib.md5(word.strip()).hexdigest() in crackedPassword:
                finish = datetime.datetime.now()
                time = finish - start
                crackedPassword[hashlib.md5(word.strip()).hexdigest()] = word.strip(), time.total_seconds()
    return crackedPassword
    wordlist.close()
    return None

def dict_attack(md5table, dictionary):
    threadcount = multiprocessing.cpu_count() -1
    print "Checking wordlist for actual words"
    try:
        dictionary != None
    except:
        print("Check your wordlist path.")
        sys.exit(-1)
    print "Grabbing md5 hashes"
    crackedPassword = dict()
    with open(md5table, 'r') as hashlist:
        for line in hashlist:
            line = line.strip()
            crackedPassword[line] = None
    hashlist.close()
    print "Cracking the md5 hashes"
    crackedPassword = md5Cracker(crackedPassword, dictionary)
    for key, value in crackedPassword.iteritems():
        print "The MD5 Hash: %s and the Password/Time is %s" % (key, value)

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

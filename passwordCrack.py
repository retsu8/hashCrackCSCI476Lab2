#!/usr/bin/python
# -*- coding: utf-8 -*-
import sys, re, hashlib, os, getopt, datetime, multiprocessing, thread
from numbapro import cuda
# Check hash length
class myThread (threading.Thread):
    def __init__(self, threadID, name, counter):
        threading.Thread.__init__(self)
        self.threadID = threadID
        self.name = name
        self.counter = counter
    def run(self):
        print "Starting " + self.name
        print_time(self.name, self.counter, 5)
        print "Exiting " + self.name

def chklength(crackedMD5):
    for hashes in crackedMD5:
        if len(hashes.strip()) != 32:
            sys.exit(1)
def buildingThreads():
    try:
        thread.start_new_thread()
    except:
        print "Error: unable to start thread"
def md5Cracker(crackedMD5, dictionary, passwords):
    with open(dictionary, 'r') as wordlist:
        for word in wordlist:
            print "Checking: ", word.strip()
            for hash_2_crack in crackedMD5:
                if hashlib.md5(word.strip()).hexdigest() == hash_2_crack:
                    passwords.append(word)
                    break
                elif len(passwords) >= len(crackedMD5):
                    wordlist.close()
                    return passwords
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
    crackedMD5 = []
    passwords = []
    i = 0
    with open(md5table, 'r') as hashlist:
        for hashes in hashlist:
            crackedMD5.append(hashes)
    hashlist.close()
    chklength(crackedMD5)
    start = datetime.datetime.now()
    print "Cracking the md5 hashes"
    passwords = md5Cracker(crackedMD5, dictionary, passwords)
    finish = datetime.datetime.now()
    time = finish - start
    for h, p, t in zip(crackedMD5, passwords, time):
        print 'Hash: {0} Password: {1} Time: {2}.'.format(h, p, t)
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

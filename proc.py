#! /usr/local/bin/python

import getopt,sys,commands

def getNoProc(user):
  sss =''
  #if scommands.getoutput('users | grep -x '+user)=='': 
   # print "There is no such username logged in currently"
    #return -1
  #else:
  s = 'top -n 1 -b | grep ' + user
  sss = commands.getoutput(s)
  print sss
  sss = sss.split('\n');
  print "The number of processes executed by user '%s': %d\n" %(user,len(sss))

  return len(sss)
#-----------------------------------------------

def getUserList():
  s = commands.getoutput('users')
  s = s.split()
  #s = set(s)
  #print s
  return set(s)


def main(argv):
   user = ''
   total = 0

   try:
      opts, args = getopt.getopt(argv,"hu:a",["uname="])
   except getopt.GetoptError:
      print 'usage: proc.py -u <username> -a\n'
      sys.exit(2)
   for opt, arg in opts:
      if opt == '-h':
         print 'usage: proc.py -u <username> -a'
         print '  -u <username>, Get number of process for the specified username'
         print '  -a, Get number of process for all the connected users'
         sys.exit()
      elif opt in ("-u", "--uname"):
         user = arg
         print 'Username is: ', user
         nop=getNoProc(user)
         if nop==-1: 
           print "Ezequil 25:17"
         #print "The number of processes executed by user '%s': %d\n" %(user,nop)
      elif opt in ("-a"):
        ul = getUserList()
        for user in ul:
          nop=getNoProc(user)
          total = total + nop
        print "Total number of processes running: %d" %(total)
      else:
        print 'usage: proc.py -u <username> -a\n'
        sys.exit(2)


  

if __name__ == "__main__":
  main(sys.argv[1:])

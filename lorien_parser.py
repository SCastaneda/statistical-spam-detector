import sys
from os import walk
from email.parser import Parser
import base64

# base64.b64decode(str)
# pe.get_payload()
# keys: ['Delivered-To', 'Received', 'Received', 'Received', 'Received', 'Received', 'X-EX-Selector', 'To', 'Message-ID', 'Date', 'From', 'Subject', 'MIME-Version', 'Content-Type', 'Content-Transfer-Encoding']

# mypath = os.getcwd()
# for (dirpath, dirnames, filenames) in os.walk(mypath):

# file = open('/Users/sam/projects/java/statistical-spam-detector/emails/lorien/1413932181.22819_329.lorien', "r")
# email = file.read()
# pe = Parser().parsestr(email)
saveToFolder = "emails/"
spamFolder = "emails/all/"

def main(argv):
    # get path of the email folders
    folder = parseArgs(argv)

    appendTo = folder+"_conc.txt"

    mypath = spamFolder+folder
    for (dirpath, dirnames, filenames) in walk(mypath):
        for filename in filenames:
            if(filename != ".DS_Store"):
                handleSingleFile(dirpath+"/"+filename, appendTo)

def handleSingleFile(filepath, appendTo):
    FILE = open(filepath, "r")
    email = FILE.read()
    FILE.close()

    parsed = Parser().parsestr(email)

    ourFormat = "\n::NEW EMAIL::"
    ourFormat += "\n::LABEL::\nSPAM"
    ourFormat += "\n::SUBJECT::\n" + `parsed['Subject']`
    ourFormat += "\n::BODY::\n"

    body = parsed.get_payload()

    # check if we need to decode the body from base 64
    if 'Content-Transfer-Encoding' in parsed and parsed['Content-Transfer-Encoding'].lower == 'base64':
        body = base64.b64decode(body)
    
    ourFormat += `body` + "\n"

    appendToFile(saveToFolder + appendTo, ourFormat.decode('string_escape'))


def parseArgs(argv):
    # make sure we have the path
    if len(argv) == 2:
        return argv[1];
    else:
        printUsageAndExit()
    # print argv

def appendToFile(filename, text):
    with open(filename, "a") as myfile:
        myfile.write(text)

def printUsageAndExit():
    print "usage:"
    print "\t" + sys.argv[0] + "spam_folder"
    print "\n\twhere `spam_folder` is assumed to reside in: " + spamFolder
    sys.exit()

main(sys.argv)
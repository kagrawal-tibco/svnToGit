 =================================================================
 TIBCO BusinessEvents
 Copyright (c) 2001-2011 TIBCO Software Inc.
 =================================================================

 What is this?
 ---------------------------------------------

 This tool helps you send many events you can configure to EMS
 so BE can consume. If you just run the program with no arguments,
 it will print the usage. See more below.


 Compiling
 ---------------------------------------------

 Ensure jsm.jar, tibjms.jar, tibjmsadmin.jar, and tibcrypt.jar is
 in your CLASSPATH (all files from EMS).

 Execute:

    cd .....
    javac -d . ....\be\5.0\tools\test\src\com\tibco\cep\test\ems\*.java


 Running
 ---------------------------------------------

 You can either pass on the command-line arguments or create a text file
 where you can specify what messages you want to send, how many, in what
 order, etc...

 Command-line way (it only limits you to one "MessageTask"):

    java com.tibco.cep.test.ems.BEJMSMessageSimulator -server mainhost:7222
	-queue MY_QUEUE -count 10000
	-event:start /Events/Start -event:end /Events/End
	-event /Events/MY_EVENT
	-property AccountId Gabe String true
	-property Balance 100000 Double false

 Via a message input file (RECOMMENDED WAY):

    java com.tibco.cep.test.ems.BEJMSMessageSimulator -messageInput sampleTaskInput.txt

 See the sampleTaskInput.txt file included in this directory.
 Here it is included for your convenience, along with some explanations (notice you need
 to remove the "<<<<<<" comments.

 In order to "receive" messages, add the "receive" qualifier in the transport.
 Messages received are considered as matched if the boolean at the end of "event" or
 each property as "true". If their values is "false" it means the match check is not done 
 and the message is counted (considered as received as expected). Default is true.


 Sample Message Input File
 ---------------------------------------------

# put any comments you want here

name startTask				<<<<< start defining your message tasks
description Initialize stuff		<<<<< task names must be unique, no spaces
transport ems send
target queue Subject.AccountDebit
count 1
event /Events/Start

name taskA
description Create accounts
transport ems send			<<<<<< "send" or "receive"
target queue Subject.AccountDebit
count 5000
event /Events/CreateAccount true	<<<<<< message only counted as received if event matches
property AccountId Gabe String true false	<<<<<< "true' means generate unique values
property Balance 100000 Double false false	<<<<<< like "Gabe0, Gabe1, Gabe2"
# shellcmd sleep 10

name taskB
description Debit some of the accounts
transport ems receive
target queue Subject.AccountDebit
count 1000
event /Events/Debit
property AccountId Gabe String true
property Amount 2500 Double false	<<<<<< "false" means don't increment each time

name endTask
description End stuff
transport ems send
target queue Subject.AccountDebit
count 1
event /Events/End

# execute startTask, taskA, followed by taskB executed twice, then taskA again, then endTask
execute startTask taskA taskB*2 taskA endTask

FTP Server Implementation with Java.
===

This is an implementation of a FTP Server with Java, it recognizes the following commands : USER, PASS, RETR, STOR, LIST, CWD, CDUP, PWD, QUIT, TYPE, PORT, SYST.
It opens communication on port 1050. The server lets you only access your home directory, because of security reasons.

The project includes some unit testing classes.

If you want to try the server just execute the command : **java -jar serveurFTP.jar** and connect with any FTP Client, as filezilla, or ftp.



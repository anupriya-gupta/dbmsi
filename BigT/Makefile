JDKPATH = /usr/lib/jvm/java-11-oracle
LIBPATH = /home/anupriya/Desktop/DBMSI-master
CLASSPATH = $(JDKPATH)/lib/classes.zip:.:..:$(LIBPATH)
BINPATH = $(JDKPATH)/bin
JAVAC = $(JDKPATH)/bin/javac -classpath $(CLASSPATH)
JAVA  = $(JDKPATH)/bin/java  -classpath $(CLASSPATH)

PROGS = together

all: $(PROGS)

hf:Heapfile.java
	$(JAVAC) bigt.java

hp:HFPage.java
	$(JAVAC) BigPage.java

tuple:Tuple.java
	$(JAVAC) Map.java

scan:Scan.java
	$(JAVAC) Stream.java

together:*.java
	$(JAVAC) *.java

clean:
	\rm -f *.class *~ \#* core 

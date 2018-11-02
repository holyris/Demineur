JFLAGS = -g
JC = javac
JVM = java


.SUFFIXES: .java .class


.java.class:
		$(JC) $(JFLAGS) $*.java


CLASSES = \
		Demineur.java \
		Fenetre.java \
		GetData.java \
		Panneau.java \
		SwitchFenetre.java \
		Eventjeu.java \
		WindowSave.java \
		SaveGame.java


MAIN = Demineur


default: classes


classes: $(CLASSES:.java=.class)


test: $(MAIN).class
	$(JVM) $(MAIN)


clean:
		$(RM) *.class

JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Board.java \
        Cards.java \
        DeadWood.java \
        Deck.java \
	Dice.java \
	Office.java \
	Player.java \
	Room.java \
	Trailer.java  

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class